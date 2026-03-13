package orm.src;
import java.util.List;
import temper.core.Nullable;
import temper.core.Core;
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
    public Query where(SqlFragment condition__1400) {
        List<WhereClause> nb__1402 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1402, new AndCondition(condition__1400));
        return new Query(this.tableName, List.copyOf(nb__1402), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orWhere(SqlFragment condition__1404) {
        List<WhereClause> nb__1406 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1406, new OrCondition(condition__1404));
        return new Query(this.tableName, List.copyOf(nb__1406), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query whereNull(SafeIdentifier field__1408) {
        SqlBuilder b__1410 = new SqlBuilder();
        b__1410.appendSafe(field__1408.getSqlValue());
        b__1410.appendSafe(" IS NULL");
        SqlFragment t_15470 = b__1410.getAccumulated();
        return this.where(t_15470);
    }
    public Query whereNotNull(SafeIdentifier field__1412) {
        SqlBuilder b__1414 = new SqlBuilder();
        b__1414.appendSafe(field__1412.getSqlValue());
        b__1414.appendSafe(" IS NOT NULL");
        SqlFragment t_15464 = b__1414.getAccumulated();
        return this.where(t_15464);
    }
    public Query whereIn(SafeIdentifier field__1416, List<SqlPart> values__1417) {
        Query return__555;
        SqlFragment t_15445;
        int t_15453;
        SqlFragment t_15458;
        fn__1418: {
            if (values__1417.isEmpty()) {
                SqlBuilder b__1419 = new SqlBuilder();
                b__1419.appendSafe("1 = 0");
                t_15445 = b__1419.getAccumulated();
                return__555 = this.where(t_15445);
                break fn__1418;
            }
            SqlBuilder b__1420 = new SqlBuilder();
            b__1420.appendSafe(field__1416.getSqlValue());
            b__1420.appendSafe(" IN (");
            b__1420.appendPart(Core.listGet(values__1417, 0));
            int i__1421 = 1;
            while (true) {
                t_15453 = values__1417.size();
                if (i__1421 >= t_15453) {
                    break;
                }
                b__1420.appendSafe(", ");
                b__1420.appendPart(Core.listGet(values__1417, i__1421));
                i__1421 = i__1421 + 1;
            }
            b__1420.appendSafe(")");
            t_15458 = b__1420.getAccumulated();
            return__555 = this.where(t_15458);
        }
        return return__555;
    }
    public Query whereInSubquery(SafeIdentifier field__1423, Query sub__1424) {
        SqlBuilder b__1426 = new SqlBuilder();
        b__1426.appendSafe(field__1423.getSqlValue());
        b__1426.appendSafe(" IN (");
        b__1426.appendFragment(sub__1424.toSql());
        b__1426.appendSafe(")");
        SqlFragment t_15440 = b__1426.getAccumulated();
        return this.where(t_15440);
    }
    public Query whereNot(SqlFragment condition__1428) {
        SqlBuilder b__1430 = new SqlBuilder();
        b__1430.appendSafe("NOT (");
        b__1430.appendFragment(condition__1428);
        b__1430.appendSafe(")");
        SqlFragment t_15431 = b__1430.getAccumulated();
        return this.where(t_15431);
    }
    public Query whereBetween(SafeIdentifier field__1432, SqlPart low__1433, SqlPart high__1434) {
        SqlBuilder b__1436 = new SqlBuilder();
        b__1436.appendSafe(field__1432.getSqlValue());
        b__1436.appendSafe(" BETWEEN ");
        b__1436.appendPart(low__1433);
        b__1436.appendSafe(" AND ");
        b__1436.appendPart(high__1434);
        SqlFragment t_15425 = b__1436.getAccumulated();
        return this.where(t_15425);
    }
    public Query whereLike(SafeIdentifier field__1438, String pattern__1439) {
        SqlBuilder b__1441 = new SqlBuilder();
        b__1441.appendSafe(field__1438.getSqlValue());
        b__1441.appendSafe(" LIKE ");
        b__1441.appendString(pattern__1439);
        SqlFragment t_15416 = b__1441.getAccumulated();
        return this.where(t_15416);
    }
    public Query whereILike(SafeIdentifier field__1443, String pattern__1444) {
        SqlBuilder b__1446 = new SqlBuilder();
        b__1446.appendSafe(field__1443.getSqlValue());
        b__1446.appendSafe(" ILIKE ");
        b__1446.appendString(pattern__1444);
        SqlFragment t_15409 = b__1446.getAccumulated();
        return this.where(t_15409);
    }
    public Query select(List<SafeIdentifier> fields__1448) {
        return new Query(this.tableName, this.conditions, fields__1448, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query selectExpr(List<SqlFragment> exprs__1451) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, exprs__1451, this.lockMode);
    }
    public Query orderBy(SafeIdentifier field__1454, boolean ascending__1455) {
        List<OrderClause> nb__1457 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__1457, new OrderClause(field__1454, ascending__1455, null));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__1457), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orderByNulls(SafeIdentifier field__1459, boolean ascending__1460, NullsPosition nulls__1461) {
        List<OrderClause> nb__1463 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__1463, new OrderClause(field__1459, ascending__1460, nulls__1461));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__1463), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query limit(int n__1465) {
        if (n__1465 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__1465, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query offset(int n__1468) {
        if (n__1468 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__1468, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query join(JoinType joinType__1471, SafeIdentifier table__1472, SqlFragment onCondition__1473) {
        List<JoinClause> nb__1475 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__1475, new JoinClause(joinType__1471, table__1472, onCondition__1473));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__1475), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query innerJoin(SafeIdentifier table__1477, SqlFragment onCondition__1478) {
        InnerJoin t_15371 = new InnerJoin();
        return this.join(t_15371, table__1477, onCondition__1478);
    }
    public Query leftJoin(SafeIdentifier table__1481, SqlFragment onCondition__1482) {
        LeftJoin t_15369 = new LeftJoin();
        return this.join(t_15369, table__1481, onCondition__1482);
    }
    public Query rightJoin(SafeIdentifier table__1485, SqlFragment onCondition__1486) {
        RightJoin t_15367 = new RightJoin();
        return this.join(t_15367, table__1485, onCondition__1486);
    }
    public Query fullJoin(SafeIdentifier table__1489, SqlFragment onCondition__1490) {
        FullJoin t_15365 = new FullJoin();
        return this.join(t_15365, table__1489, onCondition__1490);
    }
    public Query crossJoin(SafeIdentifier table__1493) {
        List<JoinClause> nb__1495 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__1495, new JoinClause(new CrossJoin(), table__1493, null));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__1495), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query groupBy(SafeIdentifier field__1497) {
        List<SafeIdentifier> nb__1499 = new ArrayList<>(this.groupByFields);
        Core.listAdd(nb__1499, field__1497);
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, List.copyOf(nb__1499), this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query having(SqlFragment condition__1501) {
        List<WhereClause> nb__1503 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__1503, new AndCondition(condition__1501));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__1503), this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orHaving(SqlFragment condition__1505) {
        List<WhereClause> nb__1507 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__1507, new OrCondition(condition__1505));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__1507), this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query distinct() {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, true, this.selectExprs, this.lockMode);
    }
    public Query lock(LockMode mode__1511) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, mode__1511);
    }
    public SqlFragment toSql() {
        int t_15287;
        SqlBuilder b__1515 = new SqlBuilder();
        if (this.isDistinct) {
            b__1515.appendSafe("SELECT DISTINCT ");
        } else {
            b__1515.appendSafe("SELECT ");
        }
        if (!this.selectExprs.isEmpty()) {
            b__1515.appendFragment(Core.listGet(this.selectExprs, 0));
            int i__1516 = 1;
            while (true) {
                t_15287 = this.selectExprs.size();
                if (i__1516 >= t_15287) {
                    break;
                }
                b__1515.appendSafe(", ");
                b__1515.appendFragment(Core.listGet(this.selectExprs, i__1516));
                i__1516 = i__1516 + 1;
            }
        } else if (this.selectedFields.isEmpty()) {
            b__1515.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__15280 = f__1517 -> f__1517.getSqlValue();
            b__1515.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__15280));
        }
        b__1515.appendSafe(" FROM ");
        b__1515.appendSafe(this.tableName.getSqlValue());
        SrcGlobal.renderJoins__706(b__1515, this.joinClauses);
        SrcGlobal.renderWhere__705(b__1515, this.conditions);
        SrcGlobal.renderGroupBy__707(b__1515, this.groupByFields);
        SrcGlobal.renderHaving__708(b__1515, this.havingConditions);
        if (!this.orderClauses.isEmpty()) {
            b__1515.appendSafe(" ORDER BY ");
            class Local_4 {
                boolean first__1518 = true;
            }
            final Local_4 local$4 = new Local_4();
            Consumer<OrderClause> fn__15279 = orc__1519 -> {
                String t_15276;
                String t_7962;
                if (!local$4.first__1518) {
                    b__1515.appendSafe(", ");
                }
                local$4.first__1518 = false;
                String t_15271 = orc__1519.getField().getSqlValue();
                b__1515.appendSafe(t_15271);
                if (orc__1519.isAscending()) {
                    t_7962 = " ASC";
                } else {
                    t_7962 = " DESC";
                }
                b__1515.appendSafe(t_7962);
                @Nullable NullsPosition np__1520 = orc__1519.getNullsPos();
                if (np__1520 != null) {
                    t_15276 = np__1520.keyword();
                    b__1515.appendSafe(t_15276);
                }
            };
            this.orderClauses.forEach(fn__15279);
        }
        @Nullable Integer lv__1521 = this.limitVal;
        if (lv__1521 != null) {
            int lv_2846 = lv__1521;
            b__1515.appendSafe(" LIMIT ");
            b__1515.appendInt32(lv_2846);
        }
        @Nullable Integer ov__1522 = this.offsetVal;
        if (ov__1522 != null) {
            int ov_2847 = ov__1522;
            b__1515.appendSafe(" OFFSET ");
            b__1515.appendInt32(ov_2847);
        }
        @Nullable LockMode lm__1523 = this.lockMode;
        if (lm__1523 != null) {
            b__1515.appendSafe(lm__1523.keyword());
        }
        return b__1515.getAccumulated();
    }
    public SqlFragment countSql() {
        SqlBuilder b__1526 = new SqlBuilder();
        b__1526.appendSafe("SELECT COUNT(*) FROM ");
        b__1526.appendSafe(this.tableName.getSqlValue());
        SrcGlobal.renderJoins__706(b__1526, this.joinClauses);
        SrcGlobal.renderWhere__705(b__1526, this.conditions);
        SrcGlobal.renderGroupBy__707(b__1526, this.groupByFields);
        SrcGlobal.renderHaving__708(b__1526, this.havingConditions);
        return b__1526.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__1528) {
        SqlFragment return__580;
        Query t_7946;
        if (defaultLimit__1528 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__580 = this.toSql();
        } else {
            t_7946 = this.limit(defaultLimit__1528);
            return__580 = t_7946.toSql();
        }
        return return__580;
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
    public Query(SafeIdentifier tableName__1531, List<WhereClause> conditions__1532, List<SafeIdentifier> selectedFields__1533, List<OrderClause> orderClauses__1534, @Nullable Integer limitVal__1535, @Nullable Integer offsetVal__1536, List<JoinClause> joinClauses__1537, List<SafeIdentifier> groupByFields__1538, List<WhereClause> havingConditions__1539, boolean isDistinct__1540, List<SqlFragment> selectExprs__1541, @Nullable LockMode lockMode__1542) {
        this.tableName = tableName__1531;
        this.conditions = conditions__1532;
        this.selectedFields = selectedFields__1533;
        this.orderClauses = orderClauses__1534;
        this.limitVal = limitVal__1535;
        this.offsetVal = offsetVal__1536;
        this.joinClauses = joinClauses__1537;
        this.groupByFields = groupByFields__1538;
        this.havingConditions = havingConditions__1539;
        this.isDistinct = isDistinct__1540;
        this.selectExprs = selectExprs__1541;
        this.lockMode = lockMode__1542;
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
