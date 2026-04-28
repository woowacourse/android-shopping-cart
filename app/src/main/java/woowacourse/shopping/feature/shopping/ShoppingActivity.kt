package woowacourse.shopping.feature.shopping

import android.R.attr.data
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.component.ShoppingAppBar
import woowacourse.shopping.core.data.ProductData
import woowacourse.shopping.core.model.Product
import woowacourse.shopping.feature.shopping.ui.ProductCard
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ShoppingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mockData = ProductData.products
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                ShoppingScreen(
                    data = mockData,
                )
            }
        }
    }
}

@Composable
fun ShoppingScreen(
    data: ImmutableList<Product>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Text(
                        text = "Shopping",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "쇼핑 카트",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp),
                    )
                },
                modifier = modifier.fillMaxWidth(),
            )
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        ShoppingContents(
            products = data,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun ShoppingContents(
    products: ImmutableList<Product>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(20.dp),
    ) {
        items(
            items = products,
            key = { product -> product.id },
        ) { product ->
            ProductCard(
                product.imageUrl,
                product.name,
                product.price,
            )
        }
    }
}

@Preview
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen(
        data = emptyList<Product>().toImmutableList(),
    )
}
