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
    static SafeIdentifier csid__342(String name__487) {
        SafeIdentifier t_3188;
        t_3188 = SrcGlobal.safeIdentifier(name__487);
        return t_3188;
    }
    static TableDef userTable__343() {
        return new TableDef(SrcTest.csid__342("users"), List.of(new FieldDef(SrcTest.csid__342("name"), new StringField(), false), new FieldDef(SrcTest.csid__342("email"), new StringField(), false), new FieldDef(SrcTest.csid__342("age"), new IntField(), true), new FieldDef(SrcTest.csid__342("score"), new FloatField(), true), new FieldDef(SrcTest.csid__342("active"), new BoolField(), true)));
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__1020() {
        Test test_22 = new Test();
        try {
            Map<String, String> params__491 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_5514 = SrcTest.userTable__343();
            SafeIdentifier t_5515 = SrcTest.csid__342("name");
            SafeIdentifier t_5516 = SrcTest.csid__342("email");
            Changeset cs__492 = SrcGlobal.changeset(t_5514, params__491).cast(List.of(t_5515, t_5516));
            boolean t_5519 = cs__492.getChanges().containsKey("name");
            Supplier<String> fn__5509 = () -> "name should be in changes";
            test_22.assert_(t_5519, fn__5509);
            boolean t_5523 = cs__492.getChanges().containsKey("email");
            Supplier<String> fn__5508 = () -> "email should be in changes";
            test_22.assert_(t_5523, fn__5508);
            boolean t_5529 = !cs__492.getChanges().containsKey("admin");
            Supplier<String> fn__5507 = () -> "admin must be dropped (not in whitelist)";
            test_22.assert_(t_5529, fn__5507);
            boolean t_5531 = cs__492.isValid();
            Supplier<String> fn__5506 = () -> "should still be valid";
            test_22.assert_(t_5531, fn__5506);
        } finally {
            test_22.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__1021() {
        Test test_23 = new Test();
        try {
            Map<String, String> params__494 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_5492 = SrcTest.userTable__343();
            SafeIdentifier t_5493 = SrcTest.csid__342("name");
            Changeset cs__495 = SrcGlobal.changeset(t_5492, params__494).cast(List.of(t_5493)).cast(List.of(SrcTest.csid__342("email")));
            boolean t_5500 = !cs__495.getChanges().containsKey("name");
            Supplier<String> fn__5488 = () -> "name must be excluded by second cast";
            test_23.assert_(t_5500, fn__5488);
            boolean t_5503 = cs__495.getChanges().containsKey("email");
            Supplier<String> fn__5487 = () -> "email should be present";
            test_23.assert_(t_5503, fn__5487);
        } finally {
            test_23.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__1022() {
        Test test_24 = new Test();
        try {
            Map<String, String> params__497 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_5474 = SrcTest.userTable__343();
            SafeIdentifier t_5475 = SrcTest.csid__342("name");
            SafeIdentifier t_5476 = SrcTest.csid__342("email");
            Changeset cs__498 = SrcGlobal.changeset(t_5474, params__497).cast(List.of(t_5475, t_5476));
            boolean t_5481 = !cs__498.getChanges().containsKey("name");
            Supplier<String> fn__5470 = () -> "empty name should not be in changes";
            test_24.assert_(t_5481, fn__5470);
            boolean t_5484 = cs__498.getChanges().containsKey("email");
            Supplier<String> fn__5469 = () -> "email should be in changes";
            test_24.assert_(t_5484, fn__5469);
        } finally {
            test_24.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__1023() {
        Test test_25 = new Test();
        try {
            Map<String, String> params__500 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_5456 = SrcTest.userTable__343();
            SafeIdentifier t_5457 = SrcTest.csid__342("name");
            Changeset cs__501 = SrcGlobal.changeset(t_5456, params__500).cast(List.of(t_5457)).validateRequired(List.of(SrcTest.csid__342("name")));
            boolean t_5461 = cs__501.isValid();
            Supplier<String> fn__5453 = () -> "should be valid";
            test_25.assert_(t_5461, fn__5453);
            boolean t_5467 = cs__501.getErrors().size() == 0;
            Supplier<String> fn__5452 = () -> "no errors expected";
            test_25.assert_(t_5467, fn__5452);
        } finally {
            test_25.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__1024() {
        Test test_26 = new Test();
        try {
            Map<String, String> params__503 = Core.mapConstructor(List.of());
            TableDef t_5432 = SrcTest.userTable__343();
            SafeIdentifier t_5433 = SrcTest.csid__342("name");
            Changeset cs__504 = SrcGlobal.changeset(t_5432, params__503).cast(List.of(t_5433)).validateRequired(List.of(SrcTest.csid__342("name")));
            boolean t_5439 = !cs__504.isValid();
            Supplier<String> fn__5430 = () -> "should be invalid";
            test_26.assert_(t_5439, fn__5430);
            boolean t_5444 = cs__504.getErrors().size() == 1;
            Supplier<String> fn__5429 = () -> "should have one error";
            test_26.assert_(t_5444, fn__5429);
            boolean t_5450 = Core.listGet(cs__504.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__5428 = () -> "error should name the field";
            test_26.assert_(t_5450, fn__5428);
        } finally {
            test_26.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__1025() {
        Test test_27 = new Test();
        try {
            Map<String, String> params__506 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_5420 = SrcTest.userTable__343();
            SafeIdentifier t_5421 = SrcTest.csid__342("name");
            Changeset cs__507 = SrcGlobal.changeset(t_5420, params__506).cast(List.of(t_5421)).validateLength(SrcTest.csid__342("name"), 2, 50);
            boolean t_5425 = cs__507.isValid();
            Supplier<String> fn__5417 = () -> "should be valid";
            test_27.assert_(t_5425, fn__5417);
        } finally {
            test_27.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__1026() {
        Test test_28 = new Test();
        try {
            Map<String, String> params__509 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_5408 = SrcTest.userTable__343();
            SafeIdentifier t_5409 = SrcTest.csid__342("name");
            Changeset cs__510 = SrcGlobal.changeset(t_5408, params__509).cast(List.of(t_5409)).validateLength(SrcTest.csid__342("name"), 2, 50);
            boolean t_5415 = !cs__510.isValid();
            Supplier<String> fn__5405 = () -> "should be invalid";
            test_28.assert_(t_5415, fn__5405);
        } finally {
            test_28.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__1027() {
        Test test_29 = new Test();
        try {
            Map<String, String> params__512 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_5396 = SrcTest.userTable__343();
            SafeIdentifier t_5397 = SrcTest.csid__342("name");
            Changeset cs__513 = SrcGlobal.changeset(t_5396, params__512).cast(List.of(t_5397)).validateLength(SrcTest.csid__342("name"), 2, 10);
            boolean t_5403 = !cs__513.isValid();
            Supplier<String> fn__5393 = () -> "should be invalid";
            test_29.assert_(t_5403, fn__5393);
        } finally {
            test_29.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__1028() {
        Test test_30 = new Test();
        try {
            Map<String, String> params__515 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_5385 = SrcTest.userTable__343();
            SafeIdentifier t_5386 = SrcTest.csid__342("age");
            Changeset cs__516 = SrcGlobal.changeset(t_5385, params__515).cast(List.of(t_5386)).validateInt(SrcTest.csid__342("age"));
            boolean t_5390 = cs__516.isValid();
            Supplier<String> fn__5382 = () -> "should be valid";
            test_30.assert_(t_5390, fn__5382);
        } finally {
            test_30.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__1029() {
        Test test_31 = new Test();
        try {
            Map<String, String> params__518 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_5373 = SrcTest.userTable__343();
            SafeIdentifier t_5374 = SrcTest.csid__342("age");
            Changeset cs__519 = SrcGlobal.changeset(t_5373, params__518).cast(List.of(t_5374)).validateInt(SrcTest.csid__342("age"));
            boolean t_5380 = !cs__519.isValid();
            Supplier<String> fn__5370 = () -> "should be invalid";
            test_31.assert_(t_5380, fn__5370);
        } finally {
            test_31.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__1030() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__521 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_5362 = SrcTest.userTable__343();
            SafeIdentifier t_5363 = SrcTest.csid__342("score");
            Changeset cs__522 = SrcGlobal.changeset(t_5362, params__521).cast(List.of(t_5363)).validateFloat(SrcTest.csid__342("score"));
            boolean t_5367 = cs__522.isValid();
            Supplier<String> fn__5359 = () -> "should be valid";
            test_32.assert_(t_5367, fn__5359);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__1031() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__524 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_5351 = SrcTest.userTable__343();
            SafeIdentifier t_5352 = SrcTest.csid__342("age");
            Changeset cs__525 = SrcGlobal.changeset(t_5351, params__524).cast(List.of(t_5352)).validateInt64(SrcTest.csid__342("age"));
            boolean t_5356 = cs__525.isValid();
            Supplier<String> fn__5348 = () -> "should be valid";
            test_33.assert_(t_5356, fn__5348);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__1032() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__527 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_5339 = SrcTest.userTable__343();
            SafeIdentifier t_5340 = SrcTest.csid__342("age");
            Changeset cs__528 = SrcGlobal.changeset(t_5339, params__527).cast(List.of(t_5340)).validateInt64(SrcTest.csid__342("age"));
            boolean t_5346 = !cs__528.isValid();
            Supplier<String> fn__5336 = () -> "should be invalid";
            test_34.assert_(t_5346, fn__5336);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__1033() {
        Test test_35 = new Test();
        try {
            Consumer<String> fn__5333 = v__530 -> {
                Map<String, String> params__531 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__530)));
                TableDef t_5325 = SrcTest.userTable__343();
                SafeIdentifier t_5326 = SrcTest.csid__342("active");
                Changeset cs__532 = SrcGlobal.changeset(t_5325, params__531).cast(List.of(t_5326)).validateBool(SrcTest.csid__342("active"));
                boolean t_5330 = cs__532.isValid();
                Supplier<String> fn__5322 = () -> "should accept: " + v__530;
                test_35.assert_(t_5330, fn__5322);
            };
            List.of("true", "1", "yes", "on").forEach(fn__5333);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__1034() {
        Test test_36 = new Test();
        try {
            Consumer<String> fn__5319 = v__534 -> {
                Map<String, String> params__535 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__534)));
                TableDef t_5311 = SrcTest.userTable__343();
                SafeIdentifier t_5312 = SrcTest.csid__342("active");
                Changeset cs__536 = SrcGlobal.changeset(t_5311, params__535).cast(List.of(t_5312)).validateBool(SrcTest.csid__342("active"));
                boolean t_5316 = cs__536.isValid();
                Supplier<String> fn__5308 = () -> "should accept: " + v__534;
                test_36.assert_(t_5316, fn__5308);
            };
            List.of("false", "0", "no", "off").forEach(fn__5319);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__1035() {
        Test test_37 = new Test();
        try {
            Consumer<String> fn__5305 = v__538 -> {
                Map<String, String> params__539 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__538)));
                TableDef t_5296 = SrcTest.userTable__343();
                SafeIdentifier t_5297 = SrcTest.csid__342("active");
                Changeset cs__540 = SrcGlobal.changeset(t_5296, params__539).cast(List.of(t_5297)).validateBool(SrcTest.csid__342("active"));
                boolean t_5303 = !cs__540.isValid();
                Supplier<String> fn__5293 = () -> "should reject ambiguous: " + v__538;
                test_37.assert_(t_5303, fn__5293);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__5305);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__1036() {
        Test test_38 = new Test();
        try {
            Map<String, String> params__542 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_5281 = SrcTest.userTable__343();
            SafeIdentifier t_5282 = SrcTest.csid__342("name");
            SafeIdentifier t_5283 = SrcTest.csid__342("email");
            Changeset cs__543 = SrcGlobal.changeset(t_5281, params__542).cast(List.of(t_5282, t_5283)).validateRequired(List.of(SrcTest.csid__342("name"), SrcTest.csid__342("email")));
            SqlFragment t_2989;
            t_2989 = cs__543.toInsertSql();
            SqlFragment sqlFrag__544 = t_2989;
            String s__545 = sqlFrag__544.toString();
            boolean t_5290 = s__545.indexOf("''") >= 0;
            Supplier<String> fn__5277 = () -> "single quote must be doubled: " + s__545;
            test_38.assert_(t_5290, fn__5277);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__1037() {
        Test test_39 = new Test();
        try {
            Map<String, String> params__547 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_5261 = SrcTest.userTable__343();
            SafeIdentifier t_5262 = SrcTest.csid__342("name");
            SafeIdentifier t_5263 = SrcTest.csid__342("email");
            Changeset cs__548 = SrcGlobal.changeset(t_5261, params__547).cast(List.of(t_5262, t_5263)).validateRequired(List.of(SrcTest.csid__342("name"), SrcTest.csid__342("email")));
            SqlFragment t_2968;
            t_2968 = cs__548.toInsertSql();
            SqlFragment sqlFrag__549 = t_2968;
            String s__550 = sqlFrag__549.toString();
            boolean t_5270 = s__550.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__5257 = () -> "has INSERT INTO: " + s__550;
            test_39.assert_(t_5270, fn__5257);
            boolean t_5274 = s__550.indexOf("'Alice'") >= 0;
            Supplier<String> fn__5256 = () -> "has quoted name: " + s__550;
            test_39.assert_(t_5274, fn__5256);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__1038() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__552 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_5243 = SrcTest.userTable__343();
            SafeIdentifier t_5244 = SrcTest.csid__342("name");
            SafeIdentifier t_5245 = SrcTest.csid__342("email");
            SafeIdentifier t_5246 = SrcTest.csid__342("age");
            Changeset cs__553 = SrcGlobal.changeset(t_5243, params__552).cast(List.of(t_5244, t_5245, t_5246)).validateRequired(List.of(SrcTest.csid__342("name"), SrcTest.csid__342("email")));
            SqlFragment t_2951;
            t_2951 = cs__553.toInsertSql();
            SqlFragment sqlFrag__554 = t_2951;
            String s__555 = sqlFrag__554.toString();
            boolean t_5253 = s__555.indexOf("25") >= 0;
            Supplier<String> fn__5238 = () -> "age rendered unquoted: " + s__555;
            test_40.assert_(t_5253, fn__5238);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__1039() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__557 = Core.mapConstructor(List.of());
            TableDef t_5231 = SrcTest.userTable__343();
            SafeIdentifier t_5232 = SrcTest.csid__342("name");
            Changeset cs__558 = SrcGlobal.changeset(t_5231, params__557).cast(List.of(t_5232)).validateRequired(List.of(SrcTest.csid__342("name")));
            boolean didBubble__559;
            boolean didBubble_5832;
            try {
                cs__558.toInsertSql();
                didBubble_5832 = false;
            } catch (RuntimeException ignored$4) {
                didBubble_5832 = true;
            }
            didBubble__559 = didBubble_5832;
            Supplier<String> fn__5229 = () -> "invalid changeset should bubble";
            test_41.assert_(didBubble__559, fn__5229);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__1040() {
        Test test_42 = new Test();
        try {
            TableDef strictTable__561 = new TableDef(SrcTest.csid__342("posts"), List.of(new FieldDef(SrcTest.csid__342("title"), new StringField(), false), new FieldDef(SrcTest.csid__342("body"), new StringField(), true)));
            Map<String, String> params__562 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_5222 = SrcTest.csid__342("body");
            Changeset cs__563 = SrcGlobal.changeset(strictTable__561, params__562).cast(List.of(t_5222));
            boolean t_5224 = cs__563.isValid();
            Supplier<String> fn__5211 = () -> "changeset should appear valid (no explicit validation run)";
            test_42.assert_(t_5224, fn__5211);
            boolean didBubble__564;
            boolean didBubble_5833;
            try {
                cs__563.toInsertSql();
                didBubble_5833 = false;
            } catch (RuntimeException ignored$5) {
                didBubble_5833 = true;
            }
            didBubble__564 = didBubble_5833;
            Supplier<String> fn__5210 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_42.assert_(didBubble__564, fn__5210);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__1041() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__566 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_5201 = SrcTest.userTable__343();
            SafeIdentifier t_5202 = SrcTest.csid__342("name");
            Changeset cs__567 = SrcGlobal.changeset(t_5201, params__566).cast(List.of(t_5202)).validateRequired(List.of(SrcTest.csid__342("name")));
            SqlFragment t_2911;
            t_2911 = cs__567.toUpdateSql(42);
            SqlFragment sqlFrag__568 = t_2911;
            String s__569 = sqlFrag__568.toString();
            boolean t_5208 = s__569.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__5198 = () -> "got: " + s__569;
            test_43.assert_(t_5208, fn__5198);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__1042() {
        Test test_44 = new Test();
        try {
            Map<String, String> params__571 = Core.mapConstructor(List.of());
            TableDef t_5191 = SrcTest.userTable__343();
            SafeIdentifier t_5192 = SrcTest.csid__342("name");
            Changeset cs__572 = SrcGlobal.changeset(t_5191, params__571).cast(List.of(t_5192)).validateRequired(List.of(SrcTest.csid__342("name")));
            boolean didBubble__573;
            boolean didBubble_5834;
            try {
                cs__572.toUpdateSql(1);
                didBubble_5834 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_5834 = true;
            }
            didBubble__573 = didBubble_5834;
            Supplier<String> fn__5189 = () -> "invalid changeset should bubble";
            test_44.assert_(didBubble__573, fn__5189);
        } finally {
            test_44.softFailToHard();
        }
    }
    static SafeIdentifier sid__344(String name__678) {
        SafeIdentifier t_2781;
        t_2781 = SrcGlobal.safeIdentifier(name__678);
        return t_2781;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__1079() {
        Test test_45 = new Test();
        try {
            Query q__681 = SrcGlobal.from(SrcTest.sid__344("users"));
            boolean t_5084 = q__681.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__5079 = () -> "bare query";
            test_45.assert_(t_5084, fn__5079);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__1080() {
        Test test_46 = new Test();
        try {
            SafeIdentifier t_5070 = SrcTest.sid__344("users");
            SafeIdentifier t_5071 = SrcTest.sid__344("id");
            SafeIdentifier t_5072 = SrcTest.sid__344("name");
            Query q__683 = SrcGlobal.from(t_5070).select(List.of(t_5071, t_5072));
            boolean t_5077 = q__683.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__5069 = () -> "select columns";
            test_46.assert_(t_5077, fn__5069);
        } finally {
            test_46.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__1081() {
        Test test_47 = new Test();
        try {
            SafeIdentifier t_5058 = SrcTest.sid__344("users");
            SqlBuilder t_5059 = new SqlBuilder();
            t_5059.appendSafe("age > ");
            t_5059.appendInt32(18);
            SqlFragment t_5062 = t_5059.getAccumulated();
            Query q__685 = SrcGlobal.from(t_5058).where(t_5062);
            boolean t_5067 = q__685.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__5057 = () -> "where int";
            test_47.assert_(t_5067, fn__5057);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__1083() {
        Test test_48 = new Test();
        try {
            SafeIdentifier t_5046 = SrcTest.sid__344("users");
            SqlBuilder t_5047 = new SqlBuilder();
            t_5047.appendSafe("active = ");
            t_5047.appendBoolean(true);
            SqlFragment t_5050 = t_5047.getAccumulated();
            Query q__687 = SrcGlobal.from(t_5046).where(t_5050);
            boolean t_5055 = q__687.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__5045 = () -> "where bool";
            test_48.assert_(t_5055, fn__5045);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__1085() {
        Test test_49 = new Test();
        try {
            SafeIdentifier t_5029 = SrcTest.sid__344("users");
            SqlBuilder t_5030 = new SqlBuilder();
            t_5030.appendSafe("age > ");
            t_5030.appendInt32(18);
            SqlFragment t_5033 = t_5030.getAccumulated();
            Query t_5034 = SrcGlobal.from(t_5029).where(t_5033);
            SqlBuilder t_5035 = new SqlBuilder();
            t_5035.appendSafe("active = ");
            t_5035.appendBoolean(true);
            Query q__689 = t_5034.where(t_5035.getAccumulated());
            boolean t_5043 = q__689.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__5028 = () -> "chained where";
            test_49.assert_(t_5043, fn__5028);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__1088() {
        Test test_50 = new Test();
        try {
            SafeIdentifier t_5020 = SrcTest.sid__344("users");
            SafeIdentifier t_5021 = SrcTest.sid__344("name");
            Query q__691 = SrcGlobal.from(t_5020).orderBy(t_5021, true);
            boolean t_5026 = q__691.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__5019 = () -> "order asc";
            test_50.assert_(t_5026, fn__5019);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__1089() {
        Test test_51 = new Test();
        try {
            SafeIdentifier t_5011 = SrcTest.sid__344("users");
            SafeIdentifier t_5012 = SrcTest.sid__344("created_at");
            Query q__693 = SrcGlobal.from(t_5011).orderBy(t_5012, false);
            boolean t_5017 = q__693.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__5010 = () -> "order desc";
            test_51.assert_(t_5017, fn__5010);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__1090() {
        Test test_52 = new Test();
        try {
            Query t_2715;
            t_2715 = SrcGlobal.from(SrcTest.sid__344("users")).limit(10);
            Query t_2716;
            t_2716 = t_2715.offset(20);
            Query q__695 = t_2716;
            boolean t_5008 = q__695.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__5003 = () -> "limit/offset";
            test_52.assert_(t_5008, fn__5003);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__1091() {
        Test test_53 = new Test();
        try {
            boolean didBubble__697;
            boolean didBubble_5835;
            try {
                SrcGlobal.from(SrcTest.sid__344("users")).limit(-1);
                didBubble_5835 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_5835 = true;
            }
            didBubble__697 = didBubble_5835;
            Supplier<String> fn__4999 = () -> "negative limit should bubble";
            test_53.assert_(didBubble__697, fn__4999);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__1092() {
        Test test_54 = new Test();
        try {
            boolean didBubble__699;
            boolean didBubble_5836;
            try {
                SrcGlobal.from(SrcTest.sid__344("users")).offset(-1);
                didBubble_5836 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_5836 = true;
            }
            didBubble__699 = didBubble_5836;
            Supplier<String> fn__4995 = () -> "negative offset should bubble";
            test_54.assert_(didBubble__699, fn__4995);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__1093() {
        Test test_55 = new Test();
        try {
            int minAge__701 = 21;
            SafeIdentifier t_4973 = SrcTest.sid__344("users");
            SafeIdentifier t_4974 = SrcTest.sid__344("id");
            SafeIdentifier t_4975 = SrcTest.sid__344("name");
            SafeIdentifier t_4976 = SrcTest.sid__344("email");
            Query t_4977 = SrcGlobal.from(t_4973).select(List.of(t_4974, t_4975, t_4976));
            SqlBuilder t_4978 = new SqlBuilder();
            t_4978.appendSafe("age >= ");
            t_4978.appendInt32(21);
            Query t_4982 = t_4977.where(t_4978.getAccumulated());
            SqlBuilder t_4983 = new SqlBuilder();
            t_4983.appendSafe("active = ");
            t_4983.appendBoolean(true);
            Query t_2701;
            t_2701 = t_4982.where(t_4983.getAccumulated()).orderBy(SrcTest.sid__344("name"), true).limit(25);
            Query t_2702;
            t_2702 = t_2701.offset(0);
            Query q__702 = t_2702;
            boolean t_4993 = q__702.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__4972 = () -> "complex query";
            test_55.assert_(t_4993, fn__4972);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__1096() {
        Test test_56 = new Test();
        try {
            Query q__704 = SrcGlobal.from(SrcTest.sid__344("users"));
            SqlFragment t_2678;
            t_2678 = q__704.safeToSql(100);
            SqlFragment t_2679 = t_2678;
            String s__705 = t_2679.toString();
            boolean t_4970 = s__705.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__4966 = () -> "should have limit: " + s__705;
            test_56.assert_(t_4970, fn__4966);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__1097() {
        Test test_57 = new Test();
        try {
            Query t_2670;
            t_2670 = SrcGlobal.from(SrcTest.sid__344("users")).limit(5);
            Query q__707 = t_2670;
            SqlFragment t_2673;
            t_2673 = q__707.safeToSql(100);
            SqlFragment t_2674 = t_2673;
            String s__708 = t_2674.toString();
            boolean t_4964 = s__708.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__4960 = () -> "explicit limit preserved: " + s__708;
            test_57.assert_(t_4964, fn__4960);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__1098() {
        Test test_58 = new Test();
        try {
            boolean didBubble__710;
            boolean didBubble_5837;
            try {
                SrcGlobal.from(SrcTest.sid__344("users")).safeToSql(-1);
                didBubble_5837 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_5837 = true;
            }
            didBubble__710 = didBubble_5837;
            Supplier<String> fn__4956 = () -> "negative defaultLimit should bubble";
            test_58.assert_(didBubble__710, fn__4956);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__1099() {
        Test test_59 = new Test();
        try {
            String evil__712 = "'; DROP TABLE users; --";
            SafeIdentifier t_4940 = SrcTest.sid__344("users");
            SqlBuilder t_4941 = new SqlBuilder();
            t_4941.appendSafe("name = ");
            t_4941.appendString("'; DROP TABLE users; --");
            SqlFragment t_4944 = t_4941.getAccumulated();
            Query q__713 = SrcGlobal.from(t_4940).where(t_4944);
            String s__714 = q__713.toSql().toString();
            boolean t_4949 = s__714.indexOf("''") >= 0;
            Supplier<String> fn__4939 = () -> "quotes must be doubled: " + s__714;
            test_59.assert_(t_4949, fn__4939);
            boolean t_4953 = s__714.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__4938 = () -> "structure intact: " + s__714;
            test_59.assert_(t_4953, fn__4938);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__1101() {
        Test test_60 = new Test();
        try {
            String attack__716 = "users; DROP TABLE users; --";
            boolean didBubble__717;
            boolean didBubble_5838;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_5838 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_5838 = true;
            }
            didBubble__717 = didBubble_5838;
            Supplier<String> fn__4935 = () -> "metacharacter-containing name must be rejected at construction";
            test_60.assert_(didBubble__717, fn__4935);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__1102() {
        Test test_61 = new Test();
        try {
            SafeIdentifier t_4924 = SrcTest.sid__344("users");
            SafeIdentifier t_4925 = SrcTest.sid__344("orders");
            SqlBuilder t_4926 = new SqlBuilder();
            t_4926.appendSafe("users.id = orders.user_id");
            SqlFragment t_4928 = t_4926.getAccumulated();
            Query q__719 = SrcGlobal.from(t_4924).innerJoin(t_4925, t_4928);
            boolean t_4933 = q__719.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__4923 = () -> "inner join";
            test_61.assert_(t_4933, fn__4923);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__1104() {
        Test test_62 = new Test();
        try {
            SafeIdentifier t_4912 = SrcTest.sid__344("users");
            SafeIdentifier t_4913 = SrcTest.sid__344("profiles");
            SqlBuilder t_4914 = new SqlBuilder();
            t_4914.appendSafe("users.id = profiles.user_id");
            SqlFragment t_4916 = t_4914.getAccumulated();
            Query q__721 = SrcGlobal.from(t_4912).leftJoin(t_4913, t_4916);
            boolean t_4921 = q__721.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__4911 = () -> "left join";
            test_62.assert_(t_4921, fn__4911);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__1106() {
        Test test_63 = new Test();
        try {
            SafeIdentifier t_4900 = SrcTest.sid__344("orders");
            SafeIdentifier t_4901 = SrcTest.sid__344("users");
            SqlBuilder t_4902 = new SqlBuilder();
            t_4902.appendSafe("orders.user_id = users.id");
            SqlFragment t_4904 = t_4902.getAccumulated();
            Query q__723 = SrcGlobal.from(t_4900).rightJoin(t_4901, t_4904);
            boolean t_4909 = q__723.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__4899 = () -> "right join";
            test_63.assert_(t_4909, fn__4899);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__1108() {
        Test test_64 = new Test();
        try {
            SafeIdentifier t_4888 = SrcTest.sid__344("users");
            SafeIdentifier t_4889 = SrcTest.sid__344("orders");
            SqlBuilder t_4890 = new SqlBuilder();
            t_4890.appendSafe("users.id = orders.user_id");
            SqlFragment t_4892 = t_4890.getAccumulated();
            Query q__725 = SrcGlobal.from(t_4888).fullJoin(t_4889, t_4892);
            boolean t_4897 = q__725.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__4887 = () -> "full join";
            test_64.assert_(t_4897, fn__4887);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__1110() {
        Test test_65 = new Test();
        try {
            SafeIdentifier t_4871 = SrcTest.sid__344("users");
            SafeIdentifier t_4872 = SrcTest.sid__344("orders");
            SqlBuilder t_4873 = new SqlBuilder();
            t_4873.appendSafe("users.id = orders.user_id");
            SqlFragment t_4875 = t_4873.getAccumulated();
            Query t_4876 = SrcGlobal.from(t_4871).innerJoin(t_4872, t_4875);
            SafeIdentifier t_4877 = SrcTest.sid__344("profiles");
            SqlBuilder t_4878 = new SqlBuilder();
            t_4878.appendSafe("users.id = profiles.user_id");
            Query q__727 = t_4876.leftJoin(t_4877, t_4878.getAccumulated());
            boolean t_4885 = q__727.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__4870 = () -> "chained joins";
            test_65.assert_(t_4885, fn__4870);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__1113() {
        Test test_66 = new Test();
        try {
            SafeIdentifier t_4852 = SrcTest.sid__344("users");
            SafeIdentifier t_4853 = SrcTest.sid__344("orders");
            SqlBuilder t_4854 = new SqlBuilder();
            t_4854.appendSafe("users.id = orders.user_id");
            SqlFragment t_4856 = t_4854.getAccumulated();
            Query t_4857 = SrcGlobal.from(t_4852).innerJoin(t_4853, t_4856);
            SqlBuilder t_4858 = new SqlBuilder();
            t_4858.appendSafe("orders.total > ");
            t_4858.appendInt32(100);
            Query t_2585;
            t_2585 = t_4857.where(t_4858.getAccumulated()).orderBy(SrcTest.sid__344("name"), true).limit(10);
            Query q__729 = t_2585;
            boolean t_4868 = q__729.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__4851 = () -> "join with where/order/limit";
            test_66.assert_(t_4868, fn__4851);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__1116() {
        Test test_67 = new Test();
        try {
            SqlFragment c__731 = SrcGlobal.col(SrcTest.sid__344("users"), SrcTest.sid__344("id"));
            boolean t_4849 = c__731.toString().equals("users.id");
            Supplier<String> fn__4843 = () -> "col helper";
            test_67.assert_(t_4849, fn__4843);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__1117() {
        Test test_68 = new Test();
        try {
            SqlFragment onCond__733 = SrcGlobal.col(SrcTest.sid__344("users"), SrcTest.sid__344("id"));
            SqlBuilder b__734 = new SqlBuilder();
            b__734.appendFragment(onCond__733);
            b__734.appendSafe(" = ");
            b__734.appendFragment(SrcGlobal.col(SrcTest.sid__344("orders"), SrcTest.sid__344("user_id")));
            SafeIdentifier t_4834 = SrcTest.sid__344("users");
            SafeIdentifier t_4835 = SrcTest.sid__344("orders");
            SqlFragment t_4836 = b__734.getAccumulated();
            Query q__735 = SrcGlobal.from(t_4834).innerJoin(t_4835, t_4836);
            boolean t_4841 = q__735.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__4823 = () -> "join with col";
            test_68.assert_(t_4841, fn__4823);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__1118() {
        Test test_75 = new Test();
        try {
            SafeIdentifier t_2544;
            t_2544 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__773 = t_2544;
            boolean t_4821 = id__773.getSqlValue().equals("user_name");
            Supplier<String> fn__4818 = () -> "value should round-trip";
            test_75.assert_(t_4821, fn__4818);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__1119() {
        Test test_76 = new Test();
        try {
            boolean didBubble__775;
            boolean didBubble_5839;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_5839 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_5839 = true;
            }
            didBubble__775 = didBubble_5839;
            Supplier<String> fn__4815 = () -> "empty string should bubble";
            test_76.assert_(didBubble__775, fn__4815);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__1120() {
        Test test_77 = new Test();
        try {
            boolean didBubble__777;
            boolean didBubble_5840;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_5840 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_5840 = true;
            }
            didBubble__777 = didBubble_5840;
            Supplier<String> fn__4812 = () -> "leading digit should bubble";
            test_77.assert_(didBubble__777, fn__4812);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__1121() {
        Test test_78 = new Test();
        try {
            List<String> cases__779 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__4809 = c__780 -> {
                boolean didBubble__781;
                boolean didBubble_5841;
                try {
                    SrcGlobal.safeIdentifier(c__780);
                    didBubble_5841 = false;
                } catch (RuntimeException ignored$13) {
                    didBubble_5841 = true;
                }
                didBubble__781 = didBubble_5841;
                Supplier<String> fn__4806 = () -> "should reject: " + c__780;
                test_78.assert_(didBubble__781, fn__4806);
            };
            cases__779.forEach(fn__4809);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__1122() {
        Test test_79 = new Test();
        try {
            SafeIdentifier t_2521;
            t_2521 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_2522 = t_2521;
            SafeIdentifier t_2523;
            t_2523 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_2524 = t_2523;
            StringField t_4796 = new StringField();
            FieldDef t_4797 = new FieldDef(t_2524, t_4796, false);
            SafeIdentifier t_2527;
            t_2527 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_2528 = t_2527;
            IntField t_4798 = new IntField();
            FieldDef t_4799 = new FieldDef(t_2528, t_4798, false);
            TableDef td__783 = new TableDef(t_2522, List.of(t_4797, t_4799));
            FieldDef t_2532;
            t_2532 = td__783.field("age");
            FieldDef f__784 = t_2532;
            boolean t_4804 = f__784.getName().getSqlValue().equals("age");
            Supplier<String> fn__4795 = () -> "should find age field";
            test_79.assert_(t_4804, fn__4795);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__1123() {
        Test test_80 = new Test();
        try {
            SafeIdentifier t_2512;
            t_2512 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_2513 = t_2512;
            SafeIdentifier t_2514;
            t_2514 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_2515 = t_2514;
            StringField t_4790 = new StringField();
            FieldDef t_4791 = new FieldDef(t_2515, t_4790, false);
            TableDef td__786 = new TableDef(t_2513, List.of(t_4791));
            boolean didBubble__787;
            boolean didBubble_5842;
            try {
                td__786.field("nonexistent");
                didBubble_5842 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_5842 = true;
            }
            didBubble__787 = didBubble_5842;
            Supplier<String> fn__4789 = () -> "unknown field should bubble";
            test_80.assert_(didBubble__787, fn__4789);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__1124() {
        Test test_81 = new Test();
        try {
            SafeIdentifier t_2500;
            t_2500 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_2501 = t_2500;
            StringField t_4778 = new StringField();
            FieldDef required__789 = new FieldDef(t_2501, t_4778, false);
            SafeIdentifier t_2504;
            t_2504 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_2505 = t_2504;
            StringField t_4780 = new StringField();
            FieldDef optional__790 = new FieldDef(t_2505, t_4780, true);
            boolean t_4784 = !required__789.isNullable();
            Supplier<String> fn__4777 = () -> "required field should not be nullable";
            test_81.assert_(t_4784, fn__4777);
            boolean t_4786 = optional__790.isNullable();
            Supplier<String> fn__4776 = () -> "optional field should be nullable";
            test_81.assert_(t_4786, fn__4776);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__1125() {
        Test test_85 = new Test();
        try {
            Function<String, String> build__916 = name__918 -> {
                SqlBuilder t_4758 = new SqlBuilder();
                t_4758.appendSafe("select * from hi where name = ");
                t_4758.appendString(name__918);
                return t_4758.getAccumulated().toString();
            };
            Function<String, String> buildWrong__917 = name__920 -> "select * from hi where name = '" + name__920 + "'";
            String actual_1127 = build__916.apply("world");
            boolean t_4768 = actual_1127.equals("select * from hi where name = 'world'");
            Supplier<String> fn__4765 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_1127 + ")";
            test_85.assert_(t_4768, fn__4765);
            String bobbyTables__922 = "Robert'); drop table hi;--";
            String actual_1129 = build__916.apply("Robert'); drop table hi;--");
            boolean t_4772 = actual_1129.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__4764 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_1129 + ")";
            test_85.assert_(t_4772, fn__4764);
            Supplier<String> fn__4763 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_85.assert_(true, fn__4763);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__1133() {
        Test test_86 = new Test();
        try {
            SqlBuilder t_4726 = new SqlBuilder();
            t_4726.appendSafe("v = ");
            t_4726.appendString("");
            String actual_1134 = t_4726.getAccumulated().toString();
            boolean t_4732 = actual_1134.equals("v = ''");
            Supplier<String> fn__4725 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_1134 + ")";
            test_86.assert_(t_4732, fn__4725);
            SqlBuilder t_4734 = new SqlBuilder();
            t_4734.appendSafe("v = ");
            t_4734.appendString("a''b");
            String actual_1137 = t_4734.getAccumulated().toString();
            boolean t_4740 = actual_1137.equals("v = 'a''''b'");
            Supplier<String> fn__4724 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_1137 + ")";
            test_86.assert_(t_4740, fn__4724);
            SqlBuilder t_4742 = new SqlBuilder();
            t_4742.appendSafe("v = ");
            t_4742.appendString("Hello \u4e16\u754c");
            String actual_1140 = t_4742.getAccumulated().toString();
            boolean t_4748 = actual_1140.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__4723 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_1140 + ")";
            test_86.assert_(t_4748, fn__4723);
            SqlBuilder t_4750 = new SqlBuilder();
            t_4750.appendSafe("v = ");
            t_4750.appendString("Line1\nLine2");
            String actual_1143 = t_4750.getAccumulated().toString();
            boolean t_4756 = actual_1143.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__4722 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_1143 + ")";
            test_86.assert_(t_4756, fn__4722);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__1146() {
        Test test_87 = new Test();
        try {
            SqlBuilder t_4697 = new SqlBuilder();
            t_4697.appendSafe("select ");
            t_4697.appendInt32(42);
            t_4697.appendSafe(", ");
            t_4697.appendInt64(43);
            t_4697.appendSafe(", ");
            t_4697.appendFloat64(19.99D);
            t_4697.appendSafe(", ");
            t_4697.appendBoolean(true);
            t_4697.appendSafe(", ");
            t_4697.appendBoolean(false);
            String actual_1147 = t_4697.getAccumulated().toString();
            boolean t_4711 = actual_1147.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__4696 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_1147 + ")";
            test_87.assert_(t_4711, fn__4696);
            LocalDate t_2445;
            t_2445 = LocalDate.of(2024, 12, 25);
            LocalDate date__925 = t_2445;
            SqlBuilder t_4713 = new SqlBuilder();
            t_4713.appendSafe("insert into t values (");
            t_4713.appendDate(date__925);
            t_4713.appendSafe(")");
            String actual_1150 = t_4713.getAccumulated().toString();
            boolean t_4720 = actual_1150.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__4695 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_1150 + ")";
            test_87.assert_(t_4720, fn__4695);
        } finally {
            test_87.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__1153() {
        Test test_88 = new Test();
        try {
            SqlBuilder t_4641 = new SqlBuilder();
            t_4641.appendSafe("v IN (");
            t_4641.appendStringList(List.of("a", "b", "c'd"));
            t_4641.appendSafe(")");
            String actual_1154 = t_4641.getAccumulated().toString();
            boolean t_4648 = actual_1154.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__4640 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_1154 + ")";
            test_88.assert_(t_4648, fn__4640);
            SqlBuilder t_4650 = new SqlBuilder();
            t_4650.appendSafe("v IN (");
            t_4650.appendInt32List(List.of(1, 2, 3));
            t_4650.appendSafe(")");
            String actual_1157 = t_4650.getAccumulated().toString();
            boolean t_4657 = actual_1157.equals("v IN (1, 2, 3)");
            Supplier<String> fn__4639 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_1157 + ")";
            test_88.assert_(t_4657, fn__4639);
            SqlBuilder t_4659 = new SqlBuilder();
            t_4659.appendSafe("v IN (");
            t_4659.appendInt64List(List.of(1, 2));
            t_4659.appendSafe(")");
            String actual_1160 = t_4659.getAccumulated().toString();
            boolean t_4666 = actual_1160.equals("v IN (1, 2)");
            Supplier<String> fn__4638 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_1160 + ")";
            test_88.assert_(t_4666, fn__4638);
            SqlBuilder t_4668 = new SqlBuilder();
            t_4668.appendSafe("v IN (");
            t_4668.appendFloat64List(List.of(1.0D, 2.0D));
            t_4668.appendSafe(")");
            String actual_1163 = t_4668.getAccumulated().toString();
            boolean t_4675 = actual_1163.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__4637 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_1163 + ")";
            test_88.assert_(t_4675, fn__4637);
            SqlBuilder t_4677 = new SqlBuilder();
            t_4677.appendSafe("v IN (");
            t_4677.appendBooleanList(List.of(true, false));
            t_4677.appendSafe(")");
            String actual_1166 = t_4677.getAccumulated().toString();
            boolean t_4684 = actual_1166.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__4636 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_1166 + ")";
            test_88.assert_(t_4684, fn__4636);
            LocalDate t_2417;
            t_2417 = LocalDate.of(2024, 1, 1);
            LocalDate t_2418 = t_2417;
            LocalDate t_2419;
            t_2419 = LocalDate.of(2024, 12, 25);
            LocalDate t_2420 = t_2419;
            List<LocalDate> dates__927 = List.of(t_2418, t_2420);
            SqlBuilder t_4686 = new SqlBuilder();
            t_4686.appendSafe("v IN (");
            t_4686.appendDateList(dates__927);
            t_4686.appendSafe(")");
            String actual_1169 = t_4686.getAccumulated().toString();
            boolean t_4693 = actual_1169.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__4635 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_1169 + ")";
            test_88.assert_(t_4693, fn__4635);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__1172() {
        Test test_89 = new Test();
        try {
            double nan__929;
            nan__929 = 0.0D / 0.0D;
            SqlBuilder t_4627 = new SqlBuilder();
            t_4627.appendSafe("v = ");
            t_4627.appendFloat64(nan__929);
            String actual_1173 = t_4627.getAccumulated().toString();
            boolean t_4633 = actual_1173.equals("v = NULL");
            Supplier<String> fn__4626 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_1173 + ")";
            test_89.assert_(t_4633, fn__4626);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__1176() {
        Test test_90 = new Test();
        try {
            double inf__931;
            inf__931 = 1.0D / 0.0D;
            SqlBuilder t_4618 = new SqlBuilder();
            t_4618.appendSafe("v = ");
            t_4618.appendFloat64(inf__931);
            String actual_1177 = t_4618.getAccumulated().toString();
            boolean t_4624 = actual_1177.equals("v = NULL");
            Supplier<String> fn__4617 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_1177 + ")";
            test_90.assert_(t_4624, fn__4617);
        } finally {
            test_90.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__1180() {
        Test test_91 = new Test();
        try {
            double ninf__933;
            ninf__933 = -1.0D / 0.0D;
            SqlBuilder t_4609 = new SqlBuilder();
            t_4609.appendSafe("v = ");
            t_4609.appendFloat64(ninf__933);
            String actual_1181 = t_4609.getAccumulated().toString();
            boolean t_4615 = actual_1181.equals("v = NULL");
            Supplier<String> fn__4608 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_1181 + ")";
            test_91.assert_(t_4615, fn__4608);
        } finally {
            test_91.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__1184() {
        Test test_92 = new Test();
        try {
            SqlBuilder t_4584 = new SqlBuilder();
            t_4584.appendSafe("v = ");
            t_4584.appendFloat64(3.14D);
            String actual_1185 = t_4584.getAccumulated().toString();
            boolean t_4590 = actual_1185.equals("v = 3.14");
            Supplier<String> fn__4583 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_1185 + ")";
            test_92.assert_(t_4590, fn__4583);
            SqlBuilder t_4592 = new SqlBuilder();
            t_4592.appendSafe("v = ");
            t_4592.appendFloat64(0.0D);
            String actual_1188 = t_4592.getAccumulated().toString();
            boolean t_4598 = actual_1188.equals("v = 0.0");
            Supplier<String> fn__4582 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_1188 + ")";
            test_92.assert_(t_4598, fn__4582);
            SqlBuilder t_4600 = new SqlBuilder();
            t_4600.appendSafe("v = ");
            t_4600.appendFloat64(-42.5D);
            String actual_1191 = t_4600.getAccumulated().toString();
            boolean t_4606 = actual_1191.equals("v = -42.5");
            Supplier<String> fn__4581 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_1191 + ")";
            test_92.assert_(t_4606, fn__4581);
        } finally {
            test_92.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__1194() {
        Test test_93 = new Test();
        try {
            LocalDate t_2313;
            t_2313 = LocalDate.of(2024, 6, 15);
            LocalDate d__936 = t_2313;
            SqlBuilder t_4573 = new SqlBuilder();
            t_4573.appendSafe("v = ");
            t_4573.appendDate(d__936);
            String actual_1195 = t_4573.getAccumulated().toString();
            boolean t_4579 = actual_1195.equals("v = '2024-06-15'");
            Supplier<String> fn__4572 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_1195 + ")";
            test_93.assert_(t_4579, fn__4572);
        } finally {
            test_93.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__1198() {
        Test test_94 = new Test();
        try {
            String name__938 = "Someone";
            SqlBuilder t_4541 = new SqlBuilder();
            t_4541.appendSafe("where p.last_name = ");
            t_4541.appendString("Someone");
            SqlFragment condition__939 = t_4541.getAccumulated();
            SqlBuilder t_4545 = new SqlBuilder();
            t_4545.appendSafe("select p.id from person p ");
            t_4545.appendFragment(condition__939);
            String actual_1200 = t_4545.getAccumulated().toString();
            boolean t_4551 = actual_1200.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__4540 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1200 + ")";
            test_94.assert_(t_4551, fn__4540);
            SqlBuilder t_4553 = new SqlBuilder();
            t_4553.appendSafe("select p.id from person p ");
            t_4553.appendPart(condition__939.toSource());
            String actual_1203 = t_4553.getAccumulated().toString();
            boolean t_4560 = actual_1203.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__4539 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1203 + ")";
            test_94.assert_(t_4560, fn__4539);
            List<SqlPart> parts__940 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_4564 = new SqlBuilder();
            t_4564.appendSafe("select ");
            t_4564.appendPartList(parts__940);
            String actual_1206 = t_4564.getAccumulated().toString();
            boolean t_4570 = actual_1206.equals("select 'a''b', 3");
            Supplier<String> fn__4538 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_1206 + ")";
            test_94.assert_(t_4570, fn__4538);
        } finally {
            test_94.softFailToHard();
        }
    }
}
