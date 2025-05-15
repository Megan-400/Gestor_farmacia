package modelo;

/**
 *
 * @author Cassandra
 */
public class Vendedor
{
    private int id_vendedor;
    private String nombre;
    private String domicilio;
    private String usuario;
    private String contrasenia;
    private String telefono;
    private int puesto;

    public Vendedor()
    {
    }

    /**
     * Constructor funcional para la alta de empleados.
     * @param id_vendedor
     * @param nombre
     * @param domicilio
     * @param usuario
     * @param contrasenia
     * @param telefono
     * @param puesto 
     */
    public Vendedor(int id_vendedor, String nombre, String domicilio, String usuario, String contrasenia, String telefono, int puesto)
    {
        this.id_vendedor = id_vendedor;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.puesto = puesto;
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
     * @return the domicilio
     */
    public String getDomicilio()
    {
        return domicilio;
    }

    /**
     * @param domicilio the domicilio to set
     */
    public void setDomicilio(String domicilio)
    {
        this.domicilio = domicilio;
    }

    /**
     * @return the usuario
     */
    public String getUsuario()
    {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia()
    {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
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
     * @return the puesto
     */
    public int getPuesto()
    {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(int puesto)
    {
        this.puesto = puesto;
    }
    
    
}
