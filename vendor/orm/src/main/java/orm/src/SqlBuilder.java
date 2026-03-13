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
    public void appendSafe(String sqlSource__1401) {
        SqlSource t_11421 = new SqlSource(sqlSource__1401);
        Core.listAdd(this.buffer, t_11421);
    }
    public void appendFragment(SqlFragment fragment__1404) {
        List<SqlPart> t_11419 = fragment__1404.getParts();
        Core.listAddAll(this.buffer, t_11419);
    }
    public void appendPart(SqlPart part__1407) {
        Core.listAdd(this.buffer, part__1407);
    }
    public void appendPartList(List<SqlPart> values__1410) {
        Consumer<SqlPart> fn__11415 = x__1412 -> {
            this.appendPart(x__1412);
        };
        this.appendList(values__1410, fn__11415);
    }
    public void appendBoolean(boolean value__1414) {
        SqlBoolean t_11412 = new SqlBoolean(value__1414);
        Core.listAdd(this.buffer, t_11412);
    }
    public void appendBooleanList(List<Boolean> values__1417) {
        Consumer<Boolean> fn__11409 = x__1419 -> {
            this.appendBoolean(x__1419);
        };
        this.appendList(values__1417, fn__11409);
    }
    public void appendDate(LocalDate value__1421) {
        SqlDate t_11406 = new SqlDate(value__1421);
        Core.listAdd(this.buffer, t_11406);
    }
    public void appendDateList(List<LocalDate> values__1424) {
        Consumer<LocalDate> fn__11403 = x__1426 -> {
            this.appendDate(x__1426);
        };
        this.appendList(values__1424, fn__11403);
    }
    public void appendFloat64(double value__1428) {
        SqlFloat64 t_11400 = new SqlFloat64(value__1428);
        Core.listAdd(this.buffer, t_11400);
    }
    public void appendFloat64List(List<Double> values__1431) {
        DoubleConsumer fn__11397 = x__1433 -> {
            this.appendFloat64(x__1433);
        };
        this.appendList(values__1431, fn__11397 :: accept);
    }
    public void appendInt32(int value__1435) {
        SqlInt32 t_11394 = new SqlInt32(value__1435);
        Core.listAdd(this.buffer, t_11394);
    }
    public void appendInt32List(List<Integer> values__1438) {
        IntConsumer fn__11391 = x__1440 -> {
            this.appendInt32(x__1440);
        };
        this.appendList(values__1438, fn__11391 :: accept);
    }
    public void appendInt64(long value__1442) {
        SqlInt64 t_11388 = new SqlInt64(value__1442);
        Core.listAdd(this.buffer, t_11388);
    }
    public void appendInt64List(List<Long> values__1445) {
        Consumer<Long> fn__11385 = x__1447 -> {
            this.appendInt64(x__1447);
        };
        this.appendList(values__1445, fn__11385);
    }
    public void appendString(String value__1449) {
        SqlString t_11382 = new SqlString(value__1449);
        Core.listAdd(this.buffer, t_11382);
    }
    public void appendStringList(List<String> values__1452) {
        Consumer<String> fn__11379 = x__1454 -> {
            this.appendString(x__1454);
        };
        this.appendList(values__1452, fn__11379);
    }
    <T__277> void appendList(List<T__277> values__1456, Consumer<T__277> appendValue__1457) {
        int t_11374;
        T__277 t_11376;
        int i__1459 = 0;
        while (true) {
            t_11374 = values__1456.size();
            if (i__1459 >= t_11374) {
                break;
            }
            if (i__1459 > 0) {
                this.appendSafe(", ");
            }
            t_11376 = Core.listGet(values__1456, i__1459);
            appendValue__1457.accept(t_11376);
            i__1459 = i__1459 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_11371 = new ArrayList<>();
        this.buffer = t_11371;
    }
}
