import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileService implements FileServiceImpl{

    public String path = "./src/users/";

    @Override
    public List<User> listUser() throws IOException, ClassNotFoundException {

        File listFile = new File(path);

        File exportFiles[] = listFile.listFiles();

        List<User> users = new LinkedList<User>();

        for (int i = 0; i < exportFiles.length; i++) {

            users.addAll(unmarshalling(exportFiles[i]));
        }

        return users;
    }

    @Override
    public  List<User> unmarshalling(File file) throws IOException, ClassNotFoundException {

        List<User> users = new ArrayList<>();
        ObjectInputStream in =  new ObjectInputStream (new FileInputStream(file));
        //   Date d1 = (Date)in.readObject();
        User user = (User)in.readObject();
        in.close();
        users.add(user);
        return  users;

    }

    public  void marshaller(User object, String nameFile, Integer counter) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("./src/users/" +nameFile+ counter + ".txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();

    }

    public  void marshallerUser(User object, String nameFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(nameFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();

    }
}
