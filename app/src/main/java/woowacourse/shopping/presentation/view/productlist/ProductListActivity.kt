package woowacourse.shopping.presentation.view.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepositoryImp
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import woowacourse.shopping.presentation.view.cart.CartActivity
import woowacourse.shopping.presentation.view.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.view.productlist.adpater.ProductListAdapter
import woowacourse.shopping.presentation.view.productlist.adpater.RecentProductListAdapter

class ProductListActivity : AppCompatActivity(), ProductContract.View {
    private lateinit var binding: ActivityProductListBinding

    private val presenter: ProductContract.Presenter by lazy {
        ProductListPresenter(
            this,
            recentProductRepository = RecentProductRepositoryImp(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)

        presenter.loadProductItems()
    }

    override fun onStart() {
        super.onStart()

        presenter.loadRecentProductItems()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                startActivity(CartActivity.createIntent(this))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setProductItemsView(products: List<ProductModel>) {
        binding.rvProductList.adapter = ProductListAdapter(products, ::onProductClickEvent)
    }

    override fun setRecentProductItemsView(recentProducts: List<RecentProductModel>) {
        binding.rvRecentProductList.adapter =
            RecentProductListAdapter(recentProducts, ::moveToActivity)
    }

    private fun onProductClickEvent(product: ProductModel) {
        presenter.saveRecentProduct(product.id)
        moveToActivity(product.id)
    }

    private fun moveToActivity(productId: Long) {
        val intent = ProductDetailActivity.createIntent(this, productId)
        startActivity(intent)
    }
}
