package ua.zakharvalko.springbootdemo.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GroupOfCategories {

    private Integer id;

    private String title;

    private List<Category> categories;

    public GroupOfCategories() {
    }

}
