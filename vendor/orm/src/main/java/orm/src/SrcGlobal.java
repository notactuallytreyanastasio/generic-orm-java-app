package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__645, Map<String, String> params__646) {
        Map<String, String> t_10695 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__645, params__646, t_10695, List.of(), true);
    }
    static boolean isIdentStart__506(int c__1254) {
        boolean return__431;
        boolean t_6044;
        boolean t_6045;
        if (c__1254 >= 97) {
            t_6044 = c__1254 <= 122;
        } else {
            t_6044 = false;
        }
        if (t_6044) {
            return__431 = true;
        } else {
            if (c__1254 >= 65) {
                t_6045 = c__1254 <= 90;
            } else {
                t_6045 = false;
            }
            if (t_6045) {
                return__431 = true;
            } else {
                return__431 = c__1254 == 95;
            }
        }
        return return__431;
    }
    static boolean isIdentPart__507(int c__1256) {
        boolean return__432;
        if (SrcGlobal.isIdentStart__506(c__1256)) {
            return__432 = true;
        } else if (c__1256 >= 48) {
            return__432 = c__1256 <= 57;
        } else {
            return__432 = false;
        }
        return return__432;
    }
    public static SafeIdentifier safeIdentifier(String name__1258) {
        int t_10693;
        if (name__1258.isEmpty()) {
            throw Core.bubble();
        }
        int idx__1260 = 0;
        if (!SrcGlobal.isIdentStart__506(name__1258.codePointAt(idx__1260))) {
            throw Core.bubble();
        }
        int t_10690 = Core.stringNext(name__1258, idx__1260);
        idx__1260 = t_10690;
        while (true) {
            if (!Core.stringHasIndex(name__1258, idx__1260)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__507(name__1258.codePointAt(idx__1260))) {
                throw Core.bubble();
            }
            t_10693 = Core.stringNext(name__1258, idx__1260);
            idx__1260 = t_10693;
        }
        return new ValidatedIdentifier(name__1258);
    }
    public static SqlFragment deleteSql(TableDef tableDef__735, int id__736) {
        SqlBuilder b__738 = new SqlBuilder();
        b__738.appendSafe("DELETE FROM ");
        b__738.appendSafe(tableDef__735.getTableName().getSqlValue());
        b__738.appendSafe(" WHERE id = ");
        b__738.appendInt32(id__736);
        return b__738.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__930) {
        return new Query(tableName__930, List.of(), List.of(), List.of(), null, null, List.of(), List.of(), List.of(), false, List.of());
    }
    public static SqlFragment col(SafeIdentifier table__932, SafeIdentifier column__933) {
        SqlBuilder b__935 = new SqlBuilder();
        b__935.appendSafe(table__932.getSqlValue());
        b__935.appendSafe(".");
        b__935.appendSafe(column__933.getSqlValue());
        return b__935.getAccumulated();
    }
    public static SqlFragment countAll() {
        SqlBuilder b__937 = new SqlBuilder();
        b__937.appendSafe("COUNT(*)");
        return b__937.getAccumulated();
    }
    public static SqlFragment countCol(SafeIdentifier field__938) {
        SqlBuilder b__940 = new SqlBuilder();
        b__940.appendSafe("COUNT(");
        b__940.appendSafe(field__938.getSqlValue());
        b__940.appendSafe(")");
        return b__940.getAccumulated();
    }
    public static SqlFragment sumCol(SafeIdentifier field__941) {
        SqlBuilder b__943 = new SqlBuilder();
        b__943.appendSafe("SUM(");
        b__943.appendSafe(field__941.getSqlValue());
        b__943.appendSafe(")");
        return b__943.getAccumulated();
    }
    public static SqlFragment avgCol(SafeIdentifier field__944) {
        SqlBuilder b__946 = new SqlBuilder();
        b__946.appendSafe("AVG(");
        b__946.appendSafe(field__944.getSqlValue());
        b__946.appendSafe(")");
        return b__946.getAccumulated();
    }
    public static SqlFragment minCol(SafeIdentifier field__947) {
        SqlBuilder b__949 = new SqlBuilder();
        b__949.appendSafe("MIN(");
        b__949.appendSafe(field__947.getSqlValue());
        b__949.appendSafe(")");
        return b__949.getAccumulated();
    }
    public static SqlFragment maxCol(SafeIdentifier field__950) {
        SqlBuilder b__952 = new SqlBuilder();
        b__952.appendSafe("MAX(");
        b__952.appendSafe(field__950.getSqlValue());
        b__952.appendSafe(")");
        return b__952.getAccumulated();
    }
    public static SqlFragment unionSql(Query a__953, Query b__954) {
        SqlBuilder sb__956 = new SqlBuilder();
        sb__956.appendSafe("(");
        sb__956.appendFragment(a__953.toSql());
        sb__956.appendSafe(") UNION (");
        sb__956.appendFragment(b__954.toSql());
        sb__956.appendSafe(")");
        return sb__956.getAccumulated();
    }
    public static SqlFragment unionAllSql(Query a__957, Query b__958) {
        SqlBuilder sb__960 = new SqlBuilder();
        sb__960.appendSafe("(");
        sb__960.appendFragment(a__957.toSql());
        sb__960.appendSafe(") UNION ALL (");
        sb__960.appendFragment(b__958.toSql());
        sb__960.appendSafe(")");
        return sb__960.getAccumulated();
    }
    public static SqlFragment intersectSql(Query a__961, Query b__962) {
        SqlBuilder sb__964 = new SqlBuilder();
        sb__964.appendSafe("(");
        sb__964.appendFragment(a__961.toSql());
        sb__964.appendSafe(") INTERSECT (");
        sb__964.appendFragment(b__962.toSql());
        sb__964.appendSafe(")");
        return sb__964.getAccumulated();
    }
    public static SqlFragment exceptSql(Query a__965, Query b__966) {
        SqlBuilder sb__968 = new SqlBuilder();
        sb__968.appendSafe("(");
        sb__968.appendFragment(a__965.toSql());
        sb__968.appendSafe(") EXCEPT (");
        sb__968.appendFragment(b__966.toSql());
        sb__968.appendSafe(")");
        return sb__968.getAccumulated();
    }
    public static SqlFragment subquery(Query q__969, SafeIdentifier alias__970) {
        SqlBuilder b__972 = new SqlBuilder();
        b__972.appendSafe("(");
        b__972.appendFragment(q__969.toSql());
        b__972.appendSafe(") AS ");
        b__972.appendSafe(alias__970.getSqlValue());
        return b__972.getAccumulated();
    }
    public static SqlFragment existsSql(Query q__973) {
        SqlBuilder b__975 = new SqlBuilder();
        b__975.appendSafe("EXISTS (");
        b__975.appendFragment(q__973.toSql());
        b__975.appendSafe(")");
        return b__975.getAccumulated();
    }
    public static UpdateQuery update(SafeIdentifier tableName__1035) {
        return new UpdateQuery(tableName__1035, List.of(), List.of(), null);
    }
    public static DeleteQuery deleteFrom(SafeIdentifier tableName__1037) {
        return new DeleteQuery(tableName__1037, List.of(), null);
    }
}
