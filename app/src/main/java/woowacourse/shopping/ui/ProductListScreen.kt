package woowacourse.shopping.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.CartActivity
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.theme.topAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    products: Products,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shopping",
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(id = R.drawable.shopping_cart_icon),
                        contentDescription = "shoppingCart",
                        modifier =
                            Modifier
                                .padding(20.dp)
                                .size(24.dp)
                                .clickable {
                                    val intent = Intent(context, CartActivity::class.java)
                                    context.startActivity(intent)
                                }
                    )
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = topAppBarColor,
                        titleContentColor = Color.White,
                    ),
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(products.products) { product ->
                    ProductItem(product)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductListScreenPreview() {
    ProductListScreen(
        products = Products(ProductFixture.productList),
    )
}
