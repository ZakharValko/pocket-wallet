package ua.zakharvalko.springbootdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {

    private Integer id;

    private String first_name;

    private String second_name;

    private String username;

    private String password;

    private List<Account> accounts;

    private Integer role_id;

    public User() {
    }

}
