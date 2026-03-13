package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__891) {
        boolean t_3426;
        boolean t_3427;
        String s__893 = Core.float64ToString(this.value);
        if (s__893.equals("NaN")) {
            t_3427 = true;
        } else {
            if (s__893.equals("Infinity")) {
                t_3426 = true;
            } else {
                t_3426 = s__893.equals("-Infinity");
            }
            t_3427 = t_3426;
        }
        if (t_3427) {
            builder__891.append("NULL");
        } else {
            builder__891.append(s__893);
        }
    }
    public SqlFloat64(double value__895) {
        this.value = value__895;
    }
    public double getValue() {
        return this.value;
    }
}
