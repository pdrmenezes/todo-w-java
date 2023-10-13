package br.com.pdrmenezes.todo.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

// an interface creates a type of contract to convey the available class methods without revealing its actual implementation
// as JPA already has an interface with some methods to talk to the db, we can just extend it passing 2 parameters: the class its representing and the type of id
// to make them available without having to create each one individually
public interface IUserRepository extends JpaRepository<UserModel, UUID> {
  
}
