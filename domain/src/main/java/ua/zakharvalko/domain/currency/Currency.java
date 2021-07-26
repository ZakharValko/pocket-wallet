package ua.zakharvalko.domain.currency;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.zakharvalko.domain.account.Account;
import ua.zakharvalko.domain.operation.Operation;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currency")
@Getter
@Setter
@ToString
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "currency-account")
    private List<Account> accounts;

    public Currency() {
    }
}
