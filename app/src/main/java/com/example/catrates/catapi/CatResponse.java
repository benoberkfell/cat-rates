package com.example.catrates.catapi;

import com.example.catrates.models.Cat;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "data", strict = false)
public class CatResponse {

    @Element(name = "data")
    private CatData catData;

    public List<Cat> getCats() {
        return catData.getCats();
    }

}

