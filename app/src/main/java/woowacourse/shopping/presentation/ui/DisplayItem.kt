package woowacourse.shopping.presentation.ui

sealed interface DisplayItem

data class Product(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val price: Long,
) : DisplayItem

data class LoadingItem(
    val current: Int,
    val total: Int,
) : DisplayItem
