package com.example.Blockchain.Singleton;

import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Entities.Transactions.TransactionEntity;
import com.example.Blockchain.Entities.Transactions.TransactionInputEntity;
import com.example.Blockchain.Entities.Transactions.TransactionOutputEntity;
import com.example.Blockchain.Entities.WalletEntity;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockChainSingleton {

    private static BlockChainSingleton blockChainSingleton;
    private static  ArrayList<BlockEntity> blockchain = new ArrayList<>();
    private static  HashMap<String, TransactionOutputEntity> UTXOs = new HashMap<>();

    private static  int difficulty = 3;
    private static  float minimumTransaction = 0.1f;
    private static  WalletEntity walletA;
    private static  WalletEntity walletB;
    private static  TransactionEntity genesisTransaction;

    private BlockChainSingleton() {
        createGenesisBlock();
    }

    // Only one thread can execute this at a time
    public static BlockChainSingleton getInstance()
    {
        if (blockChainSingleton==null)
            blockChainSingleton = new BlockChainSingleton();
        return blockChainSingleton;
    }

    public static BlockChainSingleton getBlockChainSingleton() {
        return blockChainSingleton;
    }

    public static HashMap<String, TransactionOutputEntity> getUTXOs() {
        return UTXOs;
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public static float getMinimumTransaction() {
        return minimumTransaction;
    }

    public static WalletEntity getWalletA() {
        return walletA;
    }

    public static WalletEntity getWalletB() {
        return walletB;
    }

    public ArrayList<BlockEntity> getBlockchain() {
        return blockchain;
    }
    public synchronized BlockEntity createNewBlock(){
        BlockEntity blockEntity = new BlockEntity(blockchain.get(blockchain.size()-1).hash);
        blockEntity.mineBlock(difficulty);
        blockEntity.addTransaction(walletA.sendFunds(walletB.publicKey,1000f));
        blockchain.add(blockEntity);
        if (blockchain.size()>1) isChainValid();
        return blockEntity;
    }
    public static void createGenesisBlock(){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
        walletA = new WalletEntity();
        walletB = new WalletEntity();
        BlockEntity genesisBlock = new BlockEntity("0");
        WalletEntity coinbase = new WalletEntity();
        genesisTransaction = new TransactionEntity(coinbase.publicKey, walletA.publicKey, 100f, null);
        genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction
        genesisTransaction.transactionId = "0"; //manually set the transaction id
        genesisTransaction.outputs.add(new TransactionOutputEntity(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
        UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.
        genesisBlock.addTransaction(genesisTransaction);
        blockchain.add(genesisBlock);
    }
    public Boolean isChainValid() {
        BlockEntity currentBlock;
        BlockEntity previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String, TransactionOutputEntity> tempUTXOs = new HashMap<>(); //a temporary working list of unspent transactions at a given block state.
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {

            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("#Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("#Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("#This block hasn't been mined");
                return false;
            }

            //loop thru blockchains transactions:
            TransactionOutputEntity tempOutput;
            for(int t=0; t <currentBlock.transactions.size(); t++) {
                TransactionEntity currentTransaction = currentBlock.transactions.get(t);

                if(!currentTransaction.verifiySignature()) {
                    System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                    return false;
                }
                if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
                    System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
                    return false;
                }

                for(TransactionInputEntity input: currentTransaction.inputs) {
                    tempOutput = tempUTXOs.get(input.transactionOutputId);

                    if(tempOutput == null) {
                        System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
                        return false;
                    }

                    if(input.UTXO.value != tempOutput.value) {
                        System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
                        return false;
                    }

                    tempUTXOs.remove(input.transactionOutputId);
                }

                for(TransactionOutputEntity output: currentTransaction.outputs) {
                    tempUTXOs.put(output.id, output);
                }

                if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
                    System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
                    return false;
                }
                if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
                    System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
                    return false;
                }

            }

        }
        System.out.println("Blockchain is valid");
        return true;
    }
}