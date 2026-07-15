package org.example.communityapi.post;

import org.example.communityapi.comment.Comment;
import org.example.communityapi.comment.CommentRepository;
import org.example.communityapi.comment.dto.CommentResponse;
import org.example.communityapi.common.utils.DateTimeUtils;
import org.example.communityapi.like.LikeRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    // 게시글 작성
    public CreatePostResponse createPost(Integer userId, CreatePostRequest request) {
        User user = findActiveUser(userId);

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        String now = DateTimeUtils.now();

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                request.getPostImage(),
                now,
                user
        );

        postRepository.save(post);

        return new CreatePostResponse(post.getPostId());
    }

    // 게시글 수정
    public UpdatePostResponse updatePost(Integer userId, int postId, UpdatePostRequest request) {
        User loginUser = findActiveUser(userId);
        Post post = findActivePost(postId);

        if (post.getWriter().getUserId() != loginUser.getUserId()) {
            throw new IllegalArgumentException("forbidden");
        }

        if (request.getTitle() == null
                && request.getContent() == null
                && request.getPostImage() == null) {
            throw new IllegalArgumentException("invalid_request");
        }

        String now = DateTimeUtils.now();

        post.update(
                request.getTitle(),
                request.getContent(),
                request.getPostImage(),
                now
        );

        return new UpdatePostResponse(post.getPostId());
    }

    // 게시글 삭제
    public void deletePost(Integer userId, int postId) {
        User loginUser = findActiveUser(userId);
        Post post = findActivePost(postId);

        if (post.getWriter().getUserId() != loginUser.getUserId()) {
            throw new IllegalArgumentException("forbidden");
        }

        post.delete();
    }

    // 게시글 목록 조회
    public List<PostListResponse> getPosts() {
        List<Post> posts = postRepository.findByIsDeletedFalse();
        List<PostListResponse> result = new ArrayList<>();

        int index = 0;

        while (index < posts.size()) {
            Post post = posts.get(index);

            User writerUser = post.getWriter();

            if (writerUser.isDeleted()) {
                index = index + 1;
                continue;
            }

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
    public PostDetailResponse getPostDetail(Integer userId, int postId) {
        Post post = findActivePost(postId);

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

        boolean liked = false;

        if (userId != null) {
            User loginUser = findActiveUser(userId);
            liked = likeRepository.existsByUserAndPost(loginUser, post);
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
                commentResponses,
                liked
        );
    }

    // 조회 가능 게시글 조회
    private Post findActivePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post_not_found"));

        if (post.isDeleted()) {
            throw new IllegalArgumentException("post_not_found");
        }

        if (post.getWriter().isDeleted()) {
            throw new IllegalArgumentException("post_not_found");
        }

        return post;
    }

    // 인증된 활성 사용자 조회
    private User findActiveUser(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        return userRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new IllegalArgumentException("unauthorized"));
    }
}