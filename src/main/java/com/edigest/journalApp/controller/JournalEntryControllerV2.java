package com.edigest.journalApp.controller;

import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.JournalEntryService;
import com.edigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String  userName){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myID}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myID){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myID);
        if (journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getEntryByEmail(@PathVariable String email) {
        Optional<JournalEntry> found = journalEntryService.getByEmail(email);
        return found.isPresent() ? new ResponseEntity<>(found.get(),HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myID}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myID, @PathVariable String userName){
        journalEntryService.deleteById(myID, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry, @PathVariable String userName){
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if(old != null){
            old.setTitle(myEntry.getTitle() != null & !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent() != null & !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
