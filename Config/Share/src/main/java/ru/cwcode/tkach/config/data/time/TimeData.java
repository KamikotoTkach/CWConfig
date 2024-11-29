package ru.cwcode.tkach.config.data.time;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeData {
  String time;
  transient LocalTime cached;
  
  private TimeData() {
  }
  
  public TimeData(String time) {
    this.time = time;
  }
  
  public LocalTime getLocalTime() {
    return cached == null ? cached = LocalTime.parse(time) : cached;
  }
  
  public String getTime() {
    return time;
  }
  
  public boolean isCurrentTimeMatch(long precisionMs) {
    LocalTime now = LocalTime.now();
    
    return now.isAfter(getLocalTime()) && now.isBefore(getLocalTime().plus(precisionMs, ChronoUnit.MILLIS));
  }
}
