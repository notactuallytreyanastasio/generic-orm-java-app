package orm.src;
/**
 * `SqlSource` represents known-safe SQL source code that doesn't need escaped.
 */
public final class SqlSource implements SqlPart {
    public final String source;
    public void formatTo(StringBuilder builder__1028) {
        builder__1028.append(this.source);
    }
    public SqlSource(String source__1031) {
        this.source = source__1031;
    }
    public String getSource() {
        return this.source;
    }
}
