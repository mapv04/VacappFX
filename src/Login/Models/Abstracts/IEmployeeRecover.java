package Login.Models.Abstracts;

public interface IEmployeeRecover {
    void changePassword(String user,String password,int question,String response);
}
