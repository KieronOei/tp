package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_DATE;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_HEADER;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_LOCATION;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.AddInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;
import presspal.contact.model.interview.Header;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.interview.Location;

/**
 * Parses input arguments and creates a new AddInterviewCommand object.
 */
public class AddInterviewCommandParser implements Parser<AddInterviewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddInterviewCommand
     * and returns an AddInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInterviewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_INDEX, PREFIX_HEADER, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_HEADER, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddInterviewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(); // Verify no duplicates
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
        Header header = ParserUtil.parseHeader(argMultimap.getValue(PREFIX_HEADER).orElse(""));
        LocalDateTime dateTime = ParserUtil.parseLocalDateTime(argMultimap.getValue(PREFIX_DATE).orElse(""),
                argMultimap.getValue(PREFIX_TIME).orElse(""));
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).orElse(""));
        Interview interview = new Interview(header, location, dateTime);
        // TODO: What about index?

        return new AddInterviewCommand(interview);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
