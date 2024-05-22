package woowacourse.shopping.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.products.ProductUiModel
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

class CartAdapter(
    private val onClickExit: OnClickExit,
    private val onIncreaseProductQuantity: OnIncreaseProductQuantity,
    private val onDecreaseProductQuantity: OnDecreaseProductQuantity,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val productUiModels: MutableList<ProductUiModel> = mutableListOf()

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
            productUiModels[position],
            onClickExit,
            onIncreaseProductQuantity,
            onDecreaseProductQuantity,
        )
    }

    override fun getItemCount(): Int {
        return productUiModels.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeCartItems(productUiModels: List<ProductUiModel>) {
        this.productUiModels.clear()
        this.productUiModels.addAll(productUiModels)
        notifyDataSetChanged()
    }
}
