package com.intuit.service;

import com.intuit.model.Citizen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CitizenServiceTest {

    @InjectMocks
    CitizenService service;

    @Test
    public void shouldStoreAndRetrieveCitizen() {
        Citizen citizen = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        service.addCitizen(citizen);

        Citizen byId = service.getById(citizen.getId());
        assertEquals(citizen, byId);
    }



}
