package behavioral.iterator.board

import org.maximum0.behavioral.iterator.board.Post

class ListPostIterator(posts: List<Post>): Iterator<Post> {
    private val itr: Iterator<Post> = posts.iterator()

    override fun hasNext(): Boolean {
        return itr.hasNext()
    }

    override fun next(): Post {
        return itr.next()
    }
}