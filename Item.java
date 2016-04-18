
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    private String descripcionObj;
    private float peso;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String descripcion, float peso)
    {
        this.peso = peso;
        descripcionObj = descripcion;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @return     the weight of the item 
     */
    public float getPeso()
    {
        return peso;
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @return     the description of the item 
     */
    public String getDescription()
    {
        return descripcionObj;
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @return     the description and the weight of the item in a String
     */
    public String getLongDescription()
    {
        return descripcionObj + " (Peso:" + peso + " Kg)";
    }
}
