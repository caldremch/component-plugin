package transform

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
object ClassUtils {

   private val clzPathMap = hashMapOf<Class<*>, String>()
   private val clzNameMap = hashMapOf<Class<*>, String>()

    fun getClassName(clz: Class<*>): String {
        if (clzNameMap.containsKey(clz)) {
            return clzNameMap[clz]!!
        }
        val namePath = clz.name.replace(".", "/")
        clzNameMap[clz] = namePath
        return namePath
    }

    fun getClassPath(clz: Class<*>): String {
        if (clzPathMap.containsKey(clz)) {
            return clzPathMap[clz]!!
        }
        val namePath = "L"+clz.name.replace(".", "/")
        clzPathMap[clz] = namePath
        return namePath
    }
}