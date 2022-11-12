package transform

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM7

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-31 13:51
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class HostAppVisitor(classVisitor: ClassVisitor?) : ClassVisitor(ASM7, classVisitor) {
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if ((name + descriptor) == "onCreate()V") {
            println("-----found method onCreate------")
            println("-----start inject " + ComponentManager.registerComponents.size + " lines of code ------")
            println("-----inject file: " + ComponentManager.hostApp.hostAppFilePath)
            return InjectCodeToOnCreateVisitor(mv)
        }
        return mv
    }

}