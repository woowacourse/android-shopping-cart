package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.mapper.toProductDomain
import woowacourse.shopping.databinding.ItemSelectedProductBinding
import woowacourse.shopping.domain.ShoppingProduct

class SelectedProductViewHolder(
    private val binding: ItemSelectedProductBinding,
    eventListener: OnRemoveProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onRemoveProductListener = eventListener
    }

    fun bind(shoppingProduct: ShoppingProduct) {
        binding.shoppingProduct = shoppingProduct
        binding.product = shoppingProduct.productId.toProductDomain()
        binding.initQuantityControl.shoppingProduct = shoppingProduct
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: OnRemoveProductListener,
        ): SelectedProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSelectedProductBinding.inflate(inflater, parent, false)
            return SelectedProductViewHolder(binding, eventListener)
        }
    }
}
