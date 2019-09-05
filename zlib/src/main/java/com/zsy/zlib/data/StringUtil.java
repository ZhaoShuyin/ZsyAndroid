package com.zsy.zlib.data;

/**
 * Title: ZsyGit
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/5/27 16:46
 */

public class StringUtil {


    private String removeSign(String str) {
        StringBuffer sb = new StringBuffer();
        for (char item : str.toCharArray()) {
            if (charReg(item)) {
                sb.append(item);
            }
        }
        return sb.toString();
    }

    /**
     * 利用正则表达式来解析字符串
     */
    private boolean charReg(char charValue) {
        return (charValue >= 0x4E00 && charValue <= 0X9FA5)
                || (charValue >= 'a' && charValue <= 'z')
                || (charValue >= 'A' && charValue <= 'Z')
                || (charValue >= '0' && charValue <= '9');
    }

    private double similarDegree(String strA, String strB) {
        String newStrA = removeSign(strA);
        String newStrB = removeSign(strB);

        int temp = Math.max(newStrA.length(), newStrB.length());
        int lenA = newStrA.length();
        int lenB = newStrB.length();
        int temp2 = 0;
        if (lenA >= lenB) { //在此判断newStrA和newStrB的长度
            temp2 = longestCommonSubstring(newStrA, newStrB).length();
        } else {
            temp2 = longestCommonSubstring(newStrB, newStrA).length();
        }
        return temp2 * 1.0 / temp;
    }
    /**
     * 解析两个字符串的相同部分的长度，返回公共部分，strA字符串长度 > strB字符串
     */
    private String longestCommonSubstring(String strA, String strB) {
        char[] charsStrA = strA.toCharArray();
        char[] charsStrB = strB.toCharArray();
        int m = charsStrA.length;
        int n = charsStrB.length;
        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (charsStrA[i - 1] == charsStrB[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                }
            }
        }
        char[] result = new char[matrix[m][n]];
        int currentIndex = result.length - 1;
        while (matrix[m][n] != 0) {
            if (matrix[n] == matrix[n - 1]) {
                n--;
            } else if (matrix[m][n] == matrix[m - 1][n]) {
                m--;
            } else {
                result[currentIndex] = charsStrA[m - 1];
                currentIndex--;
                n--;
                m--;
            }
        }
        return new String(result);
    }

}
