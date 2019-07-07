package com.intuit.service;

import com.intuit.model.Contender;
import com.intuit.model.Idea;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IdeaService {
    private Set<Idea> ideas = new HashSet<>();

    public void addIdea(Idea idea){
        ideas.add(idea);
    }

    public boolean isExist(Idea idea){
        return ideas.contains(idea);
    }

    public Set<Idea> getAllIdeas(){
        return ideas;
    }
}
