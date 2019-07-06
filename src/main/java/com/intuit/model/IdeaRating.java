package com.intuit.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = {"idea", "citizen"})
public class IdeaRating {
    Idea idea;
    Citizen citizen;
    Integer rating;

    public void acceptRating(Citizen citizen, Idea idea, Integer rating) {

    }

}
