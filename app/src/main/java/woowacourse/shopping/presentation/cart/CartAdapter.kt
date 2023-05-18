package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.cart.viewholder.CartItemViewHolder
import woowacourse.shopping.presentation.model.CartProductInfoModel

class CartAdapter(
    private val deleteItem: (CartProductInfoModel) -> Unit,
) : ListAdapter<CartProductInfoModel, CartItemViewHolder>(diffCallback()) {
    private lateinit var binding: ItemCartBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(binding, deleteItem)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        fun diffCallback() = object : DiffUtil.ItemCallback<CartProductInfoModel>() {
            override fun areItemsTheSame(
                oldItem: CartProductInfoModel,
                newItem: CartProductInfoModel,
            ): Boolean = oldItem.productModel.id == newItem.productModel.id

            override fun areContentsTheSame(
                oldItem: CartProductInfoModel,
                newItem: CartProductInfoModel,
            ): Boolean = oldItem == newItem
        }
    }
}
