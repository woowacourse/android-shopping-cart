@file:Suppress("FunctionName")

package woowacourse.shopping.ui

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.MemoryProductRepository
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@Composable
fun ProductListScreen(
    products: List<Product>,
    onNavigateToCartClick: () -> Unit,
    onProductClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ProductListTopBar(
                onNavigateToCartClick = onNavigateToCartClick,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = innerPadding,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp),
        ) {
            items(
                items = products,
                key = { it.id },
            ) { product ->
                ProductItem(
                    product = product,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable { onProductClick(product.id) },
                )
            }
        }
    }
}

@Composable
private fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "${product.title} 상품 이미지",
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(154.dp)
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer),
        )
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            modifier =
                Modifier.padding(
                    horizontal = 7.5.dp,
                ),
        )
        Text(
            text = DecimalFormat("#,###원").format(product.price),
            color = Color(0xFF555555),
            modifier =
                Modifier.padding(
                    horizontal = 7.5.dp,
                ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListTopBar(
    onNavigateToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = "Shopping") },
        actions = {
            IconButton(onClick = onNavigateToCartClick) {
                Image(
                    painter = painterResource(R.drawable.shopping_cart_icon),
                    contentDescription = "장바구니 아이콘",
                    modifier = Modifier.size(22.dp),
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
private fun ProductItemPreview() {
    ProductItem(
        product =
            Product(
                id = 1,
                title = "동원 스위트콘",
                price = 99_800,
                imageUrl = "https://img.dongwonmall.com/dwmall/static_root/model_img/main/153/15327_1_a.jpg?f=webp&q=80",
            ),
    )
}

@Composable
@Preview(showBackground = true)
private fun ProductListScreenPreview() {
    AndroidShoppingTheme {
        ProductListScreen(
            products = MemoryProductRepository.getProducts(),
            onNavigateToCartClick = {},
            onProductClick = {},
        )
    }
}
