package online.ecommerce.platform;


public class DigitalProduct extends Product {
    private String link;

    public DigitalProduct(int id, String name, String description, double price, int quantity, String link) {
        super(id, name, description, price, quantity);
        this.link = link;
    }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    @Override
    public String toString() {
        return super.toString() + " | Digital Link: " + link;
    }
}

