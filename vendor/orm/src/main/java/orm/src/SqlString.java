package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__1518) {
        builder__1518.append("'");
        IntConsumer fn__11438 = c__1520 -> {
            if (c__1520 == 39) {
                builder__1518.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1518, c__1520);
            }
        };
        Core.stringForEach(this.value, fn__11438);
        builder__1518.append("'");
    }
    public SqlString(String value__1522) {
        this.value = value__1522;
    }
    public String getValue() {
        return this.value;
    }
}
