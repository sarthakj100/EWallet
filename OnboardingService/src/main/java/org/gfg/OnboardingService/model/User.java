package org.gfg.OnboardingService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gfg.OnboardingService.model.enums.UserIdentifier;
import org.gfg.OnboardingService.model.enums.UserStatus;
import org.gfg.OnboardingService.model.enums.UserType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false, unique = true)
    String mobileNo;
    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    UserIdentifier userIdentifier;

    @Column(nullable = false, unique = true)
    String userIdentifierValue;

    @Enumerated(EnumType.STRING)
    UserType userType;

    @Enumerated(EnumType.STRING)
    UserStatus userStatus;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
