package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__1034) {
        String t_4218;
        if (this.value) {
            t_4218 = "TRUE";
        } else {
            t_4218 = "FALSE";
        }
        builder__1034.append(t_4218);
    }
    public SqlBoolean(boolean value__1037) {
        this.value = value__1037;
    }
    public boolean isValue() {
        return this.value;
    }
}
