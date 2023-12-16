package behavioral.iterator.board

import org.maximum0.behavioral.iterator.board.Post

class DatePostIterator(posts: List<Post>): Iterator<Post> {
    private val itr: Iterator<Post>

    init {
        posts.sortedBy { it.getDate() }.also {
            itr = it.iterator()
        }
    }

    override fun hasNext(): Boolean {
        return itr.hasNext()
    }

    override fun next(): Post {
        return itr.next()
    }
}