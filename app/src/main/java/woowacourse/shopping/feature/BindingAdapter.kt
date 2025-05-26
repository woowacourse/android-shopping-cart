package woowacourse.shopping.feature

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.feature.cart.adapter.CartAdapter
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter

@BindingAdapter("imgUrl")
fun ImageView.loadImageFromUrl(url: String) {
    Glide.with(this.context).load(url).into(this)
}

@BindingAdapter("cartItems")
fun RecyclerView.bindCartItems(items: List<Cart>?) {
    if (adapter is CartAdapter && items != null) {
        (adapter as CartAdapter).setItems(items)
    }
}

@BindingAdapter("items")
fun RecyclerView.bindItems(items: List<Any>?) {
    if (adapter is GoodsAdapter && items != null) {
        (adapter as GoodsAdapter).setItems(items)
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("count")
fun CustomCartQuantity.setQuantity(count: Int) {
    setCount(count)
}

@BindingAdapter("name")
fun CustomLastViewed.setName(name: String) {
    setName(name)
}
