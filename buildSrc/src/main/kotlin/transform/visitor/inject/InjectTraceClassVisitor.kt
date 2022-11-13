package transform.visitor.inject

//import com.caldremch.andorid.coponent.service.IComponent
import Logger
import com.android.build.api.instrumentation.ClassData
import com.caldremch.android.log.debugLog
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import transform.visitor.InjectMethodVisitor

/**
 * Created by Leon on 2022/11/8.
 * 第一个classVisitor就是寻找所有的服务
*/
class InjectTraceClassVisitor(
    classVisitor: ClassVisitor?,
   private val collectorServiceList: MutableList<ClassData>
) :ClassVisitor(Opcodes.ASM9, classVisitor) {

    companion object {
        val components = hashSetOf<String>()
    }

    private var hasApplicationAnnotation = false
    private var hasInjectClassAnnotation = false
    private var isManager = false
    private lateinit var name: String

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val oldMV = super.visitMethod(access, name, descriptor, signature, exceptions)
        debugLog { "方法:$name" }
        if (name=="<init>") {
            return InjectMethodVisitor(oldMV, collectorServiceList)
        } else {
            return oldMV
        }
    }


    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {

//        Logger.e("visitAnnotation-->$descriptor")
//        if (descriptor == ClassUtils.getClassPath(IComponent::class.java)){
////            println("visitAnnotation-->$descriptor")
//            hasApplicationAnnotation = true
//        }else if (descriptor == ClassUtils.getClassPath(Metadata::class.java)){
//
//        }


        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitEnd() {
        if (hasApplicationAnnotation) {
            Logger.log(name)
        }
    }
}