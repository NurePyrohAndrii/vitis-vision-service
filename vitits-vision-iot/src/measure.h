#include <Arduino.h>
 
extern bool measuring; // Flag to control the measuring
extern String accumulatedData; // String to accumulate the data
extern int measureFrequency; // Measurement frequency in milliseconds
extern unsigned long lastMeasurement; // Time of the last measurement

void startMeasure();
void stopMeasure();
void readData();
