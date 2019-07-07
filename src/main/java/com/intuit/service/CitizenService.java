package com.intuit.service;

import com.intuit.model.Citizen;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CitizenService {
    // In real life that will be replaced by a proper repository (JPA, NoSQL)
    private Map<UUID, Citizen> citizens = new HashMap<>();

    public void addCitizen(Citizen citizen) {
        citizens.put(citizen.getId(), citizen);
    }

    public Citizen getById(UUID id) {
        return citizens.get(id);
    }

    boolean isExist(Citizen citizen) {
        return citizens.containsKey(citizen.getId());
    }
}
