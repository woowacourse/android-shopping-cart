package woowacourse.shopping

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemCartProductBinding

class CartProductAdapter(
    private val products: List<Product>,
    private val context: Context,
) : RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>() {
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
                .placeholder(R.drawable.maxim_arabica)
                .fallback(R.drawable.maxim_arabica)
                .error(R.drawable.maxim_arabica)
                .into(ivCartProduct)
        }
    }

    override fun getItemCount(): Int = products.size

    class CartProductViewHolder(
        val binding: ItemCartProductBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}
