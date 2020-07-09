package com.example.Blockchain.Controllers;

import com.example.Blockchain.DTOs.UsersTransactionWrapperDTO;
import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Services.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/blockchain")
public
class BlockchainController {

    @Autowired
    BlockChainService blockChainService;

    @PostMapping(value = "/newBlock")
    public BlockEntity
     generateNewBlock(@RequestBody UsersTransactionWrapperDTO wrapper){
        return this.blockChainService.generateNewBlock(wrapper.getUserA(), wrapper.getUserB(), wrapper.getValue());
    }

    @GetMapping(value = "/size")
    public Integer getBlockchainSize(){
        return this.blockChainService.getBlockchainSize();
    }

    @GetMapping(value = "/fullChainData")
    public @ResponseBody ArrayList<BlockEntity> getFullChainData(){
        return this.blockChainService.getFullChainData();
    }

    @GetMapping(value = "/getBlockData")
    public BlockEntity getBlockData(Integer position) {

           return blockChainService.getBlockData(position);

    }








}