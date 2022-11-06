import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import transform.DirectoryInputManager
import transform.JarInputManager
import org.gradle.api.Project

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-30
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class ComponentTransform(var project: Project) : Transform() {

    override fun transform(transformInvocation: TransformInvocation) {

        transformInvocation.inputs.forEach {
            JarInputManager.input(it, transformInvocation.outputProvider)
            DirectoryInputManager.input(it, transformInvocation.outputProvider)
        }

    }


    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS

    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun getName(): String {
        return "ComponentTransform"
    }

}