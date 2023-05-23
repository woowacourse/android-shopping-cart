package woowacourse.shopping.domain.util

fun <T> List<T>.safeSubList(startIndex: Int, endIndex: Int): List<T> =
    if (startIndex < size) {
        val safeEndIndex = if (endIndex < size) endIndex else size
        subList(startIndex, safeEndIndex)
    } else {
        emptyList()
    }
