package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__1060) {
        String t_7202 = Long.toString(this.value);
        builder__1060.append(t_7202);
    }
    public SqlInt64(long value__1063) {
        this.value = value__1063;
    }
    public long getValue() {
        return this.value;
    }
}
