package woowacourse.shopping.ui.shopping.contract.presenter

import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.ui.shopping.ProductItem
import woowacourse.shopping.ui.shopping.ProductReadMore
import woowacourse.shopping.ui.shopping.ProductsItemType
import woowacourse.shopping.ui.shopping.RecentProductsItem
import woowacourse.shopping.ui.shopping.contract.ShoppingContract

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ProductRepository,
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository,
) : ShoppingContract.Presenter {
    private var productsData: MutableList<ProductsItemType> = mutableListOf()

    override fun setUpProducts() {
        productsData += repository.getNext(PRODUCT_COUNT)
            .map { product: Product -> ProductItem(product.toUIModel(), getCount(product.id)) }
        view.setProducts(productsData.plus(ProductReadMore))
    }

    override fun updateProducts() {
        val recentProductsData = RecentProductsItem(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() },
        )
        when {
            productsData[0] is RecentProductsItem -> productsData[0] = recentProductsData
            recentProductsData.product.isNotEmpty() -> productsData.add(0, recentProductsData)
        }
        view.setProducts(productsData.plus(ProductReadMore))
    }

    override fun fetchMoreProducts() {
        productsData += repository.getNext(PRODUCT_COUNT)
            .map { ProductItem(it.toUIModel(), getCount(it.id)) }
        view.addProducts(productsData.plus(ProductReadMore))
    }

    override fun navigateToItemDetail(id: Long) {
        val product = repository.findById(id)
        view.navigateToProductDetail(product.toUIModel())
    }

    override fun updateItemCount(id: Long, count: Int) {
        repository.findById(id).let {
            cartRepository.insert(CartProduct(it, count, true))
        }
    }

    private fun getCount(id: Long): Int {
        cartRepository.getFindById(id)?.let {
            return it.count
        }
        return 0
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
