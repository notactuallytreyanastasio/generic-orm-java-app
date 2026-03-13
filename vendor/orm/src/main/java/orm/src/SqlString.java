package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__1421) {
        builder__1421.append("'");
        IntConsumer fn__10912 = c__1423 -> {
            if (c__1423 == 39) {
                builder__1421.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1421, c__1423);
            }
        };
        Core.stringForEach(this.value, fn__10912);
        builder__1421.append("'");
    }
    public SqlString(String value__1425) {
        this.value = value__1425;
    }
    public String getValue() {
        return this.value;
    }
}
