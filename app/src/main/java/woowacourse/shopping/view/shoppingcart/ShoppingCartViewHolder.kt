package woowacourse.shopping.view.shoppingcart

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.uimodel.CartProductsUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ShoppingCartViewHolder(
    private val binding: ItemProductInCartBinding,
    cartProducts: CartProductsUIModel,
    onClickRemove: (ProductUIModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivCancel.setOnClickListener {
            onClickRemove(cartProducts.products[adapterPosition])
        }
    }

    fun bind(productUIModel: ProductUIModel) {
        Glide.with(binding.root.context)
            .load(productUIModel.url)
            .into(binding.ivProductImage)
        binding.tvProductName.text = productUIModel.name
        binding.tvPrice.text = productUIModel.price.toString()
    }
}
