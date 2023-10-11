package br.com.pdrmenezes.todo.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  @PostMapping("/create")
  public String create(@RequestBody UserModel userModel){
    System.out.println(userModel);
    return userModel.username;
    }
}