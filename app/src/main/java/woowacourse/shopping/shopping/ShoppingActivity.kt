package woowacourse.shopping.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBAdapter
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.productdetail.ProductUiModel

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    private lateinit var binding: ActivityShoppingBinding
    private val presenter: ShoppingContract.Presenter by lazy {
        ShoppingPresenter(
            view = this,
            repository = ShoppingDBAdapter(
                shoppingDao = ShoppingDao(this)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)

        presenter.loadProducts()
    }

    override fun setUpShoppingView(products: List<ProductUiModel>) {
        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = ShoppingRecyclerSpanSizeManager()
        }
        binding.productRecyclerView.adapter = ShoppingRecyclerAdapter(
            products = products,
            recentViewedProducts = products,
            onProductClicked = ::navigateToProductDetailView
        )
    }

    private fun navigateToProductDetailView(product: ProductUiModel) {
        val intent = ProductDetailActivity.getIntent(this, product)
        startActivity(intent)
    }
}
