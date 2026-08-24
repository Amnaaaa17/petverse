package petverse.util;

import petverse.model.*;
public class PetFactory {

    private PetFactory() {}

    public static Pet create(String species, String name) {
        return switch (species.trim().toLowerCase()) {
            case "dog" -> new Dog(name);
            case "cat" -> new Cat(name);
            case "rabbit" -> new Rabbit(name);
            case "parrot" -> new Parrot(name);
            default -> null;
        };
    }

    public static int unlockAgeFor(String species) {
        Pet tmp = create(species, "_");

        return tmp == null
                ? Integer.MAX_VALUE
                : tmp.unlockAge();
    }
}
