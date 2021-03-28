package ui;

import domain.Drug;
import domain.Transaction;
import service.ServiceDrug;
import service.ServiceTransaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

public class Console {
    private ServiceDrug serviceDrug;
    private ServiceTransaction serviceTransaction;
    private Scanner scanner = new Scanner(System.in);

    public Console(ServiceDrug serviceDrug, ServiceTransaction serviceTransaction) {
        this.serviceDrug = serviceDrug;
        this.serviceTransaction = serviceTransaction;
    }

    private void showMenu() {
        System.out.println("1. Add to pharmacy stock");
        System.out.println("2. Update stock");
        System.out.println("3. Delete from stock");
        System.out.println("4. Proceed to order");
        System.out.println("5. Update order");
        System.out.println("6. Delete order");
        System.out.println("7. Show stock with price lower than given price");
        System.out.println("8. Search for drugs and transactions");
        System.out.println("9. Search for transactions between two certain dates");
        System.out.println("a. Show stock");
        System.out.println("b. Show orders");
        System.out.println("x. Exit program");
    }

    public void startConsole() throws Exception {
        while (true) {
            this.showMenu();

            System.out.println("Choose one option:");
            String option = scanner.next();

            if (option.equals("1")) {
                this.handleAddToStock();
            }  else if (option.equals("2")) {
                this.handleUpdateDrugStock();
            }
            else if (option.equals("3")) {
                this.handleDeleteDurgs();
            }else if (option.equals("4")) {
                this.handleProceedToOrder();
            }
            else if (option.equals("5")) {
                this.handleUpdateOrder();
            }

            else if (option.equals("6")) {
                this.handleDeleteOrder();
            }
            else if (option.equals("7")) {
                this.handleDrugsCheaperThanMaxPrice();
            } else if (option.equals("8")) {
               this.handleSearch();
            }else if (option.equals("9")) {
                this.handleTransactionBetweenDateAndTimes();
            }
            else if (option.equals("a")){
                this.handleShowCart();
            } else if (option.equals("b")) {
                this.handleShowOrders();
            } else if (option.equals("x")) {
                break;
            } else {
                System.out.println("The command does not exist!");
            }
        }
    }

