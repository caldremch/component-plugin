package transform

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-31 10:20
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class HostAppInfo {

    var hostAppName: String = ""
    var hostAppNameSign: String = ""
    var hostAppFilePath:String = ""
    private var isFound = false;

    fun clear() {
        hostAppName = ""
        hostAppNameSign = ""
        hostAppFilePath = ""
        isFound = false
    }

    fun initByName(name: String, path:String) {
        if (isFound) throw RuntimeException("only one host app can be exist")
        hostAppName = name
        hostAppFilePath = path
        hostAppNameSign = "L${hostAppName.replace(".", "/")}"
        isFound = true
    }

    fun isFound():Boolean{
        return isFound
    }

    override fun toString(): String {
        return hostAppName
    }
}