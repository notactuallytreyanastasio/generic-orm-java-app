package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__565);
    Changeset validateRequired(List<SafeIdentifier> fields__568);
    Changeset validateLength(SafeIdentifier field__571, int min__572, int max__573);
    Changeset validateInt(SafeIdentifier field__576);
    Changeset validateInt64(SafeIdentifier field__579);
    Changeset validateFloat(SafeIdentifier field__582);
    Changeset validateBool(SafeIdentifier field__585);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__590);
}
