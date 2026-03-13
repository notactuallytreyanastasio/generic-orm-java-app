package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
public final class UpdateQuery {
    public final SafeIdentifier tableName;
    public final List<SetClause> setClauses;
    public final List<WhereClause> conditions;
    public final @Nullable Integer limitVal;
    public UpdateQuery set(SafeIdentifier field__986, SqlPart value__987) {
        List<SetClause> nb__989 = new ArrayList<>(this.setClauses);
        Core.listAdd(nb__989, new SetClause(field__986, value__987));
        return new UpdateQuery(this.tableName, List.copyOf(nb__989), this.conditions, this.limitVal);
    }
    public UpdateQuery where(SqlFragment condition__991) {
        List<WhereClause> nb__993 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__993, new AndCondition(condition__991));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__993), this.limitVal);
    }
    public UpdateQuery orWhere(SqlFragment condition__995) {
        List<WhereClause> nb__997 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__997, new OrCondition(condition__995));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__997), this.limitVal);
    }
    public UpdateQuery limit(int n__999) {
        if (n__999 < 0) {
            throw Core.bubble();
        }
        return new UpdateQuery(this.tableName, this.setClauses, this.conditions, n__999);
    }
    public SqlFragment toSql() {
        int t_9890;
        int t_9904;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        if (this.setClauses.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1003 = new SqlBuilder();
        b__1003.appendSafe("UPDATE ");
        b__1003.appendSafe(this.tableName.getSqlValue());
        b__1003.appendSafe(" SET ");
        b__1003.appendSafe(Core.listGet(this.setClauses, 0).getField().getSqlValue());
        b__1003.appendSafe(" = ");
        b__1003.appendPart(Core.listGet(this.setClauses, 0).getValue());
        int i__1004 = 1;
        while (true) {
            t_9890 = this.setClauses.size();
            if (i__1004 >= t_9890) {
                break;
            }
            b__1003.appendSafe(", ");
            b__1003.appendSafe(Core.listGet(this.setClauses, i__1004).getField().getSqlValue());
            b__1003.appendSafe(" = ");
            b__1003.appendPart(Core.listGet(this.setClauses, i__1004).getValue());
            i__1004 = i__1004 + 1;
        }
        b__1003.appendSafe(" WHERE ");
        b__1003.appendFragment(Core.listGet(this.conditions, 0).getCondition());
        int i__1005 = 1;
        while (true) {
            t_9904 = this.conditions.size();
            if (i__1005 >= t_9904) {
                break;
            }
            b__1003.appendSafe(" ");
            b__1003.appendSafe(Core.listGet(this.conditions, i__1005).keyword());
            b__1003.appendSafe(" ");
            b__1003.appendFragment(Core.listGet(this.conditions, i__1005).getCondition());
            i__1005 = i__1005 + 1;
        }
        @Nullable Integer lv__1006 = this.limitVal;
        if (lv__1006 != null) {
            int lv_1954 = lv__1006;
            b__1003.appendSafe(" LIMIT ");
            b__1003.appendInt32(lv_1954);
        }
        return b__1003.getAccumulated();
    }
    public static final class Builder {
        SafeIdentifier tableName;
        public Builder tableName(SafeIdentifier tableName) {
            this.tableName = tableName;
            return this;
        }
        List<SetClause> setClauses;
        public Builder setClauses(List<SetClause> setClauses) {
            this.setClauses = setClauses;
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
        public UpdateQuery build() {
            if (!limitVal__set || tableName == null || setClauses == null || conditions == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!limitVal__set) {
                    _message.append(" limitVal");
                }
                if (tableName == null) {
                    _message.append(" tableName");
                }
                if (setClauses == null) {
                    _message.append(" setClauses");
                }
                if (conditions == null) {
                    _message.append(" conditions");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new UpdateQuery(tableName, setClauses, conditions, limitVal);
        }
    }
    public UpdateQuery(SafeIdentifier tableName__1008, List<SetClause> setClauses__1009, List<WhereClause> conditions__1010, @Nullable Integer limitVal__1011) {
        this.tableName = tableName__1008;
        this.setClauses = setClauses__1009;
        this.conditions = conditions__1010;
        this.limitVal = limitVal__1011;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<SetClause> getSetClauses() {
        return this.setClauses;
    }
    public List<WhereClause> getConditions() {
        return this.conditions;
    }
    public @Nullable Integer getLimitVal() {
        return this.limitVal;
    }
}
