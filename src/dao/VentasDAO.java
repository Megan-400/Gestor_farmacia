package dao;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import modelo.Ventas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import modelo.Detalle_Venta;
import modelo.Metodo_Pago;

/**
 *
 * @author Cassandra
 */
public class VentasDAO
{

    public static boolean registrarVenta(Ventas ventas, List<Detalle_Venta> detalle_Venta, Metodo_Pago metodo_Pago) throws SQLException
    {
        String sqlPago = "INSERT INTO Metodo_Pago (tipo_pago) VALUES (?)";
        String sqlV = "INSERT INTO Ventas (id_vendedor, id_cliente, id_pago, fecha_compra, total) VALUES (?, ?, ?, ?, ?)";
        String sqldV = "INSERT INTO Detalle_Venta (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        Connection conn = null;

        try
        {
            conn = conexion.Conexion.conectar();
            conn.setAutoCommit(false); // ✅ Desactivamos autocommit para manejar la transacción manualmente

            int idPago;

            try (PreparedStatement psPago = conn.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS))
            {
                psPago.setString(1, metodo_Pago.getTipo_pago());
                psPago.executeUpdate();

                ResultSet rsPago = psPago.getGeneratedKeys();

                if (rsPago.next())
                {
                    idPago = rsPago.getInt(1);
                } else
                {
                    conn.rollback();
                    return false;
                }
            }

            try (PreparedStatement psVenta = conn.prepareStatement(sqlV, Statement.RETURN_GENERATED_KEYS))
            {
                psVenta.setInt(1, ventas.getId_vendedor());
                psVenta.setInt(2, ventas.getId_cliente());
                psVenta.setInt(3, idPago);
                psVenta.setDate(4, new java.sql.Date(ventas.getFecha_compra().getTime()));
                psVenta.setDouble(5, ventas.getTotal());

                int rows = psVenta.executeUpdate();

                if (rows == 0)
                {
                    conn.rollback();
                    return false;
                }

                ResultSet rsVen = psVenta.getGeneratedKeys();
                int idVenta;

                if (rsVen.next())
                {
                    idVenta = rsVen.getInt(1);
                } else
                {
                    conn.rollback();
                    return false;
                }

                try (PreparedStatement psDetalle = conn.prepareStatement(sqldV))
                {
                    for (Detalle_Venta det : detalle_Venta)
                    {
                        psDetalle.setInt(1, idVenta);
                        psDetalle.setInt(2, det.getId_producto());
                        psDetalle.setInt(3, det.getCantidad());
                        psDetalle.setDouble(4, det.getPrecio_unitario());
                        psDetalle.addBatch();
                    }

                    psDetalle.executeBatch();
                }

                conn.commit(); // ✅ Confirmamos la transacción completa
                return true;
            }

        } catch (SQLException e)
        {
            if (conn != null)
            {
                try
                {
                    conn.rollback(); // ✅ Revertimos si algo salió mal
                } catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally
        {
            if (conn != null)
            {
                try
                {
                    conn.setAutoCommit(true);
                    conn.close(); // ✅ Cerramos la conexión al final
                } catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    public Map<String, Double> ventasPorVendedor() throws SQLException
    {
        Map<String, Double> datos = new HashMap<>();
        String sql = "SELECT v.id_vendedor, SUM(v.total) as total FROM Ventas v GROUP BY v.id_vendedor";
        try (Connection conn = conexion.Conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                datos.put(rs.getString("id_vendedor"), rs.getDouble("total"));
            }
        }
        return datos;
    }

    public Map<String, Double> ventasPorMes(int año) throws SQLException
    {
        Map<String, Double> datos = new HashMap<>();
        String sql = "SELECT MONTH(fecha_compra) as mes, SUM(total) as total FROM Ventas WHERE YEAR(fecha_compra) = ? GROUP BY mes";
        try (Connection conn = conexion.Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, año);
            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                    datos.put(String.valueOf(rs.getInt("mes")), rs.getDouble("total"));
                }
            }
        }
        return datos;
    }

    public Map<Integer, Double> ventasAnuales() throws SQLException
    {
        Map<Integer, Double> datos = new HashMap<>();
        String sql = "SELECT YEAR(fecha_compra) as anio, SUM(total) as total FROM Ventas GROUP BY anio";
        try (Connection conn = conexion.Conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                datos.put(rs.getInt("anio"), rs.getDouble("total"));
            }
        }
        return datos;
    }

    public Map<String, Double> ventasPorCliente() throws SQLException
    {
        Map<String, Double> datos = new HashMap<>();
        String sql = "SELECT id_cliente, SUM(total) as total FROM Ventas GROUP BY id_cliente";
        try (Connection conn = conexion.Conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                datos.put(rs.getString("id_cliente"), rs.getDouble("total"));
            }
        }
        return datos;
    }

}
