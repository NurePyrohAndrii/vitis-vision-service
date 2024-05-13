#include <WiFiClient.h>
#include <WiFiClientSecure.h>
#include <HTTPClient.h>
#include <WebServer.h>
#include <uri/UriBraces.h>
#include <Adafruit_Sensor.h>
#include <ArduinoJson.h>

#include "network.h"
#include "measure.h"
#include "lcd.h"
#include "sensor.h"
#include "utils.h"

WebServer server(80);

void setupServer() { // Function to setup the server
  server.on("/" + String(deviceID) + "/start", HTTP_POST, []() {
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

  server.on("/" + String(deviceID) + "/stop", HTTP_GET, []() {
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
  askDeviceId();

  // Initialize the DHT sensors and the photoresistor
  dht_air.begin();
  dht_gnd.begin();
  pinMode(LDR_PIN, INPUT);
  initializeLCD();

  // Connect to WiFi and setup the server
  connectToWiFi();
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