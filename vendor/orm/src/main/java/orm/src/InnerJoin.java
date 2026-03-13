package orm.src;
public final class InnerJoin implements JoinType {
    public String keyword() {
        return "INNER JOIN";
    }
    public InnerJoin() {
    }
}
