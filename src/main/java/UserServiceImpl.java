import java.io.IOException;

public interface UserServiceImpl {

    User returnUser(String valid, FileService fileService) throws IOException, ClassNotFoundException;
    boolean existenceOfUser(Integer code, Long id, FileService fileService) throws IOException, ClassNotFoundException;
}

