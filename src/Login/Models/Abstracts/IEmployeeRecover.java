package Login.Models.Abstracts;

public interface IEmployeeRecover {
    Void changePassword(String user,String password,int question,String response);
}
