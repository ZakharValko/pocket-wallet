package ua.zakharvalko.springbootdemo.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Category {

    private Integer id;

    private String title;

    private Integer group_id;

    public Category() {
    }

}
