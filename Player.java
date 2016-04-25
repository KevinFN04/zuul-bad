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
    //Peso Maximo que puede llevar el jugador.
    public static final float PESO_MAXIMO = 10F;
    //Peso que lleva el usuario actualmente
    private float pesoObj;
    //Inventario del jugador
    private ArrayList<Item> inventario;
    /**
     * Constructor for objects of class Player
     */
    public Player(Room firstRoom)
    {
        pesoObj = 0;
        inventario = new ArrayList<Item>();
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
        if (currentRoom.getExit(direction) != null){
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

    /**
     * Metodo para recoger un itme de la sala.
     */
    public void takeObject(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Coger que?");
            return;
        }

        String objeto = command.getSecondWord();
        //Intenta coger un objeto.
        if (currentRoom.getNumberItem() == 0) {
            System.out.println("No hay objetos en la sala!!");
        }
        inventario.add(currentRoom.getItem(objeto));
        if (currentRoom.getItem(objeto).puedeRecogerse()){
            if (pesoObj + currentRoom.getItem(objeto).getPeso() <= PESO_MAXIMO){
                pesoObj += currentRoom.getItem(objeto).getPeso();
                currentRoom.removeItem(objeto);
            }
            else{
                System.out.println("No puedes coger el objeto, demasiado peso!");
            }
        }
        else{
            System.out.println("No se puede coger ese objeto!!");
        }
    }
}
