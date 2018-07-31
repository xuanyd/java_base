package com.basic.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.List;

public class CountLongWords {
    
    public static void main(String[] args) throws IOException{
        String contents = new String(Files.readAllBytes(
            Paths.get("D:\\AudioFile\\alice30.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        System.out.println(words);
    }

}