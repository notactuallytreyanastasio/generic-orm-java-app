package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__1040) {
        builder__1040.append("'");
        String t_7195 = this.value.toString();
        IntConsumer fn__7193 = c__1042 -> {
            if (c__1042 == 39) {
                builder__1040.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1040, c__1042);
            }
        };
        Core.stringForEach(t_7195, fn__7193);
        builder__1040.append("'");
    }
    public SqlDate(LocalDate value__1044) {
        this.value = value__1044;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
