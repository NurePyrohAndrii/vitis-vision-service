#include "lcd.h"

LiquidCrystal_I2C lcd(0x27, 16, 2); // LCD display properties

void initializeLCD() { // Function to initialize the LCD display
  lcd.init();
  lcd.backlight();
}

void displayMessage(const String& message) { // Function to display a message on the LCD display
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.println(message);
}