package com.store.pet.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.pet.apis.Status;
import com.store.pet.entities.Pet;
import com.store.pet.enums.PetType;
import com.store.pet.repositories.PetRepository;
import com.store.pet.requests.PetRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetRepository petRepository;

    @Test
    public void PostNewPet_WithValidPayload_Should_Pass() throws Exception {

        // Convert the PetRequest to JSON
        String jsonRequest = objectMapper.writeValueAsString(buildPetRequest());

        // Perform the POST request
        ResultActions result = mockMvc.perform(post("/api/v1/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        result.andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value(containsString("saved")))
                .andExpect(jsonPath("$.data").isNotEmpty());

    }

    @Test
    public void UpdatePet_WithValidPayload_Should_Pass() throws Exception {
        final Pet pet = petRepository.save(pet());

        //build update request
        final PetRequest request = buildPetRequest();
        request.setName("Seun Smart");
        request.setId(pet.getId());

        // Convert the PetRequest to JSON
        String jsonRequest = objectMapper.writeValueAsString(request);

        // Perform the PUT request
        ResultActions result = mockMvc.perform(put("/api/v1/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        result.andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value(containsString("updated")))
                .andExpect(jsonPath("$.data").isNotEmpty());

    }

    @Test
    void test_Get_AllPets() throws Exception {
        final List<Pet> pets = pets();
        petRepository.saveAll(pets);

        mockMvc.perform(get("/api/v1/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Pets retrieved successfully"))
                .andExpect(jsonPath("$.data.content", hasSize(pets.size())))
                .andExpect(jsonPath("$.data.content[0].name").value(pets.get(0).getName()))
                .andExpect(jsonPath("$.data.totalElements").value(pets.size()));
    }

    @Test
    void test_Get_AllPets_WithEmptyResult() throws Exception {

        mockMvc.perform(get("/api/v1/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Pets retrieved successfully"));
    }

    @Test
    void test_GetPetById_WithValidID_Should_Pass() throws Exception {
        final Pet pet = petRepository.save(pet());
        ResultActions result = mockMvc.perform(get("/api/v1/pets/{id}", pet.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value(String.format("Pet with ID: %s retrieved successfully", pet.getId())))
                .andExpect(jsonPath("$.data").isNotEmpty());

        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        Status<Pet> petResponse = mapFromJson(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(pet.getId(), petResponse.getData().getId());
        Assertions.assertEquals(pet.getName(), petResponse.getData().getName());
        Assertions.assertEquals(pet.getType(), petResponse.getData().getType());
    }


    @Test
    void test_GetPetById_WithInValidID_Should_Fail() throws Exception {
        String id = "1ad1fccc-d279-46a0-8980-1d91afd6ba67";
        mockMvc.perform(get("/api/v1/pets/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value(String.format("Pet with ID: %s does not exists", id)))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    private <T> T mapFromJson(String json, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(json, typeReference);
    }

    private List<Pet> pets() {
        // Create and return a list of mock pets for testing
        List<Pet> mockPets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mockPets.add(pet());
        }
        return mockPets;
    }

    private PetRequest buildPetRequest() {
        final PetRequest request = new PetRequest();
        request.setColor("Black");
        request.setName("Seun Smart");
        request.setType(PetType.CAT);
        request.setParents(new PetRequest.Parents("af7c1fe6-d669-414e-b066-e9733f0de7a8", "08c71152-c552-42e7-b094-f510ff44e9cb"));
        return request;
    }

    private Pet pet() {
        Pet pet = new Pet();
        pet.setCreatedAt(Instant.now());
        pet.setUpdatedAt(Instant.now());
        pet.setName("Test Test");
        pet.setParents(new Pet.Parents("af7c1fe6-d669-414e-b066-e9733f0de7a8", "af7c1fe6-d669-414e-b066-e9733f0de7a8"));
        pet.setType(PetType.CAT);
        pet.setColor("Black");
        return pet;
    }
}