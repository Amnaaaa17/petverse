package petverse.model;

import java.io.Serializable;

public abstract class Pet implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private int hunger;
    private int happiness;
    private int health;
    private int cycleCount;
    private boolean isUnlocked;
    private boolean isSick;
    private String status;

    private static final int MAX_STAT = 100;
    private static final int MIN_STAT = 0;
    private static final int AGE_CYCLES = 5;

    public Pet(String name) {
        this.name = name;
        this.age = 0;
        this.hunger = 30;
        this.happiness = 70;
        this.health = 80;
        this.cycleCount = 0;
        this.isUnlocked = false;
        this.isSick = false;
        this.status = "Ready to play!";
    }
    public abstract String getSpecies();

    public abstract String getAsciiArt();

    public abstract String makeSound();

    public abstract String playDescription();

    public abstract int unlockAge();

    public String feed() {
        if (isSick) {
            return name + " is too sick to eat! Administer medicine first.";
        }

        adjustHunger(-25);
        adjustHappiness(+10);
        status = "Nom nom nom!";
        tickCycle();

        return name
                + " enjoyed the meal! (-25 hunger, +10 happiness)";
    }

    public String play() {
        if (isSick) {
            return name + " is too sick to play! Administer medicine first.";
        }

        adjustHappiness(+20);
        adjustHunger(+15);
        status = playDescription();
        tickCycle();

        return name
                + " had a blast! (+20 happiness, +15 hunger)";
    }

    public String sleep() {
        adjustHealth(+20);
        adjustHunger(+10);
        status = "Zzz...";
        tickCycle();

        return name + " had a great nap! (+20 health)";
    }

    public String administerMedicine() {
        if (!isSick) {
            return name + " is not sick right now.";
        }

        isSick = false;
        adjustHealth(+30);
        status = "Feeling better!";

        return name + " has recovered! (+30 health)";
    }

    public String autonomousBehaviour() {

        if (hunger >= 85) {
            adjustHunger(-15);
            status = "Scavenging for food...";

            return "🍖 " + name
                    + " was too hungry and found some food!";
        }

        double roll = Math.random();

        if (roll < 0.05) {
            isSick = true;
            adjustHealth(-10);
            status = "Feeling sick...";

            return "🤒 Oh no! " + name
                    + " got sick! Use MEDICINE to help.";

        } else if (roll < 0.10) {
            adjustHappiness(+15);
            status = "Found a treat!";

            return "🍬 " + name
                    + " found a treat! (+15 happiness)";

        } else if (roll < 0.15) {
            adjustHappiness(+10);
            status = "Playing with a toy!";

            return "🧸 " + name
                    + " found a toy and started playing! (+10 happiness)";

        } else if (roll < 0.18 && happiness < 40) {
            status = "Bored...";

            return "😐 " + name
                    + " looks bored. Try playing!";

        } else if (roll < 0.20 && health < 50) {
            adjustHealth(+10);
            status = "Resting...";

            return "💤 " + name
                    + " decided to take a nap. (+10 health)";
        }

        return null;
    }
    private void tickCycle() {
        cycleCount++;

        if (cycleCount >= AGE_CYCLES) {
            age++;
            cycleCount = 0;
        }

        adjustHunger(+5);
        adjustHappiness(-3);
        adjustHealth(-2);
    }

    protected void adjustHunger(int delta) {
        hunger = clamp(hunger + delta);
    }

    protected void adjustHappiness(int delta) {
        happiness = clamp(happiness + delta);
    }

    protected void adjustHealth(int delta) {
        health = clamp(health + delta);
    }

    private int clamp(int val) {
        return Math.max(MIN_STAT, Math.min(MAX_STAT, val));
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isCritical() {
        return health < 20 || hunger > 90;
    }

    public String getMoodEmoji() {
        if (isSick) return "🤒";
        if (happiness >= 75) return "😄";
        if (happiness >= 50) return "🙂";
        if (happiness >= 25) return "😐";
        return "😢";
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getHealth() {
        return health;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public boolean isSick() {
        return isSick;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnlocked(boolean b) {
        this.isUnlocked = b;
    }

    public void setStatus(String s) {
        this.status = s;
    }
    public static String statBar(String label, int value, int max) {
        int filled = (int) Math.round((double) value / max * 20);

        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < 20; i++) {
            bar.append(i < filled ? "█" : "░");
        }

        bar.append("] ");
        bar.append(value).append("/").append(max);

        return String.format("%-12s %s", label + ":", bar);
    }

    @Override
    public String toString() {
        return String.format(
                "%s the %s (Age: %d)",
                name,
                getSpecies(),
                age
        );
    }
}
