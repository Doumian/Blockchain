package com.example.Blockchain.Users.Entities;

import com.example.Blockchain.Blockchain.Entities.Wallet.WalletEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "users")
@JsonPropertyOrder({"id", "name", "lastName"})
public class UserEntity {

    @Id
    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
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
