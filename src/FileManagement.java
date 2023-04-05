import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class FileManagement {
    /**
     * Open and read file .xml.
     */
    DictonaryManagement dictonaryManagement = new DictonaryManagement();
    private DocumentBuilderFactory fac;
    private DocumentBuilder build;
    private Document doc;
    final static String file_name = "src/Tu_dien.xml";

    {
        try {
            fac = DocumentBuilderFactory.newInstance();
            build = fac.newDocumentBuilder();
            doc = (Document) build.parse(file_name);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public Document getDoc() {
        return doc;
    }

    public DocumentBuilderFactory getFac() {
        return fac;
    }

    public void setFac(DocumentBuilderFactory fac) {
        this.fac = fac;
    }

    public DocumentBuilder getBuild() {
        return build;
    }

    public void setBuild(DocumentBuilder build) {
        this.build = build;
    }

    /**
     * Read the content of the file.
     */
    public void readFileXml() {
        try {
            NodeList dictionary = doc.getElementsByTagName("record");
            for (int i = 0; i < dictionary.getLength(); i++) {
                NodeList record = dictionary.item(i).getChildNodes();
                String word = null, meaning = null;
                for (int j = 0; j < record.getLength(); j++) {
                    if ("word".equals(record.item(j).getNodeName()))
                        word = record.item(j).getTextContent();
                    if ("meaning".equals(record.item(j).getNodeName()))
                        meaning = record.item(j).getTextContent();
                }
                if (word != null && meaning != null) {
                    Dictonary.map.put(word, meaning);
                }
            }


        } catch (DOMException ex) {
        }
    }

    /**
     * Write the content to the file.
     */
    public void writeToFile() {
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tr = null;
        try {
            tr = tff.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        tr.setOutputProperty(OutputKeys.INDENT,"yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}index-amount", "3");
        DOMSource nguon = new DOMSource(this.doc);
        StreamResult dich = new StreamResult(file_name);
        try {
            tr.transform(nguon, dich);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("Da ghi xong");
    }

    /**
     * Add new word to the dictionary.
     */
    public void addWord(Element dictonary, record rs) {
        Element record = doc.createElement("record");
        Element word = doc.createElement("word");
        word.setTextContent(rs.getWord());
        Element meaning = doc.createElement("meaning");
        meaning.setTextContent(rs.getMeaning());

        record.appendChild(word);
        record.appendChild(meaning);
        dictonary.appendChild(record);
        writeToFile();
        dictonaryManagement.addWord(rs);
    }

    /**
     * Edit a word in the dictionary.
     */
    public void editWord(record rs, String newWord) {
        NodeList lst = doc.getElementsByTagName("record");
        int i = lst.getLength() - 1;
        while (i >= 0) {
            Element e = (Element) lst.item(i);
            String id = e.getElementsByTagName("word").item(0).getTextContent();
            if (id.equals(newWord)) {
                e.getElementsByTagName("word").item(0).setTextContent(rs.getWord());
                e.getElementsByTagName("meaning").item(0).setTextContent(rs.getMeaning());
                writeToFile();
                dictonaryManagement.editWord(rs);
                break;
            }
            i--;
        }
    }

    /**
     * Remove a word from the dictionary.
     */
    public void deleteWord(String newWord) {
        NodeList lst = doc.getElementsByTagName("record");
        int i = lst.getLength() - 1;
        while (i >= 0) {
            Element e = (Element) lst.item(i);
            String id = e.getElementsByTagName("word").item(0).getTextContent();
            if (id.equals(newWord)) {
                lst.item(i).getParentNode().removeChild(lst.item(i));
                writeToFile();
                dictonaryManagement.deleteWord(newWord);
                break;
            }
            i--;
        }
    }
}
