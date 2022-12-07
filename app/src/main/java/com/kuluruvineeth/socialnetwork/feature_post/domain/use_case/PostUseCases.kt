package com.kuluruvineeth.socialnetwork.feature_post.domain.use_case

import com.kuluruvineeth.socialnetwork.core.domain.use_case.DeletePost

data class PostUseCases(
    val getPostsForFollows: GetPostsForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getPostDetails: GetPostDetailsUseCase,
    val getCommentsForPost: GetCommentsForPostUseCase,
    val createComment: CreateCommentUseCase,
    val toggleLikeForParent: ToggleLikeForParentUseCase,
    val getLikesForParent: GetLikesForParentUseCase,
    val deletePost: DeletePost
)
