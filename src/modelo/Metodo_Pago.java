package modelo;

/**
 *
 * @author Cassandra
 */
public class Metodo_Pago
{
    private int id_pago;
    private String tipo_pago;

    public Metodo_Pago()
    {
    }

    public Metodo_Pago(int id_pago, String tipo_pago)
    {
        this.id_pago = id_pago;
        this.tipo_pago = tipo_pago;
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
     * @return the tipo_pago
     */
    public String getTipo_pago()
    {
        return tipo_pago;
    }

    /**
     * @param tipo_pago the tipo_pago to set
     */
    public void setTipo_pago(String tipo_pago)
    {
        this.tipo_pago = tipo_pago;
    }
    
    
}
