package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.click.ClickOptions;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.action.basic.type.TypeOptions;
import io.github.jspinak.brobot.state.annotations.IncomingTransition;
import io.github.jspinak.brobot.state.annotations.OutgoingTransition;
import io.github.jspinak.brobot.state.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransitionSet(state = MainScreenState.class)
@RequiredArgsConstructor
@Slf4j
public class MainScreenTransitions {

    private final MainScreenState mainScreenState;
    private final Action action;

    @OutgoingTransition(to = BlackSpiritsAdventureState.class, priority = 1)
    public boolean toBlackSpiritsAdventure() {
        ClickOptions clickOptions = new ClickOptions.Builder()
            .withBeforeActionLog("Opening Black Spirit's Adventure...")
            .withSuccessLog("Black Spirit's Adventure opened successfully")
            .withFailureLog("Failed to open Black Spirit's Adventure - die icon not found")
            .setPauseBeforeBegin(0.5)
            .setPauseAfterEnd(1.5)
            .build();

        return action.click(mainScreenState.getDie(), clickOptions).isSuccess();
    }

    @OutgoingTransition(to = StorageKeeperState.class, priority = 2)
    public boolean toStorageKeeper() {
        TypeOptions typeOptions = new TypeOptions.Builder()
            .withBeforeActionLog("Opening Storage Keeper dialog...")
            .withSuccessLog("Storage Keeper dialog opened")
            .withFailureLog("Failed to open Storage Keeper - make sure you're near NPC")
            .setPauseBeforeBegin(0.5)
            .setPauseAfterEnd(1.0)
            .build();

        // Press 'r' for talk
        return action.type("r", typeOptions).isSuccess();
    }

    @OutgoingTransition(to = ProcessingState.class, priority = 3)
    public boolean toProcessing() {
        TypeOptions typeOptions = new TypeOptions.Builder()
            .withBeforeActionLog("Opening Processing window...")
            .withSuccessLog("Processing window opened")
            .withFailureLog("Failed to open Processing window")
            .setPauseBeforeBegin(0.5)
            .setPauseAfterEnd(1.0)
            .build();

        // Press 'l' for processing
        return action.type("l", typeOptions).isSuccess();
    }

    @OutgoingTransition(to = InventoryState.class, priority = 4)
    public boolean toInventory() {
        TypeOptions typeOptions = new TypeOptions.Builder()
            .withBeforeActionLog("Opening Inventory...")
            .withSuccessLog("Inventory opened")
            .withFailureLog("Failed to open Inventory")
            .setPauseBeforeBegin(0.5)
            .setPauseAfterEnd(1.0)
            .build();

        // Press 'i' for inventory
        return action.type("i", typeOptions).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .withBeforeActionLog("Verifying arrival at MainScreen...")
            .withSuccessLog("Successfully arrived at MainScreen")
            .withFailureLog("Failed to verify MainScreen - check if game is running")
            .setWaitTime(5.0)
            .setPauseAfterEnd(0.5)
            .build();

        // Check for die or processing icon as indicators of main screen
        boolean found = action.find(mainScreenState.getDie(), findOptions).isSuccess() ||
                       action.find(mainScreenState.getProcessing(), findOptions).isSuccess();

        return found;
    }
}