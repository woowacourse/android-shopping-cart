package woowacourse.shopping.feature.detail

import com.example.domain.repository.CartRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel
import woowacourse.shopping.util.convertToMoneyFormat
import java.time.LocalDateTime

class DetailPresenter(
    val view: DetailContract.View,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    product: ProductUiModel,
    recentProductUiModel: RecentProductUiModel?
) : DetailContract.Presenter {
    override var product: ProductUiModel = product
        private set
    override var recentProduct: RecentProductUiModel? = recentProductUiModel
        private set

    private val isRecentProduct: Boolean

    init {
        isRecentProduct = recentProduct?.let {
            if (product.id == it.productUiModel.id) return@let true
            return@let false
        } ?: false
    }

    override fun initScreen() {
        if (isRecentProduct || recentProduct == null) return view.hideRecentScreen()
        recentProduct?.let {
            view.setRecentScreen(
                it.productUiModel.name,
                convertToMoneyFormat(it.productUiModel.price)
            )
        }
    }

    override fun addCart() {
        cartRepository.addProduct(product.toDomain())
        view.showCartScreen()
    }

    override fun navigateRecentProductDetail() {
        recentProduct?.let {
            recentProductRepository.addRecentProduct(
                it.toDomain().copy(dateTime = LocalDateTime.now())
            )
            view.showRecentProductDetailScreen(it)
        }
    }

    override fun exit() {
        if (isRecentProduct) return view.navigateMainScreen()
        view.exitDetailScreen()
    }
}