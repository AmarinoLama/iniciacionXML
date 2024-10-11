package edu.badpals.Ejercicio1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinPersonas {

    public static void main(String[] args) {
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Juan", 20));
        personas.add(new Persona("Pedro", 30));
        personas.add(new Persona("Maria", 40));

        guardarPersonas(personas);

        guardarNombreEdad(personas);

        toXML("ref/nombreEdad.bin");

    }

    public static void guardarPersonas(List<Persona> personas) {
        try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream("ref/personas.bin"));) {
            for (Persona m : personas) {
                escritor.writeObject(m);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("Personas guardadas como objetos en personas.bin");
    }

    public static void guardarNombreEdad(List<Persona> personas) {
        try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream("ref/nombreEdad.bin"));) {
            for (Persona m : personas) {
                escritor.writeObject(m.getNombre() + " " + m.getEdad());
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("Nombres y edades guardados en nombreEdad.bin");
    }

    public static List<String> readBin(String path) {  // CAMBIAR STRING X PERSONA PARA EL OTRO BIN
        List<String> personas = new ArrayList<>();  // CAMBIAR STRING X PERSONA PARA EL OTRO BIN

        try (ObjectInputStream lector = new ObjectInputStream(new FileInputStream(path));) {
            while (true) {
                Object o = lector.readObject();
                if (o instanceof String) {  // CAMBIAR STRING X PERSONA PARA EL OTRO BIN
                    personas.add((String) o);  // CAMBIAR STRING X PERSONA PARA EL OTRO BIN
                }
            }

        } catch (EOFException ex) {
            System.out.println("Hemos llegado al final del archivo.");
            for (String m : personas) {  // CAMBIAR STRING X PERSONA PARA EL OTRO BIN
                System.out.println(m);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }
        return personas;
    }

    public static Document binToDOM(List<String> personas) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("personas");
            doc.appendChild(rootElement);

            for (String persona : personas) {
                String[] parts = persona.split(" ");
                String nombre = parts[0];
                String edad = parts[1];

                Element personaElement = doc.createElement("persona");

                Element nombreElement = doc.createElement("nombre");
                nombreElement.appendChild(doc.createTextNode(nombre));
                personaElement.appendChild(nombreElement);

                Element edadElement = doc.createElement("edad");
                edadElement.appendChild(doc.createTextNode(edad));
                personaElement.appendChild(edadElement);

                rootElement.appendChild(personaElement);
            }

            return doc;
        } catch (ParserConfigurationException ex) {
            System.err.println("Error al crear el documento DOM: " + ex.getMessage());
            return null;
        }
    }

    public static void toXML(String binPath) {

        Document doc = binToDOM(readBin(binPath));
        try {
            File f=new File("ref/personas.xml");
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            StreamResult result = new StreamResult(f);
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.out.println("¡Error! No se ha podido llevar a cabo la transformación.");
        }
    }
}