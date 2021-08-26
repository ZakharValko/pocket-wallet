package ua.zakharvalko.springbootdemo.domain.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ua.zakharvalko.springbootdemo.domain.Operation;

import java.util.Date;

@Getter
@Setter
public class OperationFilter extends Operation {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date from;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date to;

    public Integer group_id;

    public Integer currency_id;
}
