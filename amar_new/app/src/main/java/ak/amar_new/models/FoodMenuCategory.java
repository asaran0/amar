package ak.amar_new.models;

import java.util.ArrayList;

/**
 * Created by amar on 30/8/15.
 */
public class FoodMenuCategory {
    private String categoryName;
    private ArrayList<FoodMenuItem> foodMenuItems = new ArrayList<>();

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<FoodMenuItem> getFoodMenuItems() {
        return foodMenuItems;
    }

    public void setFoodMenuItems(ArrayList<FoodMenuItem> foodMenuItems) {
        this.foodMenuItems = foodMenuItems;
    }
}
