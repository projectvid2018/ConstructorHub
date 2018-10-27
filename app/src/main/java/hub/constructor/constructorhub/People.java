package hub.constructor.constructorhub;

public class People {

    String peopleId;
    String peopleUsername;
    String peopleAddress;
    String peoplePhone;
    String peopleEmail;
    String peoplePassword;

    public People() {
    }

    public People(String peopleId, String peopleAddress, String peopleUserName, String peoplePhone, String peopleEmail, String peoplePassword) {
        this.peopleId = peopleId;
        this.peopleAddress= peopleAddress;
        this.peopleUsername= peopleUserName;
        this.peoplePhone = peoplePhone;
        this.peopleEmail = peopleEmail;
        this.peoplePassword = peoplePassword;
    }

    public String getPeopleId() {
        return peopleId;
    }

    public String getPeopleUsername() {
        return peopleUsername;
    }

    public String getPeopleAddress() {
        return peopleAddress;
    }

    public String getPeoplePhone() {
        return peoplePhone;
    }

    public String getPeopleEmail() {
        return peopleEmail;
    }

    public String getPeoplePassword() {
        return peoplePassword;
    }
}
