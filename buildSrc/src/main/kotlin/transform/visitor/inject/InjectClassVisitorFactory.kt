package transform.visitor.inject

import Logger
import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.caldremch.android.log.debugLog
import com.google.common.util.concurrent.ServiceManager
import org.objectweb.asm.ClassVisitor
import transform.visitor.collect.CollectParams

/**
 * Created by Leon on 2022/11/12.
 *
 * 根究鞋类的信息, 只扫描ServiceManager, 并注入代码
 */
abstract class InjectClassVisitorFactory : AsmClassVisitorFactory<CollectParams> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return InjectTraceClassVisitor(nextClassVisitor, parameters.get().collectorServices.get())
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        debugLog { "注入过滤:${classData.className}" }
        return classData.className == ServiceManager::class.java.name
    }


}

