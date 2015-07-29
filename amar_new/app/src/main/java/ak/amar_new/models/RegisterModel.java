package ak.amar_new.models;

/**
 * Created by amar on 31/5/15.
 */
public class RegisterModel {
    private String name;
    private String email;
    private String password;
    private String mobileNo;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    public void setName(String name){this.name = name;}
    public void setEmail(String email){this.email=email;}
    public void setPassword(String password){this.password = password;}
    public void setMobileNo(String mobileNo){this.mobileNo = mobileNo;}
    public void setAddress(String address){this.address = address;}
    public void setCity(String city){this.city = city;}
    public void setState(String state){this.state = state;}
    public void setPinCode(String pinCode){this.pinCode = pinCode;}

    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getMobileNo(){return mobileNo;}
    public String getAddress(){return address;}
    public String getCity(){return city;}
    public String getState(){return state;}
    public String getPinCode(){return pinCode;}



}
