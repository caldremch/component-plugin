/**
 *
 * @auther Caldremch
 *
 * @email finishmo@qq.com
 *
 * @date 2021/3/18 09:22
 *
 * @description
 *
 *
 */
//class MethodTraceTransform(private val project: Project) : Transform() {
//
//    override fun getName(): String {
//        return "MethodTraceTransform"
//    }
//
//    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
//        return TransformManager.CONTENT_CLASS
//
//    }
//
//    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
//        return TransformManager.SCOPE_FULL_PROJECT
//    }
//
//    override fun isIncremental(): Boolean {
//        return false
//    }
//
//    override fun transform(transformInvocation: TransformInvocation) {
//
//        val buildDir = project.buildDir
//
//        val mappingOut =
//        Joiner.on(File.separatorChar).join(
//            buildDir.toString(),
//            AndroidProject.FD_OUTPUTS,
//            "mapping",
//            "debug"
//        )
//
//        val mappingFile = File(mappingOut, "mapping.txt")
//
////        if(mappingFile.exists().not()){
////            throw RuntimeException("找不到 mapping 文件 ${mappingFile.absolutePath}")
////        }
//
//        transformInvocation.inputs.forEach {
//            JarInputManager.input(it, transformInvocation.outputProvider)
//            DirectoryInputManager.input(it, transformInvocation.outputProvider)
//        }
//    }
//}