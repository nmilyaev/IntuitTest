package com.intuit.service;

import com.intuit.model.Citizen;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IdeaRatingService {
    private Set<IdeaRating> ideaRatings = new HashSet<>();

    Set<IdeaRating> getRatingsForIdea(Idea idea) {
        return ideaRatings.stream().filter(ir -> ir.getIdea().equals(idea)).collect(Collectors.toSet());
    }

    public int getAverageRating(Idea idea) {
        return ideaRatings.stream().filter(ir -> ir.getIdea().equals(idea))
                .filter(Objects::nonNull)
                .mapToInt(IdeaRating::getRating).sum();
    }

    IdeaRating add(Citizen citizen, Idea idea, Integer rating) {
        IdeaRating ideaRating = new IdeaRating(idea, citizen, rating);
        boolean result = ideaRatings.add(ideaRating);
        return result ? ideaRating : null;
    }
}
