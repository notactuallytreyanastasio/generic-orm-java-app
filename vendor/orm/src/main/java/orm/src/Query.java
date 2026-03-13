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
    public Query where(SqlFragment condition__795) {
        List<WhereClause> nb__797 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__797, new AndCondition(condition__795));
        return new Query(this.tableName, List.copyOf(nb__797), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query orWhere(SqlFragment condition__799) {
        List<WhereClause> nb__801 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__801, new OrCondition(condition__799));
        return new Query(this.tableName, List.copyOf(nb__801), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query whereNull(SafeIdentifier field__803) {
        SqlBuilder b__805 = new SqlBuilder();
        b__805.appendSafe(field__803.getSqlValue());
        b__805.appendSafe(" IS NULL");
        SqlFragment t_10304 = b__805.getAccumulated();
        return this.where(t_10304);
    }
    public Query whereNotNull(SafeIdentifier field__807) {
        SqlBuilder b__809 = new SqlBuilder();
        b__809.appendSafe(field__807.getSqlValue());
        b__809.appendSafe(" IS NOT NULL");
        SqlFragment t_10298 = b__809.getAccumulated();
        return this.where(t_10298);
    }
    public Query whereIn(SafeIdentifier field__811, List<SqlPart> values__812) {
        Query return__365;
        SqlFragment t_10279;
        int t_10287;
        SqlFragment t_10292;
        fn__813: {
            if (values__812.isEmpty()) {
                SqlBuilder b__814 = new SqlBuilder();
                b__814.appendSafe("1 = 0");
                t_10279 = b__814.getAccumulated();
                return__365 = this.where(t_10279);
                break fn__813;
            }
            SqlBuilder b__815 = new SqlBuilder();
            b__815.appendSafe(field__811.getSqlValue());
            b__815.appendSafe(" IN (");
            b__815.appendPart(Core.listGet(values__812, 0));
            int i__816 = 1;
            while (true) {
                t_10287 = values__812.size();
                if (i__816 >= t_10287) {
                    break;
                }
                b__815.appendSafe(", ");
                b__815.appendPart(Core.listGet(values__812, i__816));
                i__816 = i__816 + 1;
            }
            b__815.appendSafe(")");
            t_10292 = b__815.getAccumulated();
            return__365 = this.where(t_10292);
        }
        return return__365;
    }
    public Query whereInSubquery(SafeIdentifier field__818, Query sub__819) {
        SqlBuilder b__821 = new SqlBuilder();
        b__821.appendSafe(field__818.getSqlValue());
        b__821.appendSafe(" IN (");
        b__821.appendFragment(sub__819.toSql());
        b__821.appendSafe(")");
        SqlFragment t_10274 = b__821.getAccumulated();
        return this.where(t_10274);
    }
    public Query whereNot(SqlFragment condition__823) {
        SqlBuilder b__825 = new SqlBuilder();
        b__825.appendSafe("NOT (");
        b__825.appendFragment(condition__823);
        b__825.appendSafe(")");
        SqlFragment t_10265 = b__825.getAccumulated();
        return this.where(t_10265);
    }
    public Query whereBetween(SafeIdentifier field__827, SqlPart low__828, SqlPart high__829) {
        SqlBuilder b__831 = new SqlBuilder();
        b__831.appendSafe(field__827.getSqlValue());
        b__831.appendSafe(" BETWEEN ");
        b__831.appendPart(low__828);
        b__831.appendSafe(" AND ");
        b__831.appendPart(high__829);
        SqlFragment t_10259 = b__831.getAccumulated();
        return this.where(t_10259);
    }
    public Query whereLike(SafeIdentifier field__833, String pattern__834) {
        SqlBuilder b__836 = new SqlBuilder();
        b__836.appendSafe(field__833.getSqlValue());
        b__836.appendSafe(" LIKE ");
        b__836.appendString(pattern__834);
        SqlFragment t_10250 = b__836.getAccumulated();
        return this.where(t_10250);
    }
    public Query whereILike(SafeIdentifier field__838, String pattern__839) {
        SqlBuilder b__841 = new SqlBuilder();
        b__841.appendSafe(field__838.getSqlValue());
        b__841.appendSafe(" ILIKE ");
        b__841.appendString(pattern__839);
        SqlFragment t_10243 = b__841.getAccumulated();
        return this.where(t_10243);
    }
    public Query select(List<SafeIdentifier> fields__843) {
        return new Query(this.tableName, this.conditions, fields__843, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query selectExpr(List<SqlFragment> exprs__846) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, exprs__846);
    }
    public Query orderBy(SafeIdentifier field__849, boolean ascending__850) {
        List<OrderClause> nb__852 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__852, new OrderClause(field__849, ascending__850));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__852), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query limit(int n__854) {
        if (n__854 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__854, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query offset(int n__857) {
        if (n__857 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__857, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query join(JoinType joinType__860, SafeIdentifier table__861, SqlFragment onCondition__862) {
        List<JoinClause> nb__864 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__864, new JoinClause(joinType__860, table__861, onCondition__862));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__864), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query innerJoin(SafeIdentifier table__866, SqlFragment onCondition__867) {
        InnerJoin t_10213 = new InnerJoin();
        return this.join(t_10213, table__866, onCondition__867);
    }
    public Query leftJoin(SafeIdentifier table__870, SqlFragment onCondition__871) {
        LeftJoin t_10211 = new LeftJoin();
        return this.join(t_10211, table__870, onCondition__871);
    }
    public Query rightJoin(SafeIdentifier table__874, SqlFragment onCondition__875) {
        RightJoin t_10209 = new RightJoin();
        return this.join(t_10209, table__874, onCondition__875);
    }
    public Query fullJoin(SafeIdentifier table__878, SqlFragment onCondition__879) {
        FullJoin t_10207 = new FullJoin();
        return this.join(t_10207, table__878, onCondition__879);
    }
    public Query groupBy(SafeIdentifier field__882) {
        List<SafeIdentifier> nb__884 = new ArrayList<>(this.groupByFields);
        Core.listAdd(nb__884, field__882);
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, List.copyOf(nb__884), this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query having(SqlFragment condition__886) {
        List<WhereClause> nb__888 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__888, new AndCondition(condition__886));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__888), this.isDistinct, this.selectExprs);
    }
    public Query orHaving(SqlFragment condition__890) {
        List<WhereClause> nb__892 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__892, new OrCondition(condition__890));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__892), this.isDistinct, this.selectExprs);
    }
    public Query distinct() {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, true, this.selectExprs);
    }
    public SqlFragment toSql() {
        int t_10113;
        int t_10132;
        int t_10151;
        SqlBuilder b__897 = new SqlBuilder();
        if (this.isDistinct) {
            b__897.appendSafe("SELECT DISTINCT ");
        } else {
            b__897.appendSafe("SELECT ");
        }
        if (!this.selectExprs.isEmpty()) {
            b__897.appendFragment(Core.listGet(this.selectExprs, 0));
            int i__898 = 1;
            while (true) {
                t_10113 = this.selectExprs.size();
                if (i__898 >= t_10113) {
                    break;
                }
                b__897.appendSafe(", ");
                b__897.appendFragment(Core.listGet(this.selectExprs, i__898));
                i__898 = i__898 + 1;
            }
        } else if (this.selectedFields.isEmpty()) {
            b__897.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__10106 = f__899 -> f__899.getSqlValue();
            b__897.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__10106));
        }
        b__897.appendSafe(" FROM ");
        b__897.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__10105 = jc__900 -> {
            b__897.appendSafe(" ");
            String t_10093 = jc__900.getJoinType().keyword();
            b__897.appendSafe(t_10093);
            b__897.appendSafe(" ");
            String t_10097 = jc__900.getTable().getSqlValue();
            b__897.appendSafe(t_10097);
            b__897.appendSafe(" ON ");
            SqlFragment t_10100 = jc__900.getOnCondition();
            b__897.appendFragment(t_10100);
        };
        this.joinClauses.forEach(fn__10105);
        if (!this.conditions.isEmpty()) {
            b__897.appendSafe(" WHERE ");
            b__897.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__901 = 1;
            while (true) {
                t_10132 = this.conditions.size();
                if (i__901 >= t_10132) {
                    break;
                }
                b__897.appendSafe(" ");
                b__897.appendSafe(Core.listGet(this.conditions, i__901).keyword());
                b__897.appendSafe(" ");
                b__897.appendFragment(Core.listGet(this.conditions, i__901).getCondition());
                i__901 = i__901 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__897.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__10104 = f__902 -> f__902.getSqlValue();
            b__897.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__10104));
        }
        if (!this.havingConditions.isEmpty()) {
            b__897.appendSafe(" HAVING ");
            b__897.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__903 = 1;
            while (true) {
                t_10151 = this.havingConditions.size();
                if (i__903 >= t_10151) {
                    break;
                }
                b__897.appendSafe(" ");
                b__897.appendSafe(Core.listGet(this.havingConditions, i__903).keyword());
                b__897.appendSafe(" ");
                b__897.appendFragment(Core.listGet(this.havingConditions, i__903).getCondition());
                i__903 = i__903 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__897.appendSafe(" ORDER BY ");
            class Local_2 {
                boolean first__904 = true;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<OrderClause> fn__10103 = oc__905 -> {
                String t_5515;
                if (!local$2.first__904) {
                    b__897.appendSafe(", ");
                }
                local$2.first__904 = false;
                String t_10086 = oc__905.getField().getSqlValue();
                b__897.appendSafe(t_10086);
                if (oc__905.isAscending()) {
                    t_5515 = " ASC";
                } else {
                    t_5515 = " DESC";
                }
                b__897.appendSafe(t_5515);
            };
            this.orderClauses.forEach(fn__10103);
        }
        @Nullable Integer lv__906 = this.limitVal;
        if (lv__906 != null) {
            int lv_1952 = lv__906;
            b__897.appendSafe(" LIMIT ");
            b__897.appendInt32(lv_1952);
        }
        @Nullable Integer ov__907 = this.offsetVal;
        if (ov__907 != null) {
            int ov_1953 = ov__907;
            b__897.appendSafe(" OFFSET ");
            b__897.appendInt32(ov_1953);
        }
        return b__897.getAccumulated();
    }
    public SqlFragment countSql() {
        int t_10055;
        int t_10074;
        SqlBuilder b__910 = new SqlBuilder();
        b__910.appendSafe("SELECT COUNT(*) FROM ");
        b__910.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__10043 = jc__911 -> {
            b__910.appendSafe(" ");
            String t_10033 = jc__911.getJoinType().keyword();
            b__910.appendSafe(t_10033);
            b__910.appendSafe(" ");
            String t_10037 = jc__911.getTable().getSqlValue();
            b__910.appendSafe(t_10037);
            b__910.appendSafe(" ON ");
            SqlFragment t_10040 = jc__911.getOnCondition();
            b__910.appendFragment(t_10040);
        };
        this.joinClauses.forEach(fn__10043);
        if (!this.conditions.isEmpty()) {
            b__910.appendSafe(" WHERE ");
            b__910.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__912 = 1;
            while (true) {
                t_10055 = this.conditions.size();
                if (i__912 >= t_10055) {
                    break;
                }
                b__910.appendSafe(" ");
                b__910.appendSafe(Core.listGet(this.conditions, i__912).keyword());
                b__910.appendSafe(" ");
                b__910.appendFragment(Core.listGet(this.conditions, i__912).getCondition());
                i__912 = i__912 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__910.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__10042 = f__913 -> f__913.getSqlValue();
            b__910.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__10042));
        }
        if (!this.havingConditions.isEmpty()) {
            b__910.appendSafe(" HAVING ");
            b__910.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__914 = 1;
            while (true) {
                t_10074 = this.havingConditions.size();
                if (i__914 >= t_10074) {
                    break;
                }
                b__910.appendSafe(" ");
                b__910.appendSafe(Core.listGet(this.havingConditions, i__914).keyword());
                b__910.appendSafe(" ");
                b__910.appendFragment(Core.listGet(this.havingConditions, i__914).getCondition());
                i__914 = i__914 + 1;
            }
        }
        return b__910.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__916) {
        SqlFragment return__387;
        Query t_5464;
        if (defaultLimit__916 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__387 = this.toSql();
        } else {
            t_5464 = this.limit(defaultLimit__916);
            return__387 = t_5464.toSql();
        }
        return return__387;
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
        public Query build() {
            if (!limitVal__set || !offsetVal__set || !isDistinct__set || tableName == null || conditions == null || selectedFields == null || orderClauses == null || joinClauses == null || groupByFields == null || havingConditions == null || selectExprs == null) {
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
            return new Query(tableName, conditions, selectedFields, orderClauses, limitVal, offsetVal, joinClauses, groupByFields, havingConditions, isDistinct, selectExprs);
        }
    }
    public Query(SafeIdentifier tableName__919, List<WhereClause> conditions__920, List<SafeIdentifier> selectedFields__921, List<OrderClause> orderClauses__922, @Nullable Integer limitVal__923, @Nullable Integer offsetVal__924, List<JoinClause> joinClauses__925, List<SafeIdentifier> groupByFields__926, List<WhereClause> havingConditions__927, boolean isDistinct__928, List<SqlFragment> selectExprs__929) {
        this.tableName = tableName__919;
        this.conditions = conditions__920;
        this.selectedFields = selectedFields__921;
        this.orderClauses = orderClauses__922;
        this.limitVal = limitVal__923;
        this.offsetVal = offsetVal__924;
        this.joinClauses = joinClauses__925;
        this.groupByFields = groupByFields__926;
        this.havingConditions = havingConditions__927;
        this.isDistinct = isDistinct__928;
        this.selectExprs = selectExprs__929;
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
}
