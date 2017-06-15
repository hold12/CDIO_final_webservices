package dto;

public class RecipeComponent {
    private int recipeId;
    private int ingredientId;
    private String ingredientName;
    private String supplier;
    private double nominatedNetWeight;
    private double tolerance;

    public RecipeComponent() {}

    public RecipeComponent(int recipeId, int ingredientId, String ingredientName, String supplier, double nominatedNetWeight, double tolerance) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.supplier = supplier;
        this.nominatedNetWeight = nominatedNetWeight;
        this.tolerance = tolerance;
    }

    public RecipeComponent(int recipeId, int ingredientId, double nominatedNetWeight, double tolerance) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.nominatedNetWeight = nominatedNetWeight;
        this.tolerance = tolerance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeComponent that = (RecipeComponent) o;

        if (recipeId != that.recipeId) return false;
        if (ingredientId != that.ingredientId) return false;
        if (Double.compare(that.nominatedNetWeight, nominatedNetWeight) != 0) return false;
        return Double.compare(that.tolerance, tolerance) == 0;
    }


    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }
    public double getNominatedNetWeight() { return nominatedNetWeight; }
    public void setNominatedNetWeight(double nominatedNetWeight) { this.nominatedNetWeight = nominatedNetWeight; }
    public double getTolerance() { return tolerance; }
    public void setTolerance(double tolerance) { this.tolerance = tolerance; }

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
        return recipeId + "\t" + ingredientId + "\t" + ingredientName + "\t" + supplier + "\t" + nominatedNetWeight + "\t" + tolerance;
    }
}
