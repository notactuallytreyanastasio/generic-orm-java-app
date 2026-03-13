package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_5782;
        StringBuilder builder__863 = new StringBuilder();
        int i__864 = 0;
        while (true) {
            t_5782 = this.parts.size();
            if (i__864 >= t_5782) {
                break;
            }
            Core.listGet(this.parts, i__864).formatTo(builder__863);
            i__864 = i__864 + 1;
        }
        return builder__863.toString();
    }
    public SqlFragment(List<SqlPart> parts__866) {
        this.parts = parts__866;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
