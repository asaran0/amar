package ak.amar_new.models;

import android.view.Menu;

import java.util.ArrayList;

/**
 * Created by amar on 1/8/15.
 */
public class OutletDetail {
    private  String outletId;
    private  String outletName;
    private  String locality;
    private  String city;
    private  String pinCode;
    private  String address;
    private ArrayList<String> imageUrls = new ArrayList<>();

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getOutletId() {
        return outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

}
