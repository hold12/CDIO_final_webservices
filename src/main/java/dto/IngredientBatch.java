package dto;

public class IngredientBatch {
    private int ingredientBatchId;
    private int ingredientId;
    private String ingredientName;
    private String supplier;
    private double amount;

    public IngredientBatch() {}

    public IngredientBatch(int ingredientBatchId, int ingredientId, String ingredientName, String supplier, double amount) {
        this.ingredientBatchId = ingredientBatchId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.supplier = supplier;
        this.amount = amount;
    }

    public IngredientBatch(int ingredientBatchId, int ingredientId, double amount) {
        this.ingredientBatchId = ingredientBatchId;
        this.ingredientId = ingredientId;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IngredientBatch that = (IngredientBatch) o;

        if (ingredientBatchId != that.ingredientBatchId) return false;
        if (ingredientId != that.ingredientId) return false;
        if (ingredientName != null ? !ingredientName.equals(that.ingredientName) : that.ingredientName != null) return false;
        if (supplier != null ? !supplier.equals(that.supplier) : that.supplier != null) return false;
        return Double.compare(that.amount, amount) == 0;
    }

    public int getIngredientBatchId() { return ingredientBatchId; }
    public void setIngredientBatchId(int ingredientBatchId) { this.ingredientBatchId = ingredientBatchId; }
    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }
    public String getIngredientName() { return ingredientName; }
    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String toString() {
        return ingredientBatchId + "\t" + ingredientId +"\t" + ingredientName +"\t" + supplier +"\t" + amount;
    }
}
