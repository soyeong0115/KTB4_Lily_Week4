package org.example.communityapi.post;

import org.example.communityapi.comment.Comment;
import org.example.communityapi.comment.CommentRepository;
import org.example.communityapi.comment.dto.CommentResponse;
import org.example.communityapi.post.dto.PostDetailResponse;
import org.example.communityapi.post.dto.PostListResponse;
import org.example.communityapi.post.dto.WriterResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // Repository 주입하기
    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // 게시글 목록 조회
    public List<PostListResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostListResponse> result = new ArrayList<>();

        int index = 0;

        while (index < posts.size()) {
            Post post = posts.get(index);

            WriterResponse writer = new WriterResponse(
                    post.getWriterId(),
                    post.getWriterNickname(),
                    post.getWriterProfileImage()
            );

            int commentCount = commentRepository.countByPostId(post.getPostId());

            PostListResponse response = new PostListResponse(
                    post.getPostId(),
                    post.getTitle(),
                    post.getCreatedAt(),
                    commentCount,
                    post.getLikeCount(),
                    post.getViewCount(),
                    writer
            );

            result.add(response);

            index = index + 1;
        }

        return result;
    }

    // 게시글 및 댓글 상세 조회
    public PostDetailResponse getPostDetail(int postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new IllegalArgumentException("post_not_found");
        }

        WriterResponse writer = new WriterResponse(
                post.getWriterId(),
                post.getWriterNickname(),
                post.getWriterProfileImage()
        );

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        int index = 0;

        while (index < comments.size()) {
            Comment comment = comments.get(index);

            WriterResponse commentWriter = new WriterResponse(
                    comment.getWriterId(),
                    comment.getWriterNickname(),
                    comment.getWriterProfileImage()
            );

            CommentResponse commentResponse = new CommentResponse(
                    comment.getCommentId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    commentWriter
            );

            commentResponses.add(commentResponse);

            index = index + 1;
        }

        return new PostDetailResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getPostImage(),
                post.getCreatedAt(),
                post.getLikeCount(),
                post.getViewCount(),
                commentResponses.size(),
                writer,
                commentResponses
        );
    }
}