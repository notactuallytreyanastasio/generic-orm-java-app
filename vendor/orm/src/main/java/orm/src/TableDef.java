package orm.src;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public FieldDef field(String name__766) {
        FieldDef return__290;
        fn__767: {
            List<FieldDef> this__3570 = this.fields;
            int n__3571 = this__3570.size();
            int i__3572 = 0;
            while (i__3572 < n__3571) {
                FieldDef el__3573 = Core.listGet(this__3570, i__3572);
                i__3572 = i__3572 + 1;
                FieldDef f__768 = el__3573;
                if (f__768.getName().getSqlValue().equals(name__766)) {
                    return__290 = f__768;
                    break fn__767;
                }
            }
            throw Core.bubble();
        }
        return return__290;
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
    public TableDef(SafeIdentifier tableName__770, List<FieldDef> fields__771) {
        this.tableName = tableName__770;
        this.fields = fields__771;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
}
