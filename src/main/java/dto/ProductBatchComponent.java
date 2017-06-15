package dto;

public class ProductBatchComponent {
    private int productbatchId;
    private int ingredientbatchId;
    private double tare;
    private double netWeight;

    public ProductBatchComponent() {}

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

    public String toString() {
        return productbatchId + "\t" + ingredientbatchId +"\t" + tare +"\t" + netWeight;
    }
}
