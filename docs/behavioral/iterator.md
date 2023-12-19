## 목차
- [Iterator Pattern](#iterator-pattern)
- [주요 구성 요소](#주요-구성-요소)
- [Iterator Pattern 흐름](#Iterator-Pattern-흐름)
- [Iterator Pattern 특징](#Iterator-Pattern-특징)
- [예제 1 - 게시글 정렬](#예제-1---iterator를-통한-게시글-순회)

<br />

# Iterator Pattern

Iterator(반복자) 패턴은 **객체의 집합체를 순회**하면서 요소에 접근하는 방법을 제공하는 디자인 패턴 중 하나 입니다.

<br />

## 주요 구성 요소
![Iterator Pattern 기본 구성](./resources/iterator-basic.png)

### Iterator
- ConcreteIterator 요소를 순회하면서 접근하는 인터페이스를 정의합니다.
  - `next()` : 다음 요소에 접근하기 위한 메서드
  - `hasNext()` : 현재 위치가 유효한지 확인하는 메서드

### ConcreteIterator
- Iterator 인터페이스를 구현하며, 실제로 반복을 수행하는 클래스입니다.
- 컬렉션의 내부 구조에 따라 반복을 처리하고, 각 요소에 접근합니다.

### Aggregate
- 객체들의 집합을 나타내는 인터페이스를 정의합니다.
- 이 집합체는 반복자를 생성하는 `createIterator()` 메서드를 포함합니다.

### ConcreteAggregate
- Aggregate 인터페이스를 구현하며, 실제로 객체들의 집합을 관리하는 클래스입니다.
- 반복자를 생성하여 반환하는 `createIterator()` 메서드를 구현합니다.

<br />

## Iterator Pattern 흐름

### 집합체 객체

```kotlin
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
```

### 반복체 객체

```kotlin
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
```

### 데이터 모델

```kotlin
data class Item (
    private val name: String,
    private val cost: Int,
) {
    override fun toString(): String {
        return "${name}의 가격은 $cost 입니다"
    }
}
```

### 데이터 흐름

```kotlin
fun main() {
    // 1. 집합체 생성
    val array: ConcreteAggregate<Item> = ConcreteAggregate(listOf(
        Item("CPU", 1000),
        Item("Keyboard", 2000),
        Item("Mouse", 3000),
        Item("HDD", 4000),
    ))

    // 2. 집합체에서 반복체 객체 반환
    val iterator: Iterator<Item> = array.iterator()
  
    // 3. 반복체 순회
    while(iterator.hasNext()) {
        println(iterator.next())
    }
}
```

```
CPU의 가격은 1000 입니다
Keyboard의 가격은 2000 입니다
Mouse의 가격은 3000 입니다
HDD의 가격은 4000 입니다
```

<br />

## Iterator Pattern 특징
### 장점
- 컬렉션의 내부 구조를 감추고, 외부에서는 요소에 접근하는 인터페이스만을 제공합니다.
- 반복자를 통해 순회 방식을 변경하거나 다양한 방식으로 요소에 접근할 수 있습니다.
- 컬렉션의 구현과 반복 로직을 분리하여 각각의 역할에 집중할 수 있게 합니다.

### 단점
- 클래스가 늘어나며, 복잡도가 증가합니다.

<br />

## 예제 1 - Iterator를 통한 게시글 순회
게시판에 글을 최근글, 작성순으로 정렬해 나열할 수 있도록 합니다.
이 때, `ListPostIterator`와 `DatePostIterator` 인터페이스를 통해 정렬 전략을 구현하여 사용합니다.

### 데이터 모델
```kotlin
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
```

### 반복체 객체

```kotlin
// 게시글 날짜순 정렬 Iterator 
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

// 게시글 저장순 정렬 Iterator
class ListPostIterator(posts: List<Post>): Iterator<Post> {
  private val itr: Iterator<Post> = posts.iterator()

  override fun hasNext(): Boolean {
    return itr.hasNext()
  }

  override fun next(): Post {
    return itr.next()
  }
}

```

### 집합체 객체
```kotlin
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
```

### 데이터 흐름

```kotlin
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
```

```
게임 리뷰는 2023-11-30에 작성되었습니다.
유튜브 리뷰는 2023-09-10에 작성되었습니다.
핸드폰 리뷰는 2024-01-15에 작성되었습니다.

유튜브 리뷰는 2023-09-10에 작성되었습니다.
디자인 패턴 리뷰는 2023-10-30에 작성되었습니다.
게임 리뷰는 2023-11-30에 작성되었습니다.
핸드폰 리뷰는 2024-01-15에 작성되었습니다.
```