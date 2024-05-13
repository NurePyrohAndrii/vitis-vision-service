#include "lcd.h"
#include "measure.h"
#include <chrono>
#include <iomanip>
#include <sstream>
#include "sensor.h"

bool measuring = false; // Flag to control the measuring
String accumulatedData = ""; // String to accumulate the data
int measureFrequency = 1000; // Measurement frequency in milliseconds
unsigned long lastMeasurement = 0; // Time of the last measurement

// Constants for the photoresistor calculation
const float GAMMA = 0.7;
const float RL10 = 50;

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