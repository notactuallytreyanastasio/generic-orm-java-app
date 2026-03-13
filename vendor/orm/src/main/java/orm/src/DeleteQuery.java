package orm.src;
import java.util.List;
import temper.core.Nullable;
import temper.core.Core;
import java.util.ArrayList;
public final class DeleteQuery {
    public final SafeIdentifier tableName;
    public final List<WhereClause> conditions;
    public final @Nullable Integer limitVal;
    public DeleteQuery where(SqlFragment condition__1628) {
        List<WhereClause> nb__1630 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1630, new AndCondition(condition__1628));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1630), this.limitVal);
    }
    public DeleteQuery orWhere(SqlFragment condition__1632) {
        List<WhereClause> nb__1634 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1634, new OrCondition(condition__1632));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1634), this.limitVal);
    }
    public DeleteQuery limit(int n__1636) {
        if (n__1636 < 0) {
            throw Core.bubble();
        }
        return new DeleteQuery(this.tableName, this.conditions, n__1636);
    }
    public SqlFragment toSql() {
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1640 = new SqlBuilder();
        b__1640.appendSafe("DELETE FROM ");
        b__1640.appendSafe(this.tableName.getSqlValue());
        SrcGlobal.renderWhere__705(b__1640, this.conditions);
        @Nullable Integer lv__1641 = this.limitVal;
        if (lv__1641 != null) {
            int lv_2850 = lv__1641;
            b__1640.appendSafe(" LIMIT ");
            b__1640.appendInt32(lv_2850);
        }
        return b__1640.getAccumulated();
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
    public DeleteQuery(SafeIdentifier tableName__1643, List<WhereClause> conditions__1644, @Nullable Integer limitVal__1645) {
        this.tableName = tableName__1643;
        this.conditions = conditions__1644;
        this.limitVal = limitVal__1645;
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
