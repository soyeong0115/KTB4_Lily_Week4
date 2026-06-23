package org.example.communityapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // 이메일로 사용자 찾기
    public User findByEmail(String email);

    // 이메일 중복 확인
    public boolean existsByEmail(String email);

    // 닉네임 중복 확인
    public boolean existsByNickname(String nickname);
}