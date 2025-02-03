package com.project.memberproject.service;

import com.project.memberproject.dto.MemberDTO;
import com.project.memberproject.entity.MemberEntity;
import com.project.memberproject.repository.MemberRepository;
import jakarta.transaction.Transactional;
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

//    @Transactional
    public void update(MemberDTO memberDTO) {
        // 기존 Entity, 즉 ID가 존재하는 Entity는 save메서드 실행시 update, ID가 없으면 INSERT.
        memberRepository.save(MemberEntity.updateMemberEntity(memberDTO));
        // @Transactional 메서드에서 findById로 가져온 엔터티를 setter로 값을 수정하면 save메서드 없이도 DB 데이터가 수정됨.
//        Optional<MemberEntity> optionalMember = memberRepository.findById(memberDTO.getId());
//        if (optionalMember.isPresent()) {
//            MemberEntity member = optionalMember.get();
//            member.setMemberName(memberDTO.getMemberName());
//            member.setMemberPassword(memberDTO.getMemberPassword());
//        } else {
//            throw new IllegalArgumentException("해당 ID를 가진 회원이 존재하지 않습니다.");
//        }
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {
            return null;
        } else {
            return "ok";
        }
    }
}
