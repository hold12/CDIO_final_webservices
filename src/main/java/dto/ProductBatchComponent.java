package dto;

public class ProductBatchComponent {
    private int productbatchId;
    private int ingredientbatchId;
    private int ingredientId;
    private String ingredientName;
    private String supplier;
    private double tare;
    private double netWeight;

    public ProductBatchComponent(int productbatchId, int ingredientbatchId, int ingredientId, String ingredientName, String supplier, double tare, double netWeight) {
        this.productbatchId = productbatchId;
        this.ingredientbatchId = ingredientbatchId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.supplier = supplier;
        this.tare = tare;
        this.netWeight = netWeight;
    }

    public ProductBatchComponent(int productbatchId, int ingredientbatchId, double tare, double netWeight) {
        this.productbatchId = productbatchId;
        this.ingredientbatchId = ingredientbatchId;
        this.tare = tare;
        this.netWeight = netWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductBatchComponent that = (ProductBatchComponent) o;

        if (productbatchId != that.productbatchId) return false;
        if (ingredientbatchId != that.ingredientbatchId) return false;
        if (ingredientId != that.ingredientId) return false;
        if (ingredientName != null ? !ingredientName.equals(that.ingredientName) : that.ingredientName != null) return false;
        if (supplier != null ? !supplier.equals(that.supplier) : that.supplier != null) return false;
        if (tare != that.tare) return false;
        return netWeight == that.netWeight;
    }

    public int getProductbatchId() { return productbatchId; }
    public void setProductbatchId(int productbatchId) { this.productbatchId = productbatchId; }
    public int getIngredientbatchId() { return ingredientbatchId; }
    public void setIngredientbatchId(int ingredientbatchId) { this.ingredientbatchId = ingredientbatchId; }
    public double getTare() { return tare; }
    public void setTare(double tare) { this.tare = tare; }
    public double getNetWeight() { return netWeight; }
    public void setNetWeight(double netWeight) { this.netWeight = netWeight; }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String toString() {
        return productbatchId + "\t" + ingredientbatchId +"\t" + ingredientId + "\t" + ingredientName + "\t" + supplier + "\t" + tare +"\t" + netWeight;
    }
}
