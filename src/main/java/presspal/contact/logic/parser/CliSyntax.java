package presspal.contact.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for Person*/
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ORGANISATION = new Prefix("o/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    /* Prefix definitions for Interview*/
    public static final Prefix PREFIX_HEADER = new Prefix("h/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    /* Prefix definitions for Index */
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
}
