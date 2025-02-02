package com.project.memberproject.dto;

import com.project.memberproject.entity.MemberEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public MemberDTO(Long id, String memberEmail, String memberName, String memberPassword) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.memberPassword = memberPassword;
    }

    @Builder
    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        return new MemberDTO(
                  memberEntity.getId()
                , memberEntity.getMemberEmail()
                , memberEntity.getMemberName()
                , memberEntity.getMemberPassword()
        );
    }

    public static List<MemberDTO> toMemberDTOList(List<MemberEntity> memberEntityList) {
        List<MemberDTO> memberDTOList = new LinkedList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }
}
