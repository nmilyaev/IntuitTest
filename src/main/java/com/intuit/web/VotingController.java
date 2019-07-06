package com.intuit.web;

import com.intuit.model.Citizen;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import com.intuit.service.CitizenService;
import com.intuit.service.ContenderService;
import com.intuit.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("election")
public class VotingController {
    @Autowired
    private ContenderService contenderService;

    @Autowired
    private ElectionService electionService;

    @GetMapping("produces = application/json")
    public Set<Idea> getAllIdeas() {
        return contenderService.getAllIdeas();
    }

    @PostMapping("produces = application/json")
    public ResponseEntity<IdeaRating> addVote(Citizen citizen, Idea idea, Integer rating){
        IdeaRating ideaRating = electionService.acceptRating(citizen, idea, rating);
        if(ideaRating == null){
            return new ResponseEntity<>(ideaRating, OK);
        }
        return new ResponseEntity<>(ideaRating, UNPROCESSABLE_ENTITY);
    }

    @PutMapping("produces = application/json")
    public void editVote(Citizen citizen, Idea idea, Integer rating){
        electionService.acceptRating(citizen, idea, rating);
    }



}
