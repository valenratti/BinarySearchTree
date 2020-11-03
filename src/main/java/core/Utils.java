package core;

public class Utils {
    public static boolean isOperator(String data) {
        if(data.matches("\\+|-|/|\\*|\\(|\\)|\\^"))
            return true;
        return false;
    }

    public static boolean isConstant(String data) {
        if(data.matches("-?[0-9]+(.[0-9]+)?|x[0-9]+"))
            return true;
        return false;
    }
}
