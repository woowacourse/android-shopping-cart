package woowacourse.shopping.productList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.db.Product

class ProductRecyclerViewAdapter(
    private val values: List<Product>
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClick = { id -> Log.d("clicked", "Clicked $id") }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size


    inner class ViewHolder(
        private val binding: HolderProductBinding,
        private val onClick: (id: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.root.setOnClickListener { onClick(product.id) }
        }
    }
}

@BindingAdapter("imageUrl")
fun setImageViewResource(imageView: ImageView, resUrl: String) {
    Glide.with(imageView.context)
        .load(resUrl)
        .placeholder(R.drawable.product1)
        .fallback(android.R.drawable.ic_menu_report_image)
        .error(android.R.drawable.ic_menu_report_image)
        .thumbnail()
        .into(imageView)
}
