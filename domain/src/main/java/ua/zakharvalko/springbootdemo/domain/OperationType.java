package ua.zakharvalko.springbootdemo.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class OperationType {

    private Integer id;

    private String title;

    public OperationType() {
    }

}
