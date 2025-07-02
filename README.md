# Simple Expense Tracker
## About the Project
This is a terminal-based Java expense tracker that shows expense trends and reports.

## How to Set Up and Run

### 1. Install Java and Git

### 2. Set Java PATH
Follow this link : https://www.geeksforgeeks.org/java/how-to-set-java-path-in-windows-and-linux/

### 3. Clone the Repository

#### Command : git clone https://github.com/YashSejpal/ExpenseTracker "local-destination"
#### Command : cd "local-destination"

### 4. Compile the Project
#### Windows Command : javac -cp "src/json-20231013.jar;src/jfreechart-1.5.4.jar;src/jcommon-1.0.24.jar" -d out src/*.java
#### MacOS/Linux Command : javac -cp "src/json-20231013.jar:src/jfreechart-1.5.4.jar:src/jcommon-1.0.24.jar" -d out src/*.java"

### 5. Run the Application
#### Windows Command : java -cp "src/json-20231013.jar;src/jfreechart-1.5.4.jar;src/jcommon-1.0.24.jar;out" Main
#### MacOS/Linux Command : java -cp "src/json-20231013.jar:src/jfreechart-1.5.4.jar:src/jcommon-1.0.24.jar:out" Main
