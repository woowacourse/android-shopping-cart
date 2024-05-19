package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding

class CartAdapter(private val onClickDeleteBtn: (position: Int) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var products: List<CartProductUi> = emptyList()

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding =
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CartViewHolder(binding, onClickDeleteBtn)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateProduct(newProducts: List<CartProductUi>) {
        products = newProducts
        notifyDataSetChanged()
    }

    class CartViewHolder(
        private val binding: ItemCartProductBinding,
        private val onClickDeleteBtn: (position: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProductUi) {
            binding.cartProduct = product
            binding.ivCartItemDelete.setOnClickListener {
                onClickDeleteBtn(absoluteAdapterPosition)
            }
        }
    }
}
