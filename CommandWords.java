import java.util.*;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private HashMap<String,Option> validCommandsHM;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        Option[] opciones = Option.values();
        validCommandsHM = new HashMap<>();
        final String[] validCommands = {
                "andare", "smettere", "aiuto", "guarda", "mangiare", "indietro", "prendere", "far_cadere", "elementi"};
        for (int i = 0; i < validCommands.length; i++) {
            validCommandsHM.put(validCommands[i], opciones[i]);
        }
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommandsHM.size(); i++) {
            if(validCommandsHM.keySet().equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out
     */
    public void showAll(){
        String comandos = "Your command words are:\n";
        for(String comando : validCommandsHM.keySet()){
            comandos += comando + " ";
        }
        System.out.println(comandos);
    }

    /**
     * Return the object Option associated with a word.
     * @param commandWord The word to look up (as a string).
     * @return the object Option correspondng to the paramater commandWord, or the object Option.UNKNOWN
     *         if it is not a valid command word
     */
    public Option getCommandWord(String commandWord){
        Option comando = validCommandsHM.get(commandWord);;
        if (comando == null){
            comando = Option.UNKNOWN;
        }
        return comando;
    }
}
