package com.project.memberproject.dto;

import com.project.memberproject.entity.MemberEntity;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class MemberDTO {

    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public MemberDTO(String memberEmail, String memberName, String memberPassword) {
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.memberPassword = memberPassword;
    }

    @Builder
    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        return new MemberDTO(
                memberEntity.getMemberEmail()
                , memberEntity.getMemberName()
                , memberEntity.getMemberPassword()
        );
    }
}
