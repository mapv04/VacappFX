package UserManagement.Models.Abstracts;

public abstract class AEmployee {
    protected int id;
    protected String name;
    protected String lastName;
    protected String username;
    protected String email;
    protected String password;
    protected int type;
    protected int status;

    public abstract int getId();

    public abstract void setId(int id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getLastName();

    public abstract void setLastName(String lastName);

    public abstract String getUsername();

    public abstract void setUsername(String username);

    public abstract String getEmail();

    public abstract void setEmail(String email);

    public abstract String getPassword();

    public abstract void setPassword(String password);

    public abstract int getType();

    public abstract void setType(int type);

    public abstract int getStatus();

    public abstract void setStatus(int status);
}
