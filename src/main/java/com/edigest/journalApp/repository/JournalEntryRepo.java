package com.edigest.journalApp.repository;
import com.edigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

//public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {
//
//}

public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {
    Optional<JournalEntry> findByEmail(String email);
    @Query("{ 'email': ?0 }")
    Optional<JournalEntry> getEntryByEmail(String email);
}
