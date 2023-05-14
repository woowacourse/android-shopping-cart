package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductInCartBinding

class CartRecyclerViewHolder(
    private val binding: ItemProductInCartBinding,
    cartProducts: CartUIModel,
    onClickRemove: (ProductUIModel, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivCancel.setOnClickListener {
            onClickRemove(cartProducts.products[adapterPosition], adapterPosition)
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
