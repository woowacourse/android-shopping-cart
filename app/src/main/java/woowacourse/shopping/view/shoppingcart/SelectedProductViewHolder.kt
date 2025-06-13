package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.mapper.toProductDomain
import woowacourse.shopping.databinding.ItemSelectedProductBinding
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.product.OnQuantityControlListener

class SelectedProductViewHolder(
    private val binding: ItemSelectedProductBinding,
    onQuantityControlListener: OnQuantityControlListener,
    onRemoveProductListener: OnRemoveProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onRemoveProductListener = onRemoveProductListener
        binding.initQuantityControl.onClick = onQuantityControlListener
    }

    fun bind(shoppingProduct: ShoppingProduct) {
        binding.shoppingProduct = shoppingProduct
        binding.product = shoppingProduct.productId.toProductDomain()
        binding.initQuantityControl.quantity = shoppingProduct.quantity
        binding.initQuantityControl.productId = shoppingProduct.productId
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onQuantityControlListener: OnQuantityControlListener,
            onRemoveProductListener: OnRemoveProductListener,
        ): SelectedProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSelectedProductBinding.inflate(inflater, parent, false)
            return SelectedProductViewHolder(binding, onQuantityControlListener, onRemoveProductListener)
        }
    }
}
