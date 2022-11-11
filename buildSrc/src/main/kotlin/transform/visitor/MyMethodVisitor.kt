package transform.visitor

import Logger
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by Leon on 2022/11/9.
 * 将类进行字节码查看后, 再来修改
 */
class MyMethodVisitor(methodVisitor: MethodVisitor?) : MethodVisitor(Opcodes.ASM9, methodVisitor)
{

    override fun visitInsn(opcode: Int) {
        super.visitInsn(opcode)



    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {

        Logger.e("方法名:$owner=>$name=>$descriptor")
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }


}