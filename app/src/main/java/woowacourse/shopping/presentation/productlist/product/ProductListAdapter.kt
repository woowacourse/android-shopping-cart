package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductListAdapter(
    products: List<ProductModel>,
    private val showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.Adapter<ProductItemViewHolder>() {

    private lateinit var binding: ItemProductBinding
    private val _products = products.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductItemViewHolder(binding, showProductDetail)
    }

    override fun getItemCount(): Int = _products.size

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(_products[position])
    }

    fun setItems(products: List<ProductModel>) {
        _products.clear()
        _products.addAll(products)
        notifyDataSetChanged()
    }
}
