package com.bdo.automation.states;

import io.github.jspinak.brobot.primatives.region.Region;
import io.github.jspinak.brobot.state.annotations.State;
import io.github.jspinak.brobot.stateStructure.model.state.StateImage;
import io.github.jspinak.brobot.model.state.StateString;
import io.github.jspinak.brobot.tools.testing.mock.history.MockActionHistoryFactory;
import org.sikuli.script.Key;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Black Spirit's Adventure mini-game state
 * State classes are pure data containers - ONLY objects, NO methods
 */
@State
@Getter
@Slf4j
public class BlackSpiritsAdventureState {
    // State name is automatically derived: "BlackSpiritsAdventure"

    private final StateImage abholen;
    private final StateString close;  // Special key: ESC

    public BlackSpiritsAdventureState() {
        log.info("Initializing BlackSpiritsAdventureState");

        // Define regions for mock mode (optional)
        Region abholenRegion = new Region(960, 600, 150, 50);

        // Abholen (collect) button
        abholen = new StateImage.Builder()
            .addPatterns("black-spirits-adventure/abholen")
            .setName("Abholen Button")
            .withActionHistory(MockActionHistoryFactory.reliableButton(abholenRegion))
            .build();

        // Special key for pressing Escape to close
        close = new StateString.Builder()
            .withString(Key.ESC)  // Uses SikuliX Key.ESC constant
            .setName("Escape Key")
            .build();
    }
}