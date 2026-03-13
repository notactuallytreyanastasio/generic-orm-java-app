package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__1409) {
        String t_10909 = Integer.toString(this.value);
        builder__1409.append(t_10909);
    }
    public SqlInt32(int value__1412) {
        this.value = value__1412;
    }
    public int getValue() {
        return this.value;
    }
}
