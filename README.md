# BDO Automation with Brobot

This is a Black Desert Online automation application built using the Brobot framework.

## Project Structure

```
bdo110/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bdo/automation/
│   │   │       ├── states/          # Game state classes
│   │   │       ├── transitions/     # State transition logic
│   │   │       ├── processes/       # Automation processes
│   │   │       └── BdoAutomationApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── images/              # Game screenshots for pattern matching
│   │           ├── main-screen/
│   │           ├── black-spirits-adventure/
│   │           ├── storage-keeper/
│   │           ├── storage/
│   │           ├── amount/
│   │           ├── processing/
│   │           ├── inventory/
│   │           └── items/
└── pom.xml
```

## Setup Instructions

### 1. Prerequisites
- Java 17 or higher
- Maven 3.6+
- Black Desert Online installed and running

### 2. Add Game Images

You need to capture screenshots of the game UI elements and place them in the appropriate folders:

1. **main-screen/** - Main game screen images
   - `die.png` - Black Spirit's Adventure die icon
   - `processing.png` - Processing UI element

2. **black-spirits-adventure/** - Black Spirit's Adventure window
   - `abholen.png` - Collect reward button
   - `close.png` - Close button

3. **storage-keeper/** - Storage Keeper NPC dialog
   - `storage-keeper.png` - Storage Keeper dialog identifier
   - `close.png` - Close button

4. **storage/** - Storage window
   - `storage.png` - Storage window identifier
   - `close.png` - Close button

5. **amount/** - Amount input dialog
   - `eingabe.png` - Input field
   - `enter.png` - Confirm button
   - `close.png` - Close button

6. **processing/** - Processing window
   - `processing.png` - Processing window identifier
   - `mass-production.png` - Mass Production button
   - `grind.png` - Grind button
   - `close.png` - Close button

7. **inventory/** - Inventory window
   - `inventory.png` - Inventory window identifier
   - `search-inventory.png` - Search field
   - `close.png` - Close button

8. **items/** - Item images
   - `corn.png` - Corn item
   - `corn-searched.png` - Corn item when searched

### 3. Build the Project

```bash
cd bdo110
mvn clean package
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR directly:

```bash
java -jar target/bdo-automation-1.0.0.jar
```

## Usage

When you run the application, you'll see an interactive menu:

```
===================================
    BDO AUTOMATION SYSTEM
===================================
1. Run GetGift Process
2. Open Inventory
3. Open Processing
4. Open Storage
5. Return to Main Screen
6. Check Current State
0. Exit
===================================
```

### Available Functions

1. **GetGift Process**: Automatically collects Black Spirit's Adventure rewards
2. **Open Inventory**: Opens the inventory window (press 'i')
3. **Open Processing**: Opens the processing window (press 'l')
4. **Open Storage**: Opens storage via Storage Keeper NPC
5. **Return to Main Screen**: Closes all windows and returns to main screen
6. **Check Current State**: Detects which game window is currently active

## State Transitions

The application implements the following state transitions:

- **Main Screen → Black Spirit's Adventure**: Click die icon
- **Main Screen → Storage Keeper**: Press 'r' (talk)
- **Storage Keeper → Storage**: Press '2'
- **Main Screen → Processing**: Press 'l'
- **Main Screen → Inventory**: Press 'i'
- **Any State → Main Screen**: Press ESC (may need multiple times)

## Configuration

Edit `src/main/resources/application.properties` to adjust:

- Screen resolution
- Pattern matching similarity threshold
- Action timeouts
- Logging levels
- Mock mode for testing

## Troubleshooting

1. **Images not found**: Ensure screenshots are in the correct folders with exact names
2. **Low similarity matches**: Adjust `brobot.action.default.similarity` in application.properties
3. **Wrong screen resolution**: Update resolution settings in application.properties
4. **Actions too fast/slow**: Adjust pause settings in application.properties

## Development

To add new states or processes:

1. Create a new State class in `com.bdo.automation.states`
2. Add transitions in `TransitionManager`
3. Create automation processes in `com.bdo.automation.processes`
4. Update `BdoAutomationRunner` to include new menu options

## Testing

Run in mock mode for testing without the actual game:

```properties
# In application.properties
brobot.mock=true
```

This allows testing the application logic without requiring the game to be running.