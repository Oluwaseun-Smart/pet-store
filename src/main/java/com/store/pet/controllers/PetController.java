package com.store.pet.controllers;

import com.store.pet.apis.Status;
import com.store.pet.entities.Pet;
import com.store.pet.requests.PetRequest;
import com.store.pet.services.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<Status<Object>> create(@RequestBody PetRequest request) {
        try {
            final Pet pet = this.petService.create(request);

            final Status<Object> petResponse = Status.builder()
                    .message(String.format("Pet saved with ID: %s successfully!!!", pet.getId()))
                    .status(true)
                    .data(pet)
                    .build();

            return new ResponseEntity<>(petResponse, HttpStatus.OK);
        } catch (Exception e) {
            final Status<Object> error = Status.builder()
                    .message(e.getMessage())
                    .status(false)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Status<Object>> update(@RequestBody PetRequest request) {
        try {
            final Pet pet = this.petService.update(request);

            final Status<Object> petResponse = Status.builder()
                    .message(String.format("Pet updated with ID: %s successfully!!!", pet.getId()))
                    .status(true)
                    .data(pet)
                    .build();

            return new ResponseEntity<>(petResponse, HttpStatus.OK);
        } catch (Exception e) {
            final Status<Object> error = Status.builder()
                    .message(e.getMessage())
                    .status(false)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Status<Object>> pet(Pageable pageable) {
        try {
            final Page<Pet> pets = this.petService.getAllPets(pageable);

            final Status<Object> petsResponse = Status.builder()
                    .message("Pets retrieved successfully")
                    .status(true)
                    .data(pets)
                    .build();

            return new ResponseEntity<>(petsResponse, HttpStatus.OK);
        } catch (Exception e) {
            final Status<Object> error = Status.builder()
                    .message(e.getMessage())
                    .status(false)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Status<Object>> pet(@PathVariable String id) {
        try {
            final Pet pet = this.petService.getPetById(id);

            final Status<Object> petResponse = Status.builder()
                    .message(String.format("Pet with ID: %s retrieved successfully", pet.getId()))
                    .status(true)
                    .data(pet)
                    .build();

            return new ResponseEntity<>(petResponse, HttpStatus.OK);
        } catch (Exception e) {
            final Status<Object> error = Status.builder()
                    .message(e.getMessage())
                    .status(false)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Status<Object>> deletePetById(@PathVariable String id) {
        try {
            this.petService.deletePetById(id);

            final Status<Object> petResponse = Status.builder()
                    .message(String.format("Pet with ID: %s deleted successfully", id))
                    .status(true)
                    .build();

            return new ResponseEntity<>(petResponse, HttpStatus.OK);
        } catch (Exception e) {
            final Status<Object> error = Status.builder()
                    .message(e.getMessage())
                    .status(false)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
