object AppConfig {
    open val api_key="a6d1ff65b2144383cd737dc55f9129cf"
    open val fcmTopic="FarmershopSubscriber"
    open val maxQty=10
    open val appName= "Farmer Shop"
    open val mediaDir="farmershop"
    open val appDirectory= "FarmerShop"
    //open val server= "http://192.168.43.96/spitech/products/farmershop.co.in/"
    open val server= "http://farmershop.co.in/"
    open val authApi= server+"auth/"
    open val productApi= server+"product/"
    open val demoApi= "https://jsonplaceholder.typicode.com/"

    private val mediaPath= server+"media/"+mediaDir+"/"
    open val mediaCategory= mediaPath+"category/"
    open val mediaProduct= mediaPath+"product/"
}