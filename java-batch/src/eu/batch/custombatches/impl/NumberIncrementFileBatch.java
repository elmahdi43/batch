package eu.batch.custombatches.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import eu.batch.custombatches.interfaces.Batch;

/**
 * Implementation of Batch interface for processing files
 * that increments each line of numbers by 1.
 */
public class NumberIncrementFileBatch implements Batch<File, String> {
    
    @Override
    public String read(File input) throws IOException {
        if (!input.exists()) {
            throw new IOException("File does not exist: " + input.getAbsolutePath());
        }
        return Files.readString(input.toPath(), StandardCharsets.UTF_8);
    }
    
    @Override
    public String process(String data) throws IllegalArgumentException {
        if (data == null || data.trim().isEmpty()) {
            return "";
        }
        
        String[] lines = data.split("\\R"); // Platform independent line splitting
        List<String> processedLines = new ArrayList<>(lines.length);
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            try {
                int number = Integer.parseInt(line);
                processedLines.add(String.valueOf(number + 1));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Line " + (i + 1) + " is not a valid integer: " + line, e);
            }
        }
        
        return String.join(System.lineSeparator(), processedLines);
    }
    
    @Override
    public void write(File output, String data) throws IOException {
        Files.writeString(output.toPath(), data, StandardCharsets.UTF_8);
    }
}
