import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BillingServiceImpl implements BillingService {
    static final List<ProductEntity> productEntities = new ArrayList<>();
    private static Boolean makeTotal = false;
    static float itemTotal = 0;
    static float itemTotalWithTax = 0;

    @Override
    public void getOrder() {
        while (!makeTotal) {
            readUserInput();
        }
    }

    private void readUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Product Type (hint: b) : a)book b)food c)Medical d)Other");
        String productType = scanner.nextLine();
        System.out.println("Enter Product Name (Please Add imported for imported products) : ");
        String product = scanner.nextLine();
        System.out.println("Enter Product Quantity(hint:3) : ");
        int quantity = scanner.nextInt();
        System.out.println("Enter Product Price(hint:50) : ");
        float price = scanner.nextFloat();
        System.out.println("Press A to add and Press F to finish");
        String ans = scanner.next();

        productEntities.add(new ProductEntity(product, quantity, price, productType));

        if (ans.equalsIgnoreCase("f")) {
            makeTotal = true;
        } else if (ans.equalsIgnoreCase("a")) {
            makeTotal = false;
        } else {
            while (!ans.equalsIgnoreCase("a") && !ans.equalsIgnoreCase("f")) {
                System.err.println("Please Provide Valid Input");
                ans = scanner.nextLine();
            }
            makeTotal = !ans.equalsIgnoreCase("a");
        }
    }

    @Override
    public void prepareBill() {
        productEntities.forEach(productEntity -> {
            float tax = 0;
            // a = book, b= food, c = medicines, d= other
            if (productEntity.getProductType().equalsIgnoreCase("d")) {
                //Calculating basic tax for product
                tax = (float) (tax + (productEntity.getProductPrice() * productEntity.getProductQty() * 0.10));
            }
            if (productEntity.getProductName().toLowerCase().contains("import")) {
                //Calculating import tax for product
                tax = (float) (tax + (productEntity.getProductPrice() * productEntity.getProductQty() * 0.05));
            }
            itemTotal = itemTotal + (productEntity.getProductPrice() * productEntity.getProductQty());
            productEntity.setProductPrice((productEntity.getProductPrice() * productEntity.getProductQty()) + tax);
            itemTotalWithTax = itemTotalWithTax + productEntity.getProductPrice();

        });
    }

    @Override
    public void printBill() {
        // Printing and rounding product price to two decimal places
        System.out.println("Total Bill For Purchased Product");
        for (ProductEntity productEntity : productEntities) {
            System.out.println(productEntity.getProductName() + " : " + String.format("%.2f", productEntity.getProductPrice()));
        }
        System.out.println("Sales Taxes : " + String.format("%.2f", (itemTotalWithTax - itemTotal)));
        System.out.println("Total : " + String.format("%.2f", itemTotalWithTax));
    }
}
