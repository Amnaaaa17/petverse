package petverse.model;

public class Cat extends Pet {

    private static final long serialVersionUID = 1L;

    public Cat(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Cat";
    }

    @Override
    public String makeSound() {
        return getName() + " says: Meow~ 🐱";
    }

    @Override
    public String playDescription() {
        return "Chasing a laser dot!";
    }

    @Override
    public int unlockAge() {
        return 3;
    }

    @Override
    public String getAsciiArt() {
        return """
                 /\\_____/\\
                (  o   o  )
                 =( Y )=
                  )   (
                  (_)-(_)
               """;
    }
}
