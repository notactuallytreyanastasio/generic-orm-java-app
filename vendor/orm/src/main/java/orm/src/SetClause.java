package orm.src;
public final class SetClause {
    public final SafeIdentifier field;
    public final SqlPart value;
    public static final class Builder {
        SafeIdentifier field;
        public Builder field(SafeIdentifier field) {
            this.field = field;
            return this;
        }
        SqlPart value;
        public Builder value(SqlPart value) {
            this.value = value;
            return this;
        }
        public SetClause build() {
            return new SetClause(field, value);
        }
    }
    public SetClause(SafeIdentifier field__1060, SqlPart value__1061) {
        this.field = field__1060;
        this.value = value__1061;
    }
    public SafeIdentifier getField() {
        return this.field;
    }
    public SqlPart getValue() {
        return this.value;
    }
}
