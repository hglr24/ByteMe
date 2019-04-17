package controls;
@FunctionalInterface
public interface CredentialValidator {
 /**
  * Provides access to values of particular fields, such as the username and password text fields in the welcome page,
  * in order to validate them in another class and remove a circular dependency
  * @author Anna Darwish
  */
 String currentFieldValue();
}
