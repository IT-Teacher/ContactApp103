package uz.itteacher.contactapp103.navigation

sealed class NavigationItem (var route:String){
   object Main:NavigationItem("main")
    object History:NavigationItem("history")
    object Create:NavigationItem("create/{id}")
    object Search:NavigationItem("search")
}