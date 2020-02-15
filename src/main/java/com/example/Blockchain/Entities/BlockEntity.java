package com.example.Blockchain.Entities;

import com.example.Blockchain.Entities.Transactions.TransactionEntity;
import com.example.Blockchain.Utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;

public class BlockEntity {

    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<TransactionEntity> transactions = new ArrayList<TransactionEntity>(); //our data will be a simple message.
    public long timeStamp; //as number of milliseconds since 1/1/1970.
    public int nonce;

    //Block Constructor.
    public BlockEntity(String previousHash ) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }

    //Calculate new hash based on blocks contents
    public String calculateHash() {
        String calculatedhash = StringUtils.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        merkleRoot
        );
        return calculatedhash;
    }

    //Increases nonce value until hash target is reached.
    public void mineBlock(int difficulty) {
        merkleRoot = StringUtils.getMerkleRoot(transactions);
        String target = StringUtils.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    //Add transactions to this block
    public boolean addTransaction(TransactionEntity transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((previousHash != "0")) {
            if((!transaction.processTransaction())) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }

}