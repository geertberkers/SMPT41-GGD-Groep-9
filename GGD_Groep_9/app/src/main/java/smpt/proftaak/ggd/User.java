package smpt.proftaak.ggd;

/**
 * Created by BartKneepkens on 29/06/15.
 */
public class User {
    String name, email, number, postcode;

    public User(String name, String email, String postcode, String number) {
        this.name = name;
        this.email = email;
        this.postcode = postcode;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
