package cn.azsy.zstokhttp.zsyokhttp.zok.checkjson;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * Created by zsy on 2017/6/13.
 */

public class JsonValidator {


    private CharacterIterator characterIterator;
    private char aChar;
    private int col;

    //构造函数
    public JsonValidator() {
    }

    /**
     * 验证一个字符串是否是合法的JSON串
     *
     * @param input 要验证的字符串
     * @return true-合法 ，false-非法
     */
    public boolean validate(String input) {
        input = input.trim();//先去掉两端空格
        boolean ret = valid(input);
        return ret;
    }

    private boolean valid(String input) {
        if ("".equals(input)) return true;//json为空则直接返回

        boolean ret = true;
        characterIterator = new StringCharacterIterator(input);
        aChar = characterIterator.first();//文本中的第一个字符
        System.out.println("文本中的第一个字符  ==  " + aChar);
        col = 1;
        if (!value()) {
            ret = error("value", 1);
        } else {
            skipWhiteSpace();
            if (aChar != CharacterIterator.DONE) {
                ret = error("end", col);
            }
        }

        return ret;
    }

    private boolean value() {
        return literal("true")      //判断即送相当于true,false,null时返回true
                || literal("false")
                || literal("null")
                || string()
                || number()
                || object()
                || array();
    }

    private boolean literal(String text) {
        CharacterIterator ci = new StringCharacterIterator(text);
        char t = ci.first();
        if (aChar != t) return false;//判断第一个字符不是't',就返回false

        int start = col;
        boolean ret = true;
        for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
            if (t != nextCharacter()) {
                ret = false;
                break;
            }
        }
        nextCharacter();
        if (!ret) error("literal " + text, start);
        return ret;
    }

    private boolean array() {
        return aggregate('[', ']', false);
    }

    private boolean object() {
        return aggregate('{', '}', true);
    }

    private boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
        if (aChar != entryCharacter) return false;
        nextCharacter();
        skipWhiteSpace();
        if (aChar == exitCharacter) {
            nextCharacter();
            return true;
        }

        for (; ; ) {
            if (prefix) {
                int start = col;
                if (!string()) return error("string", start);
                skipWhiteSpace();
                if (aChar != ':') return error("colon", col);
                nextCharacter();
                skipWhiteSpace();
            }
            if (value()) {
                skipWhiteSpace();
                if (aChar == ',') {
                    nextCharacter();
                } else if (aChar == exitCharacter) {
                    break;
                } else {
                    return error("comma or " + exitCharacter, col);
                }
            } else {
                return error("value", col);
            }
            skipWhiteSpace();
        }

        nextCharacter();
        return true;
    }

    private boolean number() {
        if (!Character.isDigit(aChar) && aChar != '-') return false;
        int start = col;
        if (aChar == '-') nextCharacter();
        if (aChar == '0') {
            nextCharacter();
        } else if (Character.isDigit(aChar)) {
            while (Character.isDigit(aChar))
                nextCharacter();
        } else {
            return error("number", start);
        }
        if (aChar == '.') {
            nextCharacter();
            if (Character.isDigit(aChar)) {
                while (Character.isDigit(aChar))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        if (aChar == 'e' || aChar == 'E') {
            nextCharacter();
            if (aChar == '+' || aChar == '-') {
                nextCharacter();
            }
            if (Character.isDigit(aChar)) {
                while (Character.isDigit(aChar))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        return true;
    }

    private boolean string() {
        if (aChar != '"') return false;

        int start = col;
        boolean escaped = false;
        for (nextCharacter(); aChar != CharacterIterator.DONE; nextCharacter()) {
            if (!escaped && aChar == '\\') {
                escaped = true;
            } else if (escaped) {
                if (!escape()) {
                    return false;
                }
                escaped = false;
            } else if (aChar == '"') {
                nextCharacter();
                return true;
            }
        }
        return error("quoted string", start);
    }

    private boolean escape() {
        int start = col - 1;
        if (" \\\"/bfnrtu".indexOf(aChar) < 0) {
            return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", start);
        }
        if (aChar == 'u') {
            if (!ishex(nextCharacter()) || !ishex(nextCharacter()) || !ishex(nextCharacter())
                    || !ishex(nextCharacter())) {
                return error("unicode escape sequence  \\uxxxx ", start);
            }
        }
        return true;
    }

    private boolean ishex(char d) {
        return "0123456789abcdefABCDEF".indexOf(aChar) >= 0;
    }

    private char nextCharacter() {
        aChar = characterIterator.next();
        ++col;
        return aChar;
    }

    private void skipWhiteSpace() {
        while (Character.isWhitespace(aChar)) {
            nextCharacter();
        }
    }

    private boolean error(String type, int col) {
        System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
        return false;
    }


    public static void main(String[] args) {
        String jsonStr = "{\"website\":\"open-open.com\"}";
        System.out.println(jsonStr + ":" + new JsonValidator().validate(jsonStr));
    }


}
