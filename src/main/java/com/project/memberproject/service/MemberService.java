package com.project.memberproject.service;

import com.project.memberproject.dto.MemberDTO;
import com.project.memberproject.entity.MemberEntity;
import com.project.memberproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // repository 메서드를 이용하려면 인자(argument)로 entity 객체를 넘겨줘야됨. 즉, dto -> entity 변환이 필요.
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (memberEntity.isPresent()) {
            // Optional로 감싸진 객체를 get() 메서드를 통해서 꺼냄.
            MemberEntity memberEntityExist = memberEntity.get();
            if (memberEntityExist.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                return MemberDTO.toMemberDTO(memberEntityExist);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        return MemberDTO.toMemberDTOList(memberEntityList);
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);
        if (memberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(memberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberEmail(myEmail);
        if (memberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(memberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.updateToMemberEntity(memberDTO));
    }
}
