package orm.src;
/**
 * `SqlSource` represents known-safe SQL source code that doesn't need escaped.
 */
public final class SqlSource implements SqlPart {
    public final String source;
    public void formatTo(StringBuilder builder__2065) {
        builder__2065.append(this.source);
    }
    public SqlSource(String source__2068) {
        this.source = source__2068;
    }
    public String getSource() {
        return this.source;
    }
}
