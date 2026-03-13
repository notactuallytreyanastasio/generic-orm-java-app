package orm.src;
public final class RightJoin implements JoinType {
    public String keyword() {
        return "RIGHT JOIN";
    }
    public RightJoin() {
    }
}
