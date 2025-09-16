package com.bdo.automation.runner;

import com.bdo.automation.processes.GetGiftProcess;
import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.click.ClickOptions;
import io.github.jspinak.brobot.action.basic.type.TypeOptions;
import io.github.jspinak.brobot.navigation.Navigation;
import io.github.jspinak.brobot.config.core.FrameworkSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Main execution class for BDO automation
 * This runner provides an interactive console menu for executing various automation tasks
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test") // Don't run in test profile
public class BdoAutomationRunner implements CommandLineRunner {

    private final Navigation navigation;
    private final Action action;
    private final GetGiftProcess getGiftProcess;

    // State dependencies for direct actions
    private final MainScreenState mainScreenState;
    private final InventoryState inventoryState;
    private final ProcessingState processingState;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        log.info("Starting BDO Automation System");
        log.info("Mock mode: {}", FrameworkSettings.mock);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        runGetGiftProcess();
                        break;
                    case "2":
                        openInventory();
                        break;
                    case "3":
                        openProcessing();
                        break;
                    case "4":
                        openStorage();
                        break;
                    case "5":
                        returnToMainScreen();
                        break;
                    case "6":
                        checkCurrentState();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                log.error("Error executing command: " + e.getMessage(), e);
                System.out.println("An error occurred: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        log.info("BDO Automation System shutting down");
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("\n===================================");
        System.out.println("    BDO AUTOMATION SYSTEM");
        System.out.println("===================================");
        System.out.println("1. Run GetGift Process");
        System.out.println("2. Open Inventory");
        System.out.println("3. Open Processing");
        System.out.println("4. Open Storage");
        System.out.println("5. Return to Main Screen");
        System.out.println("6. Check Current State");
        System.out.println("0. Exit");
        System.out.println("===================================");
        System.out.print("Enter your choice: ");
    }

    private void runGetGiftProcess() {
        System.out.println("\nExecuting GetGift Process...");
        boolean success = getGiftProcess.executeWithRetry(2);

        if (success) {
            System.out.println("✓ Gift collection completed successfully!");
        } else {
            System.out.println("✗ Failed to collect gift. Please check if the game is running and the die icon is visible.");
        }
    }

    private void openInventory() {
        System.out.println("\nOpening Inventory...");

        if (navigation.openState("Inventory")) {
            System.out.println("✓ Inventory opened successfully!");

            System.out.print("Search for an item? (y/n): ");
            String searchChoice = scanner.nextLine().trim();

            if (searchChoice.equalsIgnoreCase("y")) {
                System.out.print("Enter item name: ");
                String itemName = scanner.nextLine().trim();

                // Click search field
                ClickOptions clickOptions = new ClickOptions.Builder()
                    .setPauseBeforeBegin(0.5)
                    .setPauseAfterEnd(0.5)
                    .build();
                action.click(inventoryState.getSearchField(), clickOptions);

                // Type search term
                TypeOptions typeOptions = new TypeOptions.Builder()
                    .setPauseBeforeBegin(0.5)
                    .setPauseAfterEnd(0.5)
                    .build();
                action.type(itemName, typeOptions);

                System.out.println("Searching for: " + itemName);
            }
        } else {
            System.out.println("✗ Failed to open inventory. Make sure you're on the main screen.");
        }
    }

    private void openProcessing() {
        System.out.println("\nOpening Processing Window...");

        if (navigation.openState("Processing")) {
            System.out.println("✓ Processing window opened successfully!");

            System.out.println("Select processing type:");
            System.out.println("1. Mass Production");
            System.out.println("2. Grind");
            System.out.print("Choice: ");

            String processingChoice = scanner.nextLine().trim();

            ClickOptions clickOptions = new ClickOptions.Builder()
                .setPauseBeforeBegin(0.5)
                .setPauseAfterEnd(1.0)
                .build();

            switch (processingChoice) {
                case "1":
                    action.click(processingState.getMassProductionButton(), clickOptions);
                    System.out.println("Selected Mass Production");
                    break;
                case "2":
                    action.click(processingState.getGrindButton(), clickOptions);
                    System.out.println("Selected Grind");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } else {
            System.out.println("✗ Failed to open processing. Make sure you're on the main screen.");
        }
    }

    private void openStorage() {
        System.out.println("\nOpening Storage...");

        System.out.println("Step 1: Opening Storage Keeper dialog...");
        if (navigation.openState("StorageKeeper")) {
            System.out.println("✓ Storage Keeper dialog opened");

            System.out.println("Step 2: Opening Storage window...");
            if (navigation.openState("Storage")) {
                System.out.println("✓ Storage window opened successfully!");
            } else {
                System.out.println("✗ Failed to open storage window");
            }
        } else {
            System.out.println("✗ Failed to open Storage Keeper. Make sure you're near a storage NPC.");
        }
    }

    private void returnToMainScreen() {
        System.out.println("\nReturning to Main Screen...");

        if (navigation.openState("MainScreen")) {
            System.out.println("✓ Successfully returned to main screen!");
        } else {
            System.out.println("✗ Could not verify main screen state");
        }
    }

    private void checkCurrentState() {
        System.out.println("\nChecking Current State...");

        // Use navigation to get current state
        log.info("Checking current state - this feature requires additional implementation");

        // For now, try to find unique elements from each state
        System.out.println("Attempting to identify current state...");

        if (action.find(mainScreenState.getDie()).isSuccess()) {
            System.out.println("✓ Main Screen appears to be active");
        } else {
            System.out.println("Checking other states... (additional implementation needed)");
        }
    }
}