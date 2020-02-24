package com.example.Blockchain.Services;

import com.example.Blockchain.Entities.BlockEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface BlockChainService {
    BlockEntity generateNewBlock();

    Integer getBlockchainSize();

    ArrayList<BlockEntity> getFullChainData();

    BlockEntity getBlockData(Integer position);
}
