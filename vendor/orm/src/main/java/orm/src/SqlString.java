package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__1066) {
        builder__1066.append("'");
        IntConsumer fn__7207 = c__1068 -> {
            if (c__1068 == 39) {
                builder__1066.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1066, c__1068);
            }
        };
        Core.stringForEach(this.value, fn__7207);
        builder__1066.append("'");
    }
    public SqlString(String value__1070) {
        this.value = value__1070;
    }
    public String getValue() {
        return this.value;
    }
}
