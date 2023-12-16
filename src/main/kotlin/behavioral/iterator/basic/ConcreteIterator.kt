package behavioral.iterator.basic

interface Iterator<T> {
    fun next(): T
    fun hasNext(): Boolean
}

class ConcreteIterator<T>(private val concreteAggregate: ConcreteAggregate<T>) : Iterator<T> {
    private var index: Int = -1
    override fun next(): T {
        index++
        return concreteAggregate.getItem(index)
    }

    override fun hasNext(): Boolean {
        return (index + 1) < concreteAggregate.getCount()
    }
}
