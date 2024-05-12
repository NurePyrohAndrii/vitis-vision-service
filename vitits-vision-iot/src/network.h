#ifndef NETWORK_H
#define NETWORK_H

#include <WiFi.h>

// Network information global variables and constants
extern int serialBaudRate; // Default serial communication properties if the user does not specify
extern int numNetworks;
extern const int MAX_NETWORKS;
extern int deviceID;

struct NetworkInfo { // Struct to store network information
  String ssid;
  String password;
};

extern NetworkInfo availableNetworks[];

void connectToWiFi();
void determineBaudRate();
void askDeviceId();

#endif