package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__686, Map<String, String> params__687) {
        Map<String, String> t_11221 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__686, params__687, t_11221, List.of(), true);
    }
    static boolean isIdentStart__547(int c__1351) {
        boolean return__472;
        boolean t_6355;
        boolean t_6356;
        if (c__1351 >= 97) {
            t_6355 = c__1351 <= 122;
        } else {
            t_6355 = false;
        }
        if (t_6355) {
            return__472 = true;
        } else {
            if (c__1351 >= 65) {
                t_6356 = c__1351 <= 90;
            } else {
                t_6356 = false;
            }
            if (t_6356) {
                return__472 = true;
            } else {
                return__472 = c__1351 == 95;
            }
        }
        return return__472;
    }
    static boolean isIdentPart__548(int c__1353) {
        boolean return__473;
        if (SrcGlobal.isIdentStart__547(c__1353)) {
            return__473 = true;
        } else if (c__1353 >= 48) {
            return__473 = c__1353 <= 57;
        } else {
            return__473 = false;
        }
        return return__473;
    }
    public static SafeIdentifier safeIdentifier(String name__1355) {
        int t_11219;
        if (name__1355.isEmpty()) {
            throw Core.bubble();
        }
        int idx__1357 = 0;
        if (!SrcGlobal.isIdentStart__547(name__1355.codePointAt(idx__1357))) {
            throw Core.bubble();
        }
        int t_11216 = Core.stringNext(name__1355, idx__1357);
        idx__1357 = t_11216;
        while (true) {
            if (!Core.stringHasIndex(name__1355, idx__1357)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__548(name__1355.codePointAt(idx__1357))) {
                throw Core.bubble();
            }
            t_11219 = Core.stringNext(name__1355, idx__1357);
            idx__1357 = t_11219;
        }
        return new ValidatedIdentifier(name__1355);
    }
    public static SqlFragment deleteSql(TableDef tableDef__776, int id__777) {
        SqlBuilder b__779 = new SqlBuilder();
        b__779.appendSafe("DELETE FROM ");
        b__779.appendSafe(tableDef__776.getTableName().getSqlValue());
        b__779.appendSafe(" WHERE id = ");
        b__779.appendInt32(id__777);
        return b__779.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__1011) {
        return new Query(tableName__1011, List.of(), List.of(), List.of(), null, null, List.of(), List.of(), List.of(), false, List.of(), null);
    }
    public static SqlFragment col(SafeIdentifier table__1013, SafeIdentifier column__1014) {
        SqlBuilder b__1016 = new SqlBuilder();
        b__1016.appendSafe(table__1013.getSqlValue());
        b__1016.appendSafe(".");
        b__1016.appendSafe(column__1014.getSqlValue());
        return b__1016.getAccumulated();
    }
    public static SqlFragment countAll() {
        SqlBuilder b__1018 = new SqlBuilder();
        b__1018.appendSafe("COUNT(*)");
        return b__1018.getAccumulated();
    }
    public static SqlFragment countCol(SafeIdentifier field__1019) {
        SqlBuilder b__1021 = new SqlBuilder();
        b__1021.appendSafe("COUNT(");
        b__1021.appendSafe(field__1019.getSqlValue());
        b__1021.appendSafe(")");
        return b__1021.getAccumulated();
    }
    public static SqlFragment sumCol(SafeIdentifier field__1022) {
        SqlBuilder b__1024 = new SqlBuilder();
        b__1024.appendSafe("SUM(");
        b__1024.appendSafe(field__1022.getSqlValue());
        b__1024.appendSafe(")");
        return b__1024.getAccumulated();
    }
    public static SqlFragment avgCol(SafeIdentifier field__1025) {
        SqlBuilder b__1027 = new SqlBuilder();
        b__1027.appendSafe("AVG(");
        b__1027.appendSafe(field__1025.getSqlValue());
        b__1027.appendSafe(")");
        return b__1027.getAccumulated();
    }
    public static SqlFragment minCol(SafeIdentifier field__1028) {
        SqlBuilder b__1030 = new SqlBuilder();
        b__1030.appendSafe("MIN(");
        b__1030.appendSafe(field__1028.getSqlValue());
        b__1030.appendSafe(")");
        return b__1030.getAccumulated();
    }
    public static SqlFragment maxCol(SafeIdentifier field__1031) {
        SqlBuilder b__1033 = new SqlBuilder();
        b__1033.appendSafe("MAX(");
        b__1033.appendSafe(field__1031.getSqlValue());
        b__1033.appendSafe(")");
        return b__1033.getAccumulated();
    }
    public static SqlFragment unionSql(Query a__1034, Query b__1035) {
        SqlBuilder sb__1037 = new SqlBuilder();
        sb__1037.appendSafe("(");
        sb__1037.appendFragment(a__1034.toSql());
        sb__1037.appendSafe(") UNION (");
        sb__1037.appendFragment(b__1035.toSql());
        sb__1037.appendSafe(")");
        return sb__1037.getAccumulated();
    }
    public static SqlFragment unionAllSql(Query a__1038, Query b__1039) {
        SqlBuilder sb__1041 = new SqlBuilder();
        sb__1041.appendSafe("(");
        sb__1041.appendFragment(a__1038.toSql());
        sb__1041.appendSafe(") UNION ALL (");
        sb__1041.appendFragment(b__1039.toSql());
        sb__1041.appendSafe(")");
        return sb__1041.getAccumulated();
    }
    public static SqlFragment intersectSql(Query a__1042, Query b__1043) {
        SqlBuilder sb__1045 = new SqlBuilder();
        sb__1045.appendSafe("(");
        sb__1045.appendFragment(a__1042.toSql());
        sb__1045.appendSafe(") INTERSECT (");
        sb__1045.appendFragment(b__1043.toSql());
        sb__1045.appendSafe(")");
        return sb__1045.getAccumulated();
    }
    public static SqlFragment exceptSql(Query a__1046, Query b__1047) {
        SqlBuilder sb__1049 = new SqlBuilder();
        sb__1049.appendSafe("(");
        sb__1049.appendFragment(a__1046.toSql());
        sb__1049.appendSafe(") EXCEPT (");
        sb__1049.appendFragment(b__1047.toSql());
        sb__1049.appendSafe(")");
        return sb__1049.getAccumulated();
    }
    public static SqlFragment subquery(Query q__1050, SafeIdentifier alias__1051) {
        SqlBuilder b__1053 = new SqlBuilder();
        b__1053.appendSafe("(");
        b__1053.appendFragment(q__1050.toSql());
        b__1053.appendSafe(") AS ");
        b__1053.appendSafe(alias__1051.getSqlValue());
        return b__1053.getAccumulated();
    }
    public static SqlFragment existsSql(Query q__1054) {
        SqlBuilder b__1056 = new SqlBuilder();
        b__1056.appendSafe("EXISTS (");
        b__1056.appendFragment(q__1054.toSql());
        b__1056.appendSafe(")");
        return b__1056.getAccumulated();
    }
    public static UpdateQuery update(SafeIdentifier tableName__1116) {
        return new UpdateQuery(tableName__1116, List.of(), List.of(), null);
    }
    public static DeleteQuery deleteFrom(SafeIdentifier tableName__1118) {
        return new DeleteQuery(tableName__1118, List.of(), null);
    }
}
