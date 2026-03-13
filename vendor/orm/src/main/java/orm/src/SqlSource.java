package orm.src;
/**
 * `SqlSource` represents known-safe SQL source code that doesn't need escaped.
 */
public final class SqlSource implements SqlPart {
    public final String source;
    public void formatTo(StringBuilder builder__1383) {
        builder__1383.append(this.source);
    }
    public SqlSource(String source__1386) {
        this.source = source__1386;
    }
    public String getSource() {
        return this.source;
    }
}
