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
    static SafeIdentifier csid__503(String name__648) {
        SafeIdentifier t_6032;
        t_6032 = SrcGlobal.safeIdentifier(name__648);
        return t_6032;
    }
    static TableDef userTable__504() {
        return new TableDef(SrcTest.csid__503("users"), List.of(new FieldDef(SrcTest.csid__503("name"), new StringField(), false), new FieldDef(SrcTest.csid__503("email"), new StringField(), false), new FieldDef(SrcTest.csid__503("age"), new IntField(), true), new FieldDef(SrcTest.csid__503("score"), new FloatField(), true), new FieldDef(SrcTest.csid__503("active"), new BoolField(), true)));
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__1546() {
        Test test_24 = new Test();
        try {
            Map<String, String> params__652 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_10651 = SrcTest.userTable__504();
            SafeIdentifier t_10652 = SrcTest.csid__503("name");
            SafeIdentifier t_10653 = SrcTest.csid__503("email");
            Changeset cs__653 = SrcGlobal.changeset(t_10651, params__652).cast(List.of(t_10652, t_10653));
            boolean t_10656 = cs__653.getChanges().containsKey("name");
            Supplier<String> fn__10646 = () -> "name should be in changes";
            test_24.assert_(t_10656, fn__10646);
            boolean t_10660 = cs__653.getChanges().containsKey("email");
            Supplier<String> fn__10645 = () -> "email should be in changes";
            test_24.assert_(t_10660, fn__10645);
            boolean t_10666 = !cs__653.getChanges().containsKey("admin");
            Supplier<String> fn__10644 = () -> "admin must be dropped (not in whitelist)";
            test_24.assert_(t_10666, fn__10644);
            boolean t_10668 = cs__653.isValid();
            Supplier<String> fn__10643 = () -> "should still be valid";
            test_24.assert_(t_10668, fn__10643);
        } finally {
            test_24.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__1547() {
        Test test_25 = new Test();
        try {
            Map<String, String> params__655 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_10629 = SrcTest.userTable__504();
            SafeIdentifier t_10630 = SrcTest.csid__503("name");
            Changeset cs__656 = SrcGlobal.changeset(t_10629, params__655).cast(List.of(t_10630)).cast(List.of(SrcTest.csid__503("email")));
            boolean t_10637 = !cs__656.getChanges().containsKey("name");
            Supplier<String> fn__10625 = () -> "name must be excluded by second cast";
            test_25.assert_(t_10637, fn__10625);
            boolean t_10640 = cs__656.getChanges().containsKey("email");
            Supplier<String> fn__10624 = () -> "email should be present";
            test_25.assert_(t_10640, fn__10624);
        } finally {
            test_25.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__1548() {
        Test test_26 = new Test();
        try {
            Map<String, String> params__658 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_10611 = SrcTest.userTable__504();
            SafeIdentifier t_10612 = SrcTest.csid__503("name");
            SafeIdentifier t_10613 = SrcTest.csid__503("email");
            Changeset cs__659 = SrcGlobal.changeset(t_10611, params__658).cast(List.of(t_10612, t_10613));
            boolean t_10618 = !cs__659.getChanges().containsKey("name");
            Supplier<String> fn__10607 = () -> "empty name should not be in changes";
            test_26.assert_(t_10618, fn__10607);
            boolean t_10621 = cs__659.getChanges().containsKey("email");
            Supplier<String> fn__10606 = () -> "email should be in changes";
            test_26.assert_(t_10621, fn__10606);
        } finally {
            test_26.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__1549() {
        Test test_27 = new Test();
        try {
            Map<String, String> params__661 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_10593 = SrcTest.userTable__504();
            SafeIdentifier t_10594 = SrcTest.csid__503("name");
            Changeset cs__662 = SrcGlobal.changeset(t_10593, params__661).cast(List.of(t_10594)).validateRequired(List.of(SrcTest.csid__503("name")));
            boolean t_10598 = cs__662.isValid();
            Supplier<String> fn__10590 = () -> "should be valid";
            test_27.assert_(t_10598, fn__10590);
            boolean t_10604 = cs__662.getErrors().size() == 0;
            Supplier<String> fn__10589 = () -> "no errors expected";
            test_27.assert_(t_10604, fn__10589);
        } finally {
            test_27.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__1550() {
        Test test_28 = new Test();
        try {
            Map<String, String> params__664 = Core.mapConstructor(List.of());
            TableDef t_10569 = SrcTest.userTable__504();
            SafeIdentifier t_10570 = SrcTest.csid__503("name");
            Changeset cs__665 = SrcGlobal.changeset(t_10569, params__664).cast(List.of(t_10570)).validateRequired(List.of(SrcTest.csid__503("name")));
            boolean t_10576 = !cs__665.isValid();
            Supplier<String> fn__10567 = () -> "should be invalid";
            test_28.assert_(t_10576, fn__10567);
            boolean t_10581 = cs__665.getErrors().size() == 1;
            Supplier<String> fn__10566 = () -> "should have one error";
            test_28.assert_(t_10581, fn__10566);
            boolean t_10587 = Core.listGet(cs__665.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__10565 = () -> "error should name the field";
            test_28.assert_(t_10587, fn__10565);
        } finally {
            test_28.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__1551() {
        Test test_29 = new Test();
        try {
            Map<String, String> params__667 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_10557 = SrcTest.userTable__504();
            SafeIdentifier t_10558 = SrcTest.csid__503("name");
            Changeset cs__668 = SrcGlobal.changeset(t_10557, params__667).cast(List.of(t_10558)).validateLength(SrcTest.csid__503("name"), 2, 50);
            boolean t_10562 = cs__668.isValid();
            Supplier<String> fn__10554 = () -> "should be valid";
            test_29.assert_(t_10562, fn__10554);
        } finally {
            test_29.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__1552() {
        Test test_30 = new Test();
        try {
            Map<String, String> params__670 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_10545 = SrcTest.userTable__504();
            SafeIdentifier t_10546 = SrcTest.csid__503("name");
            Changeset cs__671 = SrcGlobal.changeset(t_10545, params__670).cast(List.of(t_10546)).validateLength(SrcTest.csid__503("name"), 2, 50);
            boolean t_10552 = !cs__671.isValid();
            Supplier<String> fn__10542 = () -> "should be invalid";
            test_30.assert_(t_10552, fn__10542);
        } finally {
            test_30.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__1553() {
        Test test_31 = new Test();
        try {
            Map<String, String> params__673 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_10533 = SrcTest.userTable__504();
            SafeIdentifier t_10534 = SrcTest.csid__503("name");
            Changeset cs__674 = SrcGlobal.changeset(t_10533, params__673).cast(List.of(t_10534)).validateLength(SrcTest.csid__503("name"), 2, 10);
            boolean t_10540 = !cs__674.isValid();
            Supplier<String> fn__10530 = () -> "should be invalid";
            test_31.assert_(t_10540, fn__10530);
        } finally {
            test_31.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__1554() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__676 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_10522 = SrcTest.userTable__504();
            SafeIdentifier t_10523 = SrcTest.csid__503("age");
            Changeset cs__677 = SrcGlobal.changeset(t_10522, params__676).cast(List.of(t_10523)).validateInt(SrcTest.csid__503("age"));
            boolean t_10527 = cs__677.isValid();
            Supplier<String> fn__10519 = () -> "should be valid";
            test_32.assert_(t_10527, fn__10519);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__1555() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__679 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_10510 = SrcTest.userTable__504();
            SafeIdentifier t_10511 = SrcTest.csid__503("age");
            Changeset cs__680 = SrcGlobal.changeset(t_10510, params__679).cast(List.of(t_10511)).validateInt(SrcTest.csid__503("age"));
            boolean t_10517 = !cs__680.isValid();
            Supplier<String> fn__10507 = () -> "should be invalid";
            test_33.assert_(t_10517, fn__10507);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__1556() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__682 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_10499 = SrcTest.userTable__504();
            SafeIdentifier t_10500 = SrcTest.csid__503("score");
            Changeset cs__683 = SrcGlobal.changeset(t_10499, params__682).cast(List.of(t_10500)).validateFloat(SrcTest.csid__503("score"));
            boolean t_10504 = cs__683.isValid();
            Supplier<String> fn__10496 = () -> "should be valid";
            test_34.assert_(t_10504, fn__10496);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__1557() {
        Test test_35 = new Test();
        try {
            Map<String, String> params__685 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_10488 = SrcTest.userTable__504();
            SafeIdentifier t_10489 = SrcTest.csid__503("age");
            Changeset cs__686 = SrcGlobal.changeset(t_10488, params__685).cast(List.of(t_10489)).validateInt64(SrcTest.csid__503("age"));
            boolean t_10493 = cs__686.isValid();
            Supplier<String> fn__10485 = () -> "should be valid";
            test_35.assert_(t_10493, fn__10485);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__1558() {
        Test test_36 = new Test();
        try {
            Map<String, String> params__688 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_10476 = SrcTest.userTable__504();
            SafeIdentifier t_10477 = SrcTest.csid__503("age");
            Changeset cs__689 = SrcGlobal.changeset(t_10476, params__688).cast(List.of(t_10477)).validateInt64(SrcTest.csid__503("age"));
            boolean t_10483 = !cs__689.isValid();
            Supplier<String> fn__10473 = () -> "should be invalid";
            test_36.assert_(t_10483, fn__10473);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__1559() {
        Test test_37 = new Test();
        try {
            Consumer<String> fn__10470 = v__691 -> {
                Map<String, String> params__692 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__691)));
                TableDef t_10462 = SrcTest.userTable__504();
                SafeIdentifier t_10463 = SrcTest.csid__503("active");
                Changeset cs__693 = SrcGlobal.changeset(t_10462, params__692).cast(List.of(t_10463)).validateBool(SrcTest.csid__503("active"));
                boolean t_10467 = cs__693.isValid();
                Supplier<String> fn__10459 = () -> "should accept: " + v__691;
                test_37.assert_(t_10467, fn__10459);
            };
            List.of("true", "1", "yes", "on").forEach(fn__10470);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__1560() {
        Test test_38 = new Test();
        try {
            Consumer<String> fn__10456 = v__695 -> {
                Map<String, String> params__696 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__695)));
                TableDef t_10448 = SrcTest.userTable__504();
                SafeIdentifier t_10449 = SrcTest.csid__503("active");
                Changeset cs__697 = SrcGlobal.changeset(t_10448, params__696).cast(List.of(t_10449)).validateBool(SrcTest.csid__503("active"));
                boolean t_10453 = cs__697.isValid();
                Supplier<String> fn__10445 = () -> "should accept: " + v__695;
                test_38.assert_(t_10453, fn__10445);
            };
            List.of("false", "0", "no", "off").forEach(fn__10456);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__1561() {
        Test test_39 = new Test();
        try {
            Consumer<String> fn__10442 = v__699 -> {
                Map<String, String> params__700 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__699)));
                TableDef t_10433 = SrcTest.userTable__504();
                SafeIdentifier t_10434 = SrcTest.csid__503("active");
                Changeset cs__701 = SrcGlobal.changeset(t_10433, params__700).cast(List.of(t_10434)).validateBool(SrcTest.csid__503("active"));
                boolean t_10440 = !cs__701.isValid();
                Supplier<String> fn__10430 = () -> "should reject ambiguous: " + v__699;
                test_39.assert_(t_10440, fn__10430);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__10442);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__1562() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__703 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_10418 = SrcTest.userTable__504();
            SafeIdentifier t_10419 = SrcTest.csid__503("name");
            SafeIdentifier t_10420 = SrcTest.csid__503("email");
            Changeset cs__704 = SrcGlobal.changeset(t_10418, params__703).cast(List.of(t_10419, t_10420)).validateRequired(List.of(SrcTest.csid__503("name"), SrcTest.csid__503("email")));
            SqlFragment t_5833;
            t_5833 = cs__704.toInsertSql();
            SqlFragment sqlFrag__705 = t_5833;
            String s__706 = sqlFrag__705.toString();
            boolean t_10427 = s__706.indexOf("''") >= 0;
            Supplier<String> fn__10414 = () -> "single quote must be doubled: " + s__706;
            test_40.assert_(t_10427, fn__10414);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__1563() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__708 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_10398 = SrcTest.userTable__504();
            SafeIdentifier t_10399 = SrcTest.csid__503("name");
            SafeIdentifier t_10400 = SrcTest.csid__503("email");
            Changeset cs__709 = SrcGlobal.changeset(t_10398, params__708).cast(List.of(t_10399, t_10400)).validateRequired(List.of(SrcTest.csid__503("name"), SrcTest.csid__503("email")));
            SqlFragment t_5812;
            t_5812 = cs__709.toInsertSql();
            SqlFragment sqlFrag__710 = t_5812;
            String s__711 = sqlFrag__710.toString();
            boolean t_10407 = s__711.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__10394 = () -> "has INSERT INTO: " + s__711;
            test_41.assert_(t_10407, fn__10394);
            boolean t_10411 = s__711.indexOf("'Alice'") >= 0;
            Supplier<String> fn__10393 = () -> "has quoted name: " + s__711;
            test_41.assert_(t_10411, fn__10393);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__1564() {
        Test test_42 = new Test();
        try {
            Map<String, String> params__713 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_10380 = SrcTest.userTable__504();
            SafeIdentifier t_10381 = SrcTest.csid__503("name");
            SafeIdentifier t_10382 = SrcTest.csid__503("email");
            SafeIdentifier t_10383 = SrcTest.csid__503("age");
            Changeset cs__714 = SrcGlobal.changeset(t_10380, params__713).cast(List.of(t_10381, t_10382, t_10383)).validateRequired(List.of(SrcTest.csid__503("name"), SrcTest.csid__503("email")));
            SqlFragment t_5795;
            t_5795 = cs__714.toInsertSql();
            SqlFragment sqlFrag__715 = t_5795;
            String s__716 = sqlFrag__715.toString();
            boolean t_10390 = s__716.indexOf("25") >= 0;
            Supplier<String> fn__10375 = () -> "age rendered unquoted: " + s__716;
            test_42.assert_(t_10390, fn__10375);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__1565() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__718 = Core.mapConstructor(List.of());
            TableDef t_10368 = SrcTest.userTable__504();
            SafeIdentifier t_10369 = SrcTest.csid__503("name");
            Changeset cs__719 = SrcGlobal.changeset(t_10368, params__718).cast(List.of(t_10369)).validateRequired(List.of(SrcTest.csid__503("name")));
            boolean didBubble__720;
            boolean didBubble_10969;
            try {
                cs__719.toInsertSql();
                didBubble_10969 = false;
            } catch (RuntimeException ignored$4) {
                didBubble_10969 = true;
            }
            didBubble__720 = didBubble_10969;
            Supplier<String> fn__10366 = () -> "invalid changeset should bubble";
            test_43.assert_(didBubble__720, fn__10366);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__1566() {
        Test test_44 = new Test();
        try {
            TableDef strictTable__722 = new TableDef(SrcTest.csid__503("posts"), List.of(new FieldDef(SrcTest.csid__503("title"), new StringField(), false), new FieldDef(SrcTest.csid__503("body"), new StringField(), true)));
            Map<String, String> params__723 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_10359 = SrcTest.csid__503("body");
            Changeset cs__724 = SrcGlobal.changeset(strictTable__722, params__723).cast(List.of(t_10359));
            boolean t_10361 = cs__724.isValid();
            Supplier<String> fn__10348 = () -> "changeset should appear valid (no explicit validation run)";
            test_44.assert_(t_10361, fn__10348);
            boolean didBubble__725;
            boolean didBubble_10970;
            try {
                cs__724.toInsertSql();
                didBubble_10970 = false;
            } catch (RuntimeException ignored$5) {
                didBubble_10970 = true;
            }
            didBubble__725 = didBubble_10970;
            Supplier<String> fn__10347 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_44.assert_(didBubble__725, fn__10347);
        } finally {
            test_44.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__1567() {
        Test test_45 = new Test();
        try {
            Map<String, String> params__727 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_10338 = SrcTest.userTable__504();
            SafeIdentifier t_10339 = SrcTest.csid__503("name");
            Changeset cs__728 = SrcGlobal.changeset(t_10338, params__727).cast(List.of(t_10339)).validateRequired(List.of(SrcTest.csid__503("name")));
            SqlFragment t_5755;
            t_5755 = cs__728.toUpdateSql(42);
            SqlFragment sqlFrag__729 = t_5755;
            String s__730 = sqlFrag__729.toString();
            boolean t_10345 = s__730.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__10335 = () -> "got: " + s__730;
            test_45.assert_(t_10345, fn__10335);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__1568() {
        Test test_46 = new Test();
        try {
            Map<String, String> params__732 = Core.mapConstructor(List.of());
            TableDef t_10328 = SrcTest.userTable__504();
            SafeIdentifier t_10329 = SrcTest.csid__503("name");
            Changeset cs__733 = SrcGlobal.changeset(t_10328, params__732).cast(List.of(t_10329)).validateRequired(List.of(SrcTest.csid__503("name")));
            boolean didBubble__734;
            boolean didBubble_10971;
            try {
                cs__733.toUpdateSql(1);
                didBubble_10971 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_10971 = true;
            }
            didBubble__734 = didBubble_10971;
            Supplier<String> fn__10326 = () -> "invalid changeset should bubble";
            test_46.assert_(didBubble__734, fn__10326);
        } finally {
            test_46.softFailToHard();
        }
    }
    static SafeIdentifier sid__505(String name__1039) {
        SafeIdentifier t_5241;
        t_5241 = SrcGlobal.safeIdentifier(name__1039);
        return t_5241;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__1644() {
        Test test_47 = new Test();
        try {
            Query q__1042 = SrcGlobal.from(SrcTest.sid__505("users"));
            boolean t_9837 = q__1042.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__9832 = () -> "bare query";
            test_47.assert_(t_9837, fn__9832);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__1645() {
        Test test_48 = new Test();
        try {
            SafeIdentifier t_9823 = SrcTest.sid__505("users");
            SafeIdentifier t_9824 = SrcTest.sid__505("id");
            SafeIdentifier t_9825 = SrcTest.sid__505("name");
            Query q__1044 = SrcGlobal.from(t_9823).select(List.of(t_9824, t_9825));
            boolean t_9830 = q__1044.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__9822 = () -> "select columns";
            test_48.assert_(t_9830, fn__9822);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__1646() {
        Test test_49 = new Test();
        try {
            SafeIdentifier t_9811 = SrcTest.sid__505("users");
            SqlBuilder t_9812 = new SqlBuilder();
            t_9812.appendSafe("age > ");
            t_9812.appendInt32(18);
            SqlFragment t_9815 = t_9812.getAccumulated();
            Query q__1046 = SrcGlobal.from(t_9811).where(t_9815);
            boolean t_9820 = q__1046.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__9810 = () -> "where int";
            test_49.assert_(t_9820, fn__9810);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__1648() {
        Test test_50 = new Test();
        try {
            SafeIdentifier t_9799 = SrcTest.sid__505("users");
            SqlBuilder t_9800 = new SqlBuilder();
            t_9800.appendSafe("active = ");
            t_9800.appendBoolean(true);
            SqlFragment t_9803 = t_9800.getAccumulated();
            Query q__1048 = SrcGlobal.from(t_9799).where(t_9803);
            boolean t_9808 = q__1048.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__9798 = () -> "where bool";
            test_50.assert_(t_9808, fn__9798);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__1650() {
        Test test_51 = new Test();
        try {
            SafeIdentifier t_9782 = SrcTest.sid__505("users");
            SqlBuilder t_9783 = new SqlBuilder();
            t_9783.appendSafe("age > ");
            t_9783.appendInt32(18);
            SqlFragment t_9786 = t_9783.getAccumulated();
            Query t_9787 = SrcGlobal.from(t_9782).where(t_9786);
            SqlBuilder t_9788 = new SqlBuilder();
            t_9788.appendSafe("active = ");
            t_9788.appendBoolean(true);
            Query q__1050 = t_9787.where(t_9788.getAccumulated());
            boolean t_9796 = q__1050.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__9781 = () -> "chained where";
            test_51.assert_(t_9796, fn__9781);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__1653() {
        Test test_52 = new Test();
        try {
            SafeIdentifier t_9773 = SrcTest.sid__505("users");
            SafeIdentifier t_9774 = SrcTest.sid__505("name");
            Query q__1052 = SrcGlobal.from(t_9773).orderBy(t_9774, true);
            boolean t_9779 = q__1052.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__9772 = () -> "order asc";
            test_52.assert_(t_9779, fn__9772);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__1654() {
        Test test_53 = new Test();
        try {
            SafeIdentifier t_9764 = SrcTest.sid__505("users");
            SafeIdentifier t_9765 = SrcTest.sid__505("created_at");
            Query q__1054 = SrcGlobal.from(t_9764).orderBy(t_9765, false);
            boolean t_9770 = q__1054.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__9763 = () -> "order desc";
            test_53.assert_(t_9770, fn__9763);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__1655() {
        Test test_54 = new Test();
        try {
            Query t_5175;
            t_5175 = SrcGlobal.from(SrcTest.sid__505("users")).limit(10);
            Query t_5176;
            t_5176 = t_5175.offset(20);
            Query q__1056 = t_5176;
            boolean t_9761 = q__1056.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__9756 = () -> "limit/offset";
            test_54.assert_(t_9761, fn__9756);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__1656() {
        Test test_55 = new Test();
        try {
            boolean didBubble__1058;
            boolean didBubble_10972;
            try {
                SrcGlobal.from(SrcTest.sid__505("users")).limit(-1);
                didBubble_10972 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_10972 = true;
            }
            didBubble__1058 = didBubble_10972;
            Supplier<String> fn__9752 = () -> "negative limit should bubble";
            test_55.assert_(didBubble__1058, fn__9752);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__1657() {
        Test test_56 = new Test();
        try {
            boolean didBubble__1060;
            boolean didBubble_10973;
            try {
                SrcGlobal.from(SrcTest.sid__505("users")).offset(-1);
                didBubble_10973 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_10973 = true;
            }
            didBubble__1060 = didBubble_10973;
            Supplier<String> fn__9748 = () -> "negative offset should bubble";
            test_56.assert_(didBubble__1060, fn__9748);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__1658() {
        Test test_57 = new Test();
        try {
            int minAge__1062 = 21;
            SafeIdentifier t_9726 = SrcTest.sid__505("users");
            SafeIdentifier t_9727 = SrcTest.sid__505("id");
            SafeIdentifier t_9728 = SrcTest.sid__505("name");
            SafeIdentifier t_9729 = SrcTest.sid__505("email");
            Query t_9730 = SrcGlobal.from(t_9726).select(List.of(t_9727, t_9728, t_9729));
            SqlBuilder t_9731 = new SqlBuilder();
            t_9731.appendSafe("age >= ");
            t_9731.appendInt32(21);
            Query t_9735 = t_9730.where(t_9731.getAccumulated());
            SqlBuilder t_9736 = new SqlBuilder();
            t_9736.appendSafe("active = ");
            t_9736.appendBoolean(true);
            Query t_5161;
            t_5161 = t_9735.where(t_9736.getAccumulated()).orderBy(SrcTest.sid__505("name"), true).limit(25);
            Query t_5162;
            t_5162 = t_5161.offset(0);
            Query q__1063 = t_5162;
            boolean t_9746 = q__1063.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__9725 = () -> "complex query";
            test_57.assert_(t_9746, fn__9725);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__1661() {
        Test test_58 = new Test();
        try {
            Query q__1065 = SrcGlobal.from(SrcTest.sid__505("users"));
            SqlFragment t_5138;
            t_5138 = q__1065.safeToSql(100);
            SqlFragment t_5139 = t_5138;
            String s__1066 = t_5139.toString();
            boolean t_9723 = s__1066.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__9719 = () -> "should have limit: " + s__1066;
            test_58.assert_(t_9723, fn__9719);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__1662() {
        Test test_59 = new Test();
        try {
            Query t_5130;
            t_5130 = SrcGlobal.from(SrcTest.sid__505("users")).limit(5);
            Query q__1068 = t_5130;
            SqlFragment t_5133;
            t_5133 = q__1068.safeToSql(100);
            SqlFragment t_5134 = t_5133;
            String s__1069 = t_5134.toString();
            boolean t_9717 = s__1069.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__9713 = () -> "explicit limit preserved: " + s__1069;
            test_59.assert_(t_9717, fn__9713);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__1663() {
        Test test_60 = new Test();
        try {
            boolean didBubble__1071;
            boolean didBubble_10974;
            try {
                SrcGlobal.from(SrcTest.sid__505("users")).safeToSql(-1);
                didBubble_10974 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_10974 = true;
            }
            didBubble__1071 = didBubble_10974;
            Supplier<String> fn__9709 = () -> "negative defaultLimit should bubble";
            test_60.assert_(didBubble__1071, fn__9709);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__1664() {
        Test test_61 = new Test();
        try {
            String evil__1073 = "'; DROP TABLE users; --";
            SafeIdentifier t_9693 = SrcTest.sid__505("users");
            SqlBuilder t_9694 = new SqlBuilder();
            t_9694.appendSafe("name = ");
            t_9694.appendString("'; DROP TABLE users; --");
            SqlFragment t_9697 = t_9694.getAccumulated();
            Query q__1074 = SrcGlobal.from(t_9693).where(t_9697);
            String s__1075 = q__1074.toSql().toString();
            boolean t_9702 = s__1075.indexOf("''") >= 0;
            Supplier<String> fn__9692 = () -> "quotes must be doubled: " + s__1075;
            test_61.assert_(t_9702, fn__9692);
            boolean t_9706 = s__1075.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__9691 = () -> "structure intact: " + s__1075;
            test_61.assert_(t_9706, fn__9691);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__1666() {
        Test test_62 = new Test();
        try {
            String attack__1077 = "users; DROP TABLE users; --";
            boolean didBubble__1078;
            boolean didBubble_10975;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_10975 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_10975 = true;
            }
            didBubble__1078 = didBubble_10975;
            Supplier<String> fn__9688 = () -> "metacharacter-containing name must be rejected at construction";
            test_62.assert_(didBubble__1078, fn__9688);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__1667() {
        Test test_63 = new Test();
        try {
            SafeIdentifier t_9677 = SrcTest.sid__505("users");
            SafeIdentifier t_9678 = SrcTest.sid__505("orders");
            SqlBuilder t_9679 = new SqlBuilder();
            t_9679.appendSafe("users.id = orders.user_id");
            SqlFragment t_9681 = t_9679.getAccumulated();
            Query q__1080 = SrcGlobal.from(t_9677).innerJoin(t_9678, t_9681);
            boolean t_9686 = q__1080.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__9676 = () -> "inner join";
            test_63.assert_(t_9686, fn__9676);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__1669() {
        Test test_64 = new Test();
        try {
            SafeIdentifier t_9665 = SrcTest.sid__505("users");
            SafeIdentifier t_9666 = SrcTest.sid__505("profiles");
            SqlBuilder t_9667 = new SqlBuilder();
            t_9667.appendSafe("users.id = profiles.user_id");
            SqlFragment t_9669 = t_9667.getAccumulated();
            Query q__1082 = SrcGlobal.from(t_9665).leftJoin(t_9666, t_9669);
            boolean t_9674 = q__1082.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__9664 = () -> "left join";
            test_64.assert_(t_9674, fn__9664);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__1671() {
        Test test_65 = new Test();
        try {
            SafeIdentifier t_9653 = SrcTest.sid__505("orders");
            SafeIdentifier t_9654 = SrcTest.sid__505("users");
            SqlBuilder t_9655 = new SqlBuilder();
            t_9655.appendSafe("orders.user_id = users.id");
            SqlFragment t_9657 = t_9655.getAccumulated();
            Query q__1084 = SrcGlobal.from(t_9653).rightJoin(t_9654, t_9657);
            boolean t_9662 = q__1084.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__9652 = () -> "right join";
            test_65.assert_(t_9662, fn__9652);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__1673() {
        Test test_66 = new Test();
        try {
            SafeIdentifier t_9641 = SrcTest.sid__505("users");
            SafeIdentifier t_9642 = SrcTest.sid__505("orders");
            SqlBuilder t_9643 = new SqlBuilder();
            t_9643.appendSafe("users.id = orders.user_id");
            SqlFragment t_9645 = t_9643.getAccumulated();
            Query q__1086 = SrcGlobal.from(t_9641).fullJoin(t_9642, t_9645);
            boolean t_9650 = q__1086.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__9640 = () -> "full join";
            test_66.assert_(t_9650, fn__9640);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__1675() {
        Test test_67 = new Test();
        try {
            SafeIdentifier t_9624 = SrcTest.sid__505("users");
            SafeIdentifier t_9625 = SrcTest.sid__505("orders");
            SqlBuilder t_9626 = new SqlBuilder();
            t_9626.appendSafe("users.id = orders.user_id");
            SqlFragment t_9628 = t_9626.getAccumulated();
            Query t_9629 = SrcGlobal.from(t_9624).innerJoin(t_9625, t_9628);
            SafeIdentifier t_9630 = SrcTest.sid__505("profiles");
            SqlBuilder t_9631 = new SqlBuilder();
            t_9631.appendSafe("users.id = profiles.user_id");
            Query q__1088 = t_9629.leftJoin(t_9630, t_9631.getAccumulated());
            boolean t_9638 = q__1088.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__9623 = () -> "chained joins";
            test_67.assert_(t_9638, fn__9623);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__1678() {
        Test test_68 = new Test();
        try {
            SafeIdentifier t_9605 = SrcTest.sid__505("users");
            SafeIdentifier t_9606 = SrcTest.sid__505("orders");
            SqlBuilder t_9607 = new SqlBuilder();
            t_9607.appendSafe("users.id = orders.user_id");
            SqlFragment t_9609 = t_9607.getAccumulated();
            Query t_9610 = SrcGlobal.from(t_9605).innerJoin(t_9606, t_9609);
            SqlBuilder t_9611 = new SqlBuilder();
            t_9611.appendSafe("orders.total > ");
            t_9611.appendInt32(100);
            Query t_5045;
            t_5045 = t_9610.where(t_9611.getAccumulated()).orderBy(SrcTest.sid__505("name"), true).limit(10);
            Query q__1090 = t_5045;
            boolean t_9621 = q__1090.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__9604 = () -> "join with where/order/limit";
            test_68.assert_(t_9621, fn__9604);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__1681() {
        Test test_69 = new Test();
        try {
            SqlFragment c__1092 = SrcGlobal.col(SrcTest.sid__505("users"), SrcTest.sid__505("id"));
            boolean t_9602 = c__1092.toString().equals("users.id");
            Supplier<String> fn__9596 = () -> "col helper";
            test_69.assert_(t_9602, fn__9596);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__1682() {
        Test test_70 = new Test();
        try {
            SqlFragment onCond__1094 = SrcGlobal.col(SrcTest.sid__505("users"), SrcTest.sid__505("id"));
            SqlBuilder b__1095 = new SqlBuilder();
            b__1095.appendFragment(onCond__1094);
            b__1095.appendSafe(" = ");
            b__1095.appendFragment(SrcGlobal.col(SrcTest.sid__505("orders"), SrcTest.sid__505("user_id")));
            SafeIdentifier t_9587 = SrcTest.sid__505("users");
            SafeIdentifier t_9588 = SrcTest.sid__505("orders");
            SqlFragment t_9589 = b__1095.getAccumulated();
            Query q__1096 = SrcGlobal.from(t_9587).innerJoin(t_9588, t_9589);
            boolean t_9594 = q__1096.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__9576 = () -> "join with col";
            test_70.assert_(t_9594, fn__9576);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orWhereBasic__1683() {
        Test test_71 = new Test();
        try {
            SafeIdentifier t_9565 = SrcTest.sid__505("users");
            SqlBuilder t_9566 = new SqlBuilder();
            t_9566.appendSafe("status = ");
            t_9566.appendString("active");
            SqlFragment t_9569 = t_9566.getAccumulated();
            Query q__1098 = SrcGlobal.from(t_9565).orWhere(t_9569);
            boolean t_9574 = q__1098.toSql().toString().equals("SELECT * FROM users WHERE status = 'active'");
            Supplier<String> fn__9564 = () -> "orWhere basic";
            test_71.assert_(t_9574, fn__9564);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereThenOrWhere__1685() {
        Test test_72 = new Test();
        try {
            SafeIdentifier t_9548 = SrcTest.sid__505("users");
            SqlBuilder t_9549 = new SqlBuilder();
            t_9549.appendSafe("age > ");
            t_9549.appendInt32(18);
            SqlFragment t_9552 = t_9549.getAccumulated();
            Query t_9553 = SrcGlobal.from(t_9548).where(t_9552);
            SqlBuilder t_9554 = new SqlBuilder();
            t_9554.appendSafe("vip = ");
            t_9554.appendBoolean(true);
            Query q__1100 = t_9553.orWhere(t_9554.getAccumulated());
            boolean t_9562 = q__1100.toSql().toString().equals("SELECT * FROM users WHERE age > 18 OR vip = TRUE");
            Supplier<String> fn__9547 = () -> "where then orWhere";
            test_72.assert_(t_9562, fn__9547);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleOrWhere__1688() {
        Test test_73 = new Test();
        try {
            SafeIdentifier t_9526 = SrcTest.sid__505("users");
            SqlBuilder t_9527 = new SqlBuilder();
            t_9527.appendSafe("active = ");
            t_9527.appendBoolean(true);
            SqlFragment t_9530 = t_9527.getAccumulated();
            Query t_9531 = SrcGlobal.from(t_9526).where(t_9530);
            SqlBuilder t_9532 = new SqlBuilder();
            t_9532.appendSafe("role = ");
            t_9532.appendString("admin");
            Query t_9536 = t_9531.orWhere(t_9532.getAccumulated());
            SqlBuilder t_9537 = new SqlBuilder();
            t_9537.appendSafe("role = ");
            t_9537.appendString("moderator");
            Query q__1102 = t_9536.orWhere(t_9537.getAccumulated());
            boolean t_9545 = q__1102.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE OR role = 'admin' OR role = 'moderator'");
            Supplier<String> fn__9525 = () -> "multiple orWhere";
            test_73.assert_(t_9545, fn__9525);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedWhereAndOrWhere__1692() {
        Test test_74 = new Test();
        try {
            SafeIdentifier t_9504 = SrcTest.sid__505("users");
            SqlBuilder t_9505 = new SqlBuilder();
            t_9505.appendSafe("age > ");
            t_9505.appendInt32(18);
            SqlFragment t_9508 = t_9505.getAccumulated();
            Query t_9509 = SrcGlobal.from(t_9504).where(t_9508);
            SqlBuilder t_9510 = new SqlBuilder();
            t_9510.appendSafe("active = ");
            t_9510.appendBoolean(true);
            Query t_9514 = t_9509.where(t_9510.getAccumulated());
            SqlBuilder t_9515 = new SqlBuilder();
            t_9515.appendSafe("vip = ");
            t_9515.appendBoolean(true);
            Query q__1104 = t_9514.orWhere(t_9515.getAccumulated());
            boolean t_9523 = q__1104.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE OR vip = TRUE");
            Supplier<String> fn__9503 = () -> "mixed where and orWhere";
            test_74.assert_(t_9523, fn__9503);
        } finally {
            test_74.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNull__1696() {
        Test test_75 = new Test();
        try {
            SafeIdentifier t_9495 = SrcTest.sid__505("users");
            SafeIdentifier t_9496 = SrcTest.sid__505("deleted_at");
            Query q__1106 = SrcGlobal.from(t_9495).whereNull(t_9496);
            boolean t_9501 = q__1106.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL");
            Supplier<String> fn__9494 = () -> "whereNull";
            test_75.assert_(t_9501, fn__9494);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNull__1697() {
        Test test_76 = new Test();
        try {
            SafeIdentifier t_9486 = SrcTest.sid__505("users");
            SafeIdentifier t_9487 = SrcTest.sid__505("email");
            Query q__1108 = SrcGlobal.from(t_9486).whereNotNull(t_9487);
            boolean t_9492 = q__1108.toSql().toString().equals("SELECT * FROM users WHERE email IS NOT NULL");
            Supplier<String> fn__9485 = () -> "whereNotNull";
            test_76.assert_(t_9492, fn__9485);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNullChainedWithWhere__1698() {
        Test test_77 = new Test();
        try {
            SafeIdentifier t_9472 = SrcTest.sid__505("users");
            SqlBuilder t_9473 = new SqlBuilder();
            t_9473.appendSafe("active = ");
            t_9473.appendBoolean(true);
            SqlFragment t_9476 = t_9473.getAccumulated();
            Query q__1110 = SrcGlobal.from(t_9472).where(t_9476).whereNull(SrcTest.sid__505("deleted_at"));
            boolean t_9483 = q__1110.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND deleted_at IS NULL");
            Supplier<String> fn__9471 = () -> "whereNull chained";
            test_77.assert_(t_9483, fn__9471);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNullChainedWithOrWhere__1700() {
        Test test_78 = new Test();
        try {
            SafeIdentifier t_9458 = SrcTest.sid__505("users");
            SafeIdentifier t_9459 = SrcTest.sid__505("deleted_at");
            Query t_9460 = SrcGlobal.from(t_9458).whereNull(t_9459);
            SqlBuilder t_9461 = new SqlBuilder();
            t_9461.appendSafe("role = ");
            t_9461.appendString("admin");
            Query q__1112 = t_9460.orWhere(t_9461.getAccumulated());
            boolean t_9469 = q__1112.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL OR role = 'admin'");
            Supplier<String> fn__9457 = () -> "whereNotNull with orWhere";
            test_78.assert_(t_9469, fn__9457);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithIntValues__1702() {
        Test test_79 = new Test();
        try {
            SafeIdentifier t_9446 = SrcTest.sid__505("users");
            SafeIdentifier t_9447 = SrcTest.sid__505("id");
            SqlInt32 t_9448 = new SqlInt32(1);
            SqlInt32 t_9449 = new SqlInt32(2);
            SqlInt32 t_9450 = new SqlInt32(3);
            Query q__1114 = SrcGlobal.from(t_9446).whereIn(t_9447, List.of(t_9448, t_9449, t_9450));
            boolean t_9455 = q__1114.toSql().toString().equals("SELECT * FROM users WHERE id IN (1, 2, 3)");
            Supplier<String> fn__9445 = () -> "whereIn ints";
            test_79.assert_(t_9455, fn__9445);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithStringValuesEscaping__1703() {
        Test test_80 = new Test();
        try {
            SafeIdentifier t_9435 = SrcTest.sid__505("users");
            SafeIdentifier t_9436 = SrcTest.sid__505("name");
            SqlString t_9437 = new SqlString("Alice");
            SqlString t_9438 = new SqlString("Bob's");
            Query q__1116 = SrcGlobal.from(t_9435).whereIn(t_9436, List.of(t_9437, t_9438));
            boolean t_9443 = q__1116.toSql().toString().equals("SELECT * FROM users WHERE name IN ('Alice', 'Bob''s')");
            Supplier<String> fn__9434 = () -> "whereIn strings";
            test_80.assert_(t_9443, fn__9434);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithEmptyListProduces1_0__1704() {
        Test test_81 = new Test();
        try {
            SafeIdentifier t_9426 = SrcTest.sid__505("users");
            SafeIdentifier t_9427 = SrcTest.sid__505("id");
            Query q__1118 = SrcGlobal.from(t_9426).whereIn(t_9427, List.of());
            boolean t_9432 = q__1118.toSql().toString().equals("SELECT * FROM users WHERE 1 = 0");
            Supplier<String> fn__9425 = () -> "whereIn empty";
            test_81.assert_(t_9432, fn__9425);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInChained__1705() {
        Test test_82 = new Test();
        try {
            SafeIdentifier t_9410 = SrcTest.sid__505("users");
            SqlBuilder t_9411 = new SqlBuilder();
            t_9411.appendSafe("active = ");
            t_9411.appendBoolean(true);
            SqlFragment t_9414 = t_9411.getAccumulated();
            Query q__1120 = SrcGlobal.from(t_9410).where(t_9414).whereIn(SrcTest.sid__505("role"), List.of(new SqlString("admin"), new SqlString("user")));
            boolean t_9423 = q__1120.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND role IN ('admin', 'user')");
            Supplier<String> fn__9409 = () -> "whereIn chained";
            test_82.assert_(t_9423, fn__9409);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSingleElement__1707() {
        Test test_83 = new Test();
        try {
            SafeIdentifier t_9400 = SrcTest.sid__505("users");
            SafeIdentifier t_9401 = SrcTest.sid__505("id");
            SqlInt32 t_9402 = new SqlInt32(42);
            Query q__1122 = SrcGlobal.from(t_9400).whereIn(t_9401, List.of(t_9402));
            boolean t_9407 = q__1122.toSql().toString().equals("SELECT * FROM users WHERE id IN (42)");
            Supplier<String> fn__9399 = () -> "whereIn single";
            test_83.assert_(t_9407, fn__9399);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotBasic__1708() {
        Test test_84 = new Test();
        try {
            SafeIdentifier t_9388 = SrcTest.sid__505("users");
            SqlBuilder t_9389 = new SqlBuilder();
            t_9389.appendSafe("active = ");
            t_9389.appendBoolean(true);
            SqlFragment t_9392 = t_9389.getAccumulated();
            Query q__1124 = SrcGlobal.from(t_9388).whereNot(t_9392);
            boolean t_9397 = q__1124.toSql().toString().equals("SELECT * FROM users WHERE NOT (active = TRUE)");
            Supplier<String> fn__9387 = () -> "whereNot";
            test_84.assert_(t_9397, fn__9387);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotChained__1710() {
        Test test_85 = new Test();
        try {
            SafeIdentifier t_9371 = SrcTest.sid__505("users");
            SqlBuilder t_9372 = new SqlBuilder();
            t_9372.appendSafe("age > ");
            t_9372.appendInt32(18);
            SqlFragment t_9375 = t_9372.getAccumulated();
            Query t_9376 = SrcGlobal.from(t_9371).where(t_9375);
            SqlBuilder t_9377 = new SqlBuilder();
            t_9377.appendSafe("banned = ");
            t_9377.appendBoolean(true);
            Query q__1126 = t_9376.whereNot(t_9377.getAccumulated());
            boolean t_9385 = q__1126.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND NOT (banned = TRUE)");
            Supplier<String> fn__9370 = () -> "whereNot chained";
            test_85.assert_(t_9385, fn__9370);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenIntegers__1713() {
        Test test_86 = new Test();
        try {
            SafeIdentifier t_9360 = SrcTest.sid__505("users");
            SafeIdentifier t_9361 = SrcTest.sid__505("age");
            SqlInt32 t_9362 = new SqlInt32(18);
            SqlInt32 t_9363 = new SqlInt32(65);
            Query q__1128 = SrcGlobal.from(t_9360).whereBetween(t_9361, t_9362, t_9363);
            boolean t_9368 = q__1128.toSql().toString().equals("SELECT * FROM users WHERE age BETWEEN 18 AND 65");
            Supplier<String> fn__9359 = () -> "whereBetween ints";
            test_86.assert_(t_9368, fn__9359);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenChained__1714() {
        Test test_87 = new Test();
        try {
            SafeIdentifier t_9344 = SrcTest.sid__505("users");
            SqlBuilder t_9345 = new SqlBuilder();
            t_9345.appendSafe("active = ");
            t_9345.appendBoolean(true);
            SqlFragment t_9348 = t_9345.getAccumulated();
            Query q__1130 = SrcGlobal.from(t_9344).where(t_9348).whereBetween(SrcTest.sid__505("age"), new SqlInt32(21), new SqlInt32(30));
            boolean t_9357 = q__1130.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND age BETWEEN 21 AND 30");
            Supplier<String> fn__9343 = () -> "whereBetween chained";
            test_87.assert_(t_9357, fn__9343);
        } finally {
            test_87.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeBasic__1716() {
        Test test_88 = new Test();
        try {
            SafeIdentifier t_9335 = SrcTest.sid__505("users");
            SafeIdentifier t_9336 = SrcTest.sid__505("name");
            Query q__1132 = SrcGlobal.from(t_9335).whereLike(t_9336, "John%");
            boolean t_9341 = q__1132.toSql().toString().equals("SELECT * FROM users WHERE name LIKE 'John%'");
            Supplier<String> fn__9334 = () -> "whereLike";
            test_88.assert_(t_9341, fn__9334);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereIlikeBasic__1717() {
        Test test_89 = new Test();
        try {
            SafeIdentifier t_9326 = SrcTest.sid__505("users");
            SafeIdentifier t_9327 = SrcTest.sid__505("email");
            Query q__1134 = SrcGlobal.from(t_9326).whereILike(t_9327, "%@gmail.com");
            boolean t_9332 = q__1134.toSql().toString().equals("SELECT * FROM users WHERE email ILIKE '%@gmail.com'");
            Supplier<String> fn__9325 = () -> "whereILike";
            test_89.assert_(t_9332, fn__9325);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWithInjectionAttempt__1718() {
        Test test_90 = new Test();
        try {
            SafeIdentifier t_9312 = SrcTest.sid__505("users");
            SafeIdentifier t_9313 = SrcTest.sid__505("name");
            Query q__1136 = SrcGlobal.from(t_9312).whereLike(t_9313, "'; DROP TABLE users; --");
            String s__1137 = q__1136.toSql().toString();
            boolean t_9318 = s__1137.indexOf("''") >= 0;
            Supplier<String> fn__9311 = () -> "like injection escaped: " + s__1137;
            test_90.assert_(t_9318, fn__9311);
            boolean t_9322 = s__1137.indexOf("LIKE") >= 0;
            Supplier<String> fn__9310 = () -> "like structure intact: " + s__1137;
            test_90.assert_(t_9322, fn__9310);
        } finally {
            test_90.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWildcardPatterns__1719() {
        Test test_91 = new Test();
        try {
            SafeIdentifier t_9302 = SrcTest.sid__505("users");
            SafeIdentifier t_9303 = SrcTest.sid__505("name");
            Query q__1139 = SrcGlobal.from(t_9302).whereLike(t_9303, "%son%");
            boolean t_9308 = q__1139.toSql().toString().equals("SELECT * FROM users WHERE name LIKE '%son%'");
            Supplier<String> fn__9301 = () -> "whereLike wildcard";
            test_91.assert_(t_9308, fn__9301);
        } finally {
            test_91.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countAllProducesCount__1720() {
        Test test_92 = new Test();
        try {
            SqlFragment f__1141 = SrcGlobal.countAll();
            boolean t_9299 = f__1141.toString().equals("COUNT(*)");
            Supplier<String> fn__9295 = () -> "countAll";
            test_92.assert_(t_9299, fn__9295);
        } finally {
            test_92.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countColProducesCountField__1721() {
        Test test_93 = new Test();
        try {
            SqlFragment f__1143 = SrcGlobal.countCol(SrcTest.sid__505("id"));
            boolean t_9293 = f__1143.toString().equals("COUNT(id)");
            Supplier<String> fn__9288 = () -> "countCol";
            test_93.assert_(t_9293, fn__9288);
        } finally {
            test_93.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sumColProducesSumField__1722() {
        Test test_94 = new Test();
        try {
            SqlFragment f__1145 = SrcGlobal.sumCol(SrcTest.sid__505("amount"));
            boolean t_9286 = f__1145.toString().equals("SUM(amount)");
            Supplier<String> fn__9281 = () -> "sumCol";
            test_94.assert_(t_9286, fn__9281);
        } finally {
            test_94.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void avgColProducesAvgField__1723() {
        Test test_95 = new Test();
        try {
            SqlFragment f__1147 = SrcGlobal.avgCol(SrcTest.sid__505("price"));
            boolean t_9279 = f__1147.toString().equals("AVG(price)");
            Supplier<String> fn__9274 = () -> "avgCol";
            test_95.assert_(t_9279, fn__9274);
        } finally {
            test_95.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void minColProducesMinField__1724() {
        Test test_96 = new Test();
        try {
            SqlFragment f__1149 = SrcGlobal.minCol(SrcTest.sid__505("created_at"));
            boolean t_9272 = f__1149.toString().equals("MIN(created_at)");
            Supplier<String> fn__9267 = () -> "minCol";
            test_96.assert_(t_9272, fn__9267);
        } finally {
            test_96.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void maxColProducesMaxField__1725() {
        Test test_97 = new Test();
        try {
            SqlFragment f__1151 = SrcGlobal.maxCol(SrcTest.sid__505("score"));
            boolean t_9265 = f__1151.toString().equals("MAX(score)");
            Supplier<String> fn__9260 = () -> "maxCol";
            test_97.assert_(t_9265, fn__9260);
        } finally {
            test_97.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithAggregate__1726() {
        Test test_98 = new Test();
        try {
            SafeIdentifier t_9252 = SrcTest.sid__505("orders");
            SqlFragment t_9253 = SrcGlobal.countAll();
            Query q__1153 = SrcGlobal.from(t_9252).selectExpr(List.of(t_9253));
            boolean t_9258 = q__1153.toSql().toString().equals("SELECT COUNT(*) FROM orders");
            Supplier<String> fn__9251 = () -> "selectExpr count";
            test_98.assert_(t_9258, fn__9251);
        } finally {
            test_98.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithMultipleExpressions__1727() {
        Test test_99 = new Test();
        try {
            SqlFragment nameFrag__1155 = SrcGlobal.col(SrcTest.sid__505("users"), SrcTest.sid__505("name"));
            SafeIdentifier t_9243 = SrcTest.sid__505("users");
            SqlFragment t_9244 = SrcGlobal.countAll();
            Query q__1156 = SrcGlobal.from(t_9243).selectExpr(List.of(nameFrag__1155, t_9244));
            boolean t_9249 = q__1156.toSql().toString().equals("SELECT users.name, COUNT(*) FROM users");
            Supplier<String> fn__9239 = () -> "selectExpr multi";
            test_99.assert_(t_9249, fn__9239);
        } finally {
            test_99.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprOverridesSelectedFields__1728() {
        Test test_100 = new Test();
        try {
            SafeIdentifier t_9228 = SrcTest.sid__505("users");
            SafeIdentifier t_9229 = SrcTest.sid__505("id");
            SafeIdentifier t_9230 = SrcTest.sid__505("name");
            Query q__1158 = SrcGlobal.from(t_9228).select(List.of(t_9229, t_9230)).selectExpr(List.of(SrcGlobal.countAll()));
            boolean t_9237 = q__1158.toSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__9227 = () -> "selectExpr overrides select";
            test_100.assert_(t_9237, fn__9227);
        } finally {
            test_100.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupBySingleField__1729() {
        Test test_101 = new Test();
        try {
            SafeIdentifier t_9214 = SrcTest.sid__505("orders");
            SqlFragment t_9217 = SrcGlobal.col(SrcTest.sid__505("orders"), SrcTest.sid__505("status"));
            SqlFragment t_9218 = SrcGlobal.countAll();
            Query q__1160 = SrcGlobal.from(t_9214).selectExpr(List.of(t_9217, t_9218)).groupBy(SrcTest.sid__505("status"));
            boolean t_9225 = q__1160.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status");
            Supplier<String> fn__9213 = () -> "groupBy single";
            test_101.assert_(t_9225, fn__9213);
        } finally {
            test_101.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupByMultipleFields__1730() {
        Test test_102 = new Test();
        try {
            SafeIdentifier t_9203 = SrcTest.sid__505("orders");
            SafeIdentifier t_9204 = SrcTest.sid__505("status");
            Query q__1162 = SrcGlobal.from(t_9203).groupBy(t_9204).groupBy(SrcTest.sid__505("category"));
            boolean t_9211 = q__1162.toSql().toString().equals("SELECT * FROM orders GROUP BY status, category");
            Supplier<String> fn__9202 = () -> "groupBy multiple";
            test_102.assert_(t_9211, fn__9202);
        } finally {
            test_102.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void havingBasic__1731() {
        Test test_103 = new Test();
        try {
            SafeIdentifier t_9184 = SrcTest.sid__505("orders");
            SqlFragment t_9187 = SrcGlobal.col(SrcTest.sid__505("orders"), SrcTest.sid__505("status"));
            SqlFragment t_9188 = SrcGlobal.countAll();
            Query t_9191 = SrcGlobal.from(t_9184).selectExpr(List.of(t_9187, t_9188)).groupBy(SrcTest.sid__505("status"));
            SqlBuilder t_9192 = new SqlBuilder();
            t_9192.appendSafe("COUNT(*) > ");
            t_9192.appendInt32(5);
            Query q__1164 = t_9191.having(t_9192.getAccumulated());
            boolean t_9200 = q__1164.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status HAVING COUNT(*) > 5");
            Supplier<String> fn__9183 = () -> "having basic";
            test_103.assert_(t_9200, fn__9183);
        } finally {
            test_103.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orHaving__1733() {
        Test test_104 = new Test();
        try {
            SafeIdentifier t_9165 = SrcTest.sid__505("orders");
            SafeIdentifier t_9166 = SrcTest.sid__505("status");
            Query t_9167 = SrcGlobal.from(t_9165).groupBy(t_9166);
            SqlBuilder t_9168 = new SqlBuilder();
            t_9168.appendSafe("COUNT(*) > ");
            t_9168.appendInt32(5);
            Query t_9172 = t_9167.having(t_9168.getAccumulated());
            SqlBuilder t_9173 = new SqlBuilder();
            t_9173.appendSafe("SUM(total) > ");
            t_9173.appendInt32(1000);
            Query q__1166 = t_9172.orHaving(t_9173.getAccumulated());
            boolean t_9181 = q__1166.toSql().toString().equals("SELECT * FROM orders GROUP BY status HAVING COUNT(*) > 5 OR SUM(total) > 1000");
            Supplier<String> fn__9164 = () -> "orHaving";
            test_104.assert_(t_9181, fn__9164);
        } finally {
            test_104.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctBasic__1736() {
        Test test_105 = new Test();
        try {
            SafeIdentifier t_9155 = SrcTest.sid__505("users");
            SafeIdentifier t_9156 = SrcTest.sid__505("name");
            Query q__1168 = SrcGlobal.from(t_9155).select(List.of(t_9156)).distinct();
            boolean t_9162 = q__1168.toSql().toString().equals("SELECT DISTINCT name FROM users");
            Supplier<String> fn__9154 = () -> "distinct";
            test_105.assert_(t_9162, fn__9154);
        } finally {
            test_105.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctWithWhere__1737() {
        Test test_106 = new Test();
        try {
            SafeIdentifier t_9140 = SrcTest.sid__505("users");
            SafeIdentifier t_9141 = SrcTest.sid__505("email");
            Query t_9142 = SrcGlobal.from(t_9140).select(List.of(t_9141));
            SqlBuilder t_9143 = new SqlBuilder();
            t_9143.appendSafe("active = ");
            t_9143.appendBoolean(true);
            Query q__1170 = t_9142.where(t_9143.getAccumulated()).distinct();
            boolean t_9152 = q__1170.toSql().toString().equals("SELECT DISTINCT email FROM users WHERE active = TRUE");
            Supplier<String> fn__9139 = () -> "distinct with where";
            test_106.assert_(t_9152, fn__9139);
        } finally {
            test_106.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlBare__1739() {
        Test test_107 = new Test();
        try {
            Query q__1172 = SrcGlobal.from(SrcTest.sid__505("users"));
            boolean t_9137 = q__1172.countSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__9132 = () -> "countSql bare";
            test_107.assert_(t_9137, fn__9132);
        } finally {
            test_107.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithWhere__1740() {
        Test test_108 = new Test();
        try {
            SafeIdentifier t_9121 = SrcTest.sid__505("users");
            SqlBuilder t_9122 = new SqlBuilder();
            t_9122.appendSafe("active = ");
            t_9122.appendBoolean(true);
            SqlFragment t_9125 = t_9122.getAccumulated();
            Query q__1174 = SrcGlobal.from(t_9121).where(t_9125);
            boolean t_9130 = q__1174.countSql().toString().equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__9120 = () -> "countSql with where";
            test_108.assert_(t_9130, fn__9120);
        } finally {
            test_108.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithJoin__1742() {
        Test test_109 = new Test();
        try {
            SafeIdentifier t_9104 = SrcTest.sid__505("users");
            SafeIdentifier t_9105 = SrcTest.sid__505("orders");
            SqlBuilder t_9106 = new SqlBuilder();
            t_9106.appendSafe("users.id = orders.user_id");
            SqlFragment t_9108 = t_9106.getAccumulated();
            Query t_9109 = SrcGlobal.from(t_9104).innerJoin(t_9105, t_9108);
            SqlBuilder t_9110 = new SqlBuilder();
            t_9110.appendSafe("orders.total > ");
            t_9110.appendInt32(100);
            Query q__1176 = t_9109.where(t_9110.getAccumulated());
            boolean t_9118 = q__1176.countSql().toString().equals("SELECT COUNT(*) FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100");
            Supplier<String> fn__9103 = () -> "countSql with join";
            test_109.assert_(t_9118, fn__9103);
        } finally {
            test_109.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlDropsOrderByLimitOffset__1745() {
        Test test_110 = new Test();
        try {
            SafeIdentifier t_9090 = SrcTest.sid__505("users");
            SqlBuilder t_9091 = new SqlBuilder();
            t_9091.appendSafe("active = ");
            t_9091.appendBoolean(true);
            SqlFragment t_9094 = t_9091.getAccumulated();
            Query t_4621;
            t_4621 = SrcGlobal.from(t_9090).where(t_9094).orderBy(SrcTest.sid__505("name"), true).limit(10);
            Query t_4622;
            t_4622 = t_4621.offset(20);
            Query q__1178 = t_4622;
            String s__1179 = q__1178.countSql().toString();
            boolean t_9101 = s__1179.equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__9089 = () -> "countSql drops extras: " + s__1179;
            test_110.assert_(t_9101, fn__9089);
        } finally {
            test_110.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullAggregationQuery__1747() {
        Test test_111 = new Test();
        try {
            SafeIdentifier t_9057 = SrcTest.sid__505("orders");
            SqlFragment t_9060 = SrcGlobal.col(SrcTest.sid__505("orders"), SrcTest.sid__505("status"));
            SqlFragment t_9061 = SrcGlobal.countAll();
            SqlFragment t_9063 = SrcGlobal.sumCol(SrcTest.sid__505("total"));
            Query t_9064 = SrcGlobal.from(t_9057).selectExpr(List.of(t_9060, t_9061, t_9063));
            SafeIdentifier t_9065 = SrcTest.sid__505("users");
            SqlBuilder t_9066 = new SqlBuilder();
            t_9066.appendSafe("orders.user_id = users.id");
            Query t_9069 = t_9064.innerJoin(t_9065, t_9066.getAccumulated());
            SqlBuilder t_9070 = new SqlBuilder();
            t_9070.appendSafe("users.active = ");
            t_9070.appendBoolean(true);
            Query t_9076 = t_9069.where(t_9070.getAccumulated()).groupBy(SrcTest.sid__505("status"));
            SqlBuilder t_9077 = new SqlBuilder();
            t_9077.appendSafe("COUNT(*) > ");
            t_9077.appendInt32(3);
            Query q__1181 = t_9076.having(t_9077.getAccumulated()).orderBy(SrcTest.sid__505("status"), true);
            String expected__1182 = "SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC";
            boolean t_9087 = q__1181.toSql().toString().equals("SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC");
            Supplier<String> fn__9056 = () -> "full aggregation";
            test_111.assert_(t_9087, fn__9056);
        } finally {
            test_111.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionSql__1751() {
        Test test_112 = new Test();
        try {
            SafeIdentifier t_9039 = SrcTest.sid__505("users");
            SqlBuilder t_9040 = new SqlBuilder();
            t_9040.appendSafe("role = ");
            t_9040.appendString("admin");
            SqlFragment t_9043 = t_9040.getAccumulated();
            Query a__1184 = SrcGlobal.from(t_9039).where(t_9043);
            SafeIdentifier t_9045 = SrcTest.sid__505("users");
            SqlBuilder t_9046 = new SqlBuilder();
            t_9046.appendSafe("role = ");
            t_9046.appendString("moderator");
            SqlFragment t_9049 = t_9046.getAccumulated();
            Query b__1185 = SrcGlobal.from(t_9045).where(t_9049);
            String s__1186 = SrcGlobal.unionSql(a__1184, b__1185).toString();
            boolean t_9054 = s__1186.equals("(SELECT * FROM users WHERE role = 'admin') UNION (SELECT * FROM users WHERE role = 'moderator')");
            Supplier<String> fn__9038 = () -> "unionSql: " + s__1186;
            test_112.assert_(t_9054, fn__9038);
        } finally {
            test_112.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionAllSql__1754() {
        Test test_113 = new Test();
        try {
            SafeIdentifier t_9027 = SrcTest.sid__505("users");
            SafeIdentifier t_9028 = SrcTest.sid__505("name");
            Query a__1188 = SrcGlobal.from(t_9027).select(List.of(t_9028));
            SafeIdentifier t_9030 = SrcTest.sid__505("contacts");
            SafeIdentifier t_9031 = SrcTest.sid__505("name");
            Query b__1189 = SrcGlobal.from(t_9030).select(List.of(t_9031));
            String s__1190 = SrcGlobal.unionAllSql(a__1188, b__1189).toString();
            boolean t_9036 = s__1190.equals("(SELECT name FROM users) UNION ALL (SELECT name FROM contacts)");
            Supplier<String> fn__9026 = () -> "unionAllSql: " + s__1190;
            test_113.assert_(t_9036, fn__9026);
        } finally {
            test_113.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void intersectSql__1755() {
        Test test_114 = new Test();
        try {
            SafeIdentifier t_9015 = SrcTest.sid__505("users");
            SafeIdentifier t_9016 = SrcTest.sid__505("email");
            Query a__1192 = SrcGlobal.from(t_9015).select(List.of(t_9016));
            SafeIdentifier t_9018 = SrcTest.sid__505("subscribers");
            SafeIdentifier t_9019 = SrcTest.sid__505("email");
            Query b__1193 = SrcGlobal.from(t_9018).select(List.of(t_9019));
            String s__1194 = SrcGlobal.intersectSql(a__1192, b__1193).toString();
            boolean t_9024 = s__1194.equals("(SELECT email FROM users) INTERSECT (SELECT email FROM subscribers)");
            Supplier<String> fn__9014 = () -> "intersectSql: " + s__1194;
            test_114.assert_(t_9024, fn__9014);
        } finally {
            test_114.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void exceptSql__1756() {
        Test test_115 = new Test();
        try {
            SafeIdentifier t_9003 = SrcTest.sid__505("users");
            SafeIdentifier t_9004 = SrcTest.sid__505("id");
            Query a__1196 = SrcGlobal.from(t_9003).select(List.of(t_9004));
            SafeIdentifier t_9006 = SrcTest.sid__505("banned");
            SafeIdentifier t_9007 = SrcTest.sid__505("id");
            Query b__1197 = SrcGlobal.from(t_9006).select(List.of(t_9007));
            String s__1198 = SrcGlobal.exceptSql(a__1196, b__1197).toString();
            boolean t_9012 = s__1198.equals("(SELECT id FROM users) EXCEPT (SELECT id FROM banned)");
            Supplier<String> fn__9002 = () -> "exceptSql: " + s__1198;
            test_115.assert_(t_9012, fn__9002);
        } finally {
            test_115.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void subqueryWithAlias__1757() {
        Test test_116 = new Test();
        try {
            SafeIdentifier t_8988 = SrcTest.sid__505("orders");
            SafeIdentifier t_8989 = SrcTest.sid__505("user_id");
            Query t_8990 = SrcGlobal.from(t_8988).select(List.of(t_8989));
            SqlBuilder t_8991 = new SqlBuilder();
            t_8991.appendSafe("total > ");
            t_8991.appendInt32(100);
            Query inner__1200 = t_8990.where(t_8991.getAccumulated());
            String s__1201 = SrcGlobal.subquery(inner__1200, SrcTest.sid__505("big_orders")).toString();
            boolean t_9000 = s__1201.equals("(SELECT user_id FROM orders WHERE total > 100) AS big_orders");
            Supplier<String> fn__8987 = () -> "subquery: " + s__1201;
            test_116.assert_(t_9000, fn__8987);
        } finally {
            test_116.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSql__1759() {
        Test test_117 = new Test();
        try {
            SafeIdentifier t_8977 = SrcTest.sid__505("orders");
            SqlBuilder t_8978 = new SqlBuilder();
            t_8978.appendSafe("orders.user_id = users.id");
            SqlFragment t_8980 = t_8978.getAccumulated();
            Query inner__1203 = SrcGlobal.from(t_8977).where(t_8980);
            String s__1204 = SrcGlobal.existsSql(inner__1203).toString();
            boolean t_8985 = s__1204.equals("EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__8976 = () -> "existsSql: " + s__1204;
            test_117.assert_(t_8985, fn__8976);
        } finally {
            test_117.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubquery__1761() {
        Test test_118 = new Test();
        try {
            SafeIdentifier t_8960 = SrcTest.sid__505("orders");
            SafeIdentifier t_8961 = SrcTest.sid__505("user_id");
            Query t_8962 = SrcGlobal.from(t_8960).select(List.of(t_8961));
            SqlBuilder t_8963 = new SqlBuilder();
            t_8963.appendSafe("total > ");
            t_8963.appendInt32(1000);
            Query sub__1206 = t_8962.where(t_8963.getAccumulated());
            SafeIdentifier t_8968 = SrcTest.sid__505("users");
            SafeIdentifier t_8969 = SrcTest.sid__505("id");
            Query q__1207 = SrcGlobal.from(t_8968).whereInSubquery(t_8969, sub__1206);
            String s__1208 = q__1207.toSql().toString();
            boolean t_8974 = s__1208.equals("SELECT * FROM users WHERE id IN (SELECT user_id FROM orders WHERE total > 1000)");
            Supplier<String> fn__8959 = () -> "whereInSubquery: " + s__1208;
            test_118.assert_(t_8974, fn__8959);
        } finally {
            test_118.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void setOperationWithWhereOnEachSide__1763() {
        Test test_119 = new Test();
        try {
            SafeIdentifier t_8937 = SrcTest.sid__505("users");
            SqlBuilder t_8938 = new SqlBuilder();
            t_8938.appendSafe("age > ");
            t_8938.appendInt32(18);
            SqlFragment t_8941 = t_8938.getAccumulated();
            Query t_8942 = SrcGlobal.from(t_8937).where(t_8941);
            SqlBuilder t_8943 = new SqlBuilder();
            t_8943.appendSafe("active = ");
            t_8943.appendBoolean(true);
            Query a__1210 = t_8942.where(t_8943.getAccumulated());
            SafeIdentifier t_8948 = SrcTest.sid__505("users");
            SqlBuilder t_8949 = new SqlBuilder();
            t_8949.appendSafe("role = ");
            t_8949.appendString("vip");
            SqlFragment t_8952 = t_8949.getAccumulated();
            Query b__1211 = SrcGlobal.from(t_8948).where(t_8952);
            String s__1212 = SrcGlobal.unionSql(a__1210, b__1211).toString();
            boolean t_8957 = s__1212.equals("(SELECT * FROM users WHERE age > 18 AND active = TRUE) UNION (SELECT * FROM users WHERE role = 'vip')");
            Supplier<String> fn__8936 = () -> "union with where: " + s__1212;
            test_119.assert_(t_8957, fn__8936);
        } finally {
            test_119.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubqueryChainedWithWhere__1767() {
        Test test_120 = new Test();
        try {
            SafeIdentifier t_8920 = SrcTest.sid__505("orders");
            SafeIdentifier t_8921 = SrcTest.sid__505("user_id");
            Query sub__1214 = SrcGlobal.from(t_8920).select(List.of(t_8921));
            SafeIdentifier t_8923 = SrcTest.sid__505("users");
            SqlBuilder t_8924 = new SqlBuilder();
            t_8924.appendSafe("active = ");
            t_8924.appendBoolean(true);
            SqlFragment t_8927 = t_8924.getAccumulated();
            Query q__1215 = SrcGlobal.from(t_8923).where(t_8927).whereInSubquery(SrcTest.sid__505("id"), sub__1214);
            String s__1216 = q__1215.toSql().toString();
            boolean t_8934 = s__1216.equals("SELECT * FROM users WHERE active = TRUE AND id IN (SELECT user_id FROM orders)");
            Supplier<String> fn__8919 = () -> "whereInSubquery chained: " + s__1216;
            test_120.assert_(t_8934, fn__8919);
        } finally {
            test_120.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSqlUsedInWhere__1769() {
        Test test_121 = new Test();
        try {
            SafeIdentifier t_8906 = SrcTest.sid__505("orders");
            SqlBuilder t_8907 = new SqlBuilder();
            t_8907.appendSafe("orders.user_id = users.id");
            SqlFragment t_8909 = t_8907.getAccumulated();
            Query sub__1218 = SrcGlobal.from(t_8906).where(t_8909);
            SafeIdentifier t_8911 = SrcTest.sid__505("users");
            SqlFragment t_8912 = SrcGlobal.existsSql(sub__1218);
            Query q__1219 = SrcGlobal.from(t_8911).where(t_8912);
            String s__1220 = q__1219.toSql().toString();
            boolean t_8917 = s__1220.equals("SELECT * FROM users WHERE EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__8905 = () -> "exists in where: " + s__1220;
            test_121.assert_(t_8917, fn__8905);
        } finally {
            test_121.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBasic__1771() {
        Test test_122 = new Test();
        try {
            SafeIdentifier t_8892 = SrcTest.sid__505("users");
            SafeIdentifier t_8893 = SrcTest.sid__505("name");
            SqlString t_8894 = new SqlString("Alice");
            UpdateQuery t_8895 = SrcGlobal.update(t_8892).set(t_8893, t_8894);
            SqlBuilder t_8896 = new SqlBuilder();
            t_8896.appendSafe("id = ");
            t_8896.appendInt32(1);
            SqlFragment t_4443;
            t_4443 = t_8895.where(t_8896.getAccumulated()).toSql();
            SqlFragment q__1222 = t_4443;
            boolean t_8903 = q__1222.toString().equals("UPDATE users SET name = 'Alice' WHERE id = 1");
            Supplier<String> fn__8891 = () -> "update basic";
            test_122.assert_(t_8903, fn__8891);
        } finally {
            test_122.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleSet__1773() {
        Test test_123 = new Test();
        try {
            SafeIdentifier t_8875 = SrcTest.sid__505("users");
            SafeIdentifier t_8876 = SrcTest.sid__505("name");
            SqlString t_8877 = new SqlString("Bob");
            UpdateQuery t_8881 = SrcGlobal.update(t_8875).set(t_8876, t_8877).set(SrcTest.sid__505("age"), new SqlInt32(30));
            SqlBuilder t_8882 = new SqlBuilder();
            t_8882.appendSafe("id = ");
            t_8882.appendInt32(2);
            SqlFragment t_4428;
            t_4428 = t_8881.where(t_8882.getAccumulated()).toSql();
            SqlFragment q__1224 = t_4428;
            boolean t_8889 = q__1224.toString().equals("UPDATE users SET name = 'Bob', age = 30 WHERE id = 2");
            Supplier<String> fn__8874 = () -> "update multi set";
            test_123.assert_(t_8889, fn__8874);
        } finally {
            test_123.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleWhere__1775() {
        Test test_124 = new Test();
        try {
            SafeIdentifier t_8856 = SrcTest.sid__505("users");
            SafeIdentifier t_8857 = SrcTest.sid__505("active");
            SqlBoolean t_8858 = new SqlBoolean(false);
            UpdateQuery t_8859 = SrcGlobal.update(t_8856).set(t_8857, t_8858);
            SqlBuilder t_8860 = new SqlBuilder();
            t_8860.appendSafe("age < ");
            t_8860.appendInt32(18);
            UpdateQuery t_8864 = t_8859.where(t_8860.getAccumulated());
            SqlBuilder t_8865 = new SqlBuilder();
            t_8865.appendSafe("role = ");
            t_8865.appendString("guest");
            SqlFragment t_4410;
            t_4410 = t_8864.where(t_8865.getAccumulated()).toSql();
            SqlFragment q__1226 = t_4410;
            boolean t_8872 = q__1226.toString().equals("UPDATE users SET active = FALSE WHERE age < 18 AND role = 'guest'");
            Supplier<String> fn__8855 = () -> "update multi where";
            test_124.assert_(t_8872, fn__8855);
        } finally {
            test_124.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryOrWhere__1778() {
        Test test_125 = new Test();
        try {
            SafeIdentifier t_8837 = SrcTest.sid__505("users");
            SafeIdentifier t_8838 = SrcTest.sid__505("status");
            SqlString t_8839 = new SqlString("banned");
            UpdateQuery t_8840 = SrcGlobal.update(t_8837).set(t_8838, t_8839);
            SqlBuilder t_8841 = new SqlBuilder();
            t_8841.appendSafe("spam_count > ");
            t_8841.appendInt32(10);
            UpdateQuery t_8845 = t_8840.where(t_8841.getAccumulated());
            SqlBuilder t_8846 = new SqlBuilder();
            t_8846.appendSafe("reported = ");
            t_8846.appendBoolean(true);
            SqlFragment t_4389;
            t_4389 = t_8845.orWhere(t_8846.getAccumulated()).toSql();
            SqlFragment q__1228 = t_4389;
            boolean t_8853 = q__1228.toString().equals("UPDATE users SET status = 'banned' WHERE spam_count > 10 OR reported = TRUE");
            Supplier<String> fn__8836 = () -> "update orWhere";
            test_125.assert_(t_8853, fn__8836);
        } finally {
            test_125.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutWhere__1781() {
        Test test_126 = new Test();
        try {
            SafeIdentifier t_8830;
            SafeIdentifier t_8831;
            SqlInt32 t_8832;
            boolean didBubble__1230;
            boolean didBubble_10976;
            try {
                t_8830 = SrcTest.sid__505("users");
                t_8831 = SrcTest.sid__505("x");
                t_8832 = new SqlInt32(1);
                SrcGlobal.update(t_8830).set(t_8831, t_8832).toSql();
                didBubble_10976 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_10976 = true;
            }
            didBubble__1230 = didBubble_10976;
            Supplier<String> fn__8829 = () -> "update without WHERE should bubble";
            test_126.assert_(didBubble__1230, fn__8829);
        } finally {
            test_126.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutSet__1782() {
        Test test_127 = new Test();
        try {
            SafeIdentifier t_8821;
            SqlBuilder t_8822;
            SqlFragment t_8825;
            boolean didBubble__1232;
            boolean didBubble_10977;
            try {
                t_8821 = SrcTest.sid__505("users");
                t_8822 = new SqlBuilder();
                t_8822.appendSafe("id = ");
                t_8822.appendInt32(1);
                t_8825 = t_8822.getAccumulated();
                SrcGlobal.update(t_8821).where(t_8825).toSql();
                didBubble_10977 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_10977 = true;
            }
            didBubble__1232 = didBubble_10977;
            Supplier<String> fn__8820 = () -> "update without SET should bubble";
            test_127.assert_(didBubble__1232, fn__8820);
        } finally {
            test_127.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryWithLimit__1784() {
        Test test_128 = new Test();
        try {
            SafeIdentifier t_8807 = SrcTest.sid__505("users");
            SafeIdentifier t_8808 = SrcTest.sid__505("active");
            SqlBoolean t_8809 = new SqlBoolean(false);
            UpdateQuery t_8810 = SrcGlobal.update(t_8807).set(t_8808, t_8809);
            SqlBuilder t_8811 = new SqlBuilder();
            t_8811.appendSafe("last_login < ");
            t_8811.appendString("2024-01-01");
            UpdateQuery t_4352;
            t_4352 = t_8810.where(t_8811.getAccumulated()).limit(100);
            SqlFragment t_4353;
            t_4353 = t_4352.toSql();
            SqlFragment q__1234 = t_4353;
            boolean t_8818 = q__1234.toString().equals("UPDATE users SET active = FALSE WHERE last_login < '2024-01-01' LIMIT 100");
            Supplier<String> fn__8806 = () -> "update limit";
            test_128.assert_(t_8818, fn__8806);
        } finally {
            test_128.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryEscaping__1786() {
        Test test_129 = new Test();
        try {
            SafeIdentifier t_8793 = SrcTest.sid__505("users");
            SafeIdentifier t_8794 = SrcTest.sid__505("bio");
            SqlString t_8795 = new SqlString("It's a test");
            UpdateQuery t_8796 = SrcGlobal.update(t_8793).set(t_8794, t_8795);
            SqlBuilder t_8797 = new SqlBuilder();
            t_8797.appendSafe("id = ");
            t_8797.appendInt32(1);
            SqlFragment t_4337;
            t_4337 = t_8796.where(t_8797.getAccumulated()).toSql();
            SqlFragment q__1236 = t_4337;
            boolean t_8804 = q__1236.toString().equals("UPDATE users SET bio = 'It''s a test' WHERE id = 1");
            Supplier<String> fn__8792 = () -> "update escaping";
            test_129.assert_(t_8804, fn__8792);
        } finally {
            test_129.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBasic__1788() {
        Test test_130 = new Test();
        try {
            SafeIdentifier t_8782 = SrcTest.sid__505("users");
            SqlBuilder t_8783 = new SqlBuilder();
            t_8783.appendSafe("id = ");
            t_8783.appendInt32(1);
            SqlFragment t_8786 = t_8783.getAccumulated();
            SqlFragment t_4322;
            t_4322 = SrcGlobal.deleteFrom(t_8782).where(t_8786).toSql();
            SqlFragment q__1238 = t_4322;
            boolean t_8790 = q__1238.toString().equals("DELETE FROM users WHERE id = 1");
            Supplier<String> fn__8781 = () -> "delete basic";
            test_130.assert_(t_8790, fn__8781);
        } finally {
            test_130.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryMultipleWhere__1790() {
        Test test_131 = new Test();
        try {
            SafeIdentifier t_8766 = SrcTest.sid__505("logs");
            SqlBuilder t_8767 = new SqlBuilder();
            t_8767.appendSafe("created_at < ");
            t_8767.appendString("2024-01-01");
            SqlFragment t_8770 = t_8767.getAccumulated();
            DeleteQuery t_8771 = SrcGlobal.deleteFrom(t_8766).where(t_8770);
            SqlBuilder t_8772 = new SqlBuilder();
            t_8772.appendSafe("level = ");
            t_8772.appendString("debug");
            SqlFragment t_4310;
            t_4310 = t_8771.where(t_8772.getAccumulated()).toSql();
            SqlFragment q__1240 = t_4310;
            boolean t_8779 = q__1240.toString().equals("DELETE FROM logs WHERE created_at < '2024-01-01' AND level = 'debug'");
            Supplier<String> fn__8765 = () -> "delete multi where";
            test_131.assert_(t_8779, fn__8765);
        } finally {
            test_131.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBubblesWithoutWhere__1793() {
        Test test_132 = new Test();
        try {
            boolean didBubble__1242;
            boolean didBubble_10978;
            try {
                SrcGlobal.deleteFrom(SrcTest.sid__505("users")).toSql();
                didBubble_10978 = false;
            } catch (RuntimeException ignored$13) {
                didBubble_10978 = true;
            }
            didBubble__1242 = didBubble_10978;
            Supplier<String> fn__8761 = () -> "delete without WHERE should bubble";
            test_132.assert_(didBubble__1242, fn__8761);
        } finally {
            test_132.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryOrWhere__1794() {
        Test test_133 = new Test();
        try {
            SafeIdentifier t_8746 = SrcTest.sid__505("sessions");
            SqlBuilder t_8747 = new SqlBuilder();
            t_8747.appendSafe("expired = ");
            t_8747.appendBoolean(true);
            SqlFragment t_8750 = t_8747.getAccumulated();
            DeleteQuery t_8751 = SrcGlobal.deleteFrom(t_8746).where(t_8750);
            SqlBuilder t_8752 = new SqlBuilder();
            t_8752.appendSafe("created_at < ");
            t_8752.appendString("2023-01-01");
            SqlFragment t_4289;
            t_4289 = t_8751.orWhere(t_8752.getAccumulated()).toSql();
            SqlFragment q__1244 = t_4289;
            boolean t_8759 = q__1244.toString().equals("DELETE FROM sessions WHERE expired = TRUE OR created_at < '2023-01-01'");
            Supplier<String> fn__8745 = () -> "delete orWhere";
            test_133.assert_(t_8759, fn__8745);
        } finally {
            test_133.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryWithLimit__1797() {
        Test test_134 = new Test();
        try {
            SafeIdentifier t_8735 = SrcTest.sid__505("logs");
            SqlBuilder t_8736 = new SqlBuilder();
            t_8736.appendSafe("level = ");
            t_8736.appendString("debug");
            SqlFragment t_8739 = t_8736.getAccumulated();
            DeleteQuery t_4270;
            t_4270 = SrcGlobal.deleteFrom(t_8735).where(t_8739).limit(1000);
            SqlFragment t_4271;
            t_4271 = t_4270.toSql();
            SqlFragment q__1246 = t_4271;
            boolean t_8743 = q__1246.toString().equals("DELETE FROM logs WHERE level = 'debug' LIMIT 1000");
            Supplier<String> fn__8734 = () -> "delete limit";
            test_134.assert_(t_8743, fn__8734);
        } finally {
            test_134.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__1799() {
        Test test_141 = new Test();
        try {
            SafeIdentifier t_4259;
            t_4259 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__1284 = t_4259;
            boolean t_8732 = id__1284.getSqlValue().equals("user_name");
            Supplier<String> fn__8729 = () -> "value should round-trip";
            test_141.assert_(t_8732, fn__8729);
        } finally {
            test_141.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__1800() {
        Test test_142 = new Test();
        try {
            boolean didBubble__1286;
            boolean didBubble_10979;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_10979 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_10979 = true;
            }
            didBubble__1286 = didBubble_10979;
            Supplier<String> fn__8726 = () -> "empty string should bubble";
            test_142.assert_(didBubble__1286, fn__8726);
        } finally {
            test_142.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__1801() {
        Test test_143 = new Test();
        try {
            boolean didBubble__1288;
            boolean didBubble_10980;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_10980 = false;
            } catch (RuntimeException ignored$15) {
                didBubble_10980 = true;
            }
            didBubble__1288 = didBubble_10980;
            Supplier<String> fn__8723 = () -> "leading digit should bubble";
            test_143.assert_(didBubble__1288, fn__8723);
        } finally {
            test_143.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__1802() {
        Test test_144 = new Test();
        try {
            List<String> cases__1290 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__8720 = c__1291 -> {
                boolean didBubble__1292;
                boolean didBubble_10981;
                try {
                    SrcGlobal.safeIdentifier(c__1291);
                    didBubble_10981 = false;
                } catch (RuntimeException ignored$16) {
                    didBubble_10981 = true;
                }
                didBubble__1292 = didBubble_10981;
                Supplier<String> fn__8717 = () -> "should reject: " + c__1291;
                test_144.assert_(didBubble__1292, fn__8717);
            };
            cases__1290.forEach(fn__8720);
        } finally {
            test_144.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__1803() {
        Test test_145 = new Test();
        try {
            SafeIdentifier t_4236;
            t_4236 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_4237 = t_4236;
            SafeIdentifier t_4238;
            t_4238 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_4239 = t_4238;
            StringField t_8707 = new StringField();
            FieldDef t_8708 = new FieldDef(t_4239, t_8707, false);
            SafeIdentifier t_4242;
            t_4242 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_4243 = t_4242;
            IntField t_8709 = new IntField();
            FieldDef t_8710 = new FieldDef(t_4243, t_8709, false);
            TableDef td__1294 = new TableDef(t_4237, List.of(t_8708, t_8710));
            FieldDef t_4247;
            t_4247 = td__1294.field("age");
            FieldDef f__1295 = t_4247;
            boolean t_8715 = f__1295.getName().getSqlValue().equals("age");
            Supplier<String> fn__8706 = () -> "should find age field";
            test_145.assert_(t_8715, fn__8706);
        } finally {
            test_145.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__1804() {
        Test test_146 = new Test();
        try {
            SafeIdentifier t_4227;
            t_4227 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_4228 = t_4227;
            SafeIdentifier t_4229;
            t_4229 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_4230 = t_4229;
            StringField t_8701 = new StringField();
            FieldDef t_8702 = new FieldDef(t_4230, t_8701, false);
            TableDef td__1297 = new TableDef(t_4228, List.of(t_8702));
            boolean didBubble__1298;
            boolean didBubble_10982;
            try {
                td__1297.field("nonexistent");
                didBubble_10982 = false;
            } catch (RuntimeException ignored$17) {
                didBubble_10982 = true;
            }
            didBubble__1298 = didBubble_10982;
            Supplier<String> fn__8700 = () -> "unknown field should bubble";
            test_146.assert_(didBubble__1298, fn__8700);
        } finally {
            test_146.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__1805() {
        Test test_147 = new Test();
        try {
            SafeIdentifier t_4215;
            t_4215 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_4216 = t_4215;
            StringField t_8689 = new StringField();
            FieldDef required__1300 = new FieldDef(t_4216, t_8689, false);
            SafeIdentifier t_4219;
            t_4219 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_4220 = t_4219;
            StringField t_8691 = new StringField();
            FieldDef optional__1301 = new FieldDef(t_4220, t_8691, true);
            boolean t_8695 = !required__1300.isNullable();
            Supplier<String> fn__8688 = () -> "required field should not be nullable";
            test_147.assert_(t_8695, fn__8688);
            boolean t_8697 = optional__1301.isNullable();
            Supplier<String> fn__8687 = () -> "optional field should be nullable";
            test_147.assert_(t_8697, fn__8687);
        } finally {
            test_147.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__1806() {
        Test test_151 = new Test();
        try {
            Function<String, String> build__1427 = name__1429 -> {
                SqlBuilder t_8669 = new SqlBuilder();
                t_8669.appendSafe("select * from hi where name = ");
                t_8669.appendString(name__1429);
                return t_8669.getAccumulated().toString();
            };
            Function<String, String> buildWrong__1428 = name__1431 -> "select * from hi where name = '" + name__1431 + "'";
            String actual_1808 = build__1427.apply("world");
            boolean t_8679 = actual_1808.equals("select * from hi where name = 'world'");
            Supplier<String> fn__8676 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_1808 + ")";
            test_151.assert_(t_8679, fn__8676);
            String bobbyTables__1433 = "Robert'); drop table hi;--";
            String actual_1810 = build__1427.apply("Robert'); drop table hi;--");
            boolean t_8683 = actual_1810.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__8675 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_1810 + ")";
            test_151.assert_(t_8683, fn__8675);
            Supplier<String> fn__8674 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_151.assert_(true, fn__8674);
        } finally {
            test_151.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__1814() {
        Test test_152 = new Test();
        try {
            SqlBuilder t_8637 = new SqlBuilder();
            t_8637.appendSafe("v = ");
            t_8637.appendString("");
            String actual_1815 = t_8637.getAccumulated().toString();
            boolean t_8643 = actual_1815.equals("v = ''");
            Supplier<String> fn__8636 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_1815 + ")";
            test_152.assert_(t_8643, fn__8636);
            SqlBuilder t_8645 = new SqlBuilder();
            t_8645.appendSafe("v = ");
            t_8645.appendString("a''b");
            String actual_1818 = t_8645.getAccumulated().toString();
            boolean t_8651 = actual_1818.equals("v = 'a''''b'");
            Supplier<String> fn__8635 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_1818 + ")";
            test_152.assert_(t_8651, fn__8635);
            SqlBuilder t_8653 = new SqlBuilder();
            t_8653.appendSafe("v = ");
            t_8653.appendString("Hello \u4e16\u754c");
            String actual_1821 = t_8653.getAccumulated().toString();
            boolean t_8659 = actual_1821.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__8634 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_1821 + ")";
            test_152.assert_(t_8659, fn__8634);
            SqlBuilder t_8661 = new SqlBuilder();
            t_8661.appendSafe("v = ");
            t_8661.appendString("Line1\nLine2");
            String actual_1824 = t_8661.getAccumulated().toString();
            boolean t_8667 = actual_1824.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__8633 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_1824 + ")";
            test_152.assert_(t_8667, fn__8633);
        } finally {
            test_152.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__1827() {
        Test test_153 = new Test();
        try {
            SqlBuilder t_8608 = new SqlBuilder();
            t_8608.appendSafe("select ");
            t_8608.appendInt32(42);
            t_8608.appendSafe(", ");
            t_8608.appendInt64(43);
            t_8608.appendSafe(", ");
            t_8608.appendFloat64(19.99D);
            t_8608.appendSafe(", ");
            t_8608.appendBoolean(true);
            t_8608.appendSafe(", ");
            t_8608.appendBoolean(false);
            String actual_1828 = t_8608.getAccumulated().toString();
            boolean t_8622 = actual_1828.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__8607 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_1828 + ")";
            test_153.assert_(t_8622, fn__8607);
            LocalDate t_4160;
            t_4160 = LocalDate.of(2024, 12, 25);
            LocalDate date__1436 = t_4160;
            SqlBuilder t_8624 = new SqlBuilder();
            t_8624.appendSafe("insert into t values (");
            t_8624.appendDate(date__1436);
            t_8624.appendSafe(")");
            String actual_1831 = t_8624.getAccumulated().toString();
            boolean t_8631 = actual_1831.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__8606 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_1831 + ")";
            test_153.assert_(t_8631, fn__8606);
        } finally {
            test_153.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__1834() {
        Test test_154 = new Test();
        try {
            SqlBuilder t_8552 = new SqlBuilder();
            t_8552.appendSafe("v IN (");
            t_8552.appendStringList(List.of("a", "b", "c'd"));
            t_8552.appendSafe(")");
            String actual_1835 = t_8552.getAccumulated().toString();
            boolean t_8559 = actual_1835.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__8551 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_1835 + ")";
            test_154.assert_(t_8559, fn__8551);
            SqlBuilder t_8561 = new SqlBuilder();
            t_8561.appendSafe("v IN (");
            t_8561.appendInt32List(List.of(1, 2, 3));
            t_8561.appendSafe(")");
            String actual_1838 = t_8561.getAccumulated().toString();
            boolean t_8568 = actual_1838.equals("v IN (1, 2, 3)");
            Supplier<String> fn__8550 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_1838 + ")";
            test_154.assert_(t_8568, fn__8550);
            SqlBuilder t_8570 = new SqlBuilder();
            t_8570.appendSafe("v IN (");
            t_8570.appendInt64List(List.of(1, 2));
            t_8570.appendSafe(")");
            String actual_1841 = t_8570.getAccumulated().toString();
            boolean t_8577 = actual_1841.equals("v IN (1, 2)");
            Supplier<String> fn__8549 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_1841 + ")";
            test_154.assert_(t_8577, fn__8549);
            SqlBuilder t_8579 = new SqlBuilder();
            t_8579.appendSafe("v IN (");
            t_8579.appendFloat64List(List.of(1.0D, 2.0D));
            t_8579.appendSafe(")");
            String actual_1844 = t_8579.getAccumulated().toString();
            boolean t_8586 = actual_1844.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__8548 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_1844 + ")";
            test_154.assert_(t_8586, fn__8548);
            SqlBuilder t_8588 = new SqlBuilder();
            t_8588.appendSafe("v IN (");
            t_8588.appendBooleanList(List.of(true, false));
            t_8588.appendSafe(")");
            String actual_1847 = t_8588.getAccumulated().toString();
            boolean t_8595 = actual_1847.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__8547 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_1847 + ")";
            test_154.assert_(t_8595, fn__8547);
            LocalDate t_4132;
            t_4132 = LocalDate.of(2024, 1, 1);
            LocalDate t_4133 = t_4132;
            LocalDate t_4134;
            t_4134 = LocalDate.of(2024, 12, 25);
            LocalDate t_4135 = t_4134;
            List<LocalDate> dates__1438 = List.of(t_4133, t_4135);
            SqlBuilder t_8597 = new SqlBuilder();
            t_8597.appendSafe("v IN (");
            t_8597.appendDateList(dates__1438);
            t_8597.appendSafe(")");
            String actual_1850 = t_8597.getAccumulated().toString();
            boolean t_8604 = actual_1850.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__8546 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_1850 + ")";
            test_154.assert_(t_8604, fn__8546);
        } finally {
            test_154.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__1853() {
        Test test_155 = new Test();
        try {
            double nan__1440;
            nan__1440 = 0.0D / 0.0D;
            SqlBuilder t_8538 = new SqlBuilder();
            t_8538.appendSafe("v = ");
            t_8538.appendFloat64(nan__1440);
            String actual_1854 = t_8538.getAccumulated().toString();
            boolean t_8544 = actual_1854.equals("v = NULL");
            Supplier<String> fn__8537 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_1854 + ")";
            test_155.assert_(t_8544, fn__8537);
        } finally {
            test_155.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__1857() {
        Test test_156 = new Test();
        try {
            double inf__1442;
            inf__1442 = 1.0D / 0.0D;
            SqlBuilder t_8529 = new SqlBuilder();
            t_8529.appendSafe("v = ");
            t_8529.appendFloat64(inf__1442);
            String actual_1858 = t_8529.getAccumulated().toString();
            boolean t_8535 = actual_1858.equals("v = NULL");
            Supplier<String> fn__8528 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_1858 + ")";
            test_156.assert_(t_8535, fn__8528);
        } finally {
            test_156.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__1861() {
        Test test_157 = new Test();
        try {
            double ninf__1444;
            ninf__1444 = -1.0D / 0.0D;
            SqlBuilder t_8520 = new SqlBuilder();
            t_8520.appendSafe("v = ");
            t_8520.appendFloat64(ninf__1444);
            String actual_1862 = t_8520.getAccumulated().toString();
            boolean t_8526 = actual_1862.equals("v = NULL");
            Supplier<String> fn__8519 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_1862 + ")";
            test_157.assert_(t_8526, fn__8519);
        } finally {
            test_157.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__1865() {
        Test test_158 = new Test();
        try {
            SqlBuilder t_8495 = new SqlBuilder();
            t_8495.appendSafe("v = ");
            t_8495.appendFloat64(3.14D);
            String actual_1866 = t_8495.getAccumulated().toString();
            boolean t_8501 = actual_1866.equals("v = 3.14");
            Supplier<String> fn__8494 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_1866 + ")";
            test_158.assert_(t_8501, fn__8494);
            SqlBuilder t_8503 = new SqlBuilder();
            t_8503.appendSafe("v = ");
            t_8503.appendFloat64(0.0D);
            String actual_1869 = t_8503.getAccumulated().toString();
            boolean t_8509 = actual_1869.equals("v = 0.0");
            Supplier<String> fn__8493 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_1869 + ")";
            test_158.assert_(t_8509, fn__8493);
            SqlBuilder t_8511 = new SqlBuilder();
            t_8511.appendSafe("v = ");
            t_8511.appendFloat64(-42.5D);
            String actual_1872 = t_8511.getAccumulated().toString();
            boolean t_8517 = actual_1872.equals("v = -42.5");
            Supplier<String> fn__8492 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_1872 + ")";
            test_158.assert_(t_8517, fn__8492);
        } finally {
            test_158.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__1875() {
        Test test_159 = new Test();
        try {
            LocalDate t_4028;
            t_4028 = LocalDate.of(2024, 6, 15);
            LocalDate d__1447 = t_4028;
            SqlBuilder t_8484 = new SqlBuilder();
            t_8484.appendSafe("v = ");
            t_8484.appendDate(d__1447);
            String actual_1876 = t_8484.getAccumulated().toString();
            boolean t_8490 = actual_1876.equals("v = '2024-06-15'");
            Supplier<String> fn__8483 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_1876 + ")";
            test_159.assert_(t_8490, fn__8483);
        } finally {
            test_159.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__1879() {
        Test test_160 = new Test();
        try {
            String name__1449 = "Someone";
            SqlBuilder t_8452 = new SqlBuilder();
            t_8452.appendSafe("where p.last_name = ");
            t_8452.appendString("Someone");
            SqlFragment condition__1450 = t_8452.getAccumulated();
            SqlBuilder t_8456 = new SqlBuilder();
            t_8456.appendSafe("select p.id from person p ");
            t_8456.appendFragment(condition__1450);
            String actual_1881 = t_8456.getAccumulated().toString();
            boolean t_8462 = actual_1881.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__8451 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1881 + ")";
            test_160.assert_(t_8462, fn__8451);
            SqlBuilder t_8464 = new SqlBuilder();
            t_8464.appendSafe("select p.id from person p ");
            t_8464.appendPart(condition__1450.toSource());
            String actual_1884 = t_8464.getAccumulated().toString();
            boolean t_8471 = actual_1884.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__8450 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1884 + ")";
            test_160.assert_(t_8471, fn__8450);
            List<SqlPart> parts__1451 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_8475 = new SqlBuilder();
            t_8475.appendSafe("select ");
            t_8475.appendPartList(parts__1451);
            String actual_1887 = t_8475.getAccumulated().toString();
            boolean t_8481 = actual_1887.equals("select 'a''b', 3");
            Supplier<String> fn__8449 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_1887 + ")";
            test_160.assert_(t_8481, fn__8449);
        } finally {
            test_160.softFailToHard();
        }
    }
}
