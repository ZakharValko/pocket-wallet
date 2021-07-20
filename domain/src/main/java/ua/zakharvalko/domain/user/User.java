package ua.zakharvalko.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.zakharvalko.domain.account.Account;
import ua.zakharvalko.domain.operation.Operation;
import ua.zakharvalko.domain.role.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String secondName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Account> accounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public User() {
    }

}
