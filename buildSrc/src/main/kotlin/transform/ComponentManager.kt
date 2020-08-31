package transform

import java.util.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-31 10:14
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object ComponentManager {

    val hostApp: HostAppInfo
    val registerComponents: LinkedList<String>

    init {
        hostApp = HostAppInfo()
        registerComponents = LinkedList()
    }

    fun clear() {
        hostApp.clear()
        registerComponents.clear()
    }
}