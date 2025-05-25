package woowacourse.shopping.view.core.common

inline fun <T> withState(
    state: T?,
    block: (T) -> Unit,
) {
    state?.let(block)
}
