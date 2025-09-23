package com.bdo.automation.instructions;

import io.github.jspinak.brobot.logging.correlation.ActionSessionManager;
import com.bdo.automation.states.InventoryState;
import com.bdo.automation.states.ItemsState;
import com.bdo.automation.states.ProcessingState;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.ActionType;
import io.github.jspinak.brobot.navigation.monitoring.StateAwareScheduler;
import io.github.jspinak.brobot.navigation.transition.StateNavigator;
import io.github.jspinak.brobot.statemanagement.StateMemory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CornProcessor {
    private final StateAwareScheduler stateAwareScheduler;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    private final InventoryState inventory;
    private final ItemsState items;
    private final ProcessingState processing;
    private final Action action;
    private final StateMemory stateMemory;
    private final StateNavigator navigation;
    private final ActionSessionManager actionSession;

    public void makeCornFlour() {
        if (!navigation.openState("Processing")) {
            log.error("Could not open Processing state");
            return;
        }
        startAutomation();
    }

    public void startAutomation() {
        // Configure what states must be active
        StateAwareScheduler.StateCheckConfiguration config =
                new StateAwareScheduler.StateCheckConfiguration.Builder()
                        .withRequiredStates(List.of("Processing", "Inventory"))
                        .withRebuildOnMismatch(true)
                        .build();

        // Schedule your task with automatic state checking
        ScheduledFuture<?> future = stateAwareScheduler.scheduleWithStateCheck(
                scheduler,
                this::myAutomationTask,  // Your task
                config,                   // State requirements
                0,                       // Initial delay
                10,                      // Period
                TimeUnit.SECONDS
        );
    }

    private boolean myAutomationTask() {
        // This runs AFTER states are validated
        log.info("Running automation with verified states");
        if (stateMemory.getActiveStateNames().contains("Processing") &&
                stateMemory.getActiveStateNames().contains("Inventory")) {
            return startGrinding();
        } else {
            log.warn("Required states are not active. Current states: " + stateMemory.getActiveStateNames());
            return false;
        }
    }

    private boolean startGrinding() {
        // Use session for correlated logging
        actionSession.nextAction();
        action.click(processing.getGrindButton());

        actionSession.nextAction();
        if (!action.click(inventory.getSearchField()).isSuccess()) return false;

        actionSession.nextAction();
        if (!action.type("mais\n").isSuccess()) return false;  // \n adds Enter key

        actionSession.nextAction();
        if (!action.perform(ActionType.RIGHT_CLICK, items.getCornSearched()).isSuccess()) return false;

        actionSession.nextAction();
        if (!action.click(processing.getMassProductionButton()).isSuccess()) return false;

        return true;
    }

}
