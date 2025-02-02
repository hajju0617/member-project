package com.project.memberproject.repository;

import com.project.memberproject.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 이메일로 유저 찾기. (SELECT * FROM member_table WHERE member_email = ?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
