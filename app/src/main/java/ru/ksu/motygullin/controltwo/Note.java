package ru.ksu.motygullin.controltwo;

import java.io.Serializable;

/**
 * Created by UseR7 on 19.11.2016.
 */

public class Note implements Serializable {

    public String name;
    public String text;

    public Note(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
