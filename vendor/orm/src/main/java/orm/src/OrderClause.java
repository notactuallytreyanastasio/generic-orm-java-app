package orm.src;
import temper.core.Nullable;
public final class OrderClause {
    public final SafeIdentifier field;
    public final boolean ascending;
    public final @Nullable NullsPosition nullsPos;
    public static final class Builder {
        SafeIdentifier field;
        public Builder field(SafeIdentifier field) {
            this.field = field;
            return this;
        }
        boolean ascending;
        boolean ascending__set;
        public Builder ascending(boolean ascending) {
            ascending__set = true;
            this.ascending = ascending;
            return this;
        }
        @Nullable NullsPosition nullsPos;
        boolean nullsPos__set;
        public Builder nullsPos(@Nullable NullsPosition nullsPos) {
            nullsPos__set = true;
            this.nullsPos = nullsPos;
            return this;
        }
        public OrderClause build() {
            if (!ascending__set || !nullsPos__set || field == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!ascending__set) {
                    _message.append(" ascending");
                }
                if (!nullsPos__set) {
                    _message.append(" nullsPos");
                }
                if (field == null) {
                    _message.append(" field");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new OrderClause(field, ascending, nullsPos);
        }
    }
    public OrderClause(SafeIdentifier field__1341, boolean ascending__1342, @Nullable NullsPosition nullsPos__1343) {
        this.field = field__1341;
        this.ascending = ascending__1342;
        this.nullsPos = nullsPos__1343;
    }
    public SafeIdentifier getField() {
        return this.field;
    }
    public boolean isAscending() {
        return this.ascending;
    }
    public @Nullable NullsPosition getNullsPos() {
        return this.nullsPos;
    }
}
