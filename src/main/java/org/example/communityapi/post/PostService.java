package org.example.communityapi.post;

import org.example.communityapi.comment.Comment;
import org.example.communityapi.comment.CommentRepository;
import org.example.communityapi.comment.dto.CommentResponse;
import org.example.communityapi.common.utils.DateTimeUtils;
import org.example.communityapi.like.LikeRepository;
import org.example.communityapi.post.dto.*;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final PostValidator postValidator;

    public PostService(
            PostRepository postRepository,
            CommentRepository commentRepository,
            UserRepository userRepository,
            LikeRepository likeRepository,
            PostValidator postValidator
    ) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.postValidator = postValidator;
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
        Post post = postValidator.findActivePost(postId);

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
        Post post = postValidator.findActivePost(postId);

        if (post.getWriter().getUserId() != loginUser.getUserId()) {
            throw new IllegalArgumentException("forbidden");
        }

        post.delete();
    }

    // 게시글 목록 조회
    public PostPageResponse getPosts(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("invalid_request");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "postId")
        );

        Page<Post> postPage = postRepository.findByIsDeletedFalseAndWriter_DeletedFalse(pageable);

        List<Post> posts = postPage.getContent();
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
                    commentRepository.countByPostAndIsDeletedFalseAndWriter_DeletedFalse(post),
                    post.getLikeCount(),
                    post.getViewCount(),
                    writer,
                    post.getPostImage()
            );

            result.add(response);

            index = index + 1;
        }

        return new PostPageResponse(
                result,
                postPage.getNumber(),
                postPage.getSize(),
                postPage.hasNext()
        );
    }

    // 게시글 및 댓글 상세 조회
    public PostDetailResponse getPostDetail(Integer userId, int postId, boolean countView) {
        Post post = postValidator.findActivePost(postId);

        User loginUser = null;

        if (userId != null) {
            loginUser = findActiveUser(userId);
        }

        boolean isOwner = loginUser != null && post.getWriter().getUserId() == loginUser.getUserId();

        if (countView && !isOwner) {
            post.increaseViewCount();
        }

        User writerUser = post.getWriter();

        WriterResponse writer = new WriterResponse(
                writerUser.getUserId(),
                writerUser.getNickname(),
                writerUser.getProfileImage()
        );

        List<Comment> comments = commentRepository.findByPostAndIsDeletedFalseAndWriter_DeletedFalse(post);
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

            boolean isMyComment = userId != null && commentWriterUser.getUserId() == userId;

            CommentResponse commentResponse = new CommentResponse(
                    comment.getCommentId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    commentWriter,
                    isMyComment
            );

            commentResponses.add(commentResponse);

            index = index + 1;
        }

        boolean liked = loginUser != null && likeRepository.existsByUserAndPost(loginUser, post);

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
                liked,
                isOwner
        );
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