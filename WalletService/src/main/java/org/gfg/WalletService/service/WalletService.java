package org.gfg.WalletService.service;

import org.gfg.WalletService.Repository.WalletRepository;
import org.gfg.WalletService.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {


    @Autowired
    WalletRepository walletRepository;


    public void processTransaction(String sender, String receiver, String amount,String txnId){

        String txnMessage ;
        String txnStatus ;

        // checking if sender wallet exists
        Wallet senderWallet = walletRepository.findByMobileNo(sender);
        // checking if receiver wallet exists
        Wallet receiverWallet = walletRepository.findByMobileNo(receiver);

        if (senderWallet==null){
            txnMessage = "Sender wallet does not exist";
            txnStatus = "FAILED";
        }else if (receiverWallet==null){
            txnMessage = "Receiver wallet does not exist";
            txnStatus = "FAILED";
        }else {
          boolean isTransactionSuccess =  completeTransactionOperation(sender,receiver,amount);
          if (isTransactionSuccess){
              System.out.println("Wallet balance updated successfully");
              txnMessage = "Transaction is successfull";
              txnStatus = "SUCCESS";
          }else {
              txnMessage = "Transaction couldn't be completed";
              txnStatus = "FAILED";
          }


        }

    }

    @Transactional
    public boolean completeTransactionOperation(String sender, String receiver, String amount){
        boolean isTransactionCompleted = false;
        try {
            walletRepository.update(sender,-Double.parseDouble(amount));
            walletRepository.update(receiver,Double.parseDouble(amount));
            isTransactionCompleted = true;
        }
        catch (Exception exception){
            isTransactionCompleted = false;
        }
        return isTransactionCompleted;
    }

    public void processTopUP(String sender, String amount,String txnId){

        String txnMessage ;
        String txnStatus ;

        // checking if sender wallet exists
        Wallet senderWallet = walletRepository.findByMobileNo(sender);

        if (senderWallet==null){
            txnMessage = "Sender wallet does not exist";
            txnStatus = "FAILED";
        }else {
            walletRepository.update(sender,Double.parseDouble(amount));
                txnMessage = "Transaction is successfull";
                txnStatus = "SUCCESS";
        }

    }
}
