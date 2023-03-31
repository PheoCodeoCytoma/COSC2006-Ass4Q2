import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class BalancedDelimiters {
    public static void main(String[] args) {

        String filePath = "test_file_ass4.txt";
        Stack<DelimiterData> delimiterStack = new Stack<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                for (int i = 0; i < line.length(); i++) {
                    char currentChar = line.charAt(i);

                    if (currentChar == '(' || currentChar == '{' || currentChar == '[') {
                        delimiterStack.push(new DelimiterData(currentChar, lineNumber, i));
                    } else if (currentChar == ')' || currentChar == '}' || currentChar == ']') {
                        if (delimiterStack.isEmpty()) {
                            System.out.println("Unmatched closing delimiter '" + currentChar + "' at line " + lineNumber + ", column " + i);
                        } else {
                            DelimiterData lastDelimiter = delimiterStack.pop();
                            char lastChar = lastDelimiter.getCharacter();

                            if ((currentChar == ')' && lastChar != '(') ||
                                    (currentChar == '}' && lastChar != '{') ||
                                    (currentChar == ']' && lastChar != '[')) {
                                System.out.println("Mismatched delimiter '" + currentChar + "' at line " + lineNumber + ", column " + i + "; expected '" + getMatchingDelimiter(lastChar) + "'");
                            }
                        }
                    }
                }
            }

            while (!delimiterStack.isEmpty()) {
                DelimiterData unmatchedDelimiter = delimiterStack.pop();
                System.out.println("Unmatched opening delimiter '" + unmatchedDelimiter.getCharacter() + "' at line " + unmatchedDelimiter.getLine() + ", column " + unmatchedDelimiter.getColumn());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static char getMatchingDelimiter(char delimiter) {
        switch (delimiter) {
            case '(':
                return ')';
            case '{':
                return '}';
            case '[':
                return ']';
            default:
                throw new IllegalArgumentException("Not a valid delimiter");
        }
    }

    public static class DelimiterData {
        private char character;
        private int line;
        private int column;

        public DelimiterData(char character, int line, int column) {
            this.character = character;
            this.line = line;
            this.column = column;
        }

        public char getCharacter() {
            return character;
        }

        public int getLine() {
            return line;
        }

        public int getColumn() {
            return column;
        }
    }

}
