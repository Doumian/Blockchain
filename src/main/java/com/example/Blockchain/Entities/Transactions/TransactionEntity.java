package com.example.Blockchain.Entities.Transactions;

import com.example.Blockchain.Singleton.BlockChainSingleton;
import com.example.Blockchain.Utils.StringUtils;

import java.security.*;
import java.util.ArrayList;

public class TransactionEntity {

    public String transactionId; // this is also the hash of the transaction.
    public PublicKey sender; // senders address/public key.
    public PublicKey reciepient; // Recipients address/public key.
    public float value;
    public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

    public ArrayList<TransactionInputEntity> inputs = new ArrayList<TransactionInputEntity>();
    public ArrayList<TransactionOutputEntity> outputs = new ArrayList<>();

    private static int sequence = 0; // a rough count of how many transactions have been generated.

    // Constructor:
    public TransactionEntity(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInputEntity> inputs) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    // This Calculates the transaction hash (which will be used as its Id)
    private String calulateHash() {
        sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
        return StringUtils.applySha512(
                StringUtils.getStringFromKey(sender) +
                        StringUtils.getStringFromKey(reciepient) +
                        Float.toString(value) + sequence
        );
    }

    //Returns true if new transaction could be created.
    public boolean processTransaction() {

        if(!verifiySignature()) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        //gather transaction inputs (Make sure they are unspent):
        for(TransactionInputEntity i : inputs) {
            i.UTXO = BlockChainSingleton.getUTXOs().get(i.transactionOutputId);
        }

        //check if transaction is valid:
        if(getInputsValue() < BlockChainSingleton.getMinimumTransaction()) {
            System.out.println("#Transaction Inputs to small: " + getInputsValue());
            return false;
        }

        //generate transaction outputs:
        float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
        transactionId = calulateHash();
        outputs.add(new TransactionOutputEntity( this.reciepient, value,transactionId)); //send value to recipient
        outputs.add(new TransactionOutputEntity( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender

        //add outputs to Unspent list
        for(TransactionOutputEntity o : outputs) {
            BlockChainSingleton.getUTXOs().put(o.id , o);
        }

        //remove transaction inputs from UTXO lists as spent:
        for(TransactionInputEntity i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it
            BlockChainSingleton.getUTXOs().remove(i.UTXO.id);
        }

        return true;
    }

    //returns sum of inputs(UTXOs) values
    public float getInputsValue() {
        float total = 0;
        for(TransactionInputEntity i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it
            total += i.UTXO.value;
        }
        return total;
    }

    //returns sum of outputs:
    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutputEntity o : outputs) {
            total += o.value;
        }
        return total;
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(reciepient) + Float.toString(value)	;
        signature = StringUtils.applyECDSASig(privateKey,data);
    }
    //Verifies the data we signed hasnt been tampered with
    public boolean verifiySignature() {
        String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(reciepient) + Float.toString(value)	;
        return StringUtils.verifyECDSASig(sender, data, signature);
    }
}