package orm.src;
public final class LeftJoin implements JoinType {
    public String keyword() {
        return "LEFT JOIN";
    }
    public LeftJoin() {
    }
}
