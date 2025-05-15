package modelo;

/**
 *
 * @author Cassandra
 */
public class Detalle_Venta
{
    private int id_detalle;
    private int id_venta;
    private int id_producto;
    private int cantidad;
    private double precio_unitario;

    public Detalle_Venta()
    {
    }

    public Detalle_Venta(int id_detalle, int id_venta, int id_producto, int cantidad, double precio_unitario)
    {
        this.id_detalle = id_detalle;
        this.id_venta = id_venta;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    /**
     * @return the id_detalle
     */
    public int getId_detalle()
    {
        return id_detalle;
    }

    /**
     * @param id_detalle the id_detalle to set
     */
    public void setId_detalle(int id_detalle)
    {
        this.id_detalle = id_detalle;
    }

    /**
     * @return the id_venta
     */
    public int getId_venta()
    {
        return id_venta;
    }

    /**
     * @param id_venta the id_venta to set
     */
    public void setId_venta(int id_venta)
    {
        this.id_venta = id_venta;
    }

    /**
     * @return the id_producto
     */
    public int getId_producto()
    {
        return id_producto;
    }

    /**
     * @param id_producto the id_producto to set
     */
    public void setId_producto(int id_producto)
    {
        this.id_producto = id_producto;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad()
    {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }

    /**
     * @return the precio_unitario
     */
    public double getPrecio_unitario()
    {
        return precio_unitario;
    }

    /**
     * @param precio_unitario the precio_unitario to set
     */
    public void setPrecio_unitario(double precio_unitario)
    {
        this.precio_unitario = precio_unitario;
    }
    
    
}
