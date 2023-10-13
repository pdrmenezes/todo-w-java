package br.com.pdrmenezes.todo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  
  // lets spring intantiate the repository when needed instead of initializing it manually
  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/create")
  public UserModel create(@RequestBody UserModel userModel){
    // now with the userRepository extending the jpa interface, we can access all of its methods (count, delete, find, findById, save...)
    // this.userRepository.
    var createdUser = this.userRepository.save(userModel);
    return createdUser;
    // System.out.println(userModel);
    // return "new username: " + userModel.getUsername();
    }
}
