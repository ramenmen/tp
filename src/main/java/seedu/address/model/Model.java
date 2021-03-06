package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.UniqueMeetingList.Pair;
import seedu.address.model.memento.History;
import seedu.address.model.memento.StateManager;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Meeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the next {@code meeting} in the future with a particular offset (in minutes) from current time.
     * If there is no future meeting, return null.
     */
    Meeting getNextMeeting(long offset);

    /**
     * Returns true if a meeting with the same identity as {@code meeting} exists in the address book.
     */
    boolean hasMeeting(Meeting meeting);

    /**
     * Returns true if a meeting in the schedule has overlapped timing (includes interval) with {@code meeting}
     */
    Pair<Boolean, Optional<Meeting>> hasConflict(Meeting meeting);

    /**
     * Deletes the given meeting.
     * The meeting must exist in the address book.
     */
    void deleteMeeting(Meeting target);

    /**
     * Deletes all recurrences of the given meeting.
     * The meeting must exist in the address book.
     */
    void deleteRecurringMeetings(Meeting target);

    /**
     * Sort all the existing meeting according to date and time.
     */
    void sortMeeting();

    /**
     * Adds the given meeting.
     * {@code meeting} must not already exist in the address book.
     */
    void addMeeting(Meeting meeting);

    /**
     * Replaces the given meeting {@code target} with {@code editedMeeting}.
     * {@code target} must exist in the address book.
     * The meeting identity of {@code editedMeeting} must not be the same as
     * another existing meeting in the address book.
     */
    void setMeeting(Meeting target, Meeting editedMeeting);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodified view of the person map */
    ObservableMap<UUID, Person> getPersonMap();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered meeting list */
    ObservableList<Meeting> getFilteredMeetingList();

    /**
     * Returns the person with the uuid key.
     * @param uuid of the person.
     */
    Person getParticipant(UUID uuid);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered meeting list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMeetingList(Predicate<Meeting> predicate);

    void reattachDependentMeetings(Person editedPerson);

    void refreshApplication();

    /**
     * Returns the state manager of the current app.
     */
    StateManager getStateManager();

    /**
     * Returns the history of states of the current app.
     */
    History getHistory();
}
