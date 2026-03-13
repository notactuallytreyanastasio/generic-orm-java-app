package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__904) {
        String t_5770 = Long.toString(this.value);
        builder__904.append(t_5770);
    }
    public SqlInt64(long value__907) {
        this.value = value__907;
    }
    public long getValue() {
        return this.value;
    }
}
