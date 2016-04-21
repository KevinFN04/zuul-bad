import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private Room currentRoom;
    //private Stack<Room> salas;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player(currentRoom);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room central, ruinas, trampa, salida, pozo, mazmorra;

        // create the rooms
        central = new Room("Llegas a una sala con salida en todas las direcciones, en ella ves cuatro imponentes \ncolumnas que hace años deberian haber sido blancas.");
        ruinas = new Room("Al entrar te sorprendes al verte rodeado de ruinas de antiguas construcciones.");
        trampa = new Room("Al entrar a la sala oyes un ruido a tu espalda y te sorprendes al ver que por donde acabas de entrar ya no hay salida. Estas encerrado, 'GAME OVER'");
        salida = new Room("Al pasar por la entrada de la sala ves una luz enfrente de ti. \n¡La salida!");
        pozo = new Room("En medio de esta nueva sala te encuentras con un gran pozo y no puedes evitar preguntarte si tendra algo de agua en el fondo.");
        mazmorra = new Room("Miras alrededor y te das cuenta de que has vuelto a las mazmorras donde empezaste... Lo que pensabas, un laberinto...");
        //Items
        mazmorra.addItem("manzana", 0.12F);
        mazmorra.addItem("Llave", 0.09F);
        // initialise room exits (N,E,S,W,SE,NO)
        central.setExit("north", mazmorra);
        central.setExit("east", ruinas);
        central.setExit("south", salida);
        central.setExit("west", trampa);

        ruinas.setExit("north", pozo);
        ruinas.setExit("west", central);
        ruinas.setExit("northWest", mazmorra);

        salida.setExit("north", central);

        pozo.setExit("south", ruinas);
        pozo.setExit("west", mazmorra);

        mazmorra.setExit("east", pozo);
        mazmorra.setExit("south", central);
        mazmorra.setExit("southEast", ruinas);

        currentRoom = mazmorra;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        String victory = "Al pasar por la entrada de la sala ves una luz enfrente de ti. \n¡La salida!";
        String derrota = "Al entrar a la sala oyes un ruido a tu espalda y te sorprendes al ver que por donde acabas de entrar ya no hay salida. Estas encerrado, 'GAME OVER'";
        boolean salida = false;
        boolean trampa = false;
        boolean finished = false;
        while (!finished && !salida && !trampa) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (currentRoom.getDescription().equals(victory)){
                salida = true;
            }
            if (currentRoom.getDescription().equals(derrota)){
                trampa = true;
            }
        }
        System.out.println("Juego terminado, gracias por jugar :')");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("Abres los ojos y no reconoces la sala que tienes alrededor... Estas en una celda, con barrotes viejos y oxidados ");
        System.out.println("al mirar al techo ves un agujero por el que salir, sin pensarlo saltas fuera de la celda hasta una sala con paredes ");
        System.out.println("altas, muy altas, comidas por el moho y sin ningun tipo de techo... Hacia arriba solo se veia el cielo oscuro de la noche. ");
        System.out.println("Miras a tu alrededor esta vez fijandote mas en los detalles, solo hay un par de chabolas vacias y unas 10 celdas como ");
        System.out.println("de la que acabas de salir, ¿unas mazomorras o algo asi?");
        System.out.println("Miras hacia una de las paredes, ves una salida y lo unico que piensas es que ojala esto no sea igual que un laberinto...");
        printLocationInfo();
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            System.out.println(currentRoom.getLongDescription());
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more");
        }
        else if (commandWord.equals("back")) {
            goBack();
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone.");
        System.out.println();
        //Opcion1:
        //parser.getCommands().showAll();
        //Opcion2:
        parser.printCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        player.goRoom(command);
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Metodo para saber que salidas tiene una sala.
     */
    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Metodo para saber que salidas tiene una sala.
     */
    private void goBack(){
        player.goBack();
    }
}
