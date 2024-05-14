package woowacourse.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.productList.ProductListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val productListFragment = ProductListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, productListFragment)
            .commit()
    }
}
