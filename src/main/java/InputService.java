import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import static java.lang.Long.parseLong;

/**
 * Created by artem on 19.05.16.
 */
public class InputService {
    protected InputService() {
    }
    //ввод pin-сode
    protected Integer enterCode() throws IOException {
        System.out.println("Введите PIN код");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader scanerUserID = new BufferedReader(isr);

        String userCode = scanerUserID.readLine();
        while (!(isInt(userCode)) || !(isLengthCode(userCode)) || (!"".equals(userCode) && userCode != null && (userCode.isEmpty()) && (isInt(userCode)))) {

            System.out.println("Введите PIN код");


            try {
                userCode = scanerUserID.readLine();
            } catch (NumberFormatException e) {
                enterCode();
            }
        }

        return Integer.parseInt(userCode);
    }

    //ввод блокировки
    protected  Boolean enterLock() throws IOException {
        System.out.println("Ввод блокировки, пожалуйста введите значение:");
        System.out.println("lock or unlock");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader scanerUserLock = new BufferedReader(isr);
        String stringLock = scanerUserLock.readLine();
        stringLock = stringLock.toString().toLowerCase();
        while (!(stringLock.equals("lock") || stringLock.equals("unlock"))) {
            System.out.println("lock or unlock");

            stringLock = scanerUserLock.readLine();

        }
        boolean userLock = parseLock(stringLock);
        return userLock;
    }

    public  Boolean parseLock(String scanerUserLock) {

        if (scanerUserLock.equals("lock")) {
            return true;
        } else if (scanerUserLock.equals("unlock")) {
            return false;
        }

        return null;
    }

    //ввод ID
    public  Long enterID() throws IOException {
        System.out.println("Введите номер банковской карты");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader scanerUserID = new BufferedReader(isr);

        String userID = scanerUserID.readLine();
        userID = userID.replace(" ", "");
        while (!(isLong(userID)) || !(isLengthID(userID)) || (!"".equals(userID) && userID != null && (userID.isEmpty()))) {
            System.out.println("Введите номер банковской карты");

            try {
                userID = scanerUserID.readLine();
                userID = userID.replace(" ", "");
            } catch (NumberFormatException e) {
                enterID();
            }
        }

        return parseLong(userID);
    }

    //ввод ID
    public  Long enterPhone() throws IOException {
        System.out.println("Введите номер телефона");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader scanerPhone = new BufferedReader(isr);

        String userPhone = scanerPhone.readLine();
        if(!(userPhone.replaceFirst("^8", "")).equals(userPhone)) {
            userPhone = (userPhone.replaceFirst("^8", ""));
            userPhone=userPhone.replace("(", "");
            userPhone=userPhone.replace(")", "");
         //   System.out.println(userPhone);
        } else if(!userPhone.equals(userPhone.replace("+7", "^\\d{10}$"))){
            userPhone=userPhone.replace("+","");
            userPhone=userPhone.replace("(", "");
            userPhone=userPhone.replace(")", "");
            userPhone=userPhone.replaceFirst("^7", "");}

       // System.out.println(userPhone);
        while (!(isLong(userPhone)) || !(isLengthPhone(userPhone)) || (!"".equals(userPhone) && userPhone != null && (userPhone.isEmpty()))) {
            System.out.println("Ошибка в номере, введите номер телефона");

            try {
                userPhone = scanerPhone.readLine();
                if(!(userPhone.replaceFirst("^8", "")).equals(userPhone)) {
                    userPhone = (userPhone.replaceFirst("^8", ""));
                    userPhone=userPhone.replace("(", "");
                    userPhone=userPhone.replace(")", "");
                    System.out.println(userPhone);
                } else if(!userPhone.equals(userPhone.replace("+7", "^\\d{10}$"))){
                    userPhone=userPhone.replace("+","");
                    userPhone=userPhone.replace("(", "");
                    userPhone=userPhone.replace(")", "");
                    userPhone=userPhone.replaceFirst("^7", "");}


                System.out.println(userPhone);

            } catch (NumberFormatException e) {

            }
        }

        return parseLong(userPhone);
    }

