package org.maximum0.behavioral.iterator.basic

import behavioral.iterator.basic.ConcreteAggregate
import behavioral.iterator.basic.Iterator

fun main() {
    val array: ConcreteAggregate<Item> = ConcreteAggregate(listOf(
        Item("CPU", 1000),
        Item("Keyboard", 2000),
        Item("Mouse", 3000),
        Item("HDD", 4000),
    ))

    val iterator: Iterator<Item> = array.iterator()
    while(iterator.hasNext()) {
        println(iterator.next())
    }
}