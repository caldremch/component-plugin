package transform.visitor

import com.android.build.api.instrumentation.ClassData
import com.caldremch.andorid.coponent.service.BaseService
import com.caldremch.andorid.coponent.service.ServiceManager
import com.caldremch.android.log.debugLog
import com.caldremch.android.log.errorLog
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * Created by Leon on 2022/11/9.
 * 将类进行字节码查看后, 再来修改
 */
class InjectMethodVisitor(methodVisitor: MethodVisitor?,
                         private val  collectorServiceList: MutableList<ClassData>
                          ) : MethodVisitor(Opcodes.ASM9, methodVisitor) {

    private fun toTypeString(clazz: Class<*>): String {
        return "L${clazz.name.replace(",", "/")};"
    }
    private fun toTypeString(clazzName:String): String {
        return "L${clazzName};"
    }
    override fun visitInsn(opcode: Int) {
        try {
            if (opcode == Opcodes.IRETURN) {
                debugLog { "开始插桩..." }
                collectorServiceList.forEach {classData->
                    super.visitFieldInsn(
                        Opcodes.GETSTATIC,
                        ServiceManager::class.java.name.replace(".", "/"),
                        "serviceClzs",
                        "Ljava/util/HashMap;"
                    )

                    super.visitLdcInsn(Type.getType(toTypeString(BaseService::class.java)));

                    super.visitMethodInsn(
                        Opcodes.INVOKEVIRTUAL,
                        "java/lang/Class",
                        "getName",
                        "()Ljava/lang/String;",
                        false
                    );
                    super.visitLdcInsn(Type.getType(toTypeString(classData.className)));

                    super.visitMethodInsn(
                        Opcodes.INVOKEVIRTUAL,
                        "java/util/HashMap",
                        "put",
                        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
                        false
                    );
                }


            }
        }catch (e:Exception){
            errorLog { e.message }
            e.printStackTrace()
        }
        super.visitInsn(opcode)

    }

}