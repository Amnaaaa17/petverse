package petverse.model;

public class Dog extends Pet {

    private static final long serialVersionUID = 1L;

    public Dog(String name) {
        super(name);
        setUnlocked(true);
    }

    @Override
    public String getSpecies() {
        return "Dog";
    }

    @Override
    public String makeSound() {
        return getName() + " says: Woof woof! 🐕";
    }

    @Override
    public String playDescription() {
        return "Fetching the ball!";
    }

    @Override
    public int unlockAge() {
        return 0;
    }

    @Override
    public String getAsciiArt() {
        return """
                  / \\__
                 (    @\\___
                 /         O
                /   (_____/
               /_____/   U
               """;
    }
}
