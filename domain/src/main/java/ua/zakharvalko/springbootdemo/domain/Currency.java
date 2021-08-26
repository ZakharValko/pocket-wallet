package ua.zakharvalko.springbootdemo.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Currency {

    private Integer id;

    private String title;

    private List<Account> accounts;

    public Currency() {
    }

}
