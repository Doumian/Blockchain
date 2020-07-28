package com.example.Blockchain.Blockchain.Service;

import com.example.Blockchain.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Users.Entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface BlockChainService {
    BlockEntity generateNewBlock(UserEntity userA, UserEntity userB, Float value);

    Integer getBlockchainSize();

    ArrayList<BlockEntity> getFullChainData();

    BlockEntity getBlockData(Integer position);
}
