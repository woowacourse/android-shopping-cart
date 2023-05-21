package woowacourse.shopping.model

import com.example.domain.model.CartProducts
import com.example.domain.model.Pagination
import woowacourse.shopping.mapper.toDomain

data class PaginationUiModel(
    private val cartProducts: List<CartProductUiModel>,
    val currentPageCartProducts: List<CartProductUiModel>,
    val pageBottomNavigationUiModel: PageBottomNavigationUiModel,
    val cartBottomNavigationUiModel: CartBottomNavigationUiModel,
    val pageTotalCount: Int,
) {
    fun toDomain(): Pagination {
        return Pagination(CartProducts(cartProducts.map { it.toDomain() }), pageBottomNavigationUiModel.currentPageNumber)
    }
}
