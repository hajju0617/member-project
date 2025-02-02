package com.project.memberproject.entity;

import com.project.memberproject.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member_table")
@NoArgsConstructor
public class MemberEntity {
    // pk 설정.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)      // 유니크 제약 조건.
    private String memberEmail;

    @Column
    private String memberPassword;
    @Column
    private String memberName;

    public MemberEntity(String memberEmail, String memberName, String memberPassword) {
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.memberPassword = memberPassword;
    }

    @Builder
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        return new MemberEntity(
                  memberDTO.getMemberEmail()
                , memberDTO.getMemberName()
                , memberDTO.getMemberPassword()
        );
    }
}
