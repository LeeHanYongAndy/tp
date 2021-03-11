package seedu.duke.command;

import seedu.duke.common.RecordType;
import seedu.duke.exception.CommandException;
import seedu.duke.record.Expense;
import seedu.duke.record.RecordList;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;

import static seedu.duke.command.Utils.checkInvalidOptions;
import static seedu.duke.command.Utils.checkOptionConflict;
import static seedu.duke.command.Utils.getOptionValue;
import static seedu.duke.command.Utils.hasOption;
import static seedu.duke.common.Constant.OPTION_AMOUNT;
import static seedu.duke.common.Constant.OPTION_DATE;
import static seedu.duke.common.Constant.OPTION_EXPENSE;
import static seedu.duke.common.Constant.OPTION_LOAN;
import static seedu.duke.common.Constant.OPTION_SAVING;
import static seedu.duke.common.Validators.validateAmount;
import static seedu.duke.common.Validators.validateDate;

import java.util.ArrayList;

public class AddCommand extends Command {
    protected static final String COMMAND_ADD = "add";
    private final double amount;
    private final String issueDate;
    private final String description;

    private RecordType recordType;

    public AddCommand(ArrayList<String> arguments) throws CommandException {
        checkInvalidOptions(arguments, COMMAND_ADD,
                OPTION_EXPENSE, OPTION_LOAN, OPTION_SAVING, OPTION_AMOUNT, OPTION_DATE);
        checkOptionConflict(arguments, COMMAND_ADD, OPTION_EXPENSE, OPTION_LOAN, OPTION_SAVING);

        //System.out.println(arguments);

        if (hasOption(arguments, OPTION_EXPENSE)) {
            recordType = RecordType.EXPENSE;
            description = Utils.getOptionValue(arguments, COMMAND_ADD, OPTION_EXPENSE);
        } else if (hasOption(arguments, OPTION_LOAN)) {
            recordType = RecordType.LOAN;
            description = Utils.getOptionValue(arguments, COMMAND_ADD, OPTION_LOAN);
        } else if (hasOption(arguments, OPTION_SAVING)) {
            recordType = RecordType.SAVING;
            description = Utils.getOptionValue(arguments, COMMAND_ADD, OPTION_SAVING);
        } else {
            throw new CommandException("missing option: [-e | -l | -s]", COMMAND_ADD);
        }

        String amountInString = getOptionValue(arguments, COMMAND_ADD, OPTION_AMOUNT);
        String dateInString = getOptionValue(arguments, COMMAND_ADD, OPTION_DATE);
        if (!validateAmount(amountInString)) {
            throw new CommandException("amount contains a non numeric value", COMMAND_ADD);
        } else if (validateDate(dateInString)) {
            throw new CommandException("date format error", COMMAND_ADD);
        }
        amount = Double.parseDouble(amountInString);
        issueDate = dateInString;
    }



    @Override
    public void execute(RecordList records, Ui ui, Storage storage) {
        switch (recordType) {
        case EXPENSE:
            Expense expenseObj = new Expense(amount, issueDate, description);
            records.addRecord(expenseObj);
            storage.saveRecordListData(records);
            ui.printSuccessfulAdd(expenseObj);
            break;
        case LOAN:
            //records.addRecord(records, ui, storage);
            System.out.println("Type is LOAN");
            break;
        case SAVING:
            //records.addRecord(records, ui, storage);
            System.out.println("Type is SAVINGS");
            break;
        default:
            ui.printMessage("Unable to list records.");
        }
    }
}
