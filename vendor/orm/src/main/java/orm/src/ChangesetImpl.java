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
    public Changeset cast(List<SafeIdentifier> allowedFields__404) {
        Map<String, String> mb__406 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__5700 = f__407 -> {
            String t_5698;
            String t_5695 = f__407.getSqlValue();
            String val__408 = this._params.getOrDefault(t_5695, "");
            if (!val__408.isEmpty()) {
                t_5698 = f__407.getSqlValue();
                mb__406.put(t_5698, val__408);
            }
        };
        allowedFields__404.forEach(fn__5700);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__406), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__410) {
        Changeset return__210;
        List<ChangesetError> t_5693;
        TableDef t_3382;
        Map<String, String> t_3383;
        Map<String, String> t_3384;
        fn__411: {
            if (!this._isValid) {
                return__210 = this;
                break fn__411;
            }
            List<ChangesetError> eb__412 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__413 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__5689 = f__414 -> {
                ChangesetError t_5687;
                String t_5684 = f__414.getSqlValue();
                if (!this._changes.containsKey(t_5684)) {
                    t_5687 = new ChangesetError(f__414.getSqlValue(), "is required");
                    Core.listAdd(eb__412, t_5687);
                    local$1.valid__413 = false;
                }
            };
            fields__410.forEach(fn__5689);
            t_3382 = this._tableDef;
            t_3383 = this._params;
            t_3384 = this._changes;
            t_5693 = List.copyOf(eb__412);
            return__210 = new ChangesetImpl(t_3382, t_3383, t_3384, t_5693, local$1.valid__413);
        }
        return return__210;
    }
    public Changeset validateLength(SafeIdentifier field__416, int min__417, int max__418) {
        Changeset return__211;
        String t_5671;
        List<ChangesetError> t_5682;
        boolean t_3365;
        TableDef t_3371;
        Map<String, String> t_3372;
        Map<String, String> t_3373;
        fn__419: {
            if (!this._isValid) {
                return__211 = this;
                break fn__419;
            }
            t_5671 = field__416.getSqlValue();
            String val__420 = this._changes.getOrDefault(t_5671, "");
            int len__421 = Core.stringCountBetween(val__420, 0, val__420.length());
            if (len__421 < min__417) {
                t_3365 = true;
            } else {
                t_3365 = len__421 > max__418;
            }
            if (t_3365) {
                String msg__422 = "must be between " + Integer.toString(min__417) + " and " + Integer.toString(max__418) + " characters";
                List<ChangesetError> eb__423 = new ArrayList<>(this._errors);
                Core.listAdd(eb__423, new ChangesetError(field__416.getSqlValue(), msg__422));
                t_3371 = this._tableDef;
                t_3372 = this._params;
                t_3373 = this._changes;
                t_5682 = List.copyOf(eb__423);
                return__211 = new ChangesetImpl(t_3371, t_3372, t_3373, t_5682, false);
                break fn__419;
            }
            return__211 = this;
        }
        return return__211;
    }
    public Changeset validateInt(SafeIdentifier field__425) {
        Changeset return__212;
        String t_5662;
        List<ChangesetError> t_5669;
        TableDef t_3356;
        Map<String, String> t_3357;
        Map<String, String> t_3358;
        fn__426: {
            if (!this._isValid) {
                return__212 = this;
                break fn__426;
            }
            t_5662 = field__425.getSqlValue();
            String val__427 = this._changes.getOrDefault(t_5662, "");
            if (val__427.isEmpty()) {
                return__212 = this;
                break fn__426;
            }
            boolean parseOk__428;
            boolean parseOk_5807;
            try {
                Core.stringToInt(val__427);
                parseOk_5807 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_5807 = false;
            }
            parseOk__428 = parseOk_5807;
            if (!parseOk__428) {
                List<ChangesetError> eb__429 = new ArrayList<>(this._errors);
                Core.listAdd(eb__429, new ChangesetError(field__425.getSqlValue(), "must be an integer"));
                t_3356 = this._tableDef;
                t_3357 = this._params;
                t_3358 = this._changes;
                t_5669 = List.copyOf(eb__429);
                return__212 = new ChangesetImpl(t_3356, t_3357, t_3358, t_5669, false);
                break fn__426;
            }
            return__212 = this;
        }
        return return__212;
    }
    public Changeset validateInt64(SafeIdentifier field__431) {
        Changeset return__213;
        String t_5653;
        List<ChangesetError> t_5660;
        TableDef t_3343;
        Map<String, String> t_3344;
        Map<String, String> t_3345;
        fn__432: {
            if (!this._isValid) {
                return__213 = this;
                break fn__432;
            }
            t_5653 = field__431.getSqlValue();
            String val__433 = this._changes.getOrDefault(t_5653, "");
            if (val__433.isEmpty()) {
                return__213 = this;
                break fn__432;
            }
            boolean parseOk__434;
            boolean parseOk_5809;
            try {
                Core.stringToInt64(val__433);
                parseOk_5809 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_5809 = false;
            }
            parseOk__434 = parseOk_5809;
            if (!parseOk__434) {
                List<ChangesetError> eb__435 = new ArrayList<>(this._errors);
                Core.listAdd(eb__435, new ChangesetError(field__431.getSqlValue(), "must be a 64-bit integer"));
                t_3343 = this._tableDef;
                t_3344 = this._params;
                t_3345 = this._changes;
                t_5660 = List.copyOf(eb__435);
                return__213 = new ChangesetImpl(t_3343, t_3344, t_3345, t_5660, false);
                break fn__432;
            }
            return__213 = this;
        }
        return return__213;
    }
    public Changeset validateFloat(SafeIdentifier field__437) {
        Changeset return__214;
        String t_5644;
        List<ChangesetError> t_5651;
        TableDef t_3330;
        Map<String, String> t_3331;
        Map<String, String> t_3332;
        fn__438: {
            if (!this._isValid) {
                return__214 = this;
                break fn__438;
            }
            t_5644 = field__437.getSqlValue();
            String val__439 = this._changes.getOrDefault(t_5644, "");
            if (val__439.isEmpty()) {
                return__214 = this;
                break fn__438;
            }
            boolean parseOk__440;
            boolean parseOk_5811;
            try {
                Core.stringToFloat64(val__439);
                parseOk_5811 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_5811 = false;
            }
            parseOk__440 = parseOk_5811;
            if (!parseOk__440) {
                List<ChangesetError> eb__441 = new ArrayList<>(this._errors);
                Core.listAdd(eb__441, new ChangesetError(field__437.getSqlValue(), "must be a number"));
                t_3330 = this._tableDef;
                t_3331 = this._params;
                t_3332 = this._changes;
                t_5651 = List.copyOf(eb__441);
                return__214 = new ChangesetImpl(t_3330, t_3331, t_3332, t_5651, false);
                break fn__438;
            }
            return__214 = this;
        }
        return return__214;
    }
    public Changeset validateBool(SafeIdentifier field__443) {
        Changeset return__215;
        String t_5635;
        List<ChangesetError> t_5642;
        boolean t_3305;
        boolean t_3306;
        boolean t_3308;
        boolean t_3309;
        boolean t_3311;
        TableDef t_3317;
        Map<String, String> t_3318;
        Map<String, String> t_3319;
        fn__444: {
            if (!this._isValid) {
                return__215 = this;
                break fn__444;
            }
            t_5635 = field__443.getSqlValue();
            String val__445 = this._changes.getOrDefault(t_5635, "");
            if (val__445.isEmpty()) {
                return__215 = this;
                break fn__444;
            }
            boolean isTrue__446;
            if (val__445.equals("true")) {
                isTrue__446 = true;
            } else {
                if (val__445.equals("1")) {
                    t_3306 = true;
                } else {
                    if (val__445.equals("yes")) {
                        t_3305 = true;
                    } else {
                        t_3305 = val__445.equals("on");
                    }
                    t_3306 = t_3305;
                }
                isTrue__446 = t_3306;
            }
            boolean isFalse__447;
            if (val__445.equals("false")) {
                isFalse__447 = true;
            } else {
                if (val__445.equals("0")) {
                    t_3309 = true;
                } else {
                    if (val__445.equals("no")) {
                        t_3308 = true;
                    } else {
                        t_3308 = val__445.equals("off");
                    }
                    t_3309 = t_3308;
                }
                isFalse__447 = t_3309;
            }
            if (!isTrue__446) {
                t_3311 = !isFalse__447;
            } else {
                t_3311 = false;
            }
            if (t_3311) {
                List<ChangesetError> eb__448 = new ArrayList<>(this._errors);
                Core.listAdd(eb__448, new ChangesetError(field__443.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_3317 = this._tableDef;
                t_3318 = this._params;
                t_3319 = this._changes;
                t_5642 = List.copyOf(eb__448);
                return__215 = new ChangesetImpl(t_3317, t_3318, t_3319, t_5642, false);
                break fn__444;
            }
            return__215 = this;
        }
        return return__215;
    }
    SqlBoolean parseBoolSqlPart(String val__450) {
        SqlBoolean return__216;
        boolean t_3294;
        boolean t_3295;
        boolean t_3296;
        boolean t_3298;
        boolean t_3299;
        boolean t_3300;
        fn__451: {
            if (val__450.equals("true")) {
                t_3296 = true;
            } else {
                if (val__450.equals("1")) {
                    t_3295 = true;
                } else {
                    if (val__450.equals("yes")) {
                        t_3294 = true;
                    } else {
                        t_3294 = val__450.equals("on");
                    }
                    t_3295 = t_3294;
                }
                t_3296 = t_3295;
            }
            if (t_3296) {
                return__216 = new SqlBoolean(true);
                break fn__451;
            }
            if (val__450.equals("false")) {
                t_3300 = true;
            } else {
                if (val__450.equals("0")) {
                    t_3299 = true;
                } else {
                    if (val__450.equals("no")) {
                        t_3298 = true;
                    } else {
                        t_3298 = val__450.equals("off");
                    }
                    t_3299 = t_3298;
                }
                t_3300 = t_3299;
            }
            if (t_3300) {
                return__216 = new SqlBoolean(false);
                break fn__451;
            }
            throw Core.bubble();
        }
        return return__216;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__453, String val__454) {
        SqlPart return__217;
        int t_3281;
        long t_3284;
        double t_3287;
        LocalDate t_3292;
        fn__455: {
            FieldType ft__456 = fieldDef__453.getFieldType();
            if (ft__456 instanceof StringField) {
                return__217 = new SqlString(val__454);
                break fn__455;
            }
            if (ft__456 instanceof IntField) {
                t_3281 = Core.stringToInt(val__454);
                return__217 = new SqlInt32(t_3281);
                break fn__455;
            }
            if (ft__456 instanceof Int64Field) {
                t_3284 = Core.stringToInt64(val__454);
                return__217 = new SqlInt64(t_3284);
                break fn__455;
            }
            if (ft__456 instanceof FloatField) {
                t_3287 = Core.stringToFloat64(val__454);
                return__217 = new SqlFloat64(t_3287);
                break fn__455;
            }
            if (ft__456 instanceof BoolField) {
                return__217 = this.parseBoolSqlPart(val__454);
                break fn__455;
            }
            if (ft__456 instanceof DateField) {
                t_3292 = LocalDate.parse(val__454);
                return__217 = new SqlDate(t_3292);
                break fn__455;
            }
            throw Core.bubble();
        }
        return return__217;
    }
    public SqlFragment toInsertSql() {
        int t_5583;
        String t_5588;
        boolean t_5589;
        int t_5594;
        String t_5596;
        String t_5600;
        int t_5615;
        boolean t_3245;
        FieldDef t_3253;
        SqlPart t_3258;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__459 = 0;
        while (true) {
            t_5583 = this._tableDef.getFields().size();
            if (i__459 >= t_5583) {
                break;
            }
            FieldDef f__460 = Core.listGet(this._tableDef.getFields(), i__459);
            if (!f__460.isNullable()) {
                t_5588 = f__460.getName().getSqlValue();
                t_5589 = this._changes.containsKey(t_5588);
                t_3245 = !t_5589;
            } else {
                t_3245 = false;
            }
            if (t_3245) {
                throw Core.bubble();
            }
            i__459 = i__459 + 1;
        }
        List<Entry<String, String>> pairs__461 = Core.mappedToList(this._changes);
        if (pairs__461.size() == 0) {
            throw Core.bubble();
        }
        List<String> colNames__462 = new ArrayList<>();
        List<SqlPart> valParts__463 = new ArrayList<>();
        int i__464 = 0;
        while (true) {
            t_5594 = pairs__461.size();
            if (i__464 >= t_5594) {
                break;
            }
            Entry<String, String> pair__465 = Core.listGet(pairs__461, i__464);
            t_5596 = pair__465.getKey();
            t_3253 = this._tableDef.field(t_5596);
            FieldDef fd__466 = t_3253;
            Core.listAdd(colNames__462, fd__466.getName().getSqlValue());
            t_5600 = pair__465.getValue();
            t_3258 = this.valueToSqlPart(fd__466, t_5600);
            Core.listAdd(valParts__463, t_3258);
            i__464 = i__464 + 1;
        }
        SqlBuilder b__467 = new SqlBuilder();
        b__467.appendSafe("INSERT INTO ");
        b__467.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__467.appendSafe(" (");
        List<String> t_5608 = List.copyOf(colNames__462);
        Function<String, String> fn__5581 = c__468 -> c__468;
        b__467.appendSafe(Core.listJoinObj(t_5608, ", ", fn__5581));
        b__467.appendSafe(") VALUES (");
        b__467.appendPart(Core.listGet(valParts__463, 0));
        int j__469 = 1;
        while (true) {
            t_5615 = valParts__463.size();
            if (j__469 >= t_5615) {
                break;
            }
            b__467.appendSafe(", ");
            b__467.appendPart(Core.listGet(valParts__463, j__469));
            j__469 = j__469 + 1;
        }
        b__467.appendSafe(")");
        return b__467.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__471) {
        int t_5568;
        String t_5571;
        String t_5576;
        FieldDef t_3226;
        SqlPart t_3232;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__473 = Core.mappedToList(this._changes);
        if (pairs__473.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__474 = new SqlBuilder();
        b__474.appendSafe("UPDATE ");
        b__474.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__474.appendSafe(" SET ");
        int i__475 = 0;
        while (true) {
            t_5568 = pairs__473.size();
            if (i__475 >= t_5568) {
                break;
            }
            if (i__475 > 0) {
                b__474.appendSafe(", ");
            }
            Entry<String, String> pair__476 = Core.listGet(pairs__473, i__475);
            t_5571 = pair__476.getKey();
            t_3226 = this._tableDef.field(t_5571);
            FieldDef fd__477 = t_3226;
            b__474.appendSafe(fd__477.getName().getSqlValue());
            b__474.appendSafe(" = ");
            t_5576 = pair__476.getValue();
            t_3232 = this.valueToSqlPart(fd__477, t_5576);
            b__474.appendPart(t_3232);
            i__475 = i__475 + 1;
        }
        b__474.appendSafe(" WHERE id = ");
        b__474.appendInt32(id__471);
        return b__474.getAccumulated();
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
    public ChangesetImpl(TableDef _tableDef__479, Map<String, String> _params__480, Map<String, String> _changes__481, List<ChangesetError> _errors__482, boolean _isValid__483) {
        this._tableDef = _tableDef__479;
        this._params = _params__480;
        this._changes = _changes__481;
        this._errors = _errors__482;
        this._isValid = _isValid__483;
    }
}
