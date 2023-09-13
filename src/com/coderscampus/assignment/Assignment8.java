package com.coderscampus.assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Assignment8 {
    
    private final static String FILE_PATH = "output.txt";

    public List<Integer> getNumbers() {
        List<Integer> numbers = new ArrayList<>();
        
        try {
            numbers = Files.lines(Paths.get(FILE_PATH))
                           .flatMap(line -> {
                               String[] parts = line.split("");
                               return List.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])).stream();
                           })
                           .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return numbers;
    }
}
