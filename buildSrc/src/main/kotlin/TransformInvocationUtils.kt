import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformInvocation
import org.apache.commons.io.FileUtils
import java.io.File

/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/3/18 09:30
 *
 * @description
 *
 *
 */
object TransformInvocationUtils {

    fun dispatch(transformInvocation: TransformInvocation){
        val inputs = transformInvocation.inputs
        val outputProvider = transformInvocation.outputProvider

        inputs.forEach {
            it.directoryInputs.forEach {
                if (outputProvider != null) {
                    val dest: File = outputProvider.getContentLocation(
                        it.getName(),
                        it.getContentTypes(),
                        it.getScopes(),
                        Format.DIRECTORY
                    )
                    FileUtils.copyDirectory(it.getFile(), dest)
                }
            }
            it.jarInputs.forEach {
                if (outputProvider != null) {
                    val dest = outputProvider.getContentLocation(
                        it.getName(),
                        it.getContentTypes(),
                        it.getScopes(),
                        Format.JAR
                    )
                    FileUtils.copyFile(it.getFile(), dest)
                }
            }
        }
    }


}