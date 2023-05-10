package woowacourse.shopping.shopping

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBAdapter
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shoppingcart.ShoppingCartActivity

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

    override fun setUpShoppingView(
        products: List<ProductUiModel>,
        recentViewedProducts: List<ProductUiModel>
    ) {
        val shoppingRecyclerAdapter = ShoppingRecyclerAdapter(
            products = products,
            recentViewedProducts = recentViewedProducts,
            onProductClicked = ::navigateToProductDetailView
        )
        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = ShoppingRecyclerSpanSizeManager(shoppingRecyclerAdapter::getItemViewType)
        }
        binding.productRecyclerView.adapter = shoppingRecyclerAdapter
        binding.imgShoppingCart.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
    }

    private fun navigateToProductDetailView(product: ProductUiModel) {
        val intent = ProductDetailActivity.getIntent(this, product)
        startActivity(intent)
        presenter.addToRecentViewedProduct(product.id)
    }
}
