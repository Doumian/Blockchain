package com.example.Blockchain.Services.Impl;

import com.example.Blockchain.Entities.BlockChainEntity;
import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Entities.Transactions.TransactionEntity;
import com.example.Blockchain.Entities.Transactions.TransactionInputEntity;
import com.example.Blockchain.Entities.Transactions.TransactionOutputEntity;
import com.example.Blockchain.Entities.WalletEntity;
import com.example.Blockchain.Services.BlockChainService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    @Override
    public BlockEntity generateNewBlock(String hash) {

        if (BlockChainEntity.blockchain.isEmpty()) BlockChainEntity.createFirstBlock();
        BlockEntity blockEntity = new BlockEntity(BlockChainEntity.blockchain.get(BlockChainEntity.blockchain.size()-1).hash);
        blockEntity.mineBlock(BlockChainEntity.difficulty);
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


}
