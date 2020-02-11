package com.example.Blockchain.Entities;

import com.example.Blockchain.Entities.Transactions.TransactionEntity;
import com.example.Blockchain.Entities.Transactions.TransactionInputEntity;
import com.example.Blockchain.Entities.Transactions.TransactionOutputEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockChainEntity {

    public static ArrayList<BlockEntity> blockchain = new ArrayList<BlockEntity>();
    public static HashMap<String, TransactionOutputEntity> UTXOs = new HashMap<String,TransactionOutputEntity>();

    public static int difficulty = 3;
    public static float minimumTransaction = 0.1f;
    //public static WalletEntity walletA;
    //public static WalletEntity walletB;
    public static TransactionEntity genesisTransaction;

    public static Boolean isChainValid() {
        BlockEntity currentBlock;
        BlockEntity previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String, TransactionOutputEntity> tempUTXOs = new HashMap<String,TransactionOutputEntity>(); //a temporary working list of unspent transactions at a given block state.
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

    public static void createFirstBlock(){
        BlockEntity firstBlockEntity = new BlockEntity("0");
        blockchain.add(firstBlockEntity);
    }
}
