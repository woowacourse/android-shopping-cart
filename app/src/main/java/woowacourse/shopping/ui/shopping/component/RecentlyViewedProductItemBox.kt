package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.network.NetworkMonitor
import woowacourse.shopping.repository.cart.InMemoryCartRepository
import woowacourse.shopping.repository.product.InMemoryProductRepository
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import woowacourse.shopping.ui.shopping.viewmodel.ProductListViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun RecentlyViewedProductsItemsBox(
    viewModel: ProductListViewModel,
    onClick: (Uuid) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.recently_viewed_products),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(viewModel.recentlyViewedProducts.products) { product ->
                Column(
                    modifier =
                        Modifier
                            .width(98.dp)
                            .clickable { onClick(product.productId) },
                    verticalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.productName,
                        modifier = Modifier.size(98.dp),
                    )
                    Text(
                        text = product.productName,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun RecentlyViewedProductsItemsBox() {
    val packageName = LocalContext.current.packageName
    val allProducts = ProductFixture.productList(packageName)

    val recentViewedProductsRepository =
        remember {
            object : RecentlyViewedProductsRepository {
                override suspend fun saveViewedProduct(productId: Uuid) = Unit

                override fun getRecentlyViewedProducts(): Flow<Products> = flowOf(Products(allProducts.take(5)))

                override suspend fun getLastViewedProduct(): Product? = allProducts.firstOrNull()
            }
        }
    val networkMonitor =
        remember {
            object : NetworkMonitor {
                override fun isOnline(): Flow<Boolean> = flowOf(true)
            }
        }

    val viewModel =
        remember {
            ProductListViewModel(
                recentViewedProductsRepository = recentViewedProductsRepository,
                productRepository = InMemoryProductRepository(packageName),
                cartRepository = InMemoryCartRepository(),
                networkMonitor = networkMonitor,
            )
        }

    RecentlyViewedProductsItemsBox(
        viewModel = viewModel,
        onClick = {},
    )
}
