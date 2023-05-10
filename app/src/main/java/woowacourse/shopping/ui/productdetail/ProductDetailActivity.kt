package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import java.text.DecimalFormat

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private val presenter: ProductDetailPresenter by lazy {
        ProductDetailPresenter(
            this,
            ProductRepositoryImpl(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.loadProduct(intent.getLongExtra(PRODUCT_ID, -1))
    }

    override fun setProduct(product: ProductDetailUIState) {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.ivProductDetail)

        binding.tvProductDetailName.text = product.name
        binding.tvProductDetailPrice.text =
            getString(R.string.product_price).format(DECIMAL_FORMAT.format(product.price))
//        binding.btnProductDetailAdd.setOnClickListener { }
    }

    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"
        private val DECIMAL_FORMAT = DecimalFormat("#,###")

        fun startActivity(context: Context, productId: Long) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
            context.startActivity(intent)
        }
    }
}
