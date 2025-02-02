package com.project.memberproject.service;

import com.project.memberproject.dto.MemberDTO;
import com.project.memberproject.entity.MemberEntity;
import com.project.memberproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            // Optional로 감싸진 객체를 꺼냄.
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
}
