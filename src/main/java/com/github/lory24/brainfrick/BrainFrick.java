package com.github.lory24.brainfrick;

import com.github.lory24.brainfrick.utils.LexerOutCodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BrainFrick {
    private String fileContent;

    private final int[] values;
    private int pointer = 0, codePointer = 0;
    private final Stack<Integer> loopStarts = new Stack<>(); // The first action address of the loop(s) are stored here
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public BrainFrick(String fileContent, int memSize) {
        this.fileContent = fileContent;
        this.values = new int[memSize];
    }

    public void runScript() {
        // First: Remove all the comments from the code and all the spaces between actions
        fileContent = fileContent.trim().replaceAll("[^<>,.+\\-\\[\\]]", "");

        // Make lexing
        LexerOutCodes lexerOut = this.checkLexico();
        if (lexerOut != LexerOutCodes.OK) {
            System.out.println("[ERROR] " + lexerOut.getMessage());
            return;
        }

        // Interprete
        System.out.println("Console: ");
        int result = this.interprete();

        // Print output code
        System.out.printf("\nProgram exited with output code %d\n", result);
    }

    public LexerOutCodes checkLexico() {
        // Check for opened and closed parenthesis
        int count = 0;
        for (char c: fileContent.toCharArray()) {
            count += c == '[' ? 1 : c == ']' ? -1 : 0;
        }
        // Check errors
        return count < 0 ? LexerOutCodes.MISSING_OPEN_PARENTHESIS : count > 0 ? LexerOutCodes.MISSING_CLOSED_PARENTHESIS : // Check parenthesis
                LexerOutCodes.OK; // Fine :)
    }

    /**
     * Output codes:
     * 0  = ok
     * -1 = Integer overflow (positive)
     * -2 = Integer overflow (negative)
     * -3 = Invalid pointer
     *
     * @return Output code
     */
    public int interprete() {
        // Execute the code
        for (; codePointer < fileContent.toCharArray().length; codePointer++) {

            // region Increasing and Decreasing

            if (fileContent.charAt(codePointer) == '+') {
                // Check for integer overflow
                if (values[pointer] == Integer.MAX_VALUE) {
                    System.out.printf("Error! The current address cell (%d) cannot contain a value higher than %d!\n", pointer, values[pointer]);
                    return -1;
                }
                values[pointer]++;
            }

            if (fileContent.charAt(codePointer) == '-') {
                // Check for integer overflow
                if (values[pointer] == -Integer.MAX_VALUE) {
                    System.out.printf("Error! The current address cell (%d) cannot contain a value lower than %d!\n", pointer, values[pointer]);
                    return -2;
                }
                values[pointer]--;
            }

            // endregion Increasing and Decreasing

            // region Pointer manipulating

            if (fileContent.charAt(codePointer) == '>') {
                pointer++;
            }
            else if (fileContent.charAt(codePointer) == '<') {
                pointer--;
            }

            if (pointer < 0 || pointer > this.values.length) {
                System.out.printf("Error! The current pointer location is pointing to an invalid address (%d)!\n", pointer);
                return -3;
            }

            // endregion Pointer manipulating

            // region Loops

            if (fileContent.charAt(codePointer) == '[') {
                loopStarts.push(codePointer);
            }

            if (fileContent.charAt(codePointer) == ']') {
                if (values[pointer] == 0) {
                    loopStarts.pop();
                    continue;
                }
                codePointer = loopStarts.peek();
            }

            // endregion Loops

            // region IO

            if (fileContent.charAt(codePointer) == '.') {
                System.out.print((char) values[pointer]);
            }

            if (fileContent.charAt(codePointer) == ',') {
                try {
                    values[pointer] = reader.readLine().toCharArray()[0];
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // endregion
        }

        return 0;
    }
}
