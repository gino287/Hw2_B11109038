package com.example.hw2application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hw2application.ui.theme.Hw2ApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hw2ApplicationTheme {
                // 初始化 NavController
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 设置 NavHost
                    NavHost(navController = navController, startDestination = "frontPage") {
                        composable("frontPage") { FrontPageScreen(navController) }
                        composable("detailPage/{attractionId}") { backStackEntry ->
                            val attractionIdString = backStackEntry.arguments?.getString("attractionId")
                            val attractionId = attractionIdString?.toInt() ?: throw IllegalArgumentException("Attraction ID is required")
                            DetailPageScreen(
                                attractionId = attractionId,
                                navController = navController
                            )
                        }
                    }

                    // 可以根据需要继续添加其他目的地

                }
            }
        }
    }
}


//各個卡片
@Composable
fun AttractionItem(
    attraction: Attraction,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFFBBDEFB))
            .clickable { navController.navigate("detailPage/${attraction.id}") },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFBBDEFB))
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = attraction.imageResId),
                contentDescription = attraction.description,
                modifier = Modifier
                    .size(100.dp)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = attraction.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = attraction.description,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

data class Attraction(
    val id: Int,
    val title: String,
    val description: String,
    val imageResId: Int,
    val detailedDescription: String,
)

//首頁
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrontPageScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "台中旅遊景點",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(350.dp),
                            textAlign = TextAlign.Center,
                        )
                    },
                    modifier = Modifier
                        .shadow(elevation = 8.dp)
                        .height(70.dp)
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .background(color = Color.LightGray),
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(attractions) { attraction ->
                        AttractionItem(
                            attraction = attraction,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}


//景點資料
val attractions = listOf(
    Attraction(
        1,
        "日月潭",
        "台灣最大的半天然湖泊",
        R.drawable.sun_moon_lake,
        "日月潭是台灣最大的半天然湖泊，位於南投縣魚池鄉。這裡以其美麗的湖光山色聞名，每年吸引大量遊客前來觀光。湖泊周圍有多條步道，適合喜歡健行和騎自行車的旅客。您可以搭乘遊湖船隻，欣賞湖光山色，特別是清晨和黃昏時分，景色尤為迷人。湖邊的文武廟、慈恩塔等景點，也是值得一遊的文化和宗教名勝。"
    ),
    Attraction(
        2,
        "阿里山",
        "臺灣訪問量最大的國家風景區",
        R.drawable.alishan,
        "阿里山是台灣訪問量最大的國家風景區，以其壯麗的山景、雲海和日出聞名。登上祝山觀日樓，您可以看到壯觀的日出景象。阿里山森林鐵路是另一大特色，乘坐這條古老的火車路線，可以穿越茂密的檜木森林。神木、姊妹潭、沼平公園等景點，皆展現了阿里山豐富的自然美景，是遠足和攝影的理想地點。"
    ),
    Attraction(
        3,
        "台中公園",
        "台中市歷史最悠久的公園",
        R.drawable.taichung_park,
        "台中公園是台中市歷史最悠久的公園，位於市中心。公園內有一座古色古香的湖心亭，是拍照和休閒散步的好地方。湖中可以租借小船，悠閒地划行。公園的綠樹成蔭，是本地居民和遊客休閒放鬆的好去處。每年春天的花卉展更是吸引了眾多花卉愛好者前來觀賞。"
    ),
    Attraction(
        4,
        "高美濕地",
        "台灣豐富多元的溼地生態區域",
        R.drawable.gaomei_wetland,
        "高美濕地位於台中市清水區，是一個豐富多元的溼地生態區域。這裡的木棧道是觀鳥和欣賞夕陽的絕佳場所，尤其是在日落時分，夕陽映照在水面上，美不勝收。濕地內的豐富生態系統，吸引了大量的野生動物和鳥類，是自然愛好者的樂園。遊客可以漫步在棧道上，近距離觀察濕地生態。"
    ),
    Attraction(
        5,
        "中央公園",
        "擁有臺中之肺的活動公園",
        R.drawable.central_park,
        "中央公園是台中的綠色肺，佔地廣闊，擁有豐富的自然景觀和休閒設施。公園內有多條步道和自行車道，非常適合戶外活動和運動。公園中的人工湖、花卉區和兒童遊樂場，為家庭和孩子提供了豐富的娛樂選擇。週末和假日，這裡成為本地居民和遊客放鬆和享受大自然的好去處。"
    ),
    Attraction(
        6,
        "東勢林場",
        "堪稱台灣中部最美的森林花園",
        R.drawable.dongshi_forest_garden,
        "東勢林場位於台中市東勢區，是台灣中部最美的森林花園之一。這裡有多樣的植物和花卉展示，四季皆有不同的美景。林場內的步道和小橋流水，使得整個園區充滿了詩情畫意。春天的櫻花季和秋天的楓葉季，吸引了眾多遊客前來欣賞。這裡也是生態教育和自然觀察的好場所。"
    ),
    Attraction(
        7,
        "一中商圈",
        "台中最青春洋溢的流行商圈",
        R.drawable.yizhong_shopping_district,
        "一中商圈是台中最青春洋溢的流行商圈，位於台中一中學附近。這裡聚集了許多時尚潮流的店鋪、美食攤位和娛樂場所，是年輕人最愛的逛街勝地。無論是購物、品嘗各式小吃、還是體驗最新的娛樂活動，一中商圈都能滿足您的需求。特別是夜晚，商圈內燈火輝煌，熱鬧非凡。"
    ),
    Attraction(
        8,
        "新社花海",
        "台灣中部地區一座花卉景點區",
        R.drawable.sea_of_flowers_in_shinshe,
        "新社花海位於台中市新社區，是台灣中部地區著名的花卉景點區。每年秋冬季節，這裡舉行的花海節，展示了各種色彩繽紛的花卉，形成一片壯麗的花海景觀。遊客可以漫步在花田間，欣賞各種花卉的美麗姿態。花海節期間，還有農特產品展銷和各種表演活動，增添了不少樂趣。"
    ),
    Attraction(
        9,
        "清水斷崖",
        "位於東岸，臺灣八大景之一",
        R.drawable.qingshui_cliff,
        "清水斷崖位於台灣東岸，是台灣八大景之一。這裡的峭壁直入太平洋，形成了壯麗的海岸景觀。沿著蘇花公路行駛，您可以欣賞到壯觀的海岸線和峭壁。清晨和黃昏時分，斷崖在陽光的照射下，顯得更加壯麗。這裡是拍照和欣賞自然美景的絕佳地點，也是旅人們經常駐足的地方。"
    ),
    Attraction(
        10,
        "柳川水岸",
        "生態工法打造的親水景觀河岸",
        R.drawable.liu_chuan_lan_dai_shui_an,
        "柳川水岸是台中市區一條經過生態工法打造的親水景觀河岸。這裡的步道沿著河岸綠蔭成蔭，非常適合散步和騎自行車。河岸兩側設有多個休憩區和景觀設施，讓人們可以親近水岸，享受自然的寧靜。夜晚的柳川水岸，燈光點綴，更顯浪漫和雅緻，是情侶和家庭散步的好去處。"
    )
)

//詳細頁面
@Composable
fun DetailPageScreen(attractionId: Int, navController: NavController) {
    val attraction = loadAttractionDetails(attractionId)

    Column(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "返回")
            }
            Spacer(modifier = Modifier.weight(1f)) // 这个Spacer用来把返回按钮推到左边
        }
        Text(
            text = attraction.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
            )
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = attraction.imageResId),
            contentDescription = attraction.title,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        Text(
            text = attraction.detailedDescription,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = { /* 点击事件，用于其他功能 */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text ="景點地圖",
                fontSize = 24.sp,
            )
        }
    }
}

fun loadAttractionDetails(attractionId: Int): Attraction {
    // 实际的数据加载逻辑
    return attractions.find { it.id == attractionId }
        ?: throw IllegalStateException("Attraction not found")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hw2ApplicationTheme {
        //AttractionItem(painterResource(id = R.drawable.sun_moon_lake),"日月潭", "台灣最大的半天然湖泊")

        //AttractionItem(painterResource(id = R.drawable.sun_moon_lake),"123","456")
        val navController = rememberNavController()
        //FrontPageScreen(navController)
        DetailPageScreen(1, navController)
        //DetailPageScreen(navController)
    }
}