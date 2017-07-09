package com.example.catrates.catapi;

import com.example.catrates.models.Cat;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

public class CatData {

    @ElementList(name = "images")
    private ArrayList<Cat> cats;

    List<Cat> getCats() {
        return cats;
    }

}