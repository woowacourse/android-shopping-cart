package woowacourse.shopping.presentation.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.presentation.viewmodel.cart.CartViewModel

class CartAdapter(
    private val onClickHandler: CartViewHolder.OnClickHandler,
    private val cartViewModel: CartViewModel,
) : RecyclerView.Adapter<CartViewHolder>() {
    private var items: List<CartProduct> = emptyList<CartProduct>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, onClickHandler, cartViewModel)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item: CartProduct = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun replaceItems(newItems: List<CartProduct>) {
        items = newItems
        notifyDataSetChanged()
    }
}
