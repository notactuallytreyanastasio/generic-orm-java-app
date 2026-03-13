package orm.src;
import temper.core.Nullable;
public final class FieldDef {
    public final SafeIdentifier name;
    public final FieldType fieldType;
    public final boolean nullable;
    public final @Nullable SqlPart defaultValue;
    public final boolean virtual;
    public static final class Builder {
        SafeIdentifier name;
        public Builder name(SafeIdentifier name) {
            this.name = name;
            return this;
        }
        FieldType fieldType;
        public Builder fieldType(FieldType fieldType) {
            this.fieldType = fieldType;
            return this;
        }
        boolean nullable;
        boolean nullable__set;
        public Builder nullable(boolean nullable) {
            nullable__set = true;
            this.nullable = nullable;
            return this;
        }
        @Nullable SqlPart defaultValue;
        boolean defaultValue__set;
        public Builder defaultValue(@Nullable SqlPart defaultValue) {
            defaultValue__set = true;
            this.defaultValue = defaultValue;
            return this;
        }
        boolean virtual;
        boolean virtual__set;
        public Builder virtual(boolean virtual) {
            virtual__set = true;
            this.virtual = virtual;
            return this;
        }
        public FieldDef build() {
            if (!nullable__set || !defaultValue__set || !virtual__set || name == null || fieldType == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!nullable__set) {
                    _message.append(" nullable");
                }
                if (!defaultValue__set) {
                    _message.append(" defaultValue");
                }
                if (!virtual__set) {
                    _message.append(" virtual");
                }
                if (name == null) {
                    _message.append(" name");
                }
                if (fieldType == null) {
                    _message.append(" fieldType");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new FieldDef(name, fieldType, nullable, defaultValue, virtual);
        }
    }
    public FieldDef(SafeIdentifier name__1925, FieldType fieldType__1926, boolean nullable__1927, @Nullable SqlPart defaultValue__1928, boolean virtual__1929) {
        this.name = name__1925;
        this.fieldType = fieldType__1926;
        this.nullable = nullable__1927;
        this.defaultValue = defaultValue__1928;
        this.virtual = virtual__1929;
    }
    public SafeIdentifier getName() {
        return this.name;
    }
    public FieldType getFieldType() {
        return this.fieldType;
    }
    public boolean isNullable() {
        return this.nullable;
    }
    public @Nullable SqlPart getDefaultValue() {
        return this.defaultValue;
    }
    public boolean isVirtual() {
        return this.virtual;
    }
}
