package woowacourse.shopping.presentation.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.ShoppingProduct
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingAdapter

@BindingAdapter("app:imageUrl")
fun loadImage(
    view: ImageView,
    url: String?,
) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(view)
    }
}

@BindingAdapter("app:product")
fun setShoppingItems(
    recyclerView: RecyclerView,
    products: List<Product>?,
) {
    products?.let {
        (recyclerView.adapter as? ShoppingAdapter)?.loadData(it)
    }
}

@BindingAdapter("app:shoppingProducts")
fun setShoppingProductItems(
    recyclerView: RecyclerView,
    shoppingProducts: List<ShoppingProduct>?,
) {
    shoppingProducts?.let {
        (recyclerView.adapter as? ShoppingAdapter)?.loadShoppingProductData(it)
    }
}

@BindingAdapter("app:recentProducts")
fun setRecentProductItems(
    recyclerView: RecyclerView,
    recentProducts: List<RecentProduct>?,
) {
    recentProducts?.let {
        (recyclerView.adapter as? ShoppingAdapter)?.loadRecentProductData(it)
    }
}

@BindingAdapter("app:price")
fun setPrice(
    view: TextView,
    price: Long?,
) {
    view.text = price?.let { view.context.getString(R.string.price_format, it) } ?: ""
}

@BindingAdapter("app:visibility")
fun setLoadMoreBtnVisibility(
    view: View,
    isVisible: Boolean?,
) {
    if (isVisible == true) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
