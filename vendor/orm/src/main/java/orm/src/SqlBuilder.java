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
    public void appendSafe(String sqlSource__949) {
        SqlSource t_7190 = new SqlSource(sqlSource__949);
        Core.listAdd(this.buffer, t_7190);
    }
    public void appendFragment(SqlFragment fragment__952) {
        List<SqlPart> t_7188 = fragment__952.getParts();
        Core.listAddAll(this.buffer, t_7188);
    }
    public void appendPart(SqlPart part__955) {
        Core.listAdd(this.buffer, part__955);
    }
    public void appendPartList(List<SqlPart> values__958) {
        Consumer<SqlPart> fn__7184 = x__960 -> {
            this.appendPart(x__960);
        };
        this.appendList(values__958, fn__7184);
    }
    public void appendBoolean(boolean value__962) {
        SqlBoolean t_7181 = new SqlBoolean(value__962);
        Core.listAdd(this.buffer, t_7181);
    }
    public void appendBooleanList(List<Boolean> values__965) {
        Consumer<Boolean> fn__7178 = x__967 -> {
            this.appendBoolean(x__967);
        };
        this.appendList(values__965, fn__7178);
    }
    public void appendDate(LocalDate value__969) {
        SqlDate t_7175 = new SqlDate(value__969);
        Core.listAdd(this.buffer, t_7175);
    }
    public void appendDateList(List<LocalDate> values__972) {
        Consumer<LocalDate> fn__7172 = x__974 -> {
            this.appendDate(x__974);
        };
        this.appendList(values__972, fn__7172);
    }
    public void appendFloat64(double value__976) {
        SqlFloat64 t_7169 = new SqlFloat64(value__976);
        Core.listAdd(this.buffer, t_7169);
    }
    public void appendFloat64List(List<Double> values__979) {
        DoubleConsumer fn__7166 = x__981 -> {
            this.appendFloat64(x__981);
        };
        this.appendList(values__979, fn__7166 :: accept);
    }
    public void appendInt32(int value__983) {
        SqlInt32 t_7163 = new SqlInt32(value__983);
        Core.listAdd(this.buffer, t_7163);
    }
    public void appendInt32List(List<Integer> values__986) {
        IntConsumer fn__7160 = x__988 -> {
            this.appendInt32(x__988);
        };
        this.appendList(values__986, fn__7160 :: accept);
    }
    public void appendInt64(long value__990) {
        SqlInt64 t_7157 = new SqlInt64(value__990);
        Core.listAdd(this.buffer, t_7157);
    }
    public void appendInt64List(List<Long> values__993) {
        Consumer<Long> fn__7154 = x__995 -> {
            this.appendInt64(x__995);
        };
        this.appendList(values__993, fn__7154);
    }
    public void appendString(String value__997) {
        SqlString t_7151 = new SqlString(value__997);
        Core.listAdd(this.buffer, t_7151);
    }
    public void appendStringList(List<String> values__1000) {
        Consumer<String> fn__7148 = x__1002 -> {
            this.appendString(x__1002);
        };
        this.appendList(values__1000, fn__7148);
    }
    <T__198> void appendList(List<T__198> values__1004, Consumer<T__198> appendValue__1005) {
        int t_7143;
        T__198 t_7145;
        int i__1007 = 0;
        while (true) {
            t_7143 = values__1004.size();
            if (i__1007 >= t_7143) {
                break;
            }
            if (i__1007 > 0) {
                this.appendSafe(", ");
            }
            t_7145 = Core.listGet(values__1004, i__1007);
            appendValue__1005.accept(t_7145);
            i__1007 = i__1007 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_7140 = new ArrayList<>();
        this.buffer = t_7140;
    }
}
