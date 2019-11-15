package pr.iceworld.fernando.files;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Config {
    private String sourceFile;
    private String sourceFolder;
    private String compiledClassFolder;
    private String tempFolder;
    private String targetJarFolder;
    private String targetJarFilename;

    private String originalJarFolder;
    private String originalJarFilename;
}
