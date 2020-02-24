package com.example.Blockchain.Services.Impl;

import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Services.BlockChainService;
import com.example.Blockchain.Singleton.BlockChainSingleton;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    BlockChainSingleton blockchainInstance = BlockChainSingleton.getInstance();

    @Override
    public BlockEntity generateNewBlock() {

        return blockchainInstance.createNewBlock();

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
        return blockchainInstance.getBlockchain().get(position);
    }


}
