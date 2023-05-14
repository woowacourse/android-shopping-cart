package woowacourse.shopping.ui.detailedProduct

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityDetailedProductBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.utils.ActivityUtils
import woowacourse.shopping.utils.getSerializableExtraCompat

class DetailedProductActivity : AppCompatActivity(), DetailedProductContract.View {
    private lateinit var binding: ActivityDetailedProductBinding
    private lateinit var presenter: DetailedProductContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initPresenter()
        initToolbar()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_product)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initPresenter() {
        presenter = DetailedProductPresenter(
            this,
            intent.getSerializableExtraCompat(KEY_PRODUCT)
                ?: return ActivityUtils.keyError(this, KEY_PRODUCT),
            CartDatabase(this),
            RecentProductDatabase(this)
        )
        binding.presenter = presenter
        presenter.setUpProductDetail()
        presenter.addProductToRecent()
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

    override fun navigateToCart() {
        startActivity(CartActivity.getIntent(this))
    }

    companion object {
        private const val KEY_PRODUCT = "KEY_PRODUCT"
        fun getIntent(context: Context, product: ProductUIModel): Intent {
            return Intent(context, DetailedProductActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }
        }
    }
}
