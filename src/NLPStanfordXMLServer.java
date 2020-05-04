import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Query;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class NLPStanfordXMLServer implements Container {
    private static StanfordCoreNLP pipeline;
    private static Properties props;
    private static int port = 8080;
    private static final Logger log = Logger.getLogger( NLPStanfordXMLServer.class.getName() );
    private static int total_requests = 0;

    // An interface to the Stanford Core NLP
    public String parse(String s) throws java.io.IOException {
        Annotation annotation = new Annotation(s);
        pipeline.annotate(annotation);
        StringWriter sb = new StringWriter();
        pipeline.xmlPrint(annotation, sb);
        return sb.toString();
    }

    public void handle(Request request, Response response) {
        try {
            int request_number = ++total_requests;
            log.info("Request " + request_number + " from " + request.getClientAddress().getHostName());
            long time = System.currentTimeMillis();
   
            response.setValue("Content-Type", "text/xml");
            response.setValue("Server", "Stanford CoreNLP XML Server/1.0 (Simple 5.1.6)");
            response.setDate("Date", time);
            response.setDate("Last-Modified", time);
   
            // pass "text" POST query to Stanford Core NLP parser
            String text = request.getQuery().get("text");  
            PrintStream body = response.getPrintStream();
            body.println(parse(text));
            body.close();

            long time2 = System.currentTimeMillis();
            log.info("Request " + request_number + " done (" + (time2-time) + " ms)");
        } catch(Exception e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    } 

    public static void main(String args[]) throws Exception {
        // Use port if given
        try {
            port = Integer.parseInt(args[0]);
        } catch(Exception e) {
            // Silently keep port at 8080
        }

        props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");

        // Initialize the Stanford Core NLP
        pipeline = new StanfordCoreNLP(props);

        // Start the server
        Container container = new NLPStanfordXMLServer();
        Server server = new ContainerServer(container);
        Connection connection = new SocketConnection(server);
        SocketAddress address = new InetSocketAddress(port);
        connection.connect(address);

        log.info("Initialized server at port " + port + ".");
    }
}
