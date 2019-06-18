package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private Map<Long, TimeEntry> timeEntries = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry persisted = new TimeEntry(counter.incrementAndGet(), timeEntry.getProjectId(), timeEntry.getUserId(),
                timeEntry.getDate(), timeEntry.getHours());
        timeEntries.put(persisted.getId(), persisted);
        return persisted;
    }

    @Override
    public TimeEntry find(long timeEntryId) {

        return timeEntries.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<TimeEntry>(timeEntries.values());
    }

    @Override
    public TimeEntry update(long l, TimeEntry timeEntry) {

        TimeEntry current = timeEntries.get(l);
        if(current == null)
            return null;

        timeEntry.setId(l);
        timeEntries.put(l,timeEntry);
        return timeEntry;
    }

    @Override
    public void delete(long id) {
        timeEntries.remove(id);
    }
}
