package orm.src;
import temper.std.testing.Test;
import java.util.function.Supplier;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;
import temper.core.Core;
import java.util.Map;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.ArrayList;
public final class SrcTest {
    private SrcTest() {
    }
    static SafeIdentifier csid__703(String name__985) {
        SafeIdentifier t_9421;
        t_9421 = SrcGlobal.safeIdentifier(name__985);
        return t_9421;
    }
    static TableDef userTable__704() {
        return new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("email"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("age"), new IntField(), true, null, false), new FieldDef(SrcTest.csid__703("score"), new FloatField(), true, null, false), new FieldDef(SrcTest.csid__703("active"), new BoolField(), true, null, false)), null);
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__2272() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__989 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_16959 = SrcTest.userTable__704();
            SafeIdentifier t_16960 = SrcTest.csid__703("name");
            SafeIdentifier t_16961 = SrcTest.csid__703("email");
            Changeset cs__990 = SrcGlobal.changeset(t_16959, params__989).cast(List.of(t_16960, t_16961));
            boolean t_16964 = cs__990.getChanges().containsKey("name");
            Supplier<String> fn__16954 = () -> "name should be in changes";
            test_32.assert_(t_16964, fn__16954);
            boolean t_16968 = cs__990.getChanges().containsKey("email");
            Supplier<String> fn__16953 = () -> "email should be in changes";
            test_32.assert_(t_16968, fn__16953);
            boolean t_16974 = !cs__990.getChanges().containsKey("admin");
            Supplier<String> fn__16952 = () -> "admin must be dropped (not in whitelist)";
            test_32.assert_(t_16974, fn__16952);
            boolean t_16976 = cs__990.isValid();
            Supplier<String> fn__16951 = () -> "should still be valid";
            test_32.assert_(t_16976, fn__16951);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__2273() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__992 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_16937 = SrcTest.userTable__704();
            SafeIdentifier t_16938 = SrcTest.csid__703("name");
            Changeset cs__993 = SrcGlobal.changeset(t_16937, params__992).cast(List.of(t_16938)).cast(List.of(SrcTest.csid__703("email")));
            boolean t_16945 = !cs__993.getChanges().containsKey("name");
            Supplier<String> fn__16933 = () -> "name must be excluded by second cast";
            test_33.assert_(t_16945, fn__16933);
            boolean t_16948 = cs__993.getChanges().containsKey("email");
            Supplier<String> fn__16932 = () -> "email should be present";
            test_33.assert_(t_16948, fn__16932);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__2274() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__995 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_16919 = SrcTest.userTable__704();
            SafeIdentifier t_16920 = SrcTest.csid__703("name");
            SafeIdentifier t_16921 = SrcTest.csid__703("email");
            Changeset cs__996 = SrcGlobal.changeset(t_16919, params__995).cast(List.of(t_16920, t_16921));
            boolean t_16926 = !cs__996.getChanges().containsKey("name");
            Supplier<String> fn__16915 = () -> "empty name should not be in changes";
            test_34.assert_(t_16926, fn__16915);
            boolean t_16929 = cs__996.getChanges().containsKey("email");
            Supplier<String> fn__16914 = () -> "email should be in changes";
            test_34.assert_(t_16929, fn__16914);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__2275() {
        Test test_35 = new Test();
        try {
            Map<String, String> params__998 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_16901 = SrcTest.userTable__704();
            SafeIdentifier t_16902 = SrcTest.csid__703("name");
            Changeset cs__999 = SrcGlobal.changeset(t_16901, params__998).cast(List.of(t_16902)).validateRequired(List.of(SrcTest.csid__703("name")));
            boolean t_16906 = cs__999.isValid();
            Supplier<String> fn__16898 = () -> "should be valid";
            test_35.assert_(t_16906, fn__16898);
            boolean t_16912 = cs__999.getErrors().size() == 0;
            Supplier<String> fn__16897 = () -> "no errors expected";
            test_35.assert_(t_16912, fn__16897);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__2276() {
        Test test_36 = new Test();
        try {
            Map<String, String> params__1001 = Core.mapConstructor(List.of());
            TableDef t_16877 = SrcTest.userTable__704();
            SafeIdentifier t_16878 = SrcTest.csid__703("name");
            Changeset cs__1002 = SrcGlobal.changeset(t_16877, params__1001).cast(List.of(t_16878)).validateRequired(List.of(SrcTest.csid__703("name")));
            boolean t_16884 = !cs__1002.isValid();
            Supplier<String> fn__16875 = () -> "should be invalid";
            test_36.assert_(t_16884, fn__16875);
            boolean t_16889 = cs__1002.getErrors().size() == 1;
            Supplier<String> fn__16874 = () -> "should have one error";
            test_36.assert_(t_16889, fn__16874);
            boolean t_16895 = Core.listGet(cs__1002.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__16873 = () -> "error should name the field";
            test_36.assert_(t_16895, fn__16873);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__2277() {
        Test test_37 = new Test();
        try {
            Map<String, String> params__1004 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_16865 = SrcTest.userTable__704();
            SafeIdentifier t_16866 = SrcTest.csid__703("name");
            Changeset cs__1005 = SrcGlobal.changeset(t_16865, params__1004).cast(List.of(t_16866)).validateLength(SrcTest.csid__703("name"), 2, 50);
            boolean t_16870 = cs__1005.isValid();
            Supplier<String> fn__16862 = () -> "should be valid";
            test_37.assert_(t_16870, fn__16862);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__2278() {
        Test test_38 = new Test();
        try {
            Map<String, String> params__1007 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_16853 = SrcTest.userTable__704();
            SafeIdentifier t_16854 = SrcTest.csid__703("name");
            Changeset cs__1008 = SrcGlobal.changeset(t_16853, params__1007).cast(List.of(t_16854)).validateLength(SrcTest.csid__703("name"), 2, 50);
            boolean t_16860 = !cs__1008.isValid();
            Supplier<String> fn__16850 = () -> "should be invalid";
            test_38.assert_(t_16860, fn__16850);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__2279() {
        Test test_39 = new Test();
        try {
            Map<String, String> params__1010 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_16841 = SrcTest.userTable__704();
            SafeIdentifier t_16842 = SrcTest.csid__703("name");
            Changeset cs__1011 = SrcGlobal.changeset(t_16841, params__1010).cast(List.of(t_16842)).validateLength(SrcTest.csid__703("name"), 2, 10);
            boolean t_16848 = !cs__1011.isValid();
            Supplier<String> fn__16838 = () -> "should be invalid";
            test_39.assert_(t_16848, fn__16838);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__2280() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__1013 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_16830 = SrcTest.userTable__704();
            SafeIdentifier t_16831 = SrcTest.csid__703("age");
            Changeset cs__1014 = SrcGlobal.changeset(t_16830, params__1013).cast(List.of(t_16831)).validateInt(SrcTest.csid__703("age"));
            boolean t_16835 = cs__1014.isValid();
            Supplier<String> fn__16827 = () -> "should be valid";
            test_40.assert_(t_16835, fn__16827);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__2281() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__1016 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_16818 = SrcTest.userTable__704();
            SafeIdentifier t_16819 = SrcTest.csid__703("age");
            Changeset cs__1017 = SrcGlobal.changeset(t_16818, params__1016).cast(List.of(t_16819)).validateInt(SrcTest.csid__703("age"));
            boolean t_16825 = !cs__1017.isValid();
            Supplier<String> fn__16815 = () -> "should be invalid";
            test_41.assert_(t_16825, fn__16815);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__2282() {
        Test test_42 = new Test();
        try {
            Map<String, String> params__1019 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_16807 = SrcTest.userTable__704();
            SafeIdentifier t_16808 = SrcTest.csid__703("score");
            Changeset cs__1020 = SrcGlobal.changeset(t_16807, params__1019).cast(List.of(t_16808)).validateFloat(SrcTest.csid__703("score"));
            boolean t_16812 = cs__1020.isValid();
            Supplier<String> fn__16804 = () -> "should be valid";
            test_42.assert_(t_16812, fn__16804);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__2283() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__1022 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_16796 = SrcTest.userTable__704();
            SafeIdentifier t_16797 = SrcTest.csid__703("age");
            Changeset cs__1023 = SrcGlobal.changeset(t_16796, params__1022).cast(List.of(t_16797)).validateInt64(SrcTest.csid__703("age"));
            boolean t_16801 = cs__1023.isValid();
            Supplier<String> fn__16793 = () -> "should be valid";
            test_43.assert_(t_16801, fn__16793);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__2284() {
        Test test_44 = new Test();
        try {
            Map<String, String> params__1025 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_16784 = SrcTest.userTable__704();
            SafeIdentifier t_16785 = SrcTest.csid__703("age");
            Changeset cs__1026 = SrcGlobal.changeset(t_16784, params__1025).cast(List.of(t_16785)).validateInt64(SrcTest.csid__703("age"));
            boolean t_16791 = !cs__1026.isValid();
            Supplier<String> fn__16781 = () -> "should be invalid";
            test_44.assert_(t_16791, fn__16781);
        } finally {
            test_44.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__2285() {
        Test test_45 = new Test();
        try {
            Consumer<String> fn__16778 = v__1028 -> {
                Map<String, String> params__1029 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__1028)));
                TableDef t_16770 = SrcTest.userTable__704();
                SafeIdentifier t_16771 = SrcTest.csid__703("active");
                Changeset cs__1030 = SrcGlobal.changeset(t_16770, params__1029).cast(List.of(t_16771)).validateBool(SrcTest.csid__703("active"));
                boolean t_16775 = cs__1030.isValid();
                Supplier<String> fn__16767 = () -> "should accept: " + v__1028;
                test_45.assert_(t_16775, fn__16767);
            };
            List.of("true", "1", "yes", "on").forEach(fn__16778);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__2286() {
        Test test_46 = new Test();
        try {
            Consumer<String> fn__16764 = v__1032 -> {
                Map<String, String> params__1033 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__1032)));
                TableDef t_16756 = SrcTest.userTable__704();
                SafeIdentifier t_16757 = SrcTest.csid__703("active");
                Changeset cs__1034 = SrcGlobal.changeset(t_16756, params__1033).cast(List.of(t_16757)).validateBool(SrcTest.csid__703("active"));
                boolean t_16761 = cs__1034.isValid();
                Supplier<String> fn__16753 = () -> "should accept: " + v__1032;
                test_46.assert_(t_16761, fn__16753);
            };
            List.of("false", "0", "no", "off").forEach(fn__16764);
        } finally {
            test_46.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__2287() {
        Test test_47 = new Test();
        try {
            Consumer<String> fn__16750 = v__1036 -> {
                Map<String, String> params__1037 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__1036)));
                TableDef t_16741 = SrcTest.userTable__704();
                SafeIdentifier t_16742 = SrcTest.csid__703("active");
                Changeset cs__1038 = SrcGlobal.changeset(t_16741, params__1037).cast(List.of(t_16742)).validateBool(SrcTest.csid__703("active"));
                boolean t_16748 = !cs__1038.isValid();
                Supplier<String> fn__16738 = () -> "should reject ambiguous: " + v__1036;
                test_47.assert_(t_16748, fn__16738);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__16750);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__2288() {
        Test test_48 = new Test();
        try {
            Map<String, String> params__1040 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_16726 = SrcTest.userTable__704();
            SafeIdentifier t_16727 = SrcTest.csid__703("name");
            SafeIdentifier t_16728 = SrcTest.csid__703("email");
            Changeset cs__1041 = SrcGlobal.changeset(t_16726, params__1040).cast(List.of(t_16727, t_16728)).validateRequired(List.of(SrcTest.csid__703("name"), SrcTest.csid__703("email")));
            SqlFragment t_9222;
            t_9222 = cs__1041.toInsertSql();
            SqlFragment sqlFrag__1042 = t_9222;
            String s__1043 = sqlFrag__1042.toString();
            boolean t_16735 = s__1043.indexOf("''") >= 0;
            Supplier<String> fn__16722 = () -> "single quote must be doubled: " + s__1043;
            test_48.assert_(t_16735, fn__16722);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__2289() {
        Test test_49 = new Test();
        try {
            Map<String, String> params__1045 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_16706 = SrcTest.userTable__704();
            SafeIdentifier t_16707 = SrcTest.csid__703("name");
            SafeIdentifier t_16708 = SrcTest.csid__703("email");
            Changeset cs__1046 = SrcGlobal.changeset(t_16706, params__1045).cast(List.of(t_16707, t_16708)).validateRequired(List.of(SrcTest.csid__703("name"), SrcTest.csid__703("email")));
            SqlFragment t_9201;
            t_9201 = cs__1046.toInsertSql();
            SqlFragment sqlFrag__1047 = t_9201;
            String s__1048 = sqlFrag__1047.toString();
            boolean t_16715 = s__1048.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__16702 = () -> "has INSERT INTO: " + s__1048;
            test_49.assert_(t_16715, fn__16702);
            boolean t_16719 = s__1048.indexOf("'Alice'") >= 0;
            Supplier<String> fn__16701 = () -> "has quoted name: " + s__1048;
            test_49.assert_(t_16719, fn__16701);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__2290() {
        Test test_50 = new Test();
        try {
            Map<String, String> params__1050 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_16688 = SrcTest.userTable__704();
            SafeIdentifier t_16689 = SrcTest.csid__703("name");
            SafeIdentifier t_16690 = SrcTest.csid__703("email");
            SafeIdentifier t_16691 = SrcTest.csid__703("age");
            Changeset cs__1051 = SrcGlobal.changeset(t_16688, params__1050).cast(List.of(t_16689, t_16690, t_16691)).validateRequired(List.of(SrcTest.csid__703("name"), SrcTest.csid__703("email")));
            SqlFragment t_9184;
            t_9184 = cs__1051.toInsertSql();
            SqlFragment sqlFrag__1052 = t_9184;
            String s__1053 = sqlFrag__1052.toString();
            boolean t_16698 = s__1053.indexOf("25") >= 0;
            Supplier<String> fn__16683 = () -> "age rendered unquoted: " + s__1053;
            test_50.assert_(t_16698, fn__16683);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__2291() {
        Test test_51 = new Test();
        try {
            Map<String, String> params__1055 = Core.mapConstructor(List.of());
            TableDef t_16676 = SrcTest.userTable__704();
            SafeIdentifier t_16677 = SrcTest.csid__703("name");
            Changeset cs__1056 = SrcGlobal.changeset(t_16676, params__1055).cast(List.of(t_16677)).validateRequired(List.of(SrcTest.csid__703("name")));
            boolean didBubble__1057;
            boolean didBubble_17421;
            try {
                cs__1056.toInsertSql();
                didBubble_17421 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_17421 = true;
            }
            didBubble__1057 = didBubble_17421;
            Supplier<String> fn__16674 = () -> "invalid changeset should bubble";
            test_51.assert_(didBubble__1057, fn__16674);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__2292() {
        Test test_52 = new Test();
        try {
            TableDef strictTable__1059 = new TableDef(SrcTest.csid__703("posts"), List.of(new FieldDef(SrcTest.csid__703("title"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("body"), new StringField(), true, null, false)), null);
            Map<String, String> params__1060 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_16667 = SrcTest.csid__703("body");
            Changeset cs__1061 = SrcGlobal.changeset(strictTable__1059, params__1060).cast(List.of(t_16667));
            boolean t_16669 = cs__1061.isValid();
            Supplier<String> fn__16656 = () -> "changeset should appear valid (no explicit validation run)";
            test_52.assert_(t_16669, fn__16656);
            boolean didBubble__1062;
            boolean didBubble_17422;
            try {
                cs__1061.toInsertSql();
                didBubble_17422 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_17422 = true;
            }
            didBubble__1062 = didBubble_17422;
            Supplier<String> fn__16655 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_52.assert_(didBubble__1062, fn__16655);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__2293() {
        Test test_53 = new Test();
        try {
            Map<String, String> params__1064 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_16646 = SrcTest.userTable__704();
            SafeIdentifier t_16647 = SrcTest.csid__703("name");
            Changeset cs__1065 = SrcGlobal.changeset(t_16646, params__1064).cast(List.of(t_16647)).validateRequired(List.of(SrcTest.csid__703("name")));
            SqlFragment t_9144;
            t_9144 = cs__1065.toUpdateSql(42);
            SqlFragment sqlFrag__1066 = t_9144;
            String s__1067 = sqlFrag__1066.toString();
            boolean t_16653 = s__1067.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__16643 = () -> "got: " + s__1067;
            test_53.assert_(t_16653, fn__16643);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__2294() {
        Test test_54 = new Test();
        try {
            Map<String, String> params__1069 = Core.mapConstructor(List.of());
            TableDef t_16636 = SrcTest.userTable__704();
            SafeIdentifier t_16637 = SrcTest.csid__703("name");
            Changeset cs__1070 = SrcGlobal.changeset(t_16636, params__1069).cast(List.of(t_16637)).validateRequired(List.of(SrcTest.csid__703("name")));
            boolean didBubble__1071;
            boolean didBubble_17423;
            try {
                cs__1070.toUpdateSql(1);
                didBubble_17423 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_17423 = true;
            }
            didBubble__1071 = didBubble_17423;
            Supplier<String> fn__16634 = () -> "invalid changeset should bubble";
            test_54.assert_(didBubble__1071, fn__16634);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeAddsANewField__2295() {
        Test test_55 = new Test();
        try {
            Map<String, String> params__1073 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_16620 = SrcTest.userTable__704();
            SafeIdentifier t_16621 = SrcTest.csid__703("name");
            Changeset cs__1074 = SrcGlobal.changeset(t_16620, params__1073).cast(List.of(t_16621)).putChange(SrcTest.csid__703("email"), "alice@example.com");
            boolean t_16626 = cs__1074.getChanges().containsKey("email");
            Supplier<String> fn__16617 = () -> "email should be in changes";
            test_55.assert_(t_16626, fn__16617);
            boolean t_16632 = cs__1074.getChanges().getOrDefault("email", "").equals("alice@example.com");
            Supplier<String> fn__16616 = () -> "email value";
            test_55.assert_(t_16632, fn__16616);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeOverwritesExistingField__2296() {
        Test test_56 = new Test();
        try {
            Map<String, String> params__1076 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_16606 = SrcTest.userTable__704();
            SafeIdentifier t_16607 = SrcTest.csid__703("name");
            Changeset cs__1077 = SrcGlobal.changeset(t_16606, params__1076).cast(List.of(t_16607)).putChange(SrcTest.csid__703("name"), "Bob");
            boolean t_16614 = cs__1077.getChanges().getOrDefault("name", "").equals("Bob");
            Supplier<String> fn__16603 = () -> "name should be overwritten";
            test_56.assert_(t_16614, fn__16603);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeValueAppearsInToInsertSql__2297() {
        Test test_57 = new Test();
        try {
            Map<String, String> params__1079 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_16592 = SrcTest.userTable__704();
            SafeIdentifier t_16593 = SrcTest.csid__703("name");
            SafeIdentifier t_16594 = SrcTest.csid__703("email");
            Changeset cs__1080 = SrcGlobal.changeset(t_16592, params__1079).cast(List.of(t_16593, t_16594)).putChange(SrcTest.csid__703("name"), "Bob");
            SqlFragment t_9099;
            t_9099 = cs__1080.toInsertSql();
            SqlFragment t_9100 = t_9099;
            String s__1081 = t_9100.toString();
            boolean t_16600 = s__1081.indexOf("'Bob'") >= 0;
            Supplier<String> fn__16588 = () -> "should use putChange value: " + s__1081;
            test_57.assert_(t_16600, fn__16588);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void getChangeReturnsValueForExistingField__2298() {
        Test test_58 = new Test();
        try {
            Map<String, String> params__1083 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_16581 = SrcTest.userTable__704();
            SafeIdentifier t_16582 = SrcTest.csid__703("name");
            Changeset cs__1084 = SrcGlobal.changeset(t_16581, params__1083).cast(List.of(t_16582));
            String t_9087;
            t_9087 = cs__1084.getChange(SrcTest.csid__703("name"));
            String val__1085 = t_9087;
            boolean t_16586 = val__1085.equals("Alice");
            Supplier<String> fn__16578 = () -> "should return Alice";
            test_58.assert_(t_16586, fn__16578);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void getChangeBubblesOnMissingField__2299() {
        Test test_59 = new Test();
        try {
            Map<String, String> params__1087 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_16572 = SrcTest.userTable__704();
            SafeIdentifier t_16573 = SrcTest.csid__703("name");
            Changeset cs__1088 = SrcGlobal.changeset(t_16572, params__1087).cast(List.of(t_16573));
            boolean didBubble__1089;
            boolean didBubble_17424;
            try {
                cs__1088.getChange(SrcTest.csid__703("email"));
                didBubble_17424 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_17424 = true;
            }
            didBubble__1089 = didBubble_17424;
            Supplier<String> fn__16569 = () -> "should bubble for missing field";
            test_59.assert_(didBubble__1089, fn__16569);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteChangeRemovesField__2300() {
        Test test_60 = new Test();
        try {
            Map<String, String> params__1091 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_16554 = SrcTest.userTable__704();
            SafeIdentifier t_16555 = SrcTest.csid__703("name");
            SafeIdentifier t_16556 = SrcTest.csid__703("email");
            Changeset cs__1092 = SrcGlobal.changeset(t_16554, params__1091).cast(List.of(t_16555, t_16556)).deleteChange(SrcTest.csid__703("email"));
            boolean t_16563 = !cs__1092.getChanges().containsKey("email");
            Supplier<String> fn__16550 = () -> "email should be removed";
            test_60.assert_(t_16563, fn__16550);
            boolean t_16566 = cs__1092.getChanges().containsKey("name");
            Supplier<String> fn__16549 = () -> "name should remain";
            test_60.assert_(t_16566, fn__16549);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteChangeOnNonexistentFieldIsNoOp__2301() {
        Test test_61 = new Test();
        try {
            Map<String, String> params__1094 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_16537 = SrcTest.userTable__704();
            SafeIdentifier t_16538 = SrcTest.csid__703("name");
            Changeset cs__1095 = SrcGlobal.changeset(t_16537, params__1094).cast(List.of(t_16538)).deleteChange(SrcTest.csid__703("email"));
            boolean t_16543 = cs__1095.getChanges().containsKey("name");
            Supplier<String> fn__16534 = () -> "name should still be present";
            test_61.assert_(t_16543, fn__16534);
            boolean t_16546 = cs__1095.isValid();
            Supplier<String> fn__16533 = () -> "should still be valid";
            test_61.assert_(t_16546, fn__16533);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionPassesWhenValueInList__2302() {
        Test test_62 = new Test();
        try {
            Map<String, String> params__1097 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "admin")));
            TableDef t_16525 = SrcTest.userTable__704();
            SafeIdentifier t_16526 = SrcTest.csid__703("name");
            Changeset cs__1098 = SrcGlobal.changeset(t_16525, params__1097).cast(List.of(t_16526)).validateInclusion(SrcTest.csid__703("name"), List.of("admin", "user", "guest"));
            boolean t_16530 = cs__1098.isValid();
            Supplier<String> fn__16522 = () -> "should be valid";
            test_62.assert_(t_16530, fn__16522);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionFailsWhenValueNotInList__2303() {
        Test test_63 = new Test();
        try {
            Map<String, String> params__1100 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "hacker")));
            TableDef t_16507 = SrcTest.userTable__704();
            SafeIdentifier t_16508 = SrcTest.csid__703("name");
            Changeset cs__1101 = SrcGlobal.changeset(t_16507, params__1100).cast(List.of(t_16508)).validateInclusion(SrcTest.csid__703("name"), List.of("admin", "user", "guest"));
            boolean t_16514 = !cs__1101.isValid();
            Supplier<String> fn__16504 = () -> "should be invalid";
            test_63.assert_(t_16514, fn__16504);
            boolean t_16520 = Core.listGet(cs__1101.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__16503 = () -> "error on name";
            test_63.assert_(t_16520, fn__16503);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionSkipsWhenFieldNotInChanges__2304() {
        Test test_64 = new Test();
        try {
            Map<String, String> params__1103 = Core.mapConstructor(List.of());
            TableDef t_16495 = SrcTest.userTable__704();
            SafeIdentifier t_16496 = SrcTest.csid__703("name");
            Changeset cs__1104 = SrcGlobal.changeset(t_16495, params__1103).cast(List.of(t_16496)).validateInclusion(SrcTest.csid__703("name"), List.of("admin", "user"));
            boolean t_16500 = cs__1104.isValid();
            Supplier<String> fn__16493 = () -> "should be valid when field absent";
            test_64.assert_(t_16500, fn__16493);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionPassesWhenValueNotInList__2305() {
        Test test_65 = new Test();
        try {
            Map<String, String> params__1106 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_16485 = SrcTest.userTable__704();
            SafeIdentifier t_16486 = SrcTest.csid__703("name");
            Changeset cs__1107 = SrcGlobal.changeset(t_16485, params__1106).cast(List.of(t_16486)).validateExclusion(SrcTest.csid__703("name"), List.of("root", "admin", "superuser"));
            boolean t_16490 = cs__1107.isValid();
            Supplier<String> fn__16482 = () -> "should be valid";
            test_65.assert_(t_16490, fn__16482);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionFailsWhenValueInList__2306() {
        Test test_66 = new Test();
        try {
            Map<String, String> params__1109 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "admin")));
            TableDef t_16467 = SrcTest.userTable__704();
            SafeIdentifier t_16468 = SrcTest.csid__703("name");
            Changeset cs__1110 = SrcGlobal.changeset(t_16467, params__1109).cast(List.of(t_16468)).validateExclusion(SrcTest.csid__703("name"), List.of("root", "admin", "superuser"));
            boolean t_16474 = !cs__1110.isValid();
            Supplier<String> fn__16464 = () -> "should be invalid";
            test_66.assert_(t_16474, fn__16464);
            boolean t_16480 = Core.listGet(cs__1110.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__16463 = () -> "error on name";
            test_66.assert_(t_16480, fn__16463);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionSkipsWhenFieldNotInChanges__2307() {
        Test test_67 = new Test();
        try {
            Map<String, String> params__1112 = Core.mapConstructor(List.of());
            TableDef t_16455 = SrcTest.userTable__704();
            SafeIdentifier t_16456 = SrcTest.csid__703("name");
            Changeset cs__1113 = SrcGlobal.changeset(t_16455, params__1112).cast(List.of(t_16456)).validateExclusion(SrcTest.csid__703("name"), List.of("root", "admin"));
            boolean t_16460 = cs__1113.isValid();
            Supplier<String> fn__16453 = () -> "should be valid when field absent";
            test_67.assert_(t_16460, fn__16453);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanPasses__2308() {
        Test test_68 = new Test();
        try {
            Map<String, String> params__1115 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "25")));
            TableDef t_16444 = SrcTest.userTable__704();
            SafeIdentifier t_16445 = SrcTest.csid__703("age");
            Changeset cs__1116 = SrcGlobal.changeset(t_16444, params__1115).cast(List.of(t_16445)).validateNumber(SrcTest.csid__703("age"), new NumberValidationOpts(18.0D, null, null, null, null));
            boolean t_16450 = cs__1116.isValid();
            Supplier<String> fn__16441 = () -> "25 > 18 should pass";
            test_68.assert_(t_16450, fn__16441);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanFails__2309() {
        Test test_69 = new Test();
        try {
            Map<String, String> params__1118 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "15")));
            TableDef t_16431 = SrcTest.userTable__704();
            SafeIdentifier t_16432 = SrcTest.csid__703("age");
            Changeset cs__1119 = SrcGlobal.changeset(t_16431, params__1118).cast(List.of(t_16432)).validateNumber(SrcTest.csid__703("age"), new NumberValidationOpts(18.0D, null, null, null, null));
            boolean t_16439 = !cs__1119.isValid();
            Supplier<String> fn__16428 = () -> "15 > 18 should fail";
            test_69.assert_(t_16439, fn__16428);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanPasses__2310() {
        Test test_70 = new Test();
        try {
            Map<String, String> params__1121 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "8.5")));
            TableDef t_16419 = SrcTest.userTable__704();
            SafeIdentifier t_16420 = SrcTest.csid__703("score");
            Changeset cs__1122 = SrcGlobal.changeset(t_16419, params__1121).cast(List.of(t_16420)).validateNumber(SrcTest.csid__703("score"), new NumberValidationOpts(null, 10.0D, null, null, null));
            boolean t_16425 = cs__1122.isValid();
            Supplier<String> fn__16416 = () -> "8.5 < 10 should pass";
            test_70.assert_(t_16425, fn__16416);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanFails__2311() {
        Test test_71 = new Test();
        try {
            Map<String, String> params__1124 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "12.0")));
            TableDef t_16406 = SrcTest.userTable__704();
            SafeIdentifier t_16407 = SrcTest.csid__703("score");
            Changeset cs__1125 = SrcGlobal.changeset(t_16406, params__1124).cast(List.of(t_16407)).validateNumber(SrcTest.csid__703("score"), new NumberValidationOpts(null, 10.0D, null, null, null));
            boolean t_16414 = !cs__1125.isValid();
            Supplier<String> fn__16403 = () -> "12 < 10 should fail";
            test_71.assert_(t_16414, fn__16403);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanOrEqualBoundary__2312() {
        Test test_72 = new Test();
        try {
            Map<String, String> params__1127 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "18")));
            TableDef t_16394 = SrcTest.userTable__704();
            SafeIdentifier t_16395 = SrcTest.csid__703("age");
            Changeset cs__1128 = SrcGlobal.changeset(t_16394, params__1127).cast(List.of(t_16395)).validateNumber(SrcTest.csid__703("age"), new NumberValidationOpts(null, null, 18.0D, null, null));
            boolean t_16400 = cs__1128.isValid();
            Supplier<String> fn__16391 = () -> "18 >= 18 should pass";
            test_72.assert_(t_16400, fn__16391);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberCombinedOptions__2313() {
        Test test_73 = new Test();
        try {
            Map<String, String> params__1130 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "5.0")));
            TableDef t_16382 = SrcTest.userTable__704();
            SafeIdentifier t_16383 = SrcTest.csid__703("score");
            Changeset cs__1131 = SrcGlobal.changeset(t_16382, params__1130).cast(List.of(t_16383)).validateNumber(SrcTest.csid__703("score"), new NumberValidationOpts(0.0D, 10.0D, null, null, null));
            boolean t_16388 = cs__1131.isValid();
            Supplier<String> fn__16379 = () -> "5 > 0 and < 10 should pass";
            test_73.assert_(t_16388, fn__16379);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberNonNumericValue__2314() {
        Test test_74 = new Test();
        try {
            Map<String, String> params__1133 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "abc")));
            TableDef t_16363 = SrcTest.userTable__704();
            SafeIdentifier t_16364 = SrcTest.csid__703("age");
            Changeset cs__1134 = SrcGlobal.changeset(t_16363, params__1133).cast(List.of(t_16364)).validateNumber(SrcTest.csid__703("age"), new NumberValidationOpts(0.0D, null, null, null, null));
            boolean t_16371 = !cs__1134.isValid();
            Supplier<String> fn__16360 = () -> "non-numeric should fail";
            test_74.assert_(t_16371, fn__16360);
            boolean t_16377 = Core.listGet(cs__1134.getErrors(), 0).getMessage().equals("must be a number");
            Supplier<String> fn__16359 = () -> "correct error message";
            test_74.assert_(t_16377, fn__16359);
        } finally {
            test_74.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberSkipsWhenFieldNotInChanges__2315() {
        Test test_75 = new Test();
        try {
            Map<String, String> params__1136 = Core.mapConstructor(List.of());
            TableDef t_16350 = SrcTest.userTable__704();
            SafeIdentifier t_16351 = SrcTest.csid__703("age");
            Changeset cs__1137 = SrcGlobal.changeset(t_16350, params__1136).cast(List.of(t_16351)).validateNumber(SrcTest.csid__703("age"), new NumberValidationOpts(0.0D, null, null, null, null));
            boolean t_16356 = cs__1137.isValid();
            Supplier<String> fn__16348 = () -> "should be valid when field absent";
            test_75.assert_(t_16356, fn__16348);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateAcceptancePassesForTrueValues__2316() {
        Test test_76 = new Test();
        try {
            Consumer<String> fn__16345 = v__1139 -> {
                Map<String, String> params__1140 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__1139)));
                TableDef t_16337 = SrcTest.userTable__704();
                SafeIdentifier t_16338 = SrcTest.csid__703("active");
                Changeset cs__1141 = SrcGlobal.changeset(t_16337, params__1140).cast(List.of(t_16338)).validateAcceptance(SrcTest.csid__703("active"));
                boolean t_16342 = cs__1141.isValid();
                Supplier<String> fn__16334 = () -> "should accept: " + v__1139;
                test_76.assert_(t_16342, fn__16334);
            };
            List.of("true", "1", "yes", "on").forEach(fn__16345);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateAcceptanceFailsForNonTrueValues__2317() {
        Test test_77 = new Test();
        try {
            Map<String, String> params__1143 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", "false")));
            TableDef t_16319 = SrcTest.userTable__704();
            SafeIdentifier t_16320 = SrcTest.csid__703("active");
            Changeset cs__1144 = SrcGlobal.changeset(t_16319, params__1143).cast(List.of(t_16320)).validateAcceptance(SrcTest.csid__703("active"));
            boolean t_16326 = !cs__1144.isValid();
            Supplier<String> fn__16316 = () -> "false should not be accepted";
            test_77.assert_(t_16326, fn__16316);
            boolean t_16332 = Core.listGet(cs__1144.getErrors(), 0).getMessage().equals("must be accepted");
            Supplier<String> fn__16315 = () -> "correct message";
            test_77.assert_(t_16332, fn__16315);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationPassesWhenFieldsMatch__2318() {
        Test test_78 = new Test();
        try {
            TableDef tbl__1146 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("password"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("password_confirmation"), new StringField(), true, null, false)), null);
            Map<String, String> params__1147 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123"), new SimpleImmutableEntry<>("password_confirmation", "secret123")));
            SafeIdentifier t_16306 = SrcTest.csid__703("password");
            SafeIdentifier t_16307 = SrcTest.csid__703("password_confirmation");
            Changeset cs__1148 = SrcGlobal.changeset(tbl__1146, params__1147).cast(List.of(t_16306, t_16307)).validateConfirmation(SrcTest.csid__703("password"), SrcTest.csid__703("password_confirmation"));
            boolean t_16312 = cs__1148.isValid();
            Supplier<String> fn__16294 = () -> "matching fields should pass";
            test_78.assert_(t_16312, fn__16294);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationFailsWhenFieldsDiffer__2319() {
        Test test_79 = new Test();
        try {
            TableDef tbl__1150 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("password"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("password_confirmation"), new StringField(), true, null, false)), null);
            Map<String, String> params__1151 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123"), new SimpleImmutableEntry<>("password_confirmation", "wrong456")));
            SafeIdentifier t_16278 = SrcTest.csid__703("password");
            SafeIdentifier t_16279 = SrcTest.csid__703("password_confirmation");
            Changeset cs__1152 = SrcGlobal.changeset(tbl__1150, params__1151).cast(List.of(t_16278, t_16279)).validateConfirmation(SrcTest.csid__703("password"), SrcTest.csid__703("password_confirmation"));
            boolean t_16286 = !cs__1152.isValid();
            Supplier<String> fn__16266 = () -> "mismatched fields should fail";
            test_79.assert_(t_16286, fn__16266);
            boolean t_16292 = Core.listGet(cs__1152.getErrors(), 0).getField().equals("password_confirmation");
            Supplier<String> fn__16265 = () -> "error on confirmation field";
            test_79.assert_(t_16292, fn__16265);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationFailsWhenConfirmationMissing__2320() {
        Test test_80 = new Test();
        try {
            TableDef tbl__1154 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("password"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("password_confirmation"), new StringField(), true, null, false)), null);
            Map<String, String> params__1155 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123")));
            SafeIdentifier t_16256 = SrcTest.csid__703("password");
            Changeset cs__1156 = SrcGlobal.changeset(tbl__1154, params__1155).cast(List.of(t_16256)).validateConfirmation(SrcTest.csid__703("password"), SrcTest.csid__703("password_confirmation"));
            boolean t_16263 = !cs__1156.isValid();
            Supplier<String> fn__16245 = () -> "missing confirmation should fail";
            test_80.assert_(t_16263, fn__16245);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsPassesWhenSubstringFound__2321() {
        Test test_81 = new Test();
        try {
            Map<String, String> params__1158 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_16237 = SrcTest.userTable__704();
            SafeIdentifier t_16238 = SrcTest.csid__703("email");
            Changeset cs__1159 = SrcGlobal.changeset(t_16237, params__1158).cast(List.of(t_16238)).validateContains(SrcTest.csid__703("email"), "@");
            boolean t_16242 = cs__1159.isValid();
            Supplier<String> fn__16234 = () -> "should pass when @ present";
            test_81.assert_(t_16242, fn__16234);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsFailsWhenSubstringNotFound__2322() {
        Test test_82 = new Test();
        try {
            Map<String, String> params__1161 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice-example.com")));
            TableDef t_16225 = SrcTest.userTable__704();
            SafeIdentifier t_16226 = SrcTest.csid__703("email");
            Changeset cs__1162 = SrcGlobal.changeset(t_16225, params__1161).cast(List.of(t_16226)).validateContains(SrcTest.csid__703("email"), "@");
            boolean t_16232 = !cs__1162.isValid();
            Supplier<String> fn__16222 = () -> "should fail when @ absent";
            test_82.assert_(t_16232, fn__16222);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsSkipsWhenFieldNotInChanges__2323() {
        Test test_83 = new Test();
        try {
            Map<String, String> params__1164 = Core.mapConstructor(List.of());
            TableDef t_16214 = SrcTest.userTable__704();
            SafeIdentifier t_16215 = SrcTest.csid__703("email");
            Changeset cs__1165 = SrcGlobal.changeset(t_16214, params__1164).cast(List.of(t_16215)).validateContains(SrcTest.csid__703("email"), "@");
            boolean t_16219 = cs__1165.isValid();
            Supplier<String> fn__16212 = () -> "should be valid when field absent";
            test_83.assert_(t_16219, fn__16212);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateStartsWithPasses__2324() {
        Test test_84 = new Test();
        try {
            Map<String, String> params__1167 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Dr. Smith")));
            TableDef t_16204 = SrcTest.userTable__704();
            SafeIdentifier t_16205 = SrcTest.csid__703("name");
            Changeset cs__1168 = SrcGlobal.changeset(t_16204, params__1167).cast(List.of(t_16205)).validateStartsWith(SrcTest.csid__703("name"), "Dr.");
            boolean t_16209 = cs__1168.isValid();
            Supplier<String> fn__16201 = () -> "should pass for Dr. prefix";
            test_84.assert_(t_16209, fn__16201);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateStartsWithFails__2325() {
        Test test_85 = new Test();
        try {
            Map<String, String> params__1170 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Mr. Smith")));
            TableDef t_16192 = SrcTest.userTable__704();
            SafeIdentifier t_16193 = SrcTest.csid__703("name");
            Changeset cs__1171 = SrcGlobal.changeset(t_16192, params__1170).cast(List.of(t_16193)).validateStartsWith(SrcTest.csid__703("name"), "Dr.");
            boolean t_16199 = !cs__1171.isValid();
            Supplier<String> fn__16189 = () -> "should fail for Mr. prefix";
            test_85.assert_(t_16199, fn__16189);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithPasses__2326() {
        Test test_86 = new Test();
        try {
            Map<String, String> params__1173 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_16181 = SrcTest.userTable__704();
            SafeIdentifier t_16182 = SrcTest.csid__703("email");
            Changeset cs__1174 = SrcGlobal.changeset(t_16181, params__1173).cast(List.of(t_16182)).validateEndsWith(SrcTest.csid__703("email"), ".com");
            boolean t_16186 = cs__1174.isValid();
            Supplier<String> fn__16178 = () -> "should pass for .com suffix";
            test_86.assert_(t_16186, fn__16178);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithFails__2327() {
        Test test_87 = new Test();
        try {
            Map<String, String> params__1176 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.org")));
            TableDef t_16169 = SrcTest.userTable__704();
            SafeIdentifier t_16170 = SrcTest.csid__703("email");
            Changeset cs__1177 = SrcGlobal.changeset(t_16169, params__1176).cast(List.of(t_16170)).validateEndsWith(SrcTest.csid__703("email"), ".com");
            boolean t_16176 = !cs__1177.isValid();
            Supplier<String> fn__16166 = () -> "should fail for .org when expecting .com";
            test_87.assert_(t_16176, fn__16166);
        } finally {
            test_87.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithHandlesRepeatedSuffixCorrectly__2328() {
        Test test_88 = new Test();
        try {
            Map<String, String> params__1179 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "abcabc")));
            TableDef t_16158 = SrcTest.userTable__704();
            SafeIdentifier t_16159 = SrcTest.csid__703("name");
            Changeset cs__1180 = SrcGlobal.changeset(t_16158, params__1179).cast(List.of(t_16159)).validateEndsWith(SrcTest.csid__703("name"), "abc");
            boolean t_16163 = cs__1180.isValid();
            Supplier<String> fn__16155 = () -> "abcabc should end with abc";
            test_88.assert_(t_16163, fn__16155);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlUsesDefaultValueWhenFieldNotInChanges__2329() {
        Test test_89 = new Test();
        try {
            TableDef tbl__1182 = new TableDef(SrcTest.csid__703("posts"), List.of(new FieldDef(SrcTest.csid__703("title"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("status"), new StringField(), false, new SqlDefault(), false)), null);
            Map<String, String> params__1183 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("title", "Hello")));
            SafeIdentifier t_16139 = SrcTest.csid__703("title");
            Changeset cs__1184 = SrcGlobal.changeset(tbl__1182, params__1183).cast(List.of(t_16139));
            SqlFragment t_8735;
            t_8735 = cs__1184.toInsertSql();
            SqlFragment t_8736 = t_8735;
            String s__1185 = t_8736.toString();
            boolean t_16143 = s__1185.indexOf("INSERT INTO posts") >= 0;
            Supplier<String> fn__16127 = () -> "has INSERT INTO: " + s__1185;
            test_89.assert_(t_16143, fn__16127);
            boolean t_16147 = s__1185.indexOf("'Hello'") >= 0;
            Supplier<String> fn__16126 = () -> "has title value: " + s__1185;
            test_89.assert_(t_16147, fn__16126);
            boolean t_16151 = s__1185.indexOf("DEFAULT") >= 0;
            Supplier<String> fn__16125 = () -> "status should use DEFAULT: " + s__1185;
            test_89.assert_(t_16151, fn__16125);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlChangeOverridesDefaultValue__2330() {
        Test test_90 = new Test();
        try {
            TableDef tbl__1187 = new TableDef(SrcTest.csid__703("posts"), List.of(new FieldDef(SrcTest.csid__703("title"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("status"), new StringField(), false, new SqlDefault(), false)), null);
            Map<String, String> params__1188 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("title", "Hello"), new SimpleImmutableEntry<>("status", "published")));
            SafeIdentifier t_16117 = SrcTest.csid__703("title");
            SafeIdentifier t_16118 = SrcTest.csid__703("status");
            Changeset cs__1189 = SrcGlobal.changeset(tbl__1187, params__1188).cast(List.of(t_16117, t_16118));
            SqlFragment t_8715;
            t_8715 = cs__1189.toInsertSql();
            SqlFragment t_8716 = t_8715;
            String s__1190 = t_8716.toString();
            boolean t_16122 = s__1190.indexOf("'published'") >= 0;
            Supplier<String> fn__16104 = () -> "should use provided value: " + s__1190;
            test_90.assert_(t_16122, fn__16104);
        } finally {
            test_90.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlWithTimestampsUsesDefault__2331() {
        Test test_91 = new Test();
        try {
            List<FieldDef> t_8662;
            t_8662 = SrcGlobal.timestamps();
            List<FieldDef> ts__1192 = t_8662;
            List<FieldDef> fields__1193 = new ArrayList<>();
            Core.listAdd(fields__1193, new FieldDef(SrcTest.csid__703("title"), new StringField(), false, null, false));
            Consumer<FieldDef> fn__16070 = t__1194 -> {
                Core.listAdd(fields__1193, t__1194);
            };
            ts__1192.forEach(fn__16070);
            TableDef tbl__1195 = new TableDef(SrcTest.csid__703("articles"), List.copyOf(fields__1193), null);
            Map<String, String> params__1196 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("title", "News")));
            SafeIdentifier t_16083 = SrcTest.csid__703("title");
            Changeset cs__1197 = SrcGlobal.changeset(tbl__1195, params__1196).cast(List.of(t_16083));
            SqlFragment t_8677;
            t_8677 = cs__1197.toInsertSql();
            SqlFragment t_8678 = t_8677;
            String s__1198 = t_8678.toString();
            boolean t_16087 = s__1198.indexOf("inserted_at") >= 0;
            Supplier<String> fn__16069 = () -> "should include inserted_at: " + s__1198;
            test_91.assert_(t_16087, fn__16069);
            boolean t_16091 = s__1198.indexOf("updated_at") >= 0;
            Supplier<String> fn__16068 = () -> "should include updated_at: " + s__1198;
            test_91.assert_(t_16091, fn__16068);
            boolean t_16095 = s__1198.indexOf("DEFAULT") >= 0;
            Supplier<String> fn__16067 = () -> "timestamps should use DEFAULT: " + s__1198;
            test_91.assert_(t_16095, fn__16067);
        } finally {
            test_91.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlSkipsVirtualFields__2332() {
        Test test_92 = new Test();
        try {
            TableDef tbl__1200 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("full_name"), new StringField(), true, null, true)), null);
            Map<String, String> params__1201 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("full_name", "Alice Smith")));
            SafeIdentifier t_16053 = SrcTest.csid__703("name");
            SafeIdentifier t_16054 = SrcTest.csid__703("full_name");
            Changeset cs__1202 = SrcGlobal.changeset(tbl__1200, params__1201).cast(List.of(t_16053, t_16054));
            SqlFragment t_8651;
            t_8651 = cs__1202.toInsertSql();
            SqlFragment t_8652 = t_8651;
            String s__1203 = t_8652.toString();
            boolean t_16058 = s__1203.indexOf("'Alice'") >= 0;
            Supplier<String> fn__16041 = () -> "name should be included: " + s__1203;
            test_92.assert_(t_16058, fn__16041);
            boolean t_16064 = s__1203.indexOf("full_name") < 0;
            Supplier<String> fn__16040 = () -> "virtual field should be excluded: " + s__1203;
            test_92.assert_(t_16064, fn__16040);
        } finally {
            test_92.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlAllowsMissingNonNullableVirtualField__2333() {
        Test test_93 = new Test();
        try {
            TableDef tbl__1205 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("computed"), new StringField(), false, null, true)), null);
            Map<String, String> params__1206 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            SafeIdentifier t_16033 = SrcTest.csid__703("name");
            Changeset cs__1207 = SrcGlobal.changeset(tbl__1205, params__1206).cast(List.of(t_16033));
            SqlFragment t_8630;
            t_8630 = cs__1207.toInsertSql();
            SqlFragment t_8631 = t_8630;
            String s__1208 = t_8631.toString();
            boolean t_16037 = s__1208.indexOf("'Alice'") >= 0;
            Supplier<String> fn__16022 = () -> "should succeed: " + s__1208;
            test_93.assert_(t_16037, fn__16022);
        } finally {
            test_93.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlSkipsVirtualFields__2334() {
        Test test_94 = new Test();
        try {
            TableDef tbl__1210 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("display"), new StringField(), true, null, true)), null);
            Map<String, String> params__1211 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("display", "Bobby")));
            SafeIdentifier t_16009 = SrcTest.csid__703("name");
            SafeIdentifier t_16010 = SrcTest.csid__703("display");
            Changeset cs__1212 = SrcGlobal.changeset(tbl__1210, params__1211).cast(List.of(t_16009, t_16010));
            SqlFragment t_8607;
            t_8607 = cs__1212.toUpdateSql(1);
            SqlFragment t_8608 = t_8607;
            String s__1213 = t_8608.toString();
            boolean t_16014 = s__1213.indexOf("name = 'Bob'") >= 0;
            Supplier<String> fn__15997 = () -> "name should be in SET: " + s__1213;
            test_94.assert_(t_16014, fn__15997);
            boolean t_16020 = s__1213.indexOf("display") < 0;
            Supplier<String> fn__15996 = () -> "virtual field excluded from UPDATE: " + s__1213;
            test_94.assert_(t_16020, fn__15996);
        } finally {
            test_94.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlUsesCustomPrimaryKey__2335() {
        Test test_95 = new Test();
        try {
            TableDef tbl__1215 = new TableDef(SrcTest.csid__703("posts"), List.of(new FieldDef(SrcTest.csid__703("title"), new StringField(), false, null, false)), SrcTest.csid__703("post_id"));
            Map<String, String> params__1216 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("title", "Updated")));
            SafeIdentifier t_15990 = SrcTest.csid__703("title");
            Changeset cs__1217 = SrcGlobal.changeset(tbl__1215, params__1216).cast(List.of(t_15990));
            SqlFragment t_8589;
            t_8589 = cs__1217.toUpdateSql(99);
            SqlFragment t_8590 = t_8589;
            String s__1218 = t_8590.toString();
            boolean t_15994 = s__1218.equals("UPDATE posts SET title = 'Updated' WHERE post_id = 99");
            Supplier<String> fn__15980 = () -> "got: " + s__1218;
            test_95.assert_(t_15994, fn__15980);
        } finally {
            test_95.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteSqlUsesCustomPrimaryKey__2336() {
        Test test_96 = new Test();
        try {
            TableDef tbl__1220 = new TableDef(SrcTest.csid__703("posts"), List.of(new FieldDef(SrcTest.csid__703("title"), new StringField(), false, null, false)), SrcTest.csid__703("post_id"));
            String s__1221 = SrcGlobal.deleteSql(tbl__1220, 42).toString();
            boolean t_15967 = s__1221.equals("DELETE FROM posts WHERE post_id = 42");
            Supplier<String> fn__15956 = () -> "got: " + s__1221;
            test_96.assert_(t_15967, fn__15956);
        } finally {
            test_96.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteSqlUsesDefaultIdWhenPrimaryKeyNull__2337() {
        Test test_97 = new Test();
        try {
            TableDef tbl__1223 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false)), null);
            String s__1224 = SrcGlobal.deleteSql(tbl__1223, 7).toString();
            boolean t_15954 = s__1224.equals("DELETE FROM users WHERE id = 7");
            Supplier<String> fn__15945 = () -> "got: " + s__1224;
            test_97.assert_(t_15954, fn__15945);
        } finally {
            test_97.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void alreadyInvalidChangesetSkipsSubsequentValidators__2338() {
        Test test_98 = new Test();
        try {
            Map<String, String> params__1226 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_15919 = SrcTest.userTable__704();
            SafeIdentifier t_15920 = SrcTest.csid__703("name");
            SafeIdentifier t_15921 = SrcTest.csid__703("email");
            Changeset cs__1227 = SrcGlobal.changeset(t_15919, params__1226).cast(List.of(t_15920, t_15921)).validateLength(SrcTest.csid__703("name"), 3, 50).validateRequired(List.of(SrcTest.csid__703("name"), SrcTest.csid__703("email"))).validateContains(SrcTest.csid__703("email"), "@");
            boolean t_15932 = !cs__1227.isValid();
            Supplier<String> fn__15915 = () -> "should be invalid from validateLength";
            test_98.assert_(t_15932, fn__15915);
            boolean t_15937 = cs__1227.getErrors().size() == 1;
            Supplier<String> fn__15914 = () -> "should have exactly 1 error, not accumulate: " + Integer.toString(cs__1227.getErrors().size());
            test_98.assert_(t_15937, fn__15914);
            boolean t_15943 = Core.listGet(cs__1227.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__15913 = () -> "error should be on name";
            test_98.assert_(t_15943, fn__15913);
        } finally {
            test_98.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanOrEqualPassesAtBoundary__2339() {
        Test test_99 = new Test();
        try {
            Map<String, String> params__1229 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "10.0")));
            TableDef t_15901 = SrcTest.userTable__704();
            SafeIdentifier t_15902 = SrcTest.csid__703("score");
            Changeset cs__1230 = SrcGlobal.changeset(t_15901, params__1229).cast(List.of(t_15902)).validateNumber(SrcTest.csid__703("score"), new NumberValidationOpts(null, null, null, 10.0D, null));
            boolean t_15907 = cs__1230.isValid();
            Supplier<String> fn__15898 = () -> "10.0 <= 10.0 should pass";
            test_99.assert_(t_15907, fn__15898);
        } finally {
            test_99.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanOrEqualFailsAboveBoundary__2340() {
        Test test_100 = new Test();
        try {
            Map<String, String> params__1232 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "10.1")));
            TableDef t_15882 = SrcTest.userTable__704();
            SafeIdentifier t_15883 = SrcTest.csid__703("score");
            Changeset cs__1233 = SrcGlobal.changeset(t_15882, params__1232).cast(List.of(t_15883)).validateNumber(SrcTest.csid__703("score"), new NumberValidationOpts(null, null, null, 10.0D, null));
            boolean t_15890 = !cs__1233.isValid();
            Supplier<String> fn__15879 = () -> "10.1 <= 10.0 should fail";
            test_100.assert_(t_15890, fn__15879);
            boolean t_15896 = Core.listGet(cs__1233.getErrors(), 0).getMessage().equals("must be less than or equal to 10.0");
            Supplier<String> fn__15878 = () -> "correct message";
            test_100.assert_(t_15896, fn__15878);
        } finally {
            test_100.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberEqualToPassesWhenEqual__2341() {
        Test test_101 = new Test();
        try {
            Map<String, String> params__1235 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "42.0")));
            TableDef t_15869 = SrcTest.userTable__704();
            SafeIdentifier t_15870 = SrcTest.csid__703("score");
            Changeset cs__1236 = SrcGlobal.changeset(t_15869, params__1235).cast(List.of(t_15870)).validateNumber(SrcTest.csid__703("score"), new NumberValidationOpts(null, null, null, null, 42.0D));
            boolean t_15875 = cs__1236.isValid();
            Supplier<String> fn__15866 = () -> "42.0 == 42.0 should pass";
            test_101.assert_(t_15875, fn__15866);
        } finally {
            test_101.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberEqualToFailsWhenNotEqual__2342() {
        Test test_102 = new Test();
        try {
            Map<String, String> params__1238 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "41.9")));
            TableDef t_15850 = SrcTest.userTable__704();
            SafeIdentifier t_15851 = SrcTest.csid__703("score");
            Changeset cs__1239 = SrcGlobal.changeset(t_15850, params__1238).cast(List.of(t_15851)).validateNumber(SrcTest.csid__703("score"), new NumberValidationOpts(null, null, null, null, 42.0D));
            boolean t_15858 = !cs__1239.isValid();
            Supplier<String> fn__15847 = () -> "41.9 == 42.0 should fail";
            test_102.assert_(t_15858, fn__15847);
            boolean t_15864 = Core.listGet(cs__1239.getErrors(), 0).getMessage().equals("must be equal to 42.0");
            Supplier<String> fn__15846 = () -> "correct message";
            test_102.assert_(t_15864, fn__15846);
        } finally {
            test_102.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanFailsAtExactThreshold__2343() {
        Test test_103 = new Test();
        try {
            Map<String, String> params__1241 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "18")));
            TableDef t_15836 = SrcTest.userTable__704();
            SafeIdentifier t_15837 = SrcTest.csid__703("age");
            Changeset cs__1242 = SrcGlobal.changeset(t_15836, params__1241).cast(List.of(t_15837)).validateNumber(SrcTest.csid__703("age"), new NumberValidationOpts(18.0D, null, null, null, null));
            boolean t_15844 = !cs__1242.isValid();
            Supplier<String> fn__15833 = () -> "18 > 18 should fail (strict greater than)";
            test_103.assert_(t_15844, fn__15833);
        } finally {
            test_103.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanFailsAtExactThreshold__2344() {
        Test test_104 = new Test();
        try {
            Map<String, String> params__1244 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "10.0")));
            TableDef t_15823 = SrcTest.userTable__704();
            SafeIdentifier t_15824 = SrcTest.csid__703("score");
            Changeset cs__1245 = SrcGlobal.changeset(t_15823, params__1244).cast(List.of(t_15824)).validateNumber(SrcTest.csid__703("score"), new NumberValidationOpts(null, 10.0D, null, null, null));
            boolean t_15831 = !cs__1245.isValid();
            Supplier<String> fn__15820 = () -> "10.0 < 10.0 should fail (strict less than)";
            test_104.assert_(t_15831, fn__15820);
        } finally {
            test_104.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatFailsForNonFloatString__2345() {
        Test test_105 = new Test();
        try {
            Map<String, String> params__1247 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "abc")));
            TableDef t_15805 = SrcTest.userTable__704();
            SafeIdentifier t_15806 = SrcTest.csid__703("score");
            Changeset cs__1248 = SrcGlobal.changeset(t_15805, params__1247).cast(List.of(t_15806)).validateFloat(SrcTest.csid__703("score"));
            boolean t_15812 = !cs__1248.isValid();
            Supplier<String> fn__15802 = () -> "abc should not parse as float";
            test_105.assert_(t_15812, fn__15802);
            boolean t_15818 = Core.listGet(cs__1248.getErrors(), 0).getMessage().equals("must be a number");
            Supplier<String> fn__15801 = () -> "correct message";
            test_105.assert_(t_15818, fn__15801);
        } finally {
            test_105.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlWithAllSixFieldTypes__2346() {
        Test test_106 = new Test();
        try {
            TableDef tbl__1250 = new TableDef(SrcTest.csid__703("records"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("count"), new IntField(), false, null, false), new FieldDef(SrcTest.csid__703("big_id"), new Int64Field(), false, null, false), new FieldDef(SrcTest.csid__703("rating"), new FloatField(), false, null, false), new FieldDef(SrcTest.csid__703("active"), new BoolField(), false, null, false), new FieldDef(SrcTest.csid__703("birthday"), new DateField(), false, null, false)), null);
            Map<String, String> params__1251 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("count", "42"), new SimpleImmutableEntry<>("big_id", "9999999999"), new SimpleImmutableEntry<>("rating", "3.14"), new SimpleImmutableEntry<>("active", "true"), new SimpleImmutableEntry<>("birthday", "2000-01-15")));
            SafeIdentifier t_15769 = SrcTest.csid__703("name");
            SafeIdentifier t_15770 = SrcTest.csid__703("count");
            SafeIdentifier t_15771 = SrcTest.csid__703("big_id");
            SafeIdentifier t_15772 = SrcTest.csid__703("rating");
            SafeIdentifier t_15773 = SrcTest.csid__703("active");
            SafeIdentifier t_15774 = SrcTest.csid__703("birthday");
            Changeset cs__1252 = SrcGlobal.changeset(tbl__1250, params__1251).cast(List.of(t_15769, t_15770, t_15771, t_15772, t_15773, t_15774));
            SqlFragment t_8416;
            t_8416 = cs__1252.toInsertSql();
            SqlFragment t_8417 = t_8416;
            String s__1253 = t_8417.toString();
            boolean t_15778 = s__1253.indexOf("'Alice'") >= 0;
            Supplier<String> fn__15741 = () -> "string field: " + s__1253;
            test_106.assert_(t_15778, fn__15741);
            boolean t_15782 = s__1253.indexOf("42") >= 0;
            Supplier<String> fn__15740 = () -> "int field: " + s__1253;
            test_106.assert_(t_15782, fn__15740);
            boolean t_15786 = s__1253.indexOf("9999999999") >= 0;
            Supplier<String> fn__15739 = () -> "int64 field: " + s__1253;
            test_106.assert_(t_15786, fn__15739);
            boolean t_15790 = s__1253.indexOf("3.14") >= 0;
            Supplier<String> fn__15738 = () -> "float field: " + s__1253;
            test_106.assert_(t_15790, fn__15738);
            boolean t_15794 = s__1253.indexOf("TRUE") >= 0;
            Supplier<String> fn__15737 = () -> "bool field: " + s__1253;
            test_106.assert_(t_15794, fn__15737);
            boolean t_15798 = s__1253.indexOf("'2000-01-15'") >= 0;
            Supplier<String> fn__15736 = () -> "date field: " + s__1253;
            test_106.assert_(t_15798, fn__15736);
        } finally {
            test_106.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteChangeOnNonNullableFieldCausesToInsertSqlToBubble__2347() {
        Test test_107 = new Test();
        try {
            TableDef tbl__1255 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("email"), new StringField(), false, null, false)), null);
            Map<String, String> params__1256 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@b.com")));
            SafeIdentifier t_15729 = SrcTest.csid__703("name");
            SafeIdentifier t_15730 = SrcTest.csid__703("email");
            Changeset cs__1257 = SrcGlobal.changeset(tbl__1255, params__1256).cast(List.of(t_15729, t_15730)).deleteChange(SrcTest.csid__703("email"));
            boolean didBubble__1258;
            boolean didBubble_17425;
            try {
                cs__1257.toInsertSql();
                didBubble_17425 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_17425 = true;
            }
            didBubble__1258 = didBubble_17425;
            Supplier<String> fn__15717 = () -> "removing non-nullable field should make toInsertSql bubble";
            test_107.assert_(didBubble__1258, fn__15717);
        } finally {
            test_107.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesAtExactMin__2348() {
        Test test_108 = new Test();
        try {
            Map<String, String> params__1260 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "abc")));
            TableDef t_15709 = SrcTest.userTable__704();
            SafeIdentifier t_15710 = SrcTest.csid__703("name");
            Changeset cs__1261 = SrcGlobal.changeset(t_15709, params__1260).cast(List.of(t_15710)).validateLength(SrcTest.csid__703("name"), 3, 10);
            boolean t_15714 = cs__1261.isValid();
            Supplier<String> fn__15706 = () -> "length 3 should pass for min 3";
            test_108.assert_(t_15714, fn__15706);
        } finally {
            test_108.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesAtExactMax__2349() {
        Test test_109 = new Test();
        try {
            Map<String, String> params__1263 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "abcdefghij")));
            TableDef t_15698 = SrcTest.userTable__704();
            SafeIdentifier t_15699 = SrcTest.csid__703("name");
            Changeset cs__1264 = SrcGlobal.changeset(t_15698, params__1263).cast(List.of(t_15699)).validateLength(SrcTest.csid__703("name"), 1, 10);
            boolean t_15703 = cs__1264.isValid();
            Supplier<String> fn__15695 = () -> "length 10 should pass for max 10";
            test_109.assert_(t_15703, fn__15695);
        } finally {
            test_109.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateAcceptanceSkipsWhenFieldNotInChanges__2350() {
        Test test_110 = new Test();
        try {
            Map<String, String> params__1266 = Core.mapConstructor(List.of());
            TableDef t_15687 = SrcTest.userTable__704();
            SafeIdentifier t_15688 = SrcTest.csid__703("active");
            Changeset cs__1267 = SrcGlobal.changeset(t_15687, params__1266).cast(List.of(t_15688)).validateAcceptance(SrcTest.csid__703("active"));
            boolean t_15692 = cs__1267.isValid();
            Supplier<String> fn__15685 = () -> "should be valid when field absent";
            test_110.assert_(t_15692, fn__15685);
        } finally {
            test_110.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleValidatorsChainCorrectlyOnValidChangeset__2351() {
        Test test_111 = new Test();
        try {
            Map<String, String> params__1269 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_15660 = SrcTest.userTable__704();
            SafeIdentifier t_15661 = SrcTest.csid__703("name");
            SafeIdentifier t_15662 = SrcTest.csid__703("email");
            SafeIdentifier t_15663 = SrcTest.csid__703("age");
            Changeset cs__1270 = SrcGlobal.changeset(t_15660, params__1269).cast(List.of(t_15661, t_15662, t_15663)).validateRequired(List.of(SrcTest.csid__703("name"), SrcTest.csid__703("email"))).validateLength(SrcTest.csid__703("name"), 2, 50).validateContains(SrcTest.csid__703("email"), "@").validateInt(SrcTest.csid__703("age")).validateNumber(SrcTest.csid__703("age"), new NumberValidationOpts(0.0D, 150.0D, null, null, null));
            boolean t_15677 = cs__1270.isValid();
            Supplier<String> fn__15655 = () -> "all validators should pass";
            test_111.assert_(t_15677, fn__15655);
            boolean t_15683 = cs__1270.getErrors().size() == 0;
            Supplier<String> fn__15654 = () -> "no errors expected";
            test_111.assert_(t_15683, fn__15654);
        } finally {
            test_111.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlWithMultipleNonVirtualFields__2352() {
        Test test_112 = new Test();
        try {
            TableDef tbl__1272 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("email"), new StringField(), false, null, false)), null);
            Map<String, String> params__1273 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "bob@example.com")));
            SafeIdentifier t_15638 = SrcTest.csid__703("name");
            SafeIdentifier t_15639 = SrcTest.csid__703("email");
            Changeset cs__1274 = SrcGlobal.changeset(tbl__1272, params__1273).cast(List.of(t_15638, t_15639));
            SqlFragment t_8297;
            t_8297 = cs__1274.toUpdateSql(5);
            SqlFragment t_8298 = t_8297;
            String s__1275 = t_8298.toString();
            boolean t_15643 = s__1275.indexOf("name = 'Bob'") >= 0;
            Supplier<String> fn__15626 = () -> "name in SET: " + s__1275;
            test_112.assert_(t_15643, fn__15626);
            boolean t_15647 = s__1275.indexOf("email = 'bob@example.com'") >= 0;
            Supplier<String> fn__15625 = () -> "email in SET: " + s__1275;
            test_112.assert_(t_15647, fn__15625);
            boolean t_15651 = s__1275.indexOf("WHERE id = 5") >= 0;
            Supplier<String> fn__15624 = () -> "WHERE clause: " + s__1275;
            test_112.assert_(t_15651, fn__15624);
        } finally {
            test_112.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesWhenAllChangesAreVirtualFields__2353() {
        Test test_113 = new Test();
        try {
            TableDef tbl__1277 = new TableDef(SrcTest.csid__703("users"), List.of(new FieldDef(SrcTest.csid__703("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__703("computed"), new StringField(), true, null, true)), null);
            Map<String, String> params__1278 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("computed", "derived")));
            SafeIdentifier t_15620 = SrcTest.csid__703("computed");
            Changeset cs__1279 = SrcGlobal.changeset(tbl__1277, params__1278).cast(List.of(t_15620));
            boolean didBubble__1280;
            boolean didBubble_17426;
            try {
                cs__1279.toUpdateSql(1);
                didBubble_17426 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_17426 = true;
            }
            didBubble__1280 = didBubble_17426;
            Supplier<String> fn__15608 = () -> "should bubble when all changes are virtual";
            test_113.assert_(didBubble__1280, fn__15608);
        } finally {
            test_113.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeSatisfiesSubsequentValidateRequired__2354() {
        Test test_114 = new Test();
        try {
            Map<String, String> params__1282 = Core.mapConstructor(List.of());
            TableDef t_15598 = SrcTest.userTable__704();
            SafeIdentifier t_15599 = SrcTest.csid__703("name");
            Changeset cs__1283 = SrcGlobal.changeset(t_15598, params__1282).cast(List.of(t_15599)).putChange(SrcTest.csid__703("name"), "Injected").validateRequired(List.of(SrcTest.csid__703("name")));
            boolean t_15605 = cs__1283.isValid();
            Supplier<String> fn__15596 = () -> "putChange should satisfy required";
            test_114.assert_(t_15605, fn__15596);
        } finally {
            test_114.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateStartsWithSkipsWhenFieldNotInChanges__2355() {
        Test test_115 = new Test();
        try {
            Map<String, String> params__1285 = Core.mapConstructor(List.of());
            TableDef t_15588 = SrcTest.userTable__704();
            SafeIdentifier t_15589 = SrcTest.csid__703("name");
            Changeset cs__1286 = SrcGlobal.changeset(t_15588, params__1285).cast(List.of(t_15589)).validateStartsWith(SrcTest.csid__703("name"), "Dr.");
            boolean t_15593 = cs__1286.isValid();
            Supplier<String> fn__15586 = () -> "should be valid when field absent";
            test_115.assert_(t_15593, fn__15586);
        } finally {
            test_115.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithSkipsWhenFieldNotInChanges__2356() {
        Test test_116 = new Test();
        try {
            Map<String, String> params__1288 = Core.mapConstructor(List.of());
            TableDef t_15578 = SrcTest.userTable__704();
            SafeIdentifier t_15579 = SrcTest.csid__703("name");
            Changeset cs__1289 = SrcGlobal.changeset(t_15578, params__1288).cast(List.of(t_15579)).validateEndsWith(SrcTest.csid__703("name"), ".com");
            boolean t_15583 = cs__1289.isValid();
            Supplier<String> fn__15576 = () -> "should be valid when field absent";
            test_116.assert_(t_15583, fn__15576);
        } finally {
            test_116.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntAcceptsZero__2357() {
        Test test_117 = new Test();
        try {
            Map<String, String> params__1291 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "0")));
            TableDef t_15568 = SrcTest.userTable__704();
            SafeIdentifier t_15569 = SrcTest.csid__703("age");
            Changeset cs__1292 = SrcGlobal.changeset(t_15568, params__1291).cast(List.of(t_15569)).validateInt(SrcTest.csid__703("age"));
            boolean t_15573 = cs__1292.isValid();
            Supplier<String> fn__15565 = () -> "0 should be a valid int";
            test_117.assert_(t_15573, fn__15565);
        } finally {
            test_117.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntAcceptsNegative__2358() {
        Test test_118 = new Test();
        try {
            Map<String, String> params__1294 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "-5")));
            TableDef t_15557 = SrcTest.userTable__704();
            SafeIdentifier t_15558 = SrcTest.csid__703("age");
            Changeset cs__1295 = SrcGlobal.changeset(t_15557, params__1294).cast(List.of(t_15558)).validateInt(SrcTest.csid__703("age"));
            boolean t_15562 = cs__1295.isValid();
            Supplier<String> fn__15554 = () -> "-5 should be a valid int";
            test_118.assert_(t_15562, fn__15554);
        } finally {
            test_118.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void changesetImmutabilityValidatorsDoNotMutateBase__2359() {
        Test test_119 = new Test();
        try {
            Map<String, String> params__1297 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_15538 = SrcTest.userTable__704();
            SafeIdentifier t_15539 = SrcTest.csid__703("name");
            SafeIdentifier t_15540 = SrcTest.csid__703("email");
            Changeset base__1298 = SrcGlobal.changeset(t_15538, params__1297).cast(List.of(t_15539, t_15540));
            Changeset failed__1299 = base__1298.validateLength(SrcTest.csid__703("name"), 3, 50);
            Changeset passed__1300 = base__1298.validateRequired(List.of(SrcTest.csid__703("name"), SrcTest.csid__703("email")));
            boolean t_15549 = !failed__1299.isValid();
            Supplier<String> fn__15534 = () -> "failed branch should be invalid";
            test_119.assert_(t_15549, fn__15534);
            boolean t_15551 = passed__1300.isValid();
            Supplier<String> fn__15533 = () -> "passed branch should still be valid";
            test_119.assert_(t_15551, fn__15533);
        } finally {
            test_119.softFailToHard();
        }
    }
    static SafeIdentifier sid__709(String name__1650) {
        SafeIdentifier t_7743;
        t_7743 = SrcGlobal.safeIdentifier(name__1650);
        return t_7743;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__2441() {
        Test test_120 = new Test();
        try {
            Query q__1653 = SrcGlobal.from(SrcTest.sid__709("users"));
            boolean t_15091 = q__1653.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__15086 = () -> "bare query";
            test_120.assert_(t_15091, fn__15086);
        } finally {
            test_120.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__2442() {
        Test test_121 = new Test();
        try {
            SafeIdentifier t_15077 = SrcTest.sid__709("users");
            SafeIdentifier t_15078 = SrcTest.sid__709("id");
            SafeIdentifier t_15079 = SrcTest.sid__709("name");
            Query q__1655 = SrcGlobal.from(t_15077).select(List.of(t_15078, t_15079));
            boolean t_15084 = q__1655.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__15076 = () -> "select columns";
            test_121.assert_(t_15084, fn__15076);
        } finally {
            test_121.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__2443() {
        Test test_122 = new Test();
        try {
            SafeIdentifier t_15065 = SrcTest.sid__709("users");
            SqlBuilder t_15066 = new SqlBuilder();
            t_15066.appendSafe("age > ");
            t_15066.appendInt32(18);
            SqlFragment t_15069 = t_15066.getAccumulated();
            Query q__1657 = SrcGlobal.from(t_15065).where(t_15069);
            boolean t_15074 = q__1657.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__15064 = () -> "where int";
            test_122.assert_(t_15074, fn__15064);
        } finally {
            test_122.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__2445() {
        Test test_123 = new Test();
        try {
            SafeIdentifier t_15053 = SrcTest.sid__709("users");
            SqlBuilder t_15054 = new SqlBuilder();
            t_15054.appendSafe("active = ");
            t_15054.appendBoolean(true);
            SqlFragment t_15057 = t_15054.getAccumulated();
            Query q__1659 = SrcGlobal.from(t_15053).where(t_15057);
            boolean t_15062 = q__1659.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__15052 = () -> "where bool";
            test_123.assert_(t_15062, fn__15052);
        } finally {
            test_123.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__2447() {
        Test test_124 = new Test();
        try {
            SafeIdentifier t_15036 = SrcTest.sid__709("users");
            SqlBuilder t_15037 = new SqlBuilder();
            t_15037.appendSafe("age > ");
            t_15037.appendInt32(18);
            SqlFragment t_15040 = t_15037.getAccumulated();
            Query t_15041 = SrcGlobal.from(t_15036).where(t_15040);
            SqlBuilder t_15042 = new SqlBuilder();
            t_15042.appendSafe("active = ");
            t_15042.appendBoolean(true);
            Query q__1661 = t_15041.where(t_15042.getAccumulated());
            boolean t_15050 = q__1661.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__15035 = () -> "chained where";
            test_124.assert_(t_15050, fn__15035);
        } finally {
            test_124.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__2450() {
        Test test_125 = new Test();
        try {
            SafeIdentifier t_15027 = SrcTest.sid__709("users");
            SafeIdentifier t_15028 = SrcTest.sid__709("name");
            Query q__1663 = SrcGlobal.from(t_15027).orderBy(t_15028, true);
            boolean t_15033 = q__1663.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__15026 = () -> "order asc";
            test_125.assert_(t_15033, fn__15026);
        } finally {
            test_125.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__2451() {
        Test test_126 = new Test();
        try {
            SafeIdentifier t_15018 = SrcTest.sid__709("users");
            SafeIdentifier t_15019 = SrcTest.sid__709("created_at");
            Query q__1665 = SrcGlobal.from(t_15018).orderBy(t_15019, false);
            boolean t_15024 = q__1665.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__15017 = () -> "order desc";
            test_126.assert_(t_15024, fn__15017);
        } finally {
            test_126.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__2452() {
        Test test_127 = new Test();
        try {
            Query t_7677;
            t_7677 = SrcGlobal.from(SrcTest.sid__709("users")).limit(10);
            Query t_7678;
            t_7678 = t_7677.offset(20);
            Query q__1667 = t_7678;
            boolean t_15015 = q__1667.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__15010 = () -> "limit/offset";
            test_127.assert_(t_15015, fn__15010);
        } finally {
            test_127.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__2453() {
        Test test_128 = new Test();
        try {
            boolean didBubble__1669;
            boolean didBubble_17427;
            try {
                SrcGlobal.from(SrcTest.sid__709("users")).limit(-1);
                didBubble_17427 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_17427 = true;
            }
            didBubble__1669 = didBubble_17427;
            Supplier<String> fn__15006 = () -> "negative limit should bubble";
            test_128.assert_(didBubble__1669, fn__15006);
        } finally {
            test_128.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__2454() {
        Test test_129 = new Test();
        try {
            boolean didBubble__1671;
            boolean didBubble_17428;
            try {
                SrcGlobal.from(SrcTest.sid__709("users")).offset(-1);
                didBubble_17428 = false;
            } catch (RuntimeException ignored$13) {
                didBubble_17428 = true;
            }
            didBubble__1671 = didBubble_17428;
            Supplier<String> fn__15002 = () -> "negative offset should bubble";
            test_129.assert_(didBubble__1671, fn__15002);
        } finally {
            test_129.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__2455() {
        Test test_130 = new Test();
        try {
            int minAge__1673 = 21;
            SafeIdentifier t_14980 = SrcTest.sid__709("users");
            SafeIdentifier t_14981 = SrcTest.sid__709("id");
            SafeIdentifier t_14982 = SrcTest.sid__709("name");
            SafeIdentifier t_14983 = SrcTest.sid__709("email");
            Query t_14984 = SrcGlobal.from(t_14980).select(List.of(t_14981, t_14982, t_14983));
            SqlBuilder t_14985 = new SqlBuilder();
            t_14985.appendSafe("age >= ");
            t_14985.appendInt32(21);
            Query t_14989 = t_14984.where(t_14985.getAccumulated());
            SqlBuilder t_14990 = new SqlBuilder();
            t_14990.appendSafe("active = ");
            t_14990.appendBoolean(true);
            Query t_7663;
            t_7663 = t_14989.where(t_14990.getAccumulated()).orderBy(SrcTest.sid__709("name"), true).limit(25);
            Query t_7664;
            t_7664 = t_7663.offset(0);
            Query q__1674 = t_7664;
            boolean t_15000 = q__1674.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__14979 = () -> "complex query";
            test_130.assert_(t_15000, fn__14979);
        } finally {
            test_130.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__2458() {
        Test test_131 = new Test();
        try {
            Query q__1676 = SrcGlobal.from(SrcTest.sid__709("users"));
            SqlFragment t_7640;
            t_7640 = q__1676.safeToSql(100);
            SqlFragment t_7641 = t_7640;
            String s__1677 = t_7641.toString();
            boolean t_14977 = s__1677.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__14973 = () -> "should have limit: " + s__1677;
            test_131.assert_(t_14977, fn__14973);
        } finally {
            test_131.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__2459() {
        Test test_132 = new Test();
        try {
            Query t_7632;
            t_7632 = SrcGlobal.from(SrcTest.sid__709("users")).limit(5);
            Query q__1679 = t_7632;
            SqlFragment t_7635;
            t_7635 = q__1679.safeToSql(100);
            SqlFragment t_7636 = t_7635;
            String s__1680 = t_7636.toString();
            boolean t_14971 = s__1680.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__14967 = () -> "explicit limit preserved: " + s__1680;
            test_132.assert_(t_14971, fn__14967);
        } finally {
            test_132.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__2460() {
        Test test_133 = new Test();
        try {
            boolean didBubble__1682;
            boolean didBubble_17429;
            try {
                SrcGlobal.from(SrcTest.sid__709("users")).safeToSql(-1);
                didBubble_17429 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_17429 = true;
            }
            didBubble__1682 = didBubble_17429;
            Supplier<String> fn__14963 = () -> "negative defaultLimit should bubble";
            test_133.assert_(didBubble__1682, fn__14963);
        } finally {
            test_133.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__2461() {
        Test test_134 = new Test();
        try {
            String evil__1684 = "'; DROP TABLE users; --";
            SafeIdentifier t_14947 = SrcTest.sid__709("users");
            SqlBuilder t_14948 = new SqlBuilder();
            t_14948.appendSafe("name = ");
            t_14948.appendString("'; DROP TABLE users; --");
            SqlFragment t_14951 = t_14948.getAccumulated();
            Query q__1685 = SrcGlobal.from(t_14947).where(t_14951);
            String s__1686 = q__1685.toSql().toString();
            boolean t_14956 = s__1686.indexOf("''") >= 0;
            Supplier<String> fn__14946 = () -> "quotes must be doubled: " + s__1686;
            test_134.assert_(t_14956, fn__14946);
            boolean t_14960 = s__1686.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__14945 = () -> "structure intact: " + s__1686;
            test_134.assert_(t_14960, fn__14945);
        } finally {
            test_134.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__2463() {
        Test test_135 = new Test();
        try {
            String attack__1688 = "users; DROP TABLE users; --";
            boolean didBubble__1689;
            boolean didBubble_17430;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_17430 = false;
            } catch (RuntimeException ignored$15) {
                didBubble_17430 = true;
            }
            didBubble__1689 = didBubble_17430;
            Supplier<String> fn__14942 = () -> "metacharacter-containing name must be rejected at construction";
            test_135.assert_(didBubble__1689, fn__14942);
        } finally {
            test_135.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__2464() {
        Test test_136 = new Test();
        try {
            SafeIdentifier t_14931 = SrcTest.sid__709("users");
            SafeIdentifier t_14932 = SrcTest.sid__709("orders");
            SqlBuilder t_14933 = new SqlBuilder();
            t_14933.appendSafe("users.id = orders.user_id");
            SqlFragment t_14935 = t_14933.getAccumulated();
            Query q__1691 = SrcGlobal.from(t_14931).innerJoin(t_14932, t_14935);
            boolean t_14940 = q__1691.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__14930 = () -> "inner join";
            test_136.assert_(t_14940, fn__14930);
        } finally {
            test_136.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__2466() {
        Test test_137 = new Test();
        try {
            SafeIdentifier t_14919 = SrcTest.sid__709("users");
            SafeIdentifier t_14920 = SrcTest.sid__709("profiles");
            SqlBuilder t_14921 = new SqlBuilder();
            t_14921.appendSafe("users.id = profiles.user_id");
            SqlFragment t_14923 = t_14921.getAccumulated();
            Query q__1693 = SrcGlobal.from(t_14919).leftJoin(t_14920, t_14923);
            boolean t_14928 = q__1693.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__14918 = () -> "left join";
            test_137.assert_(t_14928, fn__14918);
        } finally {
            test_137.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__2468() {
        Test test_138 = new Test();
        try {
            SafeIdentifier t_14907 = SrcTest.sid__709("orders");
            SafeIdentifier t_14908 = SrcTest.sid__709("users");
            SqlBuilder t_14909 = new SqlBuilder();
            t_14909.appendSafe("orders.user_id = users.id");
            SqlFragment t_14911 = t_14909.getAccumulated();
            Query q__1695 = SrcGlobal.from(t_14907).rightJoin(t_14908, t_14911);
            boolean t_14916 = q__1695.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__14906 = () -> "right join";
            test_138.assert_(t_14916, fn__14906);
        } finally {
            test_138.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__2470() {
        Test test_139 = new Test();
        try {
            SafeIdentifier t_14895 = SrcTest.sid__709("users");
            SafeIdentifier t_14896 = SrcTest.sid__709("orders");
            SqlBuilder t_14897 = new SqlBuilder();
            t_14897.appendSafe("users.id = orders.user_id");
            SqlFragment t_14899 = t_14897.getAccumulated();
            Query q__1697 = SrcGlobal.from(t_14895).fullJoin(t_14896, t_14899);
            boolean t_14904 = q__1697.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__14894 = () -> "full join";
            test_139.assert_(t_14904, fn__14894);
        } finally {
            test_139.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__2472() {
        Test test_140 = new Test();
        try {
            SafeIdentifier t_14878 = SrcTest.sid__709("users");
            SafeIdentifier t_14879 = SrcTest.sid__709("orders");
            SqlBuilder t_14880 = new SqlBuilder();
            t_14880.appendSafe("users.id = orders.user_id");
            SqlFragment t_14882 = t_14880.getAccumulated();
            Query t_14883 = SrcGlobal.from(t_14878).innerJoin(t_14879, t_14882);
            SafeIdentifier t_14884 = SrcTest.sid__709("profiles");
            SqlBuilder t_14885 = new SqlBuilder();
            t_14885.appendSafe("users.id = profiles.user_id");
            Query q__1699 = t_14883.leftJoin(t_14884, t_14885.getAccumulated());
            boolean t_14892 = q__1699.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__14877 = () -> "chained joins";
            test_140.assert_(t_14892, fn__14877);
        } finally {
            test_140.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__2475() {
        Test test_141 = new Test();
        try {
            SafeIdentifier t_14859 = SrcTest.sid__709("users");
            SafeIdentifier t_14860 = SrcTest.sid__709("orders");
            SqlBuilder t_14861 = new SqlBuilder();
            t_14861.appendSafe("users.id = orders.user_id");
            SqlFragment t_14863 = t_14861.getAccumulated();
            Query t_14864 = SrcGlobal.from(t_14859).innerJoin(t_14860, t_14863);
            SqlBuilder t_14865 = new SqlBuilder();
            t_14865.appendSafe("orders.total > ");
            t_14865.appendInt32(100);
            Query t_7547;
            t_7547 = t_14864.where(t_14865.getAccumulated()).orderBy(SrcTest.sid__709("name"), true).limit(10);
            Query q__1701 = t_7547;
            boolean t_14875 = q__1701.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__14858 = () -> "join with where/order/limit";
            test_141.assert_(t_14875, fn__14858);
        } finally {
            test_141.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__2478() {
        Test test_142 = new Test();
        try {
            SqlFragment c__1703 = SrcGlobal.col(SrcTest.sid__709("users"), SrcTest.sid__709("id"));
            boolean t_14856 = c__1703.toString().equals("users.id");
            Supplier<String> fn__14850 = () -> "col helper";
            test_142.assert_(t_14856, fn__14850);
        } finally {
            test_142.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__2479() {
        Test test_143 = new Test();
        try {
            SqlFragment onCond__1705 = SrcGlobal.col(SrcTest.sid__709("users"), SrcTest.sid__709("id"));
            SqlBuilder b__1706 = new SqlBuilder();
            b__1706.appendFragment(onCond__1705);
            b__1706.appendSafe(" = ");
            b__1706.appendFragment(SrcGlobal.col(SrcTest.sid__709("orders"), SrcTest.sid__709("user_id")));
            SafeIdentifier t_14841 = SrcTest.sid__709("users");
            SafeIdentifier t_14842 = SrcTest.sid__709("orders");
            SqlFragment t_14843 = b__1706.getAccumulated();
            Query q__1707 = SrcGlobal.from(t_14841).innerJoin(t_14842, t_14843);
            boolean t_14848 = q__1707.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__14830 = () -> "join with col";
            test_143.assert_(t_14848, fn__14830);
        } finally {
            test_143.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orWhereBasic__2480() {
        Test test_144 = new Test();
        try {
            SafeIdentifier t_14819 = SrcTest.sid__709("users");
            SqlBuilder t_14820 = new SqlBuilder();
            t_14820.appendSafe("status = ");
            t_14820.appendString("active");
            SqlFragment t_14823 = t_14820.getAccumulated();
            Query q__1709 = SrcGlobal.from(t_14819).orWhere(t_14823);
            boolean t_14828 = q__1709.toSql().toString().equals("SELECT * FROM users WHERE status = 'active'");
            Supplier<String> fn__14818 = () -> "orWhere basic";
            test_144.assert_(t_14828, fn__14818);
        } finally {
            test_144.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereThenOrWhere__2482() {
        Test test_145 = new Test();
        try {
            SafeIdentifier t_14802 = SrcTest.sid__709("users");
            SqlBuilder t_14803 = new SqlBuilder();
            t_14803.appendSafe("age > ");
            t_14803.appendInt32(18);
            SqlFragment t_14806 = t_14803.getAccumulated();
            Query t_14807 = SrcGlobal.from(t_14802).where(t_14806);
            SqlBuilder t_14808 = new SqlBuilder();
            t_14808.appendSafe("vip = ");
            t_14808.appendBoolean(true);
            Query q__1711 = t_14807.orWhere(t_14808.getAccumulated());
            boolean t_14816 = q__1711.toSql().toString().equals("SELECT * FROM users WHERE age > 18 OR vip = TRUE");
            Supplier<String> fn__14801 = () -> "where then orWhere";
            test_145.assert_(t_14816, fn__14801);
        } finally {
            test_145.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleOrWhere__2485() {
        Test test_146 = new Test();
        try {
            SafeIdentifier t_14780 = SrcTest.sid__709("users");
            SqlBuilder t_14781 = new SqlBuilder();
            t_14781.appendSafe("active = ");
            t_14781.appendBoolean(true);
            SqlFragment t_14784 = t_14781.getAccumulated();
            Query t_14785 = SrcGlobal.from(t_14780).where(t_14784);
            SqlBuilder t_14786 = new SqlBuilder();
            t_14786.appendSafe("role = ");
            t_14786.appendString("admin");
            Query t_14790 = t_14785.orWhere(t_14786.getAccumulated());
            SqlBuilder t_14791 = new SqlBuilder();
            t_14791.appendSafe("role = ");
            t_14791.appendString("moderator");
            Query q__1713 = t_14790.orWhere(t_14791.getAccumulated());
            boolean t_14799 = q__1713.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE OR role = 'admin' OR role = 'moderator'");
            Supplier<String> fn__14779 = () -> "multiple orWhere";
            test_146.assert_(t_14799, fn__14779);
        } finally {
            test_146.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedWhereAndOrWhere__2489() {
        Test test_147 = new Test();
        try {
            SafeIdentifier t_14758 = SrcTest.sid__709("users");
            SqlBuilder t_14759 = new SqlBuilder();
            t_14759.appendSafe("age > ");
            t_14759.appendInt32(18);
            SqlFragment t_14762 = t_14759.getAccumulated();
            Query t_14763 = SrcGlobal.from(t_14758).where(t_14762);
            SqlBuilder t_14764 = new SqlBuilder();
            t_14764.appendSafe("active = ");
            t_14764.appendBoolean(true);
            Query t_14768 = t_14763.where(t_14764.getAccumulated());
            SqlBuilder t_14769 = new SqlBuilder();
            t_14769.appendSafe("vip = ");
            t_14769.appendBoolean(true);
            Query q__1715 = t_14768.orWhere(t_14769.getAccumulated());
            boolean t_14777 = q__1715.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE OR vip = TRUE");
            Supplier<String> fn__14757 = () -> "mixed where and orWhere";
            test_147.assert_(t_14777, fn__14757);
        } finally {
            test_147.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNull__2493() {
        Test test_148 = new Test();
        try {
            SafeIdentifier t_14749 = SrcTest.sid__709("users");
            SafeIdentifier t_14750 = SrcTest.sid__709("deleted_at");
            Query q__1717 = SrcGlobal.from(t_14749).whereNull(t_14750);
            boolean t_14755 = q__1717.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL");
            Supplier<String> fn__14748 = () -> "whereNull";
            test_148.assert_(t_14755, fn__14748);
        } finally {
            test_148.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNull__2494() {
        Test test_149 = new Test();
        try {
            SafeIdentifier t_14740 = SrcTest.sid__709("users");
            SafeIdentifier t_14741 = SrcTest.sid__709("email");
            Query q__1719 = SrcGlobal.from(t_14740).whereNotNull(t_14741);
            boolean t_14746 = q__1719.toSql().toString().equals("SELECT * FROM users WHERE email IS NOT NULL");
            Supplier<String> fn__14739 = () -> "whereNotNull";
            test_149.assert_(t_14746, fn__14739);
        } finally {
            test_149.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNullChainedWithWhere__2495() {
        Test test_150 = new Test();
        try {
            SafeIdentifier t_14726 = SrcTest.sid__709("users");
            SqlBuilder t_14727 = new SqlBuilder();
            t_14727.appendSafe("active = ");
            t_14727.appendBoolean(true);
            SqlFragment t_14730 = t_14727.getAccumulated();
            Query q__1721 = SrcGlobal.from(t_14726).where(t_14730).whereNull(SrcTest.sid__709("deleted_at"));
            boolean t_14737 = q__1721.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND deleted_at IS NULL");
            Supplier<String> fn__14725 = () -> "whereNull chained";
            test_150.assert_(t_14737, fn__14725);
        } finally {
            test_150.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNullChainedWithOrWhere__2497() {
        Test test_151 = new Test();
        try {
            SafeIdentifier t_14712 = SrcTest.sid__709("users");
            SafeIdentifier t_14713 = SrcTest.sid__709("deleted_at");
            Query t_14714 = SrcGlobal.from(t_14712).whereNull(t_14713);
            SqlBuilder t_14715 = new SqlBuilder();
            t_14715.appendSafe("role = ");
            t_14715.appendString("admin");
            Query q__1723 = t_14714.orWhere(t_14715.getAccumulated());
            boolean t_14723 = q__1723.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL OR role = 'admin'");
            Supplier<String> fn__14711 = () -> "whereNotNull with orWhere";
            test_151.assert_(t_14723, fn__14711);
        } finally {
            test_151.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithIntValues__2499() {
        Test test_152 = new Test();
        try {
            SafeIdentifier t_14700 = SrcTest.sid__709("users");
            SafeIdentifier t_14701 = SrcTest.sid__709("id");
            SqlInt32 t_14702 = new SqlInt32(1);
            SqlInt32 t_14703 = new SqlInt32(2);
            SqlInt32 t_14704 = new SqlInt32(3);
            Query q__1725 = SrcGlobal.from(t_14700).whereIn(t_14701, List.of(t_14702, t_14703, t_14704));
            boolean t_14709 = q__1725.toSql().toString().equals("SELECT * FROM users WHERE id IN (1, 2, 3)");
            Supplier<String> fn__14699 = () -> "whereIn ints";
            test_152.assert_(t_14709, fn__14699);
        } finally {
            test_152.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithStringValuesEscaping__2500() {
        Test test_153 = new Test();
        try {
            SafeIdentifier t_14689 = SrcTest.sid__709("users");
            SafeIdentifier t_14690 = SrcTest.sid__709("name");
            SqlString t_14691 = new SqlString("Alice");
            SqlString t_14692 = new SqlString("Bob's");
            Query q__1727 = SrcGlobal.from(t_14689).whereIn(t_14690, List.of(t_14691, t_14692));
            boolean t_14697 = q__1727.toSql().toString().equals("SELECT * FROM users WHERE name IN ('Alice', 'Bob''s')");
            Supplier<String> fn__14688 = () -> "whereIn strings";
            test_153.assert_(t_14697, fn__14688);
        } finally {
            test_153.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithEmptyListProduces1_0__2501() {
        Test test_154 = new Test();
        try {
            SafeIdentifier t_14680 = SrcTest.sid__709("users");
            SafeIdentifier t_14681 = SrcTest.sid__709("id");
            Query q__1729 = SrcGlobal.from(t_14680).whereIn(t_14681, List.of());
            boolean t_14686 = q__1729.toSql().toString().equals("SELECT * FROM users WHERE 1 = 0");
            Supplier<String> fn__14679 = () -> "whereIn empty";
            test_154.assert_(t_14686, fn__14679);
        } finally {
            test_154.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInChained__2502() {
        Test test_155 = new Test();
        try {
            SafeIdentifier t_14664 = SrcTest.sid__709("users");
            SqlBuilder t_14665 = new SqlBuilder();
            t_14665.appendSafe("active = ");
            t_14665.appendBoolean(true);
            SqlFragment t_14668 = t_14665.getAccumulated();
            Query q__1731 = SrcGlobal.from(t_14664).where(t_14668).whereIn(SrcTest.sid__709("role"), List.of(new SqlString("admin"), new SqlString("user")));
            boolean t_14677 = q__1731.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND role IN ('admin', 'user')");
            Supplier<String> fn__14663 = () -> "whereIn chained";
            test_155.assert_(t_14677, fn__14663);
        } finally {
            test_155.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSingleElement__2504() {
        Test test_156 = new Test();
        try {
            SafeIdentifier t_14654 = SrcTest.sid__709("users");
            SafeIdentifier t_14655 = SrcTest.sid__709("id");
            SqlInt32 t_14656 = new SqlInt32(42);
            Query q__1733 = SrcGlobal.from(t_14654).whereIn(t_14655, List.of(t_14656));
            boolean t_14661 = q__1733.toSql().toString().equals("SELECT * FROM users WHERE id IN (42)");
            Supplier<String> fn__14653 = () -> "whereIn single";
            test_156.assert_(t_14661, fn__14653);
        } finally {
            test_156.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotBasic__2505() {
        Test test_157 = new Test();
        try {
            SafeIdentifier t_14642 = SrcTest.sid__709("users");
            SqlBuilder t_14643 = new SqlBuilder();
            t_14643.appendSafe("active = ");
            t_14643.appendBoolean(true);
            SqlFragment t_14646 = t_14643.getAccumulated();
            Query q__1735 = SrcGlobal.from(t_14642).whereNot(t_14646);
            boolean t_14651 = q__1735.toSql().toString().equals("SELECT * FROM users WHERE NOT (active = TRUE)");
            Supplier<String> fn__14641 = () -> "whereNot";
            test_157.assert_(t_14651, fn__14641);
        } finally {
            test_157.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotChained__2507() {
        Test test_158 = new Test();
        try {
            SafeIdentifier t_14625 = SrcTest.sid__709("users");
            SqlBuilder t_14626 = new SqlBuilder();
            t_14626.appendSafe("age > ");
            t_14626.appendInt32(18);
            SqlFragment t_14629 = t_14626.getAccumulated();
            Query t_14630 = SrcGlobal.from(t_14625).where(t_14629);
            SqlBuilder t_14631 = new SqlBuilder();
            t_14631.appendSafe("banned = ");
            t_14631.appendBoolean(true);
            Query q__1737 = t_14630.whereNot(t_14631.getAccumulated());
            boolean t_14639 = q__1737.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND NOT (banned = TRUE)");
            Supplier<String> fn__14624 = () -> "whereNot chained";
            test_158.assert_(t_14639, fn__14624);
        } finally {
            test_158.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenIntegers__2510() {
        Test test_159 = new Test();
        try {
            SafeIdentifier t_14614 = SrcTest.sid__709("users");
            SafeIdentifier t_14615 = SrcTest.sid__709("age");
            SqlInt32 t_14616 = new SqlInt32(18);
            SqlInt32 t_14617 = new SqlInt32(65);
            Query q__1739 = SrcGlobal.from(t_14614).whereBetween(t_14615, t_14616, t_14617);
            boolean t_14622 = q__1739.toSql().toString().equals("SELECT * FROM users WHERE age BETWEEN 18 AND 65");
            Supplier<String> fn__14613 = () -> "whereBetween ints";
            test_159.assert_(t_14622, fn__14613);
        } finally {
            test_159.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenChained__2511() {
        Test test_160 = new Test();
        try {
            SafeIdentifier t_14598 = SrcTest.sid__709("users");
            SqlBuilder t_14599 = new SqlBuilder();
            t_14599.appendSafe("active = ");
            t_14599.appendBoolean(true);
            SqlFragment t_14602 = t_14599.getAccumulated();
            Query q__1741 = SrcGlobal.from(t_14598).where(t_14602).whereBetween(SrcTest.sid__709("age"), new SqlInt32(21), new SqlInt32(30));
            boolean t_14611 = q__1741.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND age BETWEEN 21 AND 30");
            Supplier<String> fn__14597 = () -> "whereBetween chained";
            test_160.assert_(t_14611, fn__14597);
        } finally {
            test_160.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeBasic__2513() {
        Test test_161 = new Test();
        try {
            SafeIdentifier t_14589 = SrcTest.sid__709("users");
            SafeIdentifier t_14590 = SrcTest.sid__709("name");
            Query q__1743 = SrcGlobal.from(t_14589).whereLike(t_14590, "John%");
            boolean t_14595 = q__1743.toSql().toString().equals("SELECT * FROM users WHERE name LIKE 'John%'");
            Supplier<String> fn__14588 = () -> "whereLike";
            test_161.assert_(t_14595, fn__14588);
        } finally {
            test_161.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereIlikeBasic__2514() {
        Test test_162 = new Test();
        try {
            SafeIdentifier t_14580 = SrcTest.sid__709("users");
            SafeIdentifier t_14581 = SrcTest.sid__709("email");
            Query q__1745 = SrcGlobal.from(t_14580).whereILike(t_14581, "%@gmail.com");
            boolean t_14586 = q__1745.toSql().toString().equals("SELECT * FROM users WHERE email ILIKE '%@gmail.com'");
            Supplier<String> fn__14579 = () -> "whereILike";
            test_162.assert_(t_14586, fn__14579);
        } finally {
            test_162.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWithInjectionAttempt__2515() {
        Test test_163 = new Test();
        try {
            SafeIdentifier t_14566 = SrcTest.sid__709("users");
            SafeIdentifier t_14567 = SrcTest.sid__709("name");
            Query q__1747 = SrcGlobal.from(t_14566).whereLike(t_14567, "'; DROP TABLE users; --");
            String s__1748 = q__1747.toSql().toString();
            boolean t_14572 = s__1748.indexOf("''") >= 0;
            Supplier<String> fn__14565 = () -> "like injection escaped: " + s__1748;
            test_163.assert_(t_14572, fn__14565);
            boolean t_14576 = s__1748.indexOf("LIKE") >= 0;
            Supplier<String> fn__14564 = () -> "like structure intact: " + s__1748;
            test_163.assert_(t_14576, fn__14564);
        } finally {
            test_163.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWildcardPatterns__2516() {
        Test test_164 = new Test();
        try {
            SafeIdentifier t_14556 = SrcTest.sid__709("users");
            SafeIdentifier t_14557 = SrcTest.sid__709("name");
            Query q__1750 = SrcGlobal.from(t_14556).whereLike(t_14557, "%son%");
            boolean t_14562 = q__1750.toSql().toString().equals("SELECT * FROM users WHERE name LIKE '%son%'");
            Supplier<String> fn__14555 = () -> "whereLike wildcard";
            test_164.assert_(t_14562, fn__14555);
        } finally {
            test_164.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countAllProducesCount__2517() {
        Test test_165 = new Test();
        try {
            SqlFragment f__1752 = SrcGlobal.countAll();
            boolean t_14553 = f__1752.toString().equals("COUNT(*)");
            Supplier<String> fn__14549 = () -> "countAll";
            test_165.assert_(t_14553, fn__14549);
        } finally {
            test_165.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countColProducesCountField__2518() {
        Test test_166 = new Test();
        try {
            SqlFragment f__1754 = SrcGlobal.countCol(SrcTest.sid__709("id"));
            boolean t_14547 = f__1754.toString().equals("COUNT(id)");
            Supplier<String> fn__14542 = () -> "countCol";
            test_166.assert_(t_14547, fn__14542);
        } finally {
            test_166.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sumColProducesSumField__2519() {
        Test test_167 = new Test();
        try {
            SqlFragment f__1756 = SrcGlobal.sumCol(SrcTest.sid__709("amount"));
            boolean t_14540 = f__1756.toString().equals("SUM(amount)");
            Supplier<String> fn__14535 = () -> "sumCol";
            test_167.assert_(t_14540, fn__14535);
        } finally {
            test_167.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void avgColProducesAvgField__2520() {
        Test test_168 = new Test();
        try {
            SqlFragment f__1758 = SrcGlobal.avgCol(SrcTest.sid__709("price"));
            boolean t_14533 = f__1758.toString().equals("AVG(price)");
            Supplier<String> fn__14528 = () -> "avgCol";
            test_168.assert_(t_14533, fn__14528);
        } finally {
            test_168.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void minColProducesMinField__2521() {
        Test test_169 = new Test();
        try {
            SqlFragment f__1760 = SrcGlobal.minCol(SrcTest.sid__709("created_at"));
            boolean t_14526 = f__1760.toString().equals("MIN(created_at)");
            Supplier<String> fn__14521 = () -> "minCol";
            test_169.assert_(t_14526, fn__14521);
        } finally {
            test_169.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void maxColProducesMaxField__2522() {
        Test test_170 = new Test();
        try {
            SqlFragment f__1762 = SrcGlobal.maxCol(SrcTest.sid__709("score"));
            boolean t_14519 = f__1762.toString().equals("MAX(score)");
            Supplier<String> fn__14514 = () -> "maxCol";
            test_170.assert_(t_14519, fn__14514);
        } finally {
            test_170.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithAggregate__2523() {
        Test test_171 = new Test();
        try {
            SafeIdentifier t_14506 = SrcTest.sid__709("orders");
            SqlFragment t_14507 = SrcGlobal.countAll();
            Query q__1764 = SrcGlobal.from(t_14506).selectExpr(List.of(t_14507));
            boolean t_14512 = q__1764.toSql().toString().equals("SELECT COUNT(*) FROM orders");
            Supplier<String> fn__14505 = () -> "selectExpr count";
            test_171.assert_(t_14512, fn__14505);
        } finally {
            test_171.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithMultipleExpressions__2524() {
        Test test_172 = new Test();
        try {
            SqlFragment nameFrag__1766 = SrcGlobal.col(SrcTest.sid__709("users"), SrcTest.sid__709("name"));
            SafeIdentifier t_14497 = SrcTest.sid__709("users");
            SqlFragment t_14498 = SrcGlobal.countAll();
            Query q__1767 = SrcGlobal.from(t_14497).selectExpr(List.of(nameFrag__1766, t_14498));
            boolean t_14503 = q__1767.toSql().toString().equals("SELECT users.name, COUNT(*) FROM users");
            Supplier<String> fn__14493 = () -> "selectExpr multi";
            test_172.assert_(t_14503, fn__14493);
        } finally {
            test_172.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprOverridesSelectedFields__2525() {
        Test test_173 = new Test();
        try {
            SafeIdentifier t_14482 = SrcTest.sid__709("users");
            SafeIdentifier t_14483 = SrcTest.sid__709("id");
            SafeIdentifier t_14484 = SrcTest.sid__709("name");
            Query q__1769 = SrcGlobal.from(t_14482).select(List.of(t_14483, t_14484)).selectExpr(List.of(SrcGlobal.countAll()));
            boolean t_14491 = q__1769.toSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__14481 = () -> "selectExpr overrides select";
            test_173.assert_(t_14491, fn__14481);
        } finally {
            test_173.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupBySingleField__2526() {
        Test test_174 = new Test();
        try {
            SafeIdentifier t_14468 = SrcTest.sid__709("orders");
            SqlFragment t_14471 = SrcGlobal.col(SrcTest.sid__709("orders"), SrcTest.sid__709("status"));
            SqlFragment t_14472 = SrcGlobal.countAll();
            Query q__1771 = SrcGlobal.from(t_14468).selectExpr(List.of(t_14471, t_14472)).groupBy(SrcTest.sid__709("status"));
            boolean t_14479 = q__1771.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status");
            Supplier<String> fn__14467 = () -> "groupBy single";
            test_174.assert_(t_14479, fn__14467);
        } finally {
            test_174.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupByMultipleFields__2527() {
        Test test_175 = new Test();
        try {
            SafeIdentifier t_14457 = SrcTest.sid__709("orders");
            SafeIdentifier t_14458 = SrcTest.sid__709("status");
            Query q__1773 = SrcGlobal.from(t_14457).groupBy(t_14458).groupBy(SrcTest.sid__709("category"));
            boolean t_14465 = q__1773.toSql().toString().equals("SELECT * FROM orders GROUP BY status, category");
            Supplier<String> fn__14456 = () -> "groupBy multiple";
            test_175.assert_(t_14465, fn__14456);
        } finally {
            test_175.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void havingBasic__2528() {
        Test test_176 = new Test();
        try {
            SafeIdentifier t_14438 = SrcTest.sid__709("orders");
            SqlFragment t_14441 = SrcGlobal.col(SrcTest.sid__709("orders"), SrcTest.sid__709("status"));
            SqlFragment t_14442 = SrcGlobal.countAll();
            Query t_14445 = SrcGlobal.from(t_14438).selectExpr(List.of(t_14441, t_14442)).groupBy(SrcTest.sid__709("status"));
            SqlBuilder t_14446 = new SqlBuilder();
            t_14446.appendSafe("COUNT(*) > ");
            t_14446.appendInt32(5);
            Query q__1775 = t_14445.having(t_14446.getAccumulated());
            boolean t_14454 = q__1775.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status HAVING COUNT(*) > 5");
            Supplier<String> fn__14437 = () -> "having basic";
            test_176.assert_(t_14454, fn__14437);
        } finally {
            test_176.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orHaving__2530() {
        Test test_177 = new Test();
        try {
            SafeIdentifier t_14419 = SrcTest.sid__709("orders");
            SafeIdentifier t_14420 = SrcTest.sid__709("status");
            Query t_14421 = SrcGlobal.from(t_14419).groupBy(t_14420);
            SqlBuilder t_14422 = new SqlBuilder();
            t_14422.appendSafe("COUNT(*) > ");
            t_14422.appendInt32(5);
            Query t_14426 = t_14421.having(t_14422.getAccumulated());
            SqlBuilder t_14427 = new SqlBuilder();
            t_14427.appendSafe("SUM(total) > ");
            t_14427.appendInt32(1000);
            Query q__1777 = t_14426.orHaving(t_14427.getAccumulated());
            boolean t_14435 = q__1777.toSql().toString().equals("SELECT * FROM orders GROUP BY status HAVING COUNT(*) > 5 OR SUM(total) > 1000");
            Supplier<String> fn__14418 = () -> "orHaving";
            test_177.assert_(t_14435, fn__14418);
        } finally {
            test_177.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctBasic__2533() {
        Test test_178 = new Test();
        try {
            SafeIdentifier t_14409 = SrcTest.sid__709("users");
            SafeIdentifier t_14410 = SrcTest.sid__709("name");
            Query q__1779 = SrcGlobal.from(t_14409).select(List.of(t_14410)).distinct();
            boolean t_14416 = q__1779.toSql().toString().equals("SELECT DISTINCT name FROM users");
            Supplier<String> fn__14408 = () -> "distinct";
            test_178.assert_(t_14416, fn__14408);
        } finally {
            test_178.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctWithWhere__2534() {
        Test test_179 = new Test();
        try {
            SafeIdentifier t_14394 = SrcTest.sid__709("users");
            SafeIdentifier t_14395 = SrcTest.sid__709("email");
            Query t_14396 = SrcGlobal.from(t_14394).select(List.of(t_14395));
            SqlBuilder t_14397 = new SqlBuilder();
            t_14397.appendSafe("active = ");
            t_14397.appendBoolean(true);
            Query q__1781 = t_14396.where(t_14397.getAccumulated()).distinct();
            boolean t_14406 = q__1781.toSql().toString().equals("SELECT DISTINCT email FROM users WHERE active = TRUE");
            Supplier<String> fn__14393 = () -> "distinct with where";
            test_179.assert_(t_14406, fn__14393);
        } finally {
            test_179.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlBare__2536() {
        Test test_180 = new Test();
        try {
            Query q__1783 = SrcGlobal.from(SrcTest.sid__709("users"));
            boolean t_14391 = q__1783.countSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__14386 = () -> "countSql bare";
            test_180.assert_(t_14391, fn__14386);
        } finally {
            test_180.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithWhere__2537() {
        Test test_181 = new Test();
        try {
            SafeIdentifier t_14375 = SrcTest.sid__709("users");
            SqlBuilder t_14376 = new SqlBuilder();
            t_14376.appendSafe("active = ");
            t_14376.appendBoolean(true);
            SqlFragment t_14379 = t_14376.getAccumulated();
            Query q__1785 = SrcGlobal.from(t_14375).where(t_14379);
            boolean t_14384 = q__1785.countSql().toString().equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__14374 = () -> "countSql with where";
            test_181.assert_(t_14384, fn__14374);
        } finally {
            test_181.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithJoin__2539() {
        Test test_182 = new Test();
        try {
            SafeIdentifier t_14358 = SrcTest.sid__709("users");
            SafeIdentifier t_14359 = SrcTest.sid__709("orders");
            SqlBuilder t_14360 = new SqlBuilder();
            t_14360.appendSafe("users.id = orders.user_id");
            SqlFragment t_14362 = t_14360.getAccumulated();
            Query t_14363 = SrcGlobal.from(t_14358).innerJoin(t_14359, t_14362);
            SqlBuilder t_14364 = new SqlBuilder();
            t_14364.appendSafe("orders.total > ");
            t_14364.appendInt32(100);
            Query q__1787 = t_14363.where(t_14364.getAccumulated());
            boolean t_14372 = q__1787.countSql().toString().equals("SELECT COUNT(*) FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100");
            Supplier<String> fn__14357 = () -> "countSql with join";
            test_182.assert_(t_14372, fn__14357);
        } finally {
            test_182.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlDropsOrderByLimitOffset__2542() {
        Test test_183 = new Test();
        try {
            SafeIdentifier t_14344 = SrcTest.sid__709("users");
            SqlBuilder t_14345 = new SqlBuilder();
            t_14345.appendSafe("active = ");
            t_14345.appendBoolean(true);
            SqlFragment t_14348 = t_14345.getAccumulated();
            Query t_7123;
            t_7123 = SrcGlobal.from(t_14344).where(t_14348).orderBy(SrcTest.sid__709("name"), true).limit(10);
            Query t_7124;
            t_7124 = t_7123.offset(20);
            Query q__1789 = t_7124;
            String s__1790 = q__1789.countSql().toString();
            boolean t_14355 = s__1790.equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__14343 = () -> "countSql drops extras: " + s__1790;
            test_183.assert_(t_14355, fn__14343);
        } finally {
            test_183.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullAggregationQuery__2544() {
        Test test_184 = new Test();
        try {
            SafeIdentifier t_14311 = SrcTest.sid__709("orders");
            SqlFragment t_14314 = SrcGlobal.col(SrcTest.sid__709("orders"), SrcTest.sid__709("status"));
            SqlFragment t_14315 = SrcGlobal.countAll();
            SqlFragment t_14317 = SrcGlobal.sumCol(SrcTest.sid__709("total"));
            Query t_14318 = SrcGlobal.from(t_14311).selectExpr(List.of(t_14314, t_14315, t_14317));
            SafeIdentifier t_14319 = SrcTest.sid__709("users");
            SqlBuilder t_14320 = new SqlBuilder();
            t_14320.appendSafe("orders.user_id = users.id");
            Query t_14323 = t_14318.innerJoin(t_14319, t_14320.getAccumulated());
            SqlBuilder t_14324 = new SqlBuilder();
            t_14324.appendSafe("users.active = ");
            t_14324.appendBoolean(true);
            Query t_14330 = t_14323.where(t_14324.getAccumulated()).groupBy(SrcTest.sid__709("status"));
            SqlBuilder t_14331 = new SqlBuilder();
            t_14331.appendSafe("COUNT(*) > ");
            t_14331.appendInt32(3);
            Query q__1792 = t_14330.having(t_14331.getAccumulated()).orderBy(SrcTest.sid__709("status"), true);
            String expected__1793 = "SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC";
            boolean t_14341 = q__1792.toSql().toString().equals("SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC");
            Supplier<String> fn__14310 = () -> "full aggregation";
            test_184.assert_(t_14341, fn__14310);
        } finally {
            test_184.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionSql__2548() {
        Test test_185 = new Test();
        try {
            SafeIdentifier t_14293 = SrcTest.sid__709("users");
            SqlBuilder t_14294 = new SqlBuilder();
            t_14294.appendSafe("role = ");
            t_14294.appendString("admin");
            SqlFragment t_14297 = t_14294.getAccumulated();
            Query a__1795 = SrcGlobal.from(t_14293).where(t_14297);
            SafeIdentifier t_14299 = SrcTest.sid__709("users");
            SqlBuilder t_14300 = new SqlBuilder();
            t_14300.appendSafe("role = ");
            t_14300.appendString("moderator");
            SqlFragment t_14303 = t_14300.getAccumulated();
            Query b__1796 = SrcGlobal.from(t_14299).where(t_14303);
            String s__1797 = SrcGlobal.unionSql(a__1795, b__1796).toString();
            boolean t_14308 = s__1797.equals("(SELECT * FROM users WHERE role = 'admin') UNION (SELECT * FROM users WHERE role = 'moderator')");
            Supplier<String> fn__14292 = () -> "unionSql: " + s__1797;
            test_185.assert_(t_14308, fn__14292);
        } finally {
            test_185.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionAllSql__2551() {
        Test test_186 = new Test();
        try {
            SafeIdentifier t_14281 = SrcTest.sid__709("users");
            SafeIdentifier t_14282 = SrcTest.sid__709("name");
            Query a__1799 = SrcGlobal.from(t_14281).select(List.of(t_14282));
            SafeIdentifier t_14284 = SrcTest.sid__709("contacts");
            SafeIdentifier t_14285 = SrcTest.sid__709("name");
            Query b__1800 = SrcGlobal.from(t_14284).select(List.of(t_14285));
            String s__1801 = SrcGlobal.unionAllSql(a__1799, b__1800).toString();
            boolean t_14290 = s__1801.equals("(SELECT name FROM users) UNION ALL (SELECT name FROM contacts)");
            Supplier<String> fn__14280 = () -> "unionAllSql: " + s__1801;
            test_186.assert_(t_14290, fn__14280);
        } finally {
            test_186.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void intersectSql__2552() {
        Test test_187 = new Test();
        try {
            SafeIdentifier t_14269 = SrcTest.sid__709("users");
            SafeIdentifier t_14270 = SrcTest.sid__709("email");
            Query a__1803 = SrcGlobal.from(t_14269).select(List.of(t_14270));
            SafeIdentifier t_14272 = SrcTest.sid__709("subscribers");
            SafeIdentifier t_14273 = SrcTest.sid__709("email");
            Query b__1804 = SrcGlobal.from(t_14272).select(List.of(t_14273));
            String s__1805 = SrcGlobal.intersectSql(a__1803, b__1804).toString();
            boolean t_14278 = s__1805.equals("(SELECT email FROM users) INTERSECT (SELECT email FROM subscribers)");
            Supplier<String> fn__14268 = () -> "intersectSql: " + s__1805;
            test_187.assert_(t_14278, fn__14268);
        } finally {
            test_187.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void exceptSql__2553() {
        Test test_188 = new Test();
        try {
            SafeIdentifier t_14257 = SrcTest.sid__709("users");
            SafeIdentifier t_14258 = SrcTest.sid__709("id");
            Query a__1807 = SrcGlobal.from(t_14257).select(List.of(t_14258));
            SafeIdentifier t_14260 = SrcTest.sid__709("banned");
            SafeIdentifier t_14261 = SrcTest.sid__709("id");
            Query b__1808 = SrcGlobal.from(t_14260).select(List.of(t_14261));
            String s__1809 = SrcGlobal.exceptSql(a__1807, b__1808).toString();
            boolean t_14266 = s__1809.equals("(SELECT id FROM users) EXCEPT (SELECT id FROM banned)");
            Supplier<String> fn__14256 = () -> "exceptSql: " + s__1809;
            test_188.assert_(t_14266, fn__14256);
        } finally {
            test_188.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void subqueryWithAlias__2554() {
        Test test_189 = new Test();
        try {
            SafeIdentifier t_14242 = SrcTest.sid__709("orders");
            SafeIdentifier t_14243 = SrcTest.sid__709("user_id");
            Query t_14244 = SrcGlobal.from(t_14242).select(List.of(t_14243));
            SqlBuilder t_14245 = new SqlBuilder();
            t_14245.appendSafe("total > ");
            t_14245.appendInt32(100);
            Query inner__1811 = t_14244.where(t_14245.getAccumulated());
            String s__1812 = SrcGlobal.subquery(inner__1811, SrcTest.sid__709("big_orders")).toString();
            boolean t_14254 = s__1812.equals("(SELECT user_id FROM orders WHERE total > 100) AS big_orders");
            Supplier<String> fn__14241 = () -> "subquery: " + s__1812;
            test_189.assert_(t_14254, fn__14241);
        } finally {
            test_189.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSql__2556() {
        Test test_190 = new Test();
        try {
            SafeIdentifier t_14231 = SrcTest.sid__709("orders");
            SqlBuilder t_14232 = new SqlBuilder();
            t_14232.appendSafe("orders.user_id = users.id");
            SqlFragment t_14234 = t_14232.getAccumulated();
            Query inner__1814 = SrcGlobal.from(t_14231).where(t_14234);
            String s__1815 = SrcGlobal.existsSql(inner__1814).toString();
            boolean t_14239 = s__1815.equals("EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__14230 = () -> "existsSql: " + s__1815;
            test_190.assert_(t_14239, fn__14230);
        } finally {
            test_190.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubquery__2558() {
        Test test_191 = new Test();
        try {
            SafeIdentifier t_14214 = SrcTest.sid__709("orders");
            SafeIdentifier t_14215 = SrcTest.sid__709("user_id");
            Query t_14216 = SrcGlobal.from(t_14214).select(List.of(t_14215));
            SqlBuilder t_14217 = new SqlBuilder();
            t_14217.appendSafe("total > ");
            t_14217.appendInt32(1000);
            Query sub__1817 = t_14216.where(t_14217.getAccumulated());
            SafeIdentifier t_14222 = SrcTest.sid__709("users");
            SafeIdentifier t_14223 = SrcTest.sid__709("id");
            Query q__1818 = SrcGlobal.from(t_14222).whereInSubquery(t_14223, sub__1817);
            String s__1819 = q__1818.toSql().toString();
            boolean t_14228 = s__1819.equals("SELECT * FROM users WHERE id IN (SELECT user_id FROM orders WHERE total > 1000)");
            Supplier<String> fn__14213 = () -> "whereInSubquery: " + s__1819;
            test_191.assert_(t_14228, fn__14213);
        } finally {
            test_191.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void setOperationWithWhereOnEachSide__2560() {
        Test test_192 = new Test();
        try {
            SafeIdentifier t_14191 = SrcTest.sid__709("users");
            SqlBuilder t_14192 = new SqlBuilder();
            t_14192.appendSafe("age > ");
            t_14192.appendInt32(18);
            SqlFragment t_14195 = t_14192.getAccumulated();
            Query t_14196 = SrcGlobal.from(t_14191).where(t_14195);
            SqlBuilder t_14197 = new SqlBuilder();
            t_14197.appendSafe("active = ");
            t_14197.appendBoolean(true);
            Query a__1821 = t_14196.where(t_14197.getAccumulated());
            SafeIdentifier t_14202 = SrcTest.sid__709("users");
            SqlBuilder t_14203 = new SqlBuilder();
            t_14203.appendSafe("role = ");
            t_14203.appendString("vip");
            SqlFragment t_14206 = t_14203.getAccumulated();
            Query b__1822 = SrcGlobal.from(t_14202).where(t_14206);
            String s__1823 = SrcGlobal.unionSql(a__1821, b__1822).toString();
            boolean t_14211 = s__1823.equals("(SELECT * FROM users WHERE age > 18 AND active = TRUE) UNION (SELECT * FROM users WHERE role = 'vip')");
            Supplier<String> fn__14190 = () -> "union with where: " + s__1823;
            test_192.assert_(t_14211, fn__14190);
        } finally {
            test_192.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubqueryChainedWithWhere__2564() {
        Test test_193 = new Test();
        try {
            SafeIdentifier t_14174 = SrcTest.sid__709("orders");
            SafeIdentifier t_14175 = SrcTest.sid__709("user_id");
            Query sub__1825 = SrcGlobal.from(t_14174).select(List.of(t_14175));
            SafeIdentifier t_14177 = SrcTest.sid__709("users");
            SqlBuilder t_14178 = new SqlBuilder();
            t_14178.appendSafe("active = ");
            t_14178.appendBoolean(true);
            SqlFragment t_14181 = t_14178.getAccumulated();
            Query q__1826 = SrcGlobal.from(t_14177).where(t_14181).whereInSubquery(SrcTest.sid__709("id"), sub__1825);
            String s__1827 = q__1826.toSql().toString();
            boolean t_14188 = s__1827.equals("SELECT * FROM users WHERE active = TRUE AND id IN (SELECT user_id FROM orders)");
            Supplier<String> fn__14173 = () -> "whereInSubquery chained: " + s__1827;
            test_193.assert_(t_14188, fn__14173);
        } finally {
            test_193.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSqlUsedInWhere__2566() {
        Test test_194 = new Test();
        try {
            SafeIdentifier t_14160 = SrcTest.sid__709("orders");
            SqlBuilder t_14161 = new SqlBuilder();
            t_14161.appendSafe("orders.user_id = users.id");
            SqlFragment t_14163 = t_14161.getAccumulated();
            Query sub__1829 = SrcGlobal.from(t_14160).where(t_14163);
            SafeIdentifier t_14165 = SrcTest.sid__709("users");
            SqlFragment t_14166 = SrcGlobal.existsSql(sub__1829);
            Query q__1830 = SrcGlobal.from(t_14165).where(t_14166);
            String s__1831 = q__1830.toSql().toString();
            boolean t_14171 = s__1831.equals("SELECT * FROM users WHERE EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__14159 = () -> "exists in where: " + s__1831;
            test_194.assert_(t_14171, fn__14159);
        } finally {
            test_194.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBasic__2568() {
        Test test_195 = new Test();
        try {
            SafeIdentifier t_14146 = SrcTest.sid__709("users");
            SafeIdentifier t_14147 = SrcTest.sid__709("name");
            SqlString t_14148 = new SqlString("Alice");
            UpdateQuery t_14149 = SrcGlobal.update(t_14146).set(t_14147, t_14148);
            SqlBuilder t_14150 = new SqlBuilder();
            t_14150.appendSafe("id = ");
            t_14150.appendInt32(1);
            SqlFragment t_6945;
            t_6945 = t_14149.where(t_14150.getAccumulated()).toSql();
            SqlFragment q__1833 = t_6945;
            boolean t_14157 = q__1833.toString().equals("UPDATE users SET name = 'Alice' WHERE id = 1");
            Supplier<String> fn__14145 = () -> "update basic";
            test_195.assert_(t_14157, fn__14145);
        } finally {
            test_195.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleSet__2570() {
        Test test_196 = new Test();
        try {
            SafeIdentifier t_14129 = SrcTest.sid__709("users");
            SafeIdentifier t_14130 = SrcTest.sid__709("name");
            SqlString t_14131 = new SqlString("Bob");
            UpdateQuery t_14135 = SrcGlobal.update(t_14129).set(t_14130, t_14131).set(SrcTest.sid__709("age"), new SqlInt32(30));
            SqlBuilder t_14136 = new SqlBuilder();
            t_14136.appendSafe("id = ");
            t_14136.appendInt32(2);
            SqlFragment t_6930;
            t_6930 = t_14135.where(t_14136.getAccumulated()).toSql();
            SqlFragment q__1835 = t_6930;
            boolean t_14143 = q__1835.toString().equals("UPDATE users SET name = 'Bob', age = 30 WHERE id = 2");
            Supplier<String> fn__14128 = () -> "update multi set";
            test_196.assert_(t_14143, fn__14128);
        } finally {
            test_196.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleWhere__2572() {
        Test test_197 = new Test();
        try {
            SafeIdentifier t_14110 = SrcTest.sid__709("users");
            SafeIdentifier t_14111 = SrcTest.sid__709("active");
            SqlBoolean t_14112 = new SqlBoolean(false);
            UpdateQuery t_14113 = SrcGlobal.update(t_14110).set(t_14111, t_14112);
            SqlBuilder t_14114 = new SqlBuilder();
            t_14114.appendSafe("age < ");
            t_14114.appendInt32(18);
            UpdateQuery t_14118 = t_14113.where(t_14114.getAccumulated());
            SqlBuilder t_14119 = new SqlBuilder();
            t_14119.appendSafe("role = ");
            t_14119.appendString("guest");
            SqlFragment t_6912;
            t_6912 = t_14118.where(t_14119.getAccumulated()).toSql();
            SqlFragment q__1837 = t_6912;
            boolean t_14126 = q__1837.toString().equals("UPDATE users SET active = FALSE WHERE age < 18 AND role = 'guest'");
            Supplier<String> fn__14109 = () -> "update multi where";
            test_197.assert_(t_14126, fn__14109);
        } finally {
            test_197.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryOrWhere__2575() {
        Test test_198 = new Test();
        try {
            SafeIdentifier t_14091 = SrcTest.sid__709("users");
            SafeIdentifier t_14092 = SrcTest.sid__709("status");
            SqlString t_14093 = new SqlString("banned");
            UpdateQuery t_14094 = SrcGlobal.update(t_14091).set(t_14092, t_14093);
            SqlBuilder t_14095 = new SqlBuilder();
            t_14095.appendSafe("spam_count > ");
            t_14095.appendInt32(10);
            UpdateQuery t_14099 = t_14094.where(t_14095.getAccumulated());
            SqlBuilder t_14100 = new SqlBuilder();
            t_14100.appendSafe("reported = ");
            t_14100.appendBoolean(true);
            SqlFragment t_6891;
            t_6891 = t_14099.orWhere(t_14100.getAccumulated()).toSql();
            SqlFragment q__1839 = t_6891;
            boolean t_14107 = q__1839.toString().equals("UPDATE users SET status = 'banned' WHERE spam_count > 10 OR reported = TRUE");
            Supplier<String> fn__14090 = () -> "update orWhere";
            test_198.assert_(t_14107, fn__14090);
        } finally {
            test_198.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutWhere__2578() {
        Test test_199 = new Test();
        try {
            SafeIdentifier t_14084;
            SafeIdentifier t_14085;
            SqlInt32 t_14086;
            boolean didBubble__1841;
            boolean didBubble_17431;
            try {
                t_14084 = SrcTest.sid__709("users");
                t_14085 = SrcTest.sid__709("x");
                t_14086 = new SqlInt32(1);
                SrcGlobal.update(t_14084).set(t_14085, t_14086).toSql();
                didBubble_17431 = false;
            } catch (RuntimeException ignored$16) {
                didBubble_17431 = true;
            }
            didBubble__1841 = didBubble_17431;
            Supplier<String> fn__14083 = () -> "update without WHERE should bubble";
            test_199.assert_(didBubble__1841, fn__14083);
        } finally {
            test_199.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutSet__2579() {
        Test test_200 = new Test();
        try {
            SafeIdentifier t_14075;
            SqlBuilder t_14076;
            SqlFragment t_14079;
            boolean didBubble__1843;
            boolean didBubble_17432;
            try {
                t_14075 = SrcTest.sid__709("users");
                t_14076 = new SqlBuilder();
                t_14076.appendSafe("id = ");
                t_14076.appendInt32(1);
                t_14079 = t_14076.getAccumulated();
                SrcGlobal.update(t_14075).where(t_14079).toSql();
                didBubble_17432 = false;
            } catch (RuntimeException ignored$17) {
                didBubble_17432 = true;
            }
            didBubble__1843 = didBubble_17432;
            Supplier<String> fn__14074 = () -> "update without SET should bubble";
            test_200.assert_(didBubble__1843, fn__14074);
        } finally {
            test_200.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryWithLimit__2581() {
        Test test_201 = new Test();
        try {
            SafeIdentifier t_14061 = SrcTest.sid__709("users");
            SafeIdentifier t_14062 = SrcTest.sid__709("active");
            SqlBoolean t_14063 = new SqlBoolean(false);
            UpdateQuery t_14064 = SrcGlobal.update(t_14061).set(t_14062, t_14063);
            SqlBuilder t_14065 = new SqlBuilder();
            t_14065.appendSafe("last_login < ");
            t_14065.appendString("2024-01-01");
            UpdateQuery t_6854;
            t_6854 = t_14064.where(t_14065.getAccumulated()).limit(100);
            SqlFragment t_6855;
            t_6855 = t_6854.toSql();
            SqlFragment q__1845 = t_6855;
            boolean t_14072 = q__1845.toString().equals("UPDATE users SET active = FALSE WHERE last_login < '2024-01-01' LIMIT 100");
            Supplier<String> fn__14060 = () -> "update limit";
            test_201.assert_(t_14072, fn__14060);
        } finally {
            test_201.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryEscaping__2583() {
        Test test_202 = new Test();
        try {
            SafeIdentifier t_14047 = SrcTest.sid__709("users");
            SafeIdentifier t_14048 = SrcTest.sid__709("bio");
            SqlString t_14049 = new SqlString("It's a test");
            UpdateQuery t_14050 = SrcGlobal.update(t_14047).set(t_14048, t_14049);
            SqlBuilder t_14051 = new SqlBuilder();
            t_14051.appendSafe("id = ");
            t_14051.appendInt32(1);
            SqlFragment t_6839;
            t_6839 = t_14050.where(t_14051.getAccumulated()).toSql();
            SqlFragment q__1847 = t_6839;
            boolean t_14058 = q__1847.toString().equals("UPDATE users SET bio = 'It''s a test' WHERE id = 1");
            Supplier<String> fn__14046 = () -> "update escaping";
            test_202.assert_(t_14058, fn__14046);
        } finally {
            test_202.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBasic__2585() {
        Test test_203 = new Test();
        try {
            SafeIdentifier t_14036 = SrcTest.sid__709("users");
            SqlBuilder t_14037 = new SqlBuilder();
            t_14037.appendSafe("id = ");
            t_14037.appendInt32(1);
            SqlFragment t_14040 = t_14037.getAccumulated();
            SqlFragment t_6824;
            t_6824 = SrcGlobal.deleteFrom(t_14036).where(t_14040).toSql();
            SqlFragment q__1849 = t_6824;
            boolean t_14044 = q__1849.toString().equals("DELETE FROM users WHERE id = 1");
            Supplier<String> fn__14035 = () -> "delete basic";
            test_203.assert_(t_14044, fn__14035);
        } finally {
            test_203.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryMultipleWhere__2587() {
        Test test_204 = new Test();
        try {
            SafeIdentifier t_14020 = SrcTest.sid__709("logs");
            SqlBuilder t_14021 = new SqlBuilder();
            t_14021.appendSafe("created_at < ");
            t_14021.appendString("2024-01-01");
            SqlFragment t_14024 = t_14021.getAccumulated();
            DeleteQuery t_14025 = SrcGlobal.deleteFrom(t_14020).where(t_14024);
            SqlBuilder t_14026 = new SqlBuilder();
            t_14026.appendSafe("level = ");
            t_14026.appendString("debug");
            SqlFragment t_6812;
            t_6812 = t_14025.where(t_14026.getAccumulated()).toSql();
            SqlFragment q__1851 = t_6812;
            boolean t_14033 = q__1851.toString().equals("DELETE FROM logs WHERE created_at < '2024-01-01' AND level = 'debug'");
            Supplier<String> fn__14019 = () -> "delete multi where";
            test_204.assert_(t_14033, fn__14019);
        } finally {
            test_204.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBubblesWithoutWhere__2590() {
        Test test_205 = new Test();
        try {
            boolean didBubble__1853;
            boolean didBubble_17433;
            try {
                SrcGlobal.deleteFrom(SrcTest.sid__709("users")).toSql();
                didBubble_17433 = false;
            } catch (RuntimeException ignored$18) {
                didBubble_17433 = true;
            }
            didBubble__1853 = didBubble_17433;
            Supplier<String> fn__14015 = () -> "delete without WHERE should bubble";
            test_205.assert_(didBubble__1853, fn__14015);
        } finally {
            test_205.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryOrWhere__2591() {
        Test test_206 = new Test();
        try {
            SafeIdentifier t_14000 = SrcTest.sid__709("sessions");
            SqlBuilder t_14001 = new SqlBuilder();
            t_14001.appendSafe("expired = ");
            t_14001.appendBoolean(true);
            SqlFragment t_14004 = t_14001.getAccumulated();
            DeleteQuery t_14005 = SrcGlobal.deleteFrom(t_14000).where(t_14004);
            SqlBuilder t_14006 = new SqlBuilder();
            t_14006.appendSafe("created_at < ");
            t_14006.appendString("2023-01-01");
            SqlFragment t_6791;
            t_6791 = t_14005.orWhere(t_14006.getAccumulated()).toSql();
            SqlFragment q__1855 = t_6791;
            boolean t_14013 = q__1855.toString().equals("DELETE FROM sessions WHERE expired = TRUE OR created_at < '2023-01-01'");
            Supplier<String> fn__13999 = () -> "delete orWhere";
            test_206.assert_(t_14013, fn__13999);
        } finally {
            test_206.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryWithLimit__2594() {
        Test test_207 = new Test();
        try {
            SafeIdentifier t_13989 = SrcTest.sid__709("logs");
            SqlBuilder t_13990 = new SqlBuilder();
            t_13990.appendSafe("level = ");
            t_13990.appendString("debug");
            SqlFragment t_13993 = t_13990.getAccumulated();
            DeleteQuery t_6772;
            t_6772 = SrcGlobal.deleteFrom(t_13989).where(t_13993).limit(1000);
            SqlFragment t_6773;
            t_6773 = t_6772.toSql();
            SqlFragment q__1857 = t_6773;
            boolean t_13997 = q__1857.toString().equals("DELETE FROM logs WHERE level = 'debug' LIMIT 1000");
            Supplier<String> fn__13988 = () -> "delete limit";
            test_207.assert_(t_13997, fn__13988);
        } finally {
            test_207.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByNullsNullsFirst__2596() {
        Test test_208 = new Test();
        try {
            SafeIdentifier t_13979 = SrcTest.sid__709("users");
            SafeIdentifier t_13980 = SrcTest.sid__709("email");
            NullsFirst t_13981 = new NullsFirst();
            Query q__1859 = SrcGlobal.from(t_13979).orderByNulls(t_13980, true, t_13981);
            boolean t_13986 = q__1859.toSql().toString().equals("SELECT * FROM users ORDER BY email ASC NULLS FIRST");
            Supplier<String> fn__13978 = () -> "nulls first";
            test_208.assert_(t_13986, fn__13978);
        } finally {
            test_208.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByNullsNullsLast__2597() {
        Test test_209 = new Test();
        try {
            SafeIdentifier t_13969 = SrcTest.sid__709("users");
            SafeIdentifier t_13970 = SrcTest.sid__709("score");
            NullsLast t_13971 = new NullsLast();
            Query q__1861 = SrcGlobal.from(t_13969).orderByNulls(t_13970, false, t_13971);
            boolean t_13976 = q__1861.toSql().toString().equals("SELECT * FROM users ORDER BY score DESC NULLS LAST");
            Supplier<String> fn__13968 = () -> "nulls last";
            test_209.assert_(t_13976, fn__13968);
        } finally {
            test_209.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedOrderByAndOrderByNulls__2598() {
        Test test_210 = new Test();
        try {
            SafeIdentifier t_13957 = SrcTest.sid__709("users");
            SafeIdentifier t_13958 = SrcTest.sid__709("name");
            Query q__1863 = SrcGlobal.from(t_13957).orderBy(t_13958, true).orderByNulls(SrcTest.sid__709("email"), true, new NullsFirst());
            boolean t_13966 = q__1863.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC, email ASC NULLS FIRST");
            Supplier<String> fn__13956 = () -> "mixed order";
            test_210.assert_(t_13966, fn__13956);
        } finally {
            test_210.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void crossJoin__2599() {
        Test test_211 = new Test();
        try {
            SafeIdentifier t_13948 = SrcTest.sid__709("users");
            SafeIdentifier t_13949 = SrcTest.sid__709("colors");
            Query q__1865 = SrcGlobal.from(t_13948).crossJoin(t_13949);
            boolean t_13954 = q__1865.toSql().toString().equals("SELECT * FROM users CROSS JOIN colors");
            Supplier<String> fn__13947 = () -> "cross join";
            test_211.assert_(t_13954, fn__13947);
        } finally {
            test_211.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void crossJoinCombinedWithOtherJoins__2600() {
        Test test_212 = new Test();
        try {
            SafeIdentifier t_13934 = SrcTest.sid__709("users");
            SafeIdentifier t_13935 = SrcTest.sid__709("orders");
            SqlBuilder t_13936 = new SqlBuilder();
            t_13936.appendSafe("users.id = orders.user_id");
            SqlFragment t_13938 = t_13936.getAccumulated();
            Query q__1867 = SrcGlobal.from(t_13934).innerJoin(t_13935, t_13938).crossJoin(SrcTest.sid__709("colors"));
            boolean t_13945 = q__1867.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id CROSS JOIN colors");
            Supplier<String> fn__13933 = () -> "cross + inner join";
            test_212.assert_(t_13945, fn__13933);
        } finally {
            test_212.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockForUpdate__2602() {
        Test test_213 = new Test();
        try {
            SafeIdentifier t_13920 = SrcTest.sid__709("users");
            SqlBuilder t_13921 = new SqlBuilder();
            t_13921.appendSafe("id = ");
            t_13921.appendInt32(1);
            SqlFragment t_13924 = t_13921.getAccumulated();
            Query q__1869 = SrcGlobal.from(t_13920).where(t_13924).lock(new ForUpdate());
            boolean t_13931 = q__1869.toSql().toString().equals("SELECT * FROM users WHERE id = 1 FOR UPDATE");
            Supplier<String> fn__13919 = () -> "for update";
            test_213.assert_(t_13931, fn__13919);
        } finally {
            test_213.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockForShare__2604() {
        Test test_214 = new Test();
        try {
            SafeIdentifier t_13909 = SrcTest.sid__709("users");
            SafeIdentifier t_13910 = SrcTest.sid__709("name");
            Query q__1871 = SrcGlobal.from(t_13909).select(List.of(t_13910)).lock(new ForShare());
            boolean t_13917 = q__1871.toSql().toString().equals("SELECT name FROM users FOR SHARE");
            Supplier<String> fn__13908 = () -> "for share";
            test_214.assert_(t_13917, fn__13908);
        } finally {
            test_214.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockWithFullQuery__2605() {
        Test test_215 = new Test();
        try {
            SafeIdentifier t_13895 = SrcTest.sid__709("accounts");
            SqlBuilder t_13896 = new SqlBuilder();
            t_13896.appendSafe("id = ");
            t_13896.appendInt32(42);
            SqlFragment t_13899 = t_13896.getAccumulated();
            Query t_6696;
            t_6696 = SrcGlobal.from(t_13895).where(t_13899).limit(1);
            Query t_13902 = t_6696.lock(new ForUpdate());
            Query q__1873 = t_13902;
            boolean t_13906 = q__1873.toSql().toString().equals("SELECT * FROM accounts WHERE id = 42 LIMIT 1 FOR UPDATE");
            Supplier<String> fn__13894 = () -> "lock full query";
            test_215.assert_(t_13906, fn__13894);
        } finally {
            test_215.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void queryBuilderImmutabilityTwoQueriesFromSameBase__2607() {
        Test test_216 = new Test();
        try {
            SafeIdentifier t_13878 = SrcTest.sid__709("users");
            SqlBuilder t_13879 = new SqlBuilder();
            t_13879.appendSafe("active = ");
            t_13879.appendBoolean(true);
            SqlFragment t_13882 = t_13879.getAccumulated();
            Query base__1875 = SrcGlobal.from(t_13878).where(t_13882);
            Query t_6677;
            t_6677 = base__1875.limit(10);
            Query q1__1876 = t_6677;
            Query t_6680;
            t_6680 = base__1875.limit(20);
            Query q2__1877 = t_6680;
            boolean t_13887 = q1__1876.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE LIMIT 10");
            Supplier<String> fn__13877 = () -> "q1";
            test_216.assert_(t_13887, fn__13877);
            boolean t_13892 = q2__1877.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE LIMIT 20");
            Supplier<String> fn__13876 = () -> "q2";
            test_216.assert_(t_13892, fn__13876);
        } finally {
            test_216.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitZeroProducesLimit0__2609() {
        Test test_217 = new Test();
        try {
            Query t_6664;
            t_6664 = SrcGlobal.from(SrcTest.sid__709("users")).limit(0);
            Query q__1879 = t_6664;
            boolean t_13874 = q__1879.toSql().toString().equals("SELECT * FROM users LIMIT 0");
            Supplier<String> fn__13869 = () -> "limit 0";
            test_217.assert_(t_13874, fn__13869);
        } finally {
            test_217.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlWithZeroDefaultLimit__2610() {
        Test test_218 = new Test();
        try {
            Query q__1881 = SrcGlobal.from(SrcTest.sid__709("users"));
            SqlFragment t_6658;
            t_6658 = q__1881.safeToSql(0);
            SqlFragment s__1882 = t_6658;
            boolean t_13867 = s__1882.toString().equals("SELECT * FROM users LIMIT 0");
            Supplier<String> fn__13863 = () -> "safeToSql 0";
            test_218.assert_(t_13867, fn__13863);
        } finally {
            test_218.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryLimitBubblesOnNegative__2611() {
        Test test_219 = new Test();
        try {
            SafeIdentifier t_13852;
            SafeIdentifier t_13853;
            SqlString t_13854;
            UpdateQuery t_13855;
            SqlBuilder t_13856;
            boolean didBubble__1884;
            boolean didBubble_17434;
            try {
                t_13852 = SrcTest.sid__709("users");
                t_13853 = SrcTest.sid__709("name");
                t_13854 = new SqlString("x");
                t_13855 = SrcGlobal.update(t_13852).set(t_13853, t_13854);
                t_13856 = new SqlBuilder();
                t_13856.appendSafe("id = ");
                t_13856.appendInt32(1);
                t_13855.where(t_13856.getAccumulated()).limit(-1);
                didBubble_17434 = false;
            } catch (RuntimeException ignored$19) {
                didBubble_17434 = true;
            }
            didBubble__1884 = didBubble_17434;
            Supplier<String> fn__13851 = () -> "UpdateQuery negative limit should bubble";
            test_219.assert_(didBubble__1884, fn__13851);
        } finally {
            test_219.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryLimitBubblesOnNegative__2613() {
        Test test_220 = new Test();
        try {
            SafeIdentifier t_13843;
            SqlBuilder t_13844;
            SqlFragment t_13847;
            boolean didBubble__1886;
            boolean didBubble_17435;
            try {
                t_13843 = SrcTest.sid__709("users");
                t_13844 = new SqlBuilder();
                t_13844.appendSafe("id = ");
                t_13844.appendInt32(1);
                t_13847 = t_13844.getAccumulated();
                SrcGlobal.deleteFrom(t_13843).where(t_13847).limit(-1);
                didBubble_17435 = false;
            } catch (RuntimeException ignored$20) {
                didBubble_17435 = true;
            }
            didBubble__1886 = didBubble_17435;
            Supplier<String> fn__13842 = () -> "DeleteQuery negative limit should bubble";
            test_220.assert_(didBubble__1886, fn__13842);
        } finally {
            test_220.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryImmutabilityTwoFromSameBase__2615() {
        Test test_221 = new Test();
        try {
            SafeIdentifier t_13812 = SrcTest.sid__709("users");
            SafeIdentifier t_13813 = SrcTest.sid__709("name");
            SqlString t_13814 = new SqlString("Alice");
            UpdateQuery t_13815 = SrcGlobal.update(t_13812).set(t_13813, t_13814);
            SqlBuilder t_13816 = new SqlBuilder();
            t_13816.appendSafe("id = ");
            t_13816.appendInt32(1);
            UpdateQuery base__1888 = t_13815.where(t_13816.getAccumulated());
            UpdateQuery q1__1889 = base__1888.set(SrcTest.sid__709("age"), new SqlInt32(25));
            UpdateQuery q2__1890 = base__1888.set(SrcTest.sid__709("age"), new SqlInt32(30));
            SqlFragment t_6618;
            t_6618 = q1__1889.toSql();
            SqlFragment t_6619 = t_6618;
            String s1__1891 = t_6619.toString();
            SqlFragment t_6621;
            t_6621 = q2__1890.toSql();
            SqlFragment t_6622 = t_6621;
            String s2__1892 = t_6622.toString();
            boolean t_13830 = s1__1891.indexOf("25") >= 0;
            Supplier<String> fn__13811 = () -> "q1 should have 25: " + s1__1891;
            test_221.assert_(t_13830, fn__13811);
            boolean t_13834 = s2__1892.indexOf("30") >= 0;
            Supplier<String> fn__13810 = () -> "q2 should have 30: " + s2__1892;
            test_221.assert_(t_13834, fn__13810);
            boolean t_13840 = s1__1891.indexOf("30") < 0;
            Supplier<String> fn__13809 = () -> "q1 should NOT have 30: " + s1__1891;
            test_221.assert_(t_13840, fn__13809);
        } finally {
            test_221.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryImmutability__2617() {
        Test test_222 = new Test();
        try {
            SafeIdentifier t_13778 = SrcTest.sid__709("users");
            SqlBuilder t_13779 = new SqlBuilder();
            t_13779.appendSafe("active = ");
            t_13779.appendBoolean(false);
            SqlFragment t_13782 = t_13779.getAccumulated();
            DeleteQuery base__1894 = SrcGlobal.deleteFrom(t_13778).where(t_13782);
            SqlBuilder t_13784 = new SqlBuilder();
            t_13784.appendSafe("age < ");
            t_13784.appendInt32(18);
            DeleteQuery q1__1895 = base__1894.where(t_13784.getAccumulated());
            SqlBuilder t_13789 = new SqlBuilder();
            t_13789.appendSafe("age > ");
            t_13789.appendInt32(65);
            DeleteQuery q2__1896 = base__1894.where(t_13789.getAccumulated());
            SqlFragment t_6584;
            t_6584 = q1__1895.toSql();
            SqlFragment t_6585 = t_6584;
            String s1__1897 = t_6585.toString();
            SqlFragment t_6587;
            t_6587 = q2__1896.toSql();
            SqlFragment t_6588 = t_6587;
            String s2__1898 = t_6588.toString();
            boolean t_13797 = s1__1897.indexOf("age < 18") >= 0;
            Supplier<String> fn__13777 = () -> "q1: " + s1__1897;
            test_222.assert_(t_13797, fn__13777);
            boolean t_13801 = s2__1898.indexOf("age > 65") >= 0;
            Supplier<String> fn__13776 = () -> "q2: " + s2__1898;
            test_222.assert_(t_13801, fn__13776);
            boolean t_13807 = s1__1897.indexOf("age > 65") < 0;
            Supplier<String> fn__13775 = () -> "q1 should not have q2 condition: " + s1__1897;
            test_222.assert_(t_13807, fn__13775);
        } finally {
            test_222.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__2621() {
        Test test_229 = new Test();
        try {
            SafeIdentifier t_6561;
            t_6561 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__1946 = t_6561;
            boolean t_13773 = id__1946.getSqlValue().equals("user_name");
            Supplier<String> fn__13770 = () -> "value should round-trip";
            test_229.assert_(t_13773, fn__13770);
        } finally {
            test_229.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__2622() {
        Test test_230 = new Test();
        try {
            boolean didBubble__1948;
            boolean didBubble_17436;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_17436 = false;
            } catch (RuntimeException ignored$21) {
                didBubble_17436 = true;
            }
            didBubble__1948 = didBubble_17436;
            Supplier<String> fn__13767 = () -> "empty string should bubble";
            test_230.assert_(didBubble__1948, fn__13767);
        } finally {
            test_230.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__2623() {
        Test test_231 = new Test();
        try {
            boolean didBubble__1950;
            boolean didBubble_17437;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_17437 = false;
            } catch (RuntimeException ignored$22) {
                didBubble_17437 = true;
            }
            didBubble__1950 = didBubble_17437;
            Supplier<String> fn__13764 = () -> "leading digit should bubble";
            test_231.assert_(didBubble__1950, fn__13764);
        } finally {
            test_231.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__2624() {
        Test test_232 = new Test();
        try {
            List<String> cases__1952 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__13761 = c__1953 -> {
                boolean didBubble__1954;
                boolean didBubble_17438;
                try {
                    SrcGlobal.safeIdentifier(c__1953);
                    didBubble_17438 = false;
                } catch (RuntimeException ignored$23) {
                    didBubble_17438 = true;
                }
                didBubble__1954 = didBubble_17438;
                Supplier<String> fn__13758 = () -> "should reject: " + c__1953;
                test_232.assert_(didBubble__1954, fn__13758);
            };
            cases__1952.forEach(fn__13761);
        } finally {
            test_232.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__2625() {
        Test test_233 = new Test();
        try {
            SafeIdentifier t_6538;
            t_6538 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_6539 = t_6538;
            SafeIdentifier t_6540;
            t_6540 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_6541 = t_6540;
            StringField t_13748 = new StringField();
            FieldDef t_13749 = new FieldDef(t_6541, t_13748, false, null, false);
            SafeIdentifier t_6544;
            t_6544 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_6545 = t_6544;
            IntField t_13750 = new IntField();
            FieldDef t_13751 = new FieldDef(t_6545, t_13750, false, null, false);
            TableDef td__1956 = new TableDef(t_6539, List.of(t_13749, t_13751), null);
            FieldDef t_6549;
            t_6549 = td__1956.field("age");
            FieldDef f__1957 = t_6549;
            boolean t_13756 = f__1957.getName().getSqlValue().equals("age");
            Supplier<String> fn__13747 = () -> "should find age field";
            test_233.assert_(t_13756, fn__13747);
        } finally {
            test_233.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__2626() {
        Test test_234 = new Test();
        try {
            SafeIdentifier t_6529;
            t_6529 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_6530 = t_6529;
            SafeIdentifier t_6531;
            t_6531 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_6532 = t_6531;
            StringField t_13742 = new StringField();
            FieldDef t_13743 = new FieldDef(t_6532, t_13742, false, null, false);
            TableDef td__1959 = new TableDef(t_6530, List.of(t_13743), null);
            boolean didBubble__1960;
            boolean didBubble_17439;
            try {
                td__1959.field("nonexistent");
                didBubble_17439 = false;
            } catch (RuntimeException ignored$24) {
                didBubble_17439 = true;
            }
            didBubble__1960 = didBubble_17439;
            Supplier<String> fn__13741 = () -> "unknown field should bubble";
            test_234.assert_(didBubble__1960, fn__13741);
        } finally {
            test_234.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__2627() {
        Test test_235 = new Test();
        try {
            SafeIdentifier t_6517;
            t_6517 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_6518 = t_6517;
            StringField t_13730 = new StringField();
            FieldDef required__1962 = new FieldDef(t_6518, t_13730, false, null, false);
            SafeIdentifier t_6521;
            t_6521 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_6522 = t_6521;
            StringField t_13732 = new StringField();
            FieldDef optional__1963 = new FieldDef(t_6522, t_13732, true, null, false);
            boolean t_13736 = !required__1962.isNullable();
            Supplier<String> fn__13729 = () -> "required field should not be nullable";
            test_235.assert_(t_13736, fn__13729);
            boolean t_13738 = optional__1963.isNullable();
            Supplier<String> fn__13728 = () -> "optional field should be nullable";
            test_235.assert_(t_13738, fn__13728);
        } finally {
            test_235.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void pkNameDefaultsToIdWhenPrimaryKeyIsNull__2628() {
        Test test_236 = new Test();
        try {
            SafeIdentifier t_6508;
            t_6508 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_6509 = t_6508;
            SafeIdentifier t_6510;
            t_6510 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_6511 = t_6510;
            StringField t_13721 = new StringField();
            FieldDef t_13722 = new FieldDef(t_6511, t_13721, false, null, false);
            TableDef td__1965 = new TableDef(t_6509, List.of(t_13722), null);
            boolean t_13726 = td__1965.pkName().equals("id");
            Supplier<String> fn__13720 = () -> "default pk should be id";
            test_236.assert_(t_13726, fn__13720);
        } finally {
            test_236.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void pkNameReturnsCustomPrimaryKey__2629() {
        Test test_237 = new Test();
        try {
            SafeIdentifier t_6496;
            t_6496 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_6497 = t_6496;
            SafeIdentifier t_6498;
            t_6498 = SrcGlobal.safeIdentifier("user_id");
            SafeIdentifier t_6499 = t_6498;
            IntField t_13713 = new IntField();
            List<FieldDef> t_6504 = List.of(new FieldDef(t_6499, t_13713, false, null, false));
            SafeIdentifier t_6502;
            t_6502 = SrcGlobal.safeIdentifier("user_id");
            SafeIdentifier t_6503 = t_6502;
            TableDef td__1967 = new TableDef(t_6497, t_6504, t_6503);
            boolean t_13718 = td__1967.pkName().equals("user_id");
            Supplier<String> fn__13712 = () -> "custom pk should be user_id";
            test_237.assert_(t_13718, fn__13712);
        } finally {
            test_237.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void timestampsReturnsTwoDateFieldDefs__2630() {
        Test test_238 = new Test();
        try {
            List<FieldDef> t_6472;
            t_6472 = SrcGlobal.timestamps();
            List<FieldDef> ts__1969 = t_6472;
            boolean t_13680 = ts__1969.size() == 2;
            Supplier<String> fn__13677 = () -> "should return 2 fields";
            test_238.assert_(t_13680, fn__13677);
            boolean t_13686 = Core.listGet(ts__1969, 0).getName().getSqlValue().equals("inserted_at");
            Supplier<String> fn__13676 = () -> "first should be inserted_at";
            test_238.assert_(t_13686, fn__13676);
            boolean t_13692 = Core.listGet(ts__1969, 1).getName().getSqlValue().equals("updated_at");
            Supplier<String> fn__13675 = () -> "second should be updated_at";
            test_238.assert_(t_13692, fn__13675);
            boolean t_13695 = Core.listGet(ts__1969, 0).isNullable();
            Supplier<String> fn__13674 = () -> "inserted_at should be nullable";
            test_238.assert_(t_13695, fn__13674);
            boolean t_13699 = Core.listGet(ts__1969, 1).isNullable();
            Supplier<String> fn__13673 = () -> "updated_at should be nullable";
            test_238.assert_(t_13699, fn__13673);
            boolean t_13705 = Core.listGet(ts__1969, 0).getDefaultValue() != null;
            Supplier<String> fn__13672 = () -> "inserted_at should have default";
            test_238.assert_(t_13705, fn__13672);
            boolean t_13710 = Core.listGet(ts__1969, 1).getDefaultValue() != null;
            Supplier<String> fn__13671 = () -> "updated_at should have default";
            test_238.assert_(t_13710, fn__13671);
        } finally {
            test_238.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefDefaultValueField__2631() {
        Test test_239 = new Test();
        try {
            SafeIdentifier t_6459;
            t_6459 = SrcGlobal.safeIdentifier("status");
            SafeIdentifier t_6460 = t_6459;
            StringField t_13658 = new StringField();
            SqlDefault t_13659 = new SqlDefault();
            FieldDef withDefault__1971 = new FieldDef(t_6460, t_13658, false, t_13659, false);
            SafeIdentifier t_6464;
            t_6464 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_6465 = t_6464;
            StringField t_13661 = new StringField();
            FieldDef withoutDefault__1972 = new FieldDef(t_6465, t_13661, false, null, false);
            boolean t_13665 = withDefault__1971.getDefaultValue() != null;
            Supplier<String> fn__13657 = () -> "should have default";
            test_239.assert_(t_13665, fn__13657);
            boolean t_13669 = withoutDefault__1972.getDefaultValue() == null;
            Supplier<String> fn__13656 = () -> "should not have default";
            test_239.assert_(t_13669, fn__13656);
        } finally {
            test_239.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefVirtualFlag__2632() {
        Test test_240 = new Test();
        try {
            SafeIdentifier t_6447;
            t_6447 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_6448 = t_6447;
            StringField t_13645 = new StringField();
            FieldDef normal__1974 = new FieldDef(t_6448, t_13645, false, null, false);
            SafeIdentifier t_6451;
            t_6451 = SrcGlobal.safeIdentifier("full_name");
            SafeIdentifier t_6452 = t_6451;
            StringField t_13647 = new StringField();
            FieldDef virt__1975 = new FieldDef(t_6452, t_13647, true, null, true);
            boolean t_13651 = !normal__1974.isVirtual();
            Supplier<String> fn__13644 = () -> "normal field should not be virtual";
            test_240.assert_(t_13651, fn__13644);
            boolean t_13653 = virt__1975.isVirtual();
            Supplier<String> fn__13643 = () -> "virtual field should be virtual";
            test_240.assert_(t_13653, fn__13643);
        } finally {
            test_240.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsSingleCharacterNames__2633() {
        Test test_241 = new Test();
        try {
            SafeIdentifier t_6439;
            t_6439 = SrcGlobal.safeIdentifier("a");
            SafeIdentifier a__1977 = t_6439;
            boolean t_13637 = a__1977.getSqlValue().equals("a");
            Supplier<String> fn__13634 = () -> "single letter should work";
            test_241.assert_(t_13637, fn__13634);
            SafeIdentifier t_6443;
            t_6443 = SrcGlobal.safeIdentifier("_");
            SafeIdentifier u__1978 = t_6443;
            boolean t_13641 = u__1978.getSqlValue().equals("_");
            Supplier<String> fn__13633 = () -> "single underscore should work";
            test_241.assert_(t_13641, fn__13633);
        } finally {
            test_241.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsAllUnderscoreNames__2634() {
        Test test_242 = new Test();
        try {
            SafeIdentifier t_6435;
            t_6435 = SrcGlobal.safeIdentifier("___");
            SafeIdentifier id__1980 = t_6435;
            boolean t_13631 = id__1980.getSqlValue().equals("___");
            Supplier<String> fn__13628 = () -> "all underscores should work";
            test_242.assert_(t_13631, fn__13628);
        } finally {
            test_242.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefWithEmptyFieldList__2635() {
        Test test_243 = new Test();
        try {
            SafeIdentifier t_6430;
            t_6430 = SrcGlobal.safeIdentifier("empty");
            SafeIdentifier t_6431 = t_6430;
            TableDef tbl__1982 = new TableDef(t_6431, List.of(), null);
            boolean didBubble__1983;
            boolean didBubble_17440;
            try {
                tbl__1982.field("anything");
                didBubble_17440 = false;
            } catch (RuntimeException ignored$25) {
                didBubble_17440 = true;
            }
            didBubble__1983 = didBubble_17440;
            Supplier<String> fn__13624 = () -> "field lookup on empty table should bubble";
            test_243.assert_(didBubble__1983, fn__13624);
        } finally {
            test_243.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__2636() {
        Test test_247 = new Test();
        try {
            Function<String, String> build__2113 = name__2115 -> {
                SqlBuilder t_13606 = new SqlBuilder();
                t_13606.appendSafe("select * from hi where name = ");
                t_13606.appendString(name__2115);
                return t_13606.getAccumulated().toString();
            };
            Function<String, String> buildWrong__2114 = name__2117 -> "select * from hi where name = '" + name__2117 + "'";
            String actual_2638 = build__2113.apply("world");
            boolean t_13616 = actual_2638.equals("select * from hi where name = 'world'");
            Supplier<String> fn__13613 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_2638 + ")";
            test_247.assert_(t_13616, fn__13613);
            String bobbyTables__2119 = "Robert'); drop table hi;--";
            String actual_2640 = build__2113.apply("Robert'); drop table hi;--");
            boolean t_13620 = actual_2640.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__13612 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_2640 + ")";
            test_247.assert_(t_13620, fn__13612);
            Supplier<String> fn__13611 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_247.assert_(true, fn__13611);
        } finally {
            test_247.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__2644() {
        Test test_248 = new Test();
        try {
            SqlBuilder t_13574 = new SqlBuilder();
            t_13574.appendSafe("v = ");
            t_13574.appendString("");
            String actual_2645 = t_13574.getAccumulated().toString();
            boolean t_13580 = actual_2645.equals("v = ''");
            Supplier<String> fn__13573 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_2645 + ")";
            test_248.assert_(t_13580, fn__13573);
            SqlBuilder t_13582 = new SqlBuilder();
            t_13582.appendSafe("v = ");
            t_13582.appendString("a''b");
            String actual_2648 = t_13582.getAccumulated().toString();
            boolean t_13588 = actual_2648.equals("v = 'a''''b'");
            Supplier<String> fn__13572 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_2648 + ")";
            test_248.assert_(t_13588, fn__13572);
            SqlBuilder t_13590 = new SqlBuilder();
            t_13590.appendSafe("v = ");
            t_13590.appendString("Hello \u4e16\u754c");
            String actual_2651 = t_13590.getAccumulated().toString();
            boolean t_13596 = actual_2651.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__13571 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_2651 + ")";
            test_248.assert_(t_13596, fn__13571);
            SqlBuilder t_13598 = new SqlBuilder();
            t_13598.appendSafe("v = ");
            t_13598.appendString("Line1\nLine2");
            String actual_2654 = t_13598.getAccumulated().toString();
            boolean t_13604 = actual_2654.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__13570 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_2654 + ")";
            test_248.assert_(t_13604, fn__13570);
        } finally {
            test_248.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__2657() {
        Test test_249 = new Test();
        try {
            SqlBuilder t_13545 = new SqlBuilder();
            t_13545.appendSafe("select ");
            t_13545.appendInt32(42);
            t_13545.appendSafe(", ");
            t_13545.appendInt64(43);
            t_13545.appendSafe(", ");
            t_13545.appendFloat64(19.99D);
            t_13545.appendSafe(", ");
            t_13545.appendBoolean(true);
            t_13545.appendSafe(", ");
            t_13545.appendBoolean(false);
            String actual_2658 = t_13545.getAccumulated().toString();
            boolean t_13559 = actual_2658.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__13544 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_2658 + ")";
            test_249.assert_(t_13559, fn__13544);
            LocalDate t_6375;
            t_6375 = LocalDate.of(2024, 12, 25);
            LocalDate date__2122 = t_6375;
            SqlBuilder t_13561 = new SqlBuilder();
            t_13561.appendSafe("insert into t values (");
            t_13561.appendDate(date__2122);
            t_13561.appendSafe(")");
            String actual_2661 = t_13561.getAccumulated().toString();
            boolean t_13568 = actual_2661.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__13543 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_2661 + ")";
            test_249.assert_(t_13568, fn__13543);
        } finally {
            test_249.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__2664() {
        Test test_250 = new Test();
        try {
            SqlBuilder t_13489 = new SqlBuilder();
            t_13489.appendSafe("v IN (");
            t_13489.appendStringList(List.of("a", "b", "c'd"));
            t_13489.appendSafe(")");
            String actual_2665 = t_13489.getAccumulated().toString();
            boolean t_13496 = actual_2665.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__13488 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_2665 + ")";
            test_250.assert_(t_13496, fn__13488);
            SqlBuilder t_13498 = new SqlBuilder();
            t_13498.appendSafe("v IN (");
            t_13498.appendInt32List(List.of(1, 2, 3));
            t_13498.appendSafe(")");
            String actual_2668 = t_13498.getAccumulated().toString();
            boolean t_13505 = actual_2668.equals("v IN (1, 2, 3)");
            Supplier<String> fn__13487 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_2668 + ")";
            test_250.assert_(t_13505, fn__13487);
            SqlBuilder t_13507 = new SqlBuilder();
            t_13507.appendSafe("v IN (");
            t_13507.appendInt64List(List.of(1, 2));
            t_13507.appendSafe(")");
            String actual_2671 = t_13507.getAccumulated().toString();
            boolean t_13514 = actual_2671.equals("v IN (1, 2)");
            Supplier<String> fn__13486 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_2671 + ")";
            test_250.assert_(t_13514, fn__13486);
            SqlBuilder t_13516 = new SqlBuilder();
            t_13516.appendSafe("v IN (");
            t_13516.appendFloat64List(List.of(1.0D, 2.0D));
            t_13516.appendSafe(")");
            String actual_2674 = t_13516.getAccumulated().toString();
            boolean t_13523 = actual_2674.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__13485 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_2674 + ")";
            test_250.assert_(t_13523, fn__13485);
            SqlBuilder t_13525 = new SqlBuilder();
            t_13525.appendSafe("v IN (");
            t_13525.appendBooleanList(List.of(true, false));
            t_13525.appendSafe(")");
            String actual_2677 = t_13525.getAccumulated().toString();
            boolean t_13532 = actual_2677.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__13484 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_2677 + ")";
            test_250.assert_(t_13532, fn__13484);
            LocalDate t_6347;
            t_6347 = LocalDate.of(2024, 1, 1);
            LocalDate t_6348 = t_6347;
            LocalDate t_6349;
            t_6349 = LocalDate.of(2024, 12, 25);
            LocalDate t_6350 = t_6349;
            List<LocalDate> dates__2124 = List.of(t_6348, t_6350);
            SqlBuilder t_13534 = new SqlBuilder();
            t_13534.appendSafe("v IN (");
            t_13534.appendDateList(dates__2124);
            t_13534.appendSafe(")");
            String actual_2680 = t_13534.getAccumulated().toString();
            boolean t_13541 = actual_2680.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__13483 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_2680 + ")";
            test_250.assert_(t_13541, fn__13483);
        } finally {
            test_250.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__2683() {
        Test test_251 = new Test();
        try {
            double nan__2126;
            nan__2126 = 0.0D / 0.0D;
            SqlBuilder t_13475 = new SqlBuilder();
            t_13475.appendSafe("v = ");
            t_13475.appendFloat64(nan__2126);
            String actual_2684 = t_13475.getAccumulated().toString();
            boolean t_13481 = actual_2684.equals("v = NULL");
            Supplier<String> fn__13474 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_2684 + ")";
            test_251.assert_(t_13481, fn__13474);
        } finally {
            test_251.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__2687() {
        Test test_252 = new Test();
        try {
            double inf__2128;
            inf__2128 = 1.0D / 0.0D;
            SqlBuilder t_13466 = new SqlBuilder();
            t_13466.appendSafe("v = ");
            t_13466.appendFloat64(inf__2128);
            String actual_2688 = t_13466.getAccumulated().toString();
            boolean t_13472 = actual_2688.equals("v = NULL");
            Supplier<String> fn__13465 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_2688 + ")";
            test_252.assert_(t_13472, fn__13465);
        } finally {
            test_252.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__2691() {
        Test test_253 = new Test();
        try {
            double ninf__2130;
            ninf__2130 = -1.0D / 0.0D;
            SqlBuilder t_13457 = new SqlBuilder();
            t_13457.appendSafe("v = ");
            t_13457.appendFloat64(ninf__2130);
            String actual_2692 = t_13457.getAccumulated().toString();
            boolean t_13463 = actual_2692.equals("v = NULL");
            Supplier<String> fn__13456 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_2692 + ")";
            test_253.assert_(t_13463, fn__13456);
        } finally {
            test_253.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__2695() {
        Test test_254 = new Test();
        try {
            SqlBuilder t_13432 = new SqlBuilder();
            t_13432.appendSafe("v = ");
            t_13432.appendFloat64(3.14D);
            String actual_2696 = t_13432.getAccumulated().toString();
            boolean t_13438 = actual_2696.equals("v = 3.14");
            Supplier<String> fn__13431 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_2696 + ")";
            test_254.assert_(t_13438, fn__13431);
            SqlBuilder t_13440 = new SqlBuilder();
            t_13440.appendSafe("v = ");
            t_13440.appendFloat64(0.0D);
            String actual_2699 = t_13440.getAccumulated().toString();
            boolean t_13446 = actual_2699.equals("v = 0.0");
            Supplier<String> fn__13430 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_2699 + ")";
            test_254.assert_(t_13446, fn__13430);
            SqlBuilder t_13448 = new SqlBuilder();
            t_13448.appendSafe("v = ");
            t_13448.appendFloat64(-42.5D);
            String actual_2702 = t_13448.getAccumulated().toString();
            boolean t_13454 = actual_2702.equals("v = -42.5");
            Supplier<String> fn__13429 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_2702 + ")";
            test_254.assert_(t_13454, fn__13429);
        } finally {
            test_254.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__2705() {
        Test test_255 = new Test();
        try {
            LocalDate t_6243;
            t_6243 = LocalDate.of(2024, 6, 15);
            LocalDate d__2133 = t_6243;
            SqlBuilder t_13421 = new SqlBuilder();
            t_13421.appendSafe("v = ");
            t_13421.appendDate(d__2133);
            String actual_2706 = t_13421.getAccumulated().toString();
            boolean t_13427 = actual_2706.equals("v = '2024-06-15'");
            Supplier<String> fn__13420 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_2706 + ")";
            test_255.assert_(t_13427, fn__13420);
        } finally {
            test_255.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__2709() {
        Test test_256 = new Test();
        try {
            String name__2135 = "Someone";
            SqlBuilder t_13389 = new SqlBuilder();
            t_13389.appendSafe("where p.last_name = ");
            t_13389.appendString("Someone");
            SqlFragment condition__2136 = t_13389.getAccumulated();
            SqlBuilder t_13393 = new SqlBuilder();
            t_13393.appendSafe("select p.id from person p ");
            t_13393.appendFragment(condition__2136);
            String actual_2711 = t_13393.getAccumulated().toString();
            boolean t_13399 = actual_2711.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__13388 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_2711 + ")";
            test_256.assert_(t_13399, fn__13388);
            SqlBuilder t_13401 = new SqlBuilder();
            t_13401.appendSafe("select p.id from person p ");
            t_13401.appendPart(condition__2136.toSource());
            String actual_2714 = t_13401.getAccumulated().toString();
            boolean t_13408 = actual_2714.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__13387 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_2714 + ")";
            test_256.assert_(t_13408, fn__13387);
            List<SqlPart> parts__2137 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_13412 = new SqlBuilder();
            t_13412.appendSafe("select ");
            t_13412.appendPartList(parts__2137);
            String actual_2717 = t_13412.getAccumulated().toString();
            boolean t_13418 = actual_2717.equals("select 'a''b', 3");
            Supplier<String> fn__13386 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_2717 + ")";
            test_256.assert_(t_13418, fn__13386);
        } finally {
            test_256.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlInt32_negativeAndZeroValues__2720() {
        Test test_257 = new Test();
        try {
            SqlBuilder t_13370 = new SqlBuilder();
            t_13370.appendSafe("v = ");
            t_13370.appendInt32(-42);
            boolean t_13376 = t_13370.getAccumulated().toString().equals("v = -42");
            Supplier<String> fn__13369 = () -> "negative int";
            test_257.assert_(t_13376, fn__13369);
            SqlBuilder t_13378 = new SqlBuilder();
            t_13378.appendSafe("v = ");
            t_13378.appendInt32(0);
            boolean t_13384 = t_13378.getAccumulated().toString().equals("v = 0");
            Supplier<String> fn__13368 = () -> "zero int";
            test_257.assert_(t_13384, fn__13368);
        } finally {
            test_257.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlInt64_negativeValue__2723() {
        Test test_258 = new Test();
        try {
            SqlBuilder t_13360 = new SqlBuilder();
            t_13360.appendSafe("v = ");
            t_13360.appendInt64(-99);
            boolean t_13366 = t_13360.getAccumulated().toString().equals("v = -99");
            Supplier<String> fn__13359 = () -> "negative int64";
            test_258.assert_(t_13366, fn__13359);
        } finally {
            test_258.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void singleElementListRendering__2725() {
        Test test_259 = new Test();
        try {
            SqlBuilder t_13341 = new SqlBuilder();
            t_13341.appendSafe("v IN (");
            t_13341.appendInt32List(List.of(42));
            t_13341.appendSafe(")");
            boolean t_13348 = t_13341.getAccumulated().toString().equals("v IN (42)");
            Supplier<String> fn__13340 = () -> "single int";
            test_259.assert_(t_13348, fn__13340);
            SqlBuilder t_13350 = new SqlBuilder();
            t_13350.appendSafe("v IN (");
            t_13350.appendStringList(List.of("only"));
            t_13350.appendSafe(")");
            boolean t_13357 = t_13350.getAccumulated().toString().equals("v IN ('only')");
            Supplier<String> fn__13339 = () -> "single string";
            test_259.assert_(t_13357, fn__13339);
        } finally {
            test_259.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDefaultRendersDefaultKeyword__2728() {
        Test test_260 = new Test();
        try {
            SqlBuilder b__2142 = new SqlBuilder();
            b__2142.appendSafe("v = ");
            b__2142.appendPart(new SqlDefault());
            boolean t_13337 = b__2142.getAccumulated().toString().equals("v = DEFAULT");
            Supplier<String> fn__13329 = () -> "default keyword";
            test_260.assert_(t_13337, fn__13329);
        } finally {
            test_260.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlStringWithBackslash__2729() {
        Test test_261 = new Test();
        try {
            SqlBuilder t_13321 = new SqlBuilder();
            t_13321.appendSafe("v = ");
            t_13321.appendString("a\\b");
            boolean t_13327 = t_13321.getAccumulated().toString().equals("v = 'a\\b'");
            Supplier<String> fn__13320 = () -> "backslash passthrough";
            test_261.assert_(t_13327, fn__13320);
        } finally {
            test_261.softFailToHard();
        }
    }
}
