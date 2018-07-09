package UserManagement;

public interface Confirmations {
    String editEmployee="Are you sure you want to modify this employee?";
    String editWorkGroup="Are you sure you want to modify this Work Group?";
    String deleteEmployee="Are you sure you want to delete this employee?";
    String deleteWorkGroup="Are you sure you want to delete this Work Group?";
    String activateUser="Are you sure you want to activate this employee?";
    String desactivateUser="Are you sure you want to desactivate this employee?";

    boolean confirmChanges(String message);
}
