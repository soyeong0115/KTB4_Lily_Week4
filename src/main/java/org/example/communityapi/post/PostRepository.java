package org.example.communityapi.post;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    // DB 대신 사용할 게시글 저장소
    private final Map<Integer, Post> posts = new HashMap<>();

    // 게시글 번호 자동 증가용
    private int sequence = 1;

    // 게시글 저장
    public Post save(
            String title,
            String content,
            String postImage,
            String createdAt,
            int writerId,
            String writerNickname,
            String writerProfileImage
    ) {
        int postId = sequence;
        sequence = sequence + 1;

        Post post = new Post(
                postId,
                title,
                content,
                postImage,
                createdAt,
                0,
                0,
                writerId,
                writerNickname,
                writerProfileImage
        );

        posts.put(postId, post);

        return post;
    }

    // 게시글 전체 조회
    public List<Post> findAll() {
        List<Post> result = new ArrayList<>();

        int postId = 1;

        while (postId < sequence) {
            Post post = posts.get(postId);

            if (post != null) {
                result.add(post);
            }

            postId = postId + 1;
        }

        return result;
    }

    // postId로 게시글 하나 조회
    public Post findById(int postId) {
        return posts.get(postId);
    }
}