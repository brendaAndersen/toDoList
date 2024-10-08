package br.com.project.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.project.todolist.user.IUserRepository;
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
                var servletPath = request.getServletPath();
                if(servletPath.equals("/tasks/")){
                    
                    var authorization = request.getHeader("Authorization");
                    System.out.println(authorization);
    
                    var authEncoded = authorization.substring("Basic".length()).trim();
    
                    byte[] authDecode = Base64.getDecoder().decode(authEncoded);
    
                    var authString = new String(authDecode);
                    
                    String[] credentials = authString.split(":");
                    
                    String username = credentials[0];
                    String password = credentials[1];
    
                    var user = this.userRepository.findByUsername(username);
    
                    if(user == null){
                        response.sendError(401);
                    } else {
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    
                        if(passwordVerify.verified){
                            request.setAttribute("idUser", user.getId());
                            filterChain.doFilter(request, response);
                        } else {
                            response.sendError(401);
                        }
                    }
                    filterChain.doFilter(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            }

    // @Override
    // public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    //         throws IOException, ServletException {
    //          System.out.println("filter");
    //          chain.doFilter(request, response);
    //         }

}

/*
servlet é a base para qualquer framework web java. 
O servlet traz um server request
*/