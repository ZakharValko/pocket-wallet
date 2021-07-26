package ua.zakharvalko.domain.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import ua.zakharvalko.domain.currency.Currency;
import ua.zakharvalko.domain.operation.Operation;
import ua.zakharvalko.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Long number;

    @Column
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    //@JsonManagedReference(value = "user-account")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    //@JsonManagedReference(value = "currency-account")
    private Currency currency;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "account-operation")
    private List<Operation> operations;

    public Account() {
    }

}
