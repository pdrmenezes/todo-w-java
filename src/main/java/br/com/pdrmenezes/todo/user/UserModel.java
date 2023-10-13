package br.com.pdrmenezes.todo.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

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
  
  // if we want to map a name different than the property name inside the db we can use the @Column annotation to give it a new name
  // e.g.: @Column(name="alias")
  private String username;
  private String name;
  private String password;
  @CreationTimestamp
  private LocalDateTime createdAt;
  
}
