package com.example.Blockchain.Services;

import com.example.Blockchain.DTOs.Block;
import org.springframework.stereotype.Service;

@Service
public interface BlockChainService {
    Block generateNewBlock(String data, String hash);
}
