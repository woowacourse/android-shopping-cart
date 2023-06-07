package woowacourse.shopping.feature.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.cart.model.CartProductState
import woowacourse.shopping.feature.cart.model.CartProductState.Companion.MIN_COUNT_VALUE
import woowacourse.shopping.feature.product.model.ProductState

class ProductViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val binding = binding as ItemProductBinding

    fun bind(
        productState: ProductState,
        cartProductState: CartProductState?,
        onProductClick: (productState: ProductState) -> Unit,
        cartProductAddFab: (productState: ProductState) -> Unit,
        cartProductCountMinus: (cartProductState: CartProductState) -> Unit,
        cartProductCountPlus: (cartProductState: CartProductState) -> Unit
    ) {
        binding.product = productState
        hideCounterView()

        if (cartProductState != null) {
            showCounterView()
            binding.counterView.count = cartProductState.count
        }

        binding.root.setOnClickListener { onProductClick(productState) }
        binding.productAddFab.setOnClickListener {
            cartProductAddFab(productState)
            showCounterView()
            binding.counterView.count = MIN_COUNT_VALUE
        }
        binding.counterView.minusClickListener = {
            if (binding.counterView.count <= MIN_COUNT_VALUE) hideCounterView()
            if (cartProductState != null) {
                cartProductCountMinus(cartProductState)
            }
        }
        binding.counterView.plusClickListener = {
            if (cartProductState != null) {
                cartProductCountPlus(cartProductState)
            }
        }
    }

    private fun showCounterView() {
        binding.productAddFab.visibility = View.INVISIBLE
        binding.counterView.visibility = View.VISIBLE
    }

    private fun hideCounterView() {
        binding.productAddFab.visibility = View.VISIBLE
        binding.counterView.visibility = View.INVISIBLE
    }

    companion object {
        fun createInstance(parent: ViewGroup): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding)
        }
    }
}
