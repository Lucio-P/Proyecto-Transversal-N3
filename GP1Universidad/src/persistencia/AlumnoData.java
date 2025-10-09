/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidades.Alumno;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

 /**
 *
 * @author arias
 */
public class AlumnoData {
    
 private Connection con = null;

    public AlumnoData() {
        con = miConexion.getmiConexion();
    }
    
    public void guardarAlumno(Alumno alumno){
        try {
            String sql = "INSERT INTO `alumno`(dni, apellido, nombre, fechaNacimiento, estado)"
                    + " VALUES (?,?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFachaNacimiento()));
            ps.setBoolean(5, alumno.isEstado());
            ps.executeUpdate();
            
            ResultSet rs =ps.getGeneratedKeys();
            if (rs.next()) {
                alumno.setId_alunmo(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Se agrego exitosamente!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: "+ ex);
        }
    }
    
    public void modificarAlumno(Alumno alumno){
        
        String sql = "UPDATE alumno SET dni=?, apellido = ?,nombre = ?,fechaNacimiento = ? WHERE id_alumno = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFachaNacimiento()));          
            ps.setInt(5, alumno.getId_alunmo());
            
            int exito =ps.executeUpdate();
            if (exito ==1) {
                JOptionPane.showMessageDialog(null, "Modificacion exitosa");
            }
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ex);
        }
    }
    
    
    public void eliminarAlumno(int id){
        String sql = "UPDATE alumno SET estado = 0 WHERE id_alumno=?";
        
        
        try {
            PreparedStatement  ps = con.prepareStatement(sql);
            
            ps.setInt(1, id);
            int exito = ps.executeUpdate();
            if (exito ==1) {
                JOptionPane.showMessageDialog(null, "Alumno eliminado");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage());
        }
    }
    
    public Alumno buscarAlumno(int id){
    
        String sql = "SELECT  dni, apellido, nombre, fechaNacimiento  FROM alumno WHERE id_alumno=? AND estado=1";
        
        Alumno alumno = null;
                
        try {
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                alumno= new Alumno();
                alumno.setId_alunmo(id);
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFachaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(true);
            }else  {
            JOptionPane.showMessageDialog(null, "No existe el alumno");}
            ps.close();
        
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage());
        }
        return alumno;
    }
    public Alumno buscarAlumnoPorDNI(int dni){
    
        String sql = "SELECT  id_alumno,dni, apellido, nombre, fechaNacimiento  FROM alumno WHERE dni=? AND estado=1";
        
        Alumno alumno = null;
        
        
        try {
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                alumno= new Alumno();
                alumno.setId_alunmo(rs.getInt("id_alumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFachaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(true);
            }else  {
            JOptionPane.showMessageDialog(null, "No existe el alumno");}
            ps.close();
        
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage());
        }
        return alumno;
    }
    
    public ArrayList<Alumno> listarAlumnos(){
    
        String sql = "SELECT  id_alumno,dni, apellido, nombre, fechaNacimiento  FROM alumno WHERE  estado=1";
        
        ArrayList<Alumno> alumnos = new ArrayList<>();
        
        
        try {
            PreparedStatement ps =con.prepareStatement(sql);
           
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                
                Alumno alumno= new Alumno();
                alumno.setId_alunmo(rs.getInt("id_alumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFachaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(true);
                
                alumnos.add(alumno);
            }
            ps.close();
        
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ ex.getMessage());
        }
        return alumnos;
    }    
}    