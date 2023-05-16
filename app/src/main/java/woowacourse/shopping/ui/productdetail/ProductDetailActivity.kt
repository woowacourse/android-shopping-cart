package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.ui.productdetail.ProductDetailContract.Presenter
import woowacourse.shopping.ui.productdetail.ProductDetailContract.View
import woowacourse.shopping.util.extension.getParcelableExtraCompat
import woowacourse.shopping.util.extension.setContentView
import woowacourse.shopping.util.extension.showImage
import woowacourse.shopping.util.factory.createProductDetailPresenter

class ProductDetailActivity : AppCompatActivity(), View, OnMenuItemClickListener {
    private lateinit var binding: ActivityProductDetailBinding
    override val presenter: Presenter by lazy {
        createProductDetailPresenter(this, this, intent.getParcelableExtraCompat(PRODUCT_KEY)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater).setContentView(this)
        initView()
    }

    private fun initView() {
        binding.productDetailPresenter = presenter
        binding.productDetailToolBar.setOnMenuItemClickListener(this)
    }

    override fun showProductImage(imageUrl: String) {
        binding.productImageView.showImage(imageUrl)
    }

    override fun showProductName(name: String) {
        binding.productNameTextView.text = name
    }

    override fun showProductPrice(amount: Int) {
        binding.productPriceTextView.text = getString(R.string.price_format, amount)
    }

    override fun navigateToBasketScreen() {
        startActivity(BasketActivity.getIntent(this))
        finish()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.close -> finish()
        }
        return true
    }

    companion object {
        private const val PRODUCT_KEY = "product_key"
        fun getIntent(context: Context, product: UiProduct): Intent =
            Intent(context, ProductDetailActivity::class.java).putExtra(PRODUCT_KEY, product)
    }
}
