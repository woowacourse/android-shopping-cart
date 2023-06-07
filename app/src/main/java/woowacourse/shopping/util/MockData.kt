package woowacourse.shopping.util

val mockData = listOf(
    """
{
                "id" : 1,
                "name" : "BMW i8",
                "price" : 13000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190529_183%2Fauto_1559133035619Mrf6z_PNG%2F20190529212501_Y1nsyfUj.png"
}
    """.trimIndent(),
    """
{
                "id" : 2,
                "name" : "포르쉐 카이엔",
                "price" : 7000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220712_52%2Fauto_1657592866600a8C4k_PNG%2F20220712112738_wVnAbAoZ.png"
}
    """.trimIndent(),
    """
{
                "id" : 3,
                "name" : "링컨 컨티넨탈",
                "price" : 5000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20200826_169%2Fauto_1598432748871YY7ah_PNG%2F20200826180547_GOQeTNRd.png"
}
    """.trimIndent(),
    """
{
                "id" : 4,
                "name" : "포르쉐 타이칸 GTS",
                "price" : 12000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220707_93%2Fauto_1657154252364qU58P_PNG%2F20220707093720_s39RRXnC.png"
}
    """.trimIndent(),
    """
{
                "id" : 5,
                "name" : "한국지엠 마티즈",
                "price" : 400,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191216_108%2Fauto_1576459952156eBbWJ_PNG%2F20191216103230_XgY333pW.png"
}
    """.trimIndent(),
    """
{
                "id" : 6,
                "name" : "한국지엠 황금마티즈",
                "price" : 66666,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191216_108%2Fauto_1576459952156eBbWJ_PNG%2F20191216103230_XgY333pW.png"
}
    """.trimIndent(),
    """
{
                "id" : 7,
                "name" : "한국지엠 티코",
                "price" : 100,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=http%3A%2F%2Fimgauto.naver.com%2F20170620_178%2Fauto_14979401212556uG7r_PNG%2F13554_2000_.png"
}
    """.trimIndent(),
    """
{
                "id" : 8,
                "name" : "마세라티 기블리",
                "price" : 6000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220412_222%2Fauto_1649744422391G7FUf_PNG%2F20220412152012_cgt0dpvC.png"
}
    """.trimIndent(),
    """
{
                "id" : 9,
                "name" : "아우디 A8",
                "price" : 18000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20210205_101%2Fauto_1612503585863g2uEr_PNG%2F20210205143938_MvJUSYv0.png"
}
    """.trimIndent(),
    """
{
                "id" : 10,
                "name" : "메르세데스-벤츠 S클래스",
                "price" : 10000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220921_70%2Fauto_1663748987116ga7sL_PNG%2F20220921172938_E9f31l3l.png"
}
    """.trimIndent(),
    """
{
                "id" : 11,
                "name" : "제네시스 G80",
                "price" : 4000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221025_113%2Fauto_1666672525475ahX6W_PNG%2F20221025133519_6s3HtiEf.png"
}
    """.trimIndent(),
    """
{
                "id" : 12,
                "name" : "기아 K7 하이브리드",
                "price" : 2000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20201012_240%2Fauto_1602467609317sf99P_PNG%2F20201012105319_IKCTvOew.png"
}
    """.trimIndent(),
    """
{
                "id" : 13,
                "name" : "현대 그랜저",
                "price" : 2000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220511_190%2Fauto_16522337997520h52J_PNG%2F20220511104952_pcU0etAp.png"
}
    """.trimIndent(),
    """
{
                "id" : 14,
                "name" : "현대 에쿠스",
                "price" : 4000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191128_114%2Fauto_1574920869337nOXTj_PNG%2F20191128150107_69V7pu20.png"
}
    """.trimIndent(),
    """
{
                "id" : 15,
                "name" : "폭스바겐 골프 GTI",
                "price" : 3000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221215_9%2Fauto_1671066673583UltC4_PNG%2F20221215101104_AeCKVute.png"
}
    """.trimIndent(),
    """
{
                "id" : 16,
                "name" : "롤스로이스 팬텀",
                "price" : 60000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221125_198%2Fauto_1669344146553I2Nki_PNG%2F20221125114217_eFl3hRkq.png"
}
    """.trimIndent(),
    """
{
                "id" : 17,
                "name" : "벤틀리 컨티넨탈 GT",
                "price" : 25000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230302_110%2Fauto_1677745790170anBcf_PNG%2F20230302172948_Zpcosg7Q.png"
}
    """.trimIndent(),
    """
{
                "id" : 18,
                "name" : "람보르기니 우루스 S",
                "price" : 20000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221114_271%2Fauto_16683888057987pf7D_PNG%2F20221114101956_kLPNhlDY.png"
}
    """.trimIndent(),
    """
{
                "id" : 19,
                "name" : "페라리 F8 스파이더",
                "price" : 32000,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190916_38%2Fauto_1568614360509yR9gN_PNG%2F20190916151225_8wUAit8g.png"
}
    """.trimIndent(),
    """
{
                "id" : 20,
                "name" : "테슬라 모델3",
                "price" : 4400,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 21,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 22,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 23,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 24,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 25,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 26,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 27,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 28,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 29,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 30,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 31,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 32,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 33,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 34,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 35,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 36,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 37,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 38,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 39,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),
    """
{
                "id" : 40,
                "name" : "더미더미",
                "price" : 99999,
                "itemImage" : "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
}
    """.trimIndent(),

)
