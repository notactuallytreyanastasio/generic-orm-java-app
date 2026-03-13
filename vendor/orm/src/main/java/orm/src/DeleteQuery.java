package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
public final class DeleteQuery {
    public final SafeIdentifier tableName;
    public final List<WhereClause> conditions;
    public final @Nullable Integer limitVal;
    public DeleteQuery where(SqlFragment condition__1097) {
        List<WhereClause> nb__1099 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1099, new AndCondition(condition__1097));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1099), this.limitVal);
    }
    public DeleteQuery orWhere(SqlFragment condition__1101) {
        List<WhereClause> nb__1103 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1103, new OrCondition(condition__1101));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1103), this.limitVal);
    }
    public DeleteQuery limit(int n__1105) {
        if (n__1105 < 0) {
            throw Core.bubble();
        }
        return new DeleteQuery(this.tableName, this.conditions, n__1105);
    }
    public SqlFragment toSql() {
        int t_10350;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1109 = new SqlBuilder();
        b__1109.appendSafe("DELETE FROM ");
        b__1109.appendSafe(this.tableName.getSqlValue());
        b__1109.appendSafe(" WHERE ");
        b__1109.appendFragment(Core.listGet(this.conditions, 0).getCondition());
        int i__1110 = 1;
        while (true) {
            t_10350 = this.conditions.size();
            if (i__1110 >= t_10350) {
                break;
            }
            b__1109.appendSafe(" ");
            b__1109.appendSafe(Core.listGet(this.conditions, i__1110).keyword());
            b__1109.appendSafe(" ");
            b__1109.appendFragment(Core.listGet(this.conditions, i__1110).getCondition());
            i__1110 = i__1110 + 1;
        }
        @Nullable Integer lv__1111 = this.limitVal;
        if (lv__1111 != null) {
            int lv_2076 = lv__1111;
            b__1109.appendSafe(" LIMIT ");
            b__1109.appendInt32(lv_2076);
        }
        return b__1109.getAccumulated();
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
    public DeleteQuery(SafeIdentifier tableName__1113, List<WhereClause> conditions__1114, @Nullable Integer limitVal__1115) {
        this.tableName = tableName__1113;
        this.conditions = conditions__1114;
        this.limitVal = limitVal__1115;
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
