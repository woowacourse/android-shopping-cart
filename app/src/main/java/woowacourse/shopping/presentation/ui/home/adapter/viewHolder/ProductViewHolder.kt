package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.ui.home.SetClickListener
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

class ProductViewHolder(
    private val binding: ItemProductBinding,
    clickProduct: SetClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setClickListener = clickProduct
    }

    fun bind(data: Products, shoppingCart: List<ProductInCartUiState>) {
        binding.product = data.product

        binding.fabItemProductQuantity.setOnClickListener {
            binding.fabItemProductQuantity.visibility = View.INVISIBLE
            binding.layoutQuantity.root.visibility = View.VISIBLE
            binding.layoutQuantity.tvQuantityAmount.text = "1"
        }

        val cart: ProductInCartUiState = shoppingCart.find { it.product.id == data.product.id }
            ?: ProductInCartUiState(
                product = data.product,
                quantity = 0,
            )

        binding.shoppingCart = cart
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemProductBinding =
            ItemProductBinding.inflate(layoutInflater, parent, false)
    }
}
