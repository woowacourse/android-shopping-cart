package woowacourse.shopping.presentation.productlist.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.MockProductDao
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.common.CounterContract
import woowacourse.shopping.presentation.model.ProductModel

class ProductItemViewHolder(
    private val binding: ItemProductBinding,
    private val showProductDetail: (ProductModel) -> Unit,
    private val showCartCount: () -> Unit,
) : RecyclerView.ViewHolder(binding.root), ProductItemContract.View {

    private val context = binding.root.context
    private lateinit var productModel: ProductModel
    private val counterPresenter: CounterContract.Presenter = binding.counterProductList.presenter
    private val productItemPresenter: ProductItemContract.Presenter by lazy {
        ProductItemPresenter(
            this,
            CartRepositoryImpl(CartDao(CartDbHelper(context)), MockProductDao),
        )
    }

    init {
        itemView.setOnClickListener { showProductDetail(productModel) }
    }

    fun bind(product: ProductModel) {
        productModel = product
        binding.productModel = product
        productItemPresenter.loadProductCount(product)
        binding.counterProductList.plusButton.setOnClickListener {
            counterPresenter.plusCount()
            productItemPresenter.changeProductCount(product, counterPresenter.counter.value!!.value)
            showCartCount()
        }
        binding.counterProductList.minusButton.setOnClickListener {
            counterPresenter.minusCount()
            counterPresenter.checkCounterVisibility()
            productItemPresenter.changeProductCount(product, counterPresenter.counter.value!!.value)
            showCartCount()
        }
        binding.buttonProductListAddCart.setOnClickListener {
            productItemPresenter.putProductInCart(product)
            counterPresenter.updateCount(1)
            counterPresenter.checkCounterVisibility()
            showCartCount()
        }
    }

    override fun setAddCartEnable(isEnabled: Boolean) {
        if (isEnabled) {
            binding.buttonProductListAddCart.visibility = View.VISIBLE
        } else {
            binding.buttonProductListAddCart.visibility = View.GONE
        }
    }

    override fun setProductCount(count: Int) {
        counterPresenter.updateCount(count)
        counterPresenter.checkCounterVisibility()
    }
}
