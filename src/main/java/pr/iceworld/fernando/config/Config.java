package pr.iceworld.fernando.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * spring 4.x with jdk 6  @PropertySource is not useful. but PropertySourcesPlaceholderConfigurer or PropertyPlaceholderConfigurer is available.
 * see CustomConfiguration
 * @PropertySources(@PropertySource( value = "file:${user.dir}/conf/config.properties"))
 */
@Component
public class Config {

    @Value("${source-project}")
    private String sourceProject;
    @Value("#{T(java.lang.String).valueOf('${source-file}')}")
    private String sourceFile;
    @Value("${source-folder}")
    private String sourceFolder;
    @Value("${compiled-class-folder}")
    private String compiledClassFolder;
    @Value("${temp-folder}")
    private String tempFolder;
    @Value("${target-jar-folder}")
    private String targetJarFolder;
    @Value("${target-jar-filename}")
    private String targetJarFilename;
    @Value("${original-jar-folder}")
    private String originalJarFolder;
    @Value("${original-jar-filename}")
    private String originalJarFilename;
    @Value("#{T(java.lang.Boolean).parseBoolean('${copy-temp-jar-2-target-folder}')}")
    private boolean copyTempJar2TargetFolder;

    public String getSourceProject() {
        return sourceProject;
    }

    public void setSourceProject(String sourceProject) {
        this.sourceProject = sourceProject;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public String getCompiledClassFolder() {
        return compiledClassFolder;
    }

    public void setCompiledClassFolder(String compiledClassFolder) {
        this.compiledClassFolder = compiledClassFolder;
    }

    public String getTempFolder() {
        return tempFolder;
    }

    public void setTempFolder(String tempFolder) {
        this.tempFolder = tempFolder;
    }

    public String getTargetJarFolder() {
        return targetJarFolder;
    }

    public void setTargetJarFolder(String targetJarFolder) {
        this.targetJarFolder = targetJarFolder;
    }

    public String getTargetJarFilename() {
        return targetJarFilename;
    }

    public void setTargetJarFilename(String targetJarFilename) {
        this.targetJarFilename = targetJarFilename;
    }

    public String getOriginalJarFolder() {
        return originalJarFolder;
    }

    public void setOriginalJarFolder(String originalJarFolder) {
        this.originalJarFolder = originalJarFolder;
    }

    public String getOriginalJarFilename() {
        return originalJarFilename;
    }

    public void setOriginalJarFilename(String originalJarFilename) {
        this.originalJarFilename = originalJarFilename;
    }

    public boolean isCopyTempJar2TargetFolder() {
        return copyTempJar2TargetFolder;
    }

    public void setCopyTempJar2TargetFolder(boolean copyTempJar2TargetFolder) {
        this.copyTempJar2TargetFolder = copyTempJar2TargetFolder;
    }
}
