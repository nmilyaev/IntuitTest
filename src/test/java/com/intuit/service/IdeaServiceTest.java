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
public class IdeaServiceTest {

    @InjectMocks
    IdeaService service;

    @Test
    public void shouldAddIdea() {
        Contender contender = new Contender(Citizen.builder()
                .id(randomUUID())
                .name("John Doe")
                .build());
        Idea idea = new Idea(contender, "Idea1 details");

        // When
        service.addIdea(idea);

        assertTrue(service.isExist(idea));
    }
}
