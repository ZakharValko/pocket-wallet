package ua.zakharvalko.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import ua.zakharvalko.springbootdemo.domain.Account;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currency")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
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
