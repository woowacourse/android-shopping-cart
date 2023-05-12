package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartDBHelper
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.model.ProductUIModel.Companion.KEY_PRODUCT
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.contract.ProductDetailContract
import woowacourse.shopping.ui.productdetail.contract.presenter.ProductDetailPresenter
import woowacourse.shopping.utils.getSerializableExtraCompat
import woowacourse.shopping.utils.keyError

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initPresenter()
        initToolbar()
        initCartButton()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initPresenter() {
        presenter = ProductDetailPresenter(
            this,
            intent.getSerializableExtraCompat(KEY_PRODUCT) ?: return keyError(KEY_PRODUCT),
            CartDatabase(CartDBHelper(this).writableDatabase),
            RecentProductDatabase(this)
        )
    }

    private fun initCartButton() {
        binding.cartButton.setOnClickListener {
            presenter.addProductToCart()
            navigateToCart()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> finish()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun setProductDetail(product: ProductUIModel) {
        binding.product = product
    }

    private fun navigateToCart() {
        startActivity(CartActivity.getIntent(this))
    }

    companion object {
        fun getIntent(context: Context, product: ProductUIModel): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }
        }
    }
}
