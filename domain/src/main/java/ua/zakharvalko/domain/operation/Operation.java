package ua.zakharvalko.domain.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.zakharvalko.domain.account.Account;
import ua.zakharvalko.domain.category.Category;
import ua.zakharvalko.domain.operationtype.OperationType;
import ua.zakharvalko.domain.user.User;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "operation")
@Getter
@Setter
@ToString
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String description;

    @Column
    private Instant date = Instant.now();

    @Column
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", referencedColumnName = "id")
    private OperationType operationType;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Operation() {
    }
}
