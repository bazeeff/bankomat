import java.io.IOException;

/**
 * Created by Artem on 22.06.2016.
 */
public interface AdminServiceImpl {
    boolean existenceOfUser(Long id, FileService fileService, AtmService atmService) throws IOException, ClassNotFoundException;

    void createUser(InputService inputService, FileService fileService, AtmService atmService) throws IOException, ClassNotFoundException;

    Integer countFile();

    void lock(FileService fileService) throws IOException, ClassNotFoundException;

    void changeCode(FileService fileService) throws IOException, ClassNotFoundException;

    void unlock(FileService fileService) throws IOException, ClassNotFoundException;

    void viewAccounts(FileService fileService) throws IOException, ClassNotFoundException;
}
