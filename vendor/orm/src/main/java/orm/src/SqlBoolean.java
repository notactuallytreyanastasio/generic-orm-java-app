package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__1389) {
        String t_6281;
        if (this.value) {
            t_6281 = "TRUE";
        } else {
            t_6281 = "FALSE";
        }
        builder__1389.append(t_6281);
    }
    public SqlBoolean(boolean value__1392) {
        this.value = value__1392;
    }
    public boolean isValue() {
        return this.value;
    }
}
