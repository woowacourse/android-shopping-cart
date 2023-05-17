package woowacourse.shopping.model

data class PageNationUiModel(
    val cartProducts: List<CartProductUiModel>,
    val currentPageCartProducts: List<CartProductUiModel>,
    val currentPage: Int,
    val pageTotalCount: Int,
    val hasPreviousPage: Boolean,
    val hasNextPage: Boolean,
    val isAllChecked: Boolean,
    val isAnyChecked: Boolean,
    val checkedCount: Int,
    val totalCheckedMoney: Int
) {
    val allSize: Int
        get() = cartProducts.size

    companion object {
        const val PAGE_LOAD_SIZE = 5
    }
}

