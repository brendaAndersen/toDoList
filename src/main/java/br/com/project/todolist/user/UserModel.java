package br.com.project.todolist.model.user;

@Data
@Entity(name="tb_users")
public class UserModel {
    private String username;

    private String name;

    private String password;
}