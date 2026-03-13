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
    public Changeset cast(List<SafeIdentifier> allowedFields__606) {
        Map<String, String> mb__608 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__11363 = f__609 -> {
            String t_11361;
            String t_11358 = f__609.getSqlValue();
            String val__610 = this._params.getOrDefault(t_11358, "");
            if (!val__610.isEmpty()) {
                t_11361 = f__609.getSqlValue();
                mb__608.put(t_11361, val__610);
            }
        };
        allowedFields__606.forEach(fn__11363);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__608), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__612) {
        Changeset return__324;
        List<ChangesetError> t_11356;
        TableDef t_6537;
        Map<String, String> t_6538;
        Map<String, String> t_6539;
        fn__613: {
            if (!this._isValid) {
                return__324 = this;
                break fn__613;
            }
            List<ChangesetError> eb__614 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__615 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__11352 = f__616 -> {
                ChangesetError t_11350;
                String t_11347 = f__616.getSqlValue();
                if (!this._changes.containsKey(t_11347)) {
                    t_11350 = new ChangesetError(f__616.getSqlValue(), "is required");
                    Core.listAdd(eb__614, t_11350);
                    local$1.valid__615 = false;
                }
            };
            fields__612.forEach(fn__11352);
            t_6537 = this._tableDef;
            t_6538 = this._params;
            t_6539 = this._changes;
            t_11356 = List.copyOf(eb__614);
            return__324 = new ChangesetImpl(t_6537, t_6538, t_6539, t_11356, local$1.valid__615);
        }
        return return__324;
    }
    public Changeset validateLength(SafeIdentifier field__618, int min__619, int max__620) {
        Changeset return__325;
        String t_11334;
        List<ChangesetError> t_11345;
        boolean t_6520;
        TableDef t_6526;
        Map<String, String> t_6527;
        Map<String, String> t_6528;
        fn__621: {
            if (!this._isValid) {
                return__325 = this;
                break fn__621;
            }
            t_11334 = field__618.getSqlValue();
            String val__622 = this._changes.getOrDefault(t_11334, "");
            int len__623 = Core.stringCountBetween(val__622, 0, val__622.length());
            if (len__623 < min__619) {
                t_6520 = true;
            } else {
                t_6520 = len__623 > max__620;
            }
            if (t_6520) {
                String msg__624 = "must be between " + Integer.toString(min__619) + " and " + Integer.toString(max__620) + " characters";
                List<ChangesetError> eb__625 = new ArrayList<>(this._errors);
                Core.listAdd(eb__625, new ChangesetError(field__618.getSqlValue(), msg__624));
                t_6526 = this._tableDef;
                t_6527 = this._params;
                t_6528 = this._changes;
                t_11345 = List.copyOf(eb__625);
                return__325 = new ChangesetImpl(t_6526, t_6527, t_6528, t_11345, false);
                break fn__621;
            }
            return__325 = this;
        }
        return return__325;
    }
    public Changeset validateInt(SafeIdentifier field__627) {
        Changeset return__326;
        String t_11325;
        List<ChangesetError> t_11332;
        TableDef t_6511;
        Map<String, String> t_6512;
        Map<String, String> t_6513;
        fn__628: {
            if (!this._isValid) {
                return__326 = this;
                break fn__628;
            }
            t_11325 = field__627.getSqlValue();
            String val__629 = this._changes.getOrDefault(t_11325, "");
            if (val__629.isEmpty()) {
                return__326 = this;
                break fn__628;
            }
            boolean parseOk__630;
            boolean parseOk_11470;
            try {
                Core.stringToInt(val__629);
                parseOk_11470 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_11470 = false;
            }
            parseOk__630 = parseOk_11470;
            if (!parseOk__630) {
                List<ChangesetError> eb__631 = new ArrayList<>(this._errors);
                Core.listAdd(eb__631, new ChangesetError(field__627.getSqlValue(), "must be an integer"));
                t_6511 = this._tableDef;
                t_6512 = this._params;
                t_6513 = this._changes;
                t_11332 = List.copyOf(eb__631);
                return__326 = new ChangesetImpl(t_6511, t_6512, t_6513, t_11332, false);
                break fn__628;
            }
            return__326 = this;
        }
        return return__326;
    }
    public Changeset validateInt64(SafeIdentifier field__633) {
        Changeset return__327;
        String t_11316;
        List<ChangesetError> t_11323;
        TableDef t_6498;
        Map<String, String> t_6499;
        Map<String, String> t_6500;
        fn__634: {
            if (!this._isValid) {
                return__327 = this;
                break fn__634;
            }
            t_11316 = field__633.getSqlValue();
            String val__635 = this._changes.getOrDefault(t_11316, "");
            if (val__635.isEmpty()) {
                return__327 = this;
                break fn__634;
            }
            boolean parseOk__636;
            boolean parseOk_11472;
            try {
                Core.stringToInt64(val__635);
                parseOk_11472 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_11472 = false;
            }
            parseOk__636 = parseOk_11472;
            if (!parseOk__636) {
                List<ChangesetError> eb__637 = new ArrayList<>(this._errors);
                Core.listAdd(eb__637, new ChangesetError(field__633.getSqlValue(), "must be a 64-bit integer"));
                t_6498 = this._tableDef;
                t_6499 = this._params;
                t_6500 = this._changes;
                t_11323 = List.copyOf(eb__637);
                return__327 = new ChangesetImpl(t_6498, t_6499, t_6500, t_11323, false);
                break fn__634;
            }
            return__327 = this;
        }
        return return__327;
    }
    public Changeset validateFloat(SafeIdentifier field__639) {
        Changeset return__328;
        String t_11307;
        List<ChangesetError> t_11314;
        TableDef t_6485;
        Map<String, String> t_6486;
        Map<String, String> t_6487;
        fn__640: {
            if (!this._isValid) {
                return__328 = this;
                break fn__640;
            }
            t_11307 = field__639.getSqlValue();
            String val__641 = this._changes.getOrDefault(t_11307, "");
            if (val__641.isEmpty()) {
                return__328 = this;
                break fn__640;
            }
            boolean parseOk__642;
            boolean parseOk_11474;
            try {
                Core.stringToFloat64(val__641);
                parseOk_11474 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_11474 = false;
            }
            parseOk__642 = parseOk_11474;
            if (!parseOk__642) {
                List<ChangesetError> eb__643 = new ArrayList<>(this._errors);
                Core.listAdd(eb__643, new ChangesetError(field__639.getSqlValue(), "must be a number"));
                t_6485 = this._tableDef;
                t_6486 = this._params;
                t_6487 = this._changes;
                t_11314 = List.copyOf(eb__643);
                return__328 = new ChangesetImpl(t_6485, t_6486, t_6487, t_11314, false);
                break fn__640;
            }
            return__328 = this;
        }
        return return__328;
    }
    public Changeset validateBool(SafeIdentifier field__645) {
        Changeset return__329;
        String t_11298;
        List<ChangesetError> t_11305;
        boolean t_6460;
        boolean t_6461;
        boolean t_6463;
        boolean t_6464;
        boolean t_6466;
        TableDef t_6472;
        Map<String, String> t_6473;
        Map<String, String> t_6474;
        fn__646: {
            if (!this._isValid) {
                return__329 = this;
                break fn__646;
            }
            t_11298 = field__645.getSqlValue();
            String val__647 = this._changes.getOrDefault(t_11298, "");
            if (val__647.isEmpty()) {
                return__329 = this;
                break fn__646;
            }
            boolean isTrue__648;
            if (val__647.equals("true")) {
                isTrue__648 = true;
            } else {
                if (val__647.equals("1")) {
                    t_6461 = true;
                } else {
                    if (val__647.equals("yes")) {
                        t_6460 = true;
                    } else {
                        t_6460 = val__647.equals("on");
                    }
                    t_6461 = t_6460;
                }
                isTrue__648 = t_6461;
            }
            boolean isFalse__649;
            if (val__647.equals("false")) {
                isFalse__649 = true;
            } else {
                if (val__647.equals("0")) {
                    t_6464 = true;
                } else {
                    if (val__647.equals("no")) {
                        t_6463 = true;
                    } else {
                        t_6463 = val__647.equals("off");
                    }
                    t_6464 = t_6463;
                }
                isFalse__649 = t_6464;
            }
            if (!isTrue__648) {
                t_6466 = !isFalse__649;
            } else {
                t_6466 = false;
            }
            if (t_6466) {
                List<ChangesetError> eb__650 = new ArrayList<>(this._errors);
                Core.listAdd(eb__650, new ChangesetError(field__645.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_6472 = this._tableDef;
                t_6473 = this._params;
                t_6474 = this._changes;
                t_11305 = List.copyOf(eb__650);
                return__329 = new ChangesetImpl(t_6472, t_6473, t_6474, t_11305, false);
                break fn__646;
            }
            return__329 = this;
        }
        return return__329;
    }
    SqlBoolean parseBoolSqlPart(String val__652) {
        SqlBoolean return__330;
        boolean t_6449;
        boolean t_6450;
        boolean t_6451;
        boolean t_6453;
        boolean t_6454;
        boolean t_6455;
        fn__653: {
            if (val__652.equals("true")) {
                t_6451 = true;
            } else {
                if (val__652.equals("1")) {
                    t_6450 = true;
                } else {
                    if (val__652.equals("yes")) {
                        t_6449 = true;
                    } else {
                        t_6449 = val__652.equals("on");
                    }
                    t_6450 = t_6449;
                }
                t_6451 = t_6450;
            }
            if (t_6451) {
                return__330 = new SqlBoolean(true);
                break fn__653;
            }
            if (val__652.equals("false")) {
                t_6455 = true;
            } else {
                if (val__652.equals("0")) {
                    t_6454 = true;
                } else {
                    if (val__652.equals("no")) {
                        t_6453 = true;
                    } else {
                        t_6453 = val__652.equals("off");
                    }
                    t_6454 = t_6453;
                }
                t_6455 = t_6454;
            }
            if (t_6455) {
                return__330 = new SqlBoolean(false);
                break fn__653;
            }
            throw Core.bubble();
        }
        return return__330;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__655, String val__656) {
        SqlPart return__331;
        int t_6436;
        long t_6439;
        double t_6442;
        LocalDate t_6447;
        fn__657: {
            FieldType ft__658 = fieldDef__655.getFieldType();
            if (ft__658 instanceof StringField) {
                return__331 = new SqlString(val__656);
                break fn__657;
            }
            if (ft__658 instanceof IntField) {
                t_6436 = Core.stringToInt(val__656);
                return__331 = new SqlInt32(t_6436);
                break fn__657;
            }
            if (ft__658 instanceof Int64Field) {
                t_6439 = Core.stringToInt64(val__656);
                return__331 = new SqlInt64(t_6439);
                break fn__657;
            }
            if (ft__658 instanceof FloatField) {
                t_6442 = Core.stringToFloat64(val__656);
                return__331 = new SqlFloat64(t_6442);
                break fn__657;
            }
            if (ft__658 instanceof BoolField) {
                return__331 = this.parseBoolSqlPart(val__656);
                break fn__657;
            }
            if (ft__658 instanceof DateField) {
                t_6447 = LocalDate.parse(val__656);
                return__331 = new SqlDate(t_6447);
                break fn__657;
            }
            throw Core.bubble();
        }
        return return__331;
    }
    public SqlFragment toInsertSql() {
        int t_11246;
        String t_11251;
        boolean t_11252;
        int t_11257;
        String t_11259;
        String t_11263;
        int t_11278;
        boolean t_6400;
        FieldDef t_6408;
        SqlPart t_6413;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__661 = 0;
        while (true) {
            t_11246 = this._tableDef.getFields().size();
            if (i__661 >= t_11246) {
                break;
            }
            FieldDef f__662 = Core.listGet(this._tableDef.getFields(), i__661);
            if (!f__662.isNullable()) {
                t_11251 = f__662.getName().getSqlValue();
                t_11252 = this._changes.containsKey(t_11251);
                t_6400 = !t_11252;
            } else {
                t_6400 = false;
            }
            if (t_6400) {
                throw Core.bubble();
            }
            i__661 = i__661 + 1;
        }
        List<Entry<String, String>> pairs__663 = Core.mappedToList(this._changes);
        if (pairs__663.size() == 0) {
            throw Core.bubble();
        }
        List<String> colNames__664 = new ArrayList<>();
        List<SqlPart> valParts__665 = new ArrayList<>();
        int i__666 = 0;
        while (true) {
            t_11257 = pairs__663.size();
            if (i__666 >= t_11257) {
                break;
            }
            Entry<String, String> pair__667 = Core.listGet(pairs__663, i__666);
            t_11259 = pair__667.getKey();
            t_6408 = this._tableDef.field(t_11259);
            FieldDef fd__668 = t_6408;
            Core.listAdd(colNames__664, fd__668.getName().getSqlValue());
            t_11263 = pair__667.getValue();
            t_6413 = this.valueToSqlPart(fd__668, t_11263);
            Core.listAdd(valParts__665, t_6413);
            i__666 = i__666 + 1;
        }
        SqlBuilder b__669 = new SqlBuilder();
        b__669.appendSafe("INSERT INTO ");
        b__669.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__669.appendSafe(" (");
        List<String> t_11271 = List.copyOf(colNames__664);
        Function<String, String> fn__11244 = c__670 -> c__670;
        b__669.appendSafe(Core.listJoinObj(t_11271, ", ", fn__11244));
        b__669.appendSafe(") VALUES (");
        b__669.appendPart(Core.listGet(valParts__665, 0));
        int j__671 = 1;
        while (true) {
            t_11278 = valParts__665.size();
            if (j__671 >= t_11278) {
                break;
            }
            b__669.appendSafe(", ");
            b__669.appendPart(Core.listGet(valParts__665, j__671));
            j__671 = j__671 + 1;
        }
        b__669.appendSafe(")");
        return b__669.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__673) {
        int t_11231;
        String t_11234;
        String t_11239;
        FieldDef t_6381;
        SqlPart t_6387;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__675 = Core.mappedToList(this._changes);
        if (pairs__675.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__676 = new SqlBuilder();
        b__676.appendSafe("UPDATE ");
        b__676.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__676.appendSafe(" SET ");
        int i__677 = 0;
        while (true) {
            t_11231 = pairs__675.size();
            if (i__677 >= t_11231) {
                break;
            }
            if (i__677 > 0) {
                b__676.appendSafe(", ");
            }
            Entry<String, String> pair__678 = Core.listGet(pairs__675, i__677);
            t_11234 = pair__678.getKey();
            t_6381 = this._tableDef.field(t_11234);
            FieldDef fd__679 = t_6381;
            b__676.appendSafe(fd__679.getName().getSqlValue());
            b__676.appendSafe(" = ");
            t_11239 = pair__678.getValue();
            t_6387 = this.valueToSqlPart(fd__679, t_11239);
            b__676.appendPart(t_6387);
            i__677 = i__677 + 1;
        }
        b__676.appendSafe(" WHERE id = ");
        b__676.appendInt32(id__673);
        return b__676.getAccumulated();
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
    public ChangesetImpl(TableDef _tableDef__681, Map<String, String> _params__682, Map<String, String> _changes__683, List<ChangesetError> _errors__684, boolean _isValid__685) {
        this._tableDef = _tableDef__681;
        this._params = _params__682;
        this._changes = _changes__683;
        this._errors = _errors__684;
        this._isValid = _isValid__685;
    }
}
