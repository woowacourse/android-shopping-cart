package woowacourse.shopping.presentation.productdetail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityDetailProductBinding
import woowacourse.shopping.domain.products

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
            .load(products[0].imageUrl)
            .placeholder(R.drawable.maxim_arabica)
            .fallback(R.drawable.maxim_arabica)
            .error(R.drawable.maxim_arabica)
            .into(binding.ivProductDetail)
        binding.tvProductDetailName.text = products[0].name
        binding.tvProductDetailPrice.text = "99,800원"
    }
}
