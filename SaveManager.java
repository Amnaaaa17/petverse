package petverse.data;

import petverse.model.Pet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {

    private static final String SAVE_FILE = "petverse_save.dat";

    private SaveManager() {}

    /** Saves the list of pets to disk. */
    public static boolean save(List<Pet> pets) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {

            oos.writeObject(new ArrayList<>(pets));
            return true;

        } catch (IOException e) {
            System.err.println(
                    "[SaveManager] Error saving: " + e.getMessage()
            );
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Pet> load() {
        File f = new File(SAVE_FILE);

        if (!f.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SAVE_FILE))) {

            return (List<Pet>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println(
                    "[SaveManager] Error loading save: "
                            + e.getMessage()
            );
            return new ArrayList<>();
        }
    }
    public static boolean saveExists() {
        return new File(SAVE_FILE).exists();
    }

    public static void deleteSave() {
        new File(SAVE_FILE).delete();
    }
}
