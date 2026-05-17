package woowacourse.shopping.data.mock

import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

object MockProductSeedData {
    val products =
        listOf(
            // ===== 시즌 음료 / 주스 / 스무디 (page 1) =====
            Product(
                id = 1,
                name = ProductName("꿀수박주스"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260429172504_1777451104073_ubR01Dh_wM.png"),
                price = Price(4500),
            ),
            Product(
                id = 2,
                name = ProductName("수박소르베 밀키 스무디"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260429172642_1777451202647_pSL9jKZaL6.png"),
                price = Price(5500),
            ),
            Product(
                id = 3,
                name = ProductName("수박 리치코코 슬러시"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260429172827_1777451307817_3OM_KV_54e.png"),
                price = Price(5300),
            ),
            Product(
                id = 4,
                name = ProductName("파인망고코코 스무디"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260429173000_1777451400716_gEDa5liT8h.png"),
                price = Price(5300),
            ),
            Product(
                id = 5,
                name = ProductName("자몽 톡톡 스무디"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260429173124_1777451484221_DbGYiTq_EF.png"),
                price = Price(4900),
            ),
            Product(
                id = 6,
                name = ProductName("제로 레몬말차 아이스티"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260429173244_1777451564019_W1OpjQHJz4.png"),
                price = Price(3900),
            ),
            Product(
                id = 7,
                name = ProductName("저당 꿀배 XO야쿠르트"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260401192153_1775038913170_2UFRSEFHv0.png"),
                price = Price(4500),
            ),
            // ===== 왕메가 / 디카페인 (page 2) =====
            Product(
                id = 8,
                name = ProductName("귤 톡톡 젤리스무디"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20250320003615_1742398575781_Dy89tEHQTL.jpg"),
                price = Price(4900),
            ),
            Product(
                id = 9,
                name = ProductName("왕메가카페라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20250320004326_1742399006488_yI4cvwXy6N.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 10,
                name = ProductName("디카페인 왕메가카페라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20250320004527_1742399127150_aZXw3Wbf4H.jpg"),
                price = Price(4800),
            ),
            Product(
                id = 11,
                name = ProductName("디카페인 라이트 바닐라 아몬드라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20250402185107_1743587467742_jeeQmgRshX.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 12,
                name = ProductName("왕메가사과유자"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20250320004730_1742399250273_UPT0oK5fSb.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 13,
                name = ProductName("왕메가헛개리카노"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20250320001838_1742397518902_MCGDHqjxXi.jpg"),
                price = Price(3900),
            ),
            Product(
                id = 14,
                name = ProductName("(HOT)디카페인 헛개리카노"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20250320002019_1742397619030_g5iEBTRsp7.jpg"),
                price = Price(2800),
            ),
            // ===== 주스 / 디카페인 (page 3) =====
            Product(
                id = 15,
                name = ProductName("코코넛 커피 스무디"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132830_1717993710301_PxTeTM9Suh.jpg"),
                price = Price(5300),
            ),
            Product(
                id = 16,
                name = ProductName("딸기주스"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610114741_1717987661472_LqJw6WITHO.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 17,
                name = ProductName("딸기바나나주스"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610114817_1717987697259_APSvRawmJi.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 18,
                name = ProductName("디카페인 에스프레소"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610110159_1717984919074_jg4RYBdr_J.jpg"),
                price = Price(2500),
            ),
            // ===== 디카페인 음료 (page 4) =====
            Product(
                id = 19,
                name = ProductName("디카페인 카페라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132005_1717993205762_9xKCxaSb9P.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 20,
                name = ProductName("디카페인 카푸치노"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132018_1717993218605_64Ij4AhmCE.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 21,
                name = ProductName("디카페인 바닐라라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610113234_1717986754069_tSPQqoiPj6.jpg"),
                price = Price(4200),
            ),
            Product(
                id = 22,
                name = ProductName("디카페인 헤이즐넛 라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132034_1717993234457_aGDHQ0U0Ap.jpg"),
                price = Price(4200),
            ),
            Product(
                id = 23,
                name = ProductName("디카페인 카라멜마끼아또"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610131917_1717993157515_k1yeakRwwv.jpg"),
                price = Price(4500),
            ),
            // ===== 시즌 라떼 / 핫 음료 (page 5) =====
            Product(
                id = 24,
                name = ProductName("오레오초코라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132638_1717993598875_AQ4q6GUaJD.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 25,
                name = ProductName("토피넛라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132535_1717993535868_Dk0SpiNnAo.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 26,
                name = ProductName("흑당버블밀크티라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610112956_1717986596859_dSAYDVVaV2.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 27,
                name = ProductName("핫초코"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610110045_1717984845295_sGoEnUlQpO.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 28,
                name = ProductName("녹차라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610104456_1717983896154_TxgXQHRHiM.jpg"),
                price = Price(3900),
            ),
            Product(
                id = 29,
                name = ProductName("로얄밀크티라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610105616_1717984576627_IStTaWZY3_.jpg"),
                price = Price(3900),
            ),
            Product(
                id = 30,
                name = ProductName("흑당라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610113159_1717986719673_UjpcQYhtmh.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 31,
                name = ProductName("흑당밀크티라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610113123_1717986683549_Q8E9tuNYJL.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 32,
                name = ProductName("흑당버블라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610113023_1717986623453_FuG0kt97Jg.jpg"),
                price = Price(4500),
            ),
            // ===== 에스프레소 클래식 (page 6) =====
            Product(
                id = 33,
                name = ProductName("카페모카"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610105838_1717984718108_ZB6aalHqIU.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 34,
                name = ProductName("카푸치노"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610105852_1717984732750_WEt0KXVcnQ.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 35,
                name = ProductName("콜드브루라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610105923_1717984763591_cKSGllWUzt.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 36,
                name = ProductName("콜드브루오리지널"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610105941_1717984781078_rdCUXhwycL.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 37,
                name = ProductName("헤이즐넛라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610110117_1717984877061_sLiVRNPq6J.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 38,
                name = ProductName("헤이즐넛아메리카노"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610110133_1717984893137_AqJAuHaq0t.jpg"),
                price = Price(2500),
            ),
            Product(
                id = 39,
                name = ProductName("꿀아메리카노"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610133054_1717993854434_bTty_9u3br.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 40,
                name = ProductName("바닐라라떼"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132249_1717993369864_VIG6IR2byt.jpg"),
                price = Price(3500),
            ),
            // ===== 스무디 / 프라페 (page 7) =====
            Product(
                id = 41,
                name = ProductName("딸기요거트스무디"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132117_1717993277823_KRGDVA4aeJ.jpg"),
                price = Price(5300),
            ),
            Product(
                id = 42,
                name = ProductName("딸기퐁크러쉬"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132132_1717993292542_TOXDd7N_72.jpg"),
                price = Price(5500),
            ),
            Product(
                id = 43,
                name = ProductName("리얼초코프라페"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132157_1717993317456_RCHCZKkudt.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 44,
                name = ProductName("망고요거트스무디"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132210_1717993330781_04rKlgIKk6.jpg"),
                price = Price(5300),
            ),
            Product(
                id = 45,
                name = ProductName("민트프라페"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610115353_1717988033471_n8WL4rST4C.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 46,
                name = ProductName("바나나퐁크러쉬"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132238_1717993358631_MGPEpkvNh1.jpg"),
                price = Price(5500),
            ),
            Product(
                id = 47,
                name = ProductName("초코허니퐁크러쉬"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132651_1717993611266_QFRonrkBzf.jpg"),
                price = Price(5500),
            ),
            Product(
                id = 48,
                name = ProductName("커피프라페"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132804_1717993684465__jH6f5a2VM.jpg"),
                price = Price(4500),
            ),
            // ===== 티 (page 8) =====
            Product(
                id = 49,
                name = ProductName("캐모마일 (HOT)"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610105908_1717984748214_qc34s7Vkq5.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 50,
                name = ProductName("페퍼민트 (HOT)"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610110026_1717984826888_gfP6LsxEV3.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 51,
                name = ProductName("녹차 (ICE)"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610112857_1717986537987_H1q4z1rnt2.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 52,
                name = ProductName("사과유자차"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132332_1717993412234_5kkTR1DkNH.jpg"),
                price = Price(3900),
            ),
            Product(
                id = 53,
                name = ProductName("얼그레이"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132511_1717993511025_ChoqheNJQf.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 54,
                name = ProductName("캐모마일 (ICE)"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132753_1717993673758__j_omi1oWY.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 55,
                name = ProductName("페퍼민트 (ICE)"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610115138_1717987898720_OdAWvoBqNh.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 56,
                name = ProductName("복숭아아이스티"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610132446_1717993486995_hvfli27vPn.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 57,
                name = ProductName("유자차"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240610105725_1717984645282_YfXeYsJzVV.jpg"),
                price = Price(3900),
            ),
            // ===== 디저트 / 베이커리 (page 9) =====
            Product(
                id = 58,
                name = ProductName("버터가 쫀득해떡"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260407215938_1775566778053_U6yDWuVmpF.jpg"),
                price = Price(2500),
            ),
            Product(
                id = 59,
                name = ProductName("내 맘대로 더블 젤라또 (딸기&요거트)"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260407171036_1775549436955_KJ8TwVQHI3.png"),
                price = Price(5500),
            ),
            Product(
                id = 60,
                name = ProductName("내 맘대로 더블 젤라또 (초코&말차)"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20260407171228_1775549548671_hyOiR1Wk4E.png"),
                price = Price(5500),
            ),
            Product(
                id = 61,
                name = ProductName("딸기 크림치즈 쫀득빵"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20251226202655_1766748415087_NsW1Ifwl8a.png"),
                price = Price(3800),
            ),
            // ===== 크로플 / 쿠키 (page 10) =====
            Product(
                id = 62,
                name = ProductName("초코젤라또크로플"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240228221348_1709126028809_6VjAVPuxOP.jpg"),
                price = Price(4500),
            ),
            Product(
                id = 63,
                name = ProductName("마카다미아 쿠키"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20220704113933_1656902373934_smXcsfdrHY.jpg"),
                price = Price(2500),
            ),
            Product(
                id = 64,
                name = ProductName("초콜릿칩 쿠키"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20220704114022_1656902422672_HECxBh1Vuk.jpg"),
                price = Price(2500),
            ),
            Product(
                id = 65,
                name = ProductName("플레인크로플"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20220701141927_1656652767760_yc4rsWS5yT.jpg"),
                price = Price(3500),
            ),
            Product(
                id = 66,
                name = ProductName("크로크무슈"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20230508143055_1683523855357_b1oieG5cId.png"),
                price = Price(4500),
            ),
            Product(
                id = 67,
                name = ProductName("치즈 케익"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240719183644_1721381804706_g8I_YcFpc7.jpg"),
                price = Price(5500),
            ),
            Product(
                id = 68,
                name = ProductName("초코무스 케익"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20220704112422_1656901462139_JSxU5sBxAN.jpg"),
                price = Price(5500),
            ),
            Product(
                id = 69,
                name = ProductName("티라미수 케익"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20220704113333_1656902013629_aEHrGt8IO6.jpg"),
                price = Price(5500),
            ),
            Product(
                id = 70,
                name = ProductName("허니브레드"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20220704113432_1656902072749_liQCQrLx0R.jpg"),
                price = Price(7500),
            ),
            // ===== MD 상품 (page 11) =====
            Product(
                id = 71,
                name = ProductName("작지만 강력한 마루 자석 세트(2종 랜덤)"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20250423235659_1745420219764_nfCsetfWOZ.jpg"),
                price = Price(8000),
            ),
            Product(
                id = 72,
                name = ProductName("나랑 같이 날아 츄! 우주선 빨대 텀블러"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20241023211612_1729685772681_nAZ0gCnL21.jpg"),
                price = Price(19800),
            ),
            Product(
                id = 73,
                name = ProductName("나랑 같이 가자 츄! 하츄핑 실리콘 가방"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20241023211908_1729685948585_e8lASrT0Uy.jpg"),
                price = Price(15000),
            ),
            Product(
                id = 74,
                name = ProductName("나랑 같이 마셔 츄! 콜드컵 키링 텀블러"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20241023211823_1729685903747_KQC1QUAgR3.jpg"),
                price = Price(12000),
            ),
            Product(
                id = 75,
                name = ProductName("나랑 같이 냠냠 츄! 로켓 멀티통"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20241023212015_1729686015557_fh9lJdKwGu.jpg"),
                price = Price(9800),
            ),
            Product(
                id = 76,
                name = ProductName("나랑 같이 놀자 츄! 카메라 빔 키링"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20241023212054_1729686054320_MdiR0iMLec.jpg"),
                price = Price(8800),
            ),
            Product(
                id = 77,
                name = ProductName("나랑 같이 달아 츄! 랜덤 티니핑 파츠 3종"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20241023211733_1729685853124_zxyhgo3unG.jpg"),
                price = Price(6000),
            ),
            Product(
                id = 78,
                name = ProductName("캐치!티니핑 하츄핑 랜덤 피규어"),
                imageUrl = ImageUrl("https://img.79plus.co.kr/megahp/manager/upload/menu/20240806193504_1722940504072_335GhVE_6s.jpg"),
                price = Price(5000),
            ),
        )
}
