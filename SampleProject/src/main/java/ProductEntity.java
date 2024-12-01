public class ProductEntity {

    private String productName;
    private int productQty;
    private float productPrice;
    private String productType;

    public ProductEntity(String productName, int productQty, float productPrice, String productType) {
        this.productName = productName;
        this.productQty = productQty;
        this.productPrice = productPrice;
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
