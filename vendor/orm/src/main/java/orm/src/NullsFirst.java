package orm.src;
public final class NullsFirst implements NullsPosition {
    public String keyword() {
        return " NULLS FIRST";
    }
    public NullsFirst() {
    }
}
