package br.com.pdrmenezes.todo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
  
  @GetMapping("/hello")
  public String helloJava(){
    return "Hello Java";
  }
  
}
