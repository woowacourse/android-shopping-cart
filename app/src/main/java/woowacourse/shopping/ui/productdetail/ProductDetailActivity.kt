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
import woowacourse.shopping.ui.productdetail.dialog.ProductDialogInterface
import woowacourse.shopping.ui.productdetail.dialog.ProductOrderDialog
import woowacourse.shopping.utils.getSerializableExtraCompat
import woowacourse.shopping.utils.keyError

class ProductDetailActivity :
    AppCompatActivity(),
    ProductDetailContract.View,
    ProductDialogInterface,
    ProductDetailListener {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var productOrderDialog: ProductOrderDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        setSupportActionBar(binding.toolbar)

        presenter = ProductDetailPresenter(
            this,
            intent.getSerializableExtraCompat(KEY_PRODUCT) ?: return keyError(KEY_PRODUCT),
            CartDatabase(CartDBHelper(this).writableDatabase),
            RecentProductDatabase(this),
        )

        binding.cartButton.setOnClickListener {
            presenter.setProductCountDialog()
        }

        binding.listener = this
        binding.latestProduct =
            intent.getSerializableExtraCompat(RECENT_KEY_PRODUCT) ?: return keyError(
                RECENT_KEY_PRODUCT,
            )
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

    override fun showProductCountDialog(product: ProductUIModel) {
        productOrderDialog = ProductOrderDialog(this, this, product)
        productOrderDialog.show()
    }

    override fun showLatestProduct(product: ProductUIModel) {
        binding.latestProduct = product
    }

    private fun navigateToCart() {
        productOrderDialog.dismiss()
        startActivity(CartActivity.from(this))
    }

    override fun addToCart() {
        presenter.addProductToCart()
        navigateToCart()
    }

    override fun increaseCount(id: Long) {
        presenter.addProductCount(id)
    }

    override fun decreaseCount(id: Long) {
        presenter.subtractProductCount(id)
    }

    override fun navigateToDetail(product: ProductUIModel) {
        startActivity(from(this, product, null))
        finish()
    }

    override fun clickLatestProduct() {
        presenter.clickLatestProduct()
    }

    companion object {
        private const val RECENT_KEY_PRODUCT = "recent_product"
        fun from(
            context: Context,
            product: ProductUIModel,
            recentProduct: ProductUIModel? = null,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
                putExtra(RECENT_KEY_PRODUCT, recentProduct)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }
}
