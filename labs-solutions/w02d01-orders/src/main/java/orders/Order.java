package orders;

public class Order {

    private Long id;
    private String productName;
    private int productCount;
    private int pricePerProduct;

    public Order(String productName, int productCount, int pricePerProduct) {
        this.productName = productName;
        this.productCount = productCount;
        this.pricePerProduct = pricePerProduct;
    }

    public Order(Long id, String productName, int productCount, int pricePerProduct) {
        this.id = id;
        this.productName = productName;
        this.productCount = productCount;
        this.pricePerProduct = pricePerProduct;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductCount() {
        return productCount;
    }

    public int getPricePerProduct() {
        return pricePerProduct;
    }
}

