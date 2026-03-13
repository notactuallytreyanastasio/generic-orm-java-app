package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__1486) {
        String t_6592;
        if (this.value) {
            t_6592 = "TRUE";
        } else {
            t_6592 = "FALSE";
        }
        builder__1486.append(t_6592);
    }
    public SqlBoolean(boolean value__1489) {
        this.value = value__1489;
    }
    public boolean isValue() {
        return this.value;
    }
}
