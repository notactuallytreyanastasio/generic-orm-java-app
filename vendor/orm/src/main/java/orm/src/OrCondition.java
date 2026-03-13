package orm.src;
public final class OrCondition implements WhereClause {
    final SqlFragment _condition;
    public SqlFragment getCondition() {
        return this._condition;
    }
    public String keyword() {
        return "OR";
    }
    public OrCondition(SqlFragment _condition__677) {
        this._condition = _condition__677;
    }
}
