package org.gfg.TopUpService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.TxnStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String txnId;

    String sender;
    String receiver;
    double amount;
    String txnMessage;
    @Enumerated(EnumType.STRING)
    TxnStatus txnStatus;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;



}