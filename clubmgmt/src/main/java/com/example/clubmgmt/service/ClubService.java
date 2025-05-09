package com.example.clubmgmt.service;

import com.example.clubmgmt.dto.ClubDTO;
import com.example.clubmgmt.entity.Club;
import com.example.clubmgmt.entity.Category;
import com.example.clubmgmt.entity.User;
import com.example.clubmgmt.repository.ClubRepository;
import com.example.clubmgmt.repository.CategoryRepository;
import com.example.clubmgmt.repository.UserRepository;
import com.example.clubmgmt.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EntityDtoConverter converter;

    @Transactional(readOnly = true)
    public List<ClubDTO> getAllClubs() {
        try {
            List<Club> clubs = clubRepository.findAllWithCategoriesAndUser();
            return converter.convertToClubDtoList(clubs);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<ClubDTO> getClubById(Long id) {
        return clubRepository.findById(id).map(converter::convertToDto);
    }

    public ClubDTO createClub(Club club) {
        Set<Category> categories = new HashSet<>();
        if (club.getCategories() != null) {
            for (Category category : club.getCategories()) {
                categoryRepository.findById(category.getId())
                    .ifPresent(categories::add);
            }
        }
        club.setCategories(categories);
        return converter.convertToDto(clubRepository.save(club));
    }

    public ClubDTO updateClub(Long id, Club updatedClub) {
        return clubRepository.findById(id).map(club -> {
            club.setName(updatedClub.getName());
            club.setDescription(updatedClub.getDescription());
            
            if (updatedClub.getCategories() != null) {
                Set<Category> categories = new HashSet<>();
                for (Category category : updatedClub.getCategories()) {
                    categoryRepository.findById(category.getId())
                        .ifPresent(categories::add);
                }
                club.setCategories(categories);
            }
            
            return converter.convertToDto(clubRepository.save(club));
        }).orElseThrow(() -> new RuntimeException("Club no encontrado"));
    }

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ClubDTO> getClubsByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return converter.convertToClubDtoList(clubRepository.findByUserId(userId));
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
    
    public Optional<Club> getClubEntityById(Long id) {
        return clubRepository.findById(id);
    }
    
    public ClubDTO addCategoryToClub(Long clubId, Long categoryId) {
        Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> new RuntimeException("Club no encontrado"));
        
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        club.addCategory(category);
        return converter.convertToDto(clubRepository.save(club));
    }
    
    public ClubDTO removeCategoryFromClub(Long clubId, Long categoryId) {
        Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> new RuntimeException("Club no encontrado"));
        
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        club.removeCategory(category);
        return converter.convertToDto(clubRepository.save(club));
    }
}