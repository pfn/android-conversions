/**
 * @author pfnguyen
 */

import java.util.List;
import java.io.File;
public interface ConversionsInterface {
    List<File> apply(File srcManaged,
              List<File> classpath,
              File androidJar,
              String pkg,
              List<File> deps,
              List<String> deppkgs);
}
