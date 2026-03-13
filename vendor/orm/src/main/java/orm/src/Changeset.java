package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__419);
    Changeset validateRequired(List<SafeIdentifier> fields__422);
    Changeset validateLength(SafeIdentifier field__425, int min__426, int max__427);
    Changeset validateInt(SafeIdentifier field__430);
    Changeset validateInt64(SafeIdentifier field__433);
    Changeset validateFloat(SafeIdentifier field__436);
    Changeset validateBool(SafeIdentifier field__439);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__444);
}
