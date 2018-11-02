package hub.constructor.constructorhub;

public class Upload {

    private String heading;
    private String companyName;
    private String companyAddress;
    private String service;
    private String price;
    private String description;
    private String mImageUrl;

    public Upload() {
    }

    public Upload(String heading, String service, String price, String mImageUrl) {
        this.heading = heading;
        this.service = service;
        this.price = price;
        this.mImageUrl = mImageUrl;
    }

    public Upload(String heading, String companyName, String companyAddress,
                  String service, String price, String description, String mImageUrl) {
        this.heading = heading;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.service = service;
        this.price = price;
        this.description = description;
        this.mImageUrl = mImageUrl;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
