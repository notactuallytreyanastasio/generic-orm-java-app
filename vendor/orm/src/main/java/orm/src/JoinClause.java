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
    public JoinClause(JoinType joinType__757, SafeIdentifier table__758, SqlFragment onCondition__759) {
        this.joinType = joinType__757;
        this.table = table__758;
        this.onCondition = onCondition__759;
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
