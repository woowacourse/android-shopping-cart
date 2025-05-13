package woowacourse.shopping.presentation.productdetail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.databinding.ActivityDetailProductBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_product)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Glide
            .with(this)
            .load(DummyProducts.values[0].imageUrl)
            .placeholder(R.drawable.ic_delete)
            .fallback(R.drawable.ic_delete)
            .error(R.drawable.ic_delete)
            .into(binding.ivProductDetail)
        binding.tvProductDetailName.text = "aaaa"
        binding.tvProductDetailPrice.text = "99,800원"
    }
}
