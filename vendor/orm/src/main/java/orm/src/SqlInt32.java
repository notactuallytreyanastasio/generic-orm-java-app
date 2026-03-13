package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__898) {
        String t_5772 = Integer.toString(this.value);
        builder__898.append(t_5772);
    }
    public SqlInt32(int value__901) {
        this.value = value__901;
    }
    public int getValue() {
        return this.value;
    }
}
