package com.store.pet.requests;

import com.store.pet.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PetRequest {

    private String id;
    private String name;
    private PetType type;
    private String color;
    private Parents parents;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Parents {
        private String mother;
        private String father;
    }
}
