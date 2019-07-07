package com.intuit.service;

import com.intuit.model.Citizen;
import com.intuit.model.Contender;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import org.apache.commons.collections4.SetUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ContenderServiceTest {

    @InjectMocks
    private ContenderService service;

    @Test
    public void shouldAddFollower() {
        Citizen citizen = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Citizen citizen1 = Citizen.builder()
                .id(randomUUID())
                .name("Donald Trump")
                .build();
        Contender contender = service.addAsAContender(citizen1);
        Idea idea1 = new Idea(contender, "Idea1 details");
        service.addIdeasToContender(contender, idea1);

        IdeaRating ideaRating = new IdeaRating(idea1, citizen, 5);

        //When
        service.checkAndAddFollower(ideaRating);
        assertTrue(contender.getFollowers().size() > 0);
        Optional<Citizen> follower = contender.getFollowers().stream().findFirst();
        assertEquals(follower.get(), citizen);
    }

    @Test
    public void shouldNotAddFollowerRatingTooLow() {
        Citizen citizen = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Citizen citizen1 = Citizen.builder()
                .id(randomUUID())
                .name("Donald Trump")
                .build();
        Contender contender = service.addAsAContender(citizen1);
        Idea idea1 = new Idea(contender, "Idea1 details");
        service.addIdeasToContender(contender, idea1);

        IdeaRating ideaRating = new IdeaRating(idea1, citizen, 4);

        //When
        service.checkAndAddFollower(ideaRating);
        assertEquals(0, contender.getFollowers().size());
    }

    @Test
    public void shoudGetAllIdeasForContender() {
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Set<Idea> ideas = createIdeas(contender);

        //When
        Set<Idea> ideasForContender = service.getIdeasForContender(contender);
        assertArrayEquals(ideas.toArray(), ideasForContender.toArray());
    }

    private Set<Idea> createIdeas(Contender contender) {
        Idea idea1 = new Idea(contender, "Idea1 details");
        Idea idea2 = new Idea(contender, "Idea2 details");
        Idea idea3 = new Idea(contender, "Idea3 details");
        service.addIdeasToContender(contender, idea1, idea2, idea3);
        return SetUtils.unmodifiableSet(idea1, idea2, idea3);
    }
}
