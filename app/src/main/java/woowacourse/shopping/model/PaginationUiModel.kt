package woowacourse.shopping.model

data class PaginationUiModel(
    val cartProducts: List<CartProductUiModel>,
    val currentPageCartProducts: List<CartProductUiModel>,
    val currentPage: Int,
    val pageTotalCount: Int,
    val hasPreviousPage: Boolean,
    val hasNextPage: Boolean,
    val isCurrentPageAllChecked: Boolean,
    val isAnyChecked: Boolean,
    val checkedCount: Int,
    val totalCheckedMoney: Int
)
