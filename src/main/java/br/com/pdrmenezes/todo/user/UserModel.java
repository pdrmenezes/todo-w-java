package br.com.pdrmenezes.todo.user;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "app_users")
public class UserModel {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private String username;
  private String name;
  private String password;
  
}
