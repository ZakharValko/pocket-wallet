package ua.zakharvalko.springbootdemo.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class Account {

    private Integer id;

    private String number;

    private Long balance;

    private Integer user_id;

    private Integer currency_id;

    private List<Operation> operations;

    public Account() {
    }
}
