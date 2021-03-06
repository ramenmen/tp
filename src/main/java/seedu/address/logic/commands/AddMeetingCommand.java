package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.UniqueMeetingList.Pair;

public class AddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "addmeeting";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to your schedule. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DATETIME + "DATETIME "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_LOCATION + "LOCATION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "v1.3 discussion "
            + PREFIX_DATETIME + "31/12/20 1400 "
            + PREFIX_DURATION + "1 30 "
            + PREFIX_LOCATION + "Cool spot "
            + PREFIX_RECURRENCE + "weekly/5";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s \n"
            + "Add participants with the addpart command now!";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the schedule";
    public static final String MESSAGE_CONFLICT_MEETING =
            "This meeting conflicts with an existing meeting in the list :\n";

    private final Meeting toAdd;
    private final int recurNumber;

    /**
     * Creates an AddMeetingCommand to add the specified {@code Meeting}
     */
    public AddMeetingCommand(Meeting meeting) {
        requireNonNull(meeting);
        toAdd = meeting;
        recurNumber = 1;
    }

    /**
     * Creates an AddMeetingCommand to add the specified {@code Meeting} and {@code Recurring Number}
     */
    public AddMeetingCommand(Meeting meeting, int recur) {
        requireNonNull(meeting);
        assert recur > 0 && recur <= 20;
        toAdd = meeting;
        recurNumber = recur;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasMeeting(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }

        // check if all recurrence meetings do not conflict with current schedule, then proceed to add them
        for (Meeting meeting : toAdd.getRecurrencesAsList(recurNumber)) {
            Pair<Boolean, Optional<Meeting>> conflictCheckResult = model.hasConflict(meeting);
            if (conflictCheckResult.getLeftValue()) {
                throw new CommandException(MESSAGE_CONFLICT_MEETING + conflictCheckResult.getRightValue().get());
            }
        }

        for (Meeting meeting : toAdd.getRecurrencesAsList(recurNumber)) {
            model.addMeeting(meeting);
        }

        model.sortMeeting();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMeetingCommand // instanceof handles nulls
                && toAdd.equals(((AddMeetingCommand) other).toAdd))
                && recurNumber == ((AddMeetingCommand) other).recurNumber;
    }
}
