

import java.io.*;
import java.util.*;


public  class AdminService extends  MenuService implements Serializable, AdminServiceImpl  {

    private InputService inputService = new InputService();

    public  String path = "./src/users/";

    public Integer counter = countFile();

    public  String nameFile = "user";

    protected AdminService() {
    }

    public boolean existenceOfUser(Long id, FileService fileService, AtmService atmService) throws IOException, ClassNotFoundException {

        List<Object> keys;

        List<User> users = fileService.listUser();

        keys = atmService.returnKeys(fileService);
        Boolean flag = null;


        for (Object o : keys) {
            if (id.toString().equals(o.toString())) {

            flag=true;
                break;
            } else {flag=false;}
        }
    return  flag;}

    public void createUser(InputService inputService, FileService fileService, AtmService atmService) throws IOException, ClassNotFoundException {
        List<Object> values;
        List<Object> keys;

        List<User> users = fileService.listUser();

        keys = atmService.returnKeys(fileService);
        LinkedList<User> myPerson = new LinkedList<User>();

        Long newID;
        newID=inputService.enterID();
       if (!existenceOfUser(newID, fileService , atmService)) {



          User newUser=new User(newID, inputService.enterCode(), inputService.enterLock(), inputService.enterBalance(),
                   "./src/users/user" + counter + ".txt");
           try {
               fileService.marshaller(newUser, nameFile, counter);
           } catch (IOException e) {
               e.printStackTrace();
           }
       } else {
           System.out.println("Пользователь с такими данными уже существует!");
       }

    }

    public Integer countFile() {
        //считаем xml-файлы

        File listFile = new File(path);
        File exportFiles[] = listFile.listFiles();
        String[] names = new String[exportFiles.length];
        Boolean[] list = new Boolean[names.length];

        for (int i = 0; i < names.length; i++) {
            names[i] = exportFiles[i].getName();

        }

        //поиск по маске кол-ва файлов пользователей в каталоге
        int counter = 0;
        for (int j = 0; j < names.length; j++)

        {

            list[j] = exportFiles[j].getName().matches(".*.txt.*");
            if (list[j].equals(true)) {
                counter++;
            }

        }


        return counter;
    }

    public void lock(FileService fileService) throws IOException, ClassNotFoundException {
        LinkedList<User> tempUsers = new LinkedList<User>();
        tempUsers.clear();
        List<User> users = fileService.listUser();
        Boolean flag1 = false;

        while (!(flag1)) {

            Long validationID = inputService.enterID();

            for (User u : users) {


                if (!(validationID.equals(u.getId()))) {
                    continue;
                }
                flag1 = true;

                tempUsers.clear();

                if (u.getLock().equals(false)) {
                    u.setLock(true);

                    fileService.marshallerUser(u, u.getFile());
                    countFile();
                    System.out.println("Пользователь успешно заблокирован" + "\n");
                } else {
                    System.out.println("Ошибка блокировки, пользователь уже заблокирован" + "\n");
                }
            }
        }
    }

    public void changeCode(FileService fileService) throws IOException, ClassNotFoundException {
        List<User> tempUsers = new LinkedList<User>();
        tempUsers.clear();
        List<User> users = fileService.listUser();
        Boolean flag1 = false;

        while (!(flag1)) {

            Long validationID = inputService.enterID();

            for (User u : users) {


                if (!(validationID.equals(u.getId()))) {
                    continue;
                }

                tempUsers.clear();
                if (u.getCode().equals(inputService.enterCode())) {
                    System.out.println("Измените PIN пользователя");
                    u.setCode(inputService.enterCode());
                   // tempUsers.add(u);
                    fileService.marshallerUser(u, u.getFile());
                    System.out.println("PIN пользователя успешно изменен" + "\n");
                    flag1 = true;
                } else {
                    System.out.println("Ошибка смена PIN" + "\n");
                }
            }
        }
    }

    public void unlock(FileService fileService) throws IOException, ClassNotFoundException {
        List<User> tempUsers = new LinkedList<User>();
        tempUsers.clear();
        List<User> users = fileService.listUser();

        Long validationID = inputService.enterID();

        for (User u : users) {

            if (!(validationID.equals(u.getId()))) {
                continue;
            }

            tempUsers.clear();

            if (u.getLock().equals(true)) {
                u.setLock(false);
                fileService.marshallerUser(u, u.getFile());
                System.out.println("Пользователь успешно разблокирован");

            } else {
                System.out.println("Ошибка разблокировки, пользователь уже разблокирован");
            }
        }
    }

    public void viewAccounts(FileService fileService) throws IOException, ClassNotFoundException {

        List<User> list = fileService.listUser();

        for (User u : list) {
            System.out.print("id: " + u.getId());
            System.out.print(" lock: " + u.getLock() + " ");
            System.out.println(" balance: " + u.getBalance());

        }
    }

}