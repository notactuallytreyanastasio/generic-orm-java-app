package orm.src;
import temper.core.Core;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import temper.core.Nullable;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.time.LocalDate;
import java.util.function.Function;
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
    Changeset addError(String field__821, String message__822) {
        List<ChangesetError> eb__824 = new ArrayList<>(this._errors);
        Core.listAdd(eb__824, new ChangesetError(field__821, message__822));
        return new ChangesetImpl(this._tableDef, this._params, this._changes, List.copyOf(eb__824), false);
    }
    public Changeset cast(List<SafeIdentifier> allowedFields__826) {
        Map<String, String> mb__828 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__17273 = f__829 -> {
            String t_17271;
            String t_17268 = f__829.getSqlValue();
            String val__830 = this._params.getOrDefault(t_17268, "");
            if (!val__830.isEmpty()) {
                t_17271 = f__829.getSqlValue();
                mb__828.put(t_17271, val__830);
            }
        };
        allowedFields__826.forEach(fn__17273);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__828), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__832) {
        Changeset return__461;
        List<ChangesetError> t_17266;
        TableDef t_9729;
        Map<String, String> t_9730;
        Map<String, String> t_9731;
        fn__833: {
            if (!this._isValid) {
                return__461 = this;
                break fn__833;
            }
            List<ChangesetError> eb__834 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__835 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__17262 = f__836 -> {
                ChangesetError t_17260;
                String t_17257 = f__836.getSqlValue();
                if (!this._changes.containsKey(t_17257)) {
                    t_17260 = new ChangesetError(f__836.getSqlValue(), "is required");
                    Core.listAdd(eb__834, t_17260);
                    local$1.valid__835 = false;
                }
            };
            fields__832.forEach(fn__17262);
            t_9729 = this._tableDef;
            t_9730 = this._params;
            t_9731 = this._changes;
            t_17266 = List.copyOf(eb__834);
            return__461 = new ChangesetImpl(t_9729, t_9730, t_9731, t_17266, local$1.valid__835);
        }
        return return__461;
    }
    public Changeset validateLength(SafeIdentifier field__838, int min__839, int max__840) {
        Changeset return__462;
        String t_17248;
        String t_17253;
        String t_17254;
        String t_17255;
        boolean t_9719;
        fn__841: {
            if (!this._isValid) {
                return__462 = this;
                break fn__841;
            }
            t_17248 = field__838.getSqlValue();
            String val__842 = this._changes.getOrDefault(t_17248, "");
            int len__843 = Core.stringCountBetween(val__842, 0, val__842.length());
            if (len__843 < min__839) {
                t_9719 = true;
            } else {
                t_9719 = len__843 > max__840;
            }
            if (t_9719) {
                t_17253 = field__838.getSqlValue();
                t_17254 = Integer.toString(min__839);
                t_17255 = Integer.toString(max__840);
                return__462 = this.addError(t_17253, "must be between " + t_17254 + " and " + t_17255 + " characters");
                break fn__841;
            }
            return__462 = this;
        }
        return return__462;
    }
    public Changeset validateInt(SafeIdentifier field__845) {
        Changeset return__463;
        String t_17243;
        String t_17246;
        fn__846: {
            if (!this._isValid) {
                return__463 = this;
                break fn__846;
            }
            t_17243 = field__845.getSqlValue();
            String val__847 = this._changes.getOrDefault(t_17243, "");
            if (val__847.isEmpty()) {
                return__463 = this;
                break fn__846;
            }
            boolean parseOk__848;
            boolean parseOk_17393;
            try {
                Core.stringToInt(val__847);
                parseOk_17393 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_17393 = false;
            }
            parseOk__848 = parseOk_17393;
            if (!parseOk__848) {
                t_17246 = field__845.getSqlValue();
                return__463 = this.addError(t_17246, "must be an integer");
                break fn__846;
            }
            return__463 = this;
        }
        return return__463;
    }
    public Changeset validateInt64(SafeIdentifier field__850) {
        Changeset return__464;
        String t_17238;
        String t_17241;
        fn__851: {
            if (!this._isValid) {
                return__464 = this;
                break fn__851;
            }
            t_17238 = field__850.getSqlValue();
            String val__852 = this._changes.getOrDefault(t_17238, "");
            if (val__852.isEmpty()) {
                return__464 = this;
                break fn__851;
            }
            boolean parseOk__853;
            boolean parseOk_17395;
            try {
                Core.stringToInt64(val__852);
                parseOk_17395 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_17395 = false;
            }
            parseOk__853 = parseOk_17395;
            if (!parseOk__853) {
                t_17241 = field__850.getSqlValue();
                return__464 = this.addError(t_17241, "must be a 64-bit integer");
                break fn__851;
            }
            return__464 = this;
        }
        return return__464;
    }
    public Changeset validateFloat(SafeIdentifier field__855) {
        Changeset return__465;
        String t_17233;
        String t_17236;
        fn__856: {
            if (!this._isValid) {
                return__465 = this;
                break fn__856;
            }
            t_17233 = field__855.getSqlValue();
            String val__857 = this._changes.getOrDefault(t_17233, "");
            if (val__857.isEmpty()) {
                return__465 = this;
                break fn__856;
            }
            boolean parseOk__858;
            boolean parseOk_17397;
            try {
                Core.stringToFloat64(val__857);
                parseOk_17397 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_17397 = false;
            }
            parseOk__858 = parseOk_17397;
            if (!parseOk__858) {
                t_17236 = field__855.getSqlValue();
                return__465 = this.addError(t_17236, "must be a number");
                break fn__856;
            }
            return__465 = this;
        }
        return return__465;
    }
    public Changeset validateBool(SafeIdentifier field__860) {
        Changeset return__466;
        String t_17228;
        String t_17231;
        boolean t_9687;
        boolean t_9688;
        boolean t_9690;
        boolean t_9691;
        boolean t_9693;
        fn__861: {
            if (!this._isValid) {
                return__466 = this;
                break fn__861;
            }
            t_17228 = field__860.getSqlValue();
            String val__862 = this._changes.getOrDefault(t_17228, "");
            if (val__862.isEmpty()) {
                return__466 = this;
                break fn__861;
            }
            boolean isTrue__863;
            if (val__862.equals("true")) {
                isTrue__863 = true;
            } else {
                if (val__862.equals("1")) {
                    t_9688 = true;
                } else {
                    if (val__862.equals("yes")) {
                        t_9687 = true;
                    } else {
                        t_9687 = val__862.equals("on");
                    }
                    t_9688 = t_9687;
                }
                isTrue__863 = t_9688;
            }
            boolean isFalse__864;
            if (val__862.equals("false")) {
                isFalse__864 = true;
            } else {
                if (val__862.equals("0")) {
                    t_9691 = true;
                } else {
                    if (val__862.equals("no")) {
                        t_9690 = true;
                    } else {
                        t_9690 = val__862.equals("off");
                    }
                    t_9691 = t_9690;
                }
                isFalse__864 = t_9691;
            }
            if (!isTrue__863) {
                t_9693 = !isFalse__864;
            } else {
                t_9693 = false;
            }
            if (t_9693) {
                t_17231 = field__860.getSqlValue();
                return__466 = this.addError(t_17231, "must be a boolean (true/false/1/0/yes/no/on/off)");
                break fn__861;
            }
            return__466 = this;
        }
        return return__466;
    }
    public Changeset putChange(SafeIdentifier field__866, String value__867) {
        int t_17216;
        Map<String, String> mb__869 = new LinkedHashMap<>();
        List<Entry<String, String>> pairs__870 = Core.mappedToList(this._changes);
        int i__871 = 0;
        while (true) {
            t_17216 = pairs__870.size();
            if (i__871 >= t_17216) {
                break;
            }
            mb__869.put(Core.listGet(pairs__870, i__871).getKey(), Core.listGet(pairs__870, i__871).getValue());
            i__871 = i__871 + 1;
        }
        mb__869.put(field__866.getSqlValue(), value__867);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__869), this._errors, this._isValid);
    }
    public String getChange(SafeIdentifier field__873) {
        String t_17210 = field__873.getSqlValue();
        if (!this._changes.containsKey(t_17210)) {
            throw Core.bubble();
        }
        String t_17212 = field__873.getSqlValue();
        return this._changes.getOrDefault(t_17212, "");
    }
    public Changeset deleteChange(SafeIdentifier field__876) {
        int t_17197;
        Map<String, String> mb__878 = new LinkedHashMap<>();
        List<Entry<String, String>> pairs__879 = Core.mappedToList(this._changes);
        int i__880 = 0;
        while (true) {
            t_17197 = pairs__879.size();
            if (i__880 >= t_17197) {
                break;
            }
            if (!Core.listGet(pairs__879, i__880).getKey().equals(field__876.getSqlValue())) {
                mb__878.put(Core.listGet(pairs__879, i__880).getKey(), Core.listGet(pairs__879, i__880).getValue());
            }
            i__880 = i__880 + 1;
        }
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__878), this._errors, this._isValid);
    }
    public Changeset validateInclusion(SafeIdentifier field__882, List<String> allowed__883) {
        Changeset return__470;
        String t_17187;
        String t_17189;
        String t_17193;
        fn__884: {
            if (!this._isValid) {
                return__470 = this;
                break fn__884;
            }
            t_17187 = field__882.getSqlValue();
            if (!this._changes.containsKey(t_17187)) {
                return__470 = this;
                break fn__884;
            }
            t_17189 = field__882.getSqlValue();
            String val__885 = this._changes.getOrDefault(t_17189, "");
            class Local_2 {
                boolean found__886 = false;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<String> fn__17186 = a__887 -> {
                if (a__887.equals(val__885)) {
                    local$2.found__886 = true;
                }
            };
            allowed__883.forEach(fn__17186);
            if (!local$2.found__886) {
                t_17193 = field__882.getSqlValue();
                return__470 = this.addError(t_17193, "is not included in the list");
                break fn__884;
            }
            return__470 = this;
        }
        return return__470;
    }
    public Changeset validateExclusion(SafeIdentifier field__889, List<String> disallowed__890) {
        Changeset return__471;
        String t_17178;
        String t_17180;
        String t_17184;
        fn__891: {
            if (!this._isValid) {
                return__471 = this;
                break fn__891;
            }
            t_17178 = field__889.getSqlValue();
            if (!this._changes.containsKey(t_17178)) {
                return__471 = this;
                break fn__891;
            }
            t_17180 = field__889.getSqlValue();
            String val__892 = this._changes.getOrDefault(t_17180, "");
            class Local_3 {
                boolean found__893 = false;
            }
            final Local_3 local$3 = new Local_3();
            Consumer<String> fn__17177 = d__894 -> {
                if (d__894.equals(val__892)) {
                    local$3.found__893 = true;
                }
            };
            disallowed__890.forEach(fn__17177);
            if (local$3.found__893) {
                t_17184 = field__889.getSqlValue();
                return__471 = this.addError(t_17184, "is reserved");
                break fn__891;
            }
            return__471 = this;
        }
        return return__471;
    }
    public Changeset validateNumber(SafeIdentifier field__896, NumberValidationOpts opts__897) {
        Changeset return__472;
        String t_17151;
        String t_17153;
        String t_17155;
        String t_17158;
        String t_17159;
        String t_17162;
        String t_17163;
        String t_17166;
        String t_17167;
        String t_17170;
        String t_17171;
        String t_17174;
        String t_17175;
        double t_9621;
        fn__898: {
            if (!this._isValid) {
                return__472 = this;
                break fn__898;
            }
            t_17151 = field__896.getSqlValue();
            if (!this._changes.containsKey(t_17151)) {
                return__472 = this;
                break fn__898;
            }
            t_17153 = field__896.getSqlValue();
            String val__899 = this._changes.getOrDefault(t_17153, "");
            boolean parseOk__900;
            boolean parseOk_17401;
            try {
                Core.stringToFloat64(val__899);
                parseOk_17401 = true;
            } catch (RuntimeException ignored$4) {
                parseOk_17401 = false;
            }
            parseOk__900 = parseOk_17401;
            if (!parseOk__900) {
                t_17155 = field__896.getSqlValue();
                return__472 = this.addError(t_17155, "must be a number");
                break fn__898;
            }
            double num__901;
            double num_17402;
            try {
                t_9621 = Core.stringToFloat64(val__899);
                num_17402 = t_9621;
            } catch (RuntimeException ignored$5) {
                num_17402 = 0.0D;
            }
            num__901 = num_17402;
            @Nullable Double gt__902 = opts__897.getGreaterThan();
            if (gt__902 != null) {
                double gt_2831 = gt__902;
                if (Double.doubleToLongBits(num__901) <= Double.doubleToLongBits(gt_2831)) {
                    t_17158 = field__896.getSqlValue();
                    t_17159 = Core.float64ToString(gt_2831);
                    return__472 = this.addError(t_17158, "must be greater than " + t_17159);
                    break fn__898;
                }
            }
            @Nullable Double lt__903 = opts__897.getLessThan();
            if (lt__903 != null) {
                double lt_2832 = lt__903;
                if (Double.doubleToLongBits(num__901) >= Double.doubleToLongBits(lt_2832)) {
                    t_17162 = field__896.getSqlValue();
                    t_17163 = Core.float64ToString(lt_2832);
                    return__472 = this.addError(t_17162, "must be less than " + t_17163);
                    break fn__898;
                }
            }
            @Nullable Double gte__904 = opts__897.getGreaterThanOrEqual();
            if (gte__904 != null) {
                double gte_2833 = gte__904;
                if (Double.doubleToLongBits(num__901) < Double.doubleToLongBits(gte_2833)) {
                    t_17166 = field__896.getSqlValue();
                    t_17167 = Core.float64ToString(gte_2833);
                    return__472 = this.addError(t_17166, "must be greater than or equal to " + t_17167);
                    break fn__898;
                }
            }
            @Nullable Double lte__905 = opts__897.getLessThanOrEqual();
            if (lte__905 != null) {
                double lte_2834 = lte__905;
                if (Double.doubleToLongBits(num__901) > Double.doubleToLongBits(lte_2834)) {
                    t_17170 = field__896.getSqlValue();
                    t_17171 = Core.float64ToString(lte_2834);
                    return__472 = this.addError(t_17170, "must be less than or equal to " + t_17171);
                    break fn__898;
                }
            }
            @Nullable Double eq__906 = opts__897.getEqualTo();
            if (eq__906 != null) {
                double eq_2835 = eq__906;
                if (Double.doubleToLongBits(num__901) != Double.doubleToLongBits(eq_2835)) {
                    t_17174 = field__896.getSqlValue();
                    t_17175 = Core.float64ToString(eq_2835);
                    return__472 = this.addError(t_17174, "must be equal to " + t_17175);
                    break fn__898;
                }
            }
            return__472 = this;
        }
        return return__472;
    }
    public Changeset validateAcceptance(SafeIdentifier field__908) {
        Changeset return__473;
        String t_17145;
        String t_17147;
        String t_17149;
        boolean t_9609;
        boolean t_9610;
        fn__909: {
            if (!this._isValid) {
                return__473 = this;
                break fn__909;
            }
            t_17145 = field__908.getSqlValue();
            if (!this._changes.containsKey(t_17145)) {
                return__473 = this;
                break fn__909;
            }
            t_17147 = field__908.getSqlValue();
            String val__910 = this._changes.getOrDefault(t_17147, "");
            boolean accepted__911;
            if (val__910.equals("true")) {
                accepted__911 = true;
            } else {
                if (val__910.equals("1")) {
                    t_9610 = true;
                } else {
                    if (val__910.equals("yes")) {
                        t_9609 = true;
                    } else {
                        t_9609 = val__910.equals("on");
                    }
                    t_9610 = t_9609;
                }
                accepted__911 = t_9610;
            }
            if (!accepted__911) {
                t_17149 = field__908.getSqlValue();
                return__473 = this.addError(t_17149, "must be accepted");
                break fn__909;
            }
            return__473 = this;
        }
        return return__473;
    }
    public Changeset validateConfirmation(SafeIdentifier field__913, SafeIdentifier confirmationField__914) {
        Changeset return__474;
        String t_17137;
        String t_17139;
        String t_17141;
        String t_17143;
        fn__915: {
            if (!this._isValid) {
                return__474 = this;
                break fn__915;
            }
            t_17137 = field__913.getSqlValue();
            if (!this._changes.containsKey(t_17137)) {
                return__474 = this;
                break fn__915;
            }
            t_17139 = field__913.getSqlValue();
            String val__916 = this._changes.getOrDefault(t_17139, "");
            t_17141 = confirmationField__914.getSqlValue();
            String conf__917 = this._changes.getOrDefault(t_17141, "");
            if (!val__916.equals(conf__917)) {
                t_17143 = confirmationField__914.getSqlValue();
                return__474 = this.addError(t_17143, "does not match");
                break fn__915;
            }
            return__474 = this;
        }
        return return__474;
    }
    public Changeset validateContains(SafeIdentifier field__919, String substring__920) {
        Changeset return__475;
        String t_17129;
        String t_17131;
        String t_17135;
        fn__921: {
            if (!this._isValid) {
                return__475 = this;
                break fn__921;
            }
            t_17129 = field__919.getSqlValue();
            if (!this._changes.containsKey(t_17129)) {
                return__475 = this;
                break fn__921;
            }
            t_17131 = field__919.getSqlValue();
            String val__922 = this._changes.getOrDefault(t_17131, "");
            if (val__922.indexOf(substring__920) < 0) {
                t_17135 = field__919.getSqlValue();
                return__475 = this.addError(t_17135, "must contain the given substring");
                break fn__921;
            }
            return__475 = this;
        }
        return return__475;
    }
    public Changeset validateStartsWith(SafeIdentifier field__924, String prefix__925) {
        Changeset return__476;
        String t_17120;
        String t_17122;
        int t_17126;
        String t_17127;
        fn__926: {
            if (!this._isValid) {
                return__476 = this;
                break fn__926;
            }
            t_17120 = field__924.getSqlValue();
            if (!this._changes.containsKey(t_17120)) {
                return__476 = this;
                break fn__926;
            }
            t_17122 = field__924.getSqlValue();
            String val__927 = this._changes.getOrDefault(t_17122, "");
            int idx__928 = val__927.indexOf(prefix__925);
            boolean starts__929;
            if (idx__928 >= 0) {
                t_17126 = Core.stringCountBetween(val__927, 0, Core.requireStringIndex(idx__928));
                starts__929 = t_17126 == 0;
            } else {
                starts__929 = false;
            }
            if (!starts__929) {
                t_17127 = field__924.getSqlValue();
                return__476 = this.addError(t_17127, "must start with the given prefix");
                break fn__926;
            }
            return__476 = this;
        }
        return return__476;
    }
    public Changeset validateEndsWith(SafeIdentifier field__931, String suffix__932) {
        Changeset return__477;
        String t_17100;
        String t_17102;
        int t_17107;
        String t_17109;
        int t_17111;
        boolean t_17112;
        int t_17116;
        int t_17117;
        String t_17118;
        boolean t_9569;
        fn__933: {
            if (!this._isValid) {
                return__477 = this;
                break fn__933;
            }
            t_17100 = field__931.getSqlValue();
            if (!this._changes.containsKey(t_17100)) {
                return__477 = this;
                break fn__933;
            }
            t_17102 = field__931.getSqlValue();
            String val__934 = this._changes.getOrDefault(t_17102, "");
            int valLen__935 = Core.stringCountBetween(val__934, 0, val__934.length());
            t_17107 = suffix__932.length();
            int suffixLen__936 = Core.stringCountBetween(suffix__932, 0, t_17107);
            if (valLen__935 < suffixLen__936) {
                t_17109 = field__931.getSqlValue();
                return__477 = this.addError(t_17109, "must end with the given suffix");
                break fn__933;
            }
            int skipCount__937 = valLen__935 - suffixLen__936;
            int strIdx__938 = 0;
            int i__939 = 0;
            while (i__939 < skipCount__937) {
                t_17111 = Core.stringNext(val__934, strIdx__938);
                strIdx__938 = t_17111;
                i__939 = i__939 + 1;
            }
            int sufIdx__940 = 0;
            boolean matches__941 = true;
            while (true) {
                if (matches__941) {
                    t_17112 = Core.stringHasIndex(suffix__932, sufIdx__940);
                    t_9569 = t_17112;
                } else {
                    t_9569 = false;
                }
                if (!t_9569) {
                    break;
                }
                if (!Core.stringHasIndex(val__934, strIdx__938)) {
                    matches__941 = false;
                } else if (val__934.codePointAt(strIdx__938) != suffix__932.codePointAt(sufIdx__940)) {
                    matches__941 = false;
                } else {
                    t_17116 = Core.stringNext(val__934, strIdx__938);
                    strIdx__938 = t_17116;
                    t_17117 = Core.stringNext(suffix__932, sufIdx__940);
                    sufIdx__940 = t_17117;
                }
            }
            if (!matches__941) {
                t_17118 = field__931.getSqlValue();
                return__477 = this.addError(t_17118, "must end with the given suffix");
                break fn__933;
            }
            return__477 = this;
        }
        return return__477;
    }
    SqlBoolean parseBoolSqlPart(String val__943) {
        SqlBoolean return__478;
        boolean t_9547;
        boolean t_9548;
        boolean t_9549;
        boolean t_9551;
        boolean t_9552;
        boolean t_9553;
        fn__944: {
            if (val__943.equals("true")) {
                t_9549 = true;
            } else {
                if (val__943.equals("1")) {
                    t_9548 = true;
                } else {
                    if (val__943.equals("yes")) {
                        t_9547 = true;
                    } else {
                        t_9547 = val__943.equals("on");
                    }
                    t_9548 = t_9547;
                }
                t_9549 = t_9548;
            }
            if (t_9549) {
                return__478 = new SqlBoolean(true);
                break fn__944;
            }
            if (val__943.equals("false")) {
                t_9553 = true;
            } else {
                if (val__943.equals("0")) {
                    t_9552 = true;
                } else {
                    if (val__943.equals("no")) {
                        t_9551 = true;
                    } else {
                        t_9551 = val__943.equals("off");
                    }
                    t_9552 = t_9551;
                }
                t_9553 = t_9552;
            }
            if (t_9553) {
                return__478 = new SqlBoolean(false);
                break fn__944;
            }
            throw Core.bubble();
        }
        return return__478;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__946, String val__947) {
        SqlPart return__479;
        int t_9534;
        long t_9537;
        double t_9540;
        LocalDate t_9545;
        fn__948: {
            FieldType ft__949 = fieldDef__946.getFieldType();
            if (ft__949 instanceof StringField) {
                return__479 = new SqlString(val__947);
                break fn__948;
            }
            if (ft__949 instanceof IntField) {
                t_9534 = Core.stringToInt(val__947);
                return__479 = new SqlInt32(t_9534);
                break fn__948;
            }
            if (ft__949 instanceof Int64Field) {
                t_9537 = Core.stringToInt64(val__947);
                return__479 = new SqlInt64(t_9537);
                break fn__948;
            }
            if (ft__949 instanceof FloatField) {
                t_9540 = Core.stringToFloat64(val__947);
                return__479 = new SqlFloat64(t_9540);
                break fn__948;
            }
            if (ft__949 instanceof BoolField) {
                return__479 = this.parseBoolSqlPart(val__947);
                break fn__948;
            }
            if (ft__949 instanceof DateField) {
                t_9545 = LocalDate.parse(val__947);
                return__479 = new SqlDate(t_9545);
                break fn__948;
            }
            throw Core.bubble();
        }
        return return__479;
    }
    public SqlFragment toInsertSql() {
        int t_17032;
        String t_17039;
        int t_17044;
        String t_17046;
        String t_17051;
        int t_17054;
        String t_17060;
        int t_17080;
        boolean t_9484;
        boolean t_9485;
        FieldDef t_9492;
        SqlPart t_9498;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__952 = 0;
        while (true) {
            continue_17375: {
                t_17032 = this._tableDef.getFields().size();
                if (i__952 >= t_17032) {
                    break;
                }
                FieldDef f__953 = Core.listGet(this._tableDef.getFields(), i__952);
                if (f__953.isVirtual()) {
                    break continue_17375;
                }
                @Nullable SqlPart dv__954 = f__953.getDefaultValue();
                if (!f__953.isNullable()) {
                    t_17039 = f__953.getName().getSqlValue();
                    if (!this._changes.containsKey(t_17039)) {
                        t_9484 = dv__954 == null;
                    } else {
                        t_9484 = false;
                    }
                    t_9485 = t_9484;
                } else {
                    t_9485 = false;
                }
                if (t_9485) {
                    throw Core.bubble();
                }
            }
            i__952 = i__952 + 1;
        }
        List<String> colNames__955 = new ArrayList<>();
        List<SqlPart> valParts__956 = new ArrayList<>();
        List<Entry<String, String>> pairs__957 = Core.mappedToList(this._changes);
        int i__958 = 0;
        while (true) {
            continue_17376: {
                t_17044 = pairs__957.size();
                if (i__958 >= t_17044) {
                    break;
                }
                Entry<String, String> pair__959 = Core.listGet(pairs__957, i__958);
                t_17046 = pair__959.getKey();
                t_9492 = this._tableDef.field(t_17046);
                FieldDef fd__960 = t_9492;
                if (fd__960.isVirtual()) {
                    break continue_17376;
                }
                Core.listAdd(colNames__955, fd__960.getName().getSqlValue());
                t_17051 = pair__959.getValue();
                t_9498 = this.valueToSqlPart(fd__960, t_17051);
                Core.listAdd(valParts__956, t_9498);
            }
            i__958 = i__958 + 1;
        }
        int i__961 = 0;
        while (true) {
            continue_17377: {
                t_17054 = this._tableDef.getFields().size();
                if (i__961 >= t_17054) {
                    break;
                }
                FieldDef f__962 = Core.listGet(this._tableDef.getFields(), i__961);
                if (f__962.isVirtual()) {
                    break continue_17377;
                }
                @Nullable SqlPart dv__963 = f__962.getDefaultValue();
                if (dv__963 != null) {
                    SqlPart dv_2843 = dv__963;
                    t_17060 = f__962.getName().getSqlValue();
                    if (!this._changes.containsKey(t_17060)) {
                        Core.listAdd(colNames__955, f__962.getName().getSqlValue());
                        Core.listAdd(valParts__956, dv_2843);
                    }
                }
            }
            i__961 = i__961 + 1;
        }
        if (valParts__956.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__964 = new SqlBuilder();
        b__964.appendSafe("INSERT INTO ");
        b__964.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__964.appendSafe(" (");
        List<String> t_17073 = List.copyOf(colNames__955);
        Function<String, String> fn__17030 = c__965 -> c__965;
        b__964.appendSafe(Core.listJoinObj(t_17073, ", ", fn__17030));
        b__964.appendSafe(") VALUES (");
        b__964.appendPart(Core.listGet(valParts__956, 0));
        int j__966 = 1;
        while (true) {
            t_17080 = valParts__956.size();
            if (j__966 >= t_17080) {
                break;
            }
            b__964.appendSafe(", ");
            b__964.appendPart(Core.listGet(valParts__956, j__966));
            j__966 = j__966 + 1;
        }
        b__964.appendSafe(")");
        return b__964.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__968) {
        int t_17013;
        String t_17015;
        String t_17022;
        FieldDef t_9459;
        SqlPart t_9466;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__970 = Core.mappedToList(this._changes);
        if (pairs__970.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__971 = new SqlBuilder();
        b__971.appendSafe("UPDATE ");
        b__971.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__971.appendSafe(" SET ");
        int setCount__972 = 0;
        int i__973 = 0;
        while (true) {
            continue_17378: {
                t_17013 = pairs__970.size();
                if (i__973 >= t_17013) {
                    break;
                }
                Entry<String, String> pair__974 = Core.listGet(pairs__970, i__973);
                t_17015 = pair__974.getKey();
                t_9459 = this._tableDef.field(t_17015);
                FieldDef fd__975 = t_9459;
                if (fd__975.isVirtual()) {
                    break continue_17378;
                }
                if (setCount__972 > 0) {
                    b__971.appendSafe(", ");
                }
                b__971.appendSafe(fd__975.getName().getSqlValue());
                b__971.appendSafe(" = ");
                t_17022 = pair__974.getValue();
                t_9466 = this.valueToSqlPart(fd__975, t_17022);
                b__971.appendPart(t_9466);
                setCount__972 = setCount__972 + 1;
            }
            i__973 = i__973 + 1;
        }
        if (setCount__972 == 0) {
            throw Core.bubble();
        }
        b__971.appendSafe(" WHERE ");
        b__971.appendSafe(this._tableDef.pkName());
        b__971.appendSafe(" = ");
        b__971.appendInt32(id__968);
        return b__971.getAccumulated();
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
    public ChangesetImpl(TableDef _tableDef__977, Map<String, String> _params__978, Map<String, String> _changes__979, List<ChangesetError> _errors__980, boolean _isValid__981) {
        this._tableDef = _tableDef__977;
        this._params = _params__978;
        this._changes = _changes__979;
        this._errors = _errors__980;
        this._isValid = _isValid__981;
    }
}
