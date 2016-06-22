import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class AtmService {

    private AdminService adminstrator = new AdminService();

    private static  InputService inputService = new InputService();

    private static  AtmService atmService =new AtmService();

    private BigDecimal cacheAtm = getCacheAtmToFile();

    private UserService userService = new UserService();

    protected AtmService() {
    }

    protected  BigDecimal getCacheAtmToFile() {
        File file = null;
        FileReader fileReader = null;
        try {
            file = new File("./src/money/cachAtm.txt");
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        char[] buffer = new char[(int) file.length()];
        try {
            fileReader.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String valueBuffer = new String(buffer);
        valueBuffer = valueBuffer.replace("\n", "");
        int value = Integer.parseInt(String.valueOf(valueBuffer));

        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BigDecimal(value);
    }

    protected  void  debetBalanceforAdmin(BigDecimal money) {

        BigDecimal balance = this.cacheAtm;
        balance=balance.add(money);


        String newBalance = String.valueOf(balance);
        try (FileWriter writer = new FileWriter("./src/money/cachAtm.txt", false)) {

            writer.write(newBalance);
            writer.flush();


        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }



    }

    void saveBalance(BigDecimal money){

        String newBalance = String.valueOf(money);
        try (FileWriter writer = new FileWriter("./src/money/cachAtm.txt", false)) {

            writer.write(newBalance);
            writer.flush();


        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }

    public  BigDecimal getCachAtm() {
        return cacheAtm;
    }

    public  void setCachAtm(BigDecimal cachAtm) {
        this.cacheAtm = cachAtm;
    }

    public  BigDecimal maxLimit() {
        String max = null;
        FileInputStream limitation;
        Properties property = new Properties();

        try {
            limitation = new FileInputStream("./atm.properties");
            property.load(limitation);

            max = property.getProperty("max.amount.per.operation");
            limitation.close();



        } catch (IOException e) {
            System.err.println("Файл отсуствует");
        }

        return new BigDecimal(Integer.parseInt(max));
    }

    public  BigDecimal minLimit() {
        String min = null;
        FileInputStream limitation;
        Properties property = new Properties();

        try {
            limitation = new FileInputStream("./atm.properties");
            property.load(limitation);


            min = property.getProperty("min.amount.per.operation");

            limitation.close();
            // System.out.println(min);

        } catch (IOException e) {
            System.err.println("Файл отсуствует");
        }

        return new BigDecimal(Integer.parseInt(min));
    }

    private  boolean existenceOfAdministator(Long id, FileService fileService) throws IOException, ClassNotFoundException {

        List<Object> keys;
        keys = atmService.returnKeys(fileService);
        Boolean flag = null;

        for (Object o : keys) {
            if (id.toString().equals(o.toString())) {

                flag=true;
                break;
            } else {flag=false;}
        }
        return  flag;}

    protected void runAtm(FileService fileService) throws IOException, ClassNotFoundException, InterruptedException,NullPointerException {

        Boolean flag = false;
        System.out.println("");
        Properties property = new Properties();
        FileInputStream admin;
        String login = null;
        String pass = null;
        try {
            admin = new FileInputStream("./src/admin/admin.txt");
            property.load(admin);

            login = property.getProperty("login");
            pass = property.getProperty("pass");
            admin.close();
            //ищем обычных пользователей из коллекции типа User
            List<User> users = fileService.listUser();
            Map<Long, Integer> all = new HashMap();
            //выбираем нужные свойства и забиваем в мап
            for (User u : users) {
                all.put(u.getId(), u.getCode());

            }
            //добиваем пару айди пин админа
            all.put(Long.parseLong(login), Integer.parseInt(pass));

            List<Object> key = all.keySet().stream().collect(Collectors.toList());


            while (!flag) {
                String valid = String.valueOf(inputService.enterID());


               if (existenceOfAdministator(Long.parseLong(valid),fileService)&&valid.equals(login)){


                   adminstrator.drawMenu(inputService, atmService, adminstrator, fileService);
                        flag=false;


                    } else  {
                  userService.drawMenu(valid, inputService, new User(), userService, atmService, fileService);
                    }


                    flag = true;

                }



        } catch (IOException e) {
            System.err.println("Файл отсуствует1");
        }
   }

    public  List<Object> returnValues(FileService fileService) throws IOException, ClassNotFoundException {

        Properties property = new Properties();
        FileInputStream admin;
        String login = null;
        String pass = null;

            admin = new FileInputStream("./src/admin/admin.txt");
            property.load(admin);

            login = property.getProperty("login");
            pass = property.getProperty("pass");
            admin.close();
            //ищем обычных пользователей из коллекции типа User
            List<User> users = fileService.listUser();
            Map<Long, Integer> all = new HashMap();
            //выбираем нужные свойства и забиваем в мап
            for (User u : users) {
                all.put(u.getId(), u.getCode());

            }
            //добиваем пару айди пин админа
            all.put(Long.parseLong(login), Integer.parseInt(pass));
        List<Object> values=null;
        values = (List<Object>) all.values().stream().collect(Collectors.toList());


   return values; }

    public  List<Object> returnKeys(FileService fileService) throws IOException, ClassNotFoundException {

        Properties property = new Properties();
        FileInputStream admin;
        String login = null;
        String pass = null;

        admin = new FileInputStream("./src/admin/admin.txt");
        property.load(admin);

        login = property.getProperty("login");
        pass = property.getProperty("pass");
        admin.close();
        //ищем обычных пользователей из коллекции типа User
        List<User> users = fileService.listUser();
        Map<Long, Integer> all = new HashMap();
        //выбираем нужные свойства и забиваем в мап
        for (User u : users) {
            all.put(u.getId(), u.getCode());

        }
        //добиваем пару айди пин админа
        all.put(Long.parseLong(login), Integer.parseInt(pass));
        List<Object> keys=null;
        keys = (List<Object>) all.keySet().stream().collect(Collectors.toList());


        return keys; }

}


