package woowacourse.shopping.util

import android.content.Context
import woowacourse.shopping.data.product.ProductDao

fun initProducts(context: Context) {
    val productDao = ProductDao(context)
    productDao.addProductEntity(
        name = "BMW i8",
        price = 13000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190529_183%2Fauto_1559133035619Mrf6z_PNG%2F20190529212501_Y1nsyfUj.png",
    )
    productDao.addProductEntity(
        name = "포르쉐 카이엔",
        price = 7000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220712_52%2Fauto_1657592866600a8C4k_PNG%2F20220712112738_wVnAbAoZ.png",
    )
    productDao.addProductEntity(
        name = "링컨 컨티넨탈",
        price = 5000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20200826_169%2Fauto_1598432748871YY7ah_PNG%2F20200826180547_GOQeTNRd.png",
    )
    productDao.addProductEntity(
        name = "포르쉐 타이칸 GTS",
        price = 12000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220707_93%2Fauto_1657154252364qU58P_PNG%2F20220707093720_s39RRXnC.png",
    )
    productDao.addProductEntity(
        name = "한국지엠 마티즈",
        price = 400,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191216_108%2Fauto_1576459952156eBbWJ_PNG%2F20191216103230_XgY333pW.png",
    )
    productDao.addProductEntity(
        name = "한국지엠 황금마티즈",
        price = 66666,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191216_108%2Fauto_1576459952156eBbWJ_PNG%2F20191216103230_XgY333pW.png",
    )

    productDao.addProductEntity(
        name = "한국지엠 티코",
        price = 100,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=http%3A%2F%2Fimgauto.naver.com%2F20170620_178%2Fauto_14979401212556uG7r_PNG%2F13554_2000_.png",
    )

    productDao.addProductEntity(
        name = "마세라티 기블리",
        price = 6000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220412_222%2Fauto_1649744422391G7FUf_PNG%2F20220412152012_cgt0dpvC.png",
    )

    productDao.addProductEntity(
        name = "아우디 A8",
        price = 18000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20210205_101%2Fauto_1612503585863g2uEr_PNG%2F20210205143938_MvJUSYv0.png",
    )

    productDao.addProductEntity(
        name = "메르세데스-벤츠 S클래스",
        price = 10000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220921_70%2Fauto_1663748987116ga7sL_PNG%2F20220921172938_E9f31l3l.png",
    )

    productDao.addProductEntity(
        name = "제네시스 G80",
        price = 4000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221025_113%2Fauto_1666672525475ahX6W_PNG%2F20221025133519_6s3HtiEf.png",
    )

    productDao.addProductEntity(
        name = "기아 K7 하이브리드",
        price = 2000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20201012_240%2Fauto_1602467609317sf99P_PNG%2F20201012105319_IKCTvOew.png",
    )

    productDao.addProductEntity(
        name = "현대 그랜저",
        price = 2000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220511_190%2Fauto_16522337997520h52J_PNG%2F20220511104952_pcU0etAp.png",
    )

    productDao.addProductEntity(
        name = "현대 에쿠스",
        price = 4000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191128_114%2Fauto_1574920869337nOXTj_PNG%2F20191128150107_69V7pu20.png",
    )

    productDao.addProductEntity(
        name = "폭스바겐 골프 GTI",
        price = 3000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221215_9%2Fauto_1671066673583UltC4_PNG%2F20221215101104_AeCKVute.png",
    )

    productDao.addProductEntity(
        name = "롤스로이스 팬텀",
        price = 60000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221125_198%2Fauto_1669344146553I2Nki_PNG%2F20221125114217_eFl3hRkq.png",
    )

    productDao.addProductEntity(
        name = "벤틀리 컨티넨탈 GT",
        price = 25000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230302_110%2Fauto_1677745790170anBcf_PNG%2F20230302172948_Zpcosg7Q.png",
    )

    productDao.addProductEntity(
        name = "람보르기니 우루스 S",
        price = 20000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221114_271%2Fauto_16683888057987pf7D_PNG%2F20221114101956_kLPNhlDY.png",
    )

    productDao.addProductEntity(
        name = "페라리 F8 스파이더",
        price = 32000,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190916_38%2Fauto_1568614360509yR9gN_PNG%2F20190916151225_8wUAit8g.png",
    )

    productDao.addProductEntity(
        name = "테슬라 모델3",
        price = 4400,
        itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png",
    )
}
