package orm.src;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public FieldDef field(String name__1277) {
        FieldDef return__451;
        fn__1278: {
            List<FieldDef> this__6482 = this.fields;
            int n__6483 = this__6482.size();
            int i__6484 = 0;
            while (i__6484 < n__6483) {
                FieldDef el__6485 = Core.listGet(this__6482, i__6484);
                i__6484 = i__6484 + 1;
                FieldDef f__1279 = el__6485;
                if (f__1279.getName().getSqlValue().equals(name__1277)) {
                    return__451 = f__1279;
                    break fn__1278;
                }
            }
            throw Core.bubble();
        }
        return return__451;
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
        public TableDef build() {
            return new TableDef(tableName, fields);
        }
    }
    public TableDef(SafeIdentifier tableName__1281, List<FieldDef> fields__1282) {
        this.tableName = tableName__1281;
        this.fields = fields__1282;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
}
