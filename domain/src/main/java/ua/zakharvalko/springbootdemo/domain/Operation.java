package ua.zakharvalko.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "operation")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
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
    private Long price;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", referencedColumnName = "id")
    private OperationType operationType;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "transfer_to", referencedColumnName = "id")
    private Account transferTo;

    @Column(name = "total_for_transfer")
    private Long totalForTransfer;

    public Operation() {
    }

    public Operation(Integer id, String description, Date date, Long price, OperationType operationType, Category category) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.price = price;
        this.operationType = operationType;
        this.category = category;
    }
}
