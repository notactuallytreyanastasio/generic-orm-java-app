package orm.src;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public FieldDef field(String name__922) {
        FieldDef return__346;
        fn__923: {
            List<FieldDef> this__4373 = this.fields;
            int n__4374 = this__4373.size();
            int i__4375 = 0;
            while (i__4375 < n__4374) {
                FieldDef el__4376 = Core.listGet(this__4373, i__4375);
                i__4375 = i__4375 + 1;
                FieldDef f__924 = el__4376;
                if (f__924.getName().getSqlValue().equals(name__922)) {
                    return__346 = f__924;
                    break fn__923;
                }
            }
            throw Core.bubble();
        }
        return return__346;
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
    public TableDef(SafeIdentifier tableName__926, List<FieldDef> fields__927) {
        this.tableName = tableName__926;
        this.fields = fields__927;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
}
