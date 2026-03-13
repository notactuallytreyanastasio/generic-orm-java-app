package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__1402) {
        boolean t_6270;
        boolean t_6271;
        String s__1404 = Core.float64ToString(this.value);
        if (s__1404.equals("NaN")) {
            t_6271 = true;
        } else {
            if (s__1404.equals("Infinity")) {
                t_6270 = true;
            } else {
                t_6270 = s__1404.equals("-Infinity");
            }
            t_6271 = t_6270;
        }
        if (t_6271) {
            builder__1402.append("NULL");
        } else {
            builder__1402.append(s__1404);
        }
    }
    public SqlFloat64(double value__1406) {
        this.value = value__1406;
    }
    public double getValue() {
        return this.value;
    }
}
