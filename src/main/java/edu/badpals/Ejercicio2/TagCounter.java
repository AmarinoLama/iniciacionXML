package edu.badpals.Ejercicio2;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TagCounter {
    public static void main(String[] args) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFIle = new File("ref/personas.xml");
        Set<String> tags = new HashSet<>();

        try {
            Document document = builder.build(xmlFIle);
            Element rootNode = document.getRootElement();
            Iterator<Content> iterator = rootNode.getDescendants();

            while (iterator.hasNext()) {
                Content content = iterator.next();
                if (content instanceof Element) {
                    String tag = ((Element) content).getName();
                    tags.add(tag);
                }
            }

            System.out.println("Cantidad de etiquetas: " + tags.size() + "\n" + tags);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }
}
