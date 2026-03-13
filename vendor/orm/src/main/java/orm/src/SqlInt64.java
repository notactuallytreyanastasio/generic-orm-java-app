package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__1512) {
        String t_11433 = Long.toString(this.value);
        builder__1512.append(t_11433);
    }
    public SqlInt64(long value__1515) {
        this.value = value__1515;
    }
    public long getValue() {
        return this.value;
    }
}
