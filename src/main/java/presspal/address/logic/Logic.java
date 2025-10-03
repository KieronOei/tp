package presspal.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import presspal.address.commons.core.GuiSettings;
import presspal.address.logic.commands.CommandResult;
import presspal.address.logic.commands.exceptions.CommandException;
import presspal.address.logic.parser.exceptions.ParseException;
import presspal.address.model.Model;
import presspal.address.model.ReadOnlyAddressBook;
import presspal.address.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
