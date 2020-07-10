package com.example.Blockchain.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserEntity {

    private String name;

    private String lastName;
    @JsonIgnore
    private WalletEntity wallet;


    public UserEntity(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        this.wallet = new WalletEntity();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }
}
