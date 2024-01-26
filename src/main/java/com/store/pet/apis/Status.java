package com.store.pet.apis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Status<T> {
    private boolean status;
    private String message;
    private T data;
}
