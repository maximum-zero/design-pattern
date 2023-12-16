package org.maximum0.behavioral.iterator.board

import java.time.LocalDate

fun main(args: Array<String>) {
    // 1. 게시판 생성
    val board: Board = Board()

    // 2. 게시판에 게시글 포스팅
    board.addPost("디자인 패턴 리뷰", LocalDate.of(2023, 10, 30))
    board.addPost("게임 리뷰", LocalDate.of(2023, 11, 30))
    board.addPost("유튜브 리뷰", LocalDate.of(2023, 9, 10))
    board.addPost("핸드폰 리뷰", LocalDate.of(2024, 1, 15))

    // 3-1. 게시판의 게시글 일반 노출
    print(board.getListPostIterator())

    // 3-2. 게시판의 게시글 날짜순 노출
    print(board.getDatePostIterator())
}

fun print(iterator: Iterator<Post>) {
    while (iterator.hasNext()) {
        println(iterator.next())
    }
    println()
}