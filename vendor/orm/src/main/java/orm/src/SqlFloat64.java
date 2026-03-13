package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__2084) {
        boolean t_9781;
        boolean t_9782;
        String s__2086 = Core.float64ToString(this.value);
        if (s__2086.equals("NaN")) {
            t_9782 = true;
        } else {
            if (s__2086.equals("Infinity")) {
                t_9781 = true;
            } else {
                t_9781 = s__2086.equals("-Infinity");
            }
            t_9782 = t_9781;
        }
        if (t_9782) {
            builder__2084.append("NULL");
        } else {
            builder__2084.append(s__2086);
        }
    }
    public SqlFloat64(double value__2088) {
        this.value = value__2088;
    }
    public double getValue() {
        return this.value;
    }
}
