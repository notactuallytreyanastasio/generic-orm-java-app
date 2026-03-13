package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__1499) {
        boolean t_6581;
        boolean t_6582;
        String s__1501 = Core.float64ToString(this.value);
        if (s__1501.equals("NaN")) {
            t_6582 = true;
        } else {
            if (s__1501.equals("Infinity")) {
                t_6581 = true;
            } else {
                t_6581 = s__1501.equals("-Infinity");
            }
            t_6582 = t_6581;
        }
        if (t_6582) {
            builder__1499.append("NULL");
        } else {
            builder__1499.append(s__1501);
        }
    }
    public SqlFloat64(double value__1503) {
        this.value = value__1503;
    }
    public double getValue() {
        return this.value;
    }
}
