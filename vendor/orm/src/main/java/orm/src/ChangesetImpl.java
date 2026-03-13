package orm.src;
import temper.core.Core;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.LinkedHashMap;
final class ChangesetImpl implements Changeset {
    final TableDef _tableDef;
    final Map<String, String> _params;
    final Map<String, String> _changes;
    final List<ChangesetError> _errors;
    final boolean _isValid;
    public TableDef getTableDef() {
        return this._tableDef;
    }
    public Map<String, String> getChanges() {
        return this._changes;
    }
    public List<ChangesetError> getErrors() {
        return this._errors;
    }
    public boolean isValid() {
        return this._isValid;
    }
    public Changeset cast(List<SafeIdentifier> allowedFields__565) {
        Map<String, String> mb__567 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__10837 = f__568 -> {
            String t_10835;
            String t_10832 = f__568.getSqlValue();
            String val__569 = this._params.getOrDefault(t_10832, "");
            if (!val__569.isEmpty()) {
                t_10835 = f__568.getSqlValue();
                mb__567.put(t_10835, val__569);
            }
        };
        allowedFields__565.forEach(fn__10837);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__567), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__571) {
        Changeset return__306;
        List<ChangesetError> t_10830;
        TableDef t_6226;
        Map<String, String> t_6227;
        Map<String, String> t_6228;
        fn__572: {
            if (!this._isValid) {
                return__306 = this;
                break fn__572;
            }
            List<ChangesetError> eb__573 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__574 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__10826 = f__575 -> {
                ChangesetError t_10824;
                String t_10821 = f__575.getSqlValue();
                if (!this._changes.containsKey(t_10821)) {
                    t_10824 = new ChangesetError(f__575.getSqlValue(), "is required");
                    Core.listAdd(eb__573, t_10824);
                    local$1.valid__574 = false;
                }
            };
            fields__571.forEach(fn__10826);
            t_6226 = this._tableDef;
            t_6227 = this._params;
            t_6228 = this._changes;
            t_10830 = List.copyOf(eb__573);
            return__306 = new ChangesetImpl(t_6226, t_6227, t_6228, t_10830, local$1.valid__574);
        }
        return return__306;
    }
    public Changeset validateLength(SafeIdentifier field__577, int min__578, int max__579) {
        Changeset return__307;
        String t_10808;
        List<ChangesetError> t_10819;
        boolean t_6209;
        TableDef t_6215;
        Map<String, String> t_6216;
        Map<String, String> t_6217;
        fn__580: {
            if (!this._isValid) {
                return__307 = this;
                break fn__580;
            }
            t_10808 = field__577.getSqlValue();
            String val__581 = this._changes.getOrDefault(t_10808, "");
            int len__582 = Core.stringCountBetween(val__581, 0, val__581.length());
            if (len__582 < min__578) {
                t_6209 = true;
            } else {
                t_6209 = len__582 > max__579;
            }
            if (t_6209) {
                String msg__583 = "must be between " + Integer.toString(min__578) + " and " + Integer.toString(max__579) + " characters";
                List<ChangesetError> eb__584 = new ArrayList<>(this._errors);
                Core.listAdd(eb__584, new ChangesetError(field__577.getSqlValue(), msg__583));
                t_6215 = this._tableDef;
                t_6216 = this._params;
                t_6217 = this._changes;
                t_10819 = List.copyOf(eb__584);
                return__307 = new ChangesetImpl(t_6215, t_6216, t_6217, t_10819, false);
                break fn__580;
            }
            return__307 = this;
        }
        return return__307;
    }
    public Changeset validateInt(SafeIdentifier field__586) {
        Changeset return__308;
        String t_10799;
        List<ChangesetError> t_10806;
        TableDef t_6200;
        Map<String, String> t_6201;
        Map<String, String> t_6202;
        fn__587: {
            if (!this._isValid) {
                return__308 = this;
                break fn__587;
            }
            t_10799 = field__586.getSqlValue();
            String val__588 = this._changes.getOrDefault(t_10799, "");
            if (val__588.isEmpty()) {
                return__308 = this;
                break fn__587;
            }
            boolean parseOk__589;
            boolean parseOk_10944;
            try {
                Core.stringToInt(val__588);
                parseOk_10944 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_10944 = false;
            }
            parseOk__589 = parseOk_10944;
            if (!parseOk__589) {
                List<ChangesetError> eb__590 = new ArrayList<>(this._errors);
                Core.listAdd(eb__590, new ChangesetError(field__586.getSqlValue(), "must be an integer"));
                t_6200 = this._tableDef;
                t_6201 = this._params;
                t_6202 = this._changes;
                t_10806 = List.copyOf(eb__590);
                return__308 = new ChangesetImpl(t_6200, t_6201, t_6202, t_10806, false);
                break fn__587;
            }
            return__308 = this;
        }
        return return__308;
    }
    public Changeset validateInt64(SafeIdentifier field__592) {
        Changeset return__309;
        String t_10790;
        List<ChangesetError> t_10797;
        TableDef t_6187;
        Map<String, String> t_6188;
        Map<String, String> t_6189;
        fn__593: {
            if (!this._isValid) {
                return__309 = this;
                break fn__593;
            }
            t_10790 = field__592.getSqlValue();
            String val__594 = this._changes.getOrDefault(t_10790, "");
            if (val__594.isEmpty()) {
                return__309 = this;
                break fn__593;
            }
            boolean parseOk__595;
            boolean parseOk_10946;
            try {
                Core.stringToInt64(val__594);
                parseOk_10946 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_10946 = false;
            }
            parseOk__595 = parseOk_10946;
            if (!parseOk__595) {
                List<ChangesetError> eb__596 = new ArrayList<>(this._errors);
                Core.listAdd(eb__596, new ChangesetError(field__592.getSqlValue(), "must be a 64-bit integer"));
                t_6187 = this._tableDef;
                t_6188 = this._params;
                t_6189 = this._changes;
                t_10797 = List.copyOf(eb__596);
                return__309 = new ChangesetImpl(t_6187, t_6188, t_6189, t_10797, false);
                break fn__593;
            }
            return__309 = this;
        }
        return return__309;
    }
    public Changeset validateFloat(SafeIdentifier field__598) {
        Changeset return__310;
        String t_10781;
        List<ChangesetError> t_10788;
        TableDef t_6174;
        Map<String, String> t_6175;
        Map<String, String> t_6176;
        fn__599: {
            if (!this._isValid) {
                return__310 = this;
                break fn__599;
            }
            t_10781 = field__598.getSqlValue();
            String val__600 = this._changes.getOrDefault(t_10781, "");
            if (val__600.isEmpty()) {
                return__310 = this;
                break fn__599;
            }
            boolean parseOk__601;
            boolean parseOk_10948;
            try {
                Core.stringToFloat64(val__600);
                parseOk_10948 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_10948 = false;
            }
            parseOk__601 = parseOk_10948;
            if (!parseOk__601) {
                List<ChangesetError> eb__602 = new ArrayList<>(this._errors);
                Core.listAdd(eb__602, new ChangesetError(field__598.getSqlValue(), "must be a number"));
                t_6174 = this._tableDef;
                t_6175 = this._params;
                t_6176 = this._changes;
                t_10788 = List.copyOf(eb__602);
                return__310 = new ChangesetImpl(t_6174, t_6175, t_6176, t_10788, false);
                break fn__599;
            }
            return__310 = this;
        }
        return return__310;
    }
    public Changeset validateBool(SafeIdentifier field__604) {
        Changeset return__311;
        String t_10772;
        List<ChangesetError> t_10779;
        boolean t_6149;
        boolean t_6150;
        boolean t_6152;
        boolean t_6153;
        boolean t_6155;
        TableDef t_6161;
        Map<String, String> t_6162;
        Map<String, String> t_6163;
        fn__605: {
            if (!this._isValid) {
                return__311 = this;
                break fn__605;
            }
            t_10772 = field__604.getSqlValue();
            String val__606 = this._changes.getOrDefault(t_10772, "");
            if (val__606.isEmpty()) {
                return__311 = this;
                break fn__605;
            }
            boolean isTrue__607;
            if (val__606.equals("true")) {
                isTrue__607 = true;
            } else {
                if (val__606.equals("1")) {
                    t_6150 = true;
                } else {
                    if (val__606.equals("yes")) {
                        t_6149 = true;
                    } else {
                        t_6149 = val__606.equals("on");
                    }
                    t_6150 = t_6149;
                }
                isTrue__607 = t_6150;
            }
            boolean isFalse__608;
            if (val__606.equals("false")) {
                isFalse__608 = true;
            } else {
                if (val__606.equals("0")) {
                    t_6153 = true;
                } else {
                    if (val__606.equals("no")) {
                        t_6152 = true;
                    } else {
                        t_6152 = val__606.equals("off");
                    }
                    t_6153 = t_6152;
                }
                isFalse__608 = t_6153;
            }
            if (!isTrue__607) {
                t_6155 = !isFalse__608;
            } else {
                t_6155 = false;
            }
            if (t_6155) {
                List<ChangesetError> eb__609 = new ArrayList<>(this._errors);
                Core.listAdd(eb__609, new ChangesetError(field__604.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_6161 = this._tableDef;
                t_6162 = this._params;
                t_6163 = this._changes;
                t_10779 = List.copyOf(eb__609);
                return__311 = new ChangesetImpl(t_6161, t_6162, t_6163, t_10779, false);
                break fn__605;
            }
            return__311 = this;
        }
        return return__311;
    }
    SqlBoolean parseBoolSqlPart(String val__611) {
        SqlBoolean return__312;
        boolean t_6138;
        boolean t_6139;
        boolean t_6140;
        boolean t_6142;
        boolean t_6143;
        boolean t_6144;
        fn__612: {
            if (val__611.equals("true")) {
                t_6140 = true;
            } else {
                if (val__611.equals("1")) {
                    t_6139 = true;
                } else {
                    if (val__611.equals("yes")) {
                        t_6138 = true;
                    } else {
                        t_6138 = val__611.equals("on");
                    }
                    t_6139 = t_6138;
                }
                t_6140 = t_6139;
            }
            if (t_6140) {
                return__312 = new SqlBoolean(true);
                break fn__612;
            }
            if (val__611.equals("false")) {
                t_6144 = true;
            } else {
                if (val__611.equals("0")) {
                    t_6143 = true;
                } else {
                    if (val__611.equals("no")) {
                        t_6142 = true;
                    } else {
                        t_6142 = val__611.equals("off");
                    }
                    t_6143 = t_6142;
                }
                t_6144 = t_6143;
            }
            if (t_6144) {
                return__312 = new SqlBoolean(false);
                break fn__612;
            }
            throw Core.bubble();
        }
        return return__312;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__614, String val__615) {
        SqlPart return__313;
        int t_6125;
        long t_6128;
        double t_6131;
        LocalDate t_6136;
        fn__616: {
            FieldType ft__617 = fieldDef__614.getFieldType();
            if (ft__617 instanceof StringField) {
                return__313 = new SqlString(val__615);
                break fn__616;
            }
            if (ft__617 instanceof IntField) {
                t_6125 = Core.stringToInt(val__615);
                return__313 = new SqlInt32(t_6125);
                break fn__616;
            }
            if (ft__617 instanceof Int64Field) {
                t_6128 = Core.stringToInt64(val__615);
                return__313 = new SqlInt64(t_6128);
                break fn__616;
            }
            if (ft__617 instanceof FloatField) {
                t_6131 = Core.stringToFloat64(val__615);
                return__313 = new SqlFloat64(t_6131);
                break fn__616;
            }
            if (ft__617 instanceof BoolField) {
                return__313 = this.parseBoolSqlPart(val__615);
                break fn__616;
            }
            if (ft__617 instanceof DateField) {
                t_6136 = LocalDate.parse(val__615);
                return__313 = new SqlDate(t_6136);
                break fn__616;
            }
            throw Core.bubble();
        }
        return return__313;
    }
    public SqlFragment toInsertSql() {
        int t_10720;
        String t_10725;
        boolean t_10726;
        int t_10731;
        String t_10733;
        String t_10737;
        int t_10752;
        boolean t_6089;
        FieldDef t_6097;
        SqlPart t_6102;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__620 = 0;
        while (true) {
            t_10720 = this._tableDef.getFields().size();
            if (i__620 >= t_10720) {
                break;
            }
            FieldDef f__621 = Core.listGet(this._tableDef.getFields(), i__620);
            if (!f__621.isNullable()) {
                t_10725 = f__621.getName().getSqlValue();
                t_10726 = this._changes.containsKey(t_10725);
                t_6089 = !t_10726;
            } else {
                t_6089 = false;
            }
            if (t_6089) {
                throw Core.bubble();
            }
            i__620 = i__620 + 1;
        }
        List<Entry<String, String>> pairs__622 = Core.mappedToList(this._changes);
        if (pairs__622.size() == 0) {
            throw Core.bubble();
        }
        List<String> colNames__623 = new ArrayList<>();
        List<SqlPart> valParts__624 = new ArrayList<>();
        int i__625 = 0;
        while (true) {
            t_10731 = pairs__622.size();
            if (i__625 >= t_10731) {
                break;
            }
            Entry<String, String> pair__626 = Core.listGet(pairs__622, i__625);
            t_10733 = pair__626.getKey();
            t_6097 = this._tableDef.field(t_10733);
            FieldDef fd__627 = t_6097;
            Core.listAdd(colNames__623, fd__627.getName().getSqlValue());
            t_10737 = pair__626.getValue();
            t_6102 = this.valueToSqlPart(fd__627, t_10737);
            Core.listAdd(valParts__624, t_6102);
            i__625 = i__625 + 1;
        }
        SqlBuilder b__628 = new SqlBuilder();
        b__628.appendSafe("INSERT INTO ");
        b__628.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__628.appendSafe(" (");
        List<String> t_10745 = List.copyOf(colNames__623);
        Function<String, String> fn__10718 = c__629 -> c__629;
        b__628.appendSafe(Core.listJoinObj(t_10745, ", ", fn__10718));
        b__628.appendSafe(") VALUES (");
        b__628.appendPart(Core.listGet(valParts__624, 0));
        int j__630 = 1;
        while (true) {
            t_10752 = valParts__624.size();
            if (j__630 >= t_10752) {
                break;
            }
            b__628.appendSafe(", ");
            b__628.appendPart(Core.listGet(valParts__624, j__630));
            j__630 = j__630 + 1;
        }
        b__628.appendSafe(")");
        return b__628.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__632) {
        int t_10705;
        String t_10708;
        String t_10713;
        FieldDef t_6070;
        SqlPart t_6076;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__634 = Core.mappedToList(this._changes);
        if (pairs__634.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__635 = new SqlBuilder();
        b__635.appendSafe("UPDATE ");
        b__635.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__635.appendSafe(" SET ");
        int i__636 = 0;
        while (true) {
            t_10705 = pairs__634.size();
            if (i__636 >= t_10705) {
                break;
            }
            if (i__636 > 0) {
                b__635.appendSafe(", ");
            }
            Entry<String, String> pair__637 = Core.listGet(pairs__634, i__636);
            t_10708 = pair__637.getKey();
            t_6070 = this._tableDef.field(t_10708);
            FieldDef fd__638 = t_6070;
            b__635.appendSafe(fd__638.getName().getSqlValue());
            b__635.appendSafe(" = ");
            t_10713 = pair__637.getValue();
            t_6076 = this.valueToSqlPart(fd__638, t_10713);
            b__635.appendPart(t_6076);
            i__636 = i__636 + 1;
        }
        b__635.appendSafe(" WHERE id = ");
        b__635.appendInt32(id__632);
        return b__635.getAccumulated();
    }
    public static final class Builder {
        TableDef _tableDef;
        public Builder _tableDef(TableDef _tableDef) {
            this._tableDef = _tableDef;
            return this;
        }
        Map<String, String> _params;
        public Builder _params(Map<String, String> _params) {
            this._params = _params;
            return this;
        }
        Map<String, String> _changes;
        public Builder _changes(Map<String, String> _changes) {
            this._changes = _changes;
            return this;
        }
        List<ChangesetError> _errors;
        public Builder _errors(List<ChangesetError> _errors) {
            this._errors = _errors;
            return this;
        }
        boolean _isValid;
        boolean _isValid__set;
        public Builder _isValid(boolean _isValid) {
            _isValid__set = true;
            this._isValid = _isValid;
            return this;
        }
        public ChangesetImpl build() {
            if (!_isValid__set || _tableDef == null || _params == null || _changes == null || _errors == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!_isValid__set) {
                    _message.append(" _isValid");
                }
                if (_tableDef == null) {
                    _message.append(" _tableDef");
                }
                if (_params == null) {
                    _message.append(" _params");
                }
                if (_changes == null) {
                    _message.append(" _changes");
                }
                if (_errors == null) {
                    _message.append(" _errors");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new ChangesetImpl(_tableDef, _params, _changes, _errors, _isValid);
        }
    }
    public ChangesetImpl(TableDef _tableDef__640, Map<String, String> _params__641, Map<String, String> _changes__642, List<ChangesetError> _errors__643, boolean _isValid__644) {
        this._tableDef = _tableDef__640;
        this._params = _params__641;
        this._changes = _changes__642;
        this._errors = _errors__643;
        this._isValid = _isValid__644;
    }
}
