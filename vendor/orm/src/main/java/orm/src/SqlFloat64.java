package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__1047) {
        boolean t_4207;
        boolean t_4208;
        String s__1049 = Core.float64ToString(this.value);
        if (s__1049.equals("NaN")) {
            t_4208 = true;
        } else {
            if (s__1049.equals("Infinity")) {
                t_4207 = true;
            } else {
                t_4207 = s__1049.equals("-Infinity");
            }
            t_4208 = t_4207;
        }
        if (t_4208) {
            builder__1047.append("NULL");
        } else {
            builder__1047.append(s__1049);
        }
    }
    public SqlFloat64(double value__1051) {
        this.value = value__1051;
    }
    public double getValue() {
        return this.value;
    }
}
