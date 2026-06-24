package org.example.communityapi.post;

import org.example.communityapi.comment.Comment;
import org.example.communityapi.comment.CommentRepository;
import org.example.communityapi.comment.dto.CommentResponse;
import org.example.communityapi.post.dto.CreatePostRequest;
import org.example.communityapi.post.dto.CreatePostResponse;
import org.example.communityapi.post.dto.PostDetailResponse;
import org.example.communityapi.post.dto.PostListResponse;
import org.example.communityapi.post.dto.UpdatePostRequest;
import org.example.communityapi.post.dto.UpdatePostResponse;
import org.example.communityapi.post.dto.WriterResponse;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // Repository 주입하기
    public PostService(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // 게시글 작성
    public CreatePostResponse createPost(Integer userId, CreatePostRequest request) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                request.getPostImage(),
                "2026-06-10 10:00:00",
                user
        );

        postRepository.save(post);

        return new CreatePostResponse(post.getPostId());
    }

    // 게시글 수정
    public UpdatePostResponse updatePost(Integer userId, int postId, UpdatePostRequest request) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new IllegalArgumentException("post_not_found");
        }

        if (post.getWriter().getUserId() != userId) {
            throw new IllegalArgumentException("forbidden");
        }

        if (request.getTitle() == null && request.getContent() == null && request.getPostImage() == null) {
            throw new IllegalArgumentException("invalid_request");
        }

        post.update(
                request.getTitle(),
                request.getContent(),
                request.getPostImage(),
                "2026-06-10 10:20:00"
        );

        return new UpdatePostResponse(post.getPostId());
    }

    // 게시글 삭제
    public void deletePost(Integer userId, int postId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new IllegalArgumentException("post_not_found");
        }

        if (post.getWriter().getUserId() != userId) {
            throw new IllegalArgumentException("forbidden");
        }

        post.delete();
    }

    // 게시글 목록 조회
    public List<PostListResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostListResponse> result = new ArrayList<>();

        int index = 0;

        while (index < posts.size()) {
            Post post = posts.get(index);

            User writerUser = post.getWriter();

            WriterResponse writer = new WriterResponse(
                    writerUser.getUserId(),
                    writerUser.getNickname(),
                    writerUser.getProfileImage()
            );

            PostListResponse response = new PostListResponse(
                    post.getPostId(),
                    post.getTitle(),
                    post.getCreatedAt(),
                    post.getCommentCount(),
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
        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new IllegalArgumentException("post_not_found");
        }

        User writerUser = post.getWriter();

        WriterResponse writer = new WriterResponse(
                writerUser.getUserId(),
                writerUser.getNickname(),
                writerUser.getProfileImage()
        );

        List<Comment> comments = commentRepository.findByPostAndIsDeletedFalse(post);
        List<CommentResponse> commentResponses = new ArrayList<>();

        int index = 0;

        while (index < comments.size()) {
            Comment comment = comments.get(index);

            User commentWriterUser = comment.getWriter();

            WriterResponse commentWriter = new WriterResponse(
                    commentWriterUser.getUserId(),
                    commentWriterUser.getNickname(),
                    commentWriterUser.getProfileImage()
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