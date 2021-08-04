package ua.zakharvalko.springbootdemo.domain.spec;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ua.zakharvalko.springbootdemo.domain.Operation;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class OperationFilter extends Operation {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd")
    private Date from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd")
    private Date to;

}
