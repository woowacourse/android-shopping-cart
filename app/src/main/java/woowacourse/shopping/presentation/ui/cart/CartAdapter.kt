package woowacourse.shopping.presentation.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.getPriceText

class CartAdapter(
    private val cartHandler: CartHandler,
    private var carts: List<Cart> = emptyList(),
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding, cartHandler)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(carts[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<Cart>) {
        carts = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return carts.size
    }
}

class CartViewHolder(private val binding: ItemCartBinding, val cartHandler: CartHandler) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var product: Product

    init {
        binding.ivClose.setOnClickListener {
            cartHandler.onDeleteClick(product)
        }
    }

    fun bind(item: Cart) {
        product = item.product
        binding.tvName.text = item.product.name
        Glide.with(binding.root.context)
            .load(item.product.imgUrl)
            .into(binding.ivCart)
        binding.tvName.text = item.product.name
        binding.tvPrice.text = getPriceText(binding.root.context, item.product.price)
    }
}
