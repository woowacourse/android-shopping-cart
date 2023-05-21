package woowacourse.shopping.ui.productdetail.contract.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.CartProduct
import com.example.domain.repository.CartRepository
import com.example.domain.repository.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.productdetail.contract.ProductDetailContract

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUIModel,
    private val visible: Boolean,
    private val cartRepository: CartRepository,
    private val recentRepository: RecentRepository,
) : ProductDetailContract.Presenter {
    private val _count: MutableLiveData<Int> = MutableLiveData(1)
    val count: LiveData<Int> get() = _count

    private var latestProduct: ProductUIModel? = null

    init {
        setUpProductDetail()
        isVisibleLatestProduct()
        setLatestProduct()
        addProductToRecent()
    }

    override fun setUpProductDetail() {
        view.setProductDetail(product)
    }

    override fun addProductToCart() {
        count.value?.let {
            CartProduct(product.toDomain(), it, true)
        }?.let {
            cartRepository.insert(it)
        }
    }

    override fun isVisibleLatestProduct() {
        view.setVisibleLatestProduct(visible)
    }

    override fun addProductToRecent() {
        recentRepository.findById(product.id)?.let {
            recentRepository.delete(it.id)
        }
        recentRepository.insert(product.toDomain())
    }

    override fun setProductCountDialog() {
        view.showProductCountDialog(product)
    }

    override fun setLatestProduct() {
        recentRepository.getRecent(1).firstOrNull()?.let { recent ->
            latestProduct = recent.toUIModel()
            view.showLatestProduct(latestProduct!!)
        }
    }

    override fun clickLatestProduct() {
        latestProduct?.let { view.navigateToDetail(it) }
    }

    override fun addProductCount(id: Long) {
        _count.value = _count.value?.plus(1)
    }

    override fun subtractProductCount(id: Long) {
        _count.value = _count.value?.minus(1)
    }
}
