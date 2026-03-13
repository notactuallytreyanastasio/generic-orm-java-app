package orm.src;
public final class ForShare implements LockMode {
    public String keyword() {
        return " FOR SHARE";
    }
    public ForShare() {
    }
}
