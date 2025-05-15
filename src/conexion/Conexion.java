package conexion;

import java.sql.*;

/**
 *
 * @author Cassandra
 */
public class Conexion
{

    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String usuario = "root";
    private static final String contraseña = "M22L10J08*e";
    private static final String nombre_BD = "Farmacia";

    public static Connection conectar()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url, usuario, contraseña);

            Statement stmt = (Statement) connection.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + nombre_BD);
            System.out.println("Base de datos verificada o creada");

            String urlBD = url + nombre_BD;
            Connection connBD = DriverManager.getConnection(urlBD, usuario, contraseña);
            Statement stmtBD = connBD.createStatement();

            crearTablas(stmtBD);
            insertarAdministrador(stmtBD);

            System.out.println("Conexión exitosa a la base de datos.");
            return connBD;
        } catch (SQLException e)
        {
            System.out.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        }
        return connection;
    }

    private static void crearTablas(Statement stmt) throws SQLException
    {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Cliente (\n"
                + "    id_cliente INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    nombre VARCHAR(100),\n"
                + "    direccion VARCHAR(255),\n"
                + "    telefono VARCHAR(15),\n"
                + "    edad INT\n"
                + ");"
        );

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Producto (\n"
                + "    id_producto INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    clave INT,\n"
                + "    nombre VARCHAR(150),\n"
                + "    fecha_vencimiento DATE,\n"
                + "    via_admin VARCHAR(50),\n"
                + "    reg_sanitario VARCHAR(50),\n"
                + "    efectos_sec TEXT,\n"
                + "    advertencia TEXT,\n"
                + "    cant_almacen INT,\n"
                + "    precio DOUBLE,\n"
                + "    descuento INT\n"
                + ");"
        );

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Vendedor (\n"
                + "    id_vendedor INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    nombre VARCHAR(100),\n"
                + "    domicilio VARCHAR(255),\n"
                + "    usuario VARCHAR(50),\n"
                + "    contrasenia VARCHAR(255),\n"
                + "    telefono VARCHAR(50),\n"
                + "    puesto INT\n"
                + ");"
        );

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Metodo_Pago (\n"
                + "    id_pago INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    tipo_pago VARCHAR(10)\n"
                + ");"
        );

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Ventas (\n"
                + "    id_venta INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    id_vendedor INT,\n"
                + "    id_cliente INT,\n"
                + "    id_pago INT,\n"
                + "    fecha_compra DATE,\n"
                + "    total DOUBLE,\n"
                + "    FOREIGN KEY (id_vendedor) REFERENCES Vendedor(id_vendedor),\n"
                + "    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),\n"
                + "    FOREIGN KEY (id_pago) REFERENCES Metodo_Pago(id_pago)\n"
                + ");"
        );

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Detalle_Venta (\n"
                + "    id_detalle INT AUTO_INCREMENT PRIMARY KEY,\n"
                + "    id_venta INT,\n"
                + "    id_producto INT,\n"
                + "    cantidad INT,\n"
                + "    precio_unitario DECIMAL(10,2),\n"
                + "    FOREIGN KEY (id_venta) REFERENCES Ventas(id_venta),\n"
                + "    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)\n"
                + ");"
        );
    }

    private static void insertarAdministrador(Statement stmt) throws SQLException
    {
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Vendedor WHERE puesto = 1");
        rs.next();

        if (rs.getInt(1) == 0)
        {
            stmt.executeUpdate("INSERT INTO Vendedor (nombre, domicilio, usuario, contrasenia, telefono, puesto) "
                    + "VALUES ('Sin Nombre', 'Sin Direccion', 'administrador', 'clave1234', '0000000000', 1)"
            );
            System.out.println("Usuario administrador creado.");
        } else
        {
            System.out.println("Usuario administrador ya existente");
        }
    }

    public static Connection conecta()
    {
        Connection connection = null;
        try
        {
            String urlBD = url + nombre_BD;
            connection = DriverManager.getConnection(urlBD, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e)
        {
            System.out.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        }
        return connection;
    }
        
}
