package org.example.communityapi.post.dto;

import java.util.List;

public class PostPageResponse {

    private List<PostListResponse> posts;
    private int page;
    private int size;
    private boolean hasNext;

    public PostPageResponse(List<PostListResponse> posts, int page, int size, boolean hasNext) {
        this.posts = posts;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
    }

    public List<PostListResponse> getPosts() {
        return posts;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
