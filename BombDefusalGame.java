import java.util.Random;

public class BombDefusalGame {

    private static final String[] WIRES = {"Red", "Blue", "Green", "Yellow", "Black"};
    private static final int MAX_ATTEMPTS = 1;
    private static final long TIME_LIMIT_SECONDS = 30; // 30 seconds per round

    private int safeWireIndex;
    private int attemptsLeft;
    private boolean bombDefused;
    private String lastMessage;
    private String lastHint;
    private String story;
    private long deadlineEpochSeconds; // when time runs out

    public BombDefusalGame() {
        resetGame();
    }

    public void resetGame() {
        attemptsLeft = MAX_ATTEMPTS;
        bombDefused = false;
        lastMessage = "";
        lastHint = "";
        pickSafeWire();
        generateStory();
        // set deadline: TIME_LIMIT_SECONDS from now
        deadlineEpochSeconds = System.currentTimeMillis() / 1000 + TIME_LIMIT_SECONDS;
    }

    private void pickSafeWire() {
        Random rand = new Random();
        safeWireIndex = rand.nextInt(WIRES.length);
    }

    private void generateStory() {
        // Pure scenario-based stories (10+ per color)
        Random rand = new Random();
        int variant = rand.nextInt(10); // 0–9 for variety

        String color = WIRES[safeWireIndex];

        switch (color) {
            case "Red":
                switch (variant) {
                    case 0: story = "The emergency protocol states: in case of fire, cut the wire associated with danger."; break;
                    case 1: story = "A note from the bomb maker reads: 'The safe wire is the same color as my favorite warning sign.'"; break;
                    case 2: story = "During testing, the only wire that didn't trigger the alarm was the one labeled 'critical hazard.'"; break;
                    case 3: story = "The technician whispered: 'When you see the color of stop signs, you'll know which one to cut.'"; break;
                    case 4: story = "In the manual, the chapter on failures highlights the wire that represents urgency."; break;
                    case 5: story = "A survivor recalled: 'the bomb squad always emphasized the wire that means stop.'"; break;
                    case 6: story = "The bomb's design includes a wire that matches the color of the highest alert level."; break;
                    case 7: story = "An intercepted message said: 'the safe wire is the color of blood and fire.'"; break;
                    case 8: story = "The training simulation always uses the same color for the safe wire when the threat level is maximum."; break;
                    case 9: story = "The defusal guide says: 'trust the wire that symbolizes immediate danger.'"; break;
                }
                break;
            case "Blue":
                switch (variant) {
                    case 0: story = "The operative's memo says: 'when in doubt, choose the wire that represents calm.'"; break;
                    case 1: story = "During the last drill, the safe wire was described as 'the color of the sea on a clear day.'"; break;
                    case 2: story = "A cryptic clue reads: 'the correct wire matches the color of the sky at noon.'"; break;
                    case 3: story = "The bomb maker's journal mentions: 'I always mark the safe wire with the color of tranquility.'"; break;
                    case 4: story = "The support team advised: 'pick the wire that reminds you of peaceful waters.'"; break;
                    case 5: story = "In the manual, the safe wire is likened to the color of logic and reason."; break;
                    case 6: story = "An email from the creator says: 'the safe wire is the color of my favorite uniform.'"; break;
                    case 7: story = "The defusal expert noted: 'the safe wire is the one that feels cold to the touch.'"; break;
                    case 8: story = "The schematic labels the safe wire with the color of stability and trust."; break;
                    case 9: story = "A riddle left behind: 'I am the color of silence and depth—cut me to survive.'"; break;
                }
                break;
            case "Green":
                switch (variant) {
                    case 0: story = "The protocol for success is clear: cut the wire that means go."; break;
                    case 1: story = "A veteran technician once said: 'the safe wire always matches the color of life.'"; break;
                    case 2: story = "The bomb maker's note: 'the safe wire is the same color as the forest where I trained.'"; break;
                    case 3: story = "During the briefing, they emphasized: 'choose the wire that represents safety.'"; break;
                    case 4: story = "The defusal guide states: 'the safe wire is the color of growth and renewal.'"; break;
                    case 5: story = "An intercepted log says: 'the correct wire is the one that signals all clear.'"; break;
                    case 6: story = "The training manual uses the color of nature to indicate the correct wire."; break;
                    case 7: story = "A clue reads: 'the wire to cut is the color of a meadow in spring.'"; break;
                    case 8: story = "The bomb's creator hinted: 'the safe wire is the color of my homeland's flag.'"; break;
                    case 9: story = "The expert's advice: 'trust the wire that means proceed.'"; break;
                }
                break;
            case "Yellow":
                switch (variant) {
                    case 0: story = "The warning in the manual is clear: the safe wire is the color of caution."; break;
                    case 1: story = "A message from the bomb maker: 'I always mark the safe wire with the color of the sun.'"; break;
                    case 2: story = "During the exercise, the safe wire was described as 'the color of high-visibility gear.'"; break;
                    case 3: story = "The technician said: 'the safe wire is the one that stands out in the dark.'"; break;
                    case 4: story = "The defusal protocol highlights the wire that represents attention."; break;
                    case 5: story = "A riddle left at the scene: 'I am the color of warning—cut me to defuse.'"; break;
                    case 6: story = "The bomb maker's journal: 'the safe wire is the color of my favorite caution tape.'"; break;
                    case 7: story = "The expert noted: 'the safe wire is the one that signals proceed with care.'"; break;
                    case 8: story = "The manual says: 'the safe wire matches the color of warning signs.'"; break;
                    case 9: story = "A clue reads: 'the wire to cut is the color of a school bus.'"; break;
                }
                break;
            case "Black":
                switch (variant) {
                    case 0: story = "The operative's final words: 'the safe wire is the one that blends into the shadows.'"; break;
                    case 1: story = "A cryptic note reads: 'the correct wire is the color of night.'"; break;
                    case 2: story = "The bomb maker's diary: 'I always hide the safe wire in plain sight.'"; break;
                    case 3: story = "The defusal expert advised: 'choose the wire that absorbs all light.'"; break;
                    case 4: story = "During the briefing, they hinted: 'the safe wire is the color of stealth.'"; break;
                    case 5: story = "A riddle left behind: 'I am the color of mystery—cut me to survive.'"; break;
                    case 6: story = "The manual states: 'the safe wire is the one that doesn't reflect.'"; break;
                    case 7: story = "The bomb maker's note: 'the safe wire is the color of my favorite coat.'"; break;
                    case 8: story = "The technician whispered: 'the safe wire is the color of the void.'"; break;
                    case 9: story = "The defusal guide says: 'trust the wire that represents the unknown.'"; break;
                }
                break;
            default:
                story = "You stare at the bomb, trying to remember your training...";
                break;
        }
    }

