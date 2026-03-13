package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_10919;
        StringBuilder builder__1374 = new StringBuilder();
        int i__1375 = 0;
        while (true) {
            t_10919 = this.parts.size();
            if (i__1375 >= t_10919) {
                break;
            }
            Core.listGet(this.parts, i__1375).formatTo(builder__1374);
            i__1375 = i__1375 + 1;
        }
        return builder__1374.toString();
    }
    public SqlFragment(List<SqlPart> parts__1377) {
        this.parts = parts__1377;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
