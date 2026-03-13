package orm.src;
import temper.core.Core;
import java.util.List;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__484, Map<String, String> params__485) {
        Map<String, String> t_5558 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__484, params__485, t_5558, List.of(), true);
    }
    static boolean isIdentStart__345(int c__743) {
        boolean return__270;
        boolean t_3200;
        boolean t_3201;
        if (c__743 >= 97) {
            t_3200 = c__743 <= 122;
        } else {
            t_3200 = false;
        }
        if (t_3200) {
            return__270 = true;
        } else {
            if (c__743 >= 65) {
                t_3201 = c__743 <= 90;
            } else {
                t_3201 = false;
            }
            if (t_3201) {
                return__270 = true;
            } else {
                return__270 = c__743 == 95;
            }
        }
        return return__270;
    }
    static boolean isIdentPart__346(int c__745) {
        boolean return__271;
        if (SrcGlobal.isIdentStart__345(c__745)) {
            return__271 = true;
        } else if (c__745 >= 48) {
            return__271 = c__745 <= 57;
        } else {
            return__271 = false;
        }
        return return__271;
    }
    public static SafeIdentifier safeIdentifier(String name__747) {
        int t_5556;
        if (name__747.isEmpty()) {
            throw Core.bubble();
        }
        int idx__749 = 0;
        if (!SrcGlobal.isIdentStart__345(name__747.codePointAt(idx__749))) {
            throw Core.bubble();
        }
        int t_5553 = Core.stringNext(name__747, idx__749);
        idx__749 = t_5553;
        while (true) {
            if (!Core.stringHasIndex(name__747, idx__749)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__346(name__747.codePointAt(idx__749))) {
                throw Core.bubble();
            }
            t_5556 = Core.stringNext(name__747, idx__749);
            idx__749 = t_5556;
        }
        return new ValidatedIdentifier(name__747);
    }
    public static SqlFragment deleteSql(TableDef tableDef__574, int id__575) {
        SqlBuilder b__577 = new SqlBuilder();
        b__577.appendSafe("DELETE FROM ");
        b__577.appendSafe(tableDef__574.getTableName().getSqlValue());
        b__577.appendSafe(" WHERE id = ");
        b__577.appendInt32(id__575);
        return b__577.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__672) {
        return new Query(tableName__672, List.of(), List.of(), List.of(), null, null, List.of());
    }
    public static SqlFragment col(SafeIdentifier table__674, SafeIdentifier column__675) {
        SqlBuilder b__677 = new SqlBuilder();
        b__677.appendSafe(table__674.getSqlValue());
        b__677.appendSafe(".");
        b__677.appendSafe(column__675.getSqlValue());
        return b__677.getAccumulated();
    }
}
