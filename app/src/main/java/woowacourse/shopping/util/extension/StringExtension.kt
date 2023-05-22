package woowacourse.shopping.util.extension

fun String.parseQueryString(): Map<String, String> {
    val queryStrings = mutableMapOf<String, String>()
    substringAfter("?").split("&").forEach {
        val (key, value) = it.trim().split("=")
        queryStrings[key] = value
    }
    return queryStrings
}
