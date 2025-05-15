package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Vendedor;

/**
 *
 * @author Cassandra
 */
public class VendedorDAO
{
    public static boolean insertarVendedor(Vendedor vendedor) {
        String sql = "INSERT INTO Vendedor (nombre, domicilio, usuario, contrasenia, telefono, puesto) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, vendedor.getNombre());
            pstmt.setString(2, vendedor.getDomicilio());
            pstmt.setString(3, vendedor.getUsuario());
            pstmt.setString(4, vendedor.getContrasenia());
            pstmt.setString(5, vendedor.getTelefono());
            pstmt.setInt(6, vendedor.getPuesto());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar al vendedor: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarVendedor(Vendedor vendedor) {
        String sql = "UPDATE Vendedor SET domicilio = ?, telefono = ?, puesto = ? WHERE nombre = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, vendedor.getDomicilio());
            pstmt.setString(2, vendedor.getTelefono());
            pstmt.setInt(3, vendedor.getPuesto());
            pstmt.setString(4, vendedor.getNombre());

            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (Exception e) {
            System.out.println("Error al modificar los datos del vendedor: " + e.getMessage());
            return false;
        }
    }

    public void eliminarVendedor(int idVendedor) {
        String sql = "DELETE FROM Vendedor WHERE id_vendedor = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idVendedor);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Vendedor eliminado con éxito.");
            } else {
                System.out.println("No se encontró el vendedor con el ID especificado.");
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar al vendedor: " + e.getMessage());
        }
    }

    public static List<Vendedor> listarVendedor() {
        List<Vendedor> listaVendedor = new ArrayList<>();
        String sql = "SELECT * FROM Vendedor";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Vendedor vendedor = new Vendedor(
                        rs.getInt("id_vendedor"),
                        rs.getString("nombre"),
                        rs.getString("domicilio"),
                        rs.getString("usuario"),
                        rs.getString("contrasenia"),
                        rs.getString("telefono"),
                        rs.getInt("puesto")
                );
                listaVendedor.add(vendedor);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar vendedores: " + e.getMessage());
        }
        return listaVendedor;
    }

    public List<Vendedor> obtenerVendedores() {
        List<Vendedor> vendedores = new ArrayList<>();
        String sql = "SELECT id_vendedor, nombre, domicilio, usuario, telefono, puesto FROM Vendedor";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Vendedor vendedor = new Vendedor();
                vendedor.setId_vendedor(rs.getInt("id_vendedor"));
                vendedor.setNombre(rs.getString("nombre"));
                vendedor.setDomicilio(rs.getString("domicilio"));
                vendedor.setUsuario(rs.getString("usuario"));
                vendedor.setTelefono(rs.getString("telefono"));
                vendedor.setPuesto(rs.getInt("puesto"));
                vendedores.add(vendedor);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vendedores: " + e.getMessage());
        }
        return vendedores;
    }

    public int obtenerIdPorNombre(String nombre) {
        int idVendedor = -1;
        String query = "SELECT id_vendedor FROM Vendedor WHERE nombre = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idVendedor = rs.getInt("id_vendedor");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idVendedor;
    }
    
    public static Vendedor inicioSesion(String usuario, String contrasenia)
    {
        String sql = "SELECT * FROM Vendedor WHERE usuario = ? AND contrasenia = ?";
        
        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasenia);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                Vendedor vendedor = new Vendedor();
                vendedor.setId_vendedor(rs.getInt("id_vendedor"));
                vendedor.setNombre(rs.getString("nombre"));
                vendedor.setUsuario(rs.getString("usuario"));
                vendedor.setPuesto(rs.getInt("puesto"));
                
                return vendedor;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
