package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__878) {
        String t_3437;
        if (this.value) {
            t_3437 = "TRUE";
        } else {
            t_3437 = "FALSE";
        }
        builder__878.append(t_3437);
    }
    public SqlBoolean(boolean value__881) {
        this.value = value__881;
    }
    public boolean isValue() {
        return this.value;
    }
}
