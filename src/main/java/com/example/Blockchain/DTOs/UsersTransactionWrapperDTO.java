package com.example.Blockchain.DTOs;

import com.example.Blockchain.Entities.UserEntity;

public class UsersTransactionWrapperDTO {
    UserEntity UserA;
    UserEntity UserB;
    private Float value;

    // Getter Methods

    public UserEntity getUserA() {
        return UserA;
    }

    public UserEntity getUserB() {
        return UserB;
    }

    public Float getValue() {
        return value;
    }

    // Setter Methods

    public void setUserA(UserEntity userAObject) {
        this.UserA = userAObject;
    }

    public void setUserB(UserEntity userBObject) {
        this.UserB = userBObject;
    }

    public void setValue(Float value) {
        this.value = value;
    }

}