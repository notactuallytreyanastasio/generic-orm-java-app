package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__910) {
        builder__910.append("'");
        IntConsumer fn__5775 = c__912 -> {
            if (c__912 == 39) {
                builder__910.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__910, c__912);
            }
        };
        Core.stringForEach(this.value, fn__5775);
        builder__910.append("'");
    }
    public SqlString(String value__914) {
        this.value = value__914;
    }
    public String getValue() {
        return this.value;
    }
}
