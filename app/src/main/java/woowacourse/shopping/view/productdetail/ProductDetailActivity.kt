package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.RecentViewedDbRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.getParcelableCompat
import woowacourse.shopping.view.cart.CartActivity

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter

    private lateinit var productData: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ProductDetailPresenter(this, CartDbRepository(this), RecentViewedDbRepository(this))
        getData()
        bindView()
        presenter.updateRecentViewedProducts(productData.id)
    }

    private fun getData() {
        intent.getParcelableCompat<ProductModel>(PRODUCT)?.let { productData = it }
    }

    private fun bindView() {
        binding.product = productData
        binding.presenter = presenter
    }

    override fun startCartActivity() {
        val intent = CartActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.close -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val PRODUCT = "PRODUCT"

        fun newIntent(context: Context, product: ProductModel): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT, product)
            return intent
        }
    }
}
