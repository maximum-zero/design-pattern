package behavioral.iterator.basic

interface Aggregate<T> {
    fun iterator(): Iterator<T>
}

class ConcreteAggregate<T>(private val items: List<T>) : Aggregate<T> {
    fun getItem(index: Int): T {
        return items[index]
    }

    fun getCount(): Int {
        return items.size
    }

    override fun iterator(): Iterator<T> {
        return ConcreteIterator(this)
    }
}