package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.cart.viewholder.CartItemViewHolder
import woowacourse.shopping.presentation.model.CartProductInfoModel

class CartAdapter(
    private val presenter: CartContract.Presenter,
) : RecyclerView.Adapter<CartItemViewHolder>() {

    private lateinit var binding: ItemCartBinding
    private val _products = mutableListOf<CartProductInfoModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(
            binding = binding,
            presenter = presenter,
        )
    }

    override fun getItemCount(): Int {
        return _products.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {}

    fun setItems(products: List<CartProductInfoModel>) {
        _products.clear()
        _products.addAll(products)
        notifyDataSetChanged()
    }
}
