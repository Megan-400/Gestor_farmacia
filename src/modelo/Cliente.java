package modelo;

/**
 *
 * @author Cassandra
 */
public class Cliente
{
    private int id_cliente;
    private String nombre;
    private String direccion;
    private String telefono;
    private int edad;

    public Cliente()
    {
    }

    public Cliente(int id_cliente, String nombre, String direccion, String telefono, int edad)
    {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.edad = edad;
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
     * @return the direccion
     */
    public String getDireccion()
    {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono()
    {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    /**
     * @return the edad
     */
    public int getEdad()
    {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad)
    {
        this.edad = edad;
    }
        
}
