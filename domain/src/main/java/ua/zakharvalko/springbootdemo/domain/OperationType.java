package ua.zakharvalko.springbootdemo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "operation_type")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class OperationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    public OperationType() {
    }

}
