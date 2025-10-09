/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidades.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Bustos Guada
 */
public class MateriaData {
    private Connection con=null;
    
    public MateriaData() throws SQLException{
        con = miConexion.getmiConexion();
    }
    
    public void guardarMateria (Materia materia) throws SQLException{
            String sql = "INSERT TO materia (nombre,anio,estado)"+ "VALUES(?,?,?)";
       try{
           PreparedStatement ps= con.prepareStatement(sql);
           ps.setString(1, materia.getNombre());
           ps.setInt(2,materia.getAnio());
           ps.setInt(3, materia.getEstado());
           ps.executeUpdate();
           ps.close();
           System.out.println("Materia guardada correctamente");
       } catch(SQLException ex){
           System.out.println("Error al guardar materia:" + ex.getMessage());
       }
    }
    
    public Materia buscarMateria(int id) throws SQLException{
        Materia materia = null;
        String sql="SELECT * FROM materia WHERE idMateria =?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
            Materia = new Materia();
            materia.setIdMateria(rs.getInt("idMateria"));
            materia.setNombre(rs.getString("nombre"));
            materia.setAnio(rs.getAnio("anio"));
            materia.setEstado(rs.getEstado("estado"));
        }
            ps.close();
    } catch (SQLException ex) {
            System.out.println("Error al buscar materia:" + ex.getMessage());
    }
    return materia;
  }
    
    public void modificarMateria (Materia materia) throws SQLException{
        String sql= "UPDATE materia SET nombre = ?, anio =?, estado = ? WHERE idMateria =?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,materia.getNombre());
            ps.setInt(2, materia.getAnio());
            ps.setInt(3,materia.getEstado());
            ps.setInt(4, materia.getIdMateria());
            ps.executeUpdate();
            ps.close();
            System.out.println("Materia modificada correctamente.");
        } catch (SQLException ex){
            System.out.println("Error al modificar materia:" + ex.getMessage());
        }
    }
    
    public void eliminarMateria(int id){
        String sql= "UPDATE materia SET estado = 0 WHERE idMateria =?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            System.out.println("Materia eliminada correctamente.");
        } catch (SQLException ex){
            System.out.println("Error al eliminar materia:" + ex.getMessage());
        }
    }
    
    public List<Materia>listarMateria() throws SQLException{
        List<Materia>listaMateria =new ArrayList<>();
        String sql ="SELECT * FROM materia WHERE estado = 1";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            
            while(rs.next()){
               Materia materia = new Materia();
               materia.setIdMateria(rs.getInt("idMateria"));
               materia.setNombre(rs.getString("nombre"));
               materia.setAnio(rs.getInt("anio"));
               materia.setEstado(rs.getEstado("estado"));
               listaMateria.add(materia);
            }
            ps.close();
        }catch (SQLException ex){
            System.out.println("Error al listar materias:" + ex.getMessage());
        }
        return listaMateria;
    }
}
