package orm.src;
import temper.core.Nullable;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public final @Nullable SafeIdentifier primaryKey;
    public FieldDef field(String name__1934) {
        FieldDef return__646;
        fn__1935: {
            List<FieldDef> this__10148 = this.fields;
            int n__10149 = this__10148.size();
            int i__10150 = 0;
            while (i__10150 < n__10149) {
                FieldDef el__10151 = Core.listGet(this__10148, i__10150);
                i__10150 = i__10150 + 1;
                FieldDef f__1936 = el__10151;
                if (f__1936.getName().getSqlValue().equals(name__1934)) {
                    return__646 = f__1936;
                    break fn__1935;
                }
            }
            throw Core.bubble();
        }
        return return__646;
    }
    public String pkName() {
        String return__647;
        fn__1938: {
            @Nullable SafeIdentifier pk__1939 = this.primaryKey;
            if (pk__1939 != null) {
                SafeIdentifier pk_2830 = pk__1939;
                return__647 = pk_2830.getSqlValue();
                break fn__1938;
            }
            return__647 = "id";
        }
        return return__647;
    }
    public static final class Builder {
        SafeIdentifier tableName;
        public Builder tableName(SafeIdentifier tableName) {
            this.tableName = tableName;
            return this;
        }
        List<FieldDef> fields;
        public Builder fields(List<FieldDef> fields) {
            this.fields = fields;
            return this;
        }
        @Nullable SafeIdentifier primaryKey;
        boolean primaryKey__set;
        public Builder primaryKey(@Nullable SafeIdentifier primaryKey) {
            primaryKey__set = true;
            this.primaryKey = primaryKey;
            return this;
        }
        public TableDef build() {
            if (!primaryKey__set || tableName == null || fields == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!primaryKey__set) {
                    _message.append(" primaryKey");
                }
                if (tableName == null) {
                    _message.append(" tableName");
                }
                if (fields == null) {
                    _message.append(" fields");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new TableDef(tableName, fields, primaryKey);
        }
    }
    public TableDef(SafeIdentifier tableName__1941, List<FieldDef> fields__1942, @Nullable SafeIdentifier primaryKey__1943) {
        this.tableName = tableName__1941;
        this.fields = fields__1942;
        this.primaryKey = primaryKey__1943;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
    public @Nullable SafeIdentifier getPrimaryKey() {
        return this.primaryKey;
    }
}
