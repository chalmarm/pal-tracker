package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @RequestMapping(
            value = "/time-entries",
            method = POST,
            headers = "Accept=application/json")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity<>(timeEntry, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = GET)
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry tEntry = timeEntryRepository.find(timeEntryId);
        if(tEntry == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(tEntry, HttpStatus.OK);
    }

    @RequestMapping(value = "/time-entries", method = GET)
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK );
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = PUT)
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) {

        TimeEntry tEntry = timeEntryRepository.update(timeEntryId, timeEntry);
        if(tEntry == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(tEntry,HttpStatus.OK);
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = DELETE)
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
