package woowacourse.shopping.ui.model

enum class ActivityResult(
    val code: Int,
    val key: String? = null,
) {
    PRODUCT_DETAIL_HISTORY_PRODUCT_CLICKED(1000, "CLICKED_PRODUCT_ID"),
    PRODUCT_DETAIL_CART_UPDATED(1001, "UPDATED_PRODUCT_ID"),
    CART_PRODUCT_EDITED(1002, "EDITED_PRODUCT_IDS"),
}
