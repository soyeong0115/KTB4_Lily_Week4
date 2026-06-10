package org.example.communityapi.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<Integer, User> users = new HashMap<>();
    private int sequence = 1;

    // 사용자 저장
    public User save(String email, String password, String nickname, String profileImage) {
        int userId = sequence;
        sequence = sequence + 1;

        User user = new User(userId, email, password, nickname, profileImage);
        users.put(userId, user);

        return user;
    }

    // 이메일로 사용자 찾기
    public User findByEmail(String email) {
        int userId = 1;

        while (userId < sequence) {
            User user = users.get(userId);

            if (user != null && user.getEmail().equals(email)) {
                return user;
            }

            userId = userId + 1;
        }

        return null;
    }

    // 이메일 중복 확인
    public boolean existsByEmail(String email) {
        int userId = 1;

        while (userId < sequence) {
            User user = users.get(userId);

            if (user != null && user.getEmail().equals(email)) {
                return true;
            }
            userId = userId + 1;
        }

        return false;
    }

    // 닉네임 중복 확인
    public boolean existsByNickname(String nickname) {
        int userId = 1;

        while (userId < sequence) {
            User user = users.get(userId);

            if (user != null && user.getNickname().equals(nickname)) {
                return true;
            }
            userId = userId + 1;
        }

        return false;
    }

    // userId로 사용자 찾기
    public User findById(int userId) {
        return users.get(userId);
    }
}