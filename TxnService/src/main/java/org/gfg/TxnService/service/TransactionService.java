package org.gfg.TxnService.service;

import org.example.CommonConstants;
import org.example.TxnStatus;
import org.gfg.TxnService.model.Transaction;
import org.gfg.TxnService.repository.TransactionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class TransactionService {


    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;


    @Autowired
    TransactionRepository transactionRepository;


    public String initiateTransaction(String sender, String receiver, String amount, String purpose){

        String txnId = UUID.randomUUID().toString();

        TxnStatus txnStatus = TxnStatus.INITIATED;

        Transaction transaction = Transaction.builder().txnId(txnId)
                .sender(sender).receiver(receiver).txnMessage(purpose)
                .amount(Double.parseDouble(amount)).txnStatus(txnStatus).build()
                ;

        transactionRepository.save(transaction);

        System.out.println("Transaction saved with initiated status");

        // sending the data to kafka

        JSONObject txnObject = new JSONObject();
        txnObject.put(CommonConstants.SENDER, transaction.getSender());
        txnObject.put(CommonConstants.RECEIVER,transaction.getReceiver());
        txnObject.put(CommonConstants.TXN_AMOUNT,transaction.getAmount());
        txnObject.put(CommonConstants.TXN_ID, transaction.getTxnId());

        kafkaTemplate.send(CommonConstants.TRANSACTIONAL_TOPIC,txnObject.toString());

        System.out.println("Transaction data sent to kafka");

        return txnId;

    }


}
