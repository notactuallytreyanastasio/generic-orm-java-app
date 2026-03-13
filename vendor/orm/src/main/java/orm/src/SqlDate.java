package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__2077) {
        builder__2077.append("'");
        String t_17344 = this.value.toString();
        IntConsumer fn__17342 = c__2079 -> {
            if (c__2079 == 39) {
                builder__2077.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__2077, c__2079);
            }
        };
        Core.stringForEach(t_17344, fn__17342);
        builder__2077.append("'");
    }
    public SqlDate(LocalDate value__2081) {
        this.value = value__2081;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
