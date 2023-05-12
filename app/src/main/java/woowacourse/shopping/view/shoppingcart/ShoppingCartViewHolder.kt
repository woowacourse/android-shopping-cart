package woowacourse.shopping.view.shoppingcart

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ShoppingCartViewHolder(
    private val binding: ItemProductInCartBinding,
    cartProducts: List<CartProductUIModel>,
    onClickRemove: (ProductUIModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivCancel.setOnClickListener {
            onClickRemove(cartProducts[adapterPosition].productUIModel)
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
