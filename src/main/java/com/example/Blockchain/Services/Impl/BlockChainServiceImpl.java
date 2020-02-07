package com.example.Blockchain.Services.Impl;

import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Services.BlockChainService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class BlockChainServiceImpl implements BlockChainService {

    public static ArrayList<BlockEntity> blockchain = new ArrayList<BlockEntity>();

    public static int difficulty = 5;

    @Override
    public BlockEntity generateNewBlock(String data, String hash) {

        if (blockchain.isEmpty()) createFirstBlock();
        BlockEntity blockEntity = new BlockEntity(data,blockchain.get(blockchain.size()-1).hash);
        blockchain.add(blockEntity);
        return blockEntity;

    }

    public static Boolean isChainValid() {
        BlockEntity currentBlockEntity;
        BlockEntity previousBlockEntity;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlockEntity = blockchain.get(i);
            previousBlockEntity = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlockEntity.hash.equals(currentBlockEntity.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlockEntity.hash.equals(currentBlockEntity.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlockEntity.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    private void createFirstBlock(){
        BlockEntity firstBlockEntity = new BlockEntity("First Block","0");
        blockchain.add(firstBlockEntity);
    }
}
