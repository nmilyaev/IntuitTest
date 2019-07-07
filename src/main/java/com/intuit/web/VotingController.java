package com.intuit.web;

import com.intuit.model.Citizen;
import com.intuit.model.Contender;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import com.intuit.service.ElectionService;
import com.intuit.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("election")
public class VotingController {

    @Autowired
    private ElectionService electionService;

    @Autowired
    private IdeaService ideaService;

    @GetMapping("produces = application/json")
    public Set<Idea> getAllIdeas() {
        return ideaService.getAllIdeas();
    }

    @PostMapping("produces = application/json")
    public ResponseEntity<IdeaRating> addVote(Citizen citizen, Idea idea, Integer rating) {
        IdeaRating ideaRating = electionService.acceptRating(citizen, idea, rating);
        if (ideaRating == null) {
            return new ResponseEntity<>(ideaRating, OK);
        }
        return new ResponseEntity<>(ideaRating, UNPROCESSABLE_ENTITY);
    }

    @GetMapping("produces = application/json")
    public Contender runElection() {
        return electionService.runElection();
    }
}
