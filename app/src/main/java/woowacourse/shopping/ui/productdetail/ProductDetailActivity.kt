package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.basket.BasketDaoImpl
import woowacourse.shopping.data.datasource.basket.LocalBasketDataSource
import woowacourse.shopping.data.repository.BasketRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.util.extension.getParcelableExtraCompat
import woowacourse.shopping.util.extension.showImage

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View,
    OnMenuItemClickListener {
    private lateinit var binding: ActivityProductDetailBinding

    override val presenter: ProductDetailContract.Presenter by lazy {
        ProductDetailPresenter(
            view = this,
            basketRepository =
            BasketRepository(LocalBasketDataSource(BasketDaoImpl(ShoppingDatabase(this)))),
            product = intent.getParcelableExtraCompat(PRODUCT_KEY)!!
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        initView()
    }

    private fun initView() {
        binding.productDetailPresenter = presenter
        binding.tbProductDetail.setOnMenuItemClickListener(this)
    }

    override fun showProductImage(imageUrl: String) {
        binding.ivProduct.showImage(imageUrl)
    }

    override fun showProductName(name: String) {
        binding.tvProductName.text = name
    }

    override fun showProductPrice(amount: Int) {
        binding.tvProductPrice.text = getString(R.string.price_format, amount)
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
