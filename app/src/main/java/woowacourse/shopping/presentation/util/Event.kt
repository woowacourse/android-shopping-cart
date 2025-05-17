package woowacourse.shopping.presentation.util

class Event<out T>(
    private val content: T,
) {
    private var hasBeenHandled = false

    /** 이벤트가 이미 처리되지 않았다면 값을 반환하고 처리된 것으로 처리 */
    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }

    /** 항상 값을 반환하지만 처리 여부에 상관없이 사용 */
    fun peekContent(): T = content
}
