package orm.src;
public final class NullsLast implements NullsPosition {
    public String keyword() {
        return " NULLS LAST";
    }
    public NullsLast() {
    }
}
