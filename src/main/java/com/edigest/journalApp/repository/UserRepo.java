package com.edigest.journalApp.repository;
import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}

//public interface UserRepo extends MongoRepository<User, ObjectId> {
//    Optional<JournalEntry> findByEmail(String email);
//    @Query("{ 'email': ?0 }")
//    Optional<JournalEntry> getEntryByEmail(String email);
//}
