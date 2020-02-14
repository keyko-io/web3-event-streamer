package io.keyko.monitoring.window;


import org.apache.kafka.streams.kstream.Windows;
import org.apache.kafka.streams.kstream.internals.TimeWindow;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

public class DailyTimeWindow extends Windows<TimeWindow> {

  private final ZoneId zoneId;
  private final long grace;
  private final int startHour;

  public DailyTimeWindow(final ZoneId zoneId, final int startHour, final Duration grace) {
    this.zoneId = zoneId;
    this.grace = grace.toMillis();
    this.startHour = startHour;
  }

  @Override
  public Map<Long, TimeWindow> windowsFor(final long timestamp) {
    final Instant instant = Instant.ofEpochMilli(timestamp);

    final ZonedDateTime zonedDateTime = instant.atZone(zoneId);
    final ZonedDateTime startTime = zonedDateTime.getHour() >= startHour ? zonedDateTime.truncatedTo(ChronoUnit.DAYS).withHour(startHour) : zonedDateTime.truncatedTo(ChronoUnit.DAYS).minusDays(1).withHour(startHour);
    final ZonedDateTime endTime = startTime.plusDays(1);

    final Map<Long, TimeWindow> windows = new LinkedHashMap<>();
    windows.put(toEpochMilli(startTime), new TimeWindow(toEpochMilli(startTime), toEpochMilli(endTime)));
    return windows;
  }

  @Override
  public long size() {
    return Duration.ofDays(1).toMillis();
  }

  @Override
  public long gracePeriodMs() {
    return grace;
  }

  private long toEpochMilli(final ZonedDateTime zonedDateTime) {
    return zonedDateTime.toInstant().toEpochMilli();
  }
}
