package modelo;

import java.util.Date;

/**
 *
 * @author Cassandra
 */
public class Producto
{

    private int id_producto;
    private int clave;
    private String nombre;
    private Date fecha_vencimiento;
    private String via_admin;
    private String reg_sanitario;
    private String efectos_sec;
    private String advertencia;
    private int cant_almacen;
    private double precio;
    private int descuento;

    public Producto()
    {
    }

    /**
     * Constructor para la alta de productos.
     *
     * @param id_producto
     * @param clave
     * @param nombre
     * @param fecha_vencimiento
     * @param via_admin
     * @param reg_sanitario
     * @param efectos_sec
     * @param advertencia
     * @param cant_almacen
     * @param precio
     * @param descuento
     */
    public Producto(int id_producto, int clave, String nombre, Date fecha_vencimiento, String via_admin, String reg_sanitario, String efectos_sec, String advertencia, int cant_almacen, double precio, int descuento)
    {
        this.id_producto = id_producto;
        this.clave = clave;
        this.nombre = nombre;
        this.fecha_vencimiento = fecha_vencimiento;
        this.via_admin = via_admin;
        this.reg_sanitario = reg_sanitario;
        this.efectos_sec = efectos_sec;
        this.advertencia = advertencia;
        this.cant_almacen = cant_almacen;
        this.precio = precio;
        this.descuento = descuento;
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
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the via_admin
     */
    public String getVia_admin()
    {
        return via_admin;
    }

    /**
     * @param via_admin the via_admin to set
     */
    public void setVia_admin(String via_admin)
    {
        this.via_admin = via_admin;
    }

    /**
     * @return the reg_sanitario
     */
    public String getReg_sanitario()
    {
        return reg_sanitario;
    }

    /**
     * @param reg_sanitario the reg_sanitario to set
     */
    public void setReg_sanitario(String reg_sanitario)
    {
        this.reg_sanitario = reg_sanitario;
    }

    /**
     * @return the efectos_sec
     */
    public String getEfectos_sec()
    {
        return efectos_sec;
    }

    /**
     * @param efectos_sec the efectos_sec to set
     */
    public void setEfectos_sec(String efectos_sec)
    {
        this.efectos_sec = efectos_sec;
    }

    /**
     * @return the advertencia
     */
    public String getAdvertencia()
    {
        return advertencia;
    }

    /**
     * @param advertencia the advertencia to set
     */
    public void setAdvertencia(String advertencia)
    {
        this.advertencia = advertencia;
    }

    /**
     * @return the cant_almacen
     */
    public int getCant_almacen()
    {
        return cant_almacen;
    }

    /**
     * @param cant_almacen the cant_almacen to set
     */
    public void setCant_almacen(int cant_almacen)
    {
        this.cant_almacen = cant_almacen;
    }

    /**
     * @return the fecha_vencimiento
     */
    public Date getFecha_vencimiento()
    {
        return fecha_vencimiento;
    }

    /**
     * @param fecha_vencimiento the fecha_vencimiento to set
     */
    public void setFecha_vencimiento(Date fecha_vencimiento)
    {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    /**
     * @return the clave
     */
    public int getClave()
    {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(int clave)
    {
        this.clave = clave;
    }

    /**
     * @return the precio
     */
    public double getPrecio()
    {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio)
    {
        this.precio = precio;
    }

    /**
     * @return the descuento
     */
    public int getDescuento()
    {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(int descuento)
    {
        this.descuento = descuento;
    }

}
