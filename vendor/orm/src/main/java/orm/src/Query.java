package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Consumer;
public final class Query {
    public final SafeIdentifier tableName;
    public final List<WhereClause> conditions;
    public final List<SafeIdentifier> selectedFields;
    public final List<OrderClause> orderClauses;
    public final @Nullable Integer limitVal;
    public final @Nullable Integer offsetVal;
    public final List<JoinClause> joinClauses;
    public final List<SafeIdentifier> groupByFields;
    public final List<WhereClause> havingConditions;
    public final boolean isDistinct;
    public final List<SqlFragment> selectExprs;
    public final @Nullable LockMode lockMode;
    public Query where(SqlFragment condition__858) {
        List<WhereClause> nb__860 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__860, new AndCondition(condition__858));
        return new Query(this.tableName, List.copyOf(nb__860), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orWhere(SqlFragment condition__862) {
        List<WhereClause> nb__864 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__864, new OrCondition(condition__862));
        return new Query(this.tableName, List.copyOf(nb__864), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query whereNull(SafeIdentifier field__866) {
        SqlBuilder b__868 = new SqlBuilder();
        b__868.appendSafe(field__866.getSqlValue());
        b__868.appendSafe(" IS NULL");
        SqlFragment t_10830 = b__868.getAccumulated();
        return this.where(t_10830);
    }
    public Query whereNotNull(SafeIdentifier field__870) {
        SqlBuilder b__872 = new SqlBuilder();
        b__872.appendSafe(field__870.getSqlValue());
        b__872.appendSafe(" IS NOT NULL");
        SqlFragment t_10824 = b__872.getAccumulated();
        return this.where(t_10824);
    }
    public Query whereIn(SafeIdentifier field__874, List<SqlPart> values__875) {
        Query return__403;
        SqlFragment t_10805;
        int t_10813;
        SqlFragment t_10818;
        fn__876: {
            if (values__875.isEmpty()) {
                SqlBuilder b__877 = new SqlBuilder();
                b__877.appendSafe("1 = 0");
                t_10805 = b__877.getAccumulated();
                return__403 = this.where(t_10805);
                break fn__876;
            }
            SqlBuilder b__878 = new SqlBuilder();
            b__878.appendSafe(field__874.getSqlValue());
            b__878.appendSafe(" IN (");
            b__878.appendPart(Core.listGet(values__875, 0));
            int i__879 = 1;
            while (true) {
                t_10813 = values__875.size();
                if (i__879 >= t_10813) {
                    break;
                }
                b__878.appendSafe(", ");
                b__878.appendPart(Core.listGet(values__875, i__879));
                i__879 = i__879 + 1;
            }
            b__878.appendSafe(")");
            t_10818 = b__878.getAccumulated();
            return__403 = this.where(t_10818);
        }
        return return__403;
    }
    public Query whereInSubquery(SafeIdentifier field__881, Query sub__882) {
        SqlBuilder b__884 = new SqlBuilder();
        b__884.appendSafe(field__881.getSqlValue());
        b__884.appendSafe(" IN (");
        b__884.appendFragment(sub__882.toSql());
        b__884.appendSafe(")");
        SqlFragment t_10800 = b__884.getAccumulated();
        return this.where(t_10800);
    }
    public Query whereNot(SqlFragment condition__886) {
        SqlBuilder b__888 = new SqlBuilder();
        b__888.appendSafe("NOT (");
        b__888.appendFragment(condition__886);
        b__888.appendSafe(")");
        SqlFragment t_10791 = b__888.getAccumulated();
        return this.where(t_10791);
    }
    public Query whereBetween(SafeIdentifier field__890, SqlPart low__891, SqlPart high__892) {
        SqlBuilder b__894 = new SqlBuilder();
        b__894.appendSafe(field__890.getSqlValue());
        b__894.appendSafe(" BETWEEN ");
        b__894.appendPart(low__891);
        b__894.appendSafe(" AND ");
        b__894.appendPart(high__892);
        SqlFragment t_10785 = b__894.getAccumulated();
        return this.where(t_10785);
    }
    public Query whereLike(SafeIdentifier field__896, String pattern__897) {
        SqlBuilder b__899 = new SqlBuilder();
        b__899.appendSafe(field__896.getSqlValue());
        b__899.appendSafe(" LIKE ");
        b__899.appendString(pattern__897);
        SqlFragment t_10776 = b__899.getAccumulated();
        return this.where(t_10776);
    }
    public Query whereILike(SafeIdentifier field__901, String pattern__902) {
        SqlBuilder b__904 = new SqlBuilder();
        b__904.appendSafe(field__901.getSqlValue());
        b__904.appendSafe(" ILIKE ");
        b__904.appendString(pattern__902);
        SqlFragment t_10769 = b__904.getAccumulated();
        return this.where(t_10769);
    }
    public Query select(List<SafeIdentifier> fields__906) {
        return new Query(this.tableName, this.conditions, fields__906, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query selectExpr(List<SqlFragment> exprs__909) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, exprs__909, this.lockMode);
    }
    public Query orderBy(SafeIdentifier field__912, boolean ascending__913) {
        List<OrderClause> nb__915 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__915, new OrderClause(field__912, ascending__913, null));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__915), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orderByNulls(SafeIdentifier field__917, boolean ascending__918, NullsPosition nulls__919) {
        List<OrderClause> nb__921 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__921, new OrderClause(field__917, ascending__918, nulls__919));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__921), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query limit(int n__923) {
        if (n__923 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__923, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query offset(int n__926) {
        if (n__926 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__926, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query join(JoinType joinType__929, SafeIdentifier table__930, SqlFragment onCondition__931) {
        List<JoinClause> nb__933 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__933, new JoinClause(joinType__929, table__930, onCondition__931));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__933), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query innerJoin(SafeIdentifier table__935, SqlFragment onCondition__936) {
        InnerJoin t_10731 = new InnerJoin();
        return this.join(t_10731, table__935, onCondition__936);
    }
    public Query leftJoin(SafeIdentifier table__939, SqlFragment onCondition__940) {
        LeftJoin t_10729 = new LeftJoin();
        return this.join(t_10729, table__939, onCondition__940);
    }
    public Query rightJoin(SafeIdentifier table__943, SqlFragment onCondition__944) {
        RightJoin t_10727 = new RightJoin();
        return this.join(t_10727, table__943, onCondition__944);
    }
    public Query fullJoin(SafeIdentifier table__947, SqlFragment onCondition__948) {
        FullJoin t_10725 = new FullJoin();
        return this.join(t_10725, table__947, onCondition__948);
    }
    public Query crossJoin(SafeIdentifier table__951) {
        List<JoinClause> nb__953 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__953, new JoinClause(new CrossJoin(), table__951, null));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__953), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query groupBy(SafeIdentifier field__955) {
        List<SafeIdentifier> nb__957 = new ArrayList<>(this.groupByFields);
        Core.listAdd(nb__957, field__955);
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, List.copyOf(nb__957), this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query having(SqlFragment condition__959) {
        List<WhereClause> nb__961 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__961, new AndCondition(condition__959));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__961), this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orHaving(SqlFragment condition__963) {
        List<WhereClause> nb__965 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__965, new OrCondition(condition__963));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__965), this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query distinct() {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, true, this.selectExprs, this.lockMode);
    }
    public Query lock(LockMode mode__969) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, mode__969);
    }
    public SqlFragment toSql() {
        int t_10616;
        int t_10635;
        int t_10654;
        SqlBuilder b__973 = new SqlBuilder();
        if (this.isDistinct) {
            b__973.appendSafe("SELECT DISTINCT ");
        } else {
            b__973.appendSafe("SELECT ");
        }
        if (!this.selectExprs.isEmpty()) {
            b__973.appendFragment(Core.listGet(this.selectExprs, 0));
            int i__974 = 1;
            while (true) {
                t_10616 = this.selectExprs.size();
                if (i__974 >= t_10616) {
                    break;
                }
                b__973.appendSafe(", ");
                b__973.appendFragment(Core.listGet(this.selectExprs, i__974));
                i__974 = i__974 + 1;
            }
        } else if (this.selectedFields.isEmpty()) {
            b__973.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__10609 = f__975 -> f__975.getSqlValue();
            b__973.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__10609));
        }
        b__973.appendSafe(" FROM ");
        b__973.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__10608 = jc__976 -> {
            b__973.appendSafe(" ");
            String t_10596 = jc__976.getJoinType().keyword();
            b__973.appendSafe(t_10596);
            b__973.appendSafe(" ");
            String t_10600 = jc__976.getTable().getSqlValue();
            b__973.appendSafe(t_10600);
            @Nullable SqlFragment oc__977 = jc__976.getOnCondition();
            if (oc__977 != null) {
                SqlFragment oc_2069 = oc__977;
                b__973.appendSafe(" ON ");
                b__973.appendFragment(oc_2069);
            }
        };
        this.joinClauses.forEach(fn__10608);
        if (!this.conditions.isEmpty()) {
            b__973.appendSafe(" WHERE ");
            b__973.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__978 = 1;
            while (true) {
                t_10635 = this.conditions.size();
                if (i__978 >= t_10635) {
                    break;
                }
                b__973.appendSafe(" ");
                b__973.appendSafe(Core.listGet(this.conditions, i__978).keyword());
                b__973.appendSafe(" ");
                b__973.appendFragment(Core.listGet(this.conditions, i__978).getCondition());
                i__978 = i__978 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__973.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__10607 = f__979 -> f__979.getSqlValue();
            b__973.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__10607));
        }
        if (!this.havingConditions.isEmpty()) {
            b__973.appendSafe(" HAVING ");
            b__973.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__980 = 1;
            while (true) {
                t_10654 = this.havingConditions.size();
                if (i__980 >= t_10654) {
                    break;
                }
                b__973.appendSafe(" ");
                b__973.appendSafe(Core.listGet(this.havingConditions, i__980).keyword());
                b__973.appendSafe(" ");
                b__973.appendFragment(Core.listGet(this.havingConditions, i__980).getCondition());
                i__980 = i__980 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__973.appendSafe(" ORDER BY ");
            class Local_2 {
                boolean first__981 = true;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<OrderClause> fn__10606 = orc__982 -> {
                String t_10591;
                String t_5801;
                if (!local$2.first__981) {
                    b__973.appendSafe(", ");
                }
                local$2.first__981 = false;
                String t_10586 = orc__982.getField().getSqlValue();
                b__973.appendSafe(t_10586);
                if (orc__982.isAscending()) {
                    t_5801 = " ASC";
                } else {
                    t_5801 = " DESC";
                }
                b__973.appendSafe(t_5801);
                @Nullable NullsPosition np__983 = orc__982.getNullsPos();
                if (np__983 != null) {
                    t_10591 = np__983.keyword();
                    b__973.appendSafe(t_10591);
                }
            };
            this.orderClauses.forEach(fn__10606);
        }
        @Nullable Integer lv__984 = this.limitVal;
        if (lv__984 != null) {
            int lv_2071 = lv__984;
            b__973.appendSafe(" LIMIT ");
            b__973.appendInt32(lv_2071);
        }
        @Nullable Integer ov__985 = this.offsetVal;
        if (ov__985 != null) {
            int ov_2072 = ov__985;
            b__973.appendSafe(" OFFSET ");
            b__973.appendInt32(ov_2072);
        }
        @Nullable LockMode lm__986 = this.lockMode;
        if (lm__986 != null) {
            b__973.appendSafe(lm__986.keyword());
        }
        return b__973.getAccumulated();
    }
    public SqlFragment countSql() {
        int t_10555;
        int t_10574;
        SqlBuilder b__989 = new SqlBuilder();
        b__989.appendSafe("SELECT COUNT(*) FROM ");
        b__989.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__10543 = jc__990 -> {
            b__989.appendSafe(" ");
            String t_10533 = jc__990.getJoinType().keyword();
            b__989.appendSafe(t_10533);
            b__989.appendSafe(" ");
            String t_10537 = jc__990.getTable().getSqlValue();
            b__989.appendSafe(t_10537);
            @Nullable SqlFragment oc2__991 = jc__990.getOnCondition();
            if (oc2__991 != null) {
                SqlFragment oc2_2074 = oc2__991;
                b__989.appendSafe(" ON ");
                b__989.appendFragment(oc2_2074);
            }
        };
        this.joinClauses.forEach(fn__10543);
        if (!this.conditions.isEmpty()) {
            b__989.appendSafe(" WHERE ");
            b__989.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__992 = 1;
            while (true) {
                t_10555 = this.conditions.size();
                if (i__992 >= t_10555) {
                    break;
                }
                b__989.appendSafe(" ");
                b__989.appendSafe(Core.listGet(this.conditions, i__992).keyword());
                b__989.appendSafe(" ");
                b__989.appendFragment(Core.listGet(this.conditions, i__992).getCondition());
                i__992 = i__992 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__989.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__10542 = f__993 -> f__993.getSqlValue();
            b__989.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__10542));
        }
        if (!this.havingConditions.isEmpty()) {
            b__989.appendSafe(" HAVING ");
            b__989.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__994 = 1;
            while (true) {
                t_10574 = this.havingConditions.size();
                if (i__994 >= t_10574) {
                    break;
                }
                b__989.appendSafe(" ");
                b__989.appendSafe(Core.listGet(this.havingConditions, i__994).keyword());
                b__989.appendSafe(" ");
                b__989.appendFragment(Core.listGet(this.havingConditions, i__994).getCondition());
                i__994 = i__994 + 1;
            }
        }
        return b__989.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__996) {
        SqlFragment return__428;
        Query t_5751;
        if (defaultLimit__996 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__428 = this.toSql();
        } else {
            t_5751 = this.limit(defaultLimit__996);
            return__428 = t_5751.toSql();
        }
        return return__428;
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
        List<SafeIdentifier> groupByFields;
        public Builder groupByFields(List<SafeIdentifier> groupByFields) {
            this.groupByFields = groupByFields;
            return this;
        }
        List<WhereClause> havingConditions;
        public Builder havingConditions(List<WhereClause> havingConditions) {
            this.havingConditions = havingConditions;
            return this;
        }
        boolean isDistinct;
        boolean isDistinct__set;
        public Builder isDistinct(boolean isDistinct) {
            isDistinct__set = true;
            this.isDistinct = isDistinct;
            return this;
        }
        List<SqlFragment> selectExprs;
        public Builder selectExprs(List<SqlFragment> selectExprs) {
            this.selectExprs = selectExprs;
            return this;
        }
        @Nullable LockMode lockMode;
        boolean lockMode__set;
        public Builder lockMode(@Nullable LockMode lockMode) {
            lockMode__set = true;
            this.lockMode = lockMode;
            return this;
        }
        public Query build() {
            if (!limitVal__set || !offsetVal__set || !isDistinct__set || !lockMode__set || tableName == null || conditions == null || selectedFields == null || orderClauses == null || joinClauses == null || groupByFields == null || havingConditions == null || selectExprs == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!limitVal__set) {
                    _message.append(" limitVal");
                }
                if (!offsetVal__set) {
                    _message.append(" offsetVal");
                }
                if (!isDistinct__set) {
                    _message.append(" isDistinct");
                }
                if (!lockMode__set) {
                    _message.append(" lockMode");
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
                if (groupByFields == null) {
                    _message.append(" groupByFields");
                }
                if (havingConditions == null) {
                    _message.append(" havingConditions");
                }
                if (selectExprs == null) {
                    _message.append(" selectExprs");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new Query(tableName, conditions, selectedFields, orderClauses, limitVal, offsetVal, joinClauses, groupByFields, havingConditions, isDistinct, selectExprs, lockMode);
        }
    }
    public Query(SafeIdentifier tableName__999, List<WhereClause> conditions__1000, List<SafeIdentifier> selectedFields__1001, List<OrderClause> orderClauses__1002, @Nullable Integer limitVal__1003, @Nullable Integer offsetVal__1004, List<JoinClause> joinClauses__1005, List<SafeIdentifier> groupByFields__1006, List<WhereClause> havingConditions__1007, boolean isDistinct__1008, List<SqlFragment> selectExprs__1009, @Nullable LockMode lockMode__1010) {
        this.tableName = tableName__999;
        this.conditions = conditions__1000;
        this.selectedFields = selectedFields__1001;
        this.orderClauses = orderClauses__1002;
        this.limitVal = limitVal__1003;
        this.offsetVal = offsetVal__1004;
        this.joinClauses = joinClauses__1005;
        this.groupByFields = groupByFields__1006;
        this.havingConditions = havingConditions__1007;
        this.isDistinct = isDistinct__1008;
        this.selectExprs = selectExprs__1009;
        this.lockMode = lockMode__1010;
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
    public List<SafeIdentifier> getGroupByFields() {
        return this.groupByFields;
    }
    public List<WhereClause> getHavingConditions() {
        return this.havingConditions;
    }
    public boolean isDistinct() {
        return this.isDistinct;
    }
    public List<SqlFragment> getSelectExprs() {
        return this.selectExprs;
    }
    public @Nullable LockMode getLockMode() {
        return this.lockMode;
    }
}
