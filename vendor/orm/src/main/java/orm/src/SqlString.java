package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__2107) {
        builder__2107.append("'");
        IntConsumer fn__17356 = c__2109 -> {
            if (c__2109 == 39) {
                builder__2107.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__2107, c__2109);
            }
        };
        Core.stringForEach(this.value, fn__17356);
        builder__2107.append("'");
    }
    public SqlString(String value__2111) {
        this.value = value__2111;
    }
    public String getValue() {
        return this.value;
    }
}
