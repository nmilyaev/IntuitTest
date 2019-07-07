package com.intuit.service;

import com.intuit.model.Citizen;
import com.intuit.model.Contender;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class IdeaRatingServiceTest {

    @InjectMocks
    IdeaRatingService service;

    @Test
    public void shouldAddAndRetrieveRatingsForIdea() {
        Citizen citizen = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea1 = new Idea(contender, "Idea1 details");

        // When
        service.addRating(citizen, idea1, 5);

        Set<IdeaRating> ratingsForIdea = service.getRatingsForIdea(idea1);
        assertEquals(1, ratingsForIdea.size());
        assertEquals(service.getAverageRating(idea1), 5, 0.0);
    }

    // Multiple citizens add ratings - in this case also check average
    @Test
    public void shouldAddAndRetrieveMultipleRatingsForIdea() {
        Citizen citizen1 = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Citizen citizen2 = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Citizen citizen3 = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea = new Idea(contender, "Idea1 details");

        // When
        service.addRating(citizen1, idea, 5);
        service.addRating(citizen2, idea, 1);
        service.addRating(citizen3, idea, 2);

        Set<IdeaRating> ratingsForIdea = service.getRatingsForIdea(idea);
        assertEquals(3, ratingsForIdea.size());
        assertEquals(service.getAverageRating(idea), 2.667, 0.001);
    }

    // The same citizen updates their rating
    @Test
    public void shouldUpdateRating() {
        Citizen citizen = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea = new Idea(contender, "Idea1 details");

        // When
        service.addRating(citizen, idea, 5);
        service.addRating(citizen, idea, 3);

        Set<IdeaRating> ratingsForIdea = service.getRatingsForIdea(idea);
        assertEquals(1, ratingsForIdea.size());
        assertEquals(service.getAverageRating(idea), 3.0, 0.001);
    }
}
