package org.maximum0.behavioral.iterator.board

import behavioral.iterator.board.DatePostIterator
import behavioral.iterator.board.ListPostIterator
import java.time.LocalDate

data class Board (
    private val posts: MutableList<Post> = mutableListOf(),
) {
    fun addPost(title: String, date: LocalDate) {
        this.posts.add(Post(title, date))
    }

    fun getDatePostIterator(): Iterator<Post> {
        return DatePostIterator(this.posts)
    }

    fun getListPostIterator(): Iterator<Post> {
        return ListPostIterator(this.posts)
    }
}
