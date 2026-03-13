package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_11445;
        StringBuilder builder__1471 = new StringBuilder();
        int i__1472 = 0;
        while (true) {
            t_11445 = this.parts.size();
            if (i__1472 >= t_11445) {
                break;
            }
            Core.listGet(this.parts, i__1472).formatTo(builder__1471);
            i__1472 = i__1472 + 1;
        }
        return builder__1471.toString();
    }
    public SqlFragment(List<SqlPart> parts__1474) {
        this.parts = parts__1474;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
