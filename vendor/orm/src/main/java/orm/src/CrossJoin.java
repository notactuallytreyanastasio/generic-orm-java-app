package orm.src;
public final class CrossJoin implements JoinType {
    public String keyword() {
        return "CROSS JOIN";
    }
    public CrossJoin() {
    }
}
