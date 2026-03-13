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
    static SafeIdentifier csid__398(String name__543) {
        SafeIdentifier t_3969;
        t_3969 = SrcGlobal.safeIdentifier(name__543);
        return t_3969;
    }
    static TableDef userTable__399() {
        return new TableDef(SrcTest.csid__398("users"), List.of(new FieldDef(SrcTest.csid__398("name"), new StringField(), false), new FieldDef(SrcTest.csid__398("email"), new StringField(), false), new FieldDef(SrcTest.csid__398("age"), new IntField(), true), new FieldDef(SrcTest.csid__398("score"), new FloatField(), true), new FieldDef(SrcTest.csid__398("active"), new BoolField(), true)));
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__1178() {
        Test test_22 = new Test();
        try {
            Map<String, String> params__547 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_6946 = SrcTest.userTable__399();
            SafeIdentifier t_6947 = SrcTest.csid__398("name");
            SafeIdentifier t_6948 = SrcTest.csid__398("email");
            Changeset cs__548 = SrcGlobal.changeset(t_6946, params__547).cast(List.of(t_6947, t_6948));
            boolean t_6951 = cs__548.getChanges().containsKey("name");
            Supplier<String> fn__6941 = () -> "name should be in changes";
            test_22.assert_(t_6951, fn__6941);
            boolean t_6955 = cs__548.getChanges().containsKey("email");
            Supplier<String> fn__6940 = () -> "email should be in changes";
            test_22.assert_(t_6955, fn__6940);
            boolean t_6961 = !cs__548.getChanges().containsKey("admin");
            Supplier<String> fn__6939 = () -> "admin must be dropped (not in whitelist)";
            test_22.assert_(t_6961, fn__6939);
            boolean t_6963 = cs__548.isValid();
            Supplier<String> fn__6938 = () -> "should still be valid";
            test_22.assert_(t_6963, fn__6938);
        } finally {
            test_22.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__1179() {
        Test test_23 = new Test();
        try {
            Map<String, String> params__550 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_6924 = SrcTest.userTable__399();
            SafeIdentifier t_6925 = SrcTest.csid__398("name");
            Changeset cs__551 = SrcGlobal.changeset(t_6924, params__550).cast(List.of(t_6925)).cast(List.of(SrcTest.csid__398("email")));
            boolean t_6932 = !cs__551.getChanges().containsKey("name");
            Supplier<String> fn__6920 = () -> "name must be excluded by second cast";
            test_23.assert_(t_6932, fn__6920);
            boolean t_6935 = cs__551.getChanges().containsKey("email");
            Supplier<String> fn__6919 = () -> "email should be present";
            test_23.assert_(t_6935, fn__6919);
        } finally {
            test_23.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__1180() {
        Test test_24 = new Test();
        try {
            Map<String, String> params__553 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_6906 = SrcTest.userTable__399();
            SafeIdentifier t_6907 = SrcTest.csid__398("name");
            SafeIdentifier t_6908 = SrcTest.csid__398("email");
            Changeset cs__554 = SrcGlobal.changeset(t_6906, params__553).cast(List.of(t_6907, t_6908));
            boolean t_6913 = !cs__554.getChanges().containsKey("name");
            Supplier<String> fn__6902 = () -> "empty name should not be in changes";
            test_24.assert_(t_6913, fn__6902);
            boolean t_6916 = cs__554.getChanges().containsKey("email");
            Supplier<String> fn__6901 = () -> "email should be in changes";
            test_24.assert_(t_6916, fn__6901);
        } finally {
            test_24.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__1181() {
        Test test_25 = new Test();
        try {
            Map<String, String> params__556 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_6888 = SrcTest.userTable__399();
            SafeIdentifier t_6889 = SrcTest.csid__398("name");
            Changeset cs__557 = SrcGlobal.changeset(t_6888, params__556).cast(List.of(t_6889)).validateRequired(List.of(SrcTest.csid__398("name")));
            boolean t_6893 = cs__557.isValid();
            Supplier<String> fn__6885 = () -> "should be valid";
            test_25.assert_(t_6893, fn__6885);
            boolean t_6899 = cs__557.getErrors().size() == 0;
            Supplier<String> fn__6884 = () -> "no errors expected";
            test_25.assert_(t_6899, fn__6884);
        } finally {
            test_25.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__1182() {
        Test test_26 = new Test();
        try {
            Map<String, String> params__559 = Core.mapConstructor(List.of());
            TableDef t_6864 = SrcTest.userTable__399();
            SafeIdentifier t_6865 = SrcTest.csid__398("name");
            Changeset cs__560 = SrcGlobal.changeset(t_6864, params__559).cast(List.of(t_6865)).validateRequired(List.of(SrcTest.csid__398("name")));
            boolean t_6871 = !cs__560.isValid();
            Supplier<String> fn__6862 = () -> "should be invalid";
            test_26.assert_(t_6871, fn__6862);
            boolean t_6876 = cs__560.getErrors().size() == 1;
            Supplier<String> fn__6861 = () -> "should have one error";
            test_26.assert_(t_6876, fn__6861);
            boolean t_6882 = Core.listGet(cs__560.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__6860 = () -> "error should name the field";
            test_26.assert_(t_6882, fn__6860);
        } finally {
            test_26.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__1183() {
        Test test_27 = new Test();
        try {
            Map<String, String> params__562 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_6852 = SrcTest.userTable__399();
            SafeIdentifier t_6853 = SrcTest.csid__398("name");
            Changeset cs__563 = SrcGlobal.changeset(t_6852, params__562).cast(List.of(t_6853)).validateLength(SrcTest.csid__398("name"), 2, 50);
            boolean t_6857 = cs__563.isValid();
            Supplier<String> fn__6849 = () -> "should be valid";
            test_27.assert_(t_6857, fn__6849);
        } finally {
            test_27.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__1184() {
        Test test_28 = new Test();
        try {
            Map<String, String> params__565 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_6840 = SrcTest.userTable__399();
            SafeIdentifier t_6841 = SrcTest.csid__398("name");
            Changeset cs__566 = SrcGlobal.changeset(t_6840, params__565).cast(List.of(t_6841)).validateLength(SrcTest.csid__398("name"), 2, 50);
            boolean t_6847 = !cs__566.isValid();
            Supplier<String> fn__6837 = () -> "should be invalid";
            test_28.assert_(t_6847, fn__6837);
        } finally {
            test_28.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__1185() {
        Test test_29 = new Test();
        try {
            Map<String, String> params__568 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_6828 = SrcTest.userTable__399();
            SafeIdentifier t_6829 = SrcTest.csid__398("name");
            Changeset cs__569 = SrcGlobal.changeset(t_6828, params__568).cast(List.of(t_6829)).validateLength(SrcTest.csid__398("name"), 2, 10);
            boolean t_6835 = !cs__569.isValid();
            Supplier<String> fn__6825 = () -> "should be invalid";
            test_29.assert_(t_6835, fn__6825);
        } finally {
            test_29.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__1186() {
        Test test_30 = new Test();
        try {
            Map<String, String> params__571 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_6817 = SrcTest.userTable__399();
            SafeIdentifier t_6818 = SrcTest.csid__398("age");
            Changeset cs__572 = SrcGlobal.changeset(t_6817, params__571).cast(List.of(t_6818)).validateInt(SrcTest.csid__398("age"));
            boolean t_6822 = cs__572.isValid();
            Supplier<String> fn__6814 = () -> "should be valid";
            test_30.assert_(t_6822, fn__6814);
        } finally {
            test_30.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__1187() {
        Test test_31 = new Test();
        try {
            Map<String, String> params__574 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_6805 = SrcTest.userTable__399();
            SafeIdentifier t_6806 = SrcTest.csid__398("age");
            Changeset cs__575 = SrcGlobal.changeset(t_6805, params__574).cast(List.of(t_6806)).validateInt(SrcTest.csid__398("age"));
            boolean t_6812 = !cs__575.isValid();
            Supplier<String> fn__6802 = () -> "should be invalid";
            test_31.assert_(t_6812, fn__6802);
        } finally {
            test_31.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__1188() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__577 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_6794 = SrcTest.userTable__399();
            SafeIdentifier t_6795 = SrcTest.csid__398("score");
            Changeset cs__578 = SrcGlobal.changeset(t_6794, params__577).cast(List.of(t_6795)).validateFloat(SrcTest.csid__398("score"));
            boolean t_6799 = cs__578.isValid();
            Supplier<String> fn__6791 = () -> "should be valid";
            test_32.assert_(t_6799, fn__6791);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__1189() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__580 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_6783 = SrcTest.userTable__399();
            SafeIdentifier t_6784 = SrcTest.csid__398("age");
            Changeset cs__581 = SrcGlobal.changeset(t_6783, params__580).cast(List.of(t_6784)).validateInt64(SrcTest.csid__398("age"));
            boolean t_6788 = cs__581.isValid();
            Supplier<String> fn__6780 = () -> "should be valid";
            test_33.assert_(t_6788, fn__6780);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__1190() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__583 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_6771 = SrcTest.userTable__399();
            SafeIdentifier t_6772 = SrcTest.csid__398("age");
            Changeset cs__584 = SrcGlobal.changeset(t_6771, params__583).cast(List.of(t_6772)).validateInt64(SrcTest.csid__398("age"));
            boolean t_6778 = !cs__584.isValid();
            Supplier<String> fn__6768 = () -> "should be invalid";
            test_34.assert_(t_6778, fn__6768);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__1191() {
        Test test_35 = new Test();
        try {
            Consumer<String> fn__6765 = v__586 -> {
                Map<String, String> params__587 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__586)));
                TableDef t_6757 = SrcTest.userTable__399();
                SafeIdentifier t_6758 = SrcTest.csid__398("active");
                Changeset cs__588 = SrcGlobal.changeset(t_6757, params__587).cast(List.of(t_6758)).validateBool(SrcTest.csid__398("active"));
                boolean t_6762 = cs__588.isValid();
                Supplier<String> fn__6754 = () -> "should accept: " + v__586;
                test_35.assert_(t_6762, fn__6754);
            };
            List.of("true", "1", "yes", "on").forEach(fn__6765);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__1192() {
        Test test_36 = new Test();
        try {
            Consumer<String> fn__6751 = v__590 -> {
                Map<String, String> params__591 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__590)));
                TableDef t_6743 = SrcTest.userTable__399();
                SafeIdentifier t_6744 = SrcTest.csid__398("active");
                Changeset cs__592 = SrcGlobal.changeset(t_6743, params__591).cast(List.of(t_6744)).validateBool(SrcTest.csid__398("active"));
                boolean t_6748 = cs__592.isValid();
                Supplier<String> fn__6740 = () -> "should accept: " + v__590;
                test_36.assert_(t_6748, fn__6740);
            };
            List.of("false", "0", "no", "off").forEach(fn__6751);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__1193() {
        Test test_37 = new Test();
        try {
            Consumer<String> fn__6737 = v__594 -> {
                Map<String, String> params__595 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__594)));
                TableDef t_6728 = SrcTest.userTable__399();
                SafeIdentifier t_6729 = SrcTest.csid__398("active");
                Changeset cs__596 = SrcGlobal.changeset(t_6728, params__595).cast(List.of(t_6729)).validateBool(SrcTest.csid__398("active"));
                boolean t_6735 = !cs__596.isValid();
                Supplier<String> fn__6725 = () -> "should reject ambiguous: " + v__594;
                test_37.assert_(t_6735, fn__6725);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__6737);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__1194() {
        Test test_38 = new Test();
        try {
            Map<String, String> params__598 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_6713 = SrcTest.userTable__399();
            SafeIdentifier t_6714 = SrcTest.csid__398("name");
            SafeIdentifier t_6715 = SrcTest.csid__398("email");
            Changeset cs__599 = SrcGlobal.changeset(t_6713, params__598).cast(List.of(t_6714, t_6715)).validateRequired(List.of(SrcTest.csid__398("name"), SrcTest.csid__398("email")));
            SqlFragment t_3770;
            t_3770 = cs__599.toInsertSql();
            SqlFragment sqlFrag__600 = t_3770;
            String s__601 = sqlFrag__600.toString();
            boolean t_6722 = s__601.indexOf("''") >= 0;
            Supplier<String> fn__6709 = () -> "single quote must be doubled: " + s__601;
            test_38.assert_(t_6722, fn__6709);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__1195() {
        Test test_39 = new Test();
        try {
            Map<String, String> params__603 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_6693 = SrcTest.userTable__399();
            SafeIdentifier t_6694 = SrcTest.csid__398("name");
            SafeIdentifier t_6695 = SrcTest.csid__398("email");
            Changeset cs__604 = SrcGlobal.changeset(t_6693, params__603).cast(List.of(t_6694, t_6695)).validateRequired(List.of(SrcTest.csid__398("name"), SrcTest.csid__398("email")));
            SqlFragment t_3749;
            t_3749 = cs__604.toInsertSql();
            SqlFragment sqlFrag__605 = t_3749;
            String s__606 = sqlFrag__605.toString();
            boolean t_6702 = s__606.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__6689 = () -> "has INSERT INTO: " + s__606;
            test_39.assert_(t_6702, fn__6689);
            boolean t_6706 = s__606.indexOf("'Alice'") >= 0;
            Supplier<String> fn__6688 = () -> "has quoted name: " + s__606;
            test_39.assert_(t_6706, fn__6688);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__1196() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__608 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_6675 = SrcTest.userTable__399();
            SafeIdentifier t_6676 = SrcTest.csid__398("name");
            SafeIdentifier t_6677 = SrcTest.csid__398("email");
            SafeIdentifier t_6678 = SrcTest.csid__398("age");
            Changeset cs__609 = SrcGlobal.changeset(t_6675, params__608).cast(List.of(t_6676, t_6677, t_6678)).validateRequired(List.of(SrcTest.csid__398("name"), SrcTest.csid__398("email")));
            SqlFragment t_3732;
            t_3732 = cs__609.toInsertSql();
            SqlFragment sqlFrag__610 = t_3732;
            String s__611 = sqlFrag__610.toString();
            boolean t_6685 = s__611.indexOf("25") >= 0;
            Supplier<String> fn__6670 = () -> "age rendered unquoted: " + s__611;
            test_40.assert_(t_6685, fn__6670);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__1197() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__613 = Core.mapConstructor(List.of());
            TableDef t_6663 = SrcTest.userTable__399();
            SafeIdentifier t_6664 = SrcTest.csid__398("name");
            Changeset cs__614 = SrcGlobal.changeset(t_6663, params__613).cast(List.of(t_6664)).validateRequired(List.of(SrcTest.csid__398("name")));
            boolean didBubble__615;
            boolean didBubble_7264;
            try {
                cs__614.toInsertSql();
                didBubble_7264 = false;
            } catch (RuntimeException ignored$4) {
                didBubble_7264 = true;
            }
            didBubble__615 = didBubble_7264;
            Supplier<String> fn__6661 = () -> "invalid changeset should bubble";
            test_41.assert_(didBubble__615, fn__6661);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__1198() {
        Test test_42 = new Test();
        try {
            TableDef strictTable__617 = new TableDef(SrcTest.csid__398("posts"), List.of(new FieldDef(SrcTest.csid__398("title"), new StringField(), false), new FieldDef(SrcTest.csid__398("body"), new StringField(), true)));
            Map<String, String> params__618 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_6654 = SrcTest.csid__398("body");
            Changeset cs__619 = SrcGlobal.changeset(strictTable__617, params__618).cast(List.of(t_6654));
            boolean t_6656 = cs__619.isValid();
            Supplier<String> fn__6643 = () -> "changeset should appear valid (no explicit validation run)";
            test_42.assert_(t_6656, fn__6643);
            boolean didBubble__620;
            boolean didBubble_7265;
            try {
                cs__619.toInsertSql();
                didBubble_7265 = false;
            } catch (RuntimeException ignored$5) {
                didBubble_7265 = true;
            }
            didBubble__620 = didBubble_7265;
            Supplier<String> fn__6642 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_42.assert_(didBubble__620, fn__6642);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__1199() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__622 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_6633 = SrcTest.userTable__399();
            SafeIdentifier t_6634 = SrcTest.csid__398("name");
            Changeset cs__623 = SrcGlobal.changeset(t_6633, params__622).cast(List.of(t_6634)).validateRequired(List.of(SrcTest.csid__398("name")));
            SqlFragment t_3692;
            t_3692 = cs__623.toUpdateSql(42);
            SqlFragment sqlFrag__624 = t_3692;
            String s__625 = sqlFrag__624.toString();
            boolean t_6640 = s__625.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__6630 = () -> "got: " + s__625;
            test_43.assert_(t_6640, fn__6630);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__1200() {
        Test test_44 = new Test();
        try {
            Map<String, String> params__627 = Core.mapConstructor(List.of());
            TableDef t_6623 = SrcTest.userTable__399();
            SafeIdentifier t_6624 = SrcTest.csid__398("name");
            Changeset cs__628 = SrcGlobal.changeset(t_6623, params__627).cast(List.of(t_6624)).validateRequired(List.of(SrcTest.csid__398("name")));
            boolean didBubble__629;
            boolean didBubble_7266;
            try {
                cs__628.toUpdateSql(1);
                didBubble_7266 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_7266 = true;
            }
            didBubble__629 = didBubble_7266;
            Supplier<String> fn__6621 = () -> "invalid changeset should bubble";
            test_44.assert_(didBubble__629, fn__6621);
        } finally {
            test_44.softFailToHard();
        }
    }
    static SafeIdentifier sid__400(String name__791) {
        SafeIdentifier t_3489;
        t_3489 = SrcGlobal.safeIdentifier(name__791);
        return t_3489;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__1237() {
        Test test_45 = new Test();
        try {
            Query q__794 = SrcGlobal.from(SrcTest.sid__400("users"));
            boolean t_6444 = q__794.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__6439 = () -> "bare query";
            test_45.assert_(t_6444, fn__6439);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__1238() {
        Test test_46 = new Test();
        try {
            SafeIdentifier t_6430 = SrcTest.sid__400("users");
            SafeIdentifier t_6431 = SrcTest.sid__400("id");
            SafeIdentifier t_6432 = SrcTest.sid__400("name");
            Query q__796 = SrcGlobal.from(t_6430).select(List.of(t_6431, t_6432));
            boolean t_6437 = q__796.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__6429 = () -> "select columns";
            test_46.assert_(t_6437, fn__6429);
        } finally {
            test_46.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__1239() {
        Test test_47 = new Test();
        try {
            SafeIdentifier t_6418 = SrcTest.sid__400("users");
            SqlBuilder t_6419 = new SqlBuilder();
            t_6419.appendSafe("age > ");
            t_6419.appendInt32(18);
            SqlFragment t_6422 = t_6419.getAccumulated();
            Query q__798 = SrcGlobal.from(t_6418).where(t_6422);
            boolean t_6427 = q__798.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__6417 = () -> "where int";
            test_47.assert_(t_6427, fn__6417);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__1241() {
        Test test_48 = new Test();
        try {
            SafeIdentifier t_6406 = SrcTest.sid__400("users");
            SqlBuilder t_6407 = new SqlBuilder();
            t_6407.appendSafe("active = ");
            t_6407.appendBoolean(true);
            SqlFragment t_6410 = t_6407.getAccumulated();
            Query q__800 = SrcGlobal.from(t_6406).where(t_6410);
            boolean t_6415 = q__800.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__6405 = () -> "where bool";
            test_48.assert_(t_6415, fn__6405);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__1243() {
        Test test_49 = new Test();
        try {
            SafeIdentifier t_6389 = SrcTest.sid__400("users");
            SqlBuilder t_6390 = new SqlBuilder();
            t_6390.appendSafe("age > ");
            t_6390.appendInt32(18);
            SqlFragment t_6393 = t_6390.getAccumulated();
            Query t_6394 = SrcGlobal.from(t_6389).where(t_6393);
            SqlBuilder t_6395 = new SqlBuilder();
            t_6395.appendSafe("active = ");
            t_6395.appendBoolean(true);
            Query q__802 = t_6394.where(t_6395.getAccumulated());
            boolean t_6403 = q__802.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__6388 = () -> "chained where";
            test_49.assert_(t_6403, fn__6388);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__1246() {
        Test test_50 = new Test();
        try {
            SafeIdentifier t_6380 = SrcTest.sid__400("users");
            SafeIdentifier t_6381 = SrcTest.sid__400("name");
            Query q__804 = SrcGlobal.from(t_6380).orderBy(t_6381, true);
            boolean t_6386 = q__804.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__6379 = () -> "order asc";
            test_50.assert_(t_6386, fn__6379);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__1247() {
        Test test_51 = new Test();
        try {
            SafeIdentifier t_6371 = SrcTest.sid__400("users");
            SafeIdentifier t_6372 = SrcTest.sid__400("created_at");
            Query q__806 = SrcGlobal.from(t_6371).orderBy(t_6372, false);
            boolean t_6377 = q__806.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__6370 = () -> "order desc";
            test_51.assert_(t_6377, fn__6370);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__1248() {
        Test test_52 = new Test();
        try {
            Query t_3423;
            t_3423 = SrcGlobal.from(SrcTest.sid__400("users")).limit(10);
            Query t_3424;
            t_3424 = t_3423.offset(20);
            Query q__808 = t_3424;
            boolean t_6368 = q__808.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__6363 = () -> "limit/offset";
            test_52.assert_(t_6368, fn__6363);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__1249() {
        Test test_53 = new Test();
        try {
            boolean didBubble__810;
            boolean didBubble_7267;
            try {
                SrcGlobal.from(SrcTest.sid__400("users")).limit(-1);
                didBubble_7267 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_7267 = true;
            }
            didBubble__810 = didBubble_7267;
            Supplier<String> fn__6359 = () -> "negative limit should bubble";
            test_53.assert_(didBubble__810, fn__6359);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__1250() {
        Test test_54 = new Test();
        try {
            boolean didBubble__812;
            boolean didBubble_7268;
            try {
                SrcGlobal.from(SrcTest.sid__400("users")).offset(-1);
                didBubble_7268 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_7268 = true;
            }
            didBubble__812 = didBubble_7268;
            Supplier<String> fn__6355 = () -> "negative offset should bubble";
            test_54.assert_(didBubble__812, fn__6355);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__1251() {
        Test test_55 = new Test();
        try {
            int minAge__814 = 21;
            SafeIdentifier t_6333 = SrcTest.sid__400("users");
            SafeIdentifier t_6334 = SrcTest.sid__400("id");
            SafeIdentifier t_6335 = SrcTest.sid__400("name");
            SafeIdentifier t_6336 = SrcTest.sid__400("email");
            Query t_6337 = SrcGlobal.from(t_6333).select(List.of(t_6334, t_6335, t_6336));
            SqlBuilder t_6338 = new SqlBuilder();
            t_6338.appendSafe("age >= ");
            t_6338.appendInt32(21);
            Query t_6342 = t_6337.where(t_6338.getAccumulated());
            SqlBuilder t_6343 = new SqlBuilder();
            t_6343.appendSafe("active = ");
            t_6343.appendBoolean(true);
            Query t_3409;
            t_3409 = t_6342.where(t_6343.getAccumulated()).orderBy(SrcTest.sid__400("name"), true).limit(25);
            Query t_3410;
            t_3410 = t_3409.offset(0);
            Query q__815 = t_3410;
            boolean t_6353 = q__815.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__6332 = () -> "complex query";
            test_55.assert_(t_6353, fn__6332);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__1254() {
        Test test_56 = new Test();
        try {
            Query q__817 = SrcGlobal.from(SrcTest.sid__400("users"));
            SqlFragment t_3386;
            t_3386 = q__817.safeToSql(100);
            SqlFragment t_3387 = t_3386;
            String s__818 = t_3387.toString();
            boolean t_6330 = s__818.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__6326 = () -> "should have limit: " + s__818;
            test_56.assert_(t_6330, fn__6326);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__1255() {
        Test test_57 = new Test();
        try {
            Query t_3378;
            t_3378 = SrcGlobal.from(SrcTest.sid__400("users")).limit(5);
            Query q__820 = t_3378;
            SqlFragment t_3381;
            t_3381 = q__820.safeToSql(100);
            SqlFragment t_3382 = t_3381;
            String s__821 = t_3382.toString();
            boolean t_6324 = s__821.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__6320 = () -> "explicit limit preserved: " + s__821;
            test_57.assert_(t_6324, fn__6320);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__1256() {
        Test test_58 = new Test();
        try {
            boolean didBubble__823;
            boolean didBubble_7269;
            try {
                SrcGlobal.from(SrcTest.sid__400("users")).safeToSql(-1);
                didBubble_7269 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_7269 = true;
            }
            didBubble__823 = didBubble_7269;
            Supplier<String> fn__6316 = () -> "negative defaultLimit should bubble";
            test_58.assert_(didBubble__823, fn__6316);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__1257() {
        Test test_59 = new Test();
        try {
            String evil__825 = "'; DROP TABLE users; --";
            SafeIdentifier t_6300 = SrcTest.sid__400("users");
            SqlBuilder t_6301 = new SqlBuilder();
            t_6301.appendSafe("name = ");
            t_6301.appendString("'; DROP TABLE users; --");
            SqlFragment t_6304 = t_6301.getAccumulated();
            Query q__826 = SrcGlobal.from(t_6300).where(t_6304);
            String s__827 = q__826.toSql().toString();
            boolean t_6309 = s__827.indexOf("''") >= 0;
            Supplier<String> fn__6299 = () -> "quotes must be doubled: " + s__827;
            test_59.assert_(t_6309, fn__6299);
            boolean t_6313 = s__827.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__6298 = () -> "structure intact: " + s__827;
            test_59.assert_(t_6313, fn__6298);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__1259() {
        Test test_60 = new Test();
        try {
            String attack__829 = "users; DROP TABLE users; --";
            boolean didBubble__830;
            boolean didBubble_7270;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_7270 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_7270 = true;
            }
            didBubble__830 = didBubble_7270;
            Supplier<String> fn__6295 = () -> "metacharacter-containing name must be rejected at construction";
            test_60.assert_(didBubble__830, fn__6295);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__1260() {
        Test test_61 = new Test();
        try {
            SafeIdentifier t_6284 = SrcTest.sid__400("users");
            SafeIdentifier t_6285 = SrcTest.sid__400("orders");
            SqlBuilder t_6286 = new SqlBuilder();
            t_6286.appendSafe("users.id = orders.user_id");
            SqlFragment t_6288 = t_6286.getAccumulated();
            Query q__832 = SrcGlobal.from(t_6284).innerJoin(t_6285, t_6288);
            boolean t_6293 = q__832.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__6283 = () -> "inner join";
            test_61.assert_(t_6293, fn__6283);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__1262() {
        Test test_62 = new Test();
        try {
            SafeIdentifier t_6272 = SrcTest.sid__400("users");
            SafeIdentifier t_6273 = SrcTest.sid__400("profiles");
            SqlBuilder t_6274 = new SqlBuilder();
            t_6274.appendSafe("users.id = profiles.user_id");
            SqlFragment t_6276 = t_6274.getAccumulated();
            Query q__834 = SrcGlobal.from(t_6272).leftJoin(t_6273, t_6276);
            boolean t_6281 = q__834.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__6271 = () -> "left join";
            test_62.assert_(t_6281, fn__6271);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__1264() {
        Test test_63 = new Test();
        try {
            SafeIdentifier t_6260 = SrcTest.sid__400("orders");
            SafeIdentifier t_6261 = SrcTest.sid__400("users");
            SqlBuilder t_6262 = new SqlBuilder();
            t_6262.appendSafe("orders.user_id = users.id");
            SqlFragment t_6264 = t_6262.getAccumulated();
            Query q__836 = SrcGlobal.from(t_6260).rightJoin(t_6261, t_6264);
            boolean t_6269 = q__836.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__6259 = () -> "right join";
            test_63.assert_(t_6269, fn__6259);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__1266() {
        Test test_64 = new Test();
        try {
            SafeIdentifier t_6248 = SrcTest.sid__400("users");
            SafeIdentifier t_6249 = SrcTest.sid__400("orders");
            SqlBuilder t_6250 = new SqlBuilder();
            t_6250.appendSafe("users.id = orders.user_id");
            SqlFragment t_6252 = t_6250.getAccumulated();
            Query q__838 = SrcGlobal.from(t_6248).fullJoin(t_6249, t_6252);
            boolean t_6257 = q__838.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__6247 = () -> "full join";
            test_64.assert_(t_6257, fn__6247);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__1268() {
        Test test_65 = new Test();
        try {
            SafeIdentifier t_6231 = SrcTest.sid__400("users");
            SafeIdentifier t_6232 = SrcTest.sid__400("orders");
            SqlBuilder t_6233 = new SqlBuilder();
            t_6233.appendSafe("users.id = orders.user_id");
            SqlFragment t_6235 = t_6233.getAccumulated();
            Query t_6236 = SrcGlobal.from(t_6231).innerJoin(t_6232, t_6235);
            SafeIdentifier t_6237 = SrcTest.sid__400("profiles");
            SqlBuilder t_6238 = new SqlBuilder();
            t_6238.appendSafe("users.id = profiles.user_id");
            Query q__840 = t_6236.leftJoin(t_6237, t_6238.getAccumulated());
            boolean t_6245 = q__840.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__6230 = () -> "chained joins";
            test_65.assert_(t_6245, fn__6230);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__1271() {
        Test test_66 = new Test();
        try {
            SafeIdentifier t_6212 = SrcTest.sid__400("users");
            SafeIdentifier t_6213 = SrcTest.sid__400("orders");
            SqlBuilder t_6214 = new SqlBuilder();
            t_6214.appendSafe("users.id = orders.user_id");
            SqlFragment t_6216 = t_6214.getAccumulated();
            Query t_6217 = SrcGlobal.from(t_6212).innerJoin(t_6213, t_6216);
            SqlBuilder t_6218 = new SqlBuilder();
            t_6218.appendSafe("orders.total > ");
            t_6218.appendInt32(100);
            Query t_3293;
            t_3293 = t_6217.where(t_6218.getAccumulated()).orderBy(SrcTest.sid__400("name"), true).limit(10);
            Query q__842 = t_3293;
            boolean t_6228 = q__842.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__6211 = () -> "join with where/order/limit";
            test_66.assert_(t_6228, fn__6211);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__1274() {
        Test test_67 = new Test();
        try {
            SqlFragment c__844 = SrcGlobal.col(SrcTest.sid__400("users"), SrcTest.sid__400("id"));
            boolean t_6209 = c__844.toString().equals("users.id");
            Supplier<String> fn__6203 = () -> "col helper";
            test_67.assert_(t_6209, fn__6203);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__1275() {
        Test test_68 = new Test();
        try {
            SqlFragment onCond__846 = SrcGlobal.col(SrcTest.sid__400("users"), SrcTest.sid__400("id"));
            SqlBuilder b__847 = new SqlBuilder();
            b__847.appendFragment(onCond__846);
            b__847.appendSafe(" = ");
            b__847.appendFragment(SrcGlobal.col(SrcTest.sid__400("orders"), SrcTest.sid__400("user_id")));
            SafeIdentifier t_6194 = SrcTest.sid__400("users");
            SafeIdentifier t_6195 = SrcTest.sid__400("orders");
            SqlFragment t_6196 = b__847.getAccumulated();
            Query q__848 = SrcGlobal.from(t_6194).innerJoin(t_6195, t_6196);
            boolean t_6201 = q__848.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__6183 = () -> "join with col";
            test_68.assert_(t_6201, fn__6183);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orWhereBasic__1276() {
        Test test_69 = new Test();
        try {
            SafeIdentifier t_6172 = SrcTest.sid__400("users");
            SqlBuilder t_6173 = new SqlBuilder();
            t_6173.appendSafe("status = ");
            t_6173.appendString("active");
            SqlFragment t_6176 = t_6173.getAccumulated();
            Query q__850 = SrcGlobal.from(t_6172).orWhere(t_6176);
            boolean t_6181 = q__850.toSql().toString().equals("SELECT * FROM users WHERE status = 'active'");
            Supplier<String> fn__6171 = () -> "orWhere basic";
            test_69.assert_(t_6181, fn__6171);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereThenOrWhere__1278() {
        Test test_70 = new Test();
        try {
            SafeIdentifier t_6155 = SrcTest.sid__400("users");
            SqlBuilder t_6156 = new SqlBuilder();
            t_6156.appendSafe("age > ");
            t_6156.appendInt32(18);
            SqlFragment t_6159 = t_6156.getAccumulated();
            Query t_6160 = SrcGlobal.from(t_6155).where(t_6159);
            SqlBuilder t_6161 = new SqlBuilder();
            t_6161.appendSafe("vip = ");
            t_6161.appendBoolean(true);
            Query q__852 = t_6160.orWhere(t_6161.getAccumulated());
            boolean t_6169 = q__852.toSql().toString().equals("SELECT * FROM users WHERE age > 18 OR vip = TRUE");
            Supplier<String> fn__6154 = () -> "where then orWhere";
            test_70.assert_(t_6169, fn__6154);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleOrWhere__1281() {
        Test test_71 = new Test();
        try {
            SafeIdentifier t_6133 = SrcTest.sid__400("users");
            SqlBuilder t_6134 = new SqlBuilder();
            t_6134.appendSafe("active = ");
            t_6134.appendBoolean(true);
            SqlFragment t_6137 = t_6134.getAccumulated();
            Query t_6138 = SrcGlobal.from(t_6133).where(t_6137);
            SqlBuilder t_6139 = new SqlBuilder();
            t_6139.appendSafe("role = ");
            t_6139.appendString("admin");
            Query t_6143 = t_6138.orWhere(t_6139.getAccumulated());
            SqlBuilder t_6144 = new SqlBuilder();
            t_6144.appendSafe("role = ");
            t_6144.appendString("moderator");
            Query q__854 = t_6143.orWhere(t_6144.getAccumulated());
            boolean t_6152 = q__854.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE OR role = 'admin' OR role = 'moderator'");
            Supplier<String> fn__6132 = () -> "multiple orWhere";
            test_71.assert_(t_6152, fn__6132);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedWhereAndOrWhere__1285() {
        Test test_72 = new Test();
        try {
            SafeIdentifier t_6111 = SrcTest.sid__400("users");
            SqlBuilder t_6112 = new SqlBuilder();
            t_6112.appendSafe("age > ");
            t_6112.appendInt32(18);
            SqlFragment t_6115 = t_6112.getAccumulated();
            Query t_6116 = SrcGlobal.from(t_6111).where(t_6115);
            SqlBuilder t_6117 = new SqlBuilder();
            t_6117.appendSafe("active = ");
            t_6117.appendBoolean(true);
            Query t_6121 = t_6116.where(t_6117.getAccumulated());
            SqlBuilder t_6122 = new SqlBuilder();
            t_6122.appendSafe("vip = ");
            t_6122.appendBoolean(true);
            Query q__856 = t_6121.orWhere(t_6122.getAccumulated());
            boolean t_6130 = q__856.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE OR vip = TRUE");
            Supplier<String> fn__6110 = () -> "mixed where and orWhere";
            test_72.assert_(t_6130, fn__6110);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNull__1289() {
        Test test_73 = new Test();
        try {
            SafeIdentifier t_6102 = SrcTest.sid__400("users");
            SafeIdentifier t_6103 = SrcTest.sid__400("deleted_at");
            Query q__858 = SrcGlobal.from(t_6102).whereNull(t_6103);
            boolean t_6108 = q__858.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL");
            Supplier<String> fn__6101 = () -> "whereNull";
            test_73.assert_(t_6108, fn__6101);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNull__1290() {
        Test test_74 = new Test();
        try {
            SafeIdentifier t_6093 = SrcTest.sid__400("users");
            SafeIdentifier t_6094 = SrcTest.sid__400("email");
            Query q__860 = SrcGlobal.from(t_6093).whereNotNull(t_6094);
            boolean t_6099 = q__860.toSql().toString().equals("SELECT * FROM users WHERE email IS NOT NULL");
            Supplier<String> fn__6092 = () -> "whereNotNull";
            test_74.assert_(t_6099, fn__6092);
        } finally {
            test_74.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNullChainedWithWhere__1291() {
        Test test_75 = new Test();
        try {
            SafeIdentifier t_6079 = SrcTest.sid__400("users");
            SqlBuilder t_6080 = new SqlBuilder();
            t_6080.appendSafe("active = ");
            t_6080.appendBoolean(true);
            SqlFragment t_6083 = t_6080.getAccumulated();
            Query q__862 = SrcGlobal.from(t_6079).where(t_6083).whereNull(SrcTest.sid__400("deleted_at"));
            boolean t_6090 = q__862.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND deleted_at IS NULL");
            Supplier<String> fn__6078 = () -> "whereNull chained";
            test_75.assert_(t_6090, fn__6078);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNullChainedWithOrWhere__1293() {
        Test test_76 = new Test();
        try {
            SafeIdentifier t_6065 = SrcTest.sid__400("users");
            SafeIdentifier t_6066 = SrcTest.sid__400("deleted_at");
            Query t_6067 = SrcGlobal.from(t_6065).whereNull(t_6066);
            SqlBuilder t_6068 = new SqlBuilder();
            t_6068.appendSafe("role = ");
            t_6068.appendString("admin");
            Query q__864 = t_6067.orWhere(t_6068.getAccumulated());
            boolean t_6076 = q__864.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL OR role = 'admin'");
            Supplier<String> fn__6064 = () -> "whereNotNull with orWhere";
            test_76.assert_(t_6076, fn__6064);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithIntValues__1295() {
        Test test_77 = new Test();
        try {
            SafeIdentifier t_6053 = SrcTest.sid__400("users");
            SafeIdentifier t_6054 = SrcTest.sid__400("id");
            SqlInt32 t_6055 = new SqlInt32(1);
            SqlInt32 t_6056 = new SqlInt32(2);
            SqlInt32 t_6057 = new SqlInt32(3);
            Query q__866 = SrcGlobal.from(t_6053).whereIn(t_6054, List.of(t_6055, t_6056, t_6057));
            boolean t_6062 = q__866.toSql().toString().equals("SELECT * FROM users WHERE id IN (1, 2, 3)");
            Supplier<String> fn__6052 = () -> "whereIn ints";
            test_77.assert_(t_6062, fn__6052);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithStringValuesEscaping__1296() {
        Test test_78 = new Test();
        try {
            SafeIdentifier t_6042 = SrcTest.sid__400("users");
            SafeIdentifier t_6043 = SrcTest.sid__400("name");
            SqlString t_6044 = new SqlString("Alice");
            SqlString t_6045 = new SqlString("Bob's");
            Query q__868 = SrcGlobal.from(t_6042).whereIn(t_6043, List.of(t_6044, t_6045));
            boolean t_6050 = q__868.toSql().toString().equals("SELECT * FROM users WHERE name IN ('Alice', 'Bob''s')");
            Supplier<String> fn__6041 = () -> "whereIn strings";
            test_78.assert_(t_6050, fn__6041);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithEmptyListProduces1_0__1297() {
        Test test_79 = new Test();
        try {
            SafeIdentifier t_6033 = SrcTest.sid__400("users");
            SafeIdentifier t_6034 = SrcTest.sid__400("id");
            Query q__870 = SrcGlobal.from(t_6033).whereIn(t_6034, List.of());
            boolean t_6039 = q__870.toSql().toString().equals("SELECT * FROM users WHERE 1 = 0");
            Supplier<String> fn__6032 = () -> "whereIn empty";
            test_79.assert_(t_6039, fn__6032);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInChained__1298() {
        Test test_80 = new Test();
        try {
            SafeIdentifier t_6017 = SrcTest.sid__400("users");
            SqlBuilder t_6018 = new SqlBuilder();
            t_6018.appendSafe("active = ");
            t_6018.appendBoolean(true);
            SqlFragment t_6021 = t_6018.getAccumulated();
            Query q__872 = SrcGlobal.from(t_6017).where(t_6021).whereIn(SrcTest.sid__400("role"), List.of(new SqlString("admin"), new SqlString("user")));
            boolean t_6030 = q__872.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND role IN ('admin', 'user')");
            Supplier<String> fn__6016 = () -> "whereIn chained";
            test_80.assert_(t_6030, fn__6016);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSingleElement__1300() {
        Test test_81 = new Test();
        try {
            SafeIdentifier t_6007 = SrcTest.sid__400("users");
            SafeIdentifier t_6008 = SrcTest.sid__400("id");
            SqlInt32 t_6009 = new SqlInt32(42);
            Query q__874 = SrcGlobal.from(t_6007).whereIn(t_6008, List.of(t_6009));
            boolean t_6014 = q__874.toSql().toString().equals("SELECT * FROM users WHERE id IN (42)");
            Supplier<String> fn__6006 = () -> "whereIn single";
            test_81.assert_(t_6014, fn__6006);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotBasic__1301() {
        Test test_82 = new Test();
        try {
            SafeIdentifier t_5995 = SrcTest.sid__400("users");
            SqlBuilder t_5996 = new SqlBuilder();
            t_5996.appendSafe("active = ");
            t_5996.appendBoolean(true);
            SqlFragment t_5999 = t_5996.getAccumulated();
            Query q__876 = SrcGlobal.from(t_5995).whereNot(t_5999);
            boolean t_6004 = q__876.toSql().toString().equals("SELECT * FROM users WHERE NOT (active = TRUE)");
            Supplier<String> fn__5994 = () -> "whereNot";
            test_82.assert_(t_6004, fn__5994);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotChained__1303() {
        Test test_83 = new Test();
        try {
            SafeIdentifier t_5978 = SrcTest.sid__400("users");
            SqlBuilder t_5979 = new SqlBuilder();
            t_5979.appendSafe("age > ");
            t_5979.appendInt32(18);
            SqlFragment t_5982 = t_5979.getAccumulated();
            Query t_5983 = SrcGlobal.from(t_5978).where(t_5982);
            SqlBuilder t_5984 = new SqlBuilder();
            t_5984.appendSafe("banned = ");
            t_5984.appendBoolean(true);
            Query q__878 = t_5983.whereNot(t_5984.getAccumulated());
            boolean t_5992 = q__878.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND NOT (banned = TRUE)");
            Supplier<String> fn__5977 = () -> "whereNot chained";
            test_83.assert_(t_5992, fn__5977);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenIntegers__1306() {
        Test test_84 = new Test();
        try {
            SafeIdentifier t_5967 = SrcTest.sid__400("users");
            SafeIdentifier t_5968 = SrcTest.sid__400("age");
            SqlInt32 t_5969 = new SqlInt32(18);
            SqlInt32 t_5970 = new SqlInt32(65);
            Query q__880 = SrcGlobal.from(t_5967).whereBetween(t_5968, t_5969, t_5970);
            boolean t_5975 = q__880.toSql().toString().equals("SELECT * FROM users WHERE age BETWEEN 18 AND 65");
            Supplier<String> fn__5966 = () -> "whereBetween ints";
            test_84.assert_(t_5975, fn__5966);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenChained__1307() {
        Test test_85 = new Test();
        try {
            SafeIdentifier t_5951 = SrcTest.sid__400("users");
            SqlBuilder t_5952 = new SqlBuilder();
            t_5952.appendSafe("active = ");
            t_5952.appendBoolean(true);
            SqlFragment t_5955 = t_5952.getAccumulated();
            Query q__882 = SrcGlobal.from(t_5951).where(t_5955).whereBetween(SrcTest.sid__400("age"), new SqlInt32(21), new SqlInt32(30));
            boolean t_5964 = q__882.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND age BETWEEN 21 AND 30");
            Supplier<String> fn__5950 = () -> "whereBetween chained";
            test_85.assert_(t_5964, fn__5950);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeBasic__1309() {
        Test test_86 = new Test();
        try {
            SafeIdentifier t_5942 = SrcTest.sid__400("users");
            SafeIdentifier t_5943 = SrcTest.sid__400("name");
            Query q__884 = SrcGlobal.from(t_5942).whereLike(t_5943, "John%");
            boolean t_5948 = q__884.toSql().toString().equals("SELECT * FROM users WHERE name LIKE 'John%'");
            Supplier<String> fn__5941 = () -> "whereLike";
            test_86.assert_(t_5948, fn__5941);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereIlikeBasic__1310() {
        Test test_87 = new Test();
        try {
            SafeIdentifier t_5933 = SrcTest.sid__400("users");
            SafeIdentifier t_5934 = SrcTest.sid__400("email");
            Query q__886 = SrcGlobal.from(t_5933).whereILike(t_5934, "%@gmail.com");
            boolean t_5939 = q__886.toSql().toString().equals("SELECT * FROM users WHERE email ILIKE '%@gmail.com'");
            Supplier<String> fn__5932 = () -> "whereILike";
            test_87.assert_(t_5939, fn__5932);
        } finally {
            test_87.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWithInjectionAttempt__1311() {
        Test test_88 = new Test();
        try {
            SafeIdentifier t_5919 = SrcTest.sid__400("users");
            SafeIdentifier t_5920 = SrcTest.sid__400("name");
            Query q__888 = SrcGlobal.from(t_5919).whereLike(t_5920, "'; DROP TABLE users; --");
            String s__889 = q__888.toSql().toString();
            boolean t_5925 = s__889.indexOf("''") >= 0;
            Supplier<String> fn__5918 = () -> "like injection escaped: " + s__889;
            test_88.assert_(t_5925, fn__5918);
            boolean t_5929 = s__889.indexOf("LIKE") >= 0;
            Supplier<String> fn__5917 = () -> "like structure intact: " + s__889;
            test_88.assert_(t_5929, fn__5917);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWildcardPatterns__1312() {
        Test test_89 = new Test();
        try {
            SafeIdentifier t_5909 = SrcTest.sid__400("users");
            SafeIdentifier t_5910 = SrcTest.sid__400("name");
            Query q__891 = SrcGlobal.from(t_5909).whereLike(t_5910, "%son%");
            boolean t_5915 = q__891.toSql().toString().equals("SELECT * FROM users WHERE name LIKE '%son%'");
            Supplier<String> fn__5908 = () -> "whereLike wildcard";
            test_89.assert_(t_5915, fn__5908);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__1313() {
        Test test_96 = new Test();
        try {
            SafeIdentifier t_3023;
            t_3023 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__929 = t_3023;
            boolean t_5906 = id__929.getSqlValue().equals("user_name");
            Supplier<String> fn__5903 = () -> "value should round-trip";
            test_96.assert_(t_5906, fn__5903);
        } finally {
            test_96.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__1314() {
        Test test_97 = new Test();
        try {
            boolean didBubble__931;
            boolean didBubble_7271;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_7271 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_7271 = true;
            }
            didBubble__931 = didBubble_7271;
            Supplier<String> fn__5900 = () -> "empty string should bubble";
            test_97.assert_(didBubble__931, fn__5900);
        } finally {
            test_97.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__1315() {
        Test test_98 = new Test();
        try {
            boolean didBubble__933;
            boolean didBubble_7272;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_7272 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_7272 = true;
            }
            didBubble__933 = didBubble_7272;
            Supplier<String> fn__5897 = () -> "leading digit should bubble";
            test_98.assert_(didBubble__933, fn__5897);
        } finally {
            test_98.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__1316() {
        Test test_99 = new Test();
        try {
            List<String> cases__935 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__5894 = c__936 -> {
                boolean didBubble__937;
                boolean didBubble_7273;
                try {
                    SrcGlobal.safeIdentifier(c__936);
                    didBubble_7273 = false;
                } catch (RuntimeException ignored$13) {
                    didBubble_7273 = true;
                }
                didBubble__937 = didBubble_7273;
                Supplier<String> fn__5891 = () -> "should reject: " + c__936;
                test_99.assert_(didBubble__937, fn__5891);
            };
            cases__935.forEach(fn__5894);
        } finally {
            test_99.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__1317() {
        Test test_100 = new Test();
        try {
            SafeIdentifier t_3000;
            t_3000 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_3001 = t_3000;
            SafeIdentifier t_3002;
            t_3002 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_3003 = t_3002;
            StringField t_5881 = new StringField();
            FieldDef t_5882 = new FieldDef(t_3003, t_5881, false);
            SafeIdentifier t_3006;
            t_3006 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_3007 = t_3006;
            IntField t_5883 = new IntField();
            FieldDef t_5884 = new FieldDef(t_3007, t_5883, false);
            TableDef td__939 = new TableDef(t_3001, List.of(t_5882, t_5884));
            FieldDef t_3011;
            t_3011 = td__939.field("age");
            FieldDef f__940 = t_3011;
            boolean t_5889 = f__940.getName().getSqlValue().equals("age");
            Supplier<String> fn__5880 = () -> "should find age field";
            test_100.assert_(t_5889, fn__5880);
        } finally {
            test_100.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__1318() {
        Test test_101 = new Test();
        try {
            SafeIdentifier t_2991;
            t_2991 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_2992 = t_2991;
            SafeIdentifier t_2993;
            t_2993 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_2994 = t_2993;
            StringField t_5875 = new StringField();
            FieldDef t_5876 = new FieldDef(t_2994, t_5875, false);
            TableDef td__942 = new TableDef(t_2992, List.of(t_5876));
            boolean didBubble__943;
            boolean didBubble_7274;
            try {
                td__942.field("nonexistent");
                didBubble_7274 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_7274 = true;
            }
            didBubble__943 = didBubble_7274;
            Supplier<String> fn__5874 = () -> "unknown field should bubble";
            test_101.assert_(didBubble__943, fn__5874);
        } finally {
            test_101.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__1319() {
        Test test_102 = new Test();
        try {
            SafeIdentifier t_2979;
            t_2979 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_2980 = t_2979;
            StringField t_5863 = new StringField();
            FieldDef required__945 = new FieldDef(t_2980, t_5863, false);
            SafeIdentifier t_2983;
            t_2983 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_2984 = t_2983;
            StringField t_5865 = new StringField();
            FieldDef optional__946 = new FieldDef(t_2984, t_5865, true);
            boolean t_5869 = !required__945.isNullable();
            Supplier<String> fn__5862 = () -> "required field should not be nullable";
            test_102.assert_(t_5869, fn__5862);
            boolean t_5871 = optional__946.isNullable();
            Supplier<String> fn__5861 = () -> "optional field should be nullable";
            test_102.assert_(t_5871, fn__5861);
        } finally {
            test_102.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__1320() {
        Test test_106 = new Test();
        try {
            Function<String, String> build__1072 = name__1074 -> {
                SqlBuilder t_5843 = new SqlBuilder();
                t_5843.appendSafe("select * from hi where name = ");
                t_5843.appendString(name__1074);
                return t_5843.getAccumulated().toString();
            };
            Function<String, String> buildWrong__1073 = name__1076 -> "select * from hi where name = '" + name__1076 + "'";
            String actual_1322 = build__1072.apply("world");
            boolean t_5853 = actual_1322.equals("select * from hi where name = 'world'");
            Supplier<String> fn__5850 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_1322 + ")";
            test_106.assert_(t_5853, fn__5850);
            String bobbyTables__1078 = "Robert'); drop table hi;--";
            String actual_1324 = build__1072.apply("Robert'); drop table hi;--");
            boolean t_5857 = actual_1324.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__5849 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_1324 + ")";
            test_106.assert_(t_5857, fn__5849);
            Supplier<String> fn__5848 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_106.assert_(true, fn__5848);
        } finally {
            test_106.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__1328() {
        Test test_107 = new Test();
        try {
            SqlBuilder t_5811 = new SqlBuilder();
            t_5811.appendSafe("v = ");
            t_5811.appendString("");
            String actual_1329 = t_5811.getAccumulated().toString();
            boolean t_5817 = actual_1329.equals("v = ''");
            Supplier<String> fn__5810 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_1329 + ")";
            test_107.assert_(t_5817, fn__5810);
            SqlBuilder t_5819 = new SqlBuilder();
            t_5819.appendSafe("v = ");
            t_5819.appendString("a''b");
            String actual_1332 = t_5819.getAccumulated().toString();
            boolean t_5825 = actual_1332.equals("v = 'a''''b'");
            Supplier<String> fn__5809 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_1332 + ")";
            test_107.assert_(t_5825, fn__5809);
            SqlBuilder t_5827 = new SqlBuilder();
            t_5827.appendSafe("v = ");
            t_5827.appendString("Hello \u4e16\u754c");
            String actual_1335 = t_5827.getAccumulated().toString();
            boolean t_5833 = actual_1335.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__5808 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_1335 + ")";
            test_107.assert_(t_5833, fn__5808);
            SqlBuilder t_5835 = new SqlBuilder();
            t_5835.appendSafe("v = ");
            t_5835.appendString("Line1\nLine2");
            String actual_1338 = t_5835.getAccumulated().toString();
            boolean t_5841 = actual_1338.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__5807 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_1338 + ")";
            test_107.assert_(t_5841, fn__5807);
        } finally {
            test_107.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__1341() {
        Test test_108 = new Test();
        try {
            SqlBuilder t_5782 = new SqlBuilder();
            t_5782.appendSafe("select ");
            t_5782.appendInt32(42);
            t_5782.appendSafe(", ");
            t_5782.appendInt64(43);
            t_5782.appendSafe(", ");
            t_5782.appendFloat64(19.99D);
            t_5782.appendSafe(", ");
            t_5782.appendBoolean(true);
            t_5782.appendSafe(", ");
            t_5782.appendBoolean(false);
            String actual_1342 = t_5782.getAccumulated().toString();
            boolean t_5796 = actual_1342.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__5781 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_1342 + ")";
            test_108.assert_(t_5796, fn__5781);
            LocalDate t_2924;
            t_2924 = LocalDate.of(2024, 12, 25);
            LocalDate date__1081 = t_2924;
            SqlBuilder t_5798 = new SqlBuilder();
            t_5798.appendSafe("insert into t values (");
            t_5798.appendDate(date__1081);
            t_5798.appendSafe(")");
            String actual_1345 = t_5798.getAccumulated().toString();
            boolean t_5805 = actual_1345.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__5780 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_1345 + ")";
            test_108.assert_(t_5805, fn__5780);
        } finally {
            test_108.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__1348() {
        Test test_109 = new Test();
        try {
            SqlBuilder t_5726 = new SqlBuilder();
            t_5726.appendSafe("v IN (");
            t_5726.appendStringList(List.of("a", "b", "c'd"));
            t_5726.appendSafe(")");
            String actual_1349 = t_5726.getAccumulated().toString();
            boolean t_5733 = actual_1349.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__5725 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_1349 + ")";
            test_109.assert_(t_5733, fn__5725);
            SqlBuilder t_5735 = new SqlBuilder();
            t_5735.appendSafe("v IN (");
            t_5735.appendInt32List(List.of(1, 2, 3));
            t_5735.appendSafe(")");
            String actual_1352 = t_5735.getAccumulated().toString();
            boolean t_5742 = actual_1352.equals("v IN (1, 2, 3)");
            Supplier<String> fn__5724 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_1352 + ")";
            test_109.assert_(t_5742, fn__5724);
            SqlBuilder t_5744 = new SqlBuilder();
            t_5744.appendSafe("v IN (");
            t_5744.appendInt64List(List.of(1, 2));
            t_5744.appendSafe(")");
            String actual_1355 = t_5744.getAccumulated().toString();
            boolean t_5751 = actual_1355.equals("v IN (1, 2)");
            Supplier<String> fn__5723 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_1355 + ")";
            test_109.assert_(t_5751, fn__5723);
            SqlBuilder t_5753 = new SqlBuilder();
            t_5753.appendSafe("v IN (");
            t_5753.appendFloat64List(List.of(1.0D, 2.0D));
            t_5753.appendSafe(")");
            String actual_1358 = t_5753.getAccumulated().toString();
            boolean t_5760 = actual_1358.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__5722 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_1358 + ")";
            test_109.assert_(t_5760, fn__5722);
            SqlBuilder t_5762 = new SqlBuilder();
            t_5762.appendSafe("v IN (");
            t_5762.appendBooleanList(List.of(true, false));
            t_5762.appendSafe(")");
            String actual_1361 = t_5762.getAccumulated().toString();
            boolean t_5769 = actual_1361.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__5721 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_1361 + ")";
            test_109.assert_(t_5769, fn__5721);
            LocalDate t_2896;
            t_2896 = LocalDate.of(2024, 1, 1);
            LocalDate t_2897 = t_2896;
            LocalDate t_2898;
            t_2898 = LocalDate.of(2024, 12, 25);
            LocalDate t_2899 = t_2898;
            List<LocalDate> dates__1083 = List.of(t_2897, t_2899);
            SqlBuilder t_5771 = new SqlBuilder();
            t_5771.appendSafe("v IN (");
            t_5771.appendDateList(dates__1083);
            t_5771.appendSafe(")");
            String actual_1364 = t_5771.getAccumulated().toString();
            boolean t_5778 = actual_1364.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__5720 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_1364 + ")";
            test_109.assert_(t_5778, fn__5720);
        } finally {
            test_109.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__1367() {
        Test test_110 = new Test();
        try {
            double nan__1085;
            nan__1085 = 0.0D / 0.0D;
            SqlBuilder t_5712 = new SqlBuilder();
            t_5712.appendSafe("v = ");
            t_5712.appendFloat64(nan__1085);
            String actual_1368 = t_5712.getAccumulated().toString();
            boolean t_5718 = actual_1368.equals("v = NULL");
            Supplier<String> fn__5711 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_1368 + ")";
            test_110.assert_(t_5718, fn__5711);
        } finally {
            test_110.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__1371() {
        Test test_111 = new Test();
        try {
            double inf__1087;
            inf__1087 = 1.0D / 0.0D;
            SqlBuilder t_5703 = new SqlBuilder();
            t_5703.appendSafe("v = ");
            t_5703.appendFloat64(inf__1087);
            String actual_1372 = t_5703.getAccumulated().toString();
            boolean t_5709 = actual_1372.equals("v = NULL");
            Supplier<String> fn__5702 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_1372 + ")";
            test_111.assert_(t_5709, fn__5702);
        } finally {
            test_111.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__1375() {
        Test test_112 = new Test();
        try {
            double ninf__1089;
            ninf__1089 = -1.0D / 0.0D;
            SqlBuilder t_5694 = new SqlBuilder();
            t_5694.appendSafe("v = ");
            t_5694.appendFloat64(ninf__1089);
            String actual_1376 = t_5694.getAccumulated().toString();
            boolean t_5700 = actual_1376.equals("v = NULL");
            Supplier<String> fn__5693 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_1376 + ")";
            test_112.assert_(t_5700, fn__5693);
        } finally {
            test_112.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__1379() {
        Test test_113 = new Test();
        try {
            SqlBuilder t_5669 = new SqlBuilder();
            t_5669.appendSafe("v = ");
            t_5669.appendFloat64(3.14D);
            String actual_1380 = t_5669.getAccumulated().toString();
            boolean t_5675 = actual_1380.equals("v = 3.14");
            Supplier<String> fn__5668 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_1380 + ")";
            test_113.assert_(t_5675, fn__5668);
            SqlBuilder t_5677 = new SqlBuilder();
            t_5677.appendSafe("v = ");
            t_5677.appendFloat64(0.0D);
            String actual_1383 = t_5677.getAccumulated().toString();
            boolean t_5683 = actual_1383.equals("v = 0.0");
            Supplier<String> fn__5667 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_1383 + ")";
            test_113.assert_(t_5683, fn__5667);
            SqlBuilder t_5685 = new SqlBuilder();
            t_5685.appendSafe("v = ");
            t_5685.appendFloat64(-42.5D);
            String actual_1386 = t_5685.getAccumulated().toString();
            boolean t_5691 = actual_1386.equals("v = -42.5");
            Supplier<String> fn__5666 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_1386 + ")";
            test_113.assert_(t_5691, fn__5666);
        } finally {
            test_113.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__1389() {
        Test test_114 = new Test();
        try {
            LocalDate t_2792;
            t_2792 = LocalDate.of(2024, 6, 15);
            LocalDate d__1092 = t_2792;
            SqlBuilder t_5658 = new SqlBuilder();
            t_5658.appendSafe("v = ");
            t_5658.appendDate(d__1092);
            String actual_1390 = t_5658.getAccumulated().toString();
            boolean t_5664 = actual_1390.equals("v = '2024-06-15'");
            Supplier<String> fn__5657 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_1390 + ")";
            test_114.assert_(t_5664, fn__5657);
        } finally {
            test_114.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__1393() {
        Test test_115 = new Test();
        try {
            String name__1094 = "Someone";
            SqlBuilder t_5626 = new SqlBuilder();
            t_5626.appendSafe("where p.last_name = ");
            t_5626.appendString("Someone");
            SqlFragment condition__1095 = t_5626.getAccumulated();
            SqlBuilder t_5630 = new SqlBuilder();
            t_5630.appendSafe("select p.id from person p ");
            t_5630.appendFragment(condition__1095);
            String actual_1395 = t_5630.getAccumulated().toString();
            boolean t_5636 = actual_1395.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__5625 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1395 + ")";
            test_115.assert_(t_5636, fn__5625);
            SqlBuilder t_5638 = new SqlBuilder();
            t_5638.appendSafe("select p.id from person p ");
            t_5638.appendPart(condition__1095.toSource());
            String actual_1398 = t_5638.getAccumulated().toString();
            boolean t_5645 = actual_1398.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__5624 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1398 + ")";
            test_115.assert_(t_5645, fn__5624);
            List<SqlPart> parts__1096 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_5649 = new SqlBuilder();
            t_5649.appendSafe("select ");
            t_5649.appendPartList(parts__1096);
            String actual_1401 = t_5649.getAccumulated().toString();
            boolean t_5655 = actual_1401.equals("select 'a''b', 3");
            Supplier<String> fn__5623 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_1401 + ")";
            test_115.assert_(t_5655, fn__5623);
        } finally {
            test_115.softFailToHard();
        }
    }
}
