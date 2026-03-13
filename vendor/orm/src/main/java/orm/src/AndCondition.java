package orm.src;
public final class AndCondition implements WhereClause {
    final SqlFragment _condition;
    public SqlFragment getCondition() {
        return this._condition;
    }
    public String keyword() {
        return "AND";
    }
    public AndCondition(SqlFragment _condition__670) {
        this._condition = _condition__670;
    }
}
