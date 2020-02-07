package com.example.Blockchain.Services;

import com.example.Blockchain.Entities.BlockEntity;
import org.springframework.stereotype.Service;

@Service
public interface BlockChainService {
    BlockEntity generateNewBlock(String data, String hash);
}
