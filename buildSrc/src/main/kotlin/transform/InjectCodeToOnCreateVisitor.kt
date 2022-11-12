package transform

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-31 13:54
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class InjectCodeToOnCreateVisitor(classVisitor: MethodVisitor) :
    MethodVisitor(Opcodes.ASM7, classVisitor) {

    override fun visitInsn(opcode: Int) {
        if (opcode == Opcodes.RETURN) {
            for (clzName in ComponentManager.registerComponents) {
                println("-----inject $clzName------")
                mv.visitTypeInsn(Opcodes.NEW, clzName)
                mv.visitInsn(Opcodes.DUP)
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, clzName, "<init>", "()V", false)
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, clzName, "init", "()V", false)
            }
            ComponentManager.registerComponents.clear()
        }
        super.visitInsn(opcode)
    }

}