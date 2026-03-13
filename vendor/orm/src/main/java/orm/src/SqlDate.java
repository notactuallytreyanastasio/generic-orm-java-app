package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__1395) {
        builder__1395.append("'");
        String t_10900 = this.value.toString();
        IntConsumer fn__10898 = c__1397 -> {
            if (c__1397 == 39) {
                builder__1395.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1395, c__1397);
            }
        };
        Core.stringForEach(t_10900, fn__10898);
        builder__1395.append("'");
    }
    public SqlDate(LocalDate value__1399) {
        this.value = value__1399;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
