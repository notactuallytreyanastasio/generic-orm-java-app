package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__1054) {
        String t_7204 = Integer.toString(this.value);
        builder__1054.append(t_7204);
    }
    public SqlInt32(int value__1057) {
        this.value = value__1057;
    }
    public int getValue() {
        return this.value;
    }
}
