package woowacourse.shopping.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.CartsImpl
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductsImpl

class ProductDetailActivity : AppCompatActivity(), CartButtonClickListener {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: Product
    private var toast: Toast? = null
    private val productKey by lazy {
        intent.getLongExtra(
            ProductDetailKey.EXTRA_PRODUCT_KEY,
            EXTRA_DEFAULT_VALUE,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        product = ProductsImpl.find(productKey)
        binding.product = product
        binding.cartButtonClickListener = this
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.ivProductImage)
    }

    override fun onClick() {
        CartsImpl.save(product)
        toast?.cancel()
        toast = Toast.makeText(this, "담겨짐!", Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_close -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val EXTRA_DEFAULT_VALUE = -1L

        fun startActivity(
            context: Context,
            productId: Long,
        ) = Intent(context, ProductDetailActivity::class.java).run {
            putExtra(ProductDetailKey.EXTRA_PRODUCT_KEY, productId)
            context.startActivity(this)
        }
    }
}
