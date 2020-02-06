package com.example.Blockchain.Services.Impl;

import com.example.Blockchain.DTOs.Block;
import com.example.Blockchain.Services.BlockChainService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class BlockChainServiceImpl implements BlockChainService {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();

    public static int difficulty = 5;

    @Override
    public Block generateNewBlock(String data, String hash) {

        if (blockchain.isEmpty()) createFirstBlock();
        Block block = new Block(data,blockchain.get(blockchain.size()-1).hash);
        blockchain.add(block);
        return block;

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    private void createFirstBlock(){
        Block firstBlock = new Block("First Block","0");
        blockchain.add(firstBlock);
    }
}
