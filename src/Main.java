import domain.*;
import repository.IRepository;
import repository.InMemoryRepository;
import service.ServiceDrug;
import service.ServiceTransaction;
import ui.Console;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        IRepository<Drug> drugRepository= new InMemoryRepository<Drug>();
        IRepository<Transaction> transactionIRepository = new InMemoryRepository<Transaction>();

        DrugValidator drugValidator = new DrugValidator();

        TransactionValidator transactionValidator = new TransactionValidator();
        ServiceDrug serviceDrug = new ServiceDrug(drugRepository, drugValidator);
        ServiceTransaction serviceTransaction = new ServiceTransaction(transactionIRepository, drugRepository , transactionValidator);

        Console console = new Console(serviceDrug, serviceTransaction);

        serviceDrug.addDrug(1, 5, 3, "Aspirine", "Bayern", false);
        serviceDrug.addDrug(2, 10, 2, "Paracetamol", "Modena", false);
        serviceDrug.addDrug(3, 15, 3, "Ibuprofen", "Pfizer", true);
        serviceTransaction.addTransaction(1,1,3000,2,"10.12.2020 13:50");
        serviceTransaction.addTransaction(2,3,4000,1,"10.12.2019 13:40");
        serviceTransaction.addTransaction(3,3,5000,2,"10.12.2022 11:50");
        console.startConsole();
    }
}
