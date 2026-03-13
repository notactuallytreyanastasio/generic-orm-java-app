package orm.src;
import temper.core.Core;
import java.util.List;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__540, Map<String, String> params__541) {
        Map<String, String> t_6990 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__540, params__541, t_6990, List.of(), true);
    }
    static boolean isIdentStart__401(int c__899) {
        boolean return__326;
        boolean t_3981;
        boolean t_3982;
        if (c__899 >= 97) {
            t_3981 = c__899 <= 122;
        } else {
            t_3981 = false;
        }
        if (t_3981) {
            return__326 = true;
        } else {
            if (c__899 >= 65) {
                t_3982 = c__899 <= 90;
            } else {
                t_3982 = false;
            }
            if (t_3982) {
                return__326 = true;
            } else {
                return__326 = c__899 == 95;
            }
        }
        return return__326;
    }
    static boolean isIdentPart__402(int c__901) {
        boolean return__327;
        if (SrcGlobal.isIdentStart__401(c__901)) {
            return__327 = true;
        } else if (c__901 >= 48) {
            return__327 = c__901 <= 57;
        } else {
            return__327 = false;
        }
        return return__327;
    }
    public static SafeIdentifier safeIdentifier(String name__903) {
        int t_6988;
        if (name__903.isEmpty()) {
            throw Core.bubble();
        }
        int idx__905 = 0;
        if (!SrcGlobal.isIdentStart__401(name__903.codePointAt(idx__905))) {
            throw Core.bubble();
        }
        int t_6985 = Core.stringNext(name__903, idx__905);
        idx__905 = t_6985;
        while (true) {
            if (!Core.stringHasIndex(name__903, idx__905)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__402(name__903.codePointAt(idx__905))) {
                throw Core.bubble();
            }
            t_6988 = Core.stringNext(name__903, idx__905);
            idx__905 = t_6988;
        }
        return new ValidatedIdentifier(name__903);
    }
    public static SqlFragment deleteSql(TableDef tableDef__630, int id__631) {
        SqlBuilder b__633 = new SqlBuilder();
        b__633.appendSafe("DELETE FROM ");
        b__633.appendSafe(tableDef__630.getTableName().getSqlValue());
        b__633.appendSafe(" WHERE id = ");
        b__633.appendInt32(id__631);
        return b__633.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__785) {
        return new Query(tableName__785, List.of(), List.of(), List.of(), null, null, List.of());
    }
    public static SqlFragment col(SafeIdentifier table__787, SafeIdentifier column__788) {
        SqlBuilder b__790 = new SqlBuilder();
        b__790.appendSafe(table__787.getSqlValue());
        b__790.appendSafe(".");
        b__790.appendSafe(column__788.getSqlValue());
        return b__790.getAccumulated();
    }
}
