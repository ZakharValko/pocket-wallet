package ua.zakharvalko.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import ua.zakharvalko.springbootdemo.domain.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_of_categories")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GroupOfCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "group-categories")
    private List<Category> categories;

    public GroupOfCategories() {
    }

}
