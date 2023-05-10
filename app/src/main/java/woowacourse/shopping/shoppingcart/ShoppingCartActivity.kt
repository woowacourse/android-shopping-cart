package woowacourse.shopping.shoppingcart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBAdapter
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.productdetail.ProductUiModel

class ShoppingCartActivity : AppCompatActivity(), ShoppingCartContract.View {

    private val presenter: ShoppingCartContract.Presenter by lazy {
        ShoppingCartPresenter(
            view = this,
            repository = ShoppingDBAdapter(
                shoppingDao = ShoppingDao(this)
            )
        )
    }
    lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)
        presenter.loadShoppingCartProducts()
    }

    override fun setUpShoppingCartView(products: List<ProductUiModel>, onRemoved: (id: Int) -> Unit) {
        binding.recyclerViewCart.adapter = ShoppingCartRecyclerAdapter(products, onRemoved)
    }
}
