package woowacourse.shopping.productdetail.contract.presenter

import com.domain.model.CartRepository
import com.domain.model.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.productdetail.contract.ProductDetailContract

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUIModel,
    private val cartRepository: CartRepository,
    private val recentRepository: RecentRepository,
) : ProductDetailContract.Presenter {
    override val count get() = _count
    private var _count = 1
        set(value) {
            val validatedValue = if (value >= 1) value else 1
            field = validatedValue
        }

    override fun setUp() {
        setupView()
        setUpProduct()
    }

    override fun setUpCountView() {
        view.setCount(_count)
        view.setUpCountView()
    }

    private fun setupView() {
        view.setupView()
    }

    private fun setUpProduct() {
        view.setProductDetail(product)
        recentRepository.findById(product.id)?.let {
            recentRepository.delete(it.id)
        }
        recentRepository.insert(product.toDomain())
    }

    override fun addCart() {
        cartRepository.insert(product.toDomain(), _count)
        view.navigateToCart()
    }

    override fun onClickCart() {
        view.showCartDialog(product)
    }

    override fun increaseCount() {
        _count++
    }

    override fun decreaseCount() {
        _count--
    }

    override fun manageRecentView() {
        val recentProducts = recentRepository.getRecent(10)
        if (recentProducts.isEmpty()) {
            view.disappearRecent()
        } else {
            view.displayRecent(recentProducts[0].toUIModel())
        }
    }
}
