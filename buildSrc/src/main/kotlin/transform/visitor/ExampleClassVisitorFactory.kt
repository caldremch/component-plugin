package transform.visitor

import Logger
import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.util.TraceClassVisitor
import transform.FindInjectClzClassVisitor
import java.io.File
import java.io.PrintWriter

/**
 * Created by Leon on 2022/11/8.
 */
abstract class ExampleClassVisitorFactory : AsmClassVisitorFactory<ExampleParams> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return if (parameters.get().writeToStdout.get()) {
            TraceClassVisitor(nextClassVisitor, PrintWriter(System.out))
        } else {
////            MethodTraceClassVisitor(nextClassVisitor)
//            val out = File("trace_out")
////            Logger.e("$nextClassVisitor--->输出文件:${out.absolutePath}")
//            TraceClassVisitor(FindInjectClassVisitor(nextClassVisitor, null), PrintWriter(out))
            FindInjectClassVisitor(nextClassVisitor)
        }
    }

    override fun isInstrumentable(classData: ClassData): Boolean {

        var ok = !classData.className.endsWith(".R\$color") &&
                !classData.className.endsWith(".R\$drawable") &&
                !classData.className.endsWith(".R\$layout") &&
                !classData.className.endsWith(".R\$mipmap") &&
                !classData.className.endsWith(".R\$string") &&
                !classData.className.endsWith(".R\$style") &&
                !classData.className.endsWith(".R\$xml") &&
                !classData.className.endsWith(".BuildConfig") &&
                !classData.className.endsWith(".R") &&
                !classData.className.endsWith(".R\$id")

        val thro =  ok && classData.className.startsWith("com.caldremch")

        if(thro){
            Logger.d("${classData.className}--有注解->${classData.classAnnotations.joinToString(",")}")
        }

        return thro
    }


}