package woowacourse.shopping.feature.products

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
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
import woowacourse.shopping.core.designsystem.component.OfflineBanner
import woowacourse.shopping.core.designsystem.theme.ExtraLightGray
import woowacourse.shopping.feature.products.component.LoadButton
import woowacourse.shopping.feature.products.component.ProductItem
import woowacourse.shopping.feature.products.component.RecentProductItem
import woowacourse.shopping.feature.products.component.ProductsTopAppBar
import woowacourse.shopping.feature.products.model.ShoppingProductInfo

@Composable
fun ProductsScreen(
    products: ImmutableList<ShoppingProductInfo>,
    recentProducts: ImmutableList<ShoppingProductInfo>,
    isLastPage: Boolean,
    formattedCartItemCount: String,
    isOnline: Boolean,
    onCartClick: () -> Unit,
    onProductClick: (id: String) -> Unit,
    onAddClick: (id: String) -> Unit,
    onIncreaseClick: (id: String) -> Unit,
    onDecreaseClick: (id: String) -> Unit,
    onLoadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        if (!isOnline) {
            OfflineBanner()
        }
        ProductsTopAppBar(
            onClick = onCartClick,
            formattedCartItemCount = formattedCartItemCount
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (recentProducts.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)) {
                        Text(
                            text = "최근 본 상품",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W700
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            recentProducts.forEach {
                                RecentProductItem(
                                    productImageUrl = it.productImageUrl,
                                    productName = it.productName,
                                    onClick = { onProductClick(it.id) },
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }
                    }
                }

                item(span = { GridItemSpan(2) }) {
                    HorizontalDivider(
                        thickness = 7.dp,
                        color = Color.ExtraLightGray
                    )
                }
            }

            items(
                items = products,
                key = { it.id },
            ) { product ->
                ProductItem(
                    productImageUrl = product.productImageUrl,
                    productName = product.productName,
                    formattedPrice = product.formattedPrice,
                    onClick = { onProductClick(product.id) },
                    formattedQuantity = product.formattedQuantity,
                    onAddClick = { onAddClick(product.id) },
                    onIncreaseClick = { onIncreaseClick(product.id) },
                    onDecreaseClick = { onDecreaseClick(product.id) },
                    modifier = Modifier.padding(
                        start = if (products.indexOf(product) % 2 == 0) 20.dp else 0.dp,
                        end = if (products.indexOf(product) % 2 != 0) 20.dp else 0.dp
                    )
                )
            }

            item(span = { GridItemSpan(2) }) {
                if (!isLastPage) {
                    LoadButton(
                        onClick = onLoadClick,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val products =
        List(50) {
            ShoppingProductInfo(
                id = it.toString(),
                productImageUrl = "",
                productName = "$it 번 상품",
                formattedPrice = "$it 원",
                formattedQuantity = "1",
            )
        }.toImmutableList()

    ProductsScreen(
        products = products,
        recentProducts = products.take(10).toImmutableList(),
        isLastPage = false,
        formattedCartItemCount = "1",
        isOnline = true,
        onCartClick = {},
        onProductClick = {},
        onAddClick = {},
        onIncreaseClick = {},
        onDecreaseClick = {},
        onLoadClick = {},
    )
}
