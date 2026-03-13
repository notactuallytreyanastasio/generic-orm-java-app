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
    public UpdateQuery set(SafeIdentifier field__1067, SqlPart value__1068) {
        List<SetClause> nb__1070 = new ArrayList<>(this.setClauses);
        Core.listAdd(nb__1070, new SetClause(field__1067, value__1068));
        return new UpdateQuery(this.tableName, List.copyOf(nb__1070), this.conditions, this.limitVal);
    }
    public UpdateQuery where(SqlFragment condition__1072) {
        List<WhereClause> nb__1074 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1074, new AndCondition(condition__1072));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__1074), this.limitVal);
    }
    public UpdateQuery orWhere(SqlFragment condition__1076) {
        List<WhereClause> nb__1078 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1078, new OrCondition(condition__1076));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__1078), this.limitVal);
    }
    public UpdateQuery limit(int n__1080) {
        if (n__1080 < 0) {
            throw Core.bubble();
        }
        return new UpdateQuery(this.tableName, this.setClauses, this.conditions, n__1080);
    }
    public SqlFragment toSql() {
        int t_10390;
        int t_10404;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        if (this.setClauses.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1084 = new SqlBuilder();
        b__1084.appendSafe("UPDATE ");
        b__1084.appendSafe(this.tableName.getSqlValue());
        b__1084.appendSafe(" SET ");
        b__1084.appendSafe(Core.listGet(this.setClauses, 0).getField().getSqlValue());
        b__1084.appendSafe(" = ");
        b__1084.appendPart(Core.listGet(this.setClauses, 0).getValue());
        int i__1085 = 1;
        while (true) {
            t_10390 = this.setClauses.size();
            if (i__1085 >= t_10390) {
                break;
            }
            b__1084.appendSafe(", ");
            b__1084.appendSafe(Core.listGet(this.setClauses, i__1085).getField().getSqlValue());
            b__1084.appendSafe(" = ");
            b__1084.appendPart(Core.listGet(this.setClauses, i__1085).getValue());
            i__1085 = i__1085 + 1;
        }
        b__1084.appendSafe(" WHERE ");
        b__1084.appendFragment(Core.listGet(this.conditions, 0).getCondition());
        int i__1086 = 1;
        while (true) {
            t_10404 = this.conditions.size();
            if (i__1086 >= t_10404) {
                break;
            }
            b__1084.appendSafe(" ");
            b__1084.appendSafe(Core.listGet(this.conditions, i__1086).keyword());
            b__1084.appendSafe(" ");
            b__1084.appendFragment(Core.listGet(this.conditions, i__1086).getCondition());
            i__1086 = i__1086 + 1;
        }
        @Nullable Integer lv__1087 = this.limitVal;
        if (lv__1087 != null) {
            int lv_2075 = lv__1087;
            b__1084.appendSafe(" LIMIT ");
            b__1084.appendInt32(lv_2075);
        }
        return b__1084.getAccumulated();
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
    public UpdateQuery(SafeIdentifier tableName__1089, List<SetClause> setClauses__1090, List<WhereClause> conditions__1091, @Nullable Integer limitVal__1092) {
        this.tableName = tableName__1089;
        this.setClauses = setClauses__1090;
        this.conditions = conditions__1091;
        this.limitVal = limitVal__1092;
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
