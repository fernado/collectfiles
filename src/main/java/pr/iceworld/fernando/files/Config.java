package pr.iceworld.fernando.files;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class Config {

    @Value("${source-file}")
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
    @Value("${copy-temp-jar-2-target-folder}")
    private boolean copyTempJar2TargetFolder;
}
