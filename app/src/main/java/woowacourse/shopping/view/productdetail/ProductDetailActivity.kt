package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.RecentViewedDbRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.PriceFormatter
import woowacourse.shopping.util.getParcelableCompat
import woowacourse.shopping.view.cart.CartActivity

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter

    private lateinit var product: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ProductDetailPresenter(this, CartDbRepository(this), RecentViewedDbRepository(this))
        getData()
        bindView()
        presenter.updateRecentViewedProducts(product.id)
    }

    private fun getData() {
        val product = intent.getParcelableCompat<ProductModel>(PRODUCT)
        if (product == null) {
            Toast.makeText(this, NOT_EXIST_DATA_ERROR, Toast.LENGTH_LONG).show()
            finish()
            return
        }
    }

    private fun bindView() {
        binding.product = product
        binding.presenter = presenter
        Glide.with(binding.root.context).load(product.imageUrl).into(binding.imgProduct)
        binding.textPrice.text = getString(R.string.korean_won, PriceFormatter.format(product.price))
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
        private const val NOT_EXIST_DATA_ERROR = "데이터가 넘어오지 않았습니다."

        fun newIntent(context: Context, product: ProductModel): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT, product)
            return intent
        }
    }
}
