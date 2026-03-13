package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__2091) {
        String t_17353 = Integer.toString(this.value);
        builder__2091.append(t_17353);
    }
    public SqlInt32(int value__2094) {
        this.value = value__2094;
    }
    public int getValue() {
        return this.value;
    }
}
