package com.github.lory24.brainfrick.utils;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ProgramArguments {
    private String filePath;
    private int memSize = 50000;

    @Contract(pure = true)
    public ProgramArguments(@NotNull String[] args) {
        if (args.length >= 1) {
            this.filePath = args[0]; // The args 0 stores the file's path
        }
        if (args.length >= 2) {
            try {
                this.memSize = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Invalid memory size! Using default one (50k entries)");
            }
        }
    }
}
