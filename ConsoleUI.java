package petverse.ui;

import petverse.model.Pet;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BLUE = "\u001B[34m";

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcomeBanner() {
        cls();

        println(CYAN + BOLD);
        println("╔══════════════════════════════════════════════════════╗");
        println("║                                                      ║");
        println("║        🐾  P E T V E R S E  🐾                      ║");
        println("║        Virtual Pet Management Simulator              ║");
        println("║                                                      ║");
        println("╚══════════════════════════════════════════════════════╝");
        println(RESET);
    }

    public void showMainMenu(boolean hasSave) {
        println(BOLD + "\n[ MAIN MENU ]" + RESET);
        println("  1. "
                + (hasSave
                ? "Continue saved game"
                : "New Game"));

        if (hasSave) {
            println("  2. New Game (delete save)");
        }

        println("  0. Exit");
        prompt();
    }

    public void showAdoptMenu(int maxUnlockedAge) {
        println(BOLD + "\n[ ADOPT A PET ]" + RESET);
        println("  Available species:");
        println("  1. 🐕 Dog      — always available (starter pet)");
        println("  2. 🐱 Cat      — unlocks at pet age 3  "
                + lockLabel(3, maxUnlockedAge));
        println("  3. 🐇 Rabbit   — unlocks at pet age 6  "
                + lockLabel(6, maxUnlockedAge));
        println("  4. 🦜 Parrot   — unlocks at pet age 10 "
                + lockLabel(10, maxUnlockedAge));
        println("  0. Back");
        prompt();
    }

    private String lockLabel(int required, int maxAge) {
        if (maxAge >= required) {
            return GREEN + "[UNLOCKED]" + RESET;
        }

        return RED
                + "[LOCKED — need age "
                + required
                + "]"
                + RESET;
    }

    // ----------------------------------------------------------------
    // Pet List
    // ----------------------------------------------------------------

    public void showPetRoster(List<Pet> pets, int selected) {
        println(BOLD + "\n[ YOUR PETS ]" + RESET);

        for (int i = 0; i < pets.size(); i++) {
            Pet p = pets.get(i);

            String marker = (i == selected)
                    ? CYAN + "► " + RESET
                    : "  ";

            String sickTag = p.isSick()
                    ? RED + " [SICK!]" + RESET
                    : "";

            String critical = p.isCritical()
                    ? YELLOW + " [!]" + RESET
                    : "";

            println(
                    marker
                            + (i + 1)
                            + ". "
                            + p
                            + " "
                            + p.getMoodEmoji()
                            + sickTag
                            + critical
            );
        }
    }
    public void showPetDashboard(Pet pet) {
        cls();

        println(
                CYAN + BOLD
                        + "╔══════════════════════════════════════════╗"
                        + RESET
        );

        println(
                CYAN + BOLD
                        + "║  "
                        + centred(
                                pet.getName()
                                        + " the "
                                        + pet.getSpecies(),
                                40
                        )
                        + "  ║"
                        + RESET
        );

        println(
                CYAN + BOLD
                        + "╚══════════════════════════════════════════╝"
                        + RESET
        );

        println();
        println(pet.getAsciiArt());

        println(
                PURPLE
                        + "  "
                        + pet.getMoodEmoji()
                        + "  "
                        + pet.getStatus()
                        + RESET
        );

        if (pet.isSick()) {
            println(
                    RED
                            + "  ⚠  "
                            + pet.getName()
                            + " is SICK! Use MEDICINE."
                            + RESET
            );
        }

        println();
        println(BOLD + "  Stats:" + RESET);

        println(
                "  "
                        + colourBar(
                                "Hunger",
                                pet.getHunger(),
                                100,
                                true
                        )
        );

        println(
                "  "
                        + colourBar(
                                "Happiness",
                                pet.getHappiness(),
                                100,
                                false
                        )
        );

        println(
                "  "
                        + colourBar(
                                "Health",
                                pet.getHealth(),
                                100,
                                false
                        )
        );

        println(
                "  "
                        + String.format(
                                "%-12s Age: %d  |  Cycle: %d/5",
                                "",
                                pet.getAge(),
                                pet.getCycleCount()
                        )
        );

        println();
    }

    private String colourBar(
            String label,
            int val,
            int max,
            boolean invertBad
    ) {
        String bar = Pet.statBar(label, val, max);

        boolean bad = invertBad
                ? val > 70
                : val < 30;

        return (bad ? RED : GREEN)
                + bar
                + RESET;
    }
    public void showInteractionMenu() {
        println(BOLD + "[ ACTIONS ]" + RESET);
        println("  1. 🍖 Feed");
        println("  2. 🎾 Play");
        println("  3. 💤 Sleep");
        println("  4. 💊 Medicine");
        println("  5. 🔊 Make Sound");
        println("  6. 🔄 Switch Pet");
        println("  7. 🐾 Adopt New Pet");
        println("  8. 💾 Save & Exit");
        println("  0. Exit Without Saving");
        prompt();
    }

    public void showResult(String message) {
        println(GREEN + "\n  >> " + message + RESET);
        pause();
    }

    public void showEvent(String event) {
        println(YELLOW + "\n  ✦ EVENT: " + event + RESET);
        pause();
    }

    public void showMilestone(String message) {
        println();
        println(
                PURPLE + BOLD
                        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★"
                        + RESET
        );

        println(
                PURPLE + BOLD
                        + "  🎉 MILESTONE: "
                        + message
                        + RESET
        );

        println(
                PURPLE + BOLD
                        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★"
                        + RESET
        );

        pause();
    }

    public void showError(String message) {
        println(RED + "\n  ✗ " + message + RESET);
        pause();
    }

    public void showSaveConfirmation(boolean success) {
        if (success) {
            println(
                    GREEN
                            + "  ✔ Game saved successfully."
                            + RESET
            );
        } else {
            println(
                    RED
                            + "  ✗ Save failed."
                            + RESET
            );
        }

        pause();
    }

    public String readLine() {
        System.out.print(CYAN + "  > " + RESET);
        return scanner.nextLine().trim();
    }

    public int readInt(int min, int max) {
        while (true) {
            String line = readLine();

            try {
                int val = Integer.parseInt(line);

                if (val >= min && val <= max) {
                    return val;
                }

            } catch (NumberFormatException ignored) {
            }

            println(
                    RED
                            + "  Please enter a number between "
                            + min
                            + " and "
                            + max
                            + "."
                            + RESET
            );
        }
    }

    public String readPetName() {
        println("  Enter a name for your new pet:");

        String name = readLine();

        if (name.isBlank()) {
            name = "Buddy";
        }

        return name;
    }

    private void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pause() {
        try {
            Thread.sleep(1400);
        } catch (InterruptedException ignored) {
        }
    }

    private void prompt() {
        System.out.print(BLUE + "\n  Choice: " + RESET);
    }

    private void println() {
        System.out.println();
    }

    private void println(String s) {
        System.out.println(s);
    }

    private String centred(String s, int width) {
        if (s.length() >= width) {
            return s;
        }

        int pad = (width - s.length()) / 2;

        return " ".repeat(pad) + s;
    }
}
