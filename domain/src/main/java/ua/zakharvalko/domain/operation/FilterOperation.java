package ua.zakharvalko.domain.operation;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FilterOperation extends Operation{

    private Date from;

    private Date to;
}
