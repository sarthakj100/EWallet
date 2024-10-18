package org.gfg.OnboardingService.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.gfg.OnboardingService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

     User findByMobileNo(String mobile);
}
