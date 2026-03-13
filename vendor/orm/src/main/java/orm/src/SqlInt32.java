package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__1506) {
        String t_11435 = Integer.toString(this.value);
        builder__1506.append(t_11435);
    }
    public SqlInt32(int value__1509) {
        this.value = value__1509;
    }
    public int getValue() {
        return this.value;
    }
}
