package com.store.pet.entities;

import com.store.pet.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@NoArgsConstructor
@Data
@Document(collection = "pets")
public class Pet {

    @Id
    private String id;

    @Field("name")
    @NotNull
    @NotEmpty
    private String name;

    @Field("color")
    @NotNull
    @NotEmpty
    private String color;

    @Field("type")
    @NotNull
    @NotEmpty
    private PetType type;

    @Field(targetType = FieldType.DATE_TIME)
    private Instant updatedAt;

    @Field(targetType = FieldType.DATE_TIME)
    private Instant createdAt;

    @NotNull
    private Parents parents;


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Valid
    public static class Parents {
        private String mother;
        private String father;
    }
}
