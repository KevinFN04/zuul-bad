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

    //private ArrayList<Item> items;

    private HashMap<String, Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param descriptionObject The room's object description.
     * @param weightObject The room's object description.
     */
    public Room(String description) 
    {
        this.description = description;
        items = new HashMap<>();
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
        String longDescription = getDescription() + "\n" + "Objetos en la sala:";
        if (items.size() > 0){
            for(Item claveActual : items.values()){
                longDescription += " " + claveActual.getLongDescription();
            }
        }
        else{
            longDescription += " No hay ningun objeto de valor en la sala.";
        }
        longDescription += "\n" + getExitString();
        return longDescription;
    }

    public void addItem(String descripcionObj, float pesoObj, boolean puedeCogerse){
        items.put(descripcionObj, new Item(descripcionObj, pesoObj, puedeCogerse));
    }

    public Item getItem(String nombreObj){
        return items.get(nombreObj);
    }
    
    public void removeItem(String nombreObj){
        items.remove(nombreObj);
    }
        
    public int getNumberItem(){
        return items.size();
    }
}