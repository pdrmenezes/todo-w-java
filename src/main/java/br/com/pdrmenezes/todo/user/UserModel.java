package br.com.pdrmenezes.todo.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
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

  // if we want to map a name different than the property name inside the db we
  // can use the @Column annotation to give it a new name
  // e.g.: @Column(name="alias")
  // the "unique" makes it so it errors when we try to create another user with an
  // already existing username
  @Column(unique = true)
  private String username;
  private String name;
  private String password;
  @CreationTimestamp
  // because of the property's capital letter, the db will convert it's column
  // name automatically to 'created_at'
  // for consistency's sake we could already create it as 'created_at' in the
  // model but it's against java's property naming conventions(?)
  private LocalDateTime createdAt;

}
