package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
public final class DeleteQuery {
    public final SafeIdentifier tableName;
    public final List<WhereClause> conditions;
    public final @Nullable Integer limitVal;
    public DeleteQuery where(SqlFragment condition__1016) {
        List<WhereClause> nb__1018 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1018, new AndCondition(condition__1016));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1018), this.limitVal);
    }
    public DeleteQuery orWhere(SqlFragment condition__1020) {
        List<WhereClause> nb__1022 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1022, new OrCondition(condition__1020));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1022), this.limitVal);
    }
    public DeleteQuery limit(int n__1024) {
        if (n__1024 < 0) {
            throw Core.bubble();
        }
        return new DeleteQuery(this.tableName, this.conditions, n__1024);
    }
    public SqlFragment toSql() {
        int t_9850;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1028 = new SqlBuilder();
        b__1028.appendSafe("DELETE FROM ");
        b__1028.appendSafe(this.tableName.getSqlValue());
        b__1028.appendSafe(" WHERE ");
        b__1028.appendFragment(Core.listGet(this.conditions, 0).getCondition());
        int i__1029 = 1;
        while (true) {
            t_9850 = this.conditions.size();
            if (i__1029 >= t_9850) {
                break;
            }
            b__1028.appendSafe(" ");
            b__1028.appendSafe(Core.listGet(this.conditions, i__1029).keyword());
            b__1028.appendSafe(" ");
            b__1028.appendFragment(Core.listGet(this.conditions, i__1029).getCondition());
            i__1029 = i__1029 + 1;
        }
        @Nullable Integer lv__1030 = this.limitVal;
        if (lv__1030 != null) {
            int lv_1955 = lv__1030;
            b__1028.appendSafe(" LIMIT ");
            b__1028.appendInt32(lv_1955);
        }
        return b__1028.getAccumulated();
    }
    public static final class Builder {
        SafeIdentifier tableName;
        public Builder tableName(SafeIdentifier tableName) {
            this.tableName = tableName;
            return this;
        }
        List<WhereClause> conditions;
        public Builder conditions(List<WhereClause> conditions) {
            this.conditions = conditions;
            return this;
        }
        @Nullable Integer limitVal;
        boolean limitVal__set;
        public Builder limitVal(@Nullable Integer limitVal) {
            limitVal__set = true;
            this.limitVal = limitVal;
            return this;
        }
        public DeleteQuery build() {
            if (!limitVal__set || tableName == null || conditions == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!limitVal__set) {
                    _message.append(" limitVal");
                }
                if (tableName == null) {
                    _message.append(" tableName");
                }
                if (conditions == null) {
                    _message.append(" conditions");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new DeleteQuery(tableName, conditions, limitVal);
        }
    }
    public DeleteQuery(SafeIdentifier tableName__1032, List<WhereClause> conditions__1033, @Nullable Integer limitVal__1034) {
        this.tableName = tableName__1032;
        this.conditions = conditions__1033;
        this.limitVal = limitVal__1034;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<WhereClause> getConditions() {
        return this.conditions;
    }
    public @Nullable Integer getLimitVal() {
        return this.limitVal;
    }
}
