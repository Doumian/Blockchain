package com.example.Blockchain.Controllers;

import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Services.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockchain")
public
class BlockchainController {

    @Autowired
    BlockChainService blockChainService;

    @GetMapping(value = "/newBlock")
    public BlockEntity
     generateNewBlock(@RequestParam String hash){
        return this.blockChainService.generateNewBlock(hash);
    }








}