package ua.zakharvalko.domain.operationtype;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "operation_type")
@Getter
@Setter
@ToString
public class OperationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    public OperationType() {
    }
}
