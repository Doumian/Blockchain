package com.example.Blockchain.DTOs;

import com.example.Blockchain.Entities.UserEntity;

public class UsersTransactionWrapperDTO {
    UserEntity UserAObject;
    UserEntity UserBObject;
    private Float value;


    // Getter Methods

    public UserEntity getUserA() {
        return UserAObject;
    }

    public UserEntity getUserB() {
        return UserBObject;
    }

    public Float getValue() {
        return value;
    }

    // Setter Methods

    public void setUserA(UserEntity userAObject) {
        this.UserAObject = userAObject;
    }

    public void setUserB(UserEntity userBObject) {
        this.UserBObject = userBObject;
    }

    public void setValue(Float value) {
        this.value = value;
    }

}