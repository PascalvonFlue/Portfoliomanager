package ch.teko.pascal.portfoliomanager;

/**
 *
 * @author Pasca
 */
public class User {
    public String firstname;
    public String lastname;
    Holdings hld = null;
    
    public User(String _firstname, String _lastname){
        this.firstname = _firstname;
        this.lastname = _lastname;
        this.hld = new Holdings();
    }
    
}
