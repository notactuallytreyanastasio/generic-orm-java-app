package orm.src;
import java.util.List;
import temper.core.Nullable;
import temper.core.Core;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
public final class Query {
    public final SafeIdentifier tableName;
    public final List<SqlFragment> conditions;
    public final List<SafeIdentifier> selectedFields;
    public final List<OrderClause> orderClauses;
    public final @Nullable Integer limitVal;
    public final @Nullable Integer offsetVal;
    public final List<JoinClause> joinClauses;
    public Query where(SqlFragment condition__612) {
        List<SqlFragment> nb__614 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__614, condition__612);
        return new Query(this.tableName, List.copyOf(nb__614), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses);
    }
    public Query select(List<SafeIdentifier> fields__616) {
        return new Query(this.tableName, this.conditions, fields__616, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses);
    }
    public Query orderBy(SafeIdentifier field__619, boolean ascending__620) {
        List<OrderClause> nb__622 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__622, new OrderClause(field__619, ascending__620));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__622), this.limitVal, this.offsetVal, this.joinClauses);
    }
    public Query limit(int n__624) {
        if (n__624 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__624, this.offsetVal, this.joinClauses);
    }
    public Query offset(int n__627) {
        if (n__627 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__627, this.joinClauses);
    }
    public Query join(JoinType joinType__630, SafeIdentifier table__631, SqlFragment onCondition__632) {
        List<JoinClause> nb__634 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__634, new JoinClause(joinType__630, table__631, onCondition__632));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__634));
    }
    public Query innerJoin(SafeIdentifier table__636, SqlFragment onCondition__637) {
        InnerJoin t_5152 = new InnerJoin();
        return this.join(t_5152, table__636, onCondition__637);
    }
    public Query leftJoin(SafeIdentifier table__640, SqlFragment onCondition__641) {
        LeftJoin t_5150 = new LeftJoin();
        return this.join(t_5150, table__640, onCondition__641);
    }
    public Query rightJoin(SafeIdentifier table__644, SqlFragment onCondition__645) {
        RightJoin t_5148 = new RightJoin();
        return this.join(t_5148, table__644, onCondition__645);
    }
    public Query fullJoin(SafeIdentifier table__648, SqlFragment onCondition__649) {
        FullJoin t_5146 = new FullJoin();
        return this.join(t_5146, table__648, onCondition__649);
    }
    public SqlFragment toSql() {
        int t_5133;
        SqlBuilder b__653 = new SqlBuilder();
        b__653.appendSafe("SELECT ");
        if (this.selectedFields.isEmpty()) {
            b__653.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__5116 = f__654 -> f__654.getSqlValue();
            b__653.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__5116));
        }
        b__653.appendSafe(" FROM ");
        b__653.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__5115 = jc__655 -> {
            b__653.appendSafe(" ");
            String t_5104 = jc__655.getJoinType().keyword();
            b__653.appendSafe(t_5104);
            b__653.appendSafe(" ");
            String t_5108 = jc__655.getTable().getSqlValue();
            b__653.appendSafe(t_5108);
            b__653.appendSafe(" ON ");
            SqlFragment t_5111 = jc__655.getOnCondition();
            b__653.appendFragment(t_5111);
        };
        this.joinClauses.forEach(fn__5115);
        if (!this.conditions.isEmpty()) {
            b__653.appendSafe(" WHERE ");
            b__653.appendFragment(Core.listGet(this.conditions, 0));
            int i__656 = 1;
            while (true) {
                t_5133 = this.conditions.size();
                if (i__656 >= t_5133) {
                    break;
                }
                b__653.appendSafe(" AND ");
                b__653.appendFragment(Core.listGet(this.conditions, i__656));
                i__656 = i__656 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__653.appendSafe(" ORDER BY ");
            class Local_2 {
                boolean first__657 = true;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<OrderClause> fn__5114 = oc__658 -> {
                String t_2812;
                if (!local$2.first__657) {
                    b__653.appendSafe(", ");
                }
                local$2.first__657 = false;
                String t_5098 = oc__658.getField().getSqlValue();
                b__653.appendSafe(t_5098);
                if (oc__658.isAscending()) {
                    t_2812 = " ASC";
                } else {
                    t_2812 = " DESC";
                }
                b__653.appendSafe(t_2812);
            };
            this.orderClauses.forEach(fn__5114);
        }
        @Nullable Integer lv__659 = this.limitVal;
        if (lv__659 != null) {
            int lv_1257 = lv__659;
            b__653.appendSafe(" LIMIT ");
            b__653.appendInt32(lv_1257);
        }
        @Nullable Integer ov__660 = this.offsetVal;
        if (ov__660 != null) {
            int ov_1258 = ov__660;
            b__653.appendSafe(" OFFSET ");
            b__653.appendInt32(ov_1258);
        }
        return b__653.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__662) {
        SqlFragment return__260;
        Query t_2805;
        if (defaultLimit__662 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__260 = this.toSql();
        } else {
            t_2805 = this.limit(defaultLimit__662);
            return__260 = t_2805.toSql();
        }
        return return__260;
    }
    public static final class Builder {
        SafeIdentifier tableName;
        public Builder tableName(SafeIdentifier tableName) {
            this.tableName = tableName;
            return this;
        }
        List<SqlFragment> conditions;
        public Builder conditions(List<SqlFragment> conditions) {
            this.conditions = conditions;
            return this;
        }
        List<SafeIdentifier> selectedFields;
        public Builder selectedFields(List<SafeIdentifier> selectedFields) {
            this.selectedFields = selectedFields;
            return this;
        }
        List<OrderClause> orderClauses;
        public Builder orderClauses(List<OrderClause> orderClauses) {
            this.orderClauses = orderClauses;
            return this;
        }
        @Nullable Integer limitVal;
        boolean limitVal__set;
        public Builder limitVal(@Nullable Integer limitVal) {
            limitVal__set = true;
            this.limitVal = limitVal;
            return this;
        }
        @Nullable Integer offsetVal;
        boolean offsetVal__set;
        public Builder offsetVal(@Nullable Integer offsetVal) {
            offsetVal__set = true;
            this.offsetVal = offsetVal;
            return this;
        }
        List<JoinClause> joinClauses;
        public Builder joinClauses(List<JoinClause> joinClauses) {
            this.joinClauses = joinClauses;
            return this;
        }
        public Query build() {
            if (!limitVal__set || !offsetVal__set || tableName == null || conditions == null || selectedFields == null || orderClauses == null || joinClauses == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!limitVal__set) {
                    _message.append(" limitVal");
                }
                if (!offsetVal__set) {
                    _message.append(" offsetVal");
                }
                if (tableName == null) {
                    _message.append(" tableName");
                }
                if (conditions == null) {
                    _message.append(" conditions");
                }
                if (selectedFields == null) {
                    _message.append(" selectedFields");
                }
                if (orderClauses == null) {
                    _message.append(" orderClauses");
                }
                if (joinClauses == null) {
                    _message.append(" joinClauses");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new Query(tableName, conditions, selectedFields, orderClauses, limitVal, offsetVal, joinClauses);
        }
    }
    public Query(SafeIdentifier tableName__665, List<SqlFragment> conditions__666, List<SafeIdentifier> selectedFields__667, List<OrderClause> orderClauses__668, @Nullable Integer limitVal__669, @Nullable Integer offsetVal__670, List<JoinClause> joinClauses__671) {
        this.tableName = tableName__665;
        this.conditions = conditions__666;
        this.selectedFields = selectedFields__667;
        this.orderClauses = orderClauses__668;
        this.limitVal = limitVal__669;
        this.offsetVal = offsetVal__670;
        this.joinClauses = joinClauses__671;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<SqlFragment> getConditions() {
        return this.conditions;
    }
    public List<SafeIdentifier> getSelectedFields() {
        return this.selectedFields;
    }
    public List<OrderClause> getOrderClauses() {
        return this.orderClauses;
    }
    public @Nullable Integer getLimitVal() {
        return this.limitVal;
    }
    public @Nullable Integer getOffsetVal() {
        return this.offsetVal;
    }
    public List<JoinClause> getJoinClauses() {
        return this.joinClauses;
    }
}
