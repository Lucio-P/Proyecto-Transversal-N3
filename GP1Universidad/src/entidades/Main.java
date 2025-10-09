/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package entidades;

import java.time.LocalDate;
import persistencia.AlumnoData;

/**
 *
 * @author lucio
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        
    Alumno More = new Alumno (11455098, "Arias", "Morena", LocalDate.of(2003, 11, 14), true);
    AlumnoData alumn = new AlumnoData();
    alumn.guardarAlumno(More);  /*agregue alumno*/
    alumn.modificarAlumno(More); /*modifique el nombre*/
    alumn.eliminarAlumno(1); /*el esatado paso a false*/
    
    }           
}
