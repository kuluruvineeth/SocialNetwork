package com.kuluruvineeth.socialnetwork.core.util

interface Paginator<T> {

    suspend fun loadNextItems()
}