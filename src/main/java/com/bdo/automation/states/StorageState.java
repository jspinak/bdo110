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
 * Storage window state
 * State classes are pure data containers - ONLY objects, NO methods
 */
@State
@Getter
@Slf4j
public class StorageState {
    // State name is automatically derived: "Storage"

    private final StateImage storageWindow;
    private final StateString close;  // Special key: ESC

    public StorageState() {
        log.info("Initializing StorageState");

        // Define regions for mock mode (optional)
        Region windowRegion = new Region(500, 200, 920, 680);

        // Storage window identifier
        storageWindow = new StateImage.Builder()
            .addPatterns("storage/storage")
            .setName("Storage Window")
            .withActionHistory(MockActionHistoryFactory.reliableButton(windowRegion))
            .build();

        // Special key for pressing Escape to close
        close = new StateString.Builder()
            .setString(Key.ESC)  // Uses SikuliX Key.ESC constant
            .setName("Escape Key")
            .build();
    }
}