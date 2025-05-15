package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Cliente;

/**
 *
 * @author Cassandra
 */
public class ClienteDAO
{

    public static boolean insertarCliente(Cliente cliente)
    {
        String sql = "INSERT INTO Cliente (nombre, direccion, telefono, edad) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getDireccion());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setInt(4, cliente.getEdad());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e)
        {
            System.err.println("Error al agregar al cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCliente(Cliente cliente)
    {
        String sql = "UPDATE Cliente SET direccion = ?, telefono = ?, edad = ? WHERE nombre = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, cliente.getDireccion());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setInt(3, cliente.getEdad());
            pstmt.setString(4, cliente.getNombre());

            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (Exception e)
        {
            System.out.println("Error al modificar los datos del cliente: " + e.getMessage());
            return false;
        }
    }

    public void eliminarCliente(int idCliente)
    {
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setInt(1, idCliente);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Cliente eliminado con éxito.");
            } else
            {
                System.out.println("No se encontró el cliente con el ID especificado.");
            }
        } catch (Exception e)
        {
            System.err.println("Error al eliminar al cliente: " + e.getMessage());
        }
    }

    public static List<Cliente> listarCliente()
    {
        List<Cliente> listaCliente = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery())
        {

            while (rs.next())
            {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getInt("edad")
                );

                listaCliente.add(cliente);
            }

        } catch (SQLException e)
        {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return listaCliente;
    }

    public List<Cliente> obtenerClientes()
    {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, direccion, telefono, edad FROM Cliente";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery())
        {
            while (rs.next())
            {
                Cliente cliente = new Cliente();

                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEdad(rs.getInt("edad"));
                clientes.add(cliente);
            }
        } catch (SQLException e)
        {
            System.err.println("Error al obtener a los clientes: " + e.getMessage());
        }
        return clientes;
    }

    public int obtenerIdPorNombre(String nombre)
    {
        int idCliente = -1;
        String query = "SELECT id_cliente FROM Cliente WHERE nombre = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                idCliente = rs.getInt("id_cliente");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return idCliente;
    }
    
    public Cliente obtenerNombrePorId(int id_Cliente)
    {        
        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?";
        
        try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id_Cliente);            
            ResultSet rs = stmt.executeQuery();            
            if (rs.next())
            {
                Cliente cliente = new Cliente();
                
                cliente.setNombre(rs.getString("nombre"));
                
                return cliente;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
