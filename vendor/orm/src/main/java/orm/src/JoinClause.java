package orm.src;
public final class JoinClause {
    public final JoinType joinType;
    public final SafeIdentifier table;
    public final SqlFragment onCondition;
    public static final class Builder {
        JoinType joinType;
        public Builder joinType(JoinType joinType) {
            this.joinType = joinType;
            return this;
        }
        SafeIdentifier table;
        public Builder table(SafeIdentifier table) {
            this.table = table;
            return this;
        }
        SqlFragment onCondition;
        public Builder onCondition(SqlFragment onCondition) {
            this.onCondition = onCondition;
            return this;
        }
        public JoinClause build() {
            return new JoinClause(joinType, table, onCondition);
        }
    }
    public JoinClause(JoinType joinType__652, SafeIdentifier table__653, SqlFragment onCondition__654) {
        this.joinType = joinType__652;
        this.table = table__653;
        this.onCondition = onCondition__654;
    }
    public JoinType getJoinType() {
        return this.joinType;
    }
    public SafeIdentifier getTable() {
        return this.table;
    }
    public SqlFragment getOnCondition() {
        return this.onCondition;
    }
}
