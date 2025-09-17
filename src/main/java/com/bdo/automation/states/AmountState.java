package com.bdo.automation.states;

import io.github.jspinak.brobot.annotations.State;
import io.github.jspinak.brobot.model.element.Region;
import io.github.jspinak.brobot.model.state.StateImage;
import io.github.jspinak.brobot.model.state.StateString;
import io.github.jspinak.brobot.tools.testing.mock.history.MockActionHistoryFactory;
import org.sikuli.script.Key;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Amount input dialog state
 * State classes are pure data containers - ONLY objects, NO methods
 */
@State
@Getter
@Slf4j
public class AmountState {
    // State name is automatically derived: "Amount"

    private final StateImage eingabe;
    private final StateString enter;  // Special key: ENTER
    private final StateString close;   // Special key: ESC

    public AmountState() {
        log.info("Initializing AmountState");

        // Define regions for mock mode (optional)
        Region eingabeRegion = new Region(960, 540, 200, 40);

        // Eingabe (input) field
        eingabe = new StateImage.Builder()
            .addPatterns("amount/eingabe")
            .setName("Eingabe Form Title")
            .withActionHistory(MockActionHistoryFactory.reliableButton(eingabeRegion))
            .build();

        // Special key for pressing Enter to confirm
        enter = new StateString.Builder()
            .setString(Key.ENTER)  // Uses SikuliX Key.ENTER constant
            .setName("Enter Key")
            .build();

        // Special key for pressing Escape to close
        close = new StateString.Builder()
            .setString(Key.ESC)  // Uses SikuliX Key.ESC constant
            .setName("Escape Key")
            .build();
    }
}