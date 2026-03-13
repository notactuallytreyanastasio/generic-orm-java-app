package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_7214;
        StringBuilder builder__1019 = new StringBuilder();
        int i__1020 = 0;
        while (true) {
            t_7214 = this.parts.size();
            if (i__1020 >= t_7214) {
                break;
            }
            Core.listGet(this.parts, i__1020).formatTo(builder__1019);
            i__1020 = i__1020 + 1;
        }
        return builder__1019.toString();
    }
    public SqlFragment(List<SqlPart> parts__1022) {
        this.parts = parts__1022;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
