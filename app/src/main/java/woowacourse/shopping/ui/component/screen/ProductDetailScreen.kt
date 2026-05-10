package woowacourse.shopping.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.component.frame.CommonFrame
import woowacourse.shopping.ui.component.item.AmountModifyButton
import woowacourse.shopping.ui.component.item.ProductImage
import woowacourse.shopping.ui.component.item.toPriceString

@Composable
fun ProductDetailScreen(
    onAddRequest: () -> Unit,
    onClose: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    product: Product,
    amount: Int,
    modifier: Modifier = Modifier,
) {
    CommonFrame(
        headerContent = { ProductDetailHeader(onClose) },
        bodyContent = { ProductDetailBody(
            onAddRequest = onAddRequest,
            product = product,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            amount = amount,
        ) },
        modifier = modifier,
    )
}

@Composable
private fun ProductDetailHeader(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "닫기 버튼",
            modifier =
                Modifier
                    .size(16.dp)
                    .clickable(onClick = onClose),
            tint = Color.White,
        )
    }
}

@Composable
private fun ProductDetailBody(
    product: Product,
    onAddRequest: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    amount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ProductDetailInfo(
            product = product,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            amount = amount,
        )

        TextButton(
            onClick = onAddRequest,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = Color(0xFF04C09E)),
        ) {
            Text(
                text = "장바구니 담기",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun ProductDetailInfo(
    product: Product,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    amount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        val configuration = LocalConfiguration.current
        val maxWidth = configuration.screenWidthDp.dp
        ProductImage(
            imageUri = product.imageUri,
            modifier =
                Modifier
                    .size(maxWidth),
        )

        Text(
            text = product.name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(18.dp),
        )
        HorizontalDivider()
        Row(
            modifier =
                Modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = product.price.toPriceString(),
                fontSize = 20.sp,
            )
            AmountModifyButton(
                onIncrease = onIncrease,
                onDecrease = onDecrease,
                amount = amount
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        onAddRequest = {},
        onClose = {},
        product =
            Product(
                imageUri = "emptyUri",
                name = "우유",
                price = 100,
            ),
        onIncrease = {  },
        onDecrease = { },
        amount = 1
    )
}
