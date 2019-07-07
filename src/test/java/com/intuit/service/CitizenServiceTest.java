package com.intuit.service;

import com.intuit.model.Citizen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CitizenServiceTest {

    @InjectMocks
    CitizenService service;

    @Test
    // I know that test is doing too much -
    // but the time constraint is really harsh
    public void shouldStoreAndRetrieveCitizen() {
        Citizen citizen = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        service.addCitizen(citizen);

        boolean isExists = service.isExist(citizen);
        assertTrue(isExists);
        Citizen byId = service.getById(citizen.getId());
        assertEquals(citizen, byId);
    }

    @Test
    public void shoudNotReturnNonExistingCitizen(){
        Citizen citizen = Citizen.builder()
                .id(randomUUID())
                .name("John Smith")
                .build();
        boolean isExists = service.isExist(citizen);
        assertFalse(isExists);
    }
}