    //проверка на кооректность банксковского счета
    public  boolean isLengthID(String x) throws NumberFormatException {
        long Len;
        Len = x.length();
        if (Len == 16) {
            return true;
        } else return false;
    }

    //проверка на кооректность телефона
    public boolean isLengthPhone(String x) throws NumberFormatException {
        long Len;
        Len = x.length();
        if (Len == 10) {
            return true;
        } else return false;
    }

    //проверка длины пин-кода
    public  boolean isLengthCode(String x) throws NumberFormatException {
        int Len;
        Len = x.length();
        if (Len == 4) {
            return true;
        } else return false;
    }

    public  boolean isLengthValueForTerminal(String x) throws NumberFormatException {
        int Len;
        Len = x.length();
        if ((Len == 1)) {
            return true;
        } else return false;
    }

    public  boolean checkingValueForTerminalAdmin(String x) throws NumberFormatException {
        int Len;
        Len = Integer.parseInt(x);
        if ((Len>0)&&(Len<7)) {
            return true;
        } else return false;
    }

    public  boolean checkingValueForTerminalUser(String x) throws NumberFormatException {
        int Len;
        Len = Integer.parseInt(x);
        if ((Len>0)&&(Len<5)) {
            return true;
        } else return false;
    }

    public  boolean isInt(String x) throws NumberFormatException {
        try {

            Integer.parseInt(x);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLong(String x) throws NumberFormatException {
        try {
            if(Long.parseLong(x)>0)
                return true;

        } catch (Exception e) {
            return false;
        }
    return false;}

    public  boolean isDouble(String x) throws NumberFormatException {
        try {
            x = x.replace(',', '.');
            if(Double.parseDouble(x)>0)

                return true;

        } catch (Exception e) {
            return false;
        }
    return false;}

    public  Boolean isZero(Double x) throws NumberFormatException {

        try {
            return (x.intValue() == 0);

        } catch (Exception e) {
            return false;
        }
    }

    //ввод баланса
    public  BigDecimal enterBalance() throws IOException {

        System.out.println("Введите сумму");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader scanerMoney = new BufferedReader(isr);
        String money = scanerMoney.readLine();
        money = money.replace(',', '.');


        while ( !isDouble(money) && !isLong(money)  ||(!"".equals(money) && money != null && (money.isEmpty()))) {
            System.out.println("Введите сумму");

            try {

                money = scanerMoney.readLine();
                money = money.replace(',', '.');

                } catch (NumberFormatException e) {
                //enterBalance();
            }
        }

        return new BigDecimal(money);
    }
    //ввод операции в теминале
    protected  String adminCodeTerminalMenu() throws IOException {
        System.out.println("Выберите пункт меню или введите команду exit для выхода из системы");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader scanerValue = new BufferedReader(isr);

        String value = scanerValue.readLine();

        if (!(value).equals("exit")) {

            while (!(isInt(value)) || !(isLengthValueForTerminal(value)) || !(checkingValueForTerminalAdmin(value)) || (!"".equals(value) && value != null && (value.isEmpty()) && (isInt(value)))) {
                System.out.println();
                System.out.println("Выберите пункт меню или введите команду exit для выхода из системы");


                try {
                    value = scanerValue.readLine();
                } catch (NumberFormatException e) {
                    adminCodeTerminalMenu();
                }
            }

            return value;
        } else { return value;

        }
    }

    protected  String userCodeTerminalMenu() throws IOException {
        System.out.println();
        System.out.println("Выберите пункт меню или введите команду exit для выхода из банкомата");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader scanerValue = new BufferedReader(isr);

        String value = scanerValue.readLine();
        if (!(value).equals("exit")) {
            while (!(isInt(value)) || !(isLengthValueForTerminal(value)) || !(checkingValueForTerminalUser(value)) || (!"".equals(value) && value != null && (value.isEmpty()) && (isInt(value)))) {

                System.out.println();
                System.out.println("Выберите пункт меню или введите команду exit для выхода из банкомата");


                try {
                    value = scanerValue.readLine();
                } catch (NumberFormatException e) {
                    userCodeTerminalMenu();
                }
            }

            return value;
        } else {return value;}
    }



}
