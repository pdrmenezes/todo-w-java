package br.com.pdrmenezes.todo.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


// an interface creates a type of contract to convey the available class methods without revealing its actual implementation
// as JPA already has an interface with some methods to talk to the db, we can just extend it passing 2 parameters: the class its representing and the type of id
// to make them available without having to create each one individually
public interface IUserRepository extends JpaRepository<UserModel, UUID> {
  // to validate and correctly handle the 'already existing username' we need a method that's not available on JpaRepository, so we'll implement it
  // spring already has autocomplete for functions that we can pass paramaters to such as findByProperty_name
  // as the username is unique, we can change the autocompleted function return type from 'List<UserModel>' to UserModel
  UserModel findByUsername(String username);
}
