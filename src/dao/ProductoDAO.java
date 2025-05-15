package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

/**
 *
 * @author Cassandra
 */
public class ProductoDAO
{

    public static boolean insertarProducto(Producto producto)
    {
        String sql = "INSERT INTO Producto (clave, nombre, fecha_vencimiento, via_admin, reg_sanitario, efectos_sec, advertencia, cant_almacen, precio, descuento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, producto.getClave());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDate(3, (Date) producto.getFecha_vencimiento());
            pstmt.setString(4, producto.getVia_admin());
            pstmt.setString(5, producto.getReg_sanitario());
            pstmt.setString(6, producto.getEfectos_sec());
            pstmt.setString(7, producto.getAdvertencia());
            pstmt.setInt(8, producto.getCant_almacen());
            pstmt.setDouble(9, producto.getPrecio());
            pstmt.setInt(10, producto.getDescuento());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            System.err.println("Error al agregar el producto: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarProducto(Producto producto)
    {
        String sql = "UPDATE Producto SET fecha_vencimiento = ?, cant_almacen = ?, precio = ?, descuento = ? WHERE clave = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setDate(1, (Date) producto.getFecha_vencimiento());
            pstmt.setInt(2, producto.getCant_almacen());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getDescuento());
            pstmt.setInt(5, producto.getClave());

            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (Exception e)
        {
            e.printStackTrace(); // útil para depurar
            System.out.println("Error al modificar los datos del producto");
            return false;
        }
    }

    public void eliminarProducto(int idProducto)
    {
        String sql = "DELETE FROM Producto WHERE clave = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setInt(1, idProducto);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Producto eliminado con éxito.");
            } else
            {
                System.out.println("No se encontró el producto con el ID especificado.");
            }
        } catch (Exception e)
        {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            e.printStackTrace(); // Esto ayuda en depuración
        }
    }

    public static List<Producto> listarProducto()
    {
        List<Producto> listaProducto = new ArrayList<>();
        String sql = "SELECT * FROM Producto";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery())
        {

            while (rs.next())
            {
                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getInt("clave"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_vencimiento"),
                        rs.getString("via_admin"),
                        rs.getString("reg_sanitario"),
                        rs.getString("efectos_sec"),
                        rs.getString("advertencia"),
                        rs.getInt("cant_almacen"),
                        rs.getDouble("precio"),
                        rs.getInt("descuento")
                );

                listaProducto.add(producto);
            }

        } catch (SQLException e)
        {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return listaProducto;
    }
    
    public List<Producto> obtenerProducto()
    {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, clave, nombre, fecha_vencimiento, via_admin, reg_sanitario, efectos_sec, advertencia, cant_almacen FROM Producto";

        try (Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery())
        {
            while (rs.next())
            {
                Producto producto = new Producto();

                producto.setId_producto(rs.getInt("id_producto"));
                producto.setClave(rs.getInt("clave"));
                producto.setNombre(rs.getString("nombre"));
                producto.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                producto.setVia_admin(rs.getString("via_admin"));
                producto.setReg_sanitario(rs.getString("reg_sanitario"));
                producto.setEfectos_sec(rs.getString("efectos_sec"));
                producto.setAdvertencia(rs.getString("advertencia"));
                producto.setCant_almacen(rs.getInt("cant_almacen"));
                productos.add(producto);
            }
        } catch (SQLException e)
        {
            System.err.println("Error al obtener a los productos: " + e.getMessage());
        }
        return productos;
    }

    public int obtenerIdPorClave(String clave)
    {
        int idProducto = -1;
        String query = "SELECT id_producto FROM Producto WHERE clave = ?";

        try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setString(1, clave);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                idProducto = rs.getInt("id_producto");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return idProducto;
    }

    public static Producto obtenerIdPorClave(int clave)
    {
        String sql = "SELECT * FROM Producto WHERE clave = ?";
        try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, clave);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                Producto p = new Producto();
                
                p.setId_producto(rs.getInt("id_producto"));
                p.setClave(rs.getInt("clave"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescuento(rs.getInt("descuento"));
                
                return p;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