    private void handleTransactionBetweenDateAndTimes() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            System.out.println("Enter start date: (dd.mm.yyyy HH:mm) ");
            scanner.nextLine();
            LocalDate start = LocalDate.parse(scanner.nextLine(),formatter);
            System.out.println("Enter end date: (dd.mm.yyyy HH:mm)");
            LocalDate end = LocalDate.parse(scanner.nextLine(),formatter);
            for (Transaction transaction : this.serviceTransaction.getBetweenTwoDateAndTimes(start,end))
                System.out.println(transaction.toString());

        } catch(Exception exception) {
            System.out.println("Errors are displayed:");
            System.out.println(exception.getMessage());
        }
    }

    private void handleSearch() throws Exception{
        try {

            System.out.println("Enter drug or transaction search key: ");
            String searchKey = scanner.next();
            for (Transaction transaction : this.serviceTransaction.searchTransaction(searchKey))
                System.out.println(transaction.toString());
            for (Drug drug : this.serviceDrug.searchDrugs(searchKey))
                System.out.println(drug.toString());
        } catch(Exception exception) {
            System.out.println("Errors are displayed:");
            System.out.println(exception.getMessage());
        }
    }

    private void handleDeleteOrder() throws Exception {

       try {

        System.out.println("Enter the transaction ID to be deleted: ");
        int idTransaction = scanner.nextInt();
        this.serviceTransaction.delete(idTransaction);
       } catch(Exception exception) {
           System.out.println("Errors are displayed:");
           System.out.println(exception.getMessage());
        }
    }

    private void handleUpdateOrder() {
        try {
            System.out.println("Enter transaction id:");
            int transactionId = scanner.nextInt();
            System.out.println("Enter drugId: ");
            int drugId = scanner.nextInt();
            System.out.println("Enter your card:");
            int clientCardNumber = scanner.nextInt();
            System.out.println("How many items from this drug: ");
            int numberOfDrugItems = scanner.nextInt();
            System.out.println("Date and time? ");
            String dateAndHour = scanner.next();

            this.serviceTransaction.updateTransaction(transactionId, drugId, clientCardNumber, numberOfDrugItems, dateAndHour);

            System.out.println("Great. Your order is updated!");
        } catch (Exception exception) {
            System.out.println("Errors are displayed:");
            System.out.println(exception.getMessage());
        }
    }

    private void handleProceedToOrder() {
        try {
            System.out.println("Enter transaction id:");
            int transactionId = scanner.nextInt();
            System.out.println("Enter drugId: ");
            int drugId = scanner.nextInt();
            System.out.println("Enter your card:");
            int clientCardNumber = scanner.nextInt();
            System.out.println("How many items from this drug: ");
            int numberOfDrugItems = scanner.nextInt();
            System.out.println("Date and time? ");
            String dateAndHour = scanner.next();

            this.serviceTransaction.addTransaction(transactionId, drugId, clientCardNumber, numberOfDrugItems, dateAndHour);

            System.out.println("Great. Your order is set!");
        } catch (Exception exception) {
            System.out.println("Errors are displayed:");
            System.out.println(exception.getMessage());
        }
    }

    private void handleShowCart() {
        for (Drug drug : this.serviceDrug.getAll())
            System.out.println(drug.toString());
    }
    private void handleShowOrders() {
        for (Transaction transaction : this.serviceTransaction.getAll())
            System.out.println(transaction.toString());
    }

    private void handleAddToStock() {
        try {
            System.out.println("Enter drug ID: ");
            int drugId = this.scanner.nextInt();
            System.out.println("Enter price: ");
            int drugPrice = this.scanner.nextInt();
            System.out.println("Number of drug items: ");
            int numberOfDrugItems = this.scanner.nextInt();
            System.out.println("Enter the name of the drug: ");
            String drugName = this.scanner.next();
            System.out.println("The drug is produced by: ");
            String drugProducer = this.scanner.next();
            System.out.println("The drug needs recipe: (true of false)");
            boolean drugNeedsRecipe = this.scanner.nextBoolean();

            this.serviceDrug.addDrug(drugId, drugPrice, numberOfDrugItems, drugName, drugProducer,drugNeedsRecipe);

            System.out.println("The items are added to the pharmacy stock!");
        } catch (Exception exception) {
            System.out.println("Errors are displayed:");
            System.out.println(exception.getMessage());
        }
    }
    private void handleUpdateDrugStock() throws Exception {
        try {
            System.out.println("Enter new drug ID: ");
            int drugId = this.scanner.nextInt();
            System.out.println("Enter new price: ");
            int drugPrice = this.scanner.nextInt();
            System.out.println("New number of drug items: ");
            int numberOfDrugItems = this.scanner.nextInt();
            System.out.println("Enter the new name of the drug: ");
            String drugName = this.scanner.next();
            System.out.println("The drug is produced by: ");
            String drugProducer = this.scanner.next();
            System.out.println("The drug needs recipe: (true of false)");
            boolean drugNeedsRecipe = this.scanner.nextBoolean();

            this.serviceDrug.updateDrug(drugId, drugPrice, numberOfDrugItems, drugName, drugProducer,drugNeedsRecipe);

            System.out.println("The new items are added to the pharmacy stock!");
        } catch (Exception exception) {
            System.out.println("Errors are displayed:");
            System.out.println(exception.getMessage());
        }
    }
    private void handleDeleteDurgs() throws Exception {
         try {System.out.println("Enter the drug ID to be deleted:");
        int drugId = scanner.nextInt();
        this.serviceDrug.delete(drugId);
    } catch  (Exception exception){
             System.out.println("Errors are displayed:");
             System.out.println(exception.getMessage());
         }
    }
    private void handleDrugsCheaperThanMaxPrice (){
        try {
            System.out.println("Enter Max Price: ");
            int maxPrice = this.scanner.nextInt();
            List<Drug> drugs = this.serviceDrug.getDrugsCheaperThanPrice(maxPrice);
            for (Drug drug : drugs ) {
                System.out.println(drug);
            }
        } catch (Exception exception){
            System.out.println("Errors are displayed:");
            System.out.println(exception.getMessage());
        }
    }
}
