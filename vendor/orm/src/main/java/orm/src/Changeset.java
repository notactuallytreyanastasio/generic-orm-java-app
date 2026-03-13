package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__363);
    Changeset validateRequired(List<SafeIdentifier> fields__366);
    Changeset validateLength(SafeIdentifier field__369, int min__370, int max__371);
    Changeset validateInt(SafeIdentifier field__374);
    Changeset validateInt64(SafeIdentifier field__377);
    Changeset validateFloat(SafeIdentifier field__380);
    Changeset validateBool(SafeIdentifier field__383);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__388);
}
