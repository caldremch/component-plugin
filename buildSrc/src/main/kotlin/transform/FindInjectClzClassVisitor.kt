package transform

import Logger
//import com.caldremch.andorid.plugin.api.IComponent
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes.ASM7
import org.objectweb.asm.AnnotationVisitor
import java.util.*

/**
 *
 *Created by Caldremch on 2019/11/24.
 *
 * filePath: 当前visit的class的文件路径
 *
 **/

class FindInjectClzClassVisitor(classVisitor: ClassVisitor?, var filePath: String?) :
    ClassVisitor(ASM7, classVisitor) {

    private var hasApplicationAnnotation = false
    private var hasInjectClassAnnotation = false
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
//        val isImplementIComponent = interfaces != null && interfaces.isNotEmpty() && interfaces.contains(ClassUtils.getClassName(IComponent::class.java))
//        if (isImplementIComponent){
//            Logger.log(name+Arrays.toString(interfaces))
//            hasApplicationAnnotation = true
//            this.name = name
//        }
    }



    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
////        println("visitAnnotation-->$descriptor")
//        if (descriptor == ClassUtils.getClassPath(IComponent::class.java)){
////            println("visitAnnotation-->$descriptor")
//            hasApplicationAnnotation = true
//        }else if (descriptor == ClassUtils.getClassPath(Metadata::class.java)){
//
//        }


        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitEnd() {
        if (hasApplicationAnnotation){
            Logger.log(name)
        }
    }
}