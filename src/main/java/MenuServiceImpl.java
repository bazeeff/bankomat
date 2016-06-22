import java.io.IOException;

/**
 * Created by artem on 21.06.16.
 */
public interface MenuServiceImpl {

    void drawMenu(String id, InputService inputService, User user, UserService userService, AtmService atmService, FileService fileService) throws IOException, ClassNotFoundException, InterruptedException;

    void drawMenu(InputService inputService, AtmService atmService, AdminService adminService, FileService fileService) throws IOException, ClassNotFoundException, InterruptedException;

}

