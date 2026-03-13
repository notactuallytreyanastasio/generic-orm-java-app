package orm.src;
public final class FullJoin implements JoinType {
    public String keyword() {
        return "FULL OUTER JOIN";
    }
    public FullJoin() {
    }
}
