package com.caldremch.plugin.visitor

import Logger
import com.caldremch.andorid.plugin.api.HostApp
import com.caldremch.andorid.plugin.api.IRegisterComponent
import com.caldremch.andorid.plugin.api.KtHostApp
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes.ASM7
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.TypePath
import shadow.bundletool.com.android.tools.r8.jetbrains.kotlin.reflect.KClass
import shadow.bundletool.com.android.tools.r8.jetbrains.kotlinx.metadata.internal.metadata.jvm.deserialization.JvmProtoBufUtil
import transform.ClassUtils
import transform.ComponentManager
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

    private var hasComponentApp = false //组件app
    private var hasHostApp = false //宿主app
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
        var isImplementIComponent = false

        if (interfaces != null && interfaces.isNotEmpty()) {
            isImplementIComponent =
                interfaces.contains(ClassUtils.getClassName(IRegisterComponent::class.java))
            hasHostApp = interfaces.contains(ClassUtils.getClassName(KtHostApp::class.java))
        }
        if (isImplementIComponent) {
            Logger.log("register component found:$name $signature")
            hasComponentApp = true
            this.name = name
        }

        if (hasHostApp){
            Logger.log("kotlin host app found:$name $signature")
            this.name = name
        }
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {

        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        if (descriptor == ClassUtils.getClassPath(HostApp::class.java)) {
            Logger.log("java host app found:$descriptor")
            hasHostApp = true
        }
        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitEnd() {
        if (hasComponentApp) {
            ComponentManager.registerComponents.add(name)
        } else if (hasHostApp) {
            ComponentManager.hostApp.initByName(name)
        }
    }
}