package woowacourse.shopping.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.productRv
    }

    override fun showCartScreen() {
        TODO("Not yet implemented")
    }

    override fun showProductDetailScreen(position: Int) {
        TODO("Not yet implemented")
    }

    override fun addProducts(products: List<MainProductItemModel>) {
        TODO("Not yet implemented")
    }
}
