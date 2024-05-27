package woowacourse.shopping.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class CartAdapter(
    private val onClickExit: OnClickExit,
    private val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    private val onDecreaseProductQuantity: OnDecreaseProductQuantity,
) : ListAdapter<ProductUiModel, CartViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(
            getItem(position),
            onClickExit,
            onIncreaseProductQuantity,
            onDecreaseProductQuantity,
        )
    }

    companion object {
        private val diffCallback =
            object : DiffUtil.ItemCallback<ProductUiModel>() {
                override fun areItemsTheSame(
                    oldItem: ProductUiModel,
                    newItem: ProductUiModel,
                ): Boolean {
                    return oldItem.productId == newItem.productId
                }

                override fun areContentsTheSame(
                    oldItem: ProductUiModel,
                    newItem: ProductUiModel,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
