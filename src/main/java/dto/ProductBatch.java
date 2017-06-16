package dto;

import java.sql.Timestamp;

public class ProductBatch {
    private int productbatchId;
    private int recipeId;
    private String recipeName;
    private Timestamp createdTime;
    private Timestamp finishedTime;
    private int status;
    private int userId;
    private String initials;

    public ProductBatch(int productbatchId, int recipeId, String recipeName, Timestamp createdTime, Timestamp finishedTime, int status, int userId, String initials) {
        this.productbatchId = productbatchId;
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.createdTime = createdTime;
        this.finishedTime = finishedTime;
        this.status = status;
        this.userId = userId;
        this.initials = initials;
    }

    public ProductBatch() {}

    public ProductBatch(int productbatchId, Timestamp createdTime, Timestamp finishedTime, int status, int recipeId, int userId) {
        this.productbatchId = productbatchId;
        this.createdTime = createdTime;
        this.finishedTime = finishedTime;
        this.status = status;
        this.recipeId = recipeId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductBatch that = (ProductBatch) o;

        if (productbatchId != that.productbatchId) return false;
        if (recipeId != that.recipeId) return false;
        if (recipeName != null ? !recipeName.equals(that.recipeName) : that.recipeName != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (finishedTime != null ? !finishedTime.equals(that.finishedTime) : that.finishedTime != null) return false;
        if (status != that.status) return false;
        if (userId != that.userId) return false;
        return (initials != null ? initials.equals(that.initials) : that.initials == null);
    }

    public int getProductbatchId() { return productbatchId; }
    public void setProductbatchId(int productbatchId) { this.productbatchId = productbatchId; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public int getRecipeId() { return recipeId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getUserId() { return userId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
    public Timestamp getCreatedTime() { return createdTime; }
    public void setCreatedTime(Timestamp createdTime) { this.createdTime = createdTime; }
    public Timestamp getFinishedTime() { return finishedTime; }
    public void setFinishedTime(Timestamp finishedTime) { this.finishedTime = finishedTime; }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String toString() { return productbatchId + "\t" + recipeId + "\t" + recipeId + "\t" + createdTime + "\t" + finishedTime + "\t" + status + "\t" + userId + "\t" + initials; }
}
