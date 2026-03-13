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
    public UpdateQuery set(SafeIdentifier field__1599, SqlPart value__1600) {
        List<SetClause> nb__1602 = new ArrayList<>(this.setClauses);
        Core.listAdd(nb__1602, new SetClause(field__1599, value__1600));
        return new UpdateQuery(this.tableName, List.copyOf(nb__1602), this.conditions, this.limitVal);
    }
    public UpdateQuery where(SqlFragment condition__1604) {
        List<WhereClause> nb__1606 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1606, new AndCondition(condition__1604));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__1606), this.limitVal);
    }
    public UpdateQuery orWhere(SqlFragment condition__1608) {
        List<WhereClause> nb__1610 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1610, new OrCondition(condition__1608));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__1610), this.limitVal);
    }
    public UpdateQuery limit(int n__1612) {
        if (n__1612 < 0) {
            throw Core.bubble();
        }
        return new UpdateQuery(this.tableName, this.setClauses, this.conditions, n__1612);
    }
    public SqlFragment toSql() {
        int t_15132;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        if (this.setClauses.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1616 = new SqlBuilder();
        b__1616.appendSafe("UPDATE ");
        b__1616.appendSafe(this.tableName.getSqlValue());
        b__1616.appendSafe(" SET ");
        b__1616.appendSafe(Core.listGet(this.setClauses, 0).getField().getSqlValue());
        b__1616.appendSafe(" = ");
        b__1616.appendPart(Core.listGet(this.setClauses, 0).getValue());
        int i__1617 = 1;
        while (true) {
            t_15132 = this.setClauses.size();
            if (i__1617 >= t_15132) {
                break;
            }
            b__1616.appendSafe(", ");
            b__1616.appendSafe(Core.listGet(this.setClauses, i__1617).getField().getSqlValue());
            b__1616.appendSafe(" = ");
            b__1616.appendPart(Core.listGet(this.setClauses, i__1617).getValue());
            i__1617 = i__1617 + 1;
        }
        SrcGlobal.renderWhere__705(b__1616, this.conditions);
        @Nullable Integer lv__1618 = this.limitVal;
        if (lv__1618 != null) {
            int lv_2849 = lv__1618;
            b__1616.appendSafe(" LIMIT ");
            b__1616.appendInt32(lv_2849);
        }
        return b__1616.getAccumulated();
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
    public UpdateQuery(SafeIdentifier tableName__1620, List<SetClause> setClauses__1621, List<WhereClause> conditions__1622, @Nullable Integer limitVal__1623) {
        this.tableName = tableName__1620;
        this.setClauses = setClauses__1621;
        this.conditions = conditions__1622;
        this.limitVal = limitVal__1623;
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
