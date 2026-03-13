package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
public final class Query {
    public final SafeIdentifier tableName;
    public final List<WhereClause> conditions;
    public final List<SafeIdentifier> selectedFields;
    public final List<OrderClause> orderClauses;
    public final @Nullable Integer limitVal;
    public final @Nullable Integer offsetVal;
    public final List<JoinClause> joinClauses;
    public Query where(SqlFragment condition__686) {
        List<WhereClause> nb__688 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__688, new AndCondition(condition__686));
        return new Query(this.tableName, List.copyOf(nb__688), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses);
    }
    public Query orWhere(SqlFragment condition__690) {
        List<WhereClause> nb__692 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__692, new OrCondition(condition__690));
        return new Query(this.tableName, List.copyOf(nb__692), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses);
    }
    public Query whereNull(SafeIdentifier field__694) {
        SqlBuilder b__696 = new SqlBuilder();
        b__696.appendSafe(field__694.getSqlValue());
        b__696.appendSafe(" IS NULL");
        SqlFragment t_6599 = b__696.getAccumulated();
        return this.where(t_6599);
    }
    public Query whereNotNull(SafeIdentifier field__698) {
        SqlBuilder b__700 = new SqlBuilder();
        b__700.appendSafe(field__698.getSqlValue());
        b__700.appendSafe(" IS NOT NULL");
        SqlFragment t_6593 = b__700.getAccumulated();
        return this.where(t_6593);
    }
    public Query whereIn(SafeIdentifier field__702, List<SqlPart> values__703) {
        Query return__301;
        SqlFragment t_6574;
        int t_6582;
        SqlFragment t_6587;
        fn__704: {
            if (values__703.isEmpty()) {
                SqlBuilder b__705 = new SqlBuilder();
                b__705.appendSafe("1 = 0");
                t_6574 = b__705.getAccumulated();
                return__301 = this.where(t_6574);
                break fn__704;
            }
            SqlBuilder b__706 = new SqlBuilder();
            b__706.appendSafe(field__702.getSqlValue());
            b__706.appendSafe(" IN (");
            b__706.appendPart(Core.listGet(values__703, 0));
            int i__707 = 1;
            while (true) {
                t_6582 = values__703.size();
                if (i__707 >= t_6582) {
                    break;
                }
                b__706.appendSafe(", ");
                b__706.appendPart(Core.listGet(values__703, i__707));
                i__707 = i__707 + 1;
            }
            b__706.appendSafe(")");
            t_6587 = b__706.getAccumulated();
            return__301 = this.where(t_6587);
        }
        return return__301;
    }
    public Query whereNot(SqlFragment condition__709) {
        SqlBuilder b__711 = new SqlBuilder();
        b__711.appendSafe("NOT (");
        b__711.appendFragment(condition__709);
        b__711.appendSafe(")");
        SqlFragment t_6569 = b__711.getAccumulated();
        return this.where(t_6569);
    }
    public Query whereBetween(SafeIdentifier field__713, SqlPart low__714, SqlPart high__715) {
        SqlBuilder b__717 = new SqlBuilder();
        b__717.appendSafe(field__713.getSqlValue());
        b__717.appendSafe(" BETWEEN ");
        b__717.appendPart(low__714);
        b__717.appendSafe(" AND ");
        b__717.appendPart(high__715);
        SqlFragment t_6563 = b__717.getAccumulated();
        return this.where(t_6563);
    }
    public Query whereLike(SafeIdentifier field__719, String pattern__720) {
        SqlBuilder b__722 = new SqlBuilder();
        b__722.appendSafe(field__719.getSqlValue());
        b__722.appendSafe(" LIKE ");
        b__722.appendString(pattern__720);
        SqlFragment t_6554 = b__722.getAccumulated();
        return this.where(t_6554);
    }
    public Query whereILike(SafeIdentifier field__724, String pattern__725) {
        SqlBuilder b__727 = new SqlBuilder();
        b__727.appendSafe(field__724.getSqlValue());
        b__727.appendSafe(" ILIKE ");
        b__727.appendString(pattern__725);
        SqlFragment t_6547 = b__727.getAccumulated();
        return this.where(t_6547);
    }
    public Query select(List<SafeIdentifier> fields__729) {
        return new Query(this.tableName, this.conditions, fields__729, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses);
    }
    public Query orderBy(SafeIdentifier field__732, boolean ascending__733) {
        List<OrderClause> nb__735 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__735, new OrderClause(field__732, ascending__733));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__735), this.limitVal, this.offsetVal, this.joinClauses);
    }
    public Query limit(int n__737) {
        if (n__737 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__737, this.offsetVal, this.joinClauses);
    }
    public Query offset(int n__740) {
        if (n__740 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__740, this.joinClauses);
    }
    public Query join(JoinType joinType__743, SafeIdentifier table__744, SqlFragment onCondition__745) {
        List<JoinClause> nb__747 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__747, new JoinClause(joinType__743, table__744, onCondition__745));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__747));
    }
    public Query innerJoin(SafeIdentifier table__749, SqlFragment onCondition__750) {
        InnerJoin t_6518 = new InnerJoin();
        return this.join(t_6518, table__749, onCondition__750);
    }
    public Query leftJoin(SafeIdentifier table__753, SqlFragment onCondition__754) {
        LeftJoin t_6516 = new LeftJoin();
        return this.join(t_6516, table__753, onCondition__754);
    }
    public Query rightJoin(SafeIdentifier table__757, SqlFragment onCondition__758) {
        RightJoin t_6514 = new RightJoin();
        return this.join(t_6514, table__757, onCondition__758);
    }
    public Query fullJoin(SafeIdentifier table__761, SqlFragment onCondition__762) {
        FullJoin t_6512 = new FullJoin();
        return this.join(t_6512, table__761, onCondition__762);
    }
    public SqlFragment toSql() {
        int t_6494;
        SqlBuilder b__766 = new SqlBuilder();
        b__766.appendSafe("SELECT ");
        if (this.selectedFields.isEmpty()) {
            b__766.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__6476 = f__767 -> f__767.getSqlValue();
            b__766.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__6476));
        }
        b__766.appendSafe(" FROM ");
        b__766.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__6475 = jc__768 -> {
            b__766.appendSafe(" ");
            String t_6464 = jc__768.getJoinType().keyword();
            b__766.appendSafe(t_6464);
            b__766.appendSafe(" ");
            String t_6468 = jc__768.getTable().getSqlValue();
            b__766.appendSafe(t_6468);
            b__766.appendSafe(" ON ");
            SqlFragment t_6471 = jc__768.getOnCondition();
            b__766.appendFragment(t_6471);
        };
        this.joinClauses.forEach(fn__6475);
        if (!this.conditions.isEmpty()) {
            b__766.appendSafe(" WHERE ");
            b__766.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__769 = 1;
            while (true) {
                t_6494 = this.conditions.size();
                if (i__769 >= t_6494) {
                    break;
                }
                b__766.appendSafe(" ");
                b__766.appendSafe(Core.listGet(this.conditions, i__769).keyword());
                b__766.appendSafe(" ");
                b__766.appendFragment(Core.listGet(this.conditions, i__769).getCondition());
                i__769 = i__769 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__766.appendSafe(" ORDER BY ");
            class Local_2 {
                boolean first__770 = true;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<OrderClause> fn__6474 = oc__771 -> {
                String t_3520;
                if (!local$2.first__770) {
                    b__766.appendSafe(", ");
                }
                local$2.first__770 = false;
                String t_6458 = oc__771.getField().getSqlValue();
                b__766.appendSafe(t_6458);
                if (oc__771.isAscending()) {
                    t_3520 = " ASC";
                } else {
                    t_3520 = " DESC";
                }
                b__766.appendSafe(t_3520);
            };
            this.orderClauses.forEach(fn__6474);
        }
        @Nullable Integer lv__772 = this.limitVal;
        if (lv__772 != null) {
            int lv_1452 = lv__772;
            b__766.appendSafe(" LIMIT ");
            b__766.appendInt32(lv_1452);
        }
        @Nullable Integer ov__773 = this.offsetVal;
        if (ov__773 != null) {
            int ov_1453 = ov__773;
            b__766.appendSafe(" OFFSET ");
            b__766.appendInt32(ov_1453);
        }
        return b__766.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__775) {
        SqlFragment return__316;
        Query t_3513;
        if (defaultLimit__775 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__316 = this.toSql();
        } else {
            t_3513 = this.limit(defaultLimit__775);
            return__316 = t_3513.toSql();
        }
        return return__316;
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
    public Query(SafeIdentifier tableName__778, List<WhereClause> conditions__779, List<SafeIdentifier> selectedFields__780, List<OrderClause> orderClauses__781, @Nullable Integer limitVal__782, @Nullable Integer offsetVal__783, List<JoinClause> joinClauses__784) {
        this.tableName = tableName__778;
        this.conditions = conditions__779;
        this.selectedFields = selectedFields__780;
        this.orderClauses = orderClauses__781;
        this.limitVal = limitVal__782;
        this.offsetVal = offsetVal__783;
        this.joinClauses = joinClauses__784;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<WhereClause> getConditions() {
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
