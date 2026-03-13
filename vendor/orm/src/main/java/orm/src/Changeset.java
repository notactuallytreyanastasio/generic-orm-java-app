package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__524);
    Changeset validateRequired(List<SafeIdentifier> fields__527);
    Changeset validateLength(SafeIdentifier field__530, int min__531, int max__532);
    Changeset validateInt(SafeIdentifier field__535);
    Changeset validateInt64(SafeIdentifier field__538);
    Changeset validateFloat(SafeIdentifier field__541);
    Changeset validateBool(SafeIdentifier field__544);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__549);
}
