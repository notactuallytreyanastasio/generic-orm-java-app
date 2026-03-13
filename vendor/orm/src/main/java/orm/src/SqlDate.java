package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__1492) {
        builder__1492.append("'");
        String t_11426 = this.value.toString();
        IntConsumer fn__11424 = c__1494 -> {
            if (c__1494 == 39) {
                builder__1492.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1492, c__1494);
            }
        };
        Core.stringForEach(t_11426, fn__11424);
        builder__1492.append("'");
    }
    public SqlDate(LocalDate value__1496) {
        this.value = value__1496;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
