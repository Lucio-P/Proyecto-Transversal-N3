/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;


import entidades.Alumno;
import entidades.Inscripcion;
import entidades.Materia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.Connection;

/**
 *
 * @author Bustos Guada
 */
public class InscripcionData {
    
    private Connection con= null;
    private MateriaData md=new MateriaData();
    private AlumnoData ad=new AlumnoData();
    
    public InscripcionData() {
        
        con=miConexion.getmiConexion();
    }
    
    public void guardarInscripcion(int idAlumno, int idMateria){
        String sql="INSERT INTO inscripto (idAlumno,idMateria,nota) VALUES (?,?,?)";
        
        try {
            PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,idAlumno);
            ps.setInt(2,idMateria);
            ps.setDouble(3,0);
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                
                JOptionPane.showMessageDialog(null, "Inscripcion guardada Correctamente");
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error al Inscribir al Alumno en la materia ");
        }
       
    }
    
    public void actualizarNota(int idAlumno, int idMateria,double nota){
        String sql="UPDATE inscripto SET nota = ? WHERE idAlumno = ? AND idMateria = ? ";
        
        try{
             PreparedStatement ps=con.prepareStatement(sql);
             ps.setDouble(1, nota);
             ps.setInt(2, idAlumno);
             ps.setInt(3, idMateria);
             
             int filas=ps.executeUpdate();
             
             if(filas>0){
                 JOptionPane.showMessageDialog(null,"Nota Actualizada");
             }
             ps.close();
             
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al actualizar nota. ");
        }
       
    }
    
    public void borrarInscripcionMateriaAlumno(int idAlumno, int idMateria){
        String sql="DELETE FROM inscripto WHERE idAlumno = ? AND idMateria = ?";
        
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            
            int exitos = ps.executeUpdate();
            
            if( exitos == 1){
                JOptionPane.showMessageDialog(null,"Inscripcion Anulada Correctamente");
            }
            
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al Anular la Inscripcion de la Materia");
        }
    }
    
    public List<Inscripcion>ObtenerInscripciones(){
             ArrayList<Inscripcion> cursadas= new ArrayList<>();
            String sql="SELECT * FROM inscripto";
            
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                Inscripcion insc= new Inscripcion();
                insc.setIdInscripto(rs.getInt("idInscripcion"));
                Alumno alu=ad.buscarAlumno(rs.getInt("idAlumno"));
                Materia mat=md.buscarMateria(rs.getInt("idMateria"));
                insc.setAlumno(alu);
                insc.setMateria(mat);
                insc.setNota(rs.getDouble("nota"));
                cursadas.add(insc);
            }
            
            ps.close();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
        }
        return cursadas;
    }
    
    public List<Inscripcion>ObtenerInscripcionesPorAlumno(int idAlumno){
            ArrayList<Inscripcion> cursadas= new ArrayList<>();
            String sql="SELECT * FROM inscripto i JOIN materia m ON i.idMateria = m.idMateria WHERE idAlumno = ?";
            
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            
            ResultSet rs =ps.executeQuery();
            
            while(rs.next()){
                
                Materia mat = new Materia (rs.getInt("idMateria"), rs.getString("nombre"), rs.getInt("año"), rs.getInt("estado"));
                
                Inscripcion insc= new Inscripcion(rs.getInt("idInscripto"), rs.getDouble("nota"), ad.buscarAlumno(idAlumno), mat);
                
                cursadas.add(insc);
            }
            
            ps.close();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
        }
        return cursadas;
    }
    
    public List<Materia>ObtenerMateriaCursadas( int idAlumno) {
        
        ArrayList<Materia> materias = new ArrayList<>();
            
            String sql="SELECT i.idMateria, m.nombre, m.año " +
                    "FROM inscripto i " +
                    "JOIN materia m ON i.idMateria = m.idMateria " +
                    "WHERE i.idAlumno = ?";
        try {
            
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("año"));
                materias.add(materia);
                
            }
            
            ps.close();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Error al acceder a la tabla Materia Inscripta ");
        }
        
        return materias;
        
    }
    
    public List<Materia>ObtenerMateriaNOcursadas(int idAlumno){
        
         ArrayList<Materia> materias= new ArrayList<>();
         
         String sql="SELECT * FROM materia WHERE estado = 1 AND idMateria NOT IN" +
                 "(SELECT idMateria FROM inscripto WHERE idAlumno = ?)";
         
         try {
            
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("año"));
                materias.add(materia);
                
            }
            
            ps.close();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Error al acceder a la tabla Materia No Inscripta ");
        }
         return materias;
    }
    
    public ArrayList<Inscripcion>ObtenerAlumnoXMateria(int idMateria){
        
            
          ArrayList <Inscripcion> inscr = new ArrayList <>();
            String sql= "SELECT a.id_alumno AS idAlumno, i.nota, a.nombre, a.apellido "
                    + "FROM inscripto i "
                    + "JOIN alumno a ON i.idAlumno = a.id_alumno "
                    + "WHERE i.idMateria = ?"; 
            try {
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMateria);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                Alumno alumno = new Alumno();
                alumno.setId_alunmo(rs.getInt("idAlumno"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                
                Inscripcion inscrip = new Inscripcion ();
                
                inscrip.setAlumno(alumno);
                inscrip.setNota(rs.getInt("nota"));
                
                inscr.add(inscrip);
                
            }
            rs.close();
            ps.close();
            
        } catch (SQLException ex) {
            
             JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
        }
          return inscr;
     }
    
    public int obtenerNota (int idAlumno, int idMateria){
        
        String sql = "SELECT nota FROM inscripto WHERE idAlumno = ? AND idMateria = ?";
        int nota = 0;
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                
                nota = rs.getInt("nota");
            }
            
            ps.close();
        }catch (SQLException ex){
            
            JOptionPane.showMessageDialog(null, "Error al obtener la nota.");
        }
        
        return nota;
    }
    
    
}
