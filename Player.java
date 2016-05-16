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
    public static final int PESO_MAXIMO = 10;
    //Peso que lleva el usuario actualmente
    private int pesoObj;
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
    public Room goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return null;
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
        return currentRoom;
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
        Item objetoRec= currentRoom.getItem(objeto);
        if (objetoRec != null && objetoRec.puedeRecogerse()){
            if (pesoObj + currentRoom.getItem(objeto).getPeso() <= PESO_MAXIMO){
                inventario.add(objetoRec);
                pesoObj += objetoRec.getPeso();
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

    /**
     * Metodo para tirar un item de la sala.
     */
    public void dropObject(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Tirar que?");
            return;
        }

        String objeto = command.getSecondWord();
        
        int index = 0;
        boolean encontrado = false;
        for(int i = 0; i < inventario.size() && !encontrado; i++) {
            if (inventario.get(i).getDescription().equals(objeto)){
                index = i;
                encontrado = true;
            }
        }
        
        if (encontrado){
            pesoObj = pesoObj - inventario.get(index).getPeso();
            currentRoom.addItem(inventario.get(index).getDescription(), inventario.get(index).getPeso(), true);
            inventario.remove(index);           
        }
        else{
            System.out.println("No puedes dejar un objeto que no tienes!");
        }
    }
    
    /**
     * Metodo para mostrar los objetos y el peso actual.
     */
    public void showObjects(Command command){
       for(Item objetoInv : inventario){
           System.out.println(objetoInv.getDescription());
        }
        System.out.println("Peso actual: " + pesoObj);
    }
    
    /**
     * Metodo para mostrar los objetos y el peso actual.
     */
    public ArrayList<Item> getInventory(){
       return inventario;
    }
}

