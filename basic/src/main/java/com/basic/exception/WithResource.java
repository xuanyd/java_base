package com.basic.exception;

import java.io.FileInputStream;
import java.util.Scanner;

public class WithResource {
    public void throwWithResource() {
        System.out.println("try中携带资源");
        try(Scanner in = new Scanner(new FileInputStream("words.txt"), "UTF-8")) {
            while(in.hasNext()){
                System.out.println(in.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}