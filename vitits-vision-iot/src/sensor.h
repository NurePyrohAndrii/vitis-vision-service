#include "DHT.h"

// DHT sensors properties
#define DHT_AIR_PIN 13
#define DHT_AIR_TYPE DHT22
#define DHT_GND_PIN 12
#define DHT_GND_TYPE DHT22

#define LDR_PIN 35 // Photoresistor pin

// Initialize the DHT sensor
extern DHT dht_air;
extern DHT dht_gnd;