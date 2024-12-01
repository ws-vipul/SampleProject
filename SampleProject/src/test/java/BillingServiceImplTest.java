import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BillingServiceImplTest {

    private BillingServiceImpl billingService;

    @BeforeEach
    public void setup() {
        billingService = new BillingServiceImpl();

        BillingServiceImpl.productEntities.clear();
    }

    @Test
    public void testPrepareBill_withTaxCalculation() {

        ProductEntity book = new ProductEntity("book", 1, 12.99f, "a");
        ProductEntity food = new ProductEntity("imported food", 2, 20.00f, "b");
        ProductEntity medical = new ProductEntity("Medical", 1, 100.0f, "c");
        ProductEntity other = new ProductEntity("imported other", 1, 15.0f, "d");

        BillingServiceImpl.productEntities.add(book);
        BillingServiceImpl.productEntities.add(food);
        BillingServiceImpl.productEntities.add(medical);
        BillingServiceImpl.productEntities.add(other);

        billingService.prepareBill();

        float expectedTotal = 12.99f + (20.00f * 2) + 100.0f + 15.0f;
        assertEquals(expectedTotal, BillingServiceImpl.itemTotal, 0.01);


        assertTrue(book.getProductPrice() >= 12.99f);
        assertTrue(food.getProductPrice() >= (20.00f * 2));
        assertTrue(other.getProductPrice() > 15.0f);
    }

    @Test
    public void testPrintBill() {
        ProductEntity book = new ProductEntity("book", 1, 12.99f, "a");
        ProductEntity food = new ProductEntity("imported food", 2, 20.00f, "b");

        BillingServiceImpl.productEntities.add(book);
        BillingServiceImpl.productEntities.add(food);

        billingService.prepareBill();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        billingService.printBill();

        String expectedOutput = "Total Bill For Purchased Product\n" + "book : 12.99\n" + "imported food : 42.00\n" + "Sales Taxes : 2.00\n" + "Total : 54.99\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testGetOrder() {
        String input = "b\n" + "imported food\n" + "2\n" + "25.00\n" + "f\n";

        // Simulate user input via System.in
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Call the method that prompts for user input
        billingService.getOrder();

        // Verify the product is added to the list
        assertEquals(1, BillingServiceImpl.productEntities.size());
        ProductEntity product = BillingServiceImpl.productEntities.get(0);
        assertEquals("imported food", product.getProductName());
        assertEquals(2, product.getProductQty());
        assertEquals(25.00f, product.getProductPrice());
    }


    @Test
    public void testInvalidInput() {
        // Simulate invalid input: invalid input "z" followed by valid input
        String input = "b\n" + "imported food\n" + "1\n" + "10.00\n" + "z\n" + "f\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        billingService.getOrder();

        assertEquals(1, BillingServiceImpl.productEntities.size());
        ProductEntity product = BillingServiceImpl.productEntities.get(0);
        assertEquals("imported food", product.getProductName());
        assertEquals(1, product.getProductQty());
        assertEquals(10.00f, product.getProductPrice());
    }

}
