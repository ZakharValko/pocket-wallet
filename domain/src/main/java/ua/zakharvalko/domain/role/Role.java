package ua.zakharvalko.domain.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.zakharvalko.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private RolesEnum title;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users;

    public Role() {
    }
}
