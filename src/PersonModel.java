import java.util.Date;

/**
 * Created by tcgogogo on 16/11/7.
 */
public class PersonModel {

    private int id;
    private String name;
    private String birthday;
    private String phoneNumber;

    public PersonModel(){}

    public PersonModel(int id, String name, String birthday, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
