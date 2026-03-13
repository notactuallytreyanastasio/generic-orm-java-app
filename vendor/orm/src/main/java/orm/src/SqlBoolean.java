package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__2071) {
        String t_9792;
        if (this.value) {
            t_9792 = "TRUE";
        } else {
            t_9792 = "FALSE";
        }
        builder__2071.append(t_9792);
    }
    public SqlBoolean(boolean value__2074) {
        this.value = value__2074;
    }
    public boolean isValue() {
        return this.value;
    }
}
