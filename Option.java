/**
 * Enumeration class Option - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Option
{
    go("ir"), quit("quitar"), help("ayuda"), look("mirar"), eat("comer"), back("retroceder"), take("coger"), drop("tirar"), items("inventario"), UNKNOWN("UNKNOWN");    

    private String command;

    /**
     * Constructor de la clase Option
     */
    Option (String command) 
    {
        this.command = command;
    }
}

