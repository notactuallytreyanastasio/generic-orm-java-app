package orm.src;
/**
 * `SqlSource` represents known-safe SQL source code that doesn't need escaped.
 */
public final class SqlSource implements SqlPart {
    public final String source;
    public void formatTo(StringBuilder builder__1480) {
        builder__1480.append(this.source);
    }
    public SqlSource(String source__1483) {
        this.source = source__1483;
    }
    public String getSource() {
        return this.source;
    }
}
