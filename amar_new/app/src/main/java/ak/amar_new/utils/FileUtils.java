package ak.amar_new.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ak.amar_new.R;
import ak.amar_new.models.FoodMenu;

/**
 * Created by amar on 30/8/15.
 */
public class FileUtils {
    public static FoodMenu ReadFoodMenu(String path){
        FoodMenu foodMenu = new FoodMenu();
        Gson gson = new Gson();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            foodMenu = gson.fromJson(bufferedReader,FoodMenu.class);
            Log.i("test FileUtils","data "+foodMenu);
        } catch (IOException e) {
            Log.i("FileUtils exception",e.toString());
            e.printStackTrace();

        }
        return foodMenu;
    }
    public static void WriteFoodMenu(String path, FoodMenu foodMenu){
        Gson gson = new Gson();
        String jsonString = gson.toJson(foodMenu);
        Log.i("test FileUtils", jsonString);

        try {
            FileWriter fw = new FileWriter(path);
            //BufferedWriter bw = new BufferedWriter(fw);
            fw.write(jsonString);
            fw.close();
        } catch (IOException e) {
            Log.i("FileUtils exception",e.toString());
            e.printStackTrace();
        }

    }
}
