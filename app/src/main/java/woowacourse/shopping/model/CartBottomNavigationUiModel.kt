package woowacourse.shopping.model

data class CartBottomNavigationUiModel(
    val isCurrentPageAllChecked: Boolean,
    val totalCheckedMoney: Int,
    val isAnyChecked: Boolean,
    val checkedCount: Int
)
