package orm.src;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public FieldDef field(String name__1374) {
        FieldDef return__492;
        fn__1375: {
            List<FieldDef> this__6801 = this.fields;
            int n__6802 = this__6801.size();
            int i__6803 = 0;
            while (i__6803 < n__6802) {
                FieldDef el__6804 = Core.listGet(this__6801, i__6803);
                i__6803 = i__6803 + 1;
                FieldDef f__1376 = el__6804;
                if (f__1376.getName().getSqlValue().equals(name__1374)) {
                    return__492 = f__1376;
                    break fn__1375;
                }
            }
            throw Core.bubble();
        }
        return return__492;
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
    public TableDef(SafeIdentifier tableName__1378, List<FieldDef> fields__1379) {
        this.tableName = tableName__1378;
        this.fields = fields__1379;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
}
