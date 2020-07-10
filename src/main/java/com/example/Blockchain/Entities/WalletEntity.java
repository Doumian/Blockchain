package com.example.Blockchain.Entities;

import com.example.Blockchain.Entities.Transactions.TransactionEntity;
import com.example.Blockchain.Entities.Transactions.TransactionInputEntity;
import com.example.Blockchain.Entities.Transactions.TransactionOutputEntity;
import com.example.Blockchain.Singleton.BlockChainSingleton;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WalletEntity {

    public PrivateKey privateKey;
    public PublicKey publicKey;

    public HashMap<String,TransactionOutputEntity> UTXOs = new HashMap<String,TransactionOutputEntity>(); //only UTXOs owned by this wallet.

    public WalletEntity(){
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    //returns balance and stores the UTXO's owned by this wallet in this.UTXOs
    public float getBalance() {
        float total = 0;
        for (Map.Entry<String, TransactionOutputEntity> item: BlockChainSingleton.getUTXOs().entrySet()){
            TransactionOutputEntity UTXO = item.getValue();
            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
                UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
                total += UTXO.value ;
            }
        }
        return total;
    }
    //Generates and returns a new transaction from this wallet.
    public TransactionEntity sendFunds(PublicKey _recipient, float value ) {
        if(getBalance() < value) { //gather balance and check funds.
            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }
        //create array list of inputs
        ArrayList<TransactionInputEntity> inputs = new ArrayList<>();

        float total = 0;
        for (Map.Entry<String, TransactionOutputEntity> item: UTXOs.entrySet()){
            TransactionOutputEntity UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInputEntity(UTXO.id));
            if(total > value) break;
        }

        TransactionEntity newTransaction = new TransactionEntity(publicKey, _recipient , value, inputs);
        newTransaction.generateSignature(privateKey);

        for(TransactionInputEntity input: inputs){
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }

}



