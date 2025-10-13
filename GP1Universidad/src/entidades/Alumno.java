/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDate;
/**
 *
 * @author arias
 */
public class Alumno {
    
   private int id_alunmo;
   private int dni;
   private String apellido;
   private String nombre;
   private LocalDate fachaNacimiento;
   private boolean estado;

    public Alumno(int id_alunmo, int dni, String apellido, String nombre, LocalDate fachaNacimiento, boolean estado) {
        this.id_alunmo = id_alunmo;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fachaNacimiento = fachaNacimiento;
        this.estado = estado;
    }

    public int getId_alunmo() {
        return id_alunmo;
    }

    public void setId_alunmo(int id_alunmo) {
        this.id_alunmo = id_alunmo;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFachaNacimiento() {
        return fachaNacimiento;
    }

    public void setFachaNacimiento(LocalDate fachaNacimiento) {
        this.fachaNacimiento = fachaNacimiento;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Alumno(int dni, String apellido, String nombre, LocalDate fachaNacimiento, boolean estado) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fachaNacimiento = fachaNacimiento;
        this.estado = estado;
    }

    public Alumno() {
        
    }

    @Override
    public String toString() {
        
        return nombre + " " + apellido;
    }

}
