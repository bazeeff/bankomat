import java.io.IOException;
import java.io.Serializable;
import java.util.List;


public class UserService extends MenuService implements Serializable, UserServiceImpl {



   public User  returnUser(String valid,FileService fileService) throws IOException, ClassNotFoundException {
        List<User> users = fileService.listUser();
        for (User u : users) {
            if (valid.equals(String.valueOf(u.getId()))) {
                return u;
            }
        }
        return null; }

    public boolean existenceOfUser(Integer code, Long id,FileService fileService) throws IOException, ClassNotFoundException {

        List<User> users = fileService.listUser();

        Boolean flag = null;

        for(User u: users){
            if ((id.toString().equals(u.getId().toString()))&&(code.toString().equals(u.getCode().toString()))) {

                flag=true;
                break;
            } else {flag=false;}

        }

        return  flag;}



}
