package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import woowacourse.shopping.ui.productdetail.viewmodel.ProductDetailViewModel
import woowacourse.shopping.ui.theme.buttonColor
import woowacourse.shopping.ui.theme.dividerColor
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun RecentlyViewedProductCard(
    viewModel: ProductDetailViewModel,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = dividerColor,
                shape = RoundedCornerShape(5.dp),
            )
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.last_viewed_product),
            color = buttonColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.W700,
        )
        Text(
            text = viewModel.lastViewedProduct?.productName ?: "",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun RecentlyViewedProductCardPreview() {
    val packageName = LocalContext.current.packageName
    val allProducts = ProductFixture.productList(packageName)
    val currentProduct = allProducts.last()

    val viewModel = remember {
        ProductDetailViewModel(
            recentViewedProductsRepository =
                object : RecentlyViewedProductsRepository {
                    override suspend fun saveViewedProduct(productId: Uuid) = Unit

                    override fun getRecentlyViewedProducts(): Flow<Products> =
                        flowOf(Products(listOf(currentProduct, allProducts.first())))

                    override suspend fun getLastViewedProduct(): Product? =
                        allProducts.firstOrNull()
                },
            currentProductId = currentProduct.productId,
        )
    }

    RecentlyViewedProductCard(
        viewModel = viewModel,
        onClick = {}
    )
}
