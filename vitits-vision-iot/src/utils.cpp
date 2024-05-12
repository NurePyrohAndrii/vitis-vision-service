#include "utils.h"

void setTimeFromString(String timeString) {  // Set the time of the device from a string in ISO 8601 format
    struct tm tm;
    strptime(timeString.c_str(), "%Y-%m-%dT%H:%M:%SZ", &tm);
    time_t t = mktime(&tm);
    struct timeval now = { .tv_sec = t };
    settimeofday(&now, NULL);
}