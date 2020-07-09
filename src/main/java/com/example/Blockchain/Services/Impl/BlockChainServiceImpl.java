package com.example.Blockchain.Services.Impl;

import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Entities.UserEntity;
import com.example.Blockchain.Exceptions.BlockOutOfBoundsException;
import com.example.Blockchain.Services.BlockChainService;
import com.example.Blockchain.Singleton.BlockChainSingleton;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    BlockChainSingleton blockchainInstance = BlockChainSingleton.getInstance();

    @Override
    public BlockEntity generateNewBlock(UserEntity userA, UserEntity userB, Float value) {

        return blockchainInstance.createNewBlock(userA, userB, value);

    }

    @Override
    public Integer getBlockchainSize() {
        return blockchainInstance.getBlockchain().size();
    }

    @Override
    public ArrayList<BlockEntity> getFullChainData() {
        return blockchainInstance.getBlockchain();
    }

    @Override
    public BlockEntity getBlockData(Integer position) {
        try{
            return blockchainInstance.getBlockchain().get(position);
        }
        catch (IndexOutOfBoundsException ex){
            throw new BlockOutOfBoundsException("There is no existing block in position: " + position);
        }
      }


}
