package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__884) {
        builder__884.append("'");
        String t_5763 = this.value.toString();
        IntConsumer fn__5761 = c__886 -> {
            if (c__886 == 39) {
                builder__884.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__884, c__886);
            }
        };
        Core.stringForEach(t_5763, fn__5761);
        builder__884.append("'");
    }
    public SqlDate(LocalDate value__888) {
        this.value = value__888;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
