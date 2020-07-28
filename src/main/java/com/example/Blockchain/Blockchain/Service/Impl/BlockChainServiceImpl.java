package com.example.Blockchain.Blockchain.Service.Impl;

import com.example.Blockchain.Blockchain.BlockChainSingleton;
import com.example.Blockchain.Blockchain.Exceptions.BlockOutOfBoundsException;
import com.example.Blockchain.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Blockchain.Service.BlockChainService;
import com.example.Blockchain.Users.Entities.UserEntity;
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
