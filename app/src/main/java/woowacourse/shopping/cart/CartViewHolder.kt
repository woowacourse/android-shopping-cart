package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.databinding.ItemCartProductListBinding

class CartViewHolder(
    private val binding: ItemCartProductListBinding,
    onCartItemRemoveButtonViewClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { onCartItemRemoveButtonViewClick(adapterPosition) }
    }

    fun bind(cartProduct: CartProductModel) {
        Glide.with(binding.root.context)
            .load(cartProduct.product.picture)
            .into(binding.cartProductListPicture)
        binding.cartProductListTitle.text = cartProduct.product.title
        binding.cartProductListPrice.text = cartProduct.product.price.toString()
    }
}
