package org.maximum0.behavioral.iterator.board

import java.time.LocalDate

data class Post (
    private val title: String,
    private val date: LocalDate,
) {
    fun getDate(): LocalDate {
        return date
    }

    override fun toString(): String {
        return "${title}는 ${date}에 작성되었습니다."
    }
}