package woowacourse.shopping.model

import com.shopping.repository.CartProductRepository
import woowacourse.shopping.model.uimodel.CartProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class Paging(private val cartProductRepository: CartProductRepository) {
    private val cartProducts: List<CartProductUIModel>
        get() = cartProductRepository.getAll().map { it.toUIModel() }

    private var index: PageIndex = PageIndex()

    fun getPageCount() = (index.fromIndex / PAGE_PRODUCT_UNIT) + PAGE_STEP

    fun loadPageProducts(): List<CartProductUIModel> {
        if (cartProducts.isEmpty()) {
            return emptyList()
        }
        return cartProducts.subList(index.fromIndex, index.getToIndex(cartProducts.size))
    }

    fun isPossiblePageUp() = index.isLastIndex(cartProducts.size)

    fun isPossiblePageDown() = index.isFirstIndex()

    fun isLastIndexOfCurrentPage() = (index.fromIndex == cartProducts.size)

    fun subPage() {
        index = index.sub()
    }

    fun addPage() {
        index = index.add()
    }

    companion object {
        private const val PAGE_STEP = 1
        private const val PAGE_PRODUCT_UNIT = 3
    }
}
