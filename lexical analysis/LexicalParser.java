import java.util.Scanner;

public class LexicalParser {
    public boolean isDelimiter(String ch) {
        return ch.equals(" ") || ch.equals(",") ||
                ch.equals(";") || ch.equals("(") ||
                ch.equals(")") || ch.equals("\"") ||
                ch.equals("[") || ch.equals("]") ||
                ch.equals("{") || ch.equals("}");
    }

    public boolean isMathOperator(String ch) {
        return ch.equals("+") || ch.equals("-") || ch.equals("*") ||
                ch.equals("/") || ch.equals(">") || ch.equals("<") ||
                ch.equals("=") || ch.equals("<=") || ch.equals(">=");
    }

    public boolean isOperator(String ch) {
        return ch.equals("==") || ch.equals("!=") ||
                ch.equals("&&") || ch.equals("||") ||
                ch.equals("++") || ch.equals("--") ||
                ch.equals("<<") || ch.equals(">>") ||
                ch.equals("+=") || ch.equals("-=") ||
                ch.equals("*=") || ch.equals("/=") ||
                ch.equals("%=") || ch.equals(">>=") ||
                ch.equals("<<=") || ch.equals("&=") ||
                ch.equals("|=") || ch.equals("^=") || ch.equals(".");
    }

    public boolean isMethod(String str) {
        return isAttribute(str) && str.contains("(") && str.contains(")");
    }

    public boolean isAttribute(String str) {
        return validIdentifier(str) && str.contains(".");
    }

    public boolean isClass(String str) {
        return validIdentifier(str) && !isConstructor(str) && Character.isUpperCase(str.charAt(0)) && !str.contains(".");
    }

    public boolean isConstructor(String str) {

        int length = str.length();

        if (validIdentifier(str) && str.charAt(length - 1) == ')') {
            for (int i = length - 1; i >= 1; i--) {
                if (str.charAt(i) == '(') {
                    return true;
                }
            }
        }
        return false;
    }
 
    public boolean validIdentifier(String str) {
        return !isInteger(str.substring(0, 1)) && !isDelimiter(str.substring(0, 1)) && !isKeyword(str);
    }

    public boolean isKeyword(String str) {
        return str.equals("abstract") || str.equals("continue") ||
                str.equals("for") || str.equals("new") ||
                str.equals("switch") || str.equals("assert") ||
                str.equals("default") || str.equals("goto") ||
                str.equals("package") || str.equals("synchronized") ||
                str.equals("boolean") || str.equals("do") ||
                str.equals("if") || str.equals("private") ||
                str.equals("this") || str.equals("break") ||
                str.equals("byte") || str.equals("case") ||
                str.equals("catch") || str.equals("char") ||
                str.equals("class") || str.equals("const") ||
                str.equals("double") || str.equals("else") ||
                str.equals("enum") || str.equals("extends") ||
                str.equals("final") || str.equals("finally") ||
                str.equals("float") || str.equals("implements") ||
                str.equals("import") || str.equals("instanceof") ||
                str.equals("int") || str.equals("interface") ||
                str.equals("long") || str.equals("native") ||
                str.equals("short") || str.equals("static") ||
                str.equals("strictfp") || str.equals("protected") ||
                str.equals("public") || str.equals("return") ||
                str.equals("super") || str.equals("throw") ||
                str.equals("throws") || str.equals("transient") ||
                str.equals("try") || str.equals("void") ||
                str.equals("volatile") || str.equals("while");
    }

    public boolean isInteger(String str) {
        int i, len = str.length();

        if (len == 0) {
            return false;
        }
        for (i = 0; i < len; i++) {
            if (str.charAt(i) != '0' && str.charAt(i) != '1' && str.charAt(i) != '2'
                    && str.charAt(i) != '3' && str.charAt(i) != '4' && str.charAt(i) != '5'
                    && str.charAt(i) != '6' && str.charAt(i) != '7' && str.charAt(i) != '8'
                    && str.charAt(i) != '9' || (str.charAt(i) == '-' && i > 0)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRealNumber(String str) {
        int i, len = str.length();
        boolean hasDecimal = false;

        if (len == 0) {
            return false;
        }
        for (i = 0; i < len; i++) { //-4.0
            if (str.charAt(i) != '0' && str.charAt(i) != '1' && str.charAt(i) != '2'
                    && str.charAt(i) != '3' && str.charAt(i) != '4' && str.charAt(i) != '5'
                    && str.charAt(i) != '6' && str.charAt(i) != '7' && str.charAt(i) != '8'
                    && str.charAt(i) != '9' && str.charAt(i) != '.' ||
                    (str.charAt(i) == '-' && i > 0)) {
                return false;
            }
            if (str.charAt(i) == '.') {
                hasDecimal = true;
            }
        }
        return hasDecimal;
    }

    public void parse(String str) {
        String[] tokens = str.split("\\s+");
        // int a = 5 * b ;
        for (String subStr : tokens) {
            if (isMathOperator(subStr)) {
                System.out.print(subStr + ": Math Operator\n");
            } else if (isKeyword(subStr)) {
                System.out.print(subStr + ": Keyword\n");
            } else if (isOperator(subStr)) {
                System.out.print(subStr + ": Operator\n");
            } else if (isInteger(subStr)) {
                System.out.print(subStr + ": Integer\n");
            } else if (isRealNumber(subStr)) {
                System.out.print(subStr + ": Real Number\n");
            } else if (isMethod(subStr) && !isDelimiter(subStr)) {
                System.out.print(subStr + ": Method Call\n");
            } else if (isAttribute(subStr) && !isDelimiter(subStr)) {
                System.out.print(subStr + ": Attribute\n");
            } else if (isClass(subStr) && !isDelimiter(subStr)) {
                System.out.print(subStr + ": Class\n");
            } else if (isConstructor(subStr) && !isDelimiter(subStr)) {
                System.out.print(subStr + ": Constructor\n");
            } else if (validIdentifier(subStr) && !isDelimiter(subStr)) {
                System.out.print(subStr + ": Valid Identifier\n");
            } else if (!validIdentifier(subStr) && !isDelimiter(subStr)) {
                System.out.print(subStr + ": Not A Valid Identifier\n");
            }else if(isDelimiter(subStr)) {
                System.out.print(subStr + ": is a valid delimeter\n");
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        LexicalParser lexicalParser = new LexicalParser();
        lexicalParser.parse(str.trim());

    }
}
