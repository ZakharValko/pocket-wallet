package ua.zakharvalko.domain.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.zakharvalko.domain.group.GroupOfCategories;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private GroupOfCategories group;

    public Category() {
    }
}
