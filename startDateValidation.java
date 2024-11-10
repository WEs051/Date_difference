public class startDateValidation {
    static boolean leap = false;
    public static void startDateValidate(String date) throws Exception {
        String label = "Start date: ";
        String[] date1Parts = date.split("/");
        if (date1Parts.length != 3) {
            throw new CPSC1181Exception((label + "missing or too many fields. The correct format is \"DD/MM/YYYY\""));
        }
        int startMonth, startDay, year;
        try {
            startMonth = Integer.parseInt(date1Parts[1]);
            startDay = Integer.parseInt(date1Parts[0]);
            year = Integer.parseInt(date1Parts[2]);
        } catch (NumberFormatException e) {
            throw new CPSC1181Exception(label + "only digits are allowed D, M and Y are digits in DD/MM/YYYY");
        }
            if (year % 400 == 0) {
                leap = true;
            } else if (year % 4 == 0 && year % 100 != 0) {
                leap = true;
            }
        checkYear(date1Parts, label);
        testDay1(startDay, startMonth, label);
        checkMonth(startMonth, label);
        checkFormat(startMonth, startDay, label);
        checkNonNumbers(date, label);

        leap = false;
    }

    public static void endDateValidate(String date) throws Exception {
        String label = "End date: ";
        String[] date2Parts = date.split("/");
        if (date2Parts.length != 3) {
            throw new CPSC1181Exception((label + "missing or too many fields. The correct format is \"DD/MM/YYYY\""));
        }
        int endMonth, endDay, year;
        try {
            endMonth = Integer.parseInt(date2Parts[1]);
            endDay = Integer.parseInt(date2Parts[0]);
            year = Integer.parseInt(date2Parts[2]);
        } catch (NumberFormatException e) {
            throw new CPSC1181Exception(label + "only digits are allowed D, M and Y are digits in DD/MM/YYYY");
        }
        if(year % 400 == 0){
            leap = true;
        } else if (year % 4 == 0 && year % 100 != 0){
            leap = true;
        }
        checkYear(date2Parts, label);
        testDay2(endMonth, endDay, label);
        checkMonth(endMonth, label);
        checkFormat(endMonth, endDay, label);
        checkNonNumbers(date, label);
        leap = false;
    }

    public static void checkYear(String[] dateParts, String label) throws Exception {
        int year = Integer.parseInt(dateParts[2]);
        if (year < 1000) {
            throw new CPSC1181Exception(label + "minimum year is 1000");
        } else if (year > 3000) {
            throw new CPSC1181Exception(label + "maximum year is 3000");
        }
    }

    public static void testDay1(int day, int month, String label) throws Exception {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 8:
            case 7:
            case 10:
            case 12:
                thirtyOneDaysMonth(day, label);
                break;
            case 2:
                twentyEightDaysMonth(day, label);
                break;
            default:
                thirtyDaysMonth(day, label);
                break;
        }
    }

    public static void testDay2(int day, int month, String label) throws Exception {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 8:
            case 7:
            case 10:
            case 12:
                thirtyOneDaysMonth(day, label);
                break;
            case 2:
                twentyEightDaysMonth(day, label);
                break;
            default:
                thirtyDaysMonth(day, label);
                break;
        }
    }

    public static void twentyEightDaysMonth(int day, String label) throws Exception {
        if(!leap){
            if (day < 1 || day > 28) {
                throw new CPSC1181Exception(label + "incorrect day digits of " + day);
            }
        } else {
            if(day < 1 || day > 29){
                throw new CPSC1181Exception(label + "incorrect day digits of " + day);
            }
        }
    }

    public static void thirtyDaysMonth(int day, String label) throws Exception {
        if (day < 1 || day > 30) {
            throw new CPSC1181Exception(label + "incorrect day digits of " + day);
        }
    }

    public static void thirtyOneDaysMonth(int day, String label) throws Exception {
        if (day < 1 || day > 31) {
            throw new CPSC1181Exception(label + "incorrect day digits of " + day);
        }
    }

    public static void checkMonth(int month, String label) throws Exception {
        if (month < 1 || (month > 12 && month < 100)) {
            throw new CPSC1181Exception(label + "incorrect month digits of " + month);
        }
    }

    public static void checkFormat(int month, int day, String label) {
        if (month > 100 || day < 1 || day > 100) {
            throw new CPSC1181Exception(label + "not the right number of symbols. Use the following format DD/MM/YYYY");
        }
    }

    public static void checkNonNumbers(String date, String label) throws  Exception{
        int count = 0;
        for (int i = 0; i < date.length(); i++) {
            if (date.charAt(i) < 47 || date.charAt(i) > 57) {
                throw new CPSC1181Exception(label + "only digits are allowed D, M and Y are digits in DD/MM/YYYY");
            }
            if(date.charAt(i) == '/'){
                count++;
            }
        }
        if(count != 2){
            throw new CPSC1181Exception(label + "invalid date, must be in format \"DD/MM/YYYY\"");
        }
    }
}
