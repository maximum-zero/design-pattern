package org.maximum0.behavioral.iterator.basic

data class Item (
    private val name: String,
    private val cost: Int,
) {
    override fun toString(): String {
        return "${name}의 가격은 $cost 입니다"
    }
}