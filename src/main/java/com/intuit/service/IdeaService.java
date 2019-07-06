package com.intuit.service;

import com.intuit.model.Contender;
import com.intuit.model.Idea;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IdeaService {
    private Set<Idea> ideas = new HashSet<>();

    public void addIdea(Contender contender, String details){
        Idea idea = new Idea(contender, details);
        ideas.add(idea);
    }

    boolean isExist(Idea idea){
        return ideas.contains(idea);
    }
}
