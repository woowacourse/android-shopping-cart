package woowacourse.shopping.view.main

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.detail.ProductDetailActivity

class ProductEventHandlerImpl(
    val context: Context,
    val binding: ItemProductBinding,
) : ProductEventHandler {
    override fun onBtnItemProductAddToCartSelected(quantity: MutableLiveData<Int>) {
        quantity.value = quantity.value?.inc()
    }

    override fun onQuantityMinusSelected(quantity: MutableLiveData<Int>) {
        quantity.value = quantity.value?.dec()
    }

    override fun onQuantityPlusSelected(quantity: MutableLiveData<Int>) {
        quantity.value = quantity.value?.inc()
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
