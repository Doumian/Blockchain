package com.example.Blockchain.Entities;

public class UserEntity {

    private String name;
    private String lastName;
    private WalletEntity wallet;


    public UserEntity(String name, String lastName, WalletEntity wallet) {
        this.name = name;
        this.lastName = lastName;
        this.wallet = wallet;
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
