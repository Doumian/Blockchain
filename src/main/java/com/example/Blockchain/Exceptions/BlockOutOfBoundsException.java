package com.example.Blockchain.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BlockOutOfBoundsException extends IndexOutOfBoundsException {

    public BlockOutOfBoundsException(String message) {
        super(message);
    }

}
