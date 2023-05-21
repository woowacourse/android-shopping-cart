package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.cart.viewholder.CartItemViewHolder
import woowacourse.shopping.presentation.common.NotifyAdapter
import woowacourse.shopping.presentation.model.CartProductInfoModel

class CartAdapter(
    private val presenter: CartContract.Presenter,
) : NotifyAdapter<CartProductInfoModel>() {

    private lateinit var binding: ItemCartBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(
            binding = binding,
            presenter = presenter,
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
}
