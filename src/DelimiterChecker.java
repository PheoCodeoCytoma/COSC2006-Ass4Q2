import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

class DelimiterData {
    char character;
    int line;
    int column;

    DelimiterData(char character, int line, int column) {
        this.character = character;
        this.line = line;
        this.column = column;
    }
}

public class DelimiterChecker {
    public static void main(String[] args) {
        String filename = "input.txt";
        try {
            Scanner scanner = new Scanner(new File("test_file_ass4.txt"));
            Stack<DelimiterData> stack = new Stack<>();

            int line = 1;
            while (scanner.hasNextLine()) {
                int column = 1;
                String currentLine = scanner.nextLine();
                for (char c : currentLine.toCharArray()) {
                    if (c == '{' || c == '[' || c == '(') {
                        stack.push(new DelimiterData(c, line, column));
                    } else if (c == '}' || c == ']' || c == ')') {
                        if (stack.empty()) {
                            System.out.println("Unmatched delimiter at line " + line + ", column " + column);
                        } else {
                            DelimiterData delimiter = stack.pop();
                            if (c == '}' && delimiter.character != '{' ||
                                    c == ']' && delimiter.character != '[' ||
                                    c == ')' && delimiter.character != '(') {
                                System.out.println("Mismatched delimiter at line " + line + ", column " + column +
                                        " (expected " + delimiter.character + ")");
                                System.out.println("Mismatched delimiter at line " + delimiter.line +
                                        ", column " + delimiter.column + " (found " + delimiter.character + ")");
                            }
                        }
                    }
                    column++;
                }
                line++;
            }
            scanner.close();

            if (!stack.empty()) {
                DelimiterData delimiter = stack.pop();
                System.out.println("Unmatched delimiter at line " + delimiter.line +
                        ", column " + delimiter.column + " (found " + delimiter.character + ")");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
