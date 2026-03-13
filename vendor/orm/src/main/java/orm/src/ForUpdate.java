package orm.src;
public final class ForUpdate implements LockMode {
    public String keyword() {
        return " FOR UPDATE";
    }
    public ForUpdate() {
    }
}
