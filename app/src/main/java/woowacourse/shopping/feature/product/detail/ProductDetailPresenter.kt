package woowacourse.shopping.feature.product.detail

import com.example.domain.repository.CartRepository
import woowacourse.shopping.databinding.DialogSelectCountBinding
import woowacourse.shopping.model.CartProductState.Companion.MAX_COUNT_VALUE
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.RecentProductState

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    override val product: ProductState?,
    override val recentProduct: RecentProductState?,
    private val cartRepository: CartRepository
) : ProductDetailContract.Presenter {

    private var count = MIN_COUNT_VALUE

    override fun loadProduct() {
        if (!isValidProduct()) return
        product!!

        view.setViewContent(product)
    }

    override fun loadRecentProduct() {
        view.setMostRecentViewContent(recentProduct)
    }

    override fun selectCount() {
        view.showSelectCountDialog()
    }

    override fun addCartProduct(count: Int) {
        if (!isValidProduct()) return
        product!!

        cartRepository.addProduct(product.id, count)
        view.showCart()
    }

    override fun minusCount(selectCountDialogBinding: DialogSelectCountBinding) {
        count--
        if (count < MIN_COUNT_VALUE) count++
        view.setCount(selectCountDialogBinding, count)
    }

    override fun plusCount(selectCountDialogBinding: DialogSelectCountBinding) {
        count++
        if (MAX_COUNT_VALUE < count) count--
        view.setCount(selectCountDialogBinding, count)
    }

    private fun isValidProduct(): Boolean {
        if (product == null) {
            view.showAccessError()
            view.closeProductDetail()
            return false
        }
        return true
    }
}
