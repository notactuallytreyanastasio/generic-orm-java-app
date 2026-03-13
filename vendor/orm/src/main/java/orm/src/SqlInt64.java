package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__2097) {
        String t_17351 = Long.toString(this.value);
        builder__2097.append(t_17351);
    }
    public SqlInt64(long value__2100) {
        this.value = value__2100;
    }
    public long getValue() {
        return this.value;
    }
}
