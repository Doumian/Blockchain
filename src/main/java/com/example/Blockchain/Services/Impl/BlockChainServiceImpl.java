package com.example.Blockchain.Services.Impl;

import com.example.Blockchain.Entities.BlockChainEntity;
import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Services.BlockChainService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    @Override
    public BlockEntity generateNewBlock(String hash) {

        if (BlockChainEntity.blockchain.isEmpty()) BlockChainEntity.createGenesisBlock();
        BlockEntity blockEntity = new BlockEntity(BlockChainEntity.blockchain.get(BlockChainEntity.blockchain.size()-1).hash);
        blockEntity.mineBlock(BlockChainEntity.difficulty);
        blockEntity.addTransaction(BlockChainEntity.walletA.sendFunds(BlockChainEntity.walletB.publicKey,1000f));
        BlockChainEntity.blockchain.add(blockEntity);
        if (BlockChainEntity.blockchain.size()>1) BlockChainEntity.isChainValid();
        return blockEntity;

    }

    @Override
    public Integer getBlockchainSize() {
        return BlockChainEntity.blockchain.size();
    }

    @Override
    public ArrayList<BlockEntity> getFullChainData() {
        return BlockChainEntity.blockchain;
    }

    @Override
    public BlockEntity getBlockData(Integer position) {
        return BlockChainEntity.blockchain.get(position);
    }


}
