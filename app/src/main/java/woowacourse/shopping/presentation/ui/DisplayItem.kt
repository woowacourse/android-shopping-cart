package woowacourse.shopping.presentation.ui

sealed interface DisplayItem

data class Product(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val price: Long,
    val count: Int = DEFAULT_PURCHASE_COUNT,
) : DisplayItem {
    companion object {
        const val DEFAULT_PURCHASE_COUNT = 1
    }
}

data class LoadingItem(
    val current: Int,
    val total: Int,
) : DisplayItem
