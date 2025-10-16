package presspal.contact.model;

import static java.util.Objects.requireNonNull;
import static presspal.contact.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import presspal.contact.commons.core.GuiSettings;
import presspal.contact.commons.core.LogsCenter;
import presspal.contact.model.person.Person;

/**
 * Represents the in-memory model of the contact book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ContactBook contactBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given contactBook and userPrefs.
     */
    public ModelManager(ReadOnlyContactBook contactBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(contactBook, userPrefs);

        logger.fine("Initializing with contact book: " + contactBook + " and user prefs " + userPrefs);

        this.contactBook = new ContactBook(contactBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.contactBook.getPersonList());
    }

    public ModelManager() {
        this(new ContactBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getContactBookFilePath() {
        return userPrefs.getContactBookFilePath();
    }

    @Override
    public void setContactBookFilePath(Path contactBookFilePath) {
        requireNonNull(contactBookFilePath);
        userPrefs.setContactBookFilePath(contactBookFilePath);
    }

    //=========== ContactBook ================================================================================

    @Override
    public void setContactBook(ReadOnlyContactBook contactBook) {
        this.contactBook.resetData(contactBook);
    }

    @Override
    public ReadOnlyContactBook getContactBook() {
        return contactBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return contactBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        contactBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        contactBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        contactBook.setPerson(target, editedPerson);
    }
    // TODO: Add a addInterview(Interview interview, Index index) method here

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedContactBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return contactBook.equals(otherModelManager.contactBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
