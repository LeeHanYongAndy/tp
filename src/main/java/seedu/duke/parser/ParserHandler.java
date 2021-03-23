package seedu.duke.parser;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;

/**
 * Parser class to handle parsing of userInput.
 */
public class ParserHandler {
    private final String[] startOptionArray;
    private final String[] endOptionArray;
    private final String[] middleOptionArray;

    public ParserHandler() {
        startOptionArray = new String[]{"-e", "-l", "-s", "-d", "-a", "-i", "-p"};
        endOptionArray = new String[]{" -e", " -l", " -s", " -d", " -a", " -i", " -p"};
        middleOptionArray = new String[]{" -e ", " -l ", " -s ", " -d ", " -a ", " -i ", " -p "};
    }

    /**
     * Check and parse if the input starts with option.
     * @param userInput contains a single string to be parsed.
     * @return a ArrayList of String containing trimmed options and arguments.
     */
    public ArrayList<String> getParseInput(String userInput) {
        ArrayList<String> extracted = new ArrayList<>();
        String trimmedInput = userInput.stripLeading();
        if (checkOptionStartWith(trimmedInput)) {
            extracted.add(trimmedInput.substring(0,2));
            trimmedInput = trimmedInput.substring(2);
        }
        return extractAfterFirstCheck(extracted, trimmedInput);
    }

    /**
     * parse the remaining trimmed input before checking for last block.
     * @param extracted ArrayList of String containing the initial parsed option or argument.
     * @param trimmedInput contains the remaining input that is needed to be parse.
     * @return a ArrayList of String containing trimmed options and arguments.
     */
    private ArrayList<String> extractAfterFirstCheck(ArrayList<String> extracted, String trimmedInput) {
        int optionIndex = getNextOptionIndex(trimmedInput);
        while (optionIndex != -1) {
            String argument = trimmedInput.substring(0,optionIndex).trim();
            extracted.add(argument);
            trimmedInput = trimmedInput.substring(optionIndex).stripLeading();
            extracted.add(trimmedInput.substring(0,2));
            trimmedInput = trimmedInput.substring(2);
            optionIndex = getNextOptionIndex(trimmedInput);
        }
        return extractFinalPart(extracted, trimmedInput);
    }

    /**
     * Check and parse if last block in ArrayList of String ends with option.
     * Add a empty string after the last block if last block ends with option.
     * @param extracted ArrayList of String containing the parsed option or argument.
     * @param trimmedInput contains the final remaining input that is needed to be parse.
     * @return a ArrayList of String containing trimmed options and arguments.
     */
    private ArrayList<String> extractFinalPart(ArrayList<String> extracted, String trimmedInput) {
        if (checkOptionEndWith(trimmedInput)) {
            String argument = trimmedInput.substring(0, (trimmedInput.length() - 2)).trim();
            extracted.add(argument);
            extracted.add(trimmedInput.substring(trimmedInput.length() - 2).trim());
            extracted.add("");
        } else {
            extracted.add(trimmedInput.trim());
        }
        return checkFirstBlock(extracted);
    }

    /**
     * Check and parse if first block in pre final ArrayList of String starts with help or creditscore.
     * Add relevant string after the help or creditscore block.
     * @param extracted ArrayList of String containing the pre-final parsed option or argument.
     * @return the final parsed ArrayList of String containing the options and arguments.
     */
    private static ArrayList<String> checkFirstBlock(ArrayList<String> extracted) {
        String firstblock = extracted.get(0);
        if (StringUtils.startsWithAny(firstblock, "help ", "creditscore ")) {
            String[] splitBlock = firstblock.split(" ", 2);
            extracted.remove(0);
            extracted.add(splitBlock[0]);
            extracted.add(splitBlock[1].trim());
        }
        if (StringUtils.startsWith(firstblock, "help") && firstblock.length() == 4) {
            extracted.add(1,"all");
        }
        if (StringUtils.startsWith(firstblock, "creditscore") && firstblock.length() == 11) {
            extracted.add(1,"");
        }
        return extracted;
    }

    /**
     * Check if the input starts with valid options.
     * @param input string that is needed to be checked against.
     * @return true if input starts with valid options, else otherwise.
     */
    private boolean checkOptionStartWith(String input) {
        return StringUtils.startsWithAny(input, startOptionArray);
    }

    /**
     * Check if the input ends with valid options.
     * @param input string that is needed to be checked against.
     * @return true if input ends with valid options, else otherwise.
     */
    private boolean checkOptionEndWith(String input) {
        return StringUtils.endsWithAny(input, endOptionArray);
    }

    /**
     * Check the next valid option index.
     * @param leftOverString containing the remaining input to be checked against.
     * @return the first index if matches any valid options in input, else -1.
     */
    private int getNextOptionIndex(String leftOverString) {
        return StringUtils.indexOfAny(leftOverString, middleOptionArray);
    }
}
