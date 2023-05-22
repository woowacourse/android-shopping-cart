package woowacourse.shopping.presentation.productlist.product

import androidx.core.view.doOnAttach
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.common.CounterContract
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productlist.ProductListContract

class ProductItemViewHolder(
    private val binding: ItemProductBinding,
    private val presenter: ProductListContract.Presenter,
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context
    private lateinit var productModel: ProductModel
    private val counterPresenter: CounterContract.Presenter = binding.counterProductList.presenter

    init {
        itemViewClick()
        setAddButtonClick()
        setCounterMinusCLick()
        setCounterPlusCLick()
    }

    private fun itemViewClick() {
        itemView.setOnClickListener {
            showProductDetail(productModel)
        }
    }

    private fun showProductDetail(productModel: ProductModel) {
        context.startActivity(
            ProductDetailActivity.getIntent(
                context,
                productModel,
            ),
        )
    }

    private fun setAddButtonClick() {
        binding.buttonProductListAddCart.setOnClickListener {
            presenter.putProductInCart(productModel)
            presenter.updateCartProductInfoList()
            counterPresenter.updateCount(1)
        }
    }

    private fun setCounterMinusCLick() {
        binding.counterProductList.minusButton.setOnClickListener {
            counterPresenter.minusCount()
            presenter.updateCartProductCount(productModel, counterPresenter.counter.value.value)
            presenter.updateCartProductInfoList()
        }
    }

    private fun setCounterPlusCLick() {
        binding.counterProductList.plusButton.setOnClickListener {
            counterPresenter.plusCount()
            presenter.updateCartProductCount(productModel, counterPresenter.counter.value.value)
            presenter.updateCartProductInfoList()
        }
    }

    fun bind(product: ProductModel) {
        productModel = product
        setUpBinding(productModel)
        counterPresenter.updateCount(
            presenter.cartProductInfoList.value.findCountByProduct(
                productModel.id,
            ),
        )
    }

    private fun setUpBinding(product: ProductModel) {
        itemView.doOnAttach {
            binding.productModel = product
            binding.presenter = presenter
            binding.lifecycleOwner = itemView.findViewTreeLifecycleOwner()
        }
    }
}
