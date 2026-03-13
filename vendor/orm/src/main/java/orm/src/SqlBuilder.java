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
    public void appendSafe(String sqlSource__1304) {
        SqlSource t_10895 = new SqlSource(sqlSource__1304);
        Core.listAdd(this.buffer, t_10895);
    }
    public void appendFragment(SqlFragment fragment__1307) {
        List<SqlPart> t_10893 = fragment__1307.getParts();
        Core.listAddAll(this.buffer, t_10893);
    }
    public void appendPart(SqlPart part__1310) {
        Core.listAdd(this.buffer, part__1310);
    }
    public void appendPartList(List<SqlPart> values__1313) {
        Consumer<SqlPart> fn__10889 = x__1315 -> {
            this.appendPart(x__1315);
        };
        this.appendList(values__1313, fn__10889);
    }
    public void appendBoolean(boolean value__1317) {
        SqlBoolean t_10886 = new SqlBoolean(value__1317);
        Core.listAdd(this.buffer, t_10886);
    }
    public void appendBooleanList(List<Boolean> values__1320) {
        Consumer<Boolean> fn__10883 = x__1322 -> {
            this.appendBoolean(x__1322);
        };
        this.appendList(values__1320, fn__10883);
    }
    public void appendDate(LocalDate value__1324) {
        SqlDate t_10880 = new SqlDate(value__1324);
        Core.listAdd(this.buffer, t_10880);
    }
    public void appendDateList(List<LocalDate> values__1327) {
        Consumer<LocalDate> fn__10877 = x__1329 -> {
            this.appendDate(x__1329);
        };
        this.appendList(values__1327, fn__10877);
    }
    public void appendFloat64(double value__1331) {
        SqlFloat64 t_10874 = new SqlFloat64(value__1331);
        Core.listAdd(this.buffer, t_10874);
    }
    public void appendFloat64List(List<Double> values__1334) {
        DoubleConsumer fn__10871 = x__1336 -> {
            this.appendFloat64(x__1336);
        };
        this.appendList(values__1334, fn__10871 :: accept);
    }
    public void appendInt32(int value__1338) {
        SqlInt32 t_10868 = new SqlInt32(value__1338);
        Core.listAdd(this.buffer, t_10868);
    }
    public void appendInt32List(List<Integer> values__1341) {
        IntConsumer fn__10865 = x__1343 -> {
            this.appendInt32(x__1343);
        };
        this.appendList(values__1341, fn__10865 :: accept);
    }
    public void appendInt64(long value__1345) {
        SqlInt64 t_10862 = new SqlInt64(value__1345);
        Core.listAdd(this.buffer, t_10862);
    }
    public void appendInt64List(List<Long> values__1348) {
        Consumer<Long> fn__10859 = x__1350 -> {
            this.appendInt64(x__1350);
        };
        this.appendList(values__1348, fn__10859);
    }
    public void appendString(String value__1352) {
        SqlString t_10856 = new SqlString(value__1352);
        Core.listAdd(this.buffer, t_10856);
    }
    public void appendStringList(List<String> values__1355) {
        Consumer<String> fn__10853 = x__1357 -> {
            this.appendString(x__1357);
        };
        this.appendList(values__1355, fn__10853);
    }
    <T__259> void appendList(List<T__259> values__1359, Consumer<T__259> appendValue__1360) {
        int t_10848;
        T__259 t_10850;
        int i__1362 = 0;
        while (true) {
            t_10848 = values__1359.size();
            if (i__1362 >= t_10848) {
                break;
            }
            if (i__1362 > 0) {
                this.appendSafe(", ");
            }
            t_10850 = Core.listGet(values__1359, i__1362);
            appendValue__1360.accept(t_10850);
            i__1362 = i__1362 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_10845 = new ArrayList<>();
        this.buffer = t_10845;
    }
}
