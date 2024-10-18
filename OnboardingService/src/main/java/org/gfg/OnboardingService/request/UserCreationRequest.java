package org.gfg.OnboardingService.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gfg.OnboardingService.model.User;
import org.gfg.OnboardingService.model.enums.UserIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {

     String name;
     String email;
     String mobileNo;
     String password;
     UserIdentifier userIdentifier;
     String userIdentifierValue;


    public User toUser(){
       User user = User.builder().name(this.name).email(this.email)
               .password(password).mobileNo(mobileNo).userIdentifier(this.userIdentifier)
               .userIdentifierValue(this.userIdentifierValue).build();

       return user;
    }

}
