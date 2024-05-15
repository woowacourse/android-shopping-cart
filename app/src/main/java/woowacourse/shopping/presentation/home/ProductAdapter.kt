package woowacourse.shopping.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.databinding.ItemProductBinding

class ProductAdapter(
    private val products: MutableList<Product>,
    private val productItemClickListener: ProductItemClickListener,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemProductBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_product, parent, false)

        return ProductViewHolder(binding, productItemClickListener)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun addProducts(products: List<Product>) {
        val position = products.size
        this.products += products
        notifyItemInserted(position)
    }

    class ProductViewHolder(private val binding: ItemProductBinding, productItemClickListener: ProductItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.productItemClickListener = productItemClickListener
        }

        fun bind(product: Product) {
            binding.product = product
        }
    }
}

@BindingAdapter("productThumbnail")
fun ImageView.setProductThumbnail(thumbnailUrl: String?) {
    Glide.with(context)
        .load(thumbnailUrl)
        .into(this)
}
