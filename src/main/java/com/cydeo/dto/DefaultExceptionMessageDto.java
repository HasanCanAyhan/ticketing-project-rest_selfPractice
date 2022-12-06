package com.cydeo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//@Builder
// you can use it instead of creating multiple different constructors,
// it will give you methods to create object.
public class DefaultExceptionMessageDto { // if it is not default message, we use this

    private String message;
}