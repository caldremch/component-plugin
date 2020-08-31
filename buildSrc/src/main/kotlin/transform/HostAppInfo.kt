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
    private var exist = false;

    fun clear() {
        hostAppName = ""
        hostAppNameSign = ""
        exist = false
    }

    fun initByName(name: String) {
        if (exist) throw RuntimeException("only one host app can be exist")
        hostAppName = name
        hostAppNameSign = "L${hostAppName.replace(".", "/")}"
        exist = true
    }
}