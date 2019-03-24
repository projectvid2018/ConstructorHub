package hub.constructor.constructorhub.Class;

public class Upload {

    private String userUid;
    private String userName;
    private String email;
    private String productId;
    private String heading;
    private String companyName;
    private String companyAddress;
    private String service;
    private String price;
    private String description;
    private String mImageUrl;

    public Upload() {
    }

    public Upload(String userUid, String userName, String productId, String heading, String mImageUrl) {
        this.userUid = userUid;
        this.userName = userName;
        this.productId = productId;
        this.heading = heading;
        this.mImageUrl = mImageUrl;
    }

    public Upload(String heading, String service, String price, String mImageUrl) {
        this.heading = heading;
        this.service = service;
        this.price = price;
        this.mImageUrl = mImageUrl;
    }

    public Upload(String userUid, String productId, String userName, String email, String heading,
                  String companyName, String companyAddress, String service,
                  String price, String description, String mImageUrl) {
        this.userUid = userUid;
        this.productId = productId;
        this.userName = userName;
        this.email = email;
        this.heading = heading;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.service = service;
        this.price = price;
        this.description = description;
        this.mImageUrl = mImageUrl;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
