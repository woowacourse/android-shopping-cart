package woowacourse.shopping.mapper

import com.example.domain.model.CartProducts
import com.example.domain.model.PageNation
import woowacourse.shopping.model.PageNationUiModel

fun PageNationUiModel.toDomain(): PageNation {
    return PageNation(CartProducts(cartProducts.map { it.toDomain() }), currentPage)
}

fun PageNation.toPresentation(): PageNationUiModel {
    return PageNationUiModel(
        allList.map { it.toPresentation() },
        currentPageCartProducts.map { it.toPresentation() },
        currentPage,
        pageCount,
        hasPreviousPage(),
        hasNextPage(),
        isAllChecked,
        isAnyChecked
    )
}
