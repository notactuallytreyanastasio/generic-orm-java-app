package orm.src;
import temper.core.Nullable;
public final class JoinClause {
    public final JoinType joinType;
    public final SafeIdentifier table;
    public final @Nullable SqlFragment onCondition;
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
        @Nullable SqlFragment onCondition;
        boolean onCondition__set;
        public Builder onCondition(@Nullable SqlFragment onCondition) {
            onCondition__set = true;
            this.onCondition = onCondition;
            return this;
        }
        public JoinClause build() {
            if (!onCondition__set || joinType == null || table == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!onCondition__set) {
                    _message.append(" onCondition");
                }
                if (joinType == null) {
                    _message.append(" joinType");
                }
                if (table == null) {
                    _message.append(" table");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new JoinClause(joinType, table, onCondition);
        }
    }
    public JoinClause(JoinType joinType__801, SafeIdentifier table__802, @Nullable SqlFragment onCondition__803) {
        this.joinType = joinType__801;
        this.table = table__802;
        this.onCondition = onCondition__803;
    }
    public JoinType getJoinType() {
        return this.joinType;
    }
    public SafeIdentifier getTable() {
        return this.table;
    }
    public @Nullable SqlFragment getOnCondition() {
        return this.onCondition;
    }
}
