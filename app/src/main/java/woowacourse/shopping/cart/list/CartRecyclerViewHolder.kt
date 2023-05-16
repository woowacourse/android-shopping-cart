package woowacourse.shopping.cart.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.CartUIModel

class CartRecyclerViewHolder(
    private val binding: ItemProductInCartBinding,
    cartProducts: CartUIModel,
    onClickRemove: (CartProductUIModel, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivCancel.setOnClickListener {
            onClickRemove(cartProducts.products[adapterPosition], adapterPosition)
        }
    }

    fun bind(cartProductUIModel: CartProductUIModel) {
        Glide.with(binding.root.context)
            .load(cartProductUIModel.product.imageUrl)
            .into(binding.ivProductImage)
        binding.tvProductName.text = cartProductUIModel.product.name
        binding.tvPrice.text = binding.root.context.getString(
            R.string.item_product_in_cart_price,
            cartProductUIModel.product.price
        )
    }
}
