package woowacourse.shopping.mapper

import com.example.domain.model.CartProducts
import com.example.domain.model.Pagination
import woowacourse.shopping.model.PaginationUiModel

fun PaginationUiModel.toDomain(): Pagination {
    return Pagination(CartProducts(cartProducts.map { it.toDomain() }), currentPage)
}

fun Pagination.toPresentation(): PaginationUiModel {
    return PaginationUiModel(
        allList.map { it.toPresentation() },
        currentPageCartProducts.map { it.toPresentation() },
        currentPage,
        pageCount,
        hasPreviousPage(),
        hasNextPage(),
        isCurrentPageAllChecked,
        isAnyChecked,
        checkedCount,
        totalCheckedMoney
    )
}
