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
    private ArrayList<Room> salas;
    private ArrayList<Item> objetos;
    private Room salida;
    private Room trampa;
    private Item objetoClave;
    //private Stack<Room> salas;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        salas = new ArrayList<Room>();
        objetos = new ArrayList<Item>();
        createRooms();
        parser = new Parser();
        player = new Player(currentRoom);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room central, ruinas, pedestal, arbol, pozo, mazmorra;

        // create the rooms
        central = new Room("Llegas a una sala con salida en todas las direcciones, en ella ves cuatro imponentes \ncolumnas que hace años deberian haber sido blancas.");
        ruinas = new Room("Al entrar te sorprendes al verte rodeado de ruinas de antiguas construcciones.");
        pedestal = new Room("En medio de esta sala se puede apreciar un pedestal.");
        arbol = new Room("Enfrente de ti se puede observar un arbol de un tamaño bastante considerable.");
        pozo = new Room("En medio de esta nueva sala te encuentras con un gran pozo y no puedes evitar preguntarte si tendra algo de agua en el fondo.");
        mazmorra = new Room("Miras alrededor y te das cuenta de que has vuelto a las mazmorras donde empezaste... Lo que pensabas, un laberinto...");

        //Items
        mazmorra.addItem("Manzana", 1, true);
        mazmorra.addItem("Llave", 1, true);
        mazmorra.addItem("Martillo", 9, true);
        mazmorra.addItem("Piedra", 9, false);

        // initialise room exits (N,E,S,W,SE,NO)
        central.setExit("north", mazmorra);
        central.setExit("east", ruinas);
        central.setExit("south", arbol);
        central.setExit("west", pedestal);

        ruinas.setExit("north", pozo);
        ruinas.setExit("west", central);
        ruinas.setExit("northWest", mazmorra);

        arbol.setExit("north", central);

        pozo.setExit("south", ruinas);
        pozo.setExit("west", mazmorra);

        mazmorra.setExit("east", pozo);
        mazmorra.setExit("south", central);
        mazmorra.setExit("southEast", ruinas);

        pedestal.setExit("east", central);

        currentRoom = mazmorra;  // start game outside

        //Asignamos la salida y la trampa aleatoriamente.
        salas.add(central);
        salas.add(ruinas);
        salas.add(pedestal);
        salas.add(arbol);
        salas.add(pozo);
        salas.add(mazmorra);
        Random nAleat = new Random();
        salida = salas.get(nAleat.nextInt(salas.size()-1));
        trampa = salas.get(nAleat.nextInt(salas.size()-1));
        while(salida == currentRoom){
            salida = salas.get(nAleat.nextInt(salas.size()-1));
        }
        while(salida == trampa || trampa == currentRoom){
            trampa = salas.get(nAleat.nextInt(salas.size()-1));
        }

        //Asigna el objeto para salir de la sala una vez.
        for(Room newSala: salas){
            setGlobalItems(newSala.getRoomItems());
        }

        objetoClave = objetos.get(nAleat.nextInt(objetos.size()-1));
        while(!objetoClave.puedeRecogerse()){
            objetoClave = objetos.get(nAleat.nextInt(objetos.size()-1));
        }
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        String victory = salida.getDescription();
        String derrota = trampa.getDescription();
        boolean estaEnSalida = false;
        boolean estaEnTrampa = false;
        boolean finished = false;
        boolean objClvUsado = false;
        while (!finished && !estaEnSalida && !estaEnTrampa) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (currentRoom.getDescription().equals(victory)){
                estaEnSalida = true;
                System.out.println("##############################################################");
                System.out.println("Al pasar por la entrada de la sala ves una luz enfrente de ti.");
                System.out.println("                      ¡La salida!");
                System.out.println("##############################################################");
            }
            if (currentRoom.getDescription().equals(derrota)){
                System.out.println("#########################################################################");
                System.out.println("Al entrar a la sala oyes un ruido a tu espalda y te sorprendes al ver que");
                System.out.println("            como todas las salidas desaparecen tras un muro.");
                Item objCompr = new Item(null, 0, false);
                int count = 0;
                while (objCompr != objetoClave && count <= player.getInventory().size()){
                    objCompr = player.getInventory().get(count);
                    count++;
                }
                if (objCompr != objetoClave || objClvUsado){
                    estaEnTrampa = true;
                    System.out.println("                     Estas encerrado, 'GAME OVER'");
                    System.out.println("#########################################################################\n");
                }
                else{
                    System.out.println("Miras al suelo y ves un hueco donde puede entrar unos de los objetos que");
                    System.out.println("                             llevas.");
                    System.out.println("                       Tiene forma de " + objetoClave.getDescription() + ".");
                    System.out.println("   Lo metes y el suelo se lo traga a la vez que las puertas se abren.");
                    System.out.println("#########################################################################\n");
                }
                objClvUsado = true;
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
        System.out.println("Type " + Option.help.getCommand() + " if you need help.");
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

        Option commandWord = command.getCommandWord();
        switch (commandWord){
            case help:
            printHelp();
            break;

            case go:
            goRoom(command);
            break;

            case quit:
            wantToQuit = quit(command);
            break;

            case look:
            System.out.println(currentRoom.getLongDescription());
            break;

            case eat:
            System.out.println("You have eaten now and you are not hungry any more");
            break;

            case back:
            goBack();
            break;

            case take:
            player.takeObject(command);
            break;

            case drop:
            player.dropObject(command);
            break;

            case items:
            player.showObjects(command);
            break;
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
        Room newRoom = player.goRoom(command);
        if (newRoom != null){
            currentRoom = newRoom;   
        }
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

    /**
     * Metodo para almacenar los objetos de una sala (En HashMap) en un arrayList global.
     */
    private void setGlobalItems(HashMap<String, Item> items){

        for(Item objActual : items.values()){
            objetos.add(objActual);
        }

    }
}
