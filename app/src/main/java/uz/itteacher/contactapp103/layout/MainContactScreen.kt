package uz.itteacher.contactapp103.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uz.itteacher.contactapp103.model.MyContact
import uz.itteacher.contactapp103.R
import uz.itteacher.contactapp103.db.AppDataBase


@Composable
fun MainContactScreen(navController: NavHostController, appDataBase: AppDataBase) {
    val myContacts = appDataBase.getMyContactDao().getAllContacts().sortedBy { it.name }

    var filter = setOf<Char>()
    for (x in myContacts){
        if (x.name.length!=0 && (x.name[0].isLetter() || x.name[0].isDigit())){
            filter+=(x.name[0])
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Kontaktlar", fontSize = 25.sp, modifier = Modifier.padding(start = 20.dp, top = 10.dp))
            Row {
                IconButton(onClick = {
                    navController.navigate("search")
                }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = {
                    navController.navigate("create")
                }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {


            if (myContacts.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.box),
                    contentDescription = "Box",
                    modifier = Modifier.size(200.dp)
                )
            }
            else{
                LazyColumn(modifier = Modifier.padding(10.dp)){
                    items(myContacts){
                        Card(
                            Modifier.fillMaxWidth().padding(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Row(modifier = Modifier.padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.person),
                                    contentDescription = "Person",
                                    modifier = Modifier.size(70.dp).padding(18.dp)
                                )
                                Text(text = it.name, fontSize = 20.sp)

                            }
                        }
                    }
                }
            }
        }
    }
    Column (
        modifier = Modifier.fillMaxSize().width(32.dp).padding(top=68.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LazyColumn (
            modifier = Modifier.align(Alignment.End).background(color = Color(
                0x60CECECE
            ), shape = RoundedCornerShape(5.dp)
            ).padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(filter.size){
                Text(text = filter.elementAt(it).toString(), fontSize = 14.sp, modifier = Modifier.clickable{})
                Spacer(modifier = Modifier.height(5.dp))
            }

        }

    }



}