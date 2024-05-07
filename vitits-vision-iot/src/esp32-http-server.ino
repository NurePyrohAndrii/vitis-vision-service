#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiClientSecure.h>
#include <HTTPClient.h>
#include <WebServer.h>
#include <LiquidCrystal_I2C.h>
#include <uri/UriBraces.h>
#include "DHT.h"
#include <Adafruit_Sensor.h>
#include <chrono>
#include <iomanip>
#include <sstream>
#include <ArduinoJson.h>


const int id = 1; // Device ID

// DHT sensors properties
#define DHT_AIR_PIN 13
#define DHT_AIR_TYPE DHT22
#define DHT_GND_PIN 12
#define DHT_GND_TYPE DHT22

#define LDR_PIN 35 // Photoresistor pin

// Constants for the photoresistor calculation
const float GAMMA = 0.7;
const float RL10 = 50;

// Initialize the DHT sensor
DHT dht_air(DHT_AIR_PIN, DHT_AIR_TYPE);
DHT dht_gnd(DHT_GND_PIN, DHT_GND_TYPE);


struct NetworkInfo { // Struct to store network information
  String ssid;
  String password;
};

// Network information global variables and constants
const int MAX_NETWORKS = 10;
NetworkInfo availableNetworks[MAX_NETWORKS];
int numNetworks = 0;

int serialBaudRate = 115200; // Default s erial communication properties if the user does not specify

WebServer server(80); // Web server setup

LiquidCrystal_I2C lcd(0x27, 20, 4); // LCD display properties

bool measuring = false; // Flag to control the measuring
String accumulatedData = ""; // String to accumulate data
unsigned long lastMeasurement = 0; // Time of the last measurement
int measureFrequency = 1000; // Measurement frequency in milliseconds

void connectToWiFi() { // Function to connect to WiFi
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

void determineBaudRate() { // Function to determine the baud rate that the user wants to use
  Serial.println("Enter the desired baud rate:");

  while (!Serial.available()) { // Wait for user input 
  }

  serialBaudRate = Serial.parseInt(); // Read the user input and set the baud rate
  Serial.begin(serialBaudRate);

  Serial.print("Baud rate set to ");
  Serial.println(serialBaudRate);
}

void initializeLCD() { // Function to initialize the LCD display
  lcd.init();
  lcd.backlight();
}

void displayMessage(const String& message) { // Function to display a message on the LCD display
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.println(message);
}

float calculateLux(float LdrValue){ // Function to calculate the lux value from the photoresistor value
  float voltage = LdrValue / 1024. * 5 / 4;
  float resistance = 2000 * voltage / (1 - voltage / 5);
  float lux = pow(RL10 * 1e3 * pow(10, GAMMA) / resistance, (1 / GAMMA));
  return lux;
}

void readData() { // Function to read data from the DHT sensors and photoresistor
  if (!measuring) return; // Only read data if measuring is true

  // Current time in ISO 8601 format
  auto now = std::chrono::system_clock::now();
  auto now_c = std::chrono::system_clock::to_time_t(now);
  std::stringstream ss;
  ss << std::put_time(std::localtime(&now_c), "%FT%TZ"); // ISO 8601 format
  String timeData = "\"timestamp\":\"" + String(ss.str().c_str()) + "\"";

  float air_humidity = dht_air.readHumidity();
  float air_temperature = dht_air.readTemperature();
  float gnd_humidity = dht_gnd.readHumidity();
  float gnd_temperature = dht_gnd.readTemperature();

  float currentLdrValue = (float) analogRead(LDR_PIN);
  float measuredLux = calculateLux(currentLdrValue);

  if (isnan(air_humidity) || isnan(air_temperature) || isnan(gnd_humidity) || isnan(gnd_temperature) || isnan(measuredLux)) {
    return; // Skip this reading if any data is not valid
  }

  // Create data string
  String data = "{ \"air_temperature\":" + String(air_temperature, 2) +
                ", \"air_humidity\":" + String(air_humidity, 2) +
                ", \"gnd_temperature\":" + String(gnd_temperature, 2) +
                ", \"gnd_humidity\":" + String(gnd_humidity, 2) +
                ", \"lux\":" + String(measuredLux, 2) +
                ", " + timeData + "},";
  
  accumulatedData += data; // Append this data to the accumulated data
}

void startMeasure() { // Function to start the measurement
  measuring = true;
  Serial.println("Measurement started");
  displayMessage("Measuring...");
}

void stopMeasure() { // Function to stop the measurement
  measuring = false;
  Serial.println("Measurement stopped");
  displayMessage("Waiting for request...");
}

void setTimeFromString(String timeString) {  // Set the time of the device from a string in ISO 8601 format
    struct tm tm;
    strptime(timeString.c_str(), "%Y-%m-%dT%H:%M:%SZ", &tm);
    time_t t = mktime(&tm);
    struct timeval now = { .tv_sec = t };
    settimeofday(&now, NULL);
}

void setupServer() { // Function to setup the server
  server.on("/" + String(id) + "/start", HTTP_POST, []() {
    if (measuring) {
      server.send(400, "application/json", "{\"error\":\"Measurement already in progress\"}");
      return;
    }

    String requestBody = server.arg("plain");
    JsonDocument doc;
    DeserializationError error = deserializeJson(doc, requestBody);

    if (error) {
      server.send(400, "application/json", "{\"error\":\"Invalid request\"}");
      return;
    }

    measureFrequency = doc["frequency"].as<int>() * 1000;
    setTimeFromString(doc["timestamp"].as<String>());

    startMeasure();
    server.send(200, "application/json", "{\"message\":\"Measurement started\"}");
  });

  server.on("/" + String(id) + "/stop", HTTP_GET, []() {
    if (!measuring) {
      server.send(400, "application/json", "{\"error\":\"No measurement in progress\"}");
      return;
    }
    stopMeasure();
    
    accumulatedData = accumulatedData.substring(0, accumulatedData.length() - 1); // remove the last comma from the accumulated data
    server.send(200, "application/json", "{\"data\":[" + accumulatedData + "]}");
    accumulatedData = ""; // Clear accumulated data
  });
}

void setup() { // Main setup function
  Serial.begin(serialBaudRate);
  delay(1000);
  determineBaudRate();
  Serial.begin(serialBaudRate);

  // Initialize the DHT sensors and the photoresistor
  dht_air.begin();
  dht_gnd.begin();
  pinMode(LDR_PIN, INPUT);
  initializeLCD();

  displayMessage("Connecting to WiFi...");
  connectToWiFi();
  displayMessage("Connected to WiFi");

  setupServer();
  server.begin();
}

void loop() { // Main loop function
  server.handleClient();
  if (measuring && millis() - lastMeasurement >= measureFrequency) {
    readData();
    lastMeasurement = millis(); // Update the last measurement time
  }
  delay(50); 
}