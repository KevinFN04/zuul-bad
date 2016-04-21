import java.util.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    // Localizacion, salas pasadas, objetos, peso
    //Localización actual del jugador
    private Room currentRoom;
    //Salas por las que ha pasado dicho jugador.
    private Stack<Room> salas;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room firstRoom)
    {
        currentRoom = firstRoom;
        salas = new Stack<Room>();
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        if (currentRoom != null){
            salas.push(currentRoom);
        }
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            System.out.println();
        }
    }
    
     /**
     * Metodo para ir hacia atras las veces que quieras hasta la primera sala.
     */
    public void goBack(){
        Room previousRoom = null;
        if (!salas.empty()){
           previousRoom = salas.pop();           
        }
        if (previousRoom == null) {
            System.out.println("No hay vuelta atras!");
        }
        else {
            currentRoom = previousRoom;
            System.out.println(currentRoom.getLongDescription());
            System.out.println();
        }
    }

}
