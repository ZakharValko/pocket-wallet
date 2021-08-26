package ua.zakharvalko.springbootdemo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Temporal;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Operation {

    private Integer id;

    private String description;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date;

    private Long price;

    private Integer account_id;

    private Integer operation_type_id;

    private Integer category_id;

    private Integer transfer_to;

    private Long total_for_transfer;

    public Operation() {
    }

    public Operation(Integer id, String description, Date date, Long price, Integer operationTypeId, Integer categoryId) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.price = price;
        this.operation_type_id = operationTypeId;
        this.category_id = categoryId;
    }
}
