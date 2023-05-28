package woowacourse.shopping.ui.shopping.contract.presenter

import android.util.Log
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
        repository.getNext(PRODUCT_COUNT, onSuccess = { datas ->
            productsData += datas.map { product: Product ->
                ProductItem(product.toUIModel(), getCount(product.id))
            }
            view.setProducts(productsData.plus(ProductReadMore))
        }, onFailure = { exception ->
            // Handle failure case
        })
    }

    override fun updateProducts() {
        val recentProductsData = RecentProductsItem(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() },
        )
        when {
            productsData.isEmpty() && recentProductsData.product.isNotEmpty() -> productsData.add(
                recentProductsData,
            )
            productsData.isNotEmpty() -> {
                updateProductsDataWithRecentData(recentProductsData)
            }
        }
        view.setProducts(productsData.plus(ProductReadMore))
    }

    private fun updateProductsDataWithRecentData(recentProductsData: RecentProductsItem) {
        when {
            recentProductsData.product.isEmpty() -> productsData.removeIf { it is RecentProductsItem }
            productsData[0] is RecentProductsItem -> productsData[0] = recentProductsData
            else -> productsData.add(0, recentProductsData)
        }
    }

    override fun fetchMoreProducts() {
        repository.getNext(PRODUCT_COUNT, onSuccess = { datas ->
            productsData += datas.map { product: Product ->
                ProductItem(product.toUIModel(), getCount(product.id))
            }

            view.addProducts(productsData.plus(ProductReadMore))
        }, onFailure = { exception ->
            // Handle failure case
        })
    }

    override fun navigateToItemDetail(id: Long) {
        val latestProduct = recentRepository.getRecent(1).firstOrNull()?.toUIModel()
        repository.findById(id, onSuccess = {
            view.navigateToProductDetail(it.toUIModel(), latestProduct)
        }, onFailure = {
            // Handle failure case
        })
    }

    override fun updateItemCount(id: Long, count: Int) {
        repository.findById(id, onSuccess = {
            cartRepository.insert(CartProduct(it, count, true))
            updateCountSize()
        }, onFailure = {
            // Handle failure case
        })
    }

    override fun increaseCount(id: Long) {
        cartRepository.updateCount(id, getCount(id) + 1)
        view.updateItem(id, getCount(id))
    }

    override fun decreaseCount(id: Long) {
        cartRepository.updateCount(id, getCount(id) - 1)
        view.updateItem(id, getCount(id))
    }

    override fun updateCountSize() {
        view.showCountSize(cartRepository.getAll().size)
    }

    private fun getCount(id: Long): Int {
        return cartRepository.findById(id)?.count ?: 0
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
