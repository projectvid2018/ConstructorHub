package hub.constructor.constructorhub;

import android.location.Address;

/**
 * Created by gmmha on 12/9/2017.
 */

public class Member {

    String username;
    String address;
    String phoneNo;
    String email;
    String password;

    public Member(String nid){

    }

    public Member(String address, String username,
                  String phoneNo, String email,
                  String password) {

        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
