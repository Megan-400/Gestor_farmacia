package modelo;

import java.util.Date;

/**
 *
 * @author Cassandra
 */
public class Ventas
{
    private int id_venta;
    private int id_vendedor;
    private int id_cliente;
    private int id_pago;
    private Date fecha_compra;
    private double total;

    public Ventas()
    {
    }

    public Ventas(int id_venta, int id_vendedor, int id_cliente, int id_pago, Date fecha_compra, double total)
    {
        this.id_venta = id_venta;
        this.id_vendedor = id_vendedor;
        this.id_cliente = id_cliente;
        this.id_pago = id_pago;
        this.fecha_compra = fecha_compra;
        this.total = total;
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
     * @return the id_vendedor
     */
    public int getId_vendedor()
    {
        return id_vendedor;
    }

    /**
     * @param id_vendedor the id_vendedor to set
     */
    public void setId_vendedor(int id_vendedor)
    {
        this.id_vendedor = id_vendedor;
    }

    /**
     * @return the id_cliente
     */
    public int getId_cliente()
    {
        return id_cliente;
    }

    /**
     * @param id_cliente the id_cliente to set
     */
    public void setId_cliente(int id_cliente)
    {
        this.id_cliente = id_cliente;
    }

    /**
     * @return the id_pago
     */
    public int getId_pago()
    {
        return id_pago;
    }

    /**
     * @param id_pago the id_pago to set
     */
    public void setId_pago(int id_pago)
    {
        this.id_pago = id_pago;
    }

    /**
     * @return the fecha_compra
     */
    public Date getFecha_compra()
    {
        return fecha_compra;
    }

    /**
     * @param fecha_compra the fecha_compra to set
     */
    public void setFecha_compra(Date fecha_compra)
    {
        this.fecha_compra = fecha_compra;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }
    
    
}
