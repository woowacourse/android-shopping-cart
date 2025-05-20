package woowacourse.shopping.ui.model

enum class ResultCode(
    val code: Int,
    val key: String? = null,
) {
    PRODUCT_DETAIL_HISTORY_PRODUCT_CLICKED(1000, "NAVIGATE_TO_PRODUCT_DETAIL_RESULT"),
}
