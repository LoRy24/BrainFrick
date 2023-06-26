package com.github.lory24.brainfrick;

import com.github.lory24.brainfrick.utils.ProgramArguments;

import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ProgramArguments programArguments = new ProgramArguments(args);
        String filePath = programArguments.getFilePath();
        String fileContent;

        while (true) {
            if (filePath == null) {
                System.out.print("Insert file path > ");
                filePath = scanner.nextLine();
            }

            try {
                fileContent = Files.readString(new File(filePath).toPath());
                break;
            } catch (Exception e) {
                System.out.println("Error while reading from file! Check if the path is correct and try again.");
                e.printStackTrace();
                filePath = null;
            }
        }

        BrainFrick brainFrick = new BrainFrick(fileContent, programArguments.getMemSize());
        brainFrick.runScript();
    }
}
