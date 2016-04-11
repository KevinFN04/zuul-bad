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
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room southEast, Room northWest) 
    {
        if(north != null)
            exits.put("north", north);
        if(east != null)
            exits.put("east", east);
        if(south != null)
            exits.put("south", south);
        if(west != null)
            exits.put("west", west);
        if(southEast != null)
            exits.put("southEast", southEast);
        if(northWest != null)
            exits.put("northWest", northWest);
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
}
