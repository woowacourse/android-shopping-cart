package woowacourse.shopping.util

fun <T> Iterable<T>.secondOrNull(predicate: ((T) -> Boolean)? = null): T? {
    var count = 0
    for (element in this) {
        if (predicate == null || predicate(element)) {
            if (count == 1) {
                return element
            }
            count++
        }
    }
    return null
}
