package ua.zakharvalko.springbootdemo.dao.querybuilder;

import org.apache.ibatis.jdbc.SQL;
import ua.zakharvalko.springbootdemo.domain.filter.OperationFilter;

public class SqlForFilter {

    public static String getAllByFilterQuery(OperationFilter filter) {
        SQL sql = new SQL(){{
            SELECT("*");
            FROM("operation");
            if (filter.getAccount_id() != null) {
                WHERE("account_id = " + filter.getAccount_id());
            }

            if(filter.getOperation_type_id() != null){
                AND();
                WHERE("operation_type_id = " + filter.getOperation_type_id());
            }

            if(filter.getCategory_id() != null){
                AND();
                WHERE("category_id = " + filter.getCategory_id());
            }

            if(filter.getFrom() != null && filter.getTo() != null){
                AND();
                WHERE("date >= '" + filter.getFrom().toInstant() + "' and date <= '" + filter.getTo().toInstant() + "'");
            } else if(filter.getFrom() == null && filter.getTo() != null){
                AND();
                WHERE(" date <= '" + filter.getTo().toInstant() + "'");
            } else if (filter.getFrom() != null && filter.getTo() == null){
                AND();
                WHERE("date >= '" + filter.getFrom().toInstant() + "'" );
            }

            if(filter.getGroup_id() != null) {
                AND();
                INNER_JOIN("category c ON (c.id = category_id)");
                WHERE("group_id = " + filter.getGroup_id());
            }

            ORDER_BY("operation.id");
        }};
        return sql.toString();
    }
}
