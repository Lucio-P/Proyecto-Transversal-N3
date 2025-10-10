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
        
        this.con=miConexion.getmiConexion();
    }
    
    public void guardarInscripcion(Inscripcion insc){
        String sql="INSET INTO Inscripción (idAlumno,idMateria,nota) VALUES (?,?,?)";
        
        try {
            PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,insc.getAlumno().getIdAlumno());
            ps.setInt(2,insc.getMateria().getIdMateria());
            ps.setDouble(3, insc.getNota());
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            
            if(rs.next()){
                insc.setIdInscripción(rs.getInt(1));
                JOptionPane.showMessageDialog(null,"Inscripción Registrada");
            }
            
            ps.close();
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
        }
       
    }
    
    public void actualizarNota(int idAlumno, int idMateria,double nota){
        String sql="UPDATE Inscripción SET nota = ? WHERE idAlumno = ? and idMateria = ? ";
        
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
        String sql="DELETE FROM Inscripción WHERE idAlumno = ? and idMateria = ?";
        
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            
            int filas=ps.executeUpdate();
            if(filas>0){
                JOptionPane.showMessageDialog(null,"Error al acce");
            }
            
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
        }
    }
    
    public List<Inscripcion>ObtenerInscripciones(){
             ArrayList<Inscripción> cursadas= new ArrayList<>();
            String sql="SELECT * FROM Inscripción";
            
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                Inscripción insc= new Inscripción();
                insc.setIdInscripción(rs.getInt("idInscripción"));
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
             ArrayList<Inscripción> cursadas= new ArrayList<>();
            String sql="SELECT * FROM Inscripción WHERE idAlumno = ?";
            
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                Inscripción insc= new Inscripción();
                insc.setIdInscripción(rs.getInt("idInscripción"));
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
            
            String sql="SELECT Inscripción.idMateria, nombre, año FROM Inscripción"
                    + "materia WHERE Inscripción.idMateria = materia.idMateria" +
                    "AND Inscripción.idAlumno = ?;";
        try {
            
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("Anio"));
                materias.add(materia);
                
            }
            
            ps.close();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
        }
        
        return materias;
        
    }
    
    public List<Materia>ObtenerMateriaNOcursadas(int idAlumno){
        
         ArrayList<Materia> materias= new ArrayList<>();
         
         String sql="SELECT * FROM materia WHERE estado = 1 AND idMateria not in" +
                 "(SELECT idMateria FROM Inscripción WHERE idAlumno =?)";
         
         try {
            
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("Anio"));
                materias.add(materia);
                
            }
            
            ps.close();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Error al acceder a la tabla inscripcion ");
        }
         return materias;
    }
    
    public List<Alumno>ObtenerAlumnoXMateria(int idMateria){
        
        ArrayList<Alumno> alumnosMateria= new ArrayList<>();
            
            String sql= "SELECT a.idAlumno,dni,nombre,apellido,fechaNacimiento,estado" +
                    "FROM Inscripción i,alumno a WHERE i.idAlumno = a.idAlumno AND idMateria = ? AND a.estado = 1";
        
          try {
            
            PreparedStatement ps=con.prepareCall(sql);
            ps.setInt(1, idMateria);
            
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNac(rs.getDate("fechaNacimiento").toLocalDate());
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
