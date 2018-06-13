package javaXml;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class xml1 {//Declaracion de propiedades
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    public Document document;

    //=====================================================
    //Inicializa los constructores
    public xml1(String sourceXml) {
        try {
            factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            builder = factory.newDocumentBuilder();
            document = builder.parse(sourceXml);
        }
        catch (org.xml.sax.SAXException e) {
            System.out.println("Error al cargar el archivo XML 1 ");
        }
        catch (javax.xml.parsers.ParserConfigurationException e) {
            System.out.println("Error al cargar el archivo XML 2 " + e);
        }
        catch (java.lang.Exception e) {
            System.out.println("Error al cargar el archivo XML 3 " + e);
        }
    }
}