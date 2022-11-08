package tn.esprit.spring.dao.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.dao.entities.Role;
import tn.esprit.spring.dao.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  User findByVerificationCode(String verificationCode);

  User findByEmail(String email);

  User findByToken(String token);
  
 
	@Query("SELECT u FROM User u WHERE u.roles= ?1")
	List<User> findUsersByRole(Set<Role> roles);
}
