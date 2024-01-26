package com.store.pet.services;

import com.store.pet.entities.Pet;
import com.store.pet.exceptions.PetNotFoundException;
import com.store.pet.repositories.PetRepository;
import com.store.pet.requests.PetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet create(PetRequest petRequest) {
        Pet pet = buildPetFromRequest(petRequest);
        return this.petRepository.save(pet);
    }

    private Pet buildPetFromRequest(PetRequest petRequest) {
        Pet pet = new Pet();
        pet.setCreatedAt(Instant.now());
        pet.setUpdatedAt(Instant.now());

        updatePetAttributes(petRequest, pet);

        return pet;
    }

    private void updatePetAttributes(PetRequest petRequest, Pet pet) {
        // Set values only if they are not null
        setIfNotNull(petRequest.getName(), pet::setName);
        setIfNotNull(petRequest.getParents(), parents -> pet.setParents(buildParentsFromRequest(parents)));
        setIfNotNull(petRequest.getType(), pet::setType);
        setIfNotNull(petRequest.getColor(), pet::setColor);
        setIfNotNull(petRequest.getId(), pet::setId);
    }

    private Pet.Parents buildParentsFromRequest(PetRequest.Parents parentsRequest) {
        Pet.Parents parents = new Pet.Parents();

        // Set values only if they are not null
        setIfNotNull(parentsRequest.getMother(), parents::setMother);
        setIfNotNull(parentsRequest.getFather(), parents::setFather);

        return parents;
    }

    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (Objects.nonNull(value)) {
            setter.accept(value);
        }
    }

    public Pet update(PetRequest petRequest) throws PetNotFoundException {
        String id = petRequest.getId();

        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("ID cannot be null, while performing an update");
        }

        final Pet pet = this.petRepository.findById(id).orElseThrow(
                () -> new PetNotFoundException(String.format("Pet with ID: %s does not exists", id))
        );

        pet.setUpdatedAt(Instant.now());

        updatePetAttributes(petRequest, pet);

        return this.petRepository.save(pet);
    }

    public Page<Pet> getAllPets(Pageable pageable) {
        return this.petRepository.findAll(pageable);
    }

    public Pet getPetById(String id) throws PetNotFoundException {
        return this.petRepository.findById(id).orElseThrow(
                () -> new PetNotFoundException(String.format("Pet with ID: %s does not exists", id))
        );
    }
}
