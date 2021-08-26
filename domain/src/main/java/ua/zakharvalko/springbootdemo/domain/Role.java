package ua.zakharvalko.springbootdemo.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Role {

    private Integer id;

    private String title;

    private List<User> users;

    public Role() {
    }

}
