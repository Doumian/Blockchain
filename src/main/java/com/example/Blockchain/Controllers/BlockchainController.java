package com.example.Blockchain.Controllers;

import com.example.Blockchain.DTOs.Block;
import com.example.Blockchain.Services.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/blockchain")
public
class BlockchainController {

    @Autowired
    BlockChainService blockChainService;

    @GetMapping(value = "/newBlock")
    public Block
     generateNewBlock(@RequestParam String data, String hash){
        return this.blockChainService.generateNewBlock(data,hash);
    }








}