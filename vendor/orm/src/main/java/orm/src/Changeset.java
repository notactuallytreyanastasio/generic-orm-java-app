package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__739);
    Changeset validateRequired(List<SafeIdentifier> fields__742);
    Changeset validateLength(SafeIdentifier field__745, int min__746, int max__747);
    Changeset validateInt(SafeIdentifier field__750);
    Changeset validateInt64(SafeIdentifier field__753);
    Changeset validateFloat(SafeIdentifier field__756);
    Changeset validateBool(SafeIdentifier field__759);
    Changeset putChange(SafeIdentifier field__762, String value__763);
    String getChange(SafeIdentifier field__766);
    Changeset deleteChange(SafeIdentifier field__769);
    Changeset validateInclusion(SafeIdentifier field__772, List<String> allowed__773);
    Changeset validateExclusion(SafeIdentifier field__776, List<String> disallowed__777);
    Changeset validateNumber(SafeIdentifier field__780, NumberValidationOpts opts__781);
    Changeset validateAcceptance(SafeIdentifier field__784);
    Changeset validateConfirmation(SafeIdentifier field__787, SafeIdentifier confirmationField__788);
    Changeset validateContains(SafeIdentifier field__791, String substring__792);
    Changeset validateStartsWith(SafeIdentifier field__795, String prefix__796);
    Changeset validateEndsWith(SafeIdentifier field__799, String suffix__800);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__805);
}
