package transform.visitor.collect

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.caldremch.andorid.coponent.service.BaseService
import com.caldremch.android.log.debugLog
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.util.TraceClassVisitor
import java.io.PrintWriter

/**
 * Created by Leon on 2022/11/12.
 *
 * 主要找到符合协议的实现类. 也只找那些接口协议的实现类
 */
abstract class CollectClassVisitorFactory : AsmClassVisitorFactory<CollectParams> {


    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return TraceClassVisitor(nextClassVisitor, PrintWriter(System.out))
    }

//    override fun createClassVisitor(
//        classContext: ClassContext,
//        nextClassVisitor: ClassVisitor
//    ): ClassVisitor {
//        return if (parameters.get().writeToStdout.get()) {
//            TraceClassVisitor(nextClassVisitor, PrintWriter(System.out))
//        } else {
//            CollectTraceClassVisitor(nextClassVisitor)
//        }
//    }

    fun hitProtocolInterface(classData: ClassData):Boolean{
//        debugLog { "${classData.className}->接口: ${classData.interfaces.joinToString(",")}" }
       return classData.interfaces.contains(BaseService::class.java.name)
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

        var thro =  ok && classData.className.startsWith("com.caldremch")
        //必须是serviceManager才执行扫描
        if(thro){
            thro  = hitProtocolInterface(classData)
            if(thro){
                parameters.get().collectorServices.add(classData)
                debugLog { "收集到:${classData.className}" }
            }
        }
        return false //最终都返回false, 因为我们不需要扫描, 只需要收集
    }


}
