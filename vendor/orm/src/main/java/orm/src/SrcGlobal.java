package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.Map;
import java.util.function.Consumer;
import temper.core.Nullable;
import java.util.function.Function;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__982, Map<String, String> params__983) {
        Map<String, String> t_17003 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__982, params__983, t_17003, List.of(), true);
    }
    static boolean isIdentStart__710(int c__1906) {
        boolean return__624;
        boolean t_9433;
        boolean t_9434;
        if (c__1906 >= 97) {
            t_9433 = c__1906 <= 122;
        } else {
            t_9433 = false;
        }
        if (t_9433) {
            return__624 = true;
        } else {
            if (c__1906 >= 65) {
                t_9434 = c__1906 <= 90;
            } else {
                t_9434 = false;
            }
            if (t_9434) {
                return__624 = true;
            } else {
                return__624 = c__1906 == 95;
            }
        }
        return return__624;
    }
    static boolean isIdentPart__711(int c__1908) {
        boolean return__625;
        if (SrcGlobal.isIdentStart__710(c__1908)) {
            return__625 = true;
        } else if (c__1908 >= 48) {
            return__625 = c__1908 <= 57;
        } else {
            return__625 = false;
        }
        return return__625;
    }
    public static SafeIdentifier safeIdentifier(String name__1910) {
        int t_17001;
        if (name__1910.isEmpty()) {
            throw Core.bubble();
        }
        int idx__1912 = 0;
        if (!SrcGlobal.isIdentStart__710(name__1910.codePointAt(idx__1912))) {
            throw Core.bubble();
        }
        int t_16998 = Core.stringNext(name__1910, idx__1912);
        idx__1912 = t_16998;
        while (true) {
            if (!Core.stringHasIndex(name__1910, idx__1912)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__711(name__1910.codePointAt(idx__1912))) {
                throw Core.bubble();
            }
            t_17001 = Core.stringNext(name__1910, idx__1912);
            idx__1912 = t_17001;
        }
        return new ValidatedIdentifier(name__1910);
    }
    public static List<FieldDef> timestamps() {
        SafeIdentifier t_8692;
        t_8692 = SrcGlobal.safeIdentifier("inserted_at");
        FieldDef t_16100 = new FieldDef(t_8692, new DateField(), true, new SqlDefault(), false);
        SafeIdentifier t_8696;
        t_8696 = SrcGlobal.safeIdentifier("updated_at");
        return List.of(t_16100, new FieldDef(t_8696, new DateField(), true, new SqlDefault(), false));
    }
    public static SqlFragment deleteSql(TableDef tableDef__1301, int id__1302) {
        SqlBuilder b__1304 = new SqlBuilder();
        b__1304.appendSafe("DELETE FROM ");
        b__1304.appendSafe(tableDef__1301.getTableName().getSqlValue());
        b__1304.appendSafe(" WHERE ");
        b__1304.appendSafe(tableDef__1301.pkName());
        b__1304.appendSafe(" = ");
        b__1304.appendInt32(id__1302);
        return b__1304.getAccumulated();
    }
    static void renderWhere__705(SqlBuilder b__1370, List<WhereClause> conditions__1371) {
        SqlFragment t_15522;
        int t_15524;
        String t_15527;
        SqlFragment t_15531;
        if (!conditions__1371.isEmpty()) {
            b__1370.appendSafe(" WHERE ");
            t_15522 = Core.listGet(conditions__1371, 0).getCondition();
            b__1370.appendFragment(t_15522);
            int i__1373 = 1;
            while (true) {
                t_15524 = conditions__1371.size();
                if (i__1373 >= t_15524) {
                    break;
                }
                b__1370.appendSafe(" ");
                t_15527 = Core.listGet(conditions__1371, i__1373).keyword();
                b__1370.appendSafe(t_15527);
                b__1370.appendSafe(" ");
                t_15531 = Core.listGet(conditions__1371, i__1373).getCondition();
                b__1370.appendFragment(t_15531);
                i__1373 = i__1373 + 1;
            }
        }
    }
    static void renderJoins__706(SqlBuilder b__1374, List<JoinClause> joinClauses__1375) {
        Consumer<JoinClause> fn__15516 = jc__1377 -> {
            b__1374.appendSafe(" ");
            String t_15507 = jc__1377.getJoinType().keyword();
            b__1374.appendSafe(t_15507);
            b__1374.appendSafe(" ");
            String t_15511 = jc__1377.getTable().getSqlValue();
            b__1374.appendSafe(t_15511);
            @Nullable SqlFragment oc__1378 = jc__1377.getOnCondition();
            if (oc__1378 != null) {
                SqlFragment oc_2844 = oc__1378;
                b__1374.appendSafe(" ON ");
                b__1374.appendFragment(oc_2844);
            }
        };
        joinClauses__1375.forEach(fn__15516);
    }
    static void renderGroupBy__707(SqlBuilder b__1379, List<SafeIdentifier> groupByFields__1380) {
        String t_15503;
        if (!groupByFields__1380.isEmpty()) {
            b__1379.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__15499 = f__1382 -> f__1382.getSqlValue();
            t_15503 = Core.listJoinObj(groupByFields__1380, ", ", fn__15499);
            b__1379.appendSafe(t_15503);
        }
    }
    static void renderHaving__708(SqlBuilder b__1383, List<WhereClause> havingConditions__1384) {
        SqlFragment t_15487;
        int t_15489;
        String t_15492;
        SqlFragment t_15496;
        if (!havingConditions__1384.isEmpty()) {
            b__1383.appendSafe(" HAVING ");
            t_15487 = Core.listGet(havingConditions__1384, 0).getCondition();
            b__1383.appendFragment(t_15487);
            int i__1386 = 1;
            while (true) {
                t_15489 = havingConditions__1384.size();
                if (i__1386 >= t_15489) {
                    break;
                }
                b__1383.appendSafe(" ");
                t_15492 = Core.listGet(havingConditions__1384, i__1386).keyword();
                b__1383.appendSafe(t_15492);
                b__1383.appendSafe(" ");
                t_15496 = Core.listGet(havingConditions__1384, i__1386).getCondition();
                b__1383.appendFragment(t_15496);
                i__1386 = i__1386 + 1;
            }
        }
    }
    public static Query from(SafeIdentifier tableName__1543) {
        return new Query(tableName__1543, List.of(), List.of(), List.of(), null, null, List.of(), List.of(), List.of(), false, List.of(), null);
    }
    public static SqlFragment col(SafeIdentifier table__1545, SafeIdentifier column__1546) {
        SqlBuilder b__1548 = new SqlBuilder();
        b__1548.appendSafe(table__1545.getSqlValue());
        b__1548.appendSafe(".");
        b__1548.appendSafe(column__1546.getSqlValue());
        return b__1548.getAccumulated();
    }
    public static SqlFragment countAll() {
        SqlBuilder b__1550 = new SqlBuilder();
        b__1550.appendSafe("COUNT(*)");
        return b__1550.getAccumulated();
    }
    public static SqlFragment countCol(SafeIdentifier field__1551) {
        SqlBuilder b__1553 = new SqlBuilder();
        b__1553.appendSafe("COUNT(");
        b__1553.appendSafe(field__1551.getSqlValue());
        b__1553.appendSafe(")");
        return b__1553.getAccumulated();
    }
    public static SqlFragment sumCol(SafeIdentifier field__1554) {
        SqlBuilder b__1556 = new SqlBuilder();
        b__1556.appendSafe("SUM(");
        b__1556.appendSafe(field__1554.getSqlValue());
        b__1556.appendSafe(")");
        return b__1556.getAccumulated();
    }
    public static SqlFragment avgCol(SafeIdentifier field__1557) {
        SqlBuilder b__1559 = new SqlBuilder();
        b__1559.appendSafe("AVG(");
        b__1559.appendSafe(field__1557.getSqlValue());
        b__1559.appendSafe(")");
        return b__1559.getAccumulated();
    }
    public static SqlFragment minCol(SafeIdentifier field__1560) {
        SqlBuilder b__1562 = new SqlBuilder();
        b__1562.appendSafe("MIN(");
        b__1562.appendSafe(field__1560.getSqlValue());
        b__1562.appendSafe(")");
        return b__1562.getAccumulated();
    }
    public static SqlFragment maxCol(SafeIdentifier field__1563) {
        SqlBuilder b__1565 = new SqlBuilder();
        b__1565.appendSafe("MAX(");
        b__1565.appendSafe(field__1563.getSqlValue());
        b__1565.appendSafe(")");
        return b__1565.getAccumulated();
    }
    public static SqlFragment unionSql(Query a__1566, Query b__1567) {
        SqlBuilder sb__1569 = new SqlBuilder();
        sb__1569.appendSafe("(");
        sb__1569.appendFragment(a__1566.toSql());
        sb__1569.appendSafe(") UNION (");
        sb__1569.appendFragment(b__1567.toSql());
        sb__1569.appendSafe(")");
        return sb__1569.getAccumulated();
    }
    public static SqlFragment unionAllSql(Query a__1570, Query b__1571) {
        SqlBuilder sb__1573 = new SqlBuilder();
        sb__1573.appendSafe("(");
        sb__1573.appendFragment(a__1570.toSql());
        sb__1573.appendSafe(") UNION ALL (");
        sb__1573.appendFragment(b__1571.toSql());
        sb__1573.appendSafe(")");
        return sb__1573.getAccumulated();
    }
    public static SqlFragment intersectSql(Query a__1574, Query b__1575) {
        SqlBuilder sb__1577 = new SqlBuilder();
        sb__1577.appendSafe("(");
        sb__1577.appendFragment(a__1574.toSql());
        sb__1577.appendSafe(") INTERSECT (");
        sb__1577.appendFragment(b__1575.toSql());
        sb__1577.appendSafe(")");
        return sb__1577.getAccumulated();
    }
    public static SqlFragment exceptSql(Query a__1578, Query b__1579) {
        SqlBuilder sb__1581 = new SqlBuilder();
        sb__1581.appendSafe("(");
        sb__1581.appendFragment(a__1578.toSql());
        sb__1581.appendSafe(") EXCEPT (");
        sb__1581.appendFragment(b__1579.toSql());
        sb__1581.appendSafe(")");
        return sb__1581.getAccumulated();
    }
    public static SqlFragment subquery(Query q__1582, SafeIdentifier alias__1583) {
        SqlBuilder b__1585 = new SqlBuilder();
        b__1585.appendSafe("(");
        b__1585.appendFragment(q__1582.toSql());
        b__1585.appendSafe(") AS ");
        b__1585.appendSafe(alias__1583.getSqlValue());
        return b__1585.getAccumulated();
    }
    public static SqlFragment existsSql(Query q__1586) {
        SqlBuilder b__1588 = new SqlBuilder();
        b__1588.appendSafe("EXISTS (");
        b__1588.appendFragment(q__1586.toSql());
        b__1588.appendSafe(")");
        return b__1588.getAccumulated();
    }
    public static UpdateQuery update(SafeIdentifier tableName__1646) {
        return new UpdateQuery(tableName__1646, List.of(), List.of(), null);
    }
    public static DeleteQuery deleteFrom(SafeIdentifier tableName__1648) {
        return new DeleteQuery(tableName__1648, List.of(), null);
    }
}
