/**
 *
 * @author Caldremch
 *
 * @date 2020-08-30
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object Logger {

    private const val TAG  = "ComponentPlugin"


    fun log(msg:String?){
        println("> component-plugin :$msg")
    }


    fun d(msg:String?){
        println(String.format("[%s] %s", TAG, msg?:""))
    }

    fun e(msg:String?){
        System.err.println(String.format("[%s] %s", TAG, msg?:""))
    }
}