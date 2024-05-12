#include <LiquidCrystal_I2C.h>

extern LiquidCrystal_I2C lcd; // LCD display properties

void initializeLCD();
void displayMessage(const String& message);