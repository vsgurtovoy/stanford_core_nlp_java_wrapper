## About

Java wrapper for the [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml), that provides the HTTP-XML-Server interface.

## Example

The server is listening <http://localhost:8080>. The text you want to analyze needs to be POSTed as field `text`:

     curl --data 'text=Hello world!' http://localhost:8080

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet href="CoreNLP-to-HTML.xsl" type="text/xsl"?>
<root>
  <document>
    <sentences>
      <sentence id="1">
        <tokens>
          <token id="1">
            <word>Hello</word>
            <lemma>hello</lemma>
            <CharacterOffsetBegin>0</CharacterOffsetBegin>
            <CharacterOffsetEnd>5</CharacterOffsetEnd>
            <POS>UH</POS>
            <NER>O</NER>
          </token>
          <token id="2">
            <word>world</word>
            <lemma>world</lemma>
            <CharacterOffsetBegin>6</CharacterOffsetBegin>
            <CharacterOffsetEnd>11</CharacterOffsetEnd>
            <POS>NN</POS>
            <NER>O</NER>
          </token>
          <token id="3">
            <word>!</word>
            <lemma>!</lemma>
            <CharacterOffsetBegin>11</CharacterOffsetBegin>
            <CharacterOffsetEnd>12</CharacterOffsetEnd>
            <POS>.</POS>
            <NER>O</NER>
          </token>
        </tokens>
        <parse>(ROOT (S (VP (NP (INTJ (UH Hello)) (NP (NN world)))) (. !))) </parse>
        <dependencies type="basic-dependencies">
          <dep type="root">
            <governor idx="0">ROOT</governor>
            <dependent idx="2">world</dependent>
          </dep>
          <dep type="discourse">
            <governor idx="2">world</governor>
            <dependent idx="1">Hello</dependent>
          </dep>
        </dependencies>
        <dependencies type="collapsed-dependencies">
          <dep type="root">
            <governor idx="0">ROOT</governor>
            <dependent idx="2">world</dependent>
          </dep>
          <dep type="discourse">
            <governor idx="2">world</governor>
            <dependent idx="1">Hello</dependent>
          </dep>
        </dependencies>
        <dependencies type="collapsed-ccprocessed-dependencies">
          <dep type="root">
            <governor idx="0">ROOT</governor>
            <dependent idx="2">world</dependent>
          </dep>
          <dep type="discourse">
            <governor idx="2">world</governor>
            <dependent idx="1">Hello</dependent>
          </dep>
        </dependencies>
      </sentence>
    </sentences>
  </document>
</root>
```

## Installation

1. Clone the repository.

2. Download and install the third party libraries:
    
        cd StanfordCoreNLPXMLServer
        ant libs

3. Compile the JAR file:

        ant jar

4. Run the server:

        ant run

5. The server is now waiting on <http://localhost:8080> for HTTP POST requests. Note the initialization can take a few minutes, because several modules and resources of Stanford CoreNLP need to be loaded.

    You can also choose a port:

        ant run -Dport=9000

## Prerequisites

- [Oracle JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html);
- [Apache Ant](http://ant.apache.org).

## Third Party Libraries

- Java based HTTP engine [Simple](http://www.simpleframework.org);
- [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml), a suite of core NLP tools.

## License

- [Simple](http://www.simpleframework.org) is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0);
- [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) is licensed under the [GNU General Public License (v2 or later)](http://www.gnu.org/licenses/gpl-2.0.html).
