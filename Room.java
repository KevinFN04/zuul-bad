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

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
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
        Room salida = null;
        if(direccion.equals("north"))
            salida = exits.get("north");
        if(direccion.equals("east"))
            salida = exits.get("east");
        if(direccion.equals("south"))
            salida = exits.get("south");
        if(direccion.equals("west"))
            salida = exits.get("west");
        if(direccion.equals("southEast"))
            salida = exits.get("southEast");
        if(direccion.equals("northWest"))
            salida = exits.get("northWest");
        return salida;
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString(){
        String salida = "Exits: ";
        if(exits.get("north") != null) {
            salida += "north ";
        }
        if(exits.get("east") != null) {
            salida += "east ";
        }
        if(exits.get("south") != null) {
            salida += "south ";
        }
        if(exits.get("west") != null) {
            salida += "west ";
        }
        if(exits.get("southEast") != null) {
            salida += "southEast ";
        }
        if(exits.get("northWest") != null) {
            salida += "northWest ";
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
}