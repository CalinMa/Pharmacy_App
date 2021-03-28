package service;

import domain.Drug;
import domain.DrugValidator;
import repository.IRepository;
import java.util.ArrayList;
import java.util.List;

public class ServiceDrug {
    private IRepository<Drug> repository;
    private DrugValidator drugValidator;

    public ServiceDrug(IRepository<Drug> repository, DrugValidator drugValidator) {
        this.repository = repository;
        this.drugValidator = drugValidator;
    }

    /**
     * ...
     * @param drugId
     * @param drugPrice
     * @param numberOfDrugItems
     * @param drugName
     * @param drugProducer
     * @param drugNeedsRecipe
     */
    public void addDrug(int drugId, int drugPrice, int numberOfDrugItems, String drugName, String drugProducer, boolean drugNeedsRecipe ) throws Exception {
        Drug drug = new Drug(drugId, drugPrice, numberOfDrugItems, drugName, drugProducer,drugNeedsRecipe);
        this.drugValidator.validate(drug);
        this.repository.create(drug);

    }

    public void updateDrug(int drugId, int drugPrice, int numberOfDrugItems, String drugName, String drugProducer, boolean drugNeedsRecipe) throws Exception {
        Drug drug = new Drug(drugId, drugPrice, numberOfDrugItems, drugName, drugProducer,drugNeedsRecipe);
        this.drugValidator.validate(drug);
        this.repository.update(drug);
    }

    public void delete(int idDrug) throws Exception {
        this.repository.delete(idDrug);
    }
    public List<Drug> getDrugsCheaperThanPrice (int maxPrice){
        List<Drug> drugs = new ArrayList<>();
        for (Drug drug: this.getAll()) {
            if (maxPrice > drug.getDrugPrice())
                drugs.add(drug);
        }
        return drugs;
    }
  public List<Drug> searchDrugs(String searchText) {
        List<Drug> results = new ArrayList<>();
       // List<String> searchList = new ArrayList<String>();
        for (Drug drug: this.getAll()) {
            if (drug.getDrugName().contains(searchText) ||
            drug.getDrugProducer().contains(searchText) ||
            String.valueOf(drug.getIdEntity()).contains(searchText) ||
                    String.valueOf(drug.getDrugPrice()).contains(searchText) ||
                    String.valueOf(drug.getNumberOfDrugItems()).contains(searchText) ||
                    String.valueOf(drug.getDrugNeedsRecipe()).contains(searchText)) {
                results.add(drug);
            }
        }

        return results;
    }
    public List<Drug> getAll() {
        return this.repository.read();
    }
}
