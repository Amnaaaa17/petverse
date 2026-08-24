package petverse.model;

public class Rabbit extends Pet {

    private static final long serialVersionUID = 1L;

    public Rabbit(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Rabbit";
    }

    @Override
    public String makeSound() {
        return getName() + " wiggles its nose at you! 🐇";
    }

    @Override
    public String playDescription() {
        return "Binkying around the room!";
    }

    @Override
    public int unlockAge() {
        return 6;
    }

    @Override
    public String getAsciiArt() {
        return """
                 (\\(\\
                 ( -.-)
                 o_(")(")
               """;
    }
}
