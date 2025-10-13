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
        String sql="UPDATE inscripto SET nota = ? WHERE idAlumno = ? and idMateria = ? ";
        
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
            JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
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
            String sql="SELECT * FROM inscripto WHERE idAlumno = ?";
            
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            
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
    
    public List<Alumno>ObtenerAlumnoXMateria(int idMateria){
        
        ArrayList<Alumno> alumnosMateria= new ArrayList<>();
            
            String sql= "SELECT a.idAlumno,dni,nombre,apellido,fechaNacimiento,estado" +
                    "FROM inscripto i,alumno a WHERE i.idAlumno = a.idAlumno AND idMateria = ? AND a.estado = 1";
        
          try {
            
            PreparedStatement ps=con.prepareCall(sql);
            ps.setInt(1, idMateria);
            
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                
                Alumno alumno = new Alumno();
                alumno.setId_alunmo(rs.getInt("idAlumno"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFachaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                alumnosMateria.add(alumno);
                
            }
            ps.close();
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
        }
          return alumnosMateria;
     }
    
    
}
