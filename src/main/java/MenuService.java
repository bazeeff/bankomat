import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by artem on 21.06.16.
 */
public class MenuService implements MenuServiceImpl {
    @Override
    public void drawMenu(String id, InputService inputService, User user, UserService userService, AtmService atmService, FileService fileService) throws IOException, ClassNotFoundException, InterruptedException {

        String value = null;
        List<Object> values;

        List<Object> keys;

        BigDecimal amountDebet = BigDecimal.ZERO;

        BigDecimal amountCredit = BigDecimal.ZERO;

        BigDecimal amountCreditPnone= BigDecimal.ZERO;

        BigDecimal balanceAtm= atmService.getCachAtm();

        String valid;

        List<User> users = fileService.listUser();


        Boolean flag1 = null;
        Boolean flag2 = false;
        keys = atmService.returnKeys(fileService);
        values = atmService.returnValues(fileService);
        valid = id;


        user.setCode(inputService.enterCode());


        if(userService.existenceOfUser(user.getCode(), Long.parseLong(id), fileService)){
            flag1=true;
        } else {flag1=false;}



        while ((flag1)) {

            System.out.println("Нажмите клавишу enter, чтобы начать работу с системой");
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader scanerMenu = new BufferedReader(isr);
            value = scanerMenu.readLine();
            while (!value.equals("exit")) {

                System.out.println("1-узнать баланс счета");
                System.out.println("2-внести деньги");
                System.out.println("3-снять деньги");
                System.out.println("4-перевести деньги на телефон");
                value = inputService.userCodeTerminalMenu();

                User u = userService.returnUser(valid,fileService);
                BigDecimal money_user_before = userService.returnUser(valid, fileService).getBalance();

                switch (value) {

                    case "1":
                        System.out.println("Баланс вашего счета: " + u.getBalance() + "\n");

                        break;

                    case "2":


                        if (u.getLock().equals(false)) {


                            BigDecimal money = u.enterBalance();

                            try {

                                if ((Integer.parseInt(String.valueOf(money)) <= Integer.parseInt(String.valueOf(atmService.maxLimit())))&&(Integer.parseInt(String.valueOf(money)) >= Integer.parseInt(String.valueOf(atmService.minLimit())))&&(Integer.parseInt(String.valueOf(money))%100)==0) {

                                    BigDecimal money_user_after = u.addCash(money);

                                    amountDebet = amountDebet.add(money_user_after.subtract(money_user_before));
                                    atmService.saveBalance(atmService.getCachAtm().add((amountDebet.subtract(amountCredit)).subtract(amountCreditPnone)));
                                    //вынес сериализацию
                                    fileService.marshallerUser(u, u.getFile());
                                    balanceAtm=balanceAtm.add(money);
                                    break;

                                } else {
                                    System.out.println("Максимальная сумма внесения:6000" + "\n" + "Миимальная сумма внесения 100"+"\n" + "Cумма внесения должна быть кратна 100" + "\n" + "Попробуйте еще раз" + "\n");
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.printf("Сумма должна быть кратна 100" + "\n");

                            }


                        } else {
                            System.out.printf("Сумма должна быть кратна 100" + "\n");
                            break;
                        }


                    case "3":


                        if (u.getLock().equals(false)) {

                            BigDecimal money = inputService.enterBalance();


                            try {


                                if ((Integer.parseInt(String.valueOf(money)) <= Integer.parseInt(String.valueOf(atmService.maxLimit())))&&(Integer.parseInt(String.valueOf(money)) >= Integer.parseInt(String.valueOf(atmService.minLimit())))&&(Integer.parseInt(String.valueOf(money))%100)==0){

                                    if ((money.compareTo(balanceAtm) == -1) || (money.compareTo(balanceAtm) == 0)) {
                                        BigDecimal money_user_after = u.getCash(money);
                                        // tempUsers.add(u);
                                        amountCredit = amountCredit.subtract(money_user_after.subtract(money_user_before));
                                        atmService.saveBalance(atmService.getCachAtm().add((amountDebet.subtract(amountCredit)).subtract(amountCreditPnone)));
                                        fileService.marshallerUser(u, u.getFile());
                                        balanceAtm=balanceAtm.subtract(money);
                                        break;
                                    }else{System.out.println("В банкомате недостаточно средств"); break;}
                                } else {
                                    System.out.println("Максимальная сумма снятия:6000" + "\n" + "Миимальная сумма внесения 100"+"\n" + "Сумма снятия должна быть кратна:100" + "\n" + "Попробуйте еще раз" + "\n");
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.printf("Сумма должна быть кратна 100" + "\n");
                                break;
                            }


                        } else {
                            System.out.println("Ваш номер счета заблокирован");

                            break;

                        }


                    case "4":


                        if (u.getLock().equals(false)) {
                            System.out.println("Перевод на телефон");
                            BigDecimal money = inputService.enterBalance();
                            Long phone= inputService.enterPhone();
                            try {

                                if ((Integer.parseInt(String.valueOf(money)) <= Integer.parseInt(String.valueOf(atmService.maxLimit())))&&(Integer.parseInt(String.valueOf(money)) >= Integer.parseInt(String.valueOf(atmService.minLimit())))&&(Integer.parseInt(String.valueOf(money))%100)==0){
                                    if ((money.compareTo(balanceAtm) == -1) || (money.compareTo(balanceAtm) == 0)) {
                                        BigDecimal money_user_after = u.getCash(money);
                                        // tempUsers.add(u);
                                        amountCreditPnone = amountCreditPnone.subtract(money_user_after.subtract(money_user_before));
                                        atmService.saveBalance(atmService.getCachAtm().add((amountDebet.subtract(amountCredit)).subtract(amountCreditPnone)));

                                        fileService.marshallerUser(u, u.getFile());
                                        System.out.println("На телефон " + phone + " зачислено " + money);
                                        balanceAtm = balanceAtm.subtract(money);
                                        break;
                                    } else {
                                        System.out.println("В банкомате недостаточно средств");
                                    }
                                } else {
                                    System.out.println("Максимальная сумма внесения:6000" + "\n" +  "Миимальная сумма внесения 100"+"\n" +"Cумма внесения должна быть кратна 100" + "\n" + "Попробуйте еще раз" + "\n");
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.printf("Сумма должна быть кратна 100" + "\n");
                                break;

                            }


                        } else {
                            System.out.println("Ваш номер счета заблокирован");
                            break;

                        }

                    default:
                        break;
                }

            }

            flag1=null;

        }
        if (flag1.equals(false)) {
            System.out.println("Неверно введена пара ID:PIN!");
        }

    }

    @Override
    public void drawMenu(InputService inputService, AtmService atmService, AdminService adminService, FileService fileService) throws IOException, ClassNotFoundException, InterruptedException {
        String value=null;
        List<Object> values;
        Boolean flag2 = false;

        values= atmService.returnValues(fileService);
        Integer validationCode = inputService.enterCode();
        BigDecimal amount= BigDecimal.ZERO;
        BigDecimal money;
        for (Object o : values) {


            if ((validationCode.equals(o.toString()))) {
                continue;
            }
        }
        while (!(flag2)) {


            flag2 = false;
            System.out.println("Нажмите клавишу enter, чтобы начать работу с системой");
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader scanerMenu = new BufferedReader(isr);
            value = scanerMenu.readLine();

            while (!value.equals("exit")) {

                System.out.println("1-Создать нового пользователя");
                System.out.println("2-Посмотреть список пользователей");
                System.out.println("3-Заблокировать пользователя");
                System.out.println("4-Разблокировать пользователя");
                System.out.println("5-Поменять PIN код пользователя");
                System.out.println("6-Добавить денег в банкомат");


                //проверки валидность меню содержатся в методе adminCodeTerminalMenu()
                value = inputService.adminCodeTerminalMenu();

                switch (value) {

                    case "1":

                        adminService.createUser(inputService, fileService, atmService);

                        break;
                    case "2":
                        adminService.viewAccounts(fileService);
                        System.out.println("\n");
                        break;
                    case "3":
                        adminService.lock(fileService);
                        break;
                    case "4":
                        adminService.unlock(fileService);
                        break;

                    case "5":
                        adminService.changeCode(fileService);
                        break;
                    case "6":
                        BigDecimal balance= atmService.getCachAtm();
                        money=inputService.enterBalance();
                        amount=amount.add(money);
                        atmService.debetBalanceforAdmin(amount);
                        break;
                    default:

                        break;
                }

                flag2=true;  }

        }
    }
}
