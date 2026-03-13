package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.function.Consumer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.IntConsumer;
import java.util.function.DoubleConsumer;
public final class SqlBuilder {
    final List<SqlPart> buffer;
    public void appendSafe(String sqlSource__793) {
        SqlSource t_5758 = new SqlSource(sqlSource__793);
        Core.listAdd(this.buffer, t_5758);
    }
    public void appendFragment(SqlFragment fragment__796) {
        List<SqlPart> t_5756 = fragment__796.getParts();
        Core.listAddAll(this.buffer, t_5756);
    }
    public void appendPart(SqlPart part__799) {
        Core.listAdd(this.buffer, part__799);
    }
    public void appendPartList(List<SqlPart> values__802) {
        Consumer<SqlPart> fn__5752 = x__804 -> {
            this.appendPart(x__804);
        };
        this.appendList(values__802, fn__5752);
    }
    public void appendBoolean(boolean value__806) {
        SqlBoolean t_5749 = new SqlBoolean(value__806);
        Core.listAdd(this.buffer, t_5749);
    }
    public void appendBooleanList(List<Boolean> values__809) {
        Consumer<Boolean> fn__5746 = x__811 -> {
            this.appendBoolean(x__811);
        };
        this.appendList(values__809, fn__5746);
    }
    public void appendDate(LocalDate value__813) {
        SqlDate t_5743 = new SqlDate(value__813);
        Core.listAdd(this.buffer, t_5743);
    }
    public void appendDateList(List<LocalDate> values__816) {
        Consumer<LocalDate> fn__5740 = x__818 -> {
            this.appendDate(x__818);
        };
        this.appendList(values__816, fn__5740);
    }
    public void appendFloat64(double value__820) {
        SqlFloat64 t_5737 = new SqlFloat64(value__820);
        Core.listAdd(this.buffer, t_5737);
    }
    public void appendFloat64List(List<Double> values__823) {
        DoubleConsumer fn__5734 = x__825 -> {
            this.appendFloat64(x__825);
        };
        this.appendList(values__823, fn__5734 :: accept);
    }
    public void appendInt32(int value__827) {
        SqlInt32 t_5731 = new SqlInt32(value__827);
        Core.listAdd(this.buffer, t_5731);
    }
    public void appendInt32List(List<Integer> values__830) {
        IntConsumer fn__5728 = x__832 -> {
            this.appendInt32(x__832);
        };
        this.appendList(values__830, fn__5728 :: accept);
    }
    public void appendInt64(long value__834) {
        SqlInt64 t_5725 = new SqlInt64(value__834);
        Core.listAdd(this.buffer, t_5725);
    }
    public void appendInt64List(List<Long> values__837) {
        Consumer<Long> fn__5722 = x__839 -> {
            this.appendInt64(x__839);
        };
        this.appendList(values__837, fn__5722);
    }
    public void appendString(String value__841) {
        SqlString t_5719 = new SqlString(value__841);
        Core.listAdd(this.buffer, t_5719);
    }
    public void appendStringList(List<String> values__844) {
        Consumer<String> fn__5716 = x__846 -> {
            this.appendString(x__846);
        };
        this.appendList(values__844, fn__5716);
    }
    <T__163> void appendList(List<T__163> values__848, Consumer<T__163> appendValue__849) {
        int t_5711;
        T__163 t_5713;
        int i__851 = 0;
        while (true) {
            t_5711 = values__848.size();
            if (i__851 >= t_5711) {
                break;
            }
            if (i__851 > 0) {
                this.appendSafe(", ");
            }
            t_5713 = Core.listGet(values__848, i__851);
            appendValue__849.accept(t_5713);
            i__851 = i__851 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_5708 = new ArrayList<>();
        this.buffer = t_5708;
    }
}
