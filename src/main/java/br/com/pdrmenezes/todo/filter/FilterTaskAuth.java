package br.com.pdrmenezes.todo.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pdrmenezes.todo.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // only validate user on '/tasks/create' path
    var servletPath = request.getServletPath();
    System.out.println(servletPath);
    if (servletPath.startsWith("/tasks")) {
      // get auth info, remove the "Basic" string and decode from base64 to byte array
      // then to string array (expected final decoded output 'user:password')
      var encodedRawAuthData = request.getHeader("Authorization");
      // encodedAuthData.substring(6);
      var encodedAuthData = encodedRawAuthData.substring("Basic".length()).trim();
      byte[] byteArrayAuthData = Base64.getDecoder().decode(encodedAuthData);
      var decodedAuthData = new String(byteArrayAuthData);

      var username = decodedAuthData.split(":")[0];
      var password = decodedAuthData.split(":")[1];

      // validate user
      var userExists = this.userRepository.findByUsername(username);
      if (userExists == null) {
        response.sendError(401);
      } else {
        // validate password
        var passwordVerification = BCrypt.verifyer().verify(password.toCharArray(), userExists.getPassword());
        if (passwordVerification.verified) {
          request.setAttribute("userId", userExists.getId());
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401);
        }
      }
    } else {
      filterChain.doFilter(request, response);
    }

  }

}
