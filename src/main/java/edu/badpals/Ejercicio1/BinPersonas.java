package edu.badpals.Ejercicio1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
                escritor.writeObject(m.getNombre() + m.getEdad());
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("Nombres y edades guardados en nombreEdad.bin");
    }

    public static void toXML() {
        // TODO
    }
}
