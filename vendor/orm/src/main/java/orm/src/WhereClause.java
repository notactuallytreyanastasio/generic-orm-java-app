package orm.src;
public interface WhereClause {
    SqlFragment getCondition();
    String keyword();
}
