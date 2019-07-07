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

import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ElectionServiceTest {

    @Mock
    CitizenService citizenService;

    @Mock
    IdeaService ideaService;

    @Mock
    IdeaRatingService ideaRatingService;

    @Mock
    ContenderService contenderService;

    @InjectMocks
    ElectionService service;

    //Test scenario: Accept rating:
    // - Add new follower to contender


    //Test scenario: Accept rating:
    // - Citizen or idea do not exist
    // I know that test is doing too much -
    // but the time constraint is really harsh
    @Test
    public void shouldWorkOnlyIfCitizenOrIdeaExist() {
        int rating = 4;
        Citizen citizen = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea = new Idea(contender, "Idea1 details");
        IdeaRating ideaRating = new IdeaRating(idea, citizen, rating);
        when(citizenService.isExist(citizen)).thenReturn(false);

        // When
        IdeaRating acceptedRating = service.acceptRating(citizen, idea, rating);
        assertNull(acceptedRating);

        when(citizenService.isExist(citizen)).thenReturn(true);
        when(ideaService.isExist(idea)).thenReturn(false);
        // When
        acceptedRating = service.acceptRating(citizen, idea, rating);
        assertNull(acceptedRating);

        when(citizenService.isExist(citizen)).thenReturn(true);
        when(ideaService.isExist(idea)).thenReturn(true);
        when(ideaRatingService.addRating(citizen, idea, rating)).thenReturn(ideaRating);
        // When
        acceptedRating = service.acceptRating(citizen, idea, rating);
        assertEquals(acceptedRating, ideaRating);
    }

    //Test scenario: Accept rating:
    // - Drop contender when 3 ratings are too low
    @Test
    public void shouldDropContenderWhenRatingTooLow() {
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
        Citizen citizen4 = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        service.getEligibleContenders().add(contender);
        Idea idea = new Idea(contender, "Idea1 details");
        contender.setIdeas(SetUtils.unmodifiableSet(idea));
        IdeaRating ideaRating1 = new IdeaRating(idea, citizen1, 1);
        IdeaRating ideaRating2 = new IdeaRating(idea, citizen2, 2);
        IdeaRating ideaRating3 = new IdeaRating(idea, citizen3, 3);
        IdeaRating ideaRating4 = new IdeaRating(idea, citizen4, 4);

        when(citizenService.isExist(citizen1)).thenReturn(true);
        when(ideaService.isExist(idea)).thenReturn(true);
        when(ideaRatingService.addRating(citizen1, idea, 1)).thenReturn(ideaRating1);

        when(ideaRatingService.getRatingsForIdea(idea)).thenReturn(
                SetUtils.unmodifiableSet(ideaRating1, ideaRating2, ideaRating3, ideaRating4));

        // When
        IdeaRating acceptedRating1 = service.acceptRating(citizen1, idea, 1);
        assertEquals(service.getEligibleContenders().size(), 0);
    }

    //Test scenario: Accept rating:
    // -Reinstate contender
    @Test
    public void shouldReinstateContenderWhenRatingIsHigh() {
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
        Citizen citizen4 = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea = new Idea(contender, "Idea1 details");
        contender.setIdeas(SetUtils.unmodifiableSet(idea));
        IdeaRating ideaRating1 = new IdeaRating(idea, citizen1, 6);
        IdeaRating ideaRating2 = new IdeaRating(idea, citizen2, 2);
        IdeaRating ideaRating3 = new IdeaRating(idea, citizen3, 3);
        IdeaRating ideaRating4 = new IdeaRating(idea, citizen4, 4);

        when(citizenService.isExist(citizen1)).thenReturn(true);
        when(ideaService.isExist(idea)).thenReturn(true);
        when(ideaRatingService.addRating(citizen1, idea, 6)).thenReturn(ideaRating1);

        when(ideaRatingService.getRatingsForIdea(idea)).thenReturn(
                SetUtils.unmodifiableSet(ideaRating1, ideaRating2, ideaRating3, ideaRating4));

        // When
        IdeaRating acceptedRating1 = service.acceptRating(citizen1, idea, 6);
        assertEquals(service.getEligibleContenders().size(), 1);
    }

    //Test scenario: Do election
    @Test
    public void shouldSelectHigestRatedContender() {
        int rating = 6;
        Citizen citizen1 = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        Citizen citizen2 = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();

        //Create contender 1 with 2 ideas and average rating of 5
        Contender contender1 = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea11 = new Idea(contender1, "Idea1 details");
        Idea idea12 = new Idea(contender1, "Idea2 details");
        contender1.addIdeas(idea11, idea12);

        service.getEligibleContenders().add(contender1);

        when(ideaRatingService.getAverageRating(idea11)).thenReturn(3.0);
        when(ideaRatingService.getAverageRating(idea12)).thenReturn(8.0);

        Contender contender2 = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea21 = new Idea(contender2, "Idea1 details");
        Idea idea22 = new Idea(contender2, "Idea2 details");
        Idea idea23 = new Idea(contender2, "Idea3 details");

        service.getEligibleContenders().add(contender2);
        contender2.addIdeas(idea21, idea22, idea23);

        when(ideaRatingService.getAverageRating(idea21)).thenReturn(1.0);
        when(ideaRatingService.getAverageRating(idea22)).thenReturn(3.0);
        when(ideaRatingService.getAverageRating(idea23)).thenReturn(5.0);

        //Determine that contender 2 is a winner
        //When
        Contender winner = service.runElection();

        assertEquals(contender1, winner);
    }

    private Contender createContenderWithIdeas(List<Citizen> citizens) {
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea = new Idea(contender, "Idea1 details");
        contender.setIdeas(SetUtils.unmodifiableSet(idea));
        IdeaRating ideaRating1 = new IdeaRating(idea, citizens.get(0), 6);
        IdeaRating ideaRating2 = new IdeaRating(idea, citizens.get(1), 2);
        IdeaRating ideaRating3 = new IdeaRating(idea, citizens.get(2), 3);
        IdeaRating ideaRating4 = new IdeaRating(idea, citizens.get(3), 4);
        return contender;
    }
}