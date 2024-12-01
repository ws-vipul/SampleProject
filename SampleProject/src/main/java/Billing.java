

public class Billing {

    public static void main(String[] args) {
        BillingService billingService = new BillingServiceImpl();
        billingService.getOrder();
        billingService.prepareBill();
        billingService.printBill();
    }

}
