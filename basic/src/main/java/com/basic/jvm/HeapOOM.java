package com.basic.jvm;

import java.util.List;
import java.util.ArrayList;

public class HeapOOM {
    
    static class OOMObject {

    }

    public static void addObject() {
        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }

    }

}