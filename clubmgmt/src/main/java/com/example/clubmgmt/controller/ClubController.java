package com.example.clubmgmt.controller;

import com.example.clubmgmt.dto.ClubDTO;
import com.example.clubmgmt.entity.Club;
import com.example.clubmgmt.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @GetMapping
    public List<ClubDTO> getAllClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubDTO> getClubById(@PathVariable Long id) {
        return clubService.getClubById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<ClubDTO> getClubsByUser(@PathVariable Long userId) {
        return clubService.getClubsByUser(userId);
    }

    @PostMapping
    public ClubDTO createClub(@RequestBody Club club) {
        return clubService.createClub(club);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubDTO> updateClub(@PathVariable Long id, @RequestBody Club club) {
        try {
            return ResponseEntity.ok(clubService.updateClub(id, club));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }
    
    // Nuevos endpoints para manejar las categorías de un club
    @PostMapping("/{clubId}/categories/{categoryId}")
    public ResponseEntity<ClubDTO> addCategoryToClub(
            @PathVariable Long clubId, 
            @PathVariable Long categoryId) {
        try {
            ClubDTO updatedClub = clubService.addCategoryToClub(clubId, categoryId);
            return ResponseEntity.ok(updatedClub);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{clubId}/categories/{categoryId}")
    public ResponseEntity<ClubDTO> removeCategoryFromClub(
            @PathVariable Long clubId, 
            @PathVariable Long categoryId) {
        try {
            ClubDTO updatedClub = clubService.removeCategoryFromClub(clubId, categoryId);
            return ResponseEntity.ok(updatedClub);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}