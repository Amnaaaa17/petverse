package petverse;

import petverse.data.SaveManager;
import petverse.model.Pet;
import petverse.ui.ConsoleUI;
import petverse.util.PetFactory;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final ConsoleUI ui;
    private List<Pet> pets;
    private int activePetIndex;

    // Unlock flags (species → whether the milestone banner was shown)
    private static final String[] ALL_SPECIES = {"dog", "cat", "rabbit", "parrot"};

    public GameManager() {
        ui = new ConsoleUI();
        pets = new ArrayList<>();
        activePetIndex = 0;
    }

    public void run() {
        ui.showWelcomeBanner();
        mainMenu();
    }

    // ----------------------------------------------------------------
    // Main Menu
    // ----------------------------------------------------------------

    private void mainMenu() {
        boolean hasSave = SaveManager.saveExists();
        ui.showMainMenu(hasSave);
        int choice = ui.readInt(0, hasSave ? 2 : 1);

        switch (choice) {
            case 0 -> exitGame(false);
            case 1 -> {
                if (hasSave) {
                    loadGame();
                } else {
                    startNewGame();
                }
            }
            case 2 -> {
                SaveManager.deleteSave();
                pets.clear();
                startNewGame();
            }
        }
    }
    private void startNewGame() {
        ui.showWelcomeBanner();
        System.out.println("\n  Welcome to PetVerse! Let's adopt your first pet.");
        adoptPet(true);

        if (pets.isEmpty()) {
            System.out.println("\n  No pet adopted. Goodbye!");
            return;
        }

        gameLoop();
    }

    private void loadGame() {
        pets = SaveManager.load();

        if (pets.isEmpty()) {
            System.out.println("\n  Save file corrupted or empty. Starting new game...");
            startNewGame();
            return;
        }

        activePetIndex = 0;
        System.out.println("\n  Game loaded! Welcome back.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        gameLoop();
    }


    private void gameLoop() {
        while (true) {
            Pet active = pets.get(activePetIndex);

            String event = active.autonomousBehaviour();

            ui.showPetDashboard(active);
            ui.showPetRoster(pets, activePetIndex);

            if (event != null) {
                ui.showEvent(event);
            }

            if (!active.isAlive()) {
                ui.showError("💀 " + active.getName()
                        + " has passed away. Rest in peace...");

                pets.remove(activePetIndex);

                if (pets.isEmpty()) {
                    System.out.println("\n  All pets have passed. Game over.");
                    return;
                }

                activePetIndex = 0;
                continue;
            }

            ui.showInteractionMenu();
            int choice = ui.readInt(0, 8);

            String result = switch (choice) {
                case 1 -> active.feed();
                case 2 -> active.play();
                case 3 -> active.sleep();
                case 4 -> active.administerMedicine();
                case 5 -> {
                    active.makeSound();
                    yield active.makeSound();
                }
                case 6 -> {
                    switchPet();
                    yield null;
                }
                case 7 -> {
                    adoptPet(false);
                    yield null;
                }
                case 8 -> {
                    exitGame(true);
                    yield null;
                }
                case 0 -> {
                    exitGame(false);
                    yield null;
                }
                default -> null;
            };

            if (result != null) {
                ui.showResult(result);
            }

            checkMilestones();
        }
    }
    private void adoptPet(boolean starterMode) {
        int maxAge = pets.stream()
                .mapToInt(Pet::getAge)
                .max()
                .orElse(0);

        ui.showAdoptMenu(maxAge);
        int choice = ui.readInt(0, 4);

        if (choice == 0) {
            return;
        }

        String[] species = {"dog", "cat", "rabbit", "parrot"};
        String chosen = species[choice - 1];

        int required = PetFactory.unlockAgeFor(chosen);

        if (maxAge < required && !starterMode) {
            ui.showError(
                    "That pet is still locked! Raise an existing pet to age "
                            + required + " first."
            );
            return;
        }

        if (starterMode && choice != 1) {
            ui.showError("Only the Dog is available as your first pet!");
            return;
        }

        String name = ui.readPetName();
        Pet newPet = PetFactory.create(chosen, name);

        if (newPet == null) {
            ui.showError("Unknown species.");
            return;
        }

        pets.add(newPet);
        activePetIndex = pets.size() - 1;

        ui.showResult(
                "You adopted " + newPet.getName()
                        + " the " + newPet.getSpecies() + "! 🎊"
        );
    }

    private void switchPet() {
        if (pets.size() == 1) {
            ui.showError("You only have one pet right now.");
            return;
        }

        ui.showPetRoster(pets, activePetIndex);

        System.out.println("\n  Enter the number of the pet to switch to:");

        int idx = ui.readInt(1, pets.size()) - 1;
        activePetIndex = idx;

        ui.showResult(
                "Switched to " + pets.get(activePetIndex).getName() + "!"
        );
    }

    private void checkMilestones() {
        int maxAge = pets.stream()
                .mapToInt(Pet::getAge)
                .max()
                .orElse(0);

        // Cat unlocks at 3, Rabbit at 6, Parrot at 10
        int[][] milestones = {
                {3, 1},
                {6, 2},
                {10, 3}
        };

        String[] labels = {
                "Cat 🐱",
                "Rabbit 🐇",
                "Parrot 🦜"
        };

        for (int i = 0; i < milestones.length; i++) {
            int threshold = milestones[i][0];

            if (maxAge >= threshold) {
                Pet template = PetFactory.create(
                        ALL_SPECIES[milestones[i][1]], "_"
                );

                if (template != null && !template.isUnlocked()) {

                    boolean alreadyAdopted = pets.stream()
                            .anyMatch(p -> p.getSpecies()
                                    .equalsIgnoreCase(
                                            ALL_SPECIES[milestones[i][1]]
                                    ));

                    ui.showMilestone(
                            "You unlocked the " + labels[i]
                                    + "! Adopt one via the menu."
                    );
                }
            }
        }
    }

    private void exitGame(boolean save) {
        if (save && !pets.isEmpty()) {
            boolean ok = SaveManager.save(pets);
            ui.showSaveConfirmation(ok);
        }

        System.out.println(
                "\n  Thanks for playing PetVerse! Goodbye 🐾\n"
        );

        System.exit(0);
    }
}
