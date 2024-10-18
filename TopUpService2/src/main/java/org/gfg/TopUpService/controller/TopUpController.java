package org.gfg.TopUpService.controller;

import org.gfg.TopUpService.service.TopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topup-service")
public class TopUpController {

    @Autowired
    TopUpService topUpService;

    @PostMapping("/initiate/topup")
    public String initiateTransaction(@RequestParam("amount") String amount){

        String sender = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Sender: "+sender);
        return topUpService.initiateTopUp(sender,amount);

    }

}
