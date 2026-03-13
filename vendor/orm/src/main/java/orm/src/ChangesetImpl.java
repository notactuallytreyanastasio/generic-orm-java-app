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
    public Changeset cast(List<SafeIdentifier> allowedFields__460) {
        Map<String, String> mb__462 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__7132 = f__463 -> {
            String t_7130;
            String t_7127 = f__463.getSqlValue();
            String val__464 = this._params.getOrDefault(t_7127, "");
            if (!val__464.isEmpty()) {
                t_7130 = f__463.getSqlValue();
                mb__462.put(t_7130, val__464);
            }
        };
        allowedFields__460.forEach(fn__7132);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__462), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__466) {
        Changeset return__245;
        List<ChangesetError> t_7125;
        TableDef t_4163;
        Map<String, String> t_4164;
        Map<String, String> t_4165;
        fn__467: {
            if (!this._isValid) {
                return__245 = this;
                break fn__467;
            }
            List<ChangesetError> eb__468 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__469 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__7121 = f__470 -> {
                ChangesetError t_7119;
                String t_7116 = f__470.getSqlValue();
                if (!this._changes.containsKey(t_7116)) {
                    t_7119 = new ChangesetError(f__470.getSqlValue(), "is required");
                    Core.listAdd(eb__468, t_7119);
                    local$1.valid__469 = false;
                }
            };
            fields__466.forEach(fn__7121);
            t_4163 = this._tableDef;
            t_4164 = this._params;
            t_4165 = this._changes;
            t_7125 = List.copyOf(eb__468);
            return__245 = new ChangesetImpl(t_4163, t_4164, t_4165, t_7125, local$1.valid__469);
        }
        return return__245;
    }
    public Changeset validateLength(SafeIdentifier field__472, int min__473, int max__474) {
        Changeset return__246;
        String t_7103;
        List<ChangesetError> t_7114;
        boolean t_4146;
        TableDef t_4152;
        Map<String, String> t_4153;
        Map<String, String> t_4154;
        fn__475: {
            if (!this._isValid) {
                return__246 = this;
                break fn__475;
            }
            t_7103 = field__472.getSqlValue();
            String val__476 = this._changes.getOrDefault(t_7103, "");
            int len__477 = Core.stringCountBetween(val__476, 0, val__476.length());
            if (len__477 < min__473) {
                t_4146 = true;
            } else {
                t_4146 = len__477 > max__474;
            }
            if (t_4146) {
                String msg__478 = "must be between " + Integer.toString(min__473) + " and " + Integer.toString(max__474) + " characters";
                List<ChangesetError> eb__479 = new ArrayList<>(this._errors);
                Core.listAdd(eb__479, new ChangesetError(field__472.getSqlValue(), msg__478));
                t_4152 = this._tableDef;
                t_4153 = this._params;
                t_4154 = this._changes;
                t_7114 = List.copyOf(eb__479);
                return__246 = new ChangesetImpl(t_4152, t_4153, t_4154, t_7114, false);
                break fn__475;
            }
            return__246 = this;
        }
        return return__246;
    }
    public Changeset validateInt(SafeIdentifier field__481) {
        Changeset return__247;
        String t_7094;
        List<ChangesetError> t_7101;
        TableDef t_4137;
        Map<String, String> t_4138;
        Map<String, String> t_4139;
        fn__482: {
            if (!this._isValid) {
                return__247 = this;
                break fn__482;
            }
            t_7094 = field__481.getSqlValue();
            String val__483 = this._changes.getOrDefault(t_7094, "");
            if (val__483.isEmpty()) {
                return__247 = this;
                break fn__482;
            }
            boolean parseOk__484;
            boolean parseOk_7239;
            try {
                Core.stringToInt(val__483);
                parseOk_7239 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_7239 = false;
            }
            parseOk__484 = parseOk_7239;
            if (!parseOk__484) {
                List<ChangesetError> eb__485 = new ArrayList<>(this._errors);
                Core.listAdd(eb__485, new ChangesetError(field__481.getSqlValue(), "must be an integer"));
                t_4137 = this._tableDef;
                t_4138 = this._params;
                t_4139 = this._changes;
                t_7101 = List.copyOf(eb__485);
                return__247 = new ChangesetImpl(t_4137, t_4138, t_4139, t_7101, false);
                break fn__482;
            }
            return__247 = this;
        }
        return return__247;
    }
    public Changeset validateInt64(SafeIdentifier field__487) {
        Changeset return__248;
        String t_7085;
        List<ChangesetError> t_7092;
        TableDef t_4124;
        Map<String, String> t_4125;
        Map<String, String> t_4126;
        fn__488: {
            if (!this._isValid) {
                return__248 = this;
                break fn__488;
            }
            t_7085 = field__487.getSqlValue();
            String val__489 = this._changes.getOrDefault(t_7085, "");
            if (val__489.isEmpty()) {
                return__248 = this;
                break fn__488;
            }
            boolean parseOk__490;
            boolean parseOk_7241;
            try {
                Core.stringToInt64(val__489);
                parseOk_7241 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_7241 = false;
            }
            parseOk__490 = parseOk_7241;
            if (!parseOk__490) {
                List<ChangesetError> eb__491 = new ArrayList<>(this._errors);
                Core.listAdd(eb__491, new ChangesetError(field__487.getSqlValue(), "must be a 64-bit integer"));
                t_4124 = this._tableDef;
                t_4125 = this._params;
                t_4126 = this._changes;
                t_7092 = List.copyOf(eb__491);
                return__248 = new ChangesetImpl(t_4124, t_4125, t_4126, t_7092, false);
                break fn__488;
            }
            return__248 = this;
        }
        return return__248;
    }
    public Changeset validateFloat(SafeIdentifier field__493) {
        Changeset return__249;
        String t_7076;
        List<ChangesetError> t_7083;
        TableDef t_4111;
        Map<String, String> t_4112;
        Map<String, String> t_4113;
        fn__494: {
            if (!this._isValid) {
                return__249 = this;
                break fn__494;
            }
            t_7076 = field__493.getSqlValue();
            String val__495 = this._changes.getOrDefault(t_7076, "");
            if (val__495.isEmpty()) {
                return__249 = this;
                break fn__494;
            }
            boolean parseOk__496;
            boolean parseOk_7243;
            try {
                Core.stringToFloat64(val__495);
                parseOk_7243 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_7243 = false;
            }
            parseOk__496 = parseOk_7243;
            if (!parseOk__496) {
                List<ChangesetError> eb__497 = new ArrayList<>(this._errors);
                Core.listAdd(eb__497, new ChangesetError(field__493.getSqlValue(), "must be a number"));
                t_4111 = this._tableDef;
                t_4112 = this._params;
                t_4113 = this._changes;
                t_7083 = List.copyOf(eb__497);
                return__249 = new ChangesetImpl(t_4111, t_4112, t_4113, t_7083, false);
                break fn__494;
            }
            return__249 = this;
        }
        return return__249;
    }
    public Changeset validateBool(SafeIdentifier field__499) {
        Changeset return__250;
        String t_7067;
        List<ChangesetError> t_7074;
        boolean t_4086;
        boolean t_4087;
        boolean t_4089;
        boolean t_4090;
        boolean t_4092;
        TableDef t_4098;
        Map<String, String> t_4099;
        Map<String, String> t_4100;
        fn__500: {
            if (!this._isValid) {
                return__250 = this;
                break fn__500;
            }
            t_7067 = field__499.getSqlValue();
            String val__501 = this._changes.getOrDefault(t_7067, "");
            if (val__501.isEmpty()) {
                return__250 = this;
                break fn__500;
            }
            boolean isTrue__502;
            if (val__501.equals("true")) {
                isTrue__502 = true;
            } else {
                if (val__501.equals("1")) {
                    t_4087 = true;
                } else {
                    if (val__501.equals("yes")) {
                        t_4086 = true;
                    } else {
                        t_4086 = val__501.equals("on");
                    }
                    t_4087 = t_4086;
                }
                isTrue__502 = t_4087;
            }
            boolean isFalse__503;
            if (val__501.equals("false")) {
                isFalse__503 = true;
            } else {
                if (val__501.equals("0")) {
                    t_4090 = true;
                } else {
                    if (val__501.equals("no")) {
                        t_4089 = true;
                    } else {
                        t_4089 = val__501.equals("off");
                    }
                    t_4090 = t_4089;
                }
                isFalse__503 = t_4090;
            }
            if (!isTrue__502) {
                t_4092 = !isFalse__503;
            } else {
                t_4092 = false;
            }
            if (t_4092) {
                List<ChangesetError> eb__504 = new ArrayList<>(this._errors);
                Core.listAdd(eb__504, new ChangesetError(field__499.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_4098 = this._tableDef;
                t_4099 = this._params;
                t_4100 = this._changes;
                t_7074 = List.copyOf(eb__504);
                return__250 = new ChangesetImpl(t_4098, t_4099, t_4100, t_7074, false);
                break fn__500;
            }
            return__250 = this;
        }
        return return__250;
    }
    SqlBoolean parseBoolSqlPart(String val__506) {
        SqlBoolean return__251;
        boolean t_4075;
        boolean t_4076;
        boolean t_4077;
        boolean t_4079;
        boolean t_4080;
        boolean t_4081;
        fn__507: {
            if (val__506.equals("true")) {
                t_4077 = true;
            } else {
                if (val__506.equals("1")) {
                    t_4076 = true;
                } else {
                    if (val__506.equals("yes")) {
                        t_4075 = true;
                    } else {
                        t_4075 = val__506.equals("on");
                    }
                    t_4076 = t_4075;
                }
                t_4077 = t_4076;
            }
            if (t_4077) {
                return__251 = new SqlBoolean(true);
                break fn__507;
            }
            if (val__506.equals("false")) {
                t_4081 = true;
            } else {
                if (val__506.equals("0")) {
                    t_4080 = true;
                } else {
                    if (val__506.equals("no")) {
                        t_4079 = true;
                    } else {
                        t_4079 = val__506.equals("off");
                    }
                    t_4080 = t_4079;
                }
                t_4081 = t_4080;
            }
            if (t_4081) {
                return__251 = new SqlBoolean(false);
                break fn__507;
            }
            throw Core.bubble();
        }
        return return__251;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__509, String val__510) {
        SqlPart return__252;
        int t_4062;
        long t_4065;
        double t_4068;
        LocalDate t_4073;
        fn__511: {
            FieldType ft__512 = fieldDef__509.getFieldType();
            if (ft__512 instanceof StringField) {
                return__252 = new SqlString(val__510);
                break fn__511;
            }
            if (ft__512 instanceof IntField) {
                t_4062 = Core.stringToInt(val__510);
                return__252 = new SqlInt32(t_4062);
                break fn__511;
            }
            if (ft__512 instanceof Int64Field) {
                t_4065 = Core.stringToInt64(val__510);
                return__252 = new SqlInt64(t_4065);
                break fn__511;
            }
            if (ft__512 instanceof FloatField) {
                t_4068 = Core.stringToFloat64(val__510);
                return__252 = new SqlFloat64(t_4068);
                break fn__511;
            }
            if (ft__512 instanceof BoolField) {
                return__252 = this.parseBoolSqlPart(val__510);
                break fn__511;
            }
            if (ft__512 instanceof DateField) {
                t_4073 = LocalDate.parse(val__510);
                return__252 = new SqlDate(t_4073);
                break fn__511;
            }
            throw Core.bubble();
        }
        return return__252;
    }
    public SqlFragment toInsertSql() {
        int t_7015;
        String t_7020;
        boolean t_7021;
        int t_7026;
        String t_7028;
        String t_7032;
        int t_7047;
        boolean t_4026;
        FieldDef t_4034;
        SqlPart t_4039;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__515 = 0;
        while (true) {
            t_7015 = this._tableDef.getFields().size();
            if (i__515 >= t_7015) {
                break;
            }
            FieldDef f__516 = Core.listGet(this._tableDef.getFields(), i__515);
            if (!f__516.isNullable()) {
                t_7020 = f__516.getName().getSqlValue();
                t_7021 = this._changes.containsKey(t_7020);
                t_4026 = !t_7021;
            } else {
                t_4026 = false;
            }
            if (t_4026) {
                throw Core.bubble();
            }
            i__515 = i__515 + 1;
        }
        List<Entry<String, String>> pairs__517 = Core.mappedToList(this._changes);
        if (pairs__517.size() == 0) {
            throw Core.bubble();
        }
        List<String> colNames__518 = new ArrayList<>();
        List<SqlPart> valParts__519 = new ArrayList<>();
        int i__520 = 0;
        while (true) {
            t_7026 = pairs__517.size();
            if (i__520 >= t_7026) {
                break;
            }
            Entry<String, String> pair__521 = Core.listGet(pairs__517, i__520);
            t_7028 = pair__521.getKey();
            t_4034 = this._tableDef.field(t_7028);
            FieldDef fd__522 = t_4034;
            Core.listAdd(colNames__518, fd__522.getName().getSqlValue());
            t_7032 = pair__521.getValue();
            t_4039 = this.valueToSqlPart(fd__522, t_7032);
            Core.listAdd(valParts__519, t_4039);
            i__520 = i__520 + 1;
        }
        SqlBuilder b__523 = new SqlBuilder();
        b__523.appendSafe("INSERT INTO ");
        b__523.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__523.appendSafe(" (");
        List<String> t_7040 = List.copyOf(colNames__518);
        Function<String, String> fn__7013 = c__524 -> c__524;
        b__523.appendSafe(Core.listJoinObj(t_7040, ", ", fn__7013));
        b__523.appendSafe(") VALUES (");
        b__523.appendPart(Core.listGet(valParts__519, 0));
        int j__525 = 1;
        while (true) {
            t_7047 = valParts__519.size();
            if (j__525 >= t_7047) {
                break;
            }
            b__523.appendSafe(", ");
            b__523.appendPart(Core.listGet(valParts__519, j__525));
            j__525 = j__525 + 1;
        }
        b__523.appendSafe(")");
        return b__523.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__527) {
        int t_7000;
        String t_7003;
        String t_7008;
        FieldDef t_4007;
        SqlPart t_4013;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__529 = Core.mappedToList(this._changes);
        if (pairs__529.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__530 = new SqlBuilder();
        b__530.appendSafe("UPDATE ");
        b__530.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__530.appendSafe(" SET ");
        int i__531 = 0;
        while (true) {
            t_7000 = pairs__529.size();
            if (i__531 >= t_7000) {
                break;
            }
            if (i__531 > 0) {
                b__530.appendSafe(", ");
            }
            Entry<String, String> pair__532 = Core.listGet(pairs__529, i__531);
            t_7003 = pair__532.getKey();
            t_4007 = this._tableDef.field(t_7003);
            FieldDef fd__533 = t_4007;
            b__530.appendSafe(fd__533.getName().getSqlValue());
            b__530.appendSafe(" = ");
            t_7008 = pair__532.getValue();
            t_4013 = this.valueToSqlPart(fd__533, t_7008);
            b__530.appendPart(t_4013);
            i__531 = i__531 + 1;
        }
        b__530.appendSafe(" WHERE id = ");
        b__530.appendInt32(id__527);
        return b__530.getAccumulated();
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
    public ChangesetImpl(TableDef _tableDef__535, Map<String, String> _params__536, Map<String, String> _changes__537, List<ChangesetError> _errors__538, boolean _isValid__539) {
        this._tableDef = _tableDef__535;
        this._params = _params__536;
        this._changes = _changes__537;
        this._errors = _errors__538;
        this._isValid = _isValid__539;
    }
}
