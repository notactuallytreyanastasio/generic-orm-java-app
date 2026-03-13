package orm.src;
import temper.core.Nullable;
public final class NumberValidationOpts {
    public final @Nullable Double greaterThan;
    public final @Nullable Double lessThan;
    public final @Nullable Double greaterThanOrEqual;
    public final @Nullable Double lessThanOrEqual;
    public final @Nullable Double equalTo;
    public static final class Builder {
        @Nullable Double greaterThan;
        boolean greaterThan__set;
        public Builder greaterThan(@Nullable Double greaterThan) {
            greaterThan__set = true;
            this.greaterThan = greaterThan;
            return this;
        }
        @Nullable Double lessThan;
        boolean lessThan__set;
        public Builder lessThan(@Nullable Double lessThan) {
            lessThan__set = true;
            this.lessThan = lessThan;
            return this;
        }
        @Nullable Double greaterThanOrEqual;
        boolean greaterThanOrEqual__set;
        public Builder greaterThanOrEqual(@Nullable Double greaterThanOrEqual) {
            greaterThanOrEqual__set = true;
            this.greaterThanOrEqual = greaterThanOrEqual;
            return this;
        }
        @Nullable Double lessThanOrEqual;
        boolean lessThanOrEqual__set;
        public Builder lessThanOrEqual(@Nullable Double lessThanOrEqual) {
            lessThanOrEqual__set = true;
            this.lessThanOrEqual = lessThanOrEqual;
            return this;
        }
        @Nullable Double equalTo;
        boolean equalTo__set;
        public Builder equalTo(@Nullable Double equalTo) {
            equalTo__set = true;
            this.equalTo = equalTo;
            return this;
        }
        public NumberValidationOpts build() {
            if (!greaterThan__set || !lessThan__set || !greaterThanOrEqual__set || !lessThanOrEqual__set || !equalTo__set) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!greaterThan__set) {
                    _message.append(" greaterThan");
                }
                if (!lessThan__set) {
                    _message.append(" lessThan");
                }
                if (!greaterThanOrEqual__set) {
                    _message.append(" greaterThanOrEqual");
                }
                if (!lessThanOrEqual__set) {
                    _message.append(" lessThanOrEqual");
                }
                if (!equalTo__set) {
                    _message.append(" equalTo");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new NumberValidationOpts(greaterThan, lessThan, greaterThanOrEqual, lessThanOrEqual, equalTo);
        }
    }
    public NumberValidationOpts(@Nullable Double greaterThan__725, @Nullable Double lessThan__726, @Nullable Double greaterThanOrEqual__727, @Nullable Double lessThanOrEqual__728, @Nullable Double equalTo__729) {
        this.greaterThan = greaterThan__725;
        this.lessThan = lessThan__726;
        this.greaterThanOrEqual = greaterThanOrEqual__727;
        this.lessThanOrEqual = lessThanOrEqual__728;
        this.equalTo = equalTo__729;
    }
    public @Nullable Double getGreaterThan() {
        return this.greaterThan;
    }
    public @Nullable Double getLessThan() {
        return this.lessThan;
    }
    public @Nullable Double getGreaterThanOrEqual() {
        return this.greaterThanOrEqual;
    }
    public @Nullable Double getLessThanOrEqual() {
        return this.lessThanOrEqual;
    }
    public @Nullable Double getEqualTo() {
        return this.equalTo;
    }
}
