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
 * Storage Keeper NPC dialog state
 * State classes are pure data containers - ONLY objects, NO methods
 */
@State
@Getter
@Slf4j
public class StorageKeeperState {
    // State name is automatically derived: "StorageKeeper"

    private final StateImage storageKeeperDialog;
    private final StateString close;  // Special key: ESC
    private final StateString storage; // Key to open storage '2'

    public StorageKeeperState() {
        log.info("Initializing StorageKeeperState");

        // Define regions for mock mode (optional)
        Region dialogRegion = new Region(960, 400, 300, 200);

        // Storage keeper dialog identifier
        storageKeeperDialog = new StateImage.Builder()
            .addPatterns("storage-keeper/storage-keeper")
            .setName("Storage Keeper Dialog")
            .withActionHistory(MockActionHistoryFactory.reliableButton(dialogRegion))
            .build();

        // Special key for pressing Escape to close
        close = new StateString.Builder()
            .setString(Key.ESC)  // Uses SikuliX Key.ESC constant
            .setName("Escape Key")
            .build();

        storage = new StateString.Builder()
                .setString("2")  // Uses SikuliX Key.ESC constant
                .setName("open storage")
                .build();
    }
}