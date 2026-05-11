package woowacourse.shopping.ui.productdetail.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.common.component.QuantityControlButton
import woowacourse.shopping.ui.common.component.ShoppingImage

@Composable
fun ProductDetailBody(
    product: Product,
    totalPrice: Int,
    count: Int,
    lastViewedProduct: Product?,
    modifier: Modifier = Modifier,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    onLastViewedProductClick: (Product) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (lastViewedProduct != null) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 35.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    LastViewedProductBanner(
                        lastViewedProduct = lastViewedProduct,
                        onClick = { onLastViewedProductClick(lastViewedProduct) },
                    )
                }
            }
        },
        containerColor = Color.White,
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ShoppingImage(
                model = product.imageUrl,
                contentDescription = "상품 상세 이미지",
                modifier = Modifier.height(360.dp),
            )
            ProductOption(
                productName = product.name,
                price = totalPrice,
                count = count,
                onIncreaseClick = onIncreaseClick,
                onDecreaseClick = onDecreaseClick,
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ProductOption(
    productName: String,
    price: Int,
    count: Int,
    modifier: Modifier = Modifier,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
) {
    val formatted = String.format("%,d", price)

    Column(modifier = modifier) {
        Text(
            text = productName,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 18.dp, top = 16.dp, bottom = 17.dp),
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "$formatted 원",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.W400,
            )

            QuantityControlButton(
                count = count,
                onIncreaseClick = onIncreaseClick,
                onDecreaseClick = onDecreaseClick,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, name = "상품 유닛")
private fun ProductUnitPreview() {
    val product1 =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )

    val product2 =
        Product(
            name = "PET보틀-정사각형(500ml)",
            price = Money(1000),
            imageUrl = "",
        )
    ProductDetailBody(
        product = product1,
        totalPrice = 30000,
        count = 3,
        onIncreaseClick = {},
        onDecreaseClick = {},
        lastViewedProduct = product2,
        modifier = Modifier,
        onLastViewedProductClick = {},
    )
}

@Composable
@Preview(showBackground = true, name = "상품 이름만")
private fun ProductOptionPreview() {
    val product =
        Product(
            name = "스피또",
            price = Money(1000),
            imageUrl = "",
        )
    ProductOption(
        productName = product.name,
        price = product.price.value,
        count = 3,
        onIncreaseClick = {},
        onDecreaseClick = {},
    )
}
