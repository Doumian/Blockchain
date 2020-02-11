package com.example.Blockchain.Services;

import com.example.Blockchain.Entities.BlockEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface BlockChainService {
    BlockEntity generateNewBlock(String hash);

    Integer getBlockchainSize();

    ArrayList<BlockEntity> getFullChainData();
}
