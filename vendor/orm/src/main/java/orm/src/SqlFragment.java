package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_17363;
        StringBuilder builder__2056 = new StringBuilder();
        int i__2057 = 0;
        while (true) {
            t_17363 = this.parts.size();
            if (i__2057 >= t_17363) {
                break;
            }
            Core.listGet(this.parts, i__2057).formatTo(builder__2056);
            i__2057 = i__2057 + 1;
        }
        return builder__2056.toString();
    }
    public SqlFragment(List<SqlPart> parts__2059) {
        this.parts = parts__2059;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
