package woowacourse.shopping.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    Glide
        .with(this.context)
        .load(url)
        .fallback(R.drawable.ic_delete)
        .error(R.drawable.ic_delete)
        .into(this)
}

class ProductAdapter(
    private var items: List<Product>,
    private val onClick: (Product) -> Unit,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(list: List<Product>) {
        items = list
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        val binding: ItemProductBinding,
        val onClick: (Product) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentItem: Product? = null

        init {
            binding.root.setOnClickListener {
                currentItem?.let { onClick(it) }
            }
        }

        fun bind(item: Product) {
            binding.product = item
            currentItem = item
        }
    }
}
