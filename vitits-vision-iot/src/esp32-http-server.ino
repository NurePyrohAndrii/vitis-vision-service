#include <WiFi.h>
#include <WiFiClient.h>
#include <WebServer.h>
#include <LiquidCrystal_I2C.h>
#include <uri/UriBraces.h>

// Device ID
const int id = 1;

// Struct to store network information
struct NetworkInfo {
  String ssid;
  String password;
};

// Network information global variables and constants
const int MAX_NETWORKS = 10;
NetworkInfo availableNetworks[MAX_NETWORKS];
int numNetworks = 0;

// Default s erial communication properties if the user does not specify
int serialBaudRate = 115200;

// LCD display properties
LiquidCrystal_I2C lcd(0x27, 20, 4);


////////////////
// REMOVE THIS LINE AFTER REMOVING LED FROM THE SCHEMATIC
const int ledPin = 26;
////////////////


// Web server setup
WebServer server(80);

// Measure time (in milliseconds) passed as a parameter in the URL of {id}/measure/{} endpoint
int measureTime;

// Function to handle HTTP requests
void handleRequest() {
  String message = "\"Hello from ESP32! My ID is " + String(id) + ", you passed measure time: " + String(measureTime/1000) + " seconds." + "\"";
  server.send(200, "application/json", "{\"result\":" + message + "}");
}

// Function to connect to WiFi
void connectToWiFi() {
  Serial.println("Scanning for available networks...");

  // Scan for available networks
  int numNetworks = WiFi.scanNetworks();

  if (numNetworks == 0) {
    Serial.println("No networks found. Please check your WiFi module.");
    while (true) {
      delay(1000);
    }
  }

  Serial.println("Available networks:");
  for (int i = 0; i < numNetworks && i < MAX_NETWORKS; i++) {
    availableNetworks[i].ssid = WiFi.SSID(i);
    availableNetworks[i].password = "";
    Serial.println(String(i) + "#" + availableNetworks[i].ssid);
  }

  // Let the user or device choose a network to connect to
  int selectedNetwork = -1;
  Serial.println("Enter the number of the network you want to connect to:");
  while (selectedNetwork < 0 || selectedNetwork >= numNetworks) {
    if (Serial.available() > 0) {
      selectedNetwork = Serial.parseInt();
    }
  }

  Serial.println("Connecting to " + availableNetworks[selectedNetwork].ssid);

  // Connect to the selected network
  WiFi.begin(availableNetworks[selectedNetwork].ssid.c_str(),
             availableNetworks[selectedNetwork].password.c_str());
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting...");
  }

  Serial.println("Connected to WiFi");
  Serial.println("IP address: " + WiFi.localIP().toString());
}

// Function to determine the baud rate that the user wants to use
void determineBaudRate() {
  Serial.println("Enter the desired baud rate:");

  while (!Serial.available()) {
    // Wait for user input
  }

  // Read the user input and set the baud rate
  serialBaudRate = Serial.parseInt();
  Serial.begin(serialBaudRate);

  Serial.print("Baud rate set to ");
  Serial.println(serialBaudRate);
}

// Function to initialize the LCD display
void initializeLCD() {
  lcd.init();
  lcd.backlight();
}

// Function to display a message on the LCD display
void displayMessage(const String& message) {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.println(message);
}

void setup() {
  Serial.begin(serialBaudRate);
  delay(1000);

  determineBaudRate();
  Serial.begin(serialBaudRate);

  initializeLCD();
  displayMessage("Connecting to WiFi...");

  connectToWiFi();

  displayMessage("Connected to WiFi");

  pinMode(ledPin, OUTPUT);

  server.on(UriBraces("/" + String(id) + "/measure/{}"), []() {
    try {
      if (server.pathArg(0).toInt() <= 0) {
        server.send(400, "application/json", "{\"error\":\"Invalid measure time\"}");
        return;
      }
      measureTime = server.pathArg(0).toInt() * 1000;
    } catch (const std::exception& e) {
      server.send(400, "application/json", "{\"error\":\"Invalid measure time\"}");
      return;
    }

    handleRequest();
  });

  server.begin();
}

void loop() {
  server.handleClient();
}