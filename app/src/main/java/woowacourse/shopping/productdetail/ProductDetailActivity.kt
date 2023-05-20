package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.utils.getSerializable
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.dao.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.productdetail.dialog.CartProductDialog

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var productModel: ProductModel
    private var recentProductModel: ProductModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExtras()

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.product_detail_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupProductDetailCartButton()

        initPresenter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.product_detail_close_action -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setupProductDetail(productModel: ProductModel) {
        binding.product = productModel
    }

    override fun setupRecentProductDetail(recentProductModel: ProductModel?) {
        binding.recentProduct = recentProductModel
        if (recentProductModel != null) {
            binding.latestRecentProductLayout.setOnClickListener {
                presenter.openProduct(recentProductModel)
            }
        }
    }

    override fun showCartProductDialog(productModel: ProductModel) {
        val dialog = CartProductDialog.createDialog(productModel)
        dialog.show(supportFragmentManager, "CartProductDialog")
    }

    override fun showProductDetail(productModel: ProductModel, recentProductModel: ProductModel?) {
        startProductDetailActivity(productModel, recentProductModel)
    }

    private fun initExtras() {
        productModel = intent.getSerializable(EXTRA_KEY_PRODUCT) ?: return finish()
        recentProductModel = intent.getSerializable(EXTRA_KEY_RECENT_PRODUCT)
    }

    private fun setupProductDetailCartButton() {
        binding.productDetailCartButton.setOnClickListener {
            presenter.setupCartProductDialog()
        }
    }

    private fun initPresenter() {
        val db = ShoppingDBOpenHelper(this).writableDatabase
        presenter = ProductDetailPresenter(
            this,
            productModel = productModel,
            recentProductModel = recentProductModel,
            recentProductRepository = RecentProductRepositoryImpl(RecentProductDao(db))
        )
    }

    private fun startProductDetailActivity(productModel: ProductModel, recentProductModel: ProductModel?) {
        val intent = createIntent(this, productModel, recentProductModel)
        startActivity(intent)
    }

    companion object {
        private const val EXTRA_KEY_PRODUCT = "product"
        private const val EXTRA_KEY_RECENT_PRODUCT = "recent_product"

        fun createIntent(context: Context, productModel: ProductModel, recentProductModel: ProductModel?): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(EXTRA_KEY_PRODUCT, productModel)
            intent.putExtra(EXTRA_KEY_RECENT_PRODUCT, recentProductModel)
            return intent
        }
    }
}
