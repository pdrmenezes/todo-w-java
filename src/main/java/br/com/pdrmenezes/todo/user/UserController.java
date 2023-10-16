package br.com.pdrmenezes.todo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/user")
public class UserController {

  // lets spring intantiate the repository when needed instead of initializing it
  // manually
  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/create")
  public ResponseEntity create(@RequestBody UserModel userModel) {
    // with the findByUsername method created in the interface we can access it on
    // the controller
    var user = this.userRepository.findByUsername(userModel.getUsername());
    if (user != null) {
      // we can pass a status code number directly or if we're not sure, we can user
      // HttpStatus to select the correct status code
      // and we can pass a body
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
    }

    var hashedPassword = BCrypt.withDefaults().hashToString((12), userModel.getPassword().toCharArray());
    userModel.setPassword(hashedPassword);

    // now with the userRepository extending the jpa interface, we can access all of
    // its methods (count, delete, find, findById, save...)
    // this.userRepository.
    var createdUser = this.userRepository.save(userModel);
    return ResponseEntity.status(201).body(createdUser);
    // System.out.println(userModel);
    // return "new username: " + userModel.getUsername();
  }
}
