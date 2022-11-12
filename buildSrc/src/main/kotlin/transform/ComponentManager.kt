package transform

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
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

    val hostApp: HostAppInfo = HostAppInfo()
    val registerComponents: LinkedList<String> = LinkedList()

    fun clear() {
        hostApp.clear()
        registerComponents.clear()
    }

    fun startVisitMainApp() {
        if (hostApp.isFound().not() || registerComponents.isEmpty()){
            Logger.log("no component info $hostApp registerComponents.size=${registerComponents.size}")
            return
        }
        try {
            val fileInputStream = FileInputStream(hostApp.hostAppFilePath)
            val classReader = ClassReader(fileInputStream)
            val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
            val classVisitor = HostAppVisitor(classWriter)
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
            val code = classWriter.toByteArray()
            val fos = FileOutputStream(hostApp.hostAppFilePath)
            fos.write(code)
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}