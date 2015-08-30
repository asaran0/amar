package ak.amar_new.models;

import java.util.ArrayList;

/**
 * Created by amar on 3/8/15.
 */
public class FoodMenu {
    private OutletDetail outletDetail;
    private ArrayList<FoodMenuCategory> foodMenuCategories = new ArrayList<>();

    public OutletDetail getOutletDetail() {
        return outletDetail;
    }

    public void setOutletDetail(OutletDetail outletDetail) {
        this.outletDetail = outletDetail;
    }

    public ArrayList<FoodMenuCategory> getFoodMenuCategories() {
        return foodMenuCategories;
    }

    public void setFoodMenuCategories(ArrayList<FoodMenuCategory> foodMenuCategories) {
        this.foodMenuCategories = foodMenuCategories;
    }
}
