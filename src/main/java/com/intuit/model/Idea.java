package com.intuit.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Idea {
    private UUID id;
    private Contender contender;
    private String details;

    public Idea(Contender contender, String details){
        this.id = UUID.randomUUID();
        this.contender = contender;
        this.details = details;
    }
}
