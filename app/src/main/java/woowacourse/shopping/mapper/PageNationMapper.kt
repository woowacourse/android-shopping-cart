package woowacourse.shopping.mapper

import com.example.domain.model.Pagination
import woowacourse.shopping.model.CartBottomNavigationUiModel
import woowacourse.shopping.model.PageBottomNavigationUiModel
import woowacourse.shopping.model.PaginationUiModel

fun Pagination.toPresentation(): PaginationUiModel {
    return PaginationUiModel(
        allList.map { it.toPresentation() },
        currentPageCartProducts.map { it.toPresentation() },
        PageBottomNavigationUiModel(hasPreviousPage(), currentPage, hasNextPage()),
        CartBottomNavigationUiModel(
            isCurrentPageAllChecked,
            totalCheckedMoney,
            isAnyChecked,
            checkedCount
        ),
        pageCount
    )
}
