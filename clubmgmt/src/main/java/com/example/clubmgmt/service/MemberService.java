package com.example.clubmgmt.service;

import com.example.clubmgmt.dto.MemberDTO;
import com.example.clubmgmt.entity.Member;
import com.example.clubmgmt.repository.MemberRepository;
import com.example.clubmgmt.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private EntityDtoConverter converter;

    public List<MemberDTO> getAllMembers() {
        return converter.convertToMemberDtoList(memberRepository.findAll());
    }

    public List<MemberDTO> getMembersByClub(Long clubId) {
        return converter.convertToMemberDtoList(memberRepository.findByClubId(clubId));
    }

    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id).map(converter::convertToDto);
    }

    public List<MemberDTO> getMembersByCategory(Long categoryId) {
        return converter.convertToMemberDtoList(memberRepository.findByCategoryId(categoryId));
    }

    public MemberDTO createMember(Member member) {
        return converter.convertToDto(memberRepository.save(member));
    }

    public MemberDTO updateMember(Long id, Member updatedMember) {
        return memberRepository.findById(id).map(member -> {
            member.setFirstName(updatedMember.getFirstName());
            member.setLastName(updatedMember.getLastName());
            member.setEmail(updatedMember.getEmail());
            member.setJoinDate(updatedMember.getJoinDate());
            member.setClub(updatedMember.getClub());
            member.setCategory(updatedMember.getCategory());
            return converter.convertToDto(memberRepository.save(member));
        }).orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
    
    // Método para obtener la entidad Member directamente (para uso interno)
    public Optional<Member> getMemberEntityById(Long id) {
        return memberRepository.findById(id);
    }
}