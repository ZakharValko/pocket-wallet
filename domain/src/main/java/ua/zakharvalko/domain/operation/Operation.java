package ua.zakharvalko.domain.operation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.zakharvalko.domain.account.Account;
import ua.zakharvalko.domain.category.Category;
import ua.zakharvalko.domain.operationtype.OperationType;
import ua.zakharvalko.domain.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private Double price;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", referencedColumnName = "id")
    private OperationType operationType;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Operation() {
    }
}
