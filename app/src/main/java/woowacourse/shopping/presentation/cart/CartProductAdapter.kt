package woowacourse.shopping.presentation.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.Product

class CartProductAdapter(
    private val context: Context,
    private val onDeleteClick: (Product) -> Unit,
) : RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartProductViewHolder {
        val view =
            ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CartProductViewHolder,
        position: Int,
    ) {
        val cartProduct = products[position]
        holder.binding.apply {
            tvCartProductName.text = cartProduct.name
            tvCartProductPrice.text = cartProduct.price.toString()
            Glide
                .with(context)
                .load(cartProduct.imageUrl)
                .placeholder(R.drawable.ic_delete)
                .fallback(R.drawable.ic_delete)
                .error(R.drawable.ic_delete)
                .into(ivCartProduct)
        }
        holder.binding.ibCartProductDelete.setOnClickListener {
            onDeleteClick(cartProduct)
            Toast.makeText(context, "상품이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = products.size

    fun setData(list: List<Product>) {
        products = list
        notifyDataSetChanged()
    }

    class CartProductViewHolder(
        val binding: ItemCartProductBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}
