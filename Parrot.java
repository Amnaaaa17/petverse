package petverse.model;

/**
 * Parrot — the rarest pet, unlocks when any pet reaches age 10.
 */
public class Parrot extends Pet {

    private static final long serialVersionUID = 1L;

    private static final String[] PHRASES = {
            "Polly wants a cracker!",
            "Hello! Hello!",
            "Pretty bird!",
            "Squawk! Feed me!",
            "Who's a good bird?"
    };

    public Parrot(String name) {
        super(name);
    }

    @Override
    public String getSpecies() {
        return "Parrot";
    }

    @Override
    public String makeSound() {
        String phrase = PHRASES[
                (int) (Math.random() * PHRASES.length)
        ];

        return getName()
                + " squawks: \"" + phrase + "\" 🦜";
    }

    @Override
    public String playDescription() {
        return "Dancing on the perch!";
    }

    @Override
    public int unlockAge() {
        return 10;
    }

    @Override
    public String getAsciiArt() {
        return """
                   _
                  {o}
                  /)
                 (  )--,
                  \\/  /
                  \\_/
               """;
    }
}
