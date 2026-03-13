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
public final class SrcTest {
    private SrcTest() {
    }
    static SafeIdentifier csid__544(String name__689) {
        SafeIdentifier t_6343;
        t_6343 = SrcGlobal.safeIdentifier(name__689);
        return t_6343;
    }
    static TableDef userTable__545() {
        return new TableDef(SrcTest.csid__544("users"), List.of(new FieldDef(SrcTest.csid__544("name"), new StringField(), false), new FieldDef(SrcTest.csid__544("email"), new StringField(), false), new FieldDef(SrcTest.csid__544("age"), new IntField(), true), new FieldDef(SrcTest.csid__544("score"), new FloatField(), true), new FieldDef(SrcTest.csid__544("active"), new BoolField(), true)));
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__1645() {
        Test test_24 = new Test();
        try {
            Map<String, String> params__693 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_11177 = SrcTest.userTable__545();
            SafeIdentifier t_11178 = SrcTest.csid__544("name");
            SafeIdentifier t_11179 = SrcTest.csid__544("email");
            Changeset cs__694 = SrcGlobal.changeset(t_11177, params__693).cast(List.of(t_11178, t_11179));
            boolean t_11182 = cs__694.getChanges().containsKey("name");
            Supplier<String> fn__11172 = () -> "name should be in changes";
            test_24.assert_(t_11182, fn__11172);
            boolean t_11186 = cs__694.getChanges().containsKey("email");
            Supplier<String> fn__11171 = () -> "email should be in changes";
            test_24.assert_(t_11186, fn__11171);
            boolean t_11192 = !cs__694.getChanges().containsKey("admin");
            Supplier<String> fn__11170 = () -> "admin must be dropped (not in whitelist)";
            test_24.assert_(t_11192, fn__11170);
            boolean t_11194 = cs__694.isValid();
            Supplier<String> fn__11169 = () -> "should still be valid";
            test_24.assert_(t_11194, fn__11169);
        } finally {
            test_24.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__1646() {
        Test test_25 = new Test();
        try {
            Map<String, String> params__696 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_11155 = SrcTest.userTable__545();
            SafeIdentifier t_11156 = SrcTest.csid__544("name");
            Changeset cs__697 = SrcGlobal.changeset(t_11155, params__696).cast(List.of(t_11156)).cast(List.of(SrcTest.csid__544("email")));
            boolean t_11163 = !cs__697.getChanges().containsKey("name");
            Supplier<String> fn__11151 = () -> "name must be excluded by second cast";
            test_25.assert_(t_11163, fn__11151);
            boolean t_11166 = cs__697.getChanges().containsKey("email");
            Supplier<String> fn__11150 = () -> "email should be present";
            test_25.assert_(t_11166, fn__11150);
        } finally {
            test_25.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__1647() {
        Test test_26 = new Test();
        try {
            Map<String, String> params__699 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_11137 = SrcTest.userTable__545();
            SafeIdentifier t_11138 = SrcTest.csid__544("name");
            SafeIdentifier t_11139 = SrcTest.csid__544("email");
            Changeset cs__700 = SrcGlobal.changeset(t_11137, params__699).cast(List.of(t_11138, t_11139));
            boolean t_11144 = !cs__700.getChanges().containsKey("name");
            Supplier<String> fn__11133 = () -> "empty name should not be in changes";
            test_26.assert_(t_11144, fn__11133);
            boolean t_11147 = cs__700.getChanges().containsKey("email");
            Supplier<String> fn__11132 = () -> "email should be in changes";
            test_26.assert_(t_11147, fn__11132);
        } finally {
            test_26.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__1648() {
        Test test_27 = new Test();
        try {
            Map<String, String> params__702 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_11119 = SrcTest.userTable__545();
            SafeIdentifier t_11120 = SrcTest.csid__544("name");
            Changeset cs__703 = SrcGlobal.changeset(t_11119, params__702).cast(List.of(t_11120)).validateRequired(List.of(SrcTest.csid__544("name")));
            boolean t_11124 = cs__703.isValid();
            Supplier<String> fn__11116 = () -> "should be valid";
            test_27.assert_(t_11124, fn__11116);
            boolean t_11130 = cs__703.getErrors().size() == 0;
            Supplier<String> fn__11115 = () -> "no errors expected";
            test_27.assert_(t_11130, fn__11115);
        } finally {
            test_27.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__1649() {
        Test test_28 = new Test();
        try {
            Map<String, String> params__705 = Core.mapConstructor(List.of());
            TableDef t_11095 = SrcTest.userTable__545();
            SafeIdentifier t_11096 = SrcTest.csid__544("name");
            Changeset cs__706 = SrcGlobal.changeset(t_11095, params__705).cast(List.of(t_11096)).validateRequired(List.of(SrcTest.csid__544("name")));
            boolean t_11102 = !cs__706.isValid();
            Supplier<String> fn__11093 = () -> "should be invalid";
            test_28.assert_(t_11102, fn__11093);
            boolean t_11107 = cs__706.getErrors().size() == 1;
            Supplier<String> fn__11092 = () -> "should have one error";
            test_28.assert_(t_11107, fn__11092);
            boolean t_11113 = Core.listGet(cs__706.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__11091 = () -> "error should name the field";
            test_28.assert_(t_11113, fn__11091);
        } finally {
            test_28.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__1650() {
        Test test_29 = new Test();
        try {
            Map<String, String> params__708 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_11083 = SrcTest.userTable__545();
            SafeIdentifier t_11084 = SrcTest.csid__544("name");
            Changeset cs__709 = SrcGlobal.changeset(t_11083, params__708).cast(List.of(t_11084)).validateLength(SrcTest.csid__544("name"), 2, 50);
            boolean t_11088 = cs__709.isValid();
            Supplier<String> fn__11080 = () -> "should be valid";
            test_29.assert_(t_11088, fn__11080);
        } finally {
            test_29.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__1651() {
        Test test_30 = new Test();
        try {
            Map<String, String> params__711 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_11071 = SrcTest.userTable__545();
            SafeIdentifier t_11072 = SrcTest.csid__544("name");
            Changeset cs__712 = SrcGlobal.changeset(t_11071, params__711).cast(List.of(t_11072)).validateLength(SrcTest.csid__544("name"), 2, 50);
            boolean t_11078 = !cs__712.isValid();
            Supplier<String> fn__11068 = () -> "should be invalid";
            test_30.assert_(t_11078, fn__11068);
        } finally {
            test_30.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__1652() {
        Test test_31 = new Test();
        try {
            Map<String, String> params__714 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_11059 = SrcTest.userTable__545();
            SafeIdentifier t_11060 = SrcTest.csid__544("name");
            Changeset cs__715 = SrcGlobal.changeset(t_11059, params__714).cast(List.of(t_11060)).validateLength(SrcTest.csid__544("name"), 2, 10);
            boolean t_11066 = !cs__715.isValid();
            Supplier<String> fn__11056 = () -> "should be invalid";
            test_31.assert_(t_11066, fn__11056);
        } finally {
            test_31.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__1653() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__717 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_11048 = SrcTest.userTable__545();
            SafeIdentifier t_11049 = SrcTest.csid__544("age");
            Changeset cs__718 = SrcGlobal.changeset(t_11048, params__717).cast(List.of(t_11049)).validateInt(SrcTest.csid__544("age"));
            boolean t_11053 = cs__718.isValid();
            Supplier<String> fn__11045 = () -> "should be valid";
            test_32.assert_(t_11053, fn__11045);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__1654() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__720 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_11036 = SrcTest.userTable__545();
            SafeIdentifier t_11037 = SrcTest.csid__544("age");
            Changeset cs__721 = SrcGlobal.changeset(t_11036, params__720).cast(List.of(t_11037)).validateInt(SrcTest.csid__544("age"));
            boolean t_11043 = !cs__721.isValid();
            Supplier<String> fn__11033 = () -> "should be invalid";
            test_33.assert_(t_11043, fn__11033);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__1655() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__723 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_11025 = SrcTest.userTable__545();
            SafeIdentifier t_11026 = SrcTest.csid__544("score");
            Changeset cs__724 = SrcGlobal.changeset(t_11025, params__723).cast(List.of(t_11026)).validateFloat(SrcTest.csid__544("score"));
            boolean t_11030 = cs__724.isValid();
            Supplier<String> fn__11022 = () -> "should be valid";
            test_34.assert_(t_11030, fn__11022);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__1656() {
        Test test_35 = new Test();
        try {
            Map<String, String> params__726 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_11014 = SrcTest.userTable__545();
            SafeIdentifier t_11015 = SrcTest.csid__544("age");
            Changeset cs__727 = SrcGlobal.changeset(t_11014, params__726).cast(List.of(t_11015)).validateInt64(SrcTest.csid__544("age"));
            boolean t_11019 = cs__727.isValid();
            Supplier<String> fn__11011 = () -> "should be valid";
            test_35.assert_(t_11019, fn__11011);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__1657() {
        Test test_36 = new Test();
        try {
            Map<String, String> params__729 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_11002 = SrcTest.userTable__545();
            SafeIdentifier t_11003 = SrcTest.csid__544("age");
            Changeset cs__730 = SrcGlobal.changeset(t_11002, params__729).cast(List.of(t_11003)).validateInt64(SrcTest.csid__544("age"));
            boolean t_11009 = !cs__730.isValid();
            Supplier<String> fn__10999 = () -> "should be invalid";
            test_36.assert_(t_11009, fn__10999);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__1658() {
        Test test_37 = new Test();
        try {
            Consumer<String> fn__10996 = v__732 -> {
                Map<String, String> params__733 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__732)));
                TableDef t_10988 = SrcTest.userTable__545();
                SafeIdentifier t_10989 = SrcTest.csid__544("active");
                Changeset cs__734 = SrcGlobal.changeset(t_10988, params__733).cast(List.of(t_10989)).validateBool(SrcTest.csid__544("active"));
                boolean t_10993 = cs__734.isValid();
                Supplier<String> fn__10985 = () -> "should accept: " + v__732;
                test_37.assert_(t_10993, fn__10985);
            };
            List.of("true", "1", "yes", "on").forEach(fn__10996);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__1659() {
        Test test_38 = new Test();
        try {
            Consumer<String> fn__10982 = v__736 -> {
                Map<String, String> params__737 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__736)));
                TableDef t_10974 = SrcTest.userTable__545();
                SafeIdentifier t_10975 = SrcTest.csid__544("active");
                Changeset cs__738 = SrcGlobal.changeset(t_10974, params__737).cast(List.of(t_10975)).validateBool(SrcTest.csid__544("active"));
                boolean t_10979 = cs__738.isValid();
                Supplier<String> fn__10971 = () -> "should accept: " + v__736;
                test_38.assert_(t_10979, fn__10971);
            };
            List.of("false", "0", "no", "off").forEach(fn__10982);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__1660() {
        Test test_39 = new Test();
        try {
            Consumer<String> fn__10968 = v__740 -> {
                Map<String, String> params__741 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__740)));
                TableDef t_10959 = SrcTest.userTable__545();
                SafeIdentifier t_10960 = SrcTest.csid__544("active");
                Changeset cs__742 = SrcGlobal.changeset(t_10959, params__741).cast(List.of(t_10960)).validateBool(SrcTest.csid__544("active"));
                boolean t_10966 = !cs__742.isValid();
                Supplier<String> fn__10956 = () -> "should reject ambiguous: " + v__740;
                test_39.assert_(t_10966, fn__10956);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__10968);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__1661() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__744 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_10944 = SrcTest.userTable__545();
            SafeIdentifier t_10945 = SrcTest.csid__544("name");
            SafeIdentifier t_10946 = SrcTest.csid__544("email");
            Changeset cs__745 = SrcGlobal.changeset(t_10944, params__744).cast(List.of(t_10945, t_10946)).validateRequired(List.of(SrcTest.csid__544("name"), SrcTest.csid__544("email")));
            SqlFragment t_6144;
            t_6144 = cs__745.toInsertSql();
            SqlFragment sqlFrag__746 = t_6144;
            String s__747 = sqlFrag__746.toString();
            boolean t_10953 = s__747.indexOf("''") >= 0;
            Supplier<String> fn__10940 = () -> "single quote must be doubled: " + s__747;
            test_40.assert_(t_10953, fn__10940);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__1662() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__749 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_10924 = SrcTest.userTable__545();
            SafeIdentifier t_10925 = SrcTest.csid__544("name");
            SafeIdentifier t_10926 = SrcTest.csid__544("email");
            Changeset cs__750 = SrcGlobal.changeset(t_10924, params__749).cast(List.of(t_10925, t_10926)).validateRequired(List.of(SrcTest.csid__544("name"), SrcTest.csid__544("email")));
            SqlFragment t_6123;
            t_6123 = cs__750.toInsertSql();
            SqlFragment sqlFrag__751 = t_6123;
            String s__752 = sqlFrag__751.toString();
            boolean t_10933 = s__752.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__10920 = () -> "has INSERT INTO: " + s__752;
            test_41.assert_(t_10933, fn__10920);
            boolean t_10937 = s__752.indexOf("'Alice'") >= 0;
            Supplier<String> fn__10919 = () -> "has quoted name: " + s__752;
            test_41.assert_(t_10937, fn__10919);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__1663() {
        Test test_42 = new Test();
        try {
            Map<String, String> params__754 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_10906 = SrcTest.userTable__545();
            SafeIdentifier t_10907 = SrcTest.csid__544("name");
            SafeIdentifier t_10908 = SrcTest.csid__544("email");
            SafeIdentifier t_10909 = SrcTest.csid__544("age");
            Changeset cs__755 = SrcGlobal.changeset(t_10906, params__754).cast(List.of(t_10907, t_10908, t_10909)).validateRequired(List.of(SrcTest.csid__544("name"), SrcTest.csid__544("email")));
            SqlFragment t_6106;
            t_6106 = cs__755.toInsertSql();
            SqlFragment sqlFrag__756 = t_6106;
            String s__757 = sqlFrag__756.toString();
            boolean t_10916 = s__757.indexOf("25") >= 0;
            Supplier<String> fn__10901 = () -> "age rendered unquoted: " + s__757;
            test_42.assert_(t_10916, fn__10901);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__1664() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__759 = Core.mapConstructor(List.of());
            TableDef t_10894 = SrcTest.userTable__545();
            SafeIdentifier t_10895 = SrcTest.csid__544("name");
            Changeset cs__760 = SrcGlobal.changeset(t_10894, params__759).cast(List.of(t_10895)).validateRequired(List.of(SrcTest.csid__544("name")));
            boolean didBubble__761;
            boolean didBubble_11495;
            try {
                cs__760.toInsertSql();
                didBubble_11495 = false;
            } catch (RuntimeException ignored$4) {
                didBubble_11495 = true;
            }
            didBubble__761 = didBubble_11495;
            Supplier<String> fn__10892 = () -> "invalid changeset should bubble";
            test_43.assert_(didBubble__761, fn__10892);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__1665() {
        Test test_44 = new Test();
        try {
            TableDef strictTable__763 = new TableDef(SrcTest.csid__544("posts"), List.of(new FieldDef(SrcTest.csid__544("title"), new StringField(), false), new FieldDef(SrcTest.csid__544("body"), new StringField(), true)));
            Map<String, String> params__764 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_10885 = SrcTest.csid__544("body");
            Changeset cs__765 = SrcGlobal.changeset(strictTable__763, params__764).cast(List.of(t_10885));
            boolean t_10887 = cs__765.isValid();
            Supplier<String> fn__10874 = () -> "changeset should appear valid (no explicit validation run)";
            test_44.assert_(t_10887, fn__10874);
            boolean didBubble__766;
            boolean didBubble_11496;
            try {
                cs__765.toInsertSql();
                didBubble_11496 = false;
            } catch (RuntimeException ignored$5) {
                didBubble_11496 = true;
            }
            didBubble__766 = didBubble_11496;
            Supplier<String> fn__10873 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_44.assert_(didBubble__766, fn__10873);
        } finally {
            test_44.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__1666() {
        Test test_45 = new Test();
        try {
            Map<String, String> params__768 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_10864 = SrcTest.userTable__545();
            SafeIdentifier t_10865 = SrcTest.csid__544("name");
            Changeset cs__769 = SrcGlobal.changeset(t_10864, params__768).cast(List.of(t_10865)).validateRequired(List.of(SrcTest.csid__544("name")));
            SqlFragment t_6066;
            t_6066 = cs__769.toUpdateSql(42);
            SqlFragment sqlFrag__770 = t_6066;
            String s__771 = sqlFrag__770.toString();
            boolean t_10871 = s__771.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__10861 = () -> "got: " + s__771;
            test_45.assert_(t_10871, fn__10861);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__1667() {
        Test test_46 = new Test();
        try {
            Map<String, String> params__773 = Core.mapConstructor(List.of());
            TableDef t_10854 = SrcTest.userTable__545();
            SafeIdentifier t_10855 = SrcTest.csid__544("name");
            Changeset cs__774 = SrcGlobal.changeset(t_10854, params__773).cast(List.of(t_10855)).validateRequired(List.of(SrcTest.csid__544("name")));
            boolean didBubble__775;
            boolean didBubble_11497;
            try {
                cs__774.toUpdateSql(1);
                didBubble_11497 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_11497 = true;
            }
            didBubble__775 = didBubble_11497;
            Supplier<String> fn__10852 = () -> "invalid changeset should bubble";
            test_46.assert_(didBubble__775, fn__10852);
        } finally {
            test_46.softFailToHard();
        }
    }
    static SafeIdentifier sid__546(String name__1120) {
        SafeIdentifier t_5526;
        t_5526 = SrcGlobal.safeIdentifier(name__1120);
        return t_5526;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__1749() {
        Test test_47 = new Test();
        try {
            Query q__1123 = SrcGlobal.from(SrcTest.sid__546("users"));
            boolean t_10337 = q__1123.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__10332 = () -> "bare query";
            test_47.assert_(t_10337, fn__10332);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__1750() {
        Test test_48 = new Test();
        try {
            SafeIdentifier t_10323 = SrcTest.sid__546("users");
            SafeIdentifier t_10324 = SrcTest.sid__546("id");
            SafeIdentifier t_10325 = SrcTest.sid__546("name");
            Query q__1125 = SrcGlobal.from(t_10323).select(List.of(t_10324, t_10325));
            boolean t_10330 = q__1125.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__10322 = () -> "select columns";
            test_48.assert_(t_10330, fn__10322);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__1751() {
        Test test_49 = new Test();
        try {
            SafeIdentifier t_10311 = SrcTest.sid__546("users");
            SqlBuilder t_10312 = new SqlBuilder();
            t_10312.appendSafe("age > ");
            t_10312.appendInt32(18);
            SqlFragment t_10315 = t_10312.getAccumulated();
            Query q__1127 = SrcGlobal.from(t_10311).where(t_10315);
            boolean t_10320 = q__1127.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__10310 = () -> "where int";
            test_49.assert_(t_10320, fn__10310);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__1753() {
        Test test_50 = new Test();
        try {
            SafeIdentifier t_10299 = SrcTest.sid__546("users");
            SqlBuilder t_10300 = new SqlBuilder();
            t_10300.appendSafe("active = ");
            t_10300.appendBoolean(true);
            SqlFragment t_10303 = t_10300.getAccumulated();
            Query q__1129 = SrcGlobal.from(t_10299).where(t_10303);
            boolean t_10308 = q__1129.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__10298 = () -> "where bool";
            test_50.assert_(t_10308, fn__10298);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__1755() {
        Test test_51 = new Test();
        try {
            SafeIdentifier t_10282 = SrcTest.sid__546("users");
            SqlBuilder t_10283 = new SqlBuilder();
            t_10283.appendSafe("age > ");
            t_10283.appendInt32(18);
            SqlFragment t_10286 = t_10283.getAccumulated();
            Query t_10287 = SrcGlobal.from(t_10282).where(t_10286);
            SqlBuilder t_10288 = new SqlBuilder();
            t_10288.appendSafe("active = ");
            t_10288.appendBoolean(true);
            Query q__1131 = t_10287.where(t_10288.getAccumulated());
            boolean t_10296 = q__1131.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__10281 = () -> "chained where";
            test_51.assert_(t_10296, fn__10281);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__1758() {
        Test test_52 = new Test();
        try {
            SafeIdentifier t_10273 = SrcTest.sid__546("users");
            SafeIdentifier t_10274 = SrcTest.sid__546("name");
            Query q__1133 = SrcGlobal.from(t_10273).orderBy(t_10274, true);
            boolean t_10279 = q__1133.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__10272 = () -> "order asc";
            test_52.assert_(t_10279, fn__10272);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__1759() {
        Test test_53 = new Test();
        try {
            SafeIdentifier t_10264 = SrcTest.sid__546("users");
            SafeIdentifier t_10265 = SrcTest.sid__546("created_at");
            Query q__1135 = SrcGlobal.from(t_10264).orderBy(t_10265, false);
            boolean t_10270 = q__1135.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__10263 = () -> "order desc";
            test_53.assert_(t_10270, fn__10263);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__1760() {
        Test test_54 = new Test();
        try {
            Query t_5460;
            t_5460 = SrcGlobal.from(SrcTest.sid__546("users")).limit(10);
            Query t_5461;
            t_5461 = t_5460.offset(20);
            Query q__1137 = t_5461;
            boolean t_10261 = q__1137.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__10256 = () -> "limit/offset";
            test_54.assert_(t_10261, fn__10256);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__1761() {
        Test test_55 = new Test();
        try {
            boolean didBubble__1139;
            boolean didBubble_11498;
            try {
                SrcGlobal.from(SrcTest.sid__546("users")).limit(-1);
                didBubble_11498 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_11498 = true;
            }
            didBubble__1139 = didBubble_11498;
            Supplier<String> fn__10252 = () -> "negative limit should bubble";
            test_55.assert_(didBubble__1139, fn__10252);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__1762() {
        Test test_56 = new Test();
        try {
            boolean didBubble__1141;
            boolean didBubble_11499;
            try {
                SrcGlobal.from(SrcTest.sid__546("users")).offset(-1);
                didBubble_11499 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_11499 = true;
            }
            didBubble__1141 = didBubble_11499;
            Supplier<String> fn__10248 = () -> "negative offset should bubble";
            test_56.assert_(didBubble__1141, fn__10248);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__1763() {
        Test test_57 = new Test();
        try {
            int minAge__1143 = 21;
            SafeIdentifier t_10226 = SrcTest.sid__546("users");
            SafeIdentifier t_10227 = SrcTest.sid__546("id");
            SafeIdentifier t_10228 = SrcTest.sid__546("name");
            SafeIdentifier t_10229 = SrcTest.sid__546("email");
            Query t_10230 = SrcGlobal.from(t_10226).select(List.of(t_10227, t_10228, t_10229));
            SqlBuilder t_10231 = new SqlBuilder();
            t_10231.appendSafe("age >= ");
            t_10231.appendInt32(21);
            Query t_10235 = t_10230.where(t_10231.getAccumulated());
            SqlBuilder t_10236 = new SqlBuilder();
            t_10236.appendSafe("active = ");
            t_10236.appendBoolean(true);
            Query t_5446;
            t_5446 = t_10235.where(t_10236.getAccumulated()).orderBy(SrcTest.sid__546("name"), true).limit(25);
            Query t_5447;
            t_5447 = t_5446.offset(0);
            Query q__1144 = t_5447;
            boolean t_10246 = q__1144.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__10225 = () -> "complex query";
            test_57.assert_(t_10246, fn__10225);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__1766() {
        Test test_58 = new Test();
        try {
            Query q__1146 = SrcGlobal.from(SrcTest.sid__546("users"));
            SqlFragment t_5423;
            t_5423 = q__1146.safeToSql(100);
            SqlFragment t_5424 = t_5423;
            String s__1147 = t_5424.toString();
            boolean t_10223 = s__1147.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__10219 = () -> "should have limit: " + s__1147;
            test_58.assert_(t_10223, fn__10219);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__1767() {
        Test test_59 = new Test();
        try {
            Query t_5415;
            t_5415 = SrcGlobal.from(SrcTest.sid__546("users")).limit(5);
            Query q__1149 = t_5415;
            SqlFragment t_5418;
            t_5418 = q__1149.safeToSql(100);
            SqlFragment t_5419 = t_5418;
            String s__1150 = t_5419.toString();
            boolean t_10217 = s__1150.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__10213 = () -> "explicit limit preserved: " + s__1150;
            test_59.assert_(t_10217, fn__10213);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__1768() {
        Test test_60 = new Test();
        try {
            boolean didBubble__1152;
            boolean didBubble_11500;
            try {
                SrcGlobal.from(SrcTest.sid__546("users")).safeToSql(-1);
                didBubble_11500 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_11500 = true;
            }
            didBubble__1152 = didBubble_11500;
            Supplier<String> fn__10209 = () -> "negative defaultLimit should bubble";
            test_60.assert_(didBubble__1152, fn__10209);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__1769() {
        Test test_61 = new Test();
        try {
            String evil__1154 = "'; DROP TABLE users; --";
            SafeIdentifier t_10193 = SrcTest.sid__546("users");
            SqlBuilder t_10194 = new SqlBuilder();
            t_10194.appendSafe("name = ");
            t_10194.appendString("'; DROP TABLE users; --");
            SqlFragment t_10197 = t_10194.getAccumulated();
            Query q__1155 = SrcGlobal.from(t_10193).where(t_10197);
            String s__1156 = q__1155.toSql().toString();
            boolean t_10202 = s__1156.indexOf("''") >= 0;
            Supplier<String> fn__10192 = () -> "quotes must be doubled: " + s__1156;
            test_61.assert_(t_10202, fn__10192);
            boolean t_10206 = s__1156.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__10191 = () -> "structure intact: " + s__1156;
            test_61.assert_(t_10206, fn__10191);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__1771() {
        Test test_62 = new Test();
        try {
            String attack__1158 = "users; DROP TABLE users; --";
            boolean didBubble__1159;
            boolean didBubble_11501;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_11501 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_11501 = true;
            }
            didBubble__1159 = didBubble_11501;
            Supplier<String> fn__10188 = () -> "metacharacter-containing name must be rejected at construction";
            test_62.assert_(didBubble__1159, fn__10188);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__1772() {
        Test test_63 = new Test();
        try {
            SafeIdentifier t_10177 = SrcTest.sid__546("users");
            SafeIdentifier t_10178 = SrcTest.sid__546("orders");
            SqlBuilder t_10179 = new SqlBuilder();
            t_10179.appendSafe("users.id = orders.user_id");
            SqlFragment t_10181 = t_10179.getAccumulated();
            Query q__1161 = SrcGlobal.from(t_10177).innerJoin(t_10178, t_10181);
            boolean t_10186 = q__1161.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__10176 = () -> "inner join";
            test_63.assert_(t_10186, fn__10176);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__1774() {
        Test test_64 = new Test();
        try {
            SafeIdentifier t_10165 = SrcTest.sid__546("users");
            SafeIdentifier t_10166 = SrcTest.sid__546("profiles");
            SqlBuilder t_10167 = new SqlBuilder();
            t_10167.appendSafe("users.id = profiles.user_id");
            SqlFragment t_10169 = t_10167.getAccumulated();
            Query q__1163 = SrcGlobal.from(t_10165).leftJoin(t_10166, t_10169);
            boolean t_10174 = q__1163.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__10164 = () -> "left join";
            test_64.assert_(t_10174, fn__10164);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__1776() {
        Test test_65 = new Test();
        try {
            SafeIdentifier t_10153 = SrcTest.sid__546("orders");
            SafeIdentifier t_10154 = SrcTest.sid__546("users");
            SqlBuilder t_10155 = new SqlBuilder();
            t_10155.appendSafe("orders.user_id = users.id");
            SqlFragment t_10157 = t_10155.getAccumulated();
            Query q__1165 = SrcGlobal.from(t_10153).rightJoin(t_10154, t_10157);
            boolean t_10162 = q__1165.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__10152 = () -> "right join";
            test_65.assert_(t_10162, fn__10152);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__1778() {
        Test test_66 = new Test();
        try {
            SafeIdentifier t_10141 = SrcTest.sid__546("users");
            SafeIdentifier t_10142 = SrcTest.sid__546("orders");
            SqlBuilder t_10143 = new SqlBuilder();
            t_10143.appendSafe("users.id = orders.user_id");
            SqlFragment t_10145 = t_10143.getAccumulated();
            Query q__1167 = SrcGlobal.from(t_10141).fullJoin(t_10142, t_10145);
            boolean t_10150 = q__1167.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__10140 = () -> "full join";
            test_66.assert_(t_10150, fn__10140);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__1780() {
        Test test_67 = new Test();
        try {
            SafeIdentifier t_10124 = SrcTest.sid__546("users");
            SafeIdentifier t_10125 = SrcTest.sid__546("orders");
            SqlBuilder t_10126 = new SqlBuilder();
            t_10126.appendSafe("users.id = orders.user_id");
            SqlFragment t_10128 = t_10126.getAccumulated();
            Query t_10129 = SrcGlobal.from(t_10124).innerJoin(t_10125, t_10128);
            SafeIdentifier t_10130 = SrcTest.sid__546("profiles");
            SqlBuilder t_10131 = new SqlBuilder();
            t_10131.appendSafe("users.id = profiles.user_id");
            Query q__1169 = t_10129.leftJoin(t_10130, t_10131.getAccumulated());
            boolean t_10138 = q__1169.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__10123 = () -> "chained joins";
            test_67.assert_(t_10138, fn__10123);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__1783() {
        Test test_68 = new Test();
        try {
            SafeIdentifier t_10105 = SrcTest.sid__546("users");
            SafeIdentifier t_10106 = SrcTest.sid__546("orders");
            SqlBuilder t_10107 = new SqlBuilder();
            t_10107.appendSafe("users.id = orders.user_id");
            SqlFragment t_10109 = t_10107.getAccumulated();
            Query t_10110 = SrcGlobal.from(t_10105).innerJoin(t_10106, t_10109);
            SqlBuilder t_10111 = new SqlBuilder();
            t_10111.appendSafe("orders.total > ");
            t_10111.appendInt32(100);
            Query t_5330;
            t_5330 = t_10110.where(t_10111.getAccumulated()).orderBy(SrcTest.sid__546("name"), true).limit(10);
            Query q__1171 = t_5330;
            boolean t_10121 = q__1171.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__10104 = () -> "join with where/order/limit";
            test_68.assert_(t_10121, fn__10104);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__1786() {
        Test test_69 = new Test();
        try {
            SqlFragment c__1173 = SrcGlobal.col(SrcTest.sid__546("users"), SrcTest.sid__546("id"));
            boolean t_10102 = c__1173.toString().equals("users.id");
            Supplier<String> fn__10096 = () -> "col helper";
            test_69.assert_(t_10102, fn__10096);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__1787() {
        Test test_70 = new Test();
        try {
            SqlFragment onCond__1175 = SrcGlobal.col(SrcTest.sid__546("users"), SrcTest.sid__546("id"));
            SqlBuilder b__1176 = new SqlBuilder();
            b__1176.appendFragment(onCond__1175);
            b__1176.appendSafe(" = ");
            b__1176.appendFragment(SrcGlobal.col(SrcTest.sid__546("orders"), SrcTest.sid__546("user_id")));
            SafeIdentifier t_10087 = SrcTest.sid__546("users");
            SafeIdentifier t_10088 = SrcTest.sid__546("orders");
            SqlFragment t_10089 = b__1176.getAccumulated();
            Query q__1177 = SrcGlobal.from(t_10087).innerJoin(t_10088, t_10089);
            boolean t_10094 = q__1177.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__10076 = () -> "join with col";
            test_70.assert_(t_10094, fn__10076);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orWhereBasic__1788() {
        Test test_71 = new Test();
        try {
            SafeIdentifier t_10065 = SrcTest.sid__546("users");
            SqlBuilder t_10066 = new SqlBuilder();
            t_10066.appendSafe("status = ");
            t_10066.appendString("active");
            SqlFragment t_10069 = t_10066.getAccumulated();
            Query q__1179 = SrcGlobal.from(t_10065).orWhere(t_10069);
            boolean t_10074 = q__1179.toSql().toString().equals("SELECT * FROM users WHERE status = 'active'");
            Supplier<String> fn__10064 = () -> "orWhere basic";
            test_71.assert_(t_10074, fn__10064);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereThenOrWhere__1790() {
        Test test_72 = new Test();
        try {
            SafeIdentifier t_10048 = SrcTest.sid__546("users");
            SqlBuilder t_10049 = new SqlBuilder();
            t_10049.appendSafe("age > ");
            t_10049.appendInt32(18);
            SqlFragment t_10052 = t_10049.getAccumulated();
            Query t_10053 = SrcGlobal.from(t_10048).where(t_10052);
            SqlBuilder t_10054 = new SqlBuilder();
            t_10054.appendSafe("vip = ");
            t_10054.appendBoolean(true);
            Query q__1181 = t_10053.orWhere(t_10054.getAccumulated());
            boolean t_10062 = q__1181.toSql().toString().equals("SELECT * FROM users WHERE age > 18 OR vip = TRUE");
            Supplier<String> fn__10047 = () -> "where then orWhere";
            test_72.assert_(t_10062, fn__10047);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleOrWhere__1793() {
        Test test_73 = new Test();
        try {
            SafeIdentifier t_10026 = SrcTest.sid__546("users");
            SqlBuilder t_10027 = new SqlBuilder();
            t_10027.appendSafe("active = ");
            t_10027.appendBoolean(true);
            SqlFragment t_10030 = t_10027.getAccumulated();
            Query t_10031 = SrcGlobal.from(t_10026).where(t_10030);
            SqlBuilder t_10032 = new SqlBuilder();
            t_10032.appendSafe("role = ");
            t_10032.appendString("admin");
            Query t_10036 = t_10031.orWhere(t_10032.getAccumulated());
            SqlBuilder t_10037 = new SqlBuilder();
            t_10037.appendSafe("role = ");
            t_10037.appendString("moderator");
            Query q__1183 = t_10036.orWhere(t_10037.getAccumulated());
            boolean t_10045 = q__1183.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE OR role = 'admin' OR role = 'moderator'");
            Supplier<String> fn__10025 = () -> "multiple orWhere";
            test_73.assert_(t_10045, fn__10025);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedWhereAndOrWhere__1797() {
        Test test_74 = new Test();
        try {
            SafeIdentifier t_10004 = SrcTest.sid__546("users");
            SqlBuilder t_10005 = new SqlBuilder();
            t_10005.appendSafe("age > ");
            t_10005.appendInt32(18);
            SqlFragment t_10008 = t_10005.getAccumulated();
            Query t_10009 = SrcGlobal.from(t_10004).where(t_10008);
            SqlBuilder t_10010 = new SqlBuilder();
            t_10010.appendSafe("active = ");
            t_10010.appendBoolean(true);
            Query t_10014 = t_10009.where(t_10010.getAccumulated());
            SqlBuilder t_10015 = new SqlBuilder();
            t_10015.appendSafe("vip = ");
            t_10015.appendBoolean(true);
            Query q__1185 = t_10014.orWhere(t_10015.getAccumulated());
            boolean t_10023 = q__1185.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE OR vip = TRUE");
            Supplier<String> fn__10003 = () -> "mixed where and orWhere";
            test_74.assert_(t_10023, fn__10003);
        } finally {
            test_74.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNull__1801() {
        Test test_75 = new Test();
        try {
            SafeIdentifier t_9995 = SrcTest.sid__546("users");
            SafeIdentifier t_9996 = SrcTest.sid__546("deleted_at");
            Query q__1187 = SrcGlobal.from(t_9995).whereNull(t_9996);
            boolean t_10001 = q__1187.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL");
            Supplier<String> fn__9994 = () -> "whereNull";
            test_75.assert_(t_10001, fn__9994);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNull__1802() {
        Test test_76 = new Test();
        try {
            SafeIdentifier t_9986 = SrcTest.sid__546("users");
            SafeIdentifier t_9987 = SrcTest.sid__546("email");
            Query q__1189 = SrcGlobal.from(t_9986).whereNotNull(t_9987);
            boolean t_9992 = q__1189.toSql().toString().equals("SELECT * FROM users WHERE email IS NOT NULL");
            Supplier<String> fn__9985 = () -> "whereNotNull";
            test_76.assert_(t_9992, fn__9985);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNullChainedWithWhere__1803() {
        Test test_77 = new Test();
        try {
            SafeIdentifier t_9972 = SrcTest.sid__546("users");
            SqlBuilder t_9973 = new SqlBuilder();
            t_9973.appendSafe("active = ");
            t_9973.appendBoolean(true);
            SqlFragment t_9976 = t_9973.getAccumulated();
            Query q__1191 = SrcGlobal.from(t_9972).where(t_9976).whereNull(SrcTest.sid__546("deleted_at"));
            boolean t_9983 = q__1191.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND deleted_at IS NULL");
            Supplier<String> fn__9971 = () -> "whereNull chained";
            test_77.assert_(t_9983, fn__9971);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNullChainedWithOrWhere__1805() {
        Test test_78 = new Test();
        try {
            SafeIdentifier t_9958 = SrcTest.sid__546("users");
            SafeIdentifier t_9959 = SrcTest.sid__546("deleted_at");
            Query t_9960 = SrcGlobal.from(t_9958).whereNull(t_9959);
            SqlBuilder t_9961 = new SqlBuilder();
            t_9961.appendSafe("role = ");
            t_9961.appendString("admin");
            Query q__1193 = t_9960.orWhere(t_9961.getAccumulated());
            boolean t_9969 = q__1193.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL OR role = 'admin'");
            Supplier<String> fn__9957 = () -> "whereNotNull with orWhere";
            test_78.assert_(t_9969, fn__9957);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithIntValues__1807() {
        Test test_79 = new Test();
        try {
            SafeIdentifier t_9946 = SrcTest.sid__546("users");
            SafeIdentifier t_9947 = SrcTest.sid__546("id");
            SqlInt32 t_9948 = new SqlInt32(1);
            SqlInt32 t_9949 = new SqlInt32(2);
            SqlInt32 t_9950 = new SqlInt32(3);
            Query q__1195 = SrcGlobal.from(t_9946).whereIn(t_9947, List.of(t_9948, t_9949, t_9950));
            boolean t_9955 = q__1195.toSql().toString().equals("SELECT * FROM users WHERE id IN (1, 2, 3)");
            Supplier<String> fn__9945 = () -> "whereIn ints";
            test_79.assert_(t_9955, fn__9945);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithStringValuesEscaping__1808() {
        Test test_80 = new Test();
        try {
            SafeIdentifier t_9935 = SrcTest.sid__546("users");
            SafeIdentifier t_9936 = SrcTest.sid__546("name");
            SqlString t_9937 = new SqlString("Alice");
            SqlString t_9938 = new SqlString("Bob's");
            Query q__1197 = SrcGlobal.from(t_9935).whereIn(t_9936, List.of(t_9937, t_9938));
            boolean t_9943 = q__1197.toSql().toString().equals("SELECT * FROM users WHERE name IN ('Alice', 'Bob''s')");
            Supplier<String> fn__9934 = () -> "whereIn strings";
            test_80.assert_(t_9943, fn__9934);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithEmptyListProduces1_0__1809() {
        Test test_81 = new Test();
        try {
            SafeIdentifier t_9926 = SrcTest.sid__546("users");
            SafeIdentifier t_9927 = SrcTest.sid__546("id");
            Query q__1199 = SrcGlobal.from(t_9926).whereIn(t_9927, List.of());
            boolean t_9932 = q__1199.toSql().toString().equals("SELECT * FROM users WHERE 1 = 0");
            Supplier<String> fn__9925 = () -> "whereIn empty";
            test_81.assert_(t_9932, fn__9925);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInChained__1810() {
        Test test_82 = new Test();
        try {
            SafeIdentifier t_9910 = SrcTest.sid__546("users");
            SqlBuilder t_9911 = new SqlBuilder();
            t_9911.appendSafe("active = ");
            t_9911.appendBoolean(true);
            SqlFragment t_9914 = t_9911.getAccumulated();
            Query q__1201 = SrcGlobal.from(t_9910).where(t_9914).whereIn(SrcTest.sid__546("role"), List.of(new SqlString("admin"), new SqlString("user")));
            boolean t_9923 = q__1201.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND role IN ('admin', 'user')");
            Supplier<String> fn__9909 = () -> "whereIn chained";
            test_82.assert_(t_9923, fn__9909);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSingleElement__1812() {
        Test test_83 = new Test();
        try {
            SafeIdentifier t_9900 = SrcTest.sid__546("users");
            SafeIdentifier t_9901 = SrcTest.sid__546("id");
            SqlInt32 t_9902 = new SqlInt32(42);
            Query q__1203 = SrcGlobal.from(t_9900).whereIn(t_9901, List.of(t_9902));
            boolean t_9907 = q__1203.toSql().toString().equals("SELECT * FROM users WHERE id IN (42)");
            Supplier<String> fn__9899 = () -> "whereIn single";
            test_83.assert_(t_9907, fn__9899);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotBasic__1813() {
        Test test_84 = new Test();
        try {
            SafeIdentifier t_9888 = SrcTest.sid__546("users");
            SqlBuilder t_9889 = new SqlBuilder();
            t_9889.appendSafe("active = ");
            t_9889.appendBoolean(true);
            SqlFragment t_9892 = t_9889.getAccumulated();
            Query q__1205 = SrcGlobal.from(t_9888).whereNot(t_9892);
            boolean t_9897 = q__1205.toSql().toString().equals("SELECT * FROM users WHERE NOT (active = TRUE)");
            Supplier<String> fn__9887 = () -> "whereNot";
            test_84.assert_(t_9897, fn__9887);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotChained__1815() {
        Test test_85 = new Test();
        try {
            SafeIdentifier t_9871 = SrcTest.sid__546("users");
            SqlBuilder t_9872 = new SqlBuilder();
            t_9872.appendSafe("age > ");
            t_9872.appendInt32(18);
            SqlFragment t_9875 = t_9872.getAccumulated();
            Query t_9876 = SrcGlobal.from(t_9871).where(t_9875);
            SqlBuilder t_9877 = new SqlBuilder();
            t_9877.appendSafe("banned = ");
            t_9877.appendBoolean(true);
            Query q__1207 = t_9876.whereNot(t_9877.getAccumulated());
            boolean t_9885 = q__1207.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND NOT (banned = TRUE)");
            Supplier<String> fn__9870 = () -> "whereNot chained";
            test_85.assert_(t_9885, fn__9870);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenIntegers__1818() {
        Test test_86 = new Test();
        try {
            SafeIdentifier t_9860 = SrcTest.sid__546("users");
            SafeIdentifier t_9861 = SrcTest.sid__546("age");
            SqlInt32 t_9862 = new SqlInt32(18);
            SqlInt32 t_9863 = new SqlInt32(65);
            Query q__1209 = SrcGlobal.from(t_9860).whereBetween(t_9861, t_9862, t_9863);
            boolean t_9868 = q__1209.toSql().toString().equals("SELECT * FROM users WHERE age BETWEEN 18 AND 65");
            Supplier<String> fn__9859 = () -> "whereBetween ints";
            test_86.assert_(t_9868, fn__9859);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenChained__1819() {
        Test test_87 = new Test();
        try {
            SafeIdentifier t_9844 = SrcTest.sid__546("users");
            SqlBuilder t_9845 = new SqlBuilder();
            t_9845.appendSafe("active = ");
            t_9845.appendBoolean(true);
            SqlFragment t_9848 = t_9845.getAccumulated();
            Query q__1211 = SrcGlobal.from(t_9844).where(t_9848).whereBetween(SrcTest.sid__546("age"), new SqlInt32(21), new SqlInt32(30));
            boolean t_9857 = q__1211.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND age BETWEEN 21 AND 30");
            Supplier<String> fn__9843 = () -> "whereBetween chained";
            test_87.assert_(t_9857, fn__9843);
        } finally {
            test_87.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeBasic__1821() {
        Test test_88 = new Test();
        try {
            SafeIdentifier t_9835 = SrcTest.sid__546("users");
            SafeIdentifier t_9836 = SrcTest.sid__546("name");
            Query q__1213 = SrcGlobal.from(t_9835).whereLike(t_9836, "John%");
            boolean t_9841 = q__1213.toSql().toString().equals("SELECT * FROM users WHERE name LIKE 'John%'");
            Supplier<String> fn__9834 = () -> "whereLike";
            test_88.assert_(t_9841, fn__9834);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereIlikeBasic__1822() {
        Test test_89 = new Test();
        try {
            SafeIdentifier t_9826 = SrcTest.sid__546("users");
            SafeIdentifier t_9827 = SrcTest.sid__546("email");
            Query q__1215 = SrcGlobal.from(t_9826).whereILike(t_9827, "%@gmail.com");
            boolean t_9832 = q__1215.toSql().toString().equals("SELECT * FROM users WHERE email ILIKE '%@gmail.com'");
            Supplier<String> fn__9825 = () -> "whereILike";
            test_89.assert_(t_9832, fn__9825);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWithInjectionAttempt__1823() {
        Test test_90 = new Test();
        try {
            SafeIdentifier t_9812 = SrcTest.sid__546("users");
            SafeIdentifier t_9813 = SrcTest.sid__546("name");
            Query q__1217 = SrcGlobal.from(t_9812).whereLike(t_9813, "'; DROP TABLE users; --");
            String s__1218 = q__1217.toSql().toString();
            boolean t_9818 = s__1218.indexOf("''") >= 0;
            Supplier<String> fn__9811 = () -> "like injection escaped: " + s__1218;
            test_90.assert_(t_9818, fn__9811);
            boolean t_9822 = s__1218.indexOf("LIKE") >= 0;
            Supplier<String> fn__9810 = () -> "like structure intact: " + s__1218;
            test_90.assert_(t_9822, fn__9810);
        } finally {
            test_90.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWildcardPatterns__1824() {
        Test test_91 = new Test();
        try {
            SafeIdentifier t_9802 = SrcTest.sid__546("users");
            SafeIdentifier t_9803 = SrcTest.sid__546("name");
            Query q__1220 = SrcGlobal.from(t_9802).whereLike(t_9803, "%son%");
            boolean t_9808 = q__1220.toSql().toString().equals("SELECT * FROM users WHERE name LIKE '%son%'");
            Supplier<String> fn__9801 = () -> "whereLike wildcard";
            test_91.assert_(t_9808, fn__9801);
        } finally {
            test_91.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countAllProducesCount__1825() {
        Test test_92 = new Test();
        try {
            SqlFragment f__1222 = SrcGlobal.countAll();
            boolean t_9799 = f__1222.toString().equals("COUNT(*)");
            Supplier<String> fn__9795 = () -> "countAll";
            test_92.assert_(t_9799, fn__9795);
        } finally {
            test_92.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countColProducesCountField__1826() {
        Test test_93 = new Test();
        try {
            SqlFragment f__1224 = SrcGlobal.countCol(SrcTest.sid__546("id"));
            boolean t_9793 = f__1224.toString().equals("COUNT(id)");
            Supplier<String> fn__9788 = () -> "countCol";
            test_93.assert_(t_9793, fn__9788);
        } finally {
            test_93.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sumColProducesSumField__1827() {
        Test test_94 = new Test();
        try {
            SqlFragment f__1226 = SrcGlobal.sumCol(SrcTest.sid__546("amount"));
            boolean t_9786 = f__1226.toString().equals("SUM(amount)");
            Supplier<String> fn__9781 = () -> "sumCol";
            test_94.assert_(t_9786, fn__9781);
        } finally {
            test_94.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void avgColProducesAvgField__1828() {
        Test test_95 = new Test();
        try {
            SqlFragment f__1228 = SrcGlobal.avgCol(SrcTest.sid__546("price"));
            boolean t_9779 = f__1228.toString().equals("AVG(price)");
            Supplier<String> fn__9774 = () -> "avgCol";
            test_95.assert_(t_9779, fn__9774);
        } finally {
            test_95.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void minColProducesMinField__1829() {
        Test test_96 = new Test();
        try {
            SqlFragment f__1230 = SrcGlobal.minCol(SrcTest.sid__546("created_at"));
            boolean t_9772 = f__1230.toString().equals("MIN(created_at)");
            Supplier<String> fn__9767 = () -> "minCol";
            test_96.assert_(t_9772, fn__9767);
        } finally {
            test_96.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void maxColProducesMaxField__1830() {
        Test test_97 = new Test();
        try {
            SqlFragment f__1232 = SrcGlobal.maxCol(SrcTest.sid__546("score"));
            boolean t_9765 = f__1232.toString().equals("MAX(score)");
            Supplier<String> fn__9760 = () -> "maxCol";
            test_97.assert_(t_9765, fn__9760);
        } finally {
            test_97.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithAggregate__1831() {
        Test test_98 = new Test();
        try {
            SafeIdentifier t_9752 = SrcTest.sid__546("orders");
            SqlFragment t_9753 = SrcGlobal.countAll();
            Query q__1234 = SrcGlobal.from(t_9752).selectExpr(List.of(t_9753));
            boolean t_9758 = q__1234.toSql().toString().equals("SELECT COUNT(*) FROM orders");
            Supplier<String> fn__9751 = () -> "selectExpr count";
            test_98.assert_(t_9758, fn__9751);
        } finally {
            test_98.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithMultipleExpressions__1832() {
        Test test_99 = new Test();
        try {
            SqlFragment nameFrag__1236 = SrcGlobal.col(SrcTest.sid__546("users"), SrcTest.sid__546("name"));
            SafeIdentifier t_9743 = SrcTest.sid__546("users");
            SqlFragment t_9744 = SrcGlobal.countAll();
            Query q__1237 = SrcGlobal.from(t_9743).selectExpr(List.of(nameFrag__1236, t_9744));
            boolean t_9749 = q__1237.toSql().toString().equals("SELECT users.name, COUNT(*) FROM users");
            Supplier<String> fn__9739 = () -> "selectExpr multi";
            test_99.assert_(t_9749, fn__9739);
        } finally {
            test_99.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprOverridesSelectedFields__1833() {
        Test test_100 = new Test();
        try {
            SafeIdentifier t_9728 = SrcTest.sid__546("users");
            SafeIdentifier t_9729 = SrcTest.sid__546("id");
            SafeIdentifier t_9730 = SrcTest.sid__546("name");
            Query q__1239 = SrcGlobal.from(t_9728).select(List.of(t_9729, t_9730)).selectExpr(List.of(SrcGlobal.countAll()));
            boolean t_9737 = q__1239.toSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__9727 = () -> "selectExpr overrides select";
            test_100.assert_(t_9737, fn__9727);
        } finally {
            test_100.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupBySingleField__1834() {
        Test test_101 = new Test();
        try {
            SafeIdentifier t_9714 = SrcTest.sid__546("orders");
            SqlFragment t_9717 = SrcGlobal.col(SrcTest.sid__546("orders"), SrcTest.sid__546("status"));
            SqlFragment t_9718 = SrcGlobal.countAll();
            Query q__1241 = SrcGlobal.from(t_9714).selectExpr(List.of(t_9717, t_9718)).groupBy(SrcTest.sid__546("status"));
            boolean t_9725 = q__1241.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status");
            Supplier<String> fn__9713 = () -> "groupBy single";
            test_101.assert_(t_9725, fn__9713);
        } finally {
            test_101.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupByMultipleFields__1835() {
        Test test_102 = new Test();
        try {
            SafeIdentifier t_9703 = SrcTest.sid__546("orders");
            SafeIdentifier t_9704 = SrcTest.sid__546("status");
            Query q__1243 = SrcGlobal.from(t_9703).groupBy(t_9704).groupBy(SrcTest.sid__546("category"));
            boolean t_9711 = q__1243.toSql().toString().equals("SELECT * FROM orders GROUP BY status, category");
            Supplier<String> fn__9702 = () -> "groupBy multiple";
            test_102.assert_(t_9711, fn__9702);
        } finally {
            test_102.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void havingBasic__1836() {
        Test test_103 = new Test();
        try {
            SafeIdentifier t_9684 = SrcTest.sid__546("orders");
            SqlFragment t_9687 = SrcGlobal.col(SrcTest.sid__546("orders"), SrcTest.sid__546("status"));
            SqlFragment t_9688 = SrcGlobal.countAll();
            Query t_9691 = SrcGlobal.from(t_9684).selectExpr(List.of(t_9687, t_9688)).groupBy(SrcTest.sid__546("status"));
            SqlBuilder t_9692 = new SqlBuilder();
            t_9692.appendSafe("COUNT(*) > ");
            t_9692.appendInt32(5);
            Query q__1245 = t_9691.having(t_9692.getAccumulated());
            boolean t_9700 = q__1245.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status HAVING COUNT(*) > 5");
            Supplier<String> fn__9683 = () -> "having basic";
            test_103.assert_(t_9700, fn__9683);
        } finally {
            test_103.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orHaving__1838() {
        Test test_104 = new Test();
        try {
            SafeIdentifier t_9665 = SrcTest.sid__546("orders");
            SafeIdentifier t_9666 = SrcTest.sid__546("status");
            Query t_9667 = SrcGlobal.from(t_9665).groupBy(t_9666);
            SqlBuilder t_9668 = new SqlBuilder();
            t_9668.appendSafe("COUNT(*) > ");
            t_9668.appendInt32(5);
            Query t_9672 = t_9667.having(t_9668.getAccumulated());
            SqlBuilder t_9673 = new SqlBuilder();
            t_9673.appendSafe("SUM(total) > ");
            t_9673.appendInt32(1000);
            Query q__1247 = t_9672.orHaving(t_9673.getAccumulated());
            boolean t_9681 = q__1247.toSql().toString().equals("SELECT * FROM orders GROUP BY status HAVING COUNT(*) > 5 OR SUM(total) > 1000");
            Supplier<String> fn__9664 = () -> "orHaving";
            test_104.assert_(t_9681, fn__9664);
        } finally {
            test_104.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctBasic__1841() {
        Test test_105 = new Test();
        try {
            SafeIdentifier t_9655 = SrcTest.sid__546("users");
            SafeIdentifier t_9656 = SrcTest.sid__546("name");
            Query q__1249 = SrcGlobal.from(t_9655).select(List.of(t_9656)).distinct();
            boolean t_9662 = q__1249.toSql().toString().equals("SELECT DISTINCT name FROM users");
            Supplier<String> fn__9654 = () -> "distinct";
            test_105.assert_(t_9662, fn__9654);
        } finally {
            test_105.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctWithWhere__1842() {
        Test test_106 = new Test();
        try {
            SafeIdentifier t_9640 = SrcTest.sid__546("users");
            SafeIdentifier t_9641 = SrcTest.sid__546("email");
            Query t_9642 = SrcGlobal.from(t_9640).select(List.of(t_9641));
            SqlBuilder t_9643 = new SqlBuilder();
            t_9643.appendSafe("active = ");
            t_9643.appendBoolean(true);
            Query q__1251 = t_9642.where(t_9643.getAccumulated()).distinct();
            boolean t_9652 = q__1251.toSql().toString().equals("SELECT DISTINCT email FROM users WHERE active = TRUE");
            Supplier<String> fn__9639 = () -> "distinct with where";
            test_106.assert_(t_9652, fn__9639);
        } finally {
            test_106.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlBare__1844() {
        Test test_107 = new Test();
        try {
            Query q__1253 = SrcGlobal.from(SrcTest.sid__546("users"));
            boolean t_9637 = q__1253.countSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__9632 = () -> "countSql bare";
            test_107.assert_(t_9637, fn__9632);
        } finally {
            test_107.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithWhere__1845() {
        Test test_108 = new Test();
        try {
            SafeIdentifier t_9621 = SrcTest.sid__546("users");
            SqlBuilder t_9622 = new SqlBuilder();
            t_9622.appendSafe("active = ");
            t_9622.appendBoolean(true);
            SqlFragment t_9625 = t_9622.getAccumulated();
            Query q__1255 = SrcGlobal.from(t_9621).where(t_9625);
            boolean t_9630 = q__1255.countSql().toString().equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__9620 = () -> "countSql with where";
            test_108.assert_(t_9630, fn__9620);
        } finally {
            test_108.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithJoin__1847() {
        Test test_109 = new Test();
        try {
            SafeIdentifier t_9604 = SrcTest.sid__546("users");
            SafeIdentifier t_9605 = SrcTest.sid__546("orders");
            SqlBuilder t_9606 = new SqlBuilder();
            t_9606.appendSafe("users.id = orders.user_id");
            SqlFragment t_9608 = t_9606.getAccumulated();
            Query t_9609 = SrcGlobal.from(t_9604).innerJoin(t_9605, t_9608);
            SqlBuilder t_9610 = new SqlBuilder();
            t_9610.appendSafe("orders.total > ");
            t_9610.appendInt32(100);
            Query q__1257 = t_9609.where(t_9610.getAccumulated());
            boolean t_9618 = q__1257.countSql().toString().equals("SELECT COUNT(*) FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100");
            Supplier<String> fn__9603 = () -> "countSql with join";
            test_109.assert_(t_9618, fn__9603);
        } finally {
            test_109.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlDropsOrderByLimitOffset__1850() {
        Test test_110 = new Test();
        try {
            SafeIdentifier t_9590 = SrcTest.sid__546("users");
            SqlBuilder t_9591 = new SqlBuilder();
            t_9591.appendSafe("active = ");
            t_9591.appendBoolean(true);
            SqlFragment t_9594 = t_9591.getAccumulated();
            Query t_4906;
            t_4906 = SrcGlobal.from(t_9590).where(t_9594).orderBy(SrcTest.sid__546("name"), true).limit(10);
            Query t_4907;
            t_4907 = t_4906.offset(20);
            Query q__1259 = t_4907;
            String s__1260 = q__1259.countSql().toString();
            boolean t_9601 = s__1260.equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__9589 = () -> "countSql drops extras: " + s__1260;
            test_110.assert_(t_9601, fn__9589);
        } finally {
            test_110.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullAggregationQuery__1852() {
        Test test_111 = new Test();
        try {
            SafeIdentifier t_9557 = SrcTest.sid__546("orders");
            SqlFragment t_9560 = SrcGlobal.col(SrcTest.sid__546("orders"), SrcTest.sid__546("status"));
            SqlFragment t_9561 = SrcGlobal.countAll();
            SqlFragment t_9563 = SrcGlobal.sumCol(SrcTest.sid__546("total"));
            Query t_9564 = SrcGlobal.from(t_9557).selectExpr(List.of(t_9560, t_9561, t_9563));
            SafeIdentifier t_9565 = SrcTest.sid__546("users");
            SqlBuilder t_9566 = new SqlBuilder();
            t_9566.appendSafe("orders.user_id = users.id");
            Query t_9569 = t_9564.innerJoin(t_9565, t_9566.getAccumulated());
            SqlBuilder t_9570 = new SqlBuilder();
            t_9570.appendSafe("users.active = ");
            t_9570.appendBoolean(true);
            Query t_9576 = t_9569.where(t_9570.getAccumulated()).groupBy(SrcTest.sid__546("status"));
            SqlBuilder t_9577 = new SqlBuilder();
            t_9577.appendSafe("COUNT(*) > ");
            t_9577.appendInt32(3);
            Query q__1262 = t_9576.having(t_9577.getAccumulated()).orderBy(SrcTest.sid__546("status"), true);
            String expected__1263 = "SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC";
            boolean t_9587 = q__1262.toSql().toString().equals("SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC");
            Supplier<String> fn__9556 = () -> "full aggregation";
            test_111.assert_(t_9587, fn__9556);
        } finally {
            test_111.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionSql__1856() {
        Test test_112 = new Test();
        try {
            SafeIdentifier t_9539 = SrcTest.sid__546("users");
            SqlBuilder t_9540 = new SqlBuilder();
            t_9540.appendSafe("role = ");
            t_9540.appendString("admin");
            SqlFragment t_9543 = t_9540.getAccumulated();
            Query a__1265 = SrcGlobal.from(t_9539).where(t_9543);
            SafeIdentifier t_9545 = SrcTest.sid__546("users");
            SqlBuilder t_9546 = new SqlBuilder();
            t_9546.appendSafe("role = ");
            t_9546.appendString("moderator");
            SqlFragment t_9549 = t_9546.getAccumulated();
            Query b__1266 = SrcGlobal.from(t_9545).where(t_9549);
            String s__1267 = SrcGlobal.unionSql(a__1265, b__1266).toString();
            boolean t_9554 = s__1267.equals("(SELECT * FROM users WHERE role = 'admin') UNION (SELECT * FROM users WHERE role = 'moderator')");
            Supplier<String> fn__9538 = () -> "unionSql: " + s__1267;
            test_112.assert_(t_9554, fn__9538);
        } finally {
            test_112.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionAllSql__1859() {
        Test test_113 = new Test();
        try {
            SafeIdentifier t_9527 = SrcTest.sid__546("users");
            SafeIdentifier t_9528 = SrcTest.sid__546("name");
            Query a__1269 = SrcGlobal.from(t_9527).select(List.of(t_9528));
            SafeIdentifier t_9530 = SrcTest.sid__546("contacts");
            SafeIdentifier t_9531 = SrcTest.sid__546("name");
            Query b__1270 = SrcGlobal.from(t_9530).select(List.of(t_9531));
            String s__1271 = SrcGlobal.unionAllSql(a__1269, b__1270).toString();
            boolean t_9536 = s__1271.equals("(SELECT name FROM users) UNION ALL (SELECT name FROM contacts)");
            Supplier<String> fn__9526 = () -> "unionAllSql: " + s__1271;
            test_113.assert_(t_9536, fn__9526);
        } finally {
            test_113.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void intersectSql__1860() {
        Test test_114 = new Test();
        try {
            SafeIdentifier t_9515 = SrcTest.sid__546("users");
            SafeIdentifier t_9516 = SrcTest.sid__546("email");
            Query a__1273 = SrcGlobal.from(t_9515).select(List.of(t_9516));
            SafeIdentifier t_9518 = SrcTest.sid__546("subscribers");
            SafeIdentifier t_9519 = SrcTest.sid__546("email");
            Query b__1274 = SrcGlobal.from(t_9518).select(List.of(t_9519));
            String s__1275 = SrcGlobal.intersectSql(a__1273, b__1274).toString();
            boolean t_9524 = s__1275.equals("(SELECT email FROM users) INTERSECT (SELECT email FROM subscribers)");
            Supplier<String> fn__9514 = () -> "intersectSql: " + s__1275;
            test_114.assert_(t_9524, fn__9514);
        } finally {
            test_114.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void exceptSql__1861() {
        Test test_115 = new Test();
        try {
            SafeIdentifier t_9503 = SrcTest.sid__546("users");
            SafeIdentifier t_9504 = SrcTest.sid__546("id");
            Query a__1277 = SrcGlobal.from(t_9503).select(List.of(t_9504));
            SafeIdentifier t_9506 = SrcTest.sid__546("banned");
            SafeIdentifier t_9507 = SrcTest.sid__546("id");
            Query b__1278 = SrcGlobal.from(t_9506).select(List.of(t_9507));
            String s__1279 = SrcGlobal.exceptSql(a__1277, b__1278).toString();
            boolean t_9512 = s__1279.equals("(SELECT id FROM users) EXCEPT (SELECT id FROM banned)");
            Supplier<String> fn__9502 = () -> "exceptSql: " + s__1279;
            test_115.assert_(t_9512, fn__9502);
        } finally {
            test_115.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void subqueryWithAlias__1862() {
        Test test_116 = new Test();
        try {
            SafeIdentifier t_9488 = SrcTest.sid__546("orders");
            SafeIdentifier t_9489 = SrcTest.sid__546("user_id");
            Query t_9490 = SrcGlobal.from(t_9488).select(List.of(t_9489));
            SqlBuilder t_9491 = new SqlBuilder();
            t_9491.appendSafe("total > ");
            t_9491.appendInt32(100);
            Query inner__1281 = t_9490.where(t_9491.getAccumulated());
            String s__1282 = SrcGlobal.subquery(inner__1281, SrcTest.sid__546("big_orders")).toString();
            boolean t_9500 = s__1282.equals("(SELECT user_id FROM orders WHERE total > 100) AS big_orders");
            Supplier<String> fn__9487 = () -> "subquery: " + s__1282;
            test_116.assert_(t_9500, fn__9487);
        } finally {
            test_116.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSql__1864() {
        Test test_117 = new Test();
        try {
            SafeIdentifier t_9477 = SrcTest.sid__546("orders");
            SqlBuilder t_9478 = new SqlBuilder();
            t_9478.appendSafe("orders.user_id = users.id");
            SqlFragment t_9480 = t_9478.getAccumulated();
            Query inner__1284 = SrcGlobal.from(t_9477).where(t_9480);
            String s__1285 = SrcGlobal.existsSql(inner__1284).toString();
            boolean t_9485 = s__1285.equals("EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__9476 = () -> "existsSql: " + s__1285;
            test_117.assert_(t_9485, fn__9476);
        } finally {
            test_117.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubquery__1866() {
        Test test_118 = new Test();
        try {
            SafeIdentifier t_9460 = SrcTest.sid__546("orders");
            SafeIdentifier t_9461 = SrcTest.sid__546("user_id");
            Query t_9462 = SrcGlobal.from(t_9460).select(List.of(t_9461));
            SqlBuilder t_9463 = new SqlBuilder();
            t_9463.appendSafe("total > ");
            t_9463.appendInt32(1000);
            Query sub__1287 = t_9462.where(t_9463.getAccumulated());
            SafeIdentifier t_9468 = SrcTest.sid__546("users");
            SafeIdentifier t_9469 = SrcTest.sid__546("id");
            Query q__1288 = SrcGlobal.from(t_9468).whereInSubquery(t_9469, sub__1287);
            String s__1289 = q__1288.toSql().toString();
            boolean t_9474 = s__1289.equals("SELECT * FROM users WHERE id IN (SELECT user_id FROM orders WHERE total > 1000)");
            Supplier<String> fn__9459 = () -> "whereInSubquery: " + s__1289;
            test_118.assert_(t_9474, fn__9459);
        } finally {
            test_118.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void setOperationWithWhereOnEachSide__1868() {
        Test test_119 = new Test();
        try {
            SafeIdentifier t_9437 = SrcTest.sid__546("users");
            SqlBuilder t_9438 = new SqlBuilder();
            t_9438.appendSafe("age > ");
            t_9438.appendInt32(18);
            SqlFragment t_9441 = t_9438.getAccumulated();
            Query t_9442 = SrcGlobal.from(t_9437).where(t_9441);
            SqlBuilder t_9443 = new SqlBuilder();
            t_9443.appendSafe("active = ");
            t_9443.appendBoolean(true);
            Query a__1291 = t_9442.where(t_9443.getAccumulated());
            SafeIdentifier t_9448 = SrcTest.sid__546("users");
            SqlBuilder t_9449 = new SqlBuilder();
            t_9449.appendSafe("role = ");
            t_9449.appendString("vip");
            SqlFragment t_9452 = t_9449.getAccumulated();
            Query b__1292 = SrcGlobal.from(t_9448).where(t_9452);
            String s__1293 = SrcGlobal.unionSql(a__1291, b__1292).toString();
            boolean t_9457 = s__1293.equals("(SELECT * FROM users WHERE age > 18 AND active = TRUE) UNION (SELECT * FROM users WHERE role = 'vip')");
            Supplier<String> fn__9436 = () -> "union with where: " + s__1293;
            test_119.assert_(t_9457, fn__9436);
        } finally {
            test_119.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubqueryChainedWithWhere__1872() {
        Test test_120 = new Test();
        try {
            SafeIdentifier t_9420 = SrcTest.sid__546("orders");
            SafeIdentifier t_9421 = SrcTest.sid__546("user_id");
            Query sub__1295 = SrcGlobal.from(t_9420).select(List.of(t_9421));
            SafeIdentifier t_9423 = SrcTest.sid__546("users");
            SqlBuilder t_9424 = new SqlBuilder();
            t_9424.appendSafe("active = ");
            t_9424.appendBoolean(true);
            SqlFragment t_9427 = t_9424.getAccumulated();
            Query q__1296 = SrcGlobal.from(t_9423).where(t_9427).whereInSubquery(SrcTest.sid__546("id"), sub__1295);
            String s__1297 = q__1296.toSql().toString();
            boolean t_9434 = s__1297.equals("SELECT * FROM users WHERE active = TRUE AND id IN (SELECT user_id FROM orders)");
            Supplier<String> fn__9419 = () -> "whereInSubquery chained: " + s__1297;
            test_120.assert_(t_9434, fn__9419);
        } finally {
            test_120.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSqlUsedInWhere__1874() {
        Test test_121 = new Test();
        try {
            SafeIdentifier t_9406 = SrcTest.sid__546("orders");
            SqlBuilder t_9407 = new SqlBuilder();
            t_9407.appendSafe("orders.user_id = users.id");
            SqlFragment t_9409 = t_9407.getAccumulated();
            Query sub__1299 = SrcGlobal.from(t_9406).where(t_9409);
            SafeIdentifier t_9411 = SrcTest.sid__546("users");
            SqlFragment t_9412 = SrcGlobal.existsSql(sub__1299);
            Query q__1300 = SrcGlobal.from(t_9411).where(t_9412);
            String s__1301 = q__1300.toSql().toString();
            boolean t_9417 = s__1301.equals("SELECT * FROM users WHERE EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__9405 = () -> "exists in where: " + s__1301;
            test_121.assert_(t_9417, fn__9405);
        } finally {
            test_121.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBasic__1876() {
        Test test_122 = new Test();
        try {
            SafeIdentifier t_9392 = SrcTest.sid__546("users");
            SafeIdentifier t_9393 = SrcTest.sid__546("name");
            SqlString t_9394 = new SqlString("Alice");
            UpdateQuery t_9395 = SrcGlobal.update(t_9392).set(t_9393, t_9394);
            SqlBuilder t_9396 = new SqlBuilder();
            t_9396.appendSafe("id = ");
            t_9396.appendInt32(1);
            SqlFragment t_4728;
            t_4728 = t_9395.where(t_9396.getAccumulated()).toSql();
            SqlFragment q__1303 = t_4728;
            boolean t_9403 = q__1303.toString().equals("UPDATE users SET name = 'Alice' WHERE id = 1");
            Supplier<String> fn__9391 = () -> "update basic";
            test_122.assert_(t_9403, fn__9391);
        } finally {
            test_122.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleSet__1878() {
        Test test_123 = new Test();
        try {
            SafeIdentifier t_9375 = SrcTest.sid__546("users");
            SafeIdentifier t_9376 = SrcTest.sid__546("name");
            SqlString t_9377 = new SqlString("Bob");
            UpdateQuery t_9381 = SrcGlobal.update(t_9375).set(t_9376, t_9377).set(SrcTest.sid__546("age"), new SqlInt32(30));
            SqlBuilder t_9382 = new SqlBuilder();
            t_9382.appendSafe("id = ");
            t_9382.appendInt32(2);
            SqlFragment t_4713;
            t_4713 = t_9381.where(t_9382.getAccumulated()).toSql();
            SqlFragment q__1305 = t_4713;
            boolean t_9389 = q__1305.toString().equals("UPDATE users SET name = 'Bob', age = 30 WHERE id = 2");
            Supplier<String> fn__9374 = () -> "update multi set";
            test_123.assert_(t_9389, fn__9374);
        } finally {
            test_123.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleWhere__1880() {
        Test test_124 = new Test();
        try {
            SafeIdentifier t_9356 = SrcTest.sid__546("users");
            SafeIdentifier t_9357 = SrcTest.sid__546("active");
            SqlBoolean t_9358 = new SqlBoolean(false);
            UpdateQuery t_9359 = SrcGlobal.update(t_9356).set(t_9357, t_9358);
            SqlBuilder t_9360 = new SqlBuilder();
            t_9360.appendSafe("age < ");
            t_9360.appendInt32(18);
            UpdateQuery t_9364 = t_9359.where(t_9360.getAccumulated());
            SqlBuilder t_9365 = new SqlBuilder();
            t_9365.appendSafe("role = ");
            t_9365.appendString("guest");
            SqlFragment t_4695;
            t_4695 = t_9364.where(t_9365.getAccumulated()).toSql();
            SqlFragment q__1307 = t_4695;
            boolean t_9372 = q__1307.toString().equals("UPDATE users SET active = FALSE WHERE age < 18 AND role = 'guest'");
            Supplier<String> fn__9355 = () -> "update multi where";
            test_124.assert_(t_9372, fn__9355);
        } finally {
            test_124.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryOrWhere__1883() {
        Test test_125 = new Test();
        try {
            SafeIdentifier t_9337 = SrcTest.sid__546("users");
            SafeIdentifier t_9338 = SrcTest.sid__546("status");
            SqlString t_9339 = new SqlString("banned");
            UpdateQuery t_9340 = SrcGlobal.update(t_9337).set(t_9338, t_9339);
            SqlBuilder t_9341 = new SqlBuilder();
            t_9341.appendSafe("spam_count > ");
            t_9341.appendInt32(10);
            UpdateQuery t_9345 = t_9340.where(t_9341.getAccumulated());
            SqlBuilder t_9346 = new SqlBuilder();
            t_9346.appendSafe("reported = ");
            t_9346.appendBoolean(true);
            SqlFragment t_4674;
            t_4674 = t_9345.orWhere(t_9346.getAccumulated()).toSql();
            SqlFragment q__1309 = t_4674;
            boolean t_9353 = q__1309.toString().equals("UPDATE users SET status = 'banned' WHERE spam_count > 10 OR reported = TRUE");
            Supplier<String> fn__9336 = () -> "update orWhere";
            test_125.assert_(t_9353, fn__9336);
        } finally {
            test_125.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutWhere__1886() {
        Test test_126 = new Test();
        try {
            SafeIdentifier t_9330;
            SafeIdentifier t_9331;
            SqlInt32 t_9332;
            boolean didBubble__1311;
            boolean didBubble_11502;
            try {
                t_9330 = SrcTest.sid__546("users");
                t_9331 = SrcTest.sid__546("x");
                t_9332 = new SqlInt32(1);
                SrcGlobal.update(t_9330).set(t_9331, t_9332).toSql();
                didBubble_11502 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_11502 = true;
            }
            didBubble__1311 = didBubble_11502;
            Supplier<String> fn__9329 = () -> "update without WHERE should bubble";
            test_126.assert_(didBubble__1311, fn__9329);
        } finally {
            test_126.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutSet__1887() {
        Test test_127 = new Test();
        try {
            SafeIdentifier t_9321;
            SqlBuilder t_9322;
            SqlFragment t_9325;
            boolean didBubble__1313;
            boolean didBubble_11503;
            try {
                t_9321 = SrcTest.sid__546("users");
                t_9322 = new SqlBuilder();
                t_9322.appendSafe("id = ");
                t_9322.appendInt32(1);
                t_9325 = t_9322.getAccumulated();
                SrcGlobal.update(t_9321).where(t_9325).toSql();
                didBubble_11503 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_11503 = true;
            }
            didBubble__1313 = didBubble_11503;
            Supplier<String> fn__9320 = () -> "update without SET should bubble";
            test_127.assert_(didBubble__1313, fn__9320);
        } finally {
            test_127.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryWithLimit__1889() {
        Test test_128 = new Test();
        try {
            SafeIdentifier t_9307 = SrcTest.sid__546("users");
            SafeIdentifier t_9308 = SrcTest.sid__546("active");
            SqlBoolean t_9309 = new SqlBoolean(false);
            UpdateQuery t_9310 = SrcGlobal.update(t_9307).set(t_9308, t_9309);
            SqlBuilder t_9311 = new SqlBuilder();
            t_9311.appendSafe("last_login < ");
            t_9311.appendString("2024-01-01");
            UpdateQuery t_4637;
            t_4637 = t_9310.where(t_9311.getAccumulated()).limit(100);
            SqlFragment t_4638;
            t_4638 = t_4637.toSql();
            SqlFragment q__1315 = t_4638;
            boolean t_9318 = q__1315.toString().equals("UPDATE users SET active = FALSE WHERE last_login < '2024-01-01' LIMIT 100");
            Supplier<String> fn__9306 = () -> "update limit";
            test_128.assert_(t_9318, fn__9306);
        } finally {
            test_128.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryEscaping__1891() {
        Test test_129 = new Test();
        try {
            SafeIdentifier t_9293 = SrcTest.sid__546("users");
            SafeIdentifier t_9294 = SrcTest.sid__546("bio");
            SqlString t_9295 = new SqlString("It's a test");
            UpdateQuery t_9296 = SrcGlobal.update(t_9293).set(t_9294, t_9295);
            SqlBuilder t_9297 = new SqlBuilder();
            t_9297.appendSafe("id = ");
            t_9297.appendInt32(1);
            SqlFragment t_4622;
            t_4622 = t_9296.where(t_9297.getAccumulated()).toSql();
            SqlFragment q__1317 = t_4622;
            boolean t_9304 = q__1317.toString().equals("UPDATE users SET bio = 'It''s a test' WHERE id = 1");
            Supplier<String> fn__9292 = () -> "update escaping";
            test_129.assert_(t_9304, fn__9292);
        } finally {
            test_129.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBasic__1893() {
        Test test_130 = new Test();
        try {
            SafeIdentifier t_9282 = SrcTest.sid__546("users");
            SqlBuilder t_9283 = new SqlBuilder();
            t_9283.appendSafe("id = ");
            t_9283.appendInt32(1);
            SqlFragment t_9286 = t_9283.getAccumulated();
            SqlFragment t_4607;
            t_4607 = SrcGlobal.deleteFrom(t_9282).where(t_9286).toSql();
            SqlFragment q__1319 = t_4607;
            boolean t_9290 = q__1319.toString().equals("DELETE FROM users WHERE id = 1");
            Supplier<String> fn__9281 = () -> "delete basic";
            test_130.assert_(t_9290, fn__9281);
        } finally {
            test_130.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryMultipleWhere__1895() {
        Test test_131 = new Test();
        try {
            SafeIdentifier t_9266 = SrcTest.sid__546("logs");
            SqlBuilder t_9267 = new SqlBuilder();
            t_9267.appendSafe("created_at < ");
            t_9267.appendString("2024-01-01");
            SqlFragment t_9270 = t_9267.getAccumulated();
            DeleteQuery t_9271 = SrcGlobal.deleteFrom(t_9266).where(t_9270);
            SqlBuilder t_9272 = new SqlBuilder();
            t_9272.appendSafe("level = ");
            t_9272.appendString("debug");
            SqlFragment t_4595;
            t_4595 = t_9271.where(t_9272.getAccumulated()).toSql();
            SqlFragment q__1321 = t_4595;
            boolean t_9279 = q__1321.toString().equals("DELETE FROM logs WHERE created_at < '2024-01-01' AND level = 'debug'");
            Supplier<String> fn__9265 = () -> "delete multi where";
            test_131.assert_(t_9279, fn__9265);
        } finally {
            test_131.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBubblesWithoutWhere__1898() {
        Test test_132 = new Test();
        try {
            boolean didBubble__1323;
            boolean didBubble_11504;
            try {
                SrcGlobal.deleteFrom(SrcTest.sid__546("users")).toSql();
                didBubble_11504 = false;
            } catch (RuntimeException ignored$13) {
                didBubble_11504 = true;
            }
            didBubble__1323 = didBubble_11504;
            Supplier<String> fn__9261 = () -> "delete without WHERE should bubble";
            test_132.assert_(didBubble__1323, fn__9261);
        } finally {
            test_132.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryOrWhere__1899() {
        Test test_133 = new Test();
        try {
            SafeIdentifier t_9246 = SrcTest.sid__546("sessions");
            SqlBuilder t_9247 = new SqlBuilder();
            t_9247.appendSafe("expired = ");
            t_9247.appendBoolean(true);
            SqlFragment t_9250 = t_9247.getAccumulated();
            DeleteQuery t_9251 = SrcGlobal.deleteFrom(t_9246).where(t_9250);
            SqlBuilder t_9252 = new SqlBuilder();
            t_9252.appendSafe("created_at < ");
            t_9252.appendString("2023-01-01");
            SqlFragment t_4574;
            t_4574 = t_9251.orWhere(t_9252.getAccumulated()).toSql();
            SqlFragment q__1325 = t_4574;
            boolean t_9259 = q__1325.toString().equals("DELETE FROM sessions WHERE expired = TRUE OR created_at < '2023-01-01'");
            Supplier<String> fn__9245 = () -> "delete orWhere";
            test_133.assert_(t_9259, fn__9245);
        } finally {
            test_133.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryWithLimit__1902() {
        Test test_134 = new Test();
        try {
            SafeIdentifier t_9235 = SrcTest.sid__546("logs");
            SqlBuilder t_9236 = new SqlBuilder();
            t_9236.appendSafe("level = ");
            t_9236.appendString("debug");
            SqlFragment t_9239 = t_9236.getAccumulated();
            DeleteQuery t_4555;
            t_4555 = SrcGlobal.deleteFrom(t_9235).where(t_9239).limit(1000);
            SqlFragment t_4556;
            t_4556 = t_4555.toSql();
            SqlFragment q__1327 = t_4556;
            boolean t_9243 = q__1327.toString().equals("DELETE FROM logs WHERE level = 'debug' LIMIT 1000");
            Supplier<String> fn__9234 = () -> "delete limit";
            test_134.assert_(t_9243, fn__9234);
        } finally {
            test_134.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByNullsNullsFirst__1904() {
        Test test_135 = new Test();
        try {
            SafeIdentifier t_9225 = SrcTest.sid__546("users");
            SafeIdentifier t_9226 = SrcTest.sid__546("email");
            NullsFirst t_9227 = new NullsFirst();
            Query q__1329 = SrcGlobal.from(t_9225).orderByNulls(t_9226, true, t_9227);
            boolean t_9232 = q__1329.toSql().toString().equals("SELECT * FROM users ORDER BY email ASC NULLS FIRST");
            Supplier<String> fn__9224 = () -> "nulls first";
            test_135.assert_(t_9232, fn__9224);
        } finally {
            test_135.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByNullsNullsLast__1905() {
        Test test_136 = new Test();
        try {
            SafeIdentifier t_9215 = SrcTest.sid__546("users");
            SafeIdentifier t_9216 = SrcTest.sid__546("score");
            NullsLast t_9217 = new NullsLast();
            Query q__1331 = SrcGlobal.from(t_9215).orderByNulls(t_9216, false, t_9217);
            boolean t_9222 = q__1331.toSql().toString().equals("SELECT * FROM users ORDER BY score DESC NULLS LAST");
            Supplier<String> fn__9214 = () -> "nulls last";
            test_136.assert_(t_9222, fn__9214);
        } finally {
            test_136.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedOrderByAndOrderByNulls__1906() {
        Test test_137 = new Test();
        try {
            SafeIdentifier t_9203 = SrcTest.sid__546("users");
            SafeIdentifier t_9204 = SrcTest.sid__546("name");
            Query q__1333 = SrcGlobal.from(t_9203).orderBy(t_9204, true).orderByNulls(SrcTest.sid__546("email"), true, new NullsFirst());
            boolean t_9212 = q__1333.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC, email ASC NULLS FIRST");
            Supplier<String> fn__9202 = () -> "mixed order";
            test_137.assert_(t_9212, fn__9202);
        } finally {
            test_137.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void crossJoin__1907() {
        Test test_138 = new Test();
        try {
            SafeIdentifier t_9194 = SrcTest.sid__546("users");
            SafeIdentifier t_9195 = SrcTest.sid__546("colors");
            Query q__1335 = SrcGlobal.from(t_9194).crossJoin(t_9195);
            boolean t_9200 = q__1335.toSql().toString().equals("SELECT * FROM users CROSS JOIN colors");
            Supplier<String> fn__9193 = () -> "cross join";
            test_138.assert_(t_9200, fn__9193);
        } finally {
            test_138.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void crossJoinCombinedWithOtherJoins__1908() {
        Test test_139 = new Test();
        try {
            SafeIdentifier t_9180 = SrcTest.sid__546("users");
            SafeIdentifier t_9181 = SrcTest.sid__546("orders");
            SqlBuilder t_9182 = new SqlBuilder();
            t_9182.appendSafe("users.id = orders.user_id");
            SqlFragment t_9184 = t_9182.getAccumulated();
            Query q__1337 = SrcGlobal.from(t_9180).innerJoin(t_9181, t_9184).crossJoin(SrcTest.sid__546("colors"));
            boolean t_9191 = q__1337.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id CROSS JOIN colors");
            Supplier<String> fn__9179 = () -> "cross + inner join";
            test_139.assert_(t_9191, fn__9179);
        } finally {
            test_139.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockForUpdate__1910() {
        Test test_140 = new Test();
        try {
            SafeIdentifier t_9166 = SrcTest.sid__546("users");
            SqlBuilder t_9167 = new SqlBuilder();
            t_9167.appendSafe("id = ");
            t_9167.appendInt32(1);
            SqlFragment t_9170 = t_9167.getAccumulated();
            Query q__1339 = SrcGlobal.from(t_9166).where(t_9170).lock(new ForUpdate());
            boolean t_9177 = q__1339.toSql().toString().equals("SELECT * FROM users WHERE id = 1 FOR UPDATE");
            Supplier<String> fn__9165 = () -> "for update";
            test_140.assert_(t_9177, fn__9165);
        } finally {
            test_140.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockForShare__1912() {
        Test test_141 = new Test();
        try {
            SafeIdentifier t_9155 = SrcTest.sid__546("users");
            SafeIdentifier t_9156 = SrcTest.sid__546("name");
            Query q__1341 = SrcGlobal.from(t_9155).select(List.of(t_9156)).lock(new ForShare());
            boolean t_9163 = q__1341.toSql().toString().equals("SELECT name FROM users FOR SHARE");
            Supplier<String> fn__9154 = () -> "for share";
            test_141.assert_(t_9163, fn__9154);
        } finally {
            test_141.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockWithFullQuery__1913() {
        Test test_142 = new Test();
        try {
            SafeIdentifier t_9141 = SrcTest.sid__546("accounts");
            SqlBuilder t_9142 = new SqlBuilder();
            t_9142.appendSafe("id = ");
            t_9142.appendInt32(42);
            SqlFragment t_9145 = t_9142.getAccumulated();
            Query t_4479;
            t_4479 = SrcGlobal.from(t_9141).where(t_9145).limit(1);
            Query t_9148 = t_4479.lock(new ForUpdate());
            Query q__1343 = t_9148;
            boolean t_9152 = q__1343.toSql().toString().equals("SELECT * FROM accounts WHERE id = 42 LIMIT 1 FOR UPDATE");
            Supplier<String> fn__9140 = () -> "lock full query";
            test_142.assert_(t_9152, fn__9140);
        } finally {
            test_142.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__1915() {
        Test test_149 = new Test();
        try {
            SafeIdentifier t_4468;
            t_4468 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__1381 = t_4468;
            boolean t_9138 = id__1381.getSqlValue().equals("user_name");
            Supplier<String> fn__9135 = () -> "value should round-trip";
            test_149.assert_(t_9138, fn__9135);
        } finally {
            test_149.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__1916() {
        Test test_150 = new Test();
        try {
            boolean didBubble__1383;
            boolean didBubble_11505;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_11505 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_11505 = true;
            }
            didBubble__1383 = didBubble_11505;
            Supplier<String> fn__9132 = () -> "empty string should bubble";
            test_150.assert_(didBubble__1383, fn__9132);
        } finally {
            test_150.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__1917() {
        Test test_151 = new Test();
        try {
            boolean didBubble__1385;
            boolean didBubble_11506;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_11506 = false;
            } catch (RuntimeException ignored$15) {
                didBubble_11506 = true;
            }
            didBubble__1385 = didBubble_11506;
            Supplier<String> fn__9129 = () -> "leading digit should bubble";
            test_151.assert_(didBubble__1385, fn__9129);
        } finally {
            test_151.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__1918() {
        Test test_152 = new Test();
        try {
            List<String> cases__1387 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__9126 = c__1388 -> {
                boolean didBubble__1389;
                boolean didBubble_11507;
                try {
                    SrcGlobal.safeIdentifier(c__1388);
                    didBubble_11507 = false;
                } catch (RuntimeException ignored$16) {
                    didBubble_11507 = true;
                }
                didBubble__1389 = didBubble_11507;
                Supplier<String> fn__9123 = () -> "should reject: " + c__1388;
                test_152.assert_(didBubble__1389, fn__9123);
            };
            cases__1387.forEach(fn__9126);
        } finally {
            test_152.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__1919() {
        Test test_153 = new Test();
        try {
            SafeIdentifier t_4445;
            t_4445 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_4446 = t_4445;
            SafeIdentifier t_4447;
            t_4447 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_4448 = t_4447;
            StringField t_9113 = new StringField();
            FieldDef t_9114 = new FieldDef(t_4448, t_9113, false);
            SafeIdentifier t_4451;
            t_4451 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_4452 = t_4451;
            IntField t_9115 = new IntField();
            FieldDef t_9116 = new FieldDef(t_4452, t_9115, false);
            TableDef td__1391 = new TableDef(t_4446, List.of(t_9114, t_9116));
            FieldDef t_4456;
            t_4456 = td__1391.field("age");
            FieldDef f__1392 = t_4456;
            boolean t_9121 = f__1392.getName().getSqlValue().equals("age");
            Supplier<String> fn__9112 = () -> "should find age field";
            test_153.assert_(t_9121, fn__9112);
        } finally {
            test_153.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__1920() {
        Test test_154 = new Test();
        try {
            SafeIdentifier t_4436;
            t_4436 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_4437 = t_4436;
            SafeIdentifier t_4438;
            t_4438 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_4439 = t_4438;
            StringField t_9107 = new StringField();
            FieldDef t_9108 = new FieldDef(t_4439, t_9107, false);
            TableDef td__1394 = new TableDef(t_4437, List.of(t_9108));
            boolean didBubble__1395;
            boolean didBubble_11508;
            try {
                td__1394.field("nonexistent");
                didBubble_11508 = false;
            } catch (RuntimeException ignored$17) {
                didBubble_11508 = true;
            }
            didBubble__1395 = didBubble_11508;
            Supplier<String> fn__9106 = () -> "unknown field should bubble";
            test_154.assert_(didBubble__1395, fn__9106);
        } finally {
            test_154.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__1921() {
        Test test_155 = new Test();
        try {
            SafeIdentifier t_4424;
            t_4424 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_4425 = t_4424;
            StringField t_9095 = new StringField();
            FieldDef required__1397 = new FieldDef(t_4425, t_9095, false);
            SafeIdentifier t_4428;
            t_4428 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_4429 = t_4428;
            StringField t_9097 = new StringField();
            FieldDef optional__1398 = new FieldDef(t_4429, t_9097, true);
            boolean t_9101 = !required__1397.isNullable();
            Supplier<String> fn__9094 = () -> "required field should not be nullable";
            test_155.assert_(t_9101, fn__9094);
            boolean t_9103 = optional__1398.isNullable();
            Supplier<String> fn__9093 = () -> "optional field should be nullable";
            test_155.assert_(t_9103, fn__9093);
        } finally {
            test_155.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__1922() {
        Test test_159 = new Test();
        try {
            Function<String, String> build__1524 = name__1526 -> {
                SqlBuilder t_9075 = new SqlBuilder();
                t_9075.appendSafe("select * from hi where name = ");
                t_9075.appendString(name__1526);
                return t_9075.getAccumulated().toString();
            };
            Function<String, String> buildWrong__1525 = name__1528 -> "select * from hi where name = '" + name__1528 + "'";
            String actual_1924 = build__1524.apply("world");
            boolean t_9085 = actual_1924.equals("select * from hi where name = 'world'");
            Supplier<String> fn__9082 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_1924 + ")";
            test_159.assert_(t_9085, fn__9082);
            String bobbyTables__1530 = "Robert'); drop table hi;--";
            String actual_1926 = build__1524.apply("Robert'); drop table hi;--");
            boolean t_9089 = actual_1926.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__9081 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_1926 + ")";
            test_159.assert_(t_9089, fn__9081);
            Supplier<String> fn__9080 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_159.assert_(true, fn__9080);
        } finally {
            test_159.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__1930() {
        Test test_160 = new Test();
        try {
            SqlBuilder t_9043 = new SqlBuilder();
            t_9043.appendSafe("v = ");
            t_9043.appendString("");
            String actual_1931 = t_9043.getAccumulated().toString();
            boolean t_9049 = actual_1931.equals("v = ''");
            Supplier<String> fn__9042 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_1931 + ")";
            test_160.assert_(t_9049, fn__9042);
            SqlBuilder t_9051 = new SqlBuilder();
            t_9051.appendSafe("v = ");
            t_9051.appendString("a''b");
            String actual_1934 = t_9051.getAccumulated().toString();
            boolean t_9057 = actual_1934.equals("v = 'a''''b'");
            Supplier<String> fn__9041 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_1934 + ")";
            test_160.assert_(t_9057, fn__9041);
            SqlBuilder t_9059 = new SqlBuilder();
            t_9059.appendSafe("v = ");
            t_9059.appendString("Hello \u4e16\u754c");
            String actual_1937 = t_9059.getAccumulated().toString();
            boolean t_9065 = actual_1937.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__9040 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_1937 + ")";
            test_160.assert_(t_9065, fn__9040);
            SqlBuilder t_9067 = new SqlBuilder();
            t_9067.appendSafe("v = ");
            t_9067.appendString("Line1\nLine2");
            String actual_1940 = t_9067.getAccumulated().toString();
            boolean t_9073 = actual_1940.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__9039 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_1940 + ")";
            test_160.assert_(t_9073, fn__9039);
        } finally {
            test_160.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__1943() {
        Test test_161 = new Test();
        try {
            SqlBuilder t_9014 = new SqlBuilder();
            t_9014.appendSafe("select ");
            t_9014.appendInt32(42);
            t_9014.appendSafe(", ");
            t_9014.appendInt64(43);
            t_9014.appendSafe(", ");
            t_9014.appendFloat64(19.99D);
            t_9014.appendSafe(", ");
            t_9014.appendBoolean(true);
            t_9014.appendSafe(", ");
            t_9014.appendBoolean(false);
            String actual_1944 = t_9014.getAccumulated().toString();
            boolean t_9028 = actual_1944.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__9013 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_1944 + ")";
            test_161.assert_(t_9028, fn__9013);
            LocalDate t_4369;
            t_4369 = LocalDate.of(2024, 12, 25);
            LocalDate date__1533 = t_4369;
            SqlBuilder t_9030 = new SqlBuilder();
            t_9030.appendSafe("insert into t values (");
            t_9030.appendDate(date__1533);
            t_9030.appendSafe(")");
            String actual_1947 = t_9030.getAccumulated().toString();
            boolean t_9037 = actual_1947.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__9012 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_1947 + ")";
            test_161.assert_(t_9037, fn__9012);
        } finally {
            test_161.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__1950() {
        Test test_162 = new Test();
        try {
            SqlBuilder t_8958 = new SqlBuilder();
            t_8958.appendSafe("v IN (");
            t_8958.appendStringList(List.of("a", "b", "c'd"));
            t_8958.appendSafe(")");
            String actual_1951 = t_8958.getAccumulated().toString();
            boolean t_8965 = actual_1951.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__8957 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_1951 + ")";
            test_162.assert_(t_8965, fn__8957);
            SqlBuilder t_8967 = new SqlBuilder();
            t_8967.appendSafe("v IN (");
            t_8967.appendInt32List(List.of(1, 2, 3));
            t_8967.appendSafe(")");
            String actual_1954 = t_8967.getAccumulated().toString();
            boolean t_8974 = actual_1954.equals("v IN (1, 2, 3)");
            Supplier<String> fn__8956 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_1954 + ")";
            test_162.assert_(t_8974, fn__8956);
            SqlBuilder t_8976 = new SqlBuilder();
            t_8976.appendSafe("v IN (");
            t_8976.appendInt64List(List.of(1, 2));
            t_8976.appendSafe(")");
            String actual_1957 = t_8976.getAccumulated().toString();
            boolean t_8983 = actual_1957.equals("v IN (1, 2)");
            Supplier<String> fn__8955 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_1957 + ")";
            test_162.assert_(t_8983, fn__8955);
            SqlBuilder t_8985 = new SqlBuilder();
            t_8985.appendSafe("v IN (");
            t_8985.appendFloat64List(List.of(1.0D, 2.0D));
            t_8985.appendSafe(")");
            String actual_1960 = t_8985.getAccumulated().toString();
            boolean t_8992 = actual_1960.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__8954 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_1960 + ")";
            test_162.assert_(t_8992, fn__8954);
            SqlBuilder t_8994 = new SqlBuilder();
            t_8994.appendSafe("v IN (");
            t_8994.appendBooleanList(List.of(true, false));
            t_8994.appendSafe(")");
            String actual_1963 = t_8994.getAccumulated().toString();
            boolean t_9001 = actual_1963.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__8953 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_1963 + ")";
            test_162.assert_(t_9001, fn__8953);
            LocalDate t_4341;
            t_4341 = LocalDate.of(2024, 1, 1);
            LocalDate t_4342 = t_4341;
            LocalDate t_4343;
            t_4343 = LocalDate.of(2024, 12, 25);
            LocalDate t_4344 = t_4343;
            List<LocalDate> dates__1535 = List.of(t_4342, t_4344);
            SqlBuilder t_9003 = new SqlBuilder();
            t_9003.appendSafe("v IN (");
            t_9003.appendDateList(dates__1535);
            t_9003.appendSafe(")");
            String actual_1966 = t_9003.getAccumulated().toString();
            boolean t_9010 = actual_1966.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__8952 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_1966 + ")";
            test_162.assert_(t_9010, fn__8952);
        } finally {
            test_162.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__1969() {
        Test test_163 = new Test();
        try {
            double nan__1537;
            nan__1537 = 0.0D / 0.0D;
            SqlBuilder t_8944 = new SqlBuilder();
            t_8944.appendSafe("v = ");
            t_8944.appendFloat64(nan__1537);
            String actual_1970 = t_8944.getAccumulated().toString();
            boolean t_8950 = actual_1970.equals("v = NULL");
            Supplier<String> fn__8943 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_1970 + ")";
            test_163.assert_(t_8950, fn__8943);
        } finally {
            test_163.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__1973() {
        Test test_164 = new Test();
        try {
            double inf__1539;
            inf__1539 = 1.0D / 0.0D;
            SqlBuilder t_8935 = new SqlBuilder();
            t_8935.appendSafe("v = ");
            t_8935.appendFloat64(inf__1539);
            String actual_1974 = t_8935.getAccumulated().toString();
            boolean t_8941 = actual_1974.equals("v = NULL");
            Supplier<String> fn__8934 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_1974 + ")";
            test_164.assert_(t_8941, fn__8934);
        } finally {
            test_164.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__1977() {
        Test test_165 = new Test();
        try {
            double ninf__1541;
            ninf__1541 = -1.0D / 0.0D;
            SqlBuilder t_8926 = new SqlBuilder();
            t_8926.appendSafe("v = ");
            t_8926.appendFloat64(ninf__1541);
            String actual_1978 = t_8926.getAccumulated().toString();
            boolean t_8932 = actual_1978.equals("v = NULL");
            Supplier<String> fn__8925 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_1978 + ")";
            test_165.assert_(t_8932, fn__8925);
        } finally {
            test_165.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__1981() {
        Test test_166 = new Test();
        try {
            SqlBuilder t_8901 = new SqlBuilder();
            t_8901.appendSafe("v = ");
            t_8901.appendFloat64(3.14D);
            String actual_1982 = t_8901.getAccumulated().toString();
            boolean t_8907 = actual_1982.equals("v = 3.14");
            Supplier<String> fn__8900 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_1982 + ")";
            test_166.assert_(t_8907, fn__8900);
            SqlBuilder t_8909 = new SqlBuilder();
            t_8909.appendSafe("v = ");
            t_8909.appendFloat64(0.0D);
            String actual_1985 = t_8909.getAccumulated().toString();
            boolean t_8915 = actual_1985.equals("v = 0.0");
            Supplier<String> fn__8899 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_1985 + ")";
            test_166.assert_(t_8915, fn__8899);
            SqlBuilder t_8917 = new SqlBuilder();
            t_8917.appendSafe("v = ");
            t_8917.appendFloat64(-42.5D);
            String actual_1988 = t_8917.getAccumulated().toString();
            boolean t_8923 = actual_1988.equals("v = -42.5");
            Supplier<String> fn__8898 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_1988 + ")";
            test_166.assert_(t_8923, fn__8898);
        } finally {
            test_166.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__1991() {
        Test test_167 = new Test();
        try {
            LocalDate t_4237;
            t_4237 = LocalDate.of(2024, 6, 15);
            LocalDate d__1544 = t_4237;
            SqlBuilder t_8890 = new SqlBuilder();
            t_8890.appendSafe("v = ");
            t_8890.appendDate(d__1544);
            String actual_1992 = t_8890.getAccumulated().toString();
            boolean t_8896 = actual_1992.equals("v = '2024-06-15'");
            Supplier<String> fn__8889 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_1992 + ")";
            test_167.assert_(t_8896, fn__8889);
        } finally {
            test_167.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__1995() {
        Test test_168 = new Test();
        try {
            String name__1546 = "Someone";
            SqlBuilder t_8858 = new SqlBuilder();
            t_8858.appendSafe("where p.last_name = ");
            t_8858.appendString("Someone");
            SqlFragment condition__1547 = t_8858.getAccumulated();
            SqlBuilder t_8862 = new SqlBuilder();
            t_8862.appendSafe("select p.id from person p ");
            t_8862.appendFragment(condition__1547);
            String actual_1997 = t_8862.getAccumulated().toString();
            boolean t_8868 = actual_1997.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__8857 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1997 + ")";
            test_168.assert_(t_8868, fn__8857);
            SqlBuilder t_8870 = new SqlBuilder();
            t_8870.appendSafe("select p.id from person p ");
            t_8870.appendPart(condition__1547.toSource());
            String actual_2000 = t_8870.getAccumulated().toString();
            boolean t_8877 = actual_2000.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__8856 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_2000 + ")";
            test_168.assert_(t_8877, fn__8856);
            List<SqlPart> parts__1548 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_8881 = new SqlBuilder();
            t_8881.appendSafe("select ");
            t_8881.appendPartList(parts__1548);
            String actual_2003 = t_8881.getAccumulated().toString();
            boolean t_8887 = actual_2003.equals("select 'a''b', 3");
            Supplier<String> fn__8855 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_2003 + ")";
            test_168.assert_(t_8887, fn__8855);
        } finally {
            test_168.softFailToHard();
        }
    }
}
