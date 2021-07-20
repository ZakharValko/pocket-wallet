package ua.zakharvalko.domain.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.zakharvalko.domain.currency.Currency;
import ua.zakharvalko.domain.user.User;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@ToString
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
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    public Account() {
    }
}
