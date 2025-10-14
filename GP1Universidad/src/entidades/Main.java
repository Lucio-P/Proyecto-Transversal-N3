/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package entidades;

import java.time.LocalDate;
import persistencia.AlumnoData;
import persistencia.InscripcionData;
import persistencia.MateriaData;

/**
 *
 * @author lucio
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        
   /* Alumno More = new Alumno (11455098, "Arias", "Morena", LocalDate.of(2003, 11, 14), true);
    AlumnoData alumn = new AlumnoData();
    alumn.guardarAlumno(More);
    alumn.modificarAlumno(More);
    alumn.eliminarAlumno(1);*/
    
    /*Materia matematicas = new Materia("Matematicas 1", 1, 1);
    MateriaData mates = new MateriaData();
    mates.guardarMateria(matematicas);
    mates.buscarMateria(1);
    mates.modificarMateria(matematicas);
    mates.eliminarMateria(1);*/
   
   AlumnoData ad = new AlumnoData();
   MateriaData md = new MateriaData();
   InscripcionData id = new InscripcionData();
   
   Alumno More = ad.buscarAlumno(1);
   Materia matematicas = md.buscarMateria(1);
   Inscripcion insc = new Inscripcion(5, More, matematicas);
   id.guardarInscripcion(1, 1);
   for (Inscripcion inscripcion : id.ObtenerInscripciones()) {
       System.out.println("id " + inscripcion.getIdInscripto());
       System.out.println("apellido " + inscripcion.getAlumno().getApellido());
       System.out.println("materia " + inscripcion.getMateria().getNombre());
   }
    }
    
    }           
