package transform.visitor

//import com.caldremch.andorid.coponent.service.IComponent
import Logger
import com.caldremch.andorid.coponent.service.IRegisterComponent
import com.caldremch.android.log.errorLog
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import transform.ClassUtils
import java.util.Arrays

/**
 * Created by Leon on 2022/11/8.
 */
class FindInjectClassVisitor(classVisitor: ClassVisitor?) :
    ClassVisitor(Opcodes.ASM9, classVisitor) {


    companion object {
        val components = hashSetOf<String>()
    }

    private var hasApplicationAnnotation = false
    private var hasInjectClassAnnotation = false
    private var isManager = false
    private lateinit var name: String

    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        Logger.e("FindInjectClassVisitor访问-->:${name}")
        val isImplementIComponent =
            interfaces != null && interfaces.isNotEmpty() && interfaces.contains(
                ClassUtils.getClassName(IRegisterComponent::class.java)
            )
        if (isImplementIComponent) {
            Logger.e("命中: ${name + Arrays.toString(interfaces)}")
            errorLog {"命中: ${name + Arrays.toString(interfaces)}"}
            hasApplicationAnnotation = true
            this.name = name
            components.add(name)
        }

        if (name.equals("com/caldremch/andorid/coponent/service/ServiceManager")) {
            //开始往里面注入属
            isManager = true
            Logger.e("找到Manager")
        }

    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val oldMV = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (isManager) {
            isManager = false;
            return MyMethodVisitor(oldMV)
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