    public String[] getWires() {
        return WIRES;
    }

    public String getStory() {
        return story;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isBombDefused() {
        return bombDefused;
    }

    public boolean isGameOver() {
        return bombDefused || attemptsLeft <= 0 || timeLeftSeconds() <= 0;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastHint() {
        return lastHint;
    }

    public void checkWire(int choiceIndex) {
        if (isGameOver()) {
            return;
        }

        if (choiceIndex == safeWireIndex) {
            bombDefused = true;
            lastMessage = "Correct wire! BOMB DEFUSED! You saved the city!";
            lastHint = "";
        } else {
            attemptsLeft--;
            lastMessage = "Wrong wire!";
            if (attemptsLeft > 0 && timeLeftSeconds() > 0) {
                lastHint = getHint(choiceIndex);
            } else if (timeLeftSeconds() <= 0) {
                lastHint = "Time's up! The bomb explodes!";
            } else {
                lastHint = "The bomb explodes! The safe wire was: " + WIRES[safeWireIndex] + ".";
            }
        }
    }

    private String getHint(int choiceIndex) {
        if (safeWireIndex > choiceIndex) {
            return "You are close... try a wire later in the list.";
        } else if (safeWireIndex < choiceIndex) {
            return "Too far... try an earlier wire.";
        } else {
            return "";
        }
    }

    public long timeLeftSeconds() {
        long now = System.currentTimeMillis() / 1000;
        long remaining = deadlineEpochSeconds - now;
        return remaining > 0 ? remaining : 0;
    }
}
