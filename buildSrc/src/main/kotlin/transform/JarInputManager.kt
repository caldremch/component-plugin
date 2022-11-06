package transform

import com.android.SdkConstants
import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
//import org.apache.commons.codec.digest.DigestUtils
//import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.util.jar.JarFile
import java.util.zip.ZipEntry

/**
 *Created by Caldremch on 2019/11/24.
 * 从jar包中找出继承IApp的类
 **/
object JarInputManager {


    fun input(input: TransformInput, outputProvider: TransformOutputProvider) {
        input.jarInputs.forEach { jarInput ->
            val jarFile = JarFile(jarInput.file)
            jarFile.entries().toList().forEach {
//                println(it.name)
                val entry = ZipEntry(it.name)
                if (it.name.endsWith(SdkConstants.DOT_CLASS)) {
                    val classReader = ClassReader(jarFile.getInputStream(entry).readBytes())
                    val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
                    val classVisitor = FindInjectClzClassVisitor(classWriter, null)
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                }
            }
//            var jarName = jarInput.name
//            val md5Name = DigestUtils.md5Hex(jarInput.file.absolutePath)
//            if (jarName.endsWith(".jar")) {
//                jarName = jarName.substring(0, jarName.length - 4)
//            }
//
//            val dest = outputProvider.getContentLocation(
//                    jarName + md5Name,
//                    jarInput.contentTypes, jarInput.scopes, Format.JAR
//            )
//
//            FileUtils.copyFile(jarInput.file, dest)
        }
    }
}