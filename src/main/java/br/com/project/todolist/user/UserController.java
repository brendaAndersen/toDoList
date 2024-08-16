package br.com.project.todolist.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist");
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

}


/*
 * -> a controller é responsável pela configuração das rotas da aplicação. A annotations
 * de RestController é uma combinação das annotations @Controller e @ResponseBody, sendo a
 * @Controller para criar um Map do model object e encontrar uma view e o @RestController 
 * apenas retorna o objeto e os dados do objeto são gravados na resposta HTTP como JSON ou XML.
 * -> Response entity possibilita a manipulação dos erros, setando as mensagens e status code
 * -> O repository permite a instituição de casos de uso da aplicação, sendo ele intermeditado 
 * por uma interface, isto é, a estrutura do repositório em si.
 */