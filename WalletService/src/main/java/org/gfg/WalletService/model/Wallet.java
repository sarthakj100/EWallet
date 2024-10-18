package org.gfg.WalletService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.UserIdentifier;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "wallet")
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false, unique = true)
    String mobileNo;

    double balance;

    @Enumerated(EnumType.STRING)
    UserIdentifier userIdentifier;

    String userIdentifierValue;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;


}
