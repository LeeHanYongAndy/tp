package seedu.duke.command;

import seedu.duke.record.RecordList;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;

public abstract class Command {
    public abstract void execute(RecordList records, Ui ui, Storage storage);
}
