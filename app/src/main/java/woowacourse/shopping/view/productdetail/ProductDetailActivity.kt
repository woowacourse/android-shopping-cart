package woowacourse.shopping.view.productdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.PriceFormatter
import woowacourse.shopping.util.getParcelableCompat
import woowacourse.shopping.view.cart.CartActivity

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableCompat<ProductModel>(PRODUCT)
        if (product == null) {
            Toast.makeText(this, NOT_EXIST_DATA_ERROR, Toast.LENGTH_LONG).show()
            finish()
            return
        }
        binding.product = product
        Glide.with(binding.root.context).load(product.imageUrl).into(binding.imgProduct)
        binding.textPrice.text = getString(R.string.korean_won, PriceFormatter.format(product.price))
        binding.btnPutInCart.setOnClickListener {
            CartActivity.newIntent(this)
        }
    }

    companion object {
        const val PRODUCT = "PRODUCT"
        private const val NOT_EXIST_DATA_ERROR = "데이터가 넘어오지 않았습니다."
    }
}
