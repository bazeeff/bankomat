import java.io.IOException;

/**
 * Created by Artem on 22.06.2016.
 */
public class Atm {

    private static AtmService atmService =new AtmService();

    private static FileService  fileService= new FileService();

    public Atm(AtmService atmService) {
        try {
            atmService.runAtm(fileService);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException,NullPointerException, InterruptedException {

        try {

            Atm atm = new Atm(atmService);
        } catch (NullPointerException e) {
            System.out.println("Удачного дня");
        }
    }
}
