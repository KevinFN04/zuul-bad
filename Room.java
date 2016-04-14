import java.util.*;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;

    private HashMap<String, Room> exits;

    //Clase Item
    private String descripcionObj;
    private float peso;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param descriptionObject The room's object description.
     * @param weightObject The room's object description.
     */
    public Room(String description, String descriptionObject, float weightObject) 
    {
        this.description = description;
        descripcionObj = descriptionObject;
        peso = weightObject;
        if (descriptionObject != null){
            descripcionObj += " y pesa: " + peso + " Kg\n";
        }
        else{
            descripcionObj = "No hay objeto util en esta sala";
        }
        exits = new HashMap<>();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion){
        return exits.get(direccion);
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString(){
        String salida = "Exits: ";
        for(String claveActual : exits.keySet()){
            salida += claveActual + " ";
        }
        return salida;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription(){
        return getDescription() + "\n" + "El objeto de esta sala es: "+ descripcionObj + getExitString();
    }
}