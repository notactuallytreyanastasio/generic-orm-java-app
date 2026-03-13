package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__1415) {
        String t_10907 = Long.toString(this.value);
        builder__1415.append(t_10907);
    }
    public SqlInt64(long value__1418) {
        this.value = value__1418;
    }
    public long getValue() {
        return this.value;
    }
}
