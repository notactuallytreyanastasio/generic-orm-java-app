package orm.src;
/**
 * `SqlSource` represents known-safe SQL source code that doesn't need escaped.
 */
public final class SqlSource implements SqlPart {
    public final String source;
    public void formatTo(StringBuilder builder__872) {
        builder__872.append(this.source);
    }
    public SqlSource(String source__875) {
        this.source = source__875;
    }
    public String getSource() {
        return this.source;
    }
}
