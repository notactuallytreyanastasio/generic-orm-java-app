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
    public void appendSafe(String sqlSource__1986) {
        SqlSource t_17339 = new SqlSource(sqlSource__1986);
        Core.listAdd(this.buffer, t_17339);
    }
    public void appendFragment(SqlFragment fragment__1989) {
        List<SqlPart> t_17337 = fragment__1989.getParts();
        Core.listAddAll(this.buffer, t_17337);
    }
    public void appendPart(SqlPart part__1992) {
        Core.listAdd(this.buffer, part__1992);
    }
    public void appendPartList(List<SqlPart> values__1995) {
        Consumer<SqlPart> fn__17333 = x__1997 -> {
            this.appendPart(x__1997);
        };
        this.appendList(values__1995, fn__17333);
    }
    public void appendBoolean(boolean value__1999) {
        SqlBoolean t_17330 = new SqlBoolean(value__1999);
        Core.listAdd(this.buffer, t_17330);
    }
    public void appendBooleanList(List<Boolean> values__2002) {
        Consumer<Boolean> fn__17327 = x__2004 -> {
            this.appendBoolean(x__2004);
        };
        this.appendList(values__2002, fn__17327);
    }
    public void appendDate(LocalDate value__2006) {
        SqlDate t_17324 = new SqlDate(value__2006);
        Core.listAdd(this.buffer, t_17324);
    }
    public void appendDateList(List<LocalDate> values__2009) {
        Consumer<LocalDate> fn__17321 = x__2011 -> {
            this.appendDate(x__2011);
        };
        this.appendList(values__2009, fn__17321);
    }
    public void appendFloat64(double value__2013) {
        SqlFloat64 t_17318 = new SqlFloat64(value__2013);
        Core.listAdd(this.buffer, t_17318);
    }
    public void appendFloat64List(List<Double> values__2016) {
        DoubleConsumer fn__17315 = x__2018 -> {
            this.appendFloat64(x__2018);
        };
        this.appendList(values__2016, fn__17315 :: accept);
    }
    public void appendInt32(int value__2020) {
        SqlInt32 t_17312 = new SqlInt32(value__2020);
        Core.listAdd(this.buffer, t_17312);
    }
    public void appendInt32List(List<Integer> values__2023) {
        IntConsumer fn__17309 = x__2025 -> {
            this.appendInt32(x__2025);
        };
        this.appendList(values__2023, fn__17309 :: accept);
    }
    public void appendInt64(long value__2027) {
        SqlInt64 t_17306 = new SqlInt64(value__2027);
        Core.listAdd(this.buffer, t_17306);
    }
    public void appendInt64List(List<Long> values__2030) {
        Consumer<Long> fn__17303 = x__2032 -> {
            this.appendInt64(x__2032);
        };
        this.appendList(values__2030, fn__17303);
    }
    public void appendString(String value__2034) {
        SqlString t_17300 = new SqlString(value__2034);
        Core.listAdd(this.buffer, t_17300);
    }
    public void appendStringList(List<String> values__2037) {
        Consumer<String> fn__17297 = x__2039 -> {
            this.appendString(x__2039);
        };
        this.appendList(values__2037, fn__17297);
    }
    <T__394> void appendList(List<T__394> values__2041, Consumer<T__394> appendValue__2042) {
        int t_17292;
        T__394 t_17294;
        int i__2044 = 0;
        while (true) {
            t_17292 = values__2041.size();
            if (i__2044 >= t_17292) {
                break;
            }
            if (i__2044 > 0) {
                this.appendSafe(", ");
            }
            t_17294 = Core.listGet(values__2041, i__2044);
            appendValue__2042.accept(t_17294);
            i__2044 = i__2044 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_17289 = new ArrayList<>();
        this.buffer = t_17289;
    }
}
