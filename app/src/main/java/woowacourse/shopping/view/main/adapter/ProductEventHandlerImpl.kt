package woowacourse.shopping.view.main.adapter

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.detail.ProductDetailActivity

class ProductEventHandlerImpl(
    val context: Context,
    val binding: ItemProductBinding,
    val totalShoppingCartSize: MutableLiveData<Int>,
) : ProductEventHandler {
    override fun onBtnItemProductAddToCartSelected(quantity: MutableLiveData<Int>) {
        super.onQuantityPlusSelected(quantity)
        totalShoppingCartSize.value = totalShoppingCartSize.value?.inc()
    }

    override fun onQuantityMinusSelected(quantity: MutableLiveData<Int>) {
        super.onQuantityMinusSelected(quantity)
        totalShoppingCartSize.value = totalShoppingCartSize.value?.dec()
    }

    override fun onQuantityPlusSelected(quantity: MutableLiveData<Int>) {
        super.onQuantityPlusSelected(quantity)
        totalShoppingCartSize.value = totalShoppingCartSize.value?.inc()
    }

    override fun onProductSelected(product: Product) {
        context.startActivity(ProductDetailActivity.newIntent(context, product))
    }

    override fun whenQuantityChangedSelectView(quantity: MutableLiveData<Int>) {
        quantity.value?.let { quantityValue ->
            if (quantityValue < 1) {
                binding.btnItemProductAddToCart.visibility = View.VISIBLE
                binding.layoutProductQuantitySelector.visibility = View.GONE
            } else {
                binding.btnItemProductAddToCart.visibility = View.GONE
                binding.layoutProductQuantitySelector.visibility = View.VISIBLE
            }
        }
    }
}
