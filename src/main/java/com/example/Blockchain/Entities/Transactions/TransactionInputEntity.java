package com.example.Blockchain.Entities.Transactions;

public class TransactionInputEntity {
    public String transactionOutputId; //Reference to TransactionOutputs -> transactionId
    public TransactionOutputEntity UTXO; //Contains the Unspent transaction output

    public TransactionInputEntity(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }
}