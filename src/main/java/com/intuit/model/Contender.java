package com.intuit.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Contender {
    private Citizen citizen;
    private Set<Citizen> followers = new HashSet<>();
    private Set<Idea> ideas = new HashSet<>();

    public Contender(Citizen citizen) {
        this.setCitizen(citizen);
    }

    public void addIdeas(Idea ... newIdeas) {
        ideas.addAll(Arrays.asList(newIdeas));
    }
}
