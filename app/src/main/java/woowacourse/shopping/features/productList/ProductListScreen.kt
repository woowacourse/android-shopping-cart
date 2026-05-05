package woowacourse.shopping.features.productList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.data.MockData
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.features.constant.Format.formatPrice
import woowacourse.shopping.features.constant.ShoppingColor.APP_BAR_COLOR

@Composable
fun ProductListScreen(
    productList: List<Product>,
    isLastPage: Boolean,
    onCartClick: () -> Unit,
    loadProducts: () -> Unit,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        ProductListTopAppBar(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            onClick = onCartClick,
        )

        ProductCardGrid(
            products = productList,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            onProductClick = { product -> onProductClick(product) },
            onMoreClick = {
                loadProducts()
            },
            isLastPage = isLastPage,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListTopAppBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Shopping",
                fontSize = 20.sp,
            )
        },
        actions = {
            IconButton(
                onClick = onClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "장바구니 아이콘",
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color(APP_BAR_COLOR),
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
            ),
        windowInsets = WindowInsets(0, 0, 0, 0),
    )
}

@Composable
private fun ProductCardGrid(
    products: List<Product>,
    isLastPage: Boolean,
    onProductClick: (Product) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = products,
            key = { item -> item.id },
        ) { item ->
            ProductCard(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = item.imageUrl.value,
                productName = item.name.value,
                price = item.price.value,
                onClick = {
                    onProductClick(item)
                },
            )
        }
        if (!isLastPage) {
            item(
                span = { GridItemSpan(2) },
            ) {
                MoreButton(
                    onClick = { onMoreClick() },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 20.dp)
                            .background(
                                color = Color(0xFF555555),
                                shape = RoundedCornerShape(size = 45.dp),
                            ),
                )
            }
        }
    }
}

@Composable
private fun ProductCard(
    productName: String,
    price: Int,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier.clickable {
                onClick()
            },
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "상품 이미지",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(154.dp),
            contentScale = ContentScale.Crop,
        )
        ProductInfoColumn(
            modifier =
                Modifier
                    .padding(start = 6.dp, end = 9.dp, top = 8.dp, bottom = 12.dp),
            productName = productName,
            price = price,
        )
    }
}

@Composable
private fun ProductInfoColumn(
    productName: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            productName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            formatPrice(price),
            fontSize = 16.sp,
            color = Color.Gray,
        )
    }
}

@Composable
private fun MoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text("더보기")
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    ProductListScreen(
        productList = MockData.products,
        isLastPage = false,
        onCartClick = {},
        loadProducts = {},
        onProductClick = {},
    )
}
