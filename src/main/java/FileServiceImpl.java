import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Artem on 22.06.2016.
 */
public interface FileServiceImpl {
    List<User> listUser() throws IOException, ClassNotFoundException;
    List<User> unmarshalling(File file) throws IOException, ClassNotFoundException;
}
