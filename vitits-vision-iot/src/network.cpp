#include "network.h"
#include "lcd.h"

int serialBaudRate = 115200; // Default serial communication properties
const int MAX_NETWORKS = 10;
NetworkInfo availableNetworks[MAX_NETWORKS];
int numNetworks = 0;
int deviceID = 0;

void connectToWiFi() { // Function to connect to WiFi
  displayMessage("Connecting to WiFi...");
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
  displayMessage("Connected to WiFi");
}

void determineBaudRate() { // Function to determine the baud rate that the user wants to use
  Serial.println("Enter the desired baud rate:");

  while (!Serial.available()) { // Wait for user input 
  }

  serialBaudRate = Serial.parseInt(); // Read the user input and set the baud rate
  Serial.begin(serialBaudRate);

  Serial.print("Baud rate set to ");
  Serial.println(serialBaudRate);
}

void askDeviceId() { // Function to ask the user for the device ID
  Serial.println("Enter the device ID:");
  while (!Serial.available()) { // Wait for user input
  }

  deviceID = Serial.parseInt(); // Read the user input and set the device ID
  Serial.print("Device ID set to ");
  Serial.println(deviceID);
}