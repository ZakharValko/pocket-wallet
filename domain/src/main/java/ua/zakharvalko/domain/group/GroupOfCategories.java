package ua.zakharvalko.domain.group;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.zakharvalko.domain.category.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_of_categories")
@Getter
@Setter
@ToString
public class GroupOfCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Category> categories;

    public GroupOfCategories() {
    }
}
