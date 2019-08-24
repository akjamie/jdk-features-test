package org.akj.algorithm;

import java.math.BigInteger;
import java.security.InvalidParameterException;

/**
 * big string number Operation utils
 *
 * @Author Jamie Zhang
 */
public class BigStringNumOperationUtil {
    private static final String REGEX = "^[0-9]+$";

    public static void main(String[] args) {
        //test add
        String arg1 = "234456577687512312312321756";
        String arg2 = "12324242";
        String result = BigStringNumOperationUtil.add(arg1, arg2);
        BigInteger expected = new BigInteger(arg1).add(new BigInteger(arg2));

        BigStringNumOperationUtil.assertEquals(expected, new BigInteger(result));
        System.out.println("expected: " + expected + ", actual: " + result);

        System.out.println(BigStringNumOperationUtil.subtract(arg1, arg2));
        System.out.println(new BigInteger(arg1).subtract(new BigInteger(arg2)));

        System.out.println(BigStringNumOperationUtil.subtract(arg2, arg1));
        System.out.println(new BigInteger(arg2).subtract(new BigInteger(arg1)));
    }

    private static void assertEquals(BigInteger expected, BigInteger actual) {
        if (!expected.equals(actual)) {
            throw new IllegalStateException("Assert failed,expected:" + expected + ", actual:" + actual);
        }
    }

    /**
     * Big string numbers add
     *
     * @param arg1
     * @param arg2
     * @return
     */
    public static String add(String arg1, String arg2) {
        //1.verify the input, if not valid throw exception
        if (null == arg1 || null == arg2 || !arg1.matches(REGEX) || !arg2.matches(REGEX)) {
            throw new InvalidParameterException();
        }

        //2.extract each 'bit' for calculation, reverse to make sure the calculation starts from lower bits,
        // rather than from higher bits
        char[] args1 = new StringBuffer(arg1).reverse().toString().toCharArray();
        char[] args2 = new StringBuffer(arg2).reverse().toString().toCharArray();

        //3. calculation
        int length = args1.length > args2.length ? args1.length : args2.length;
        //高位可能进位
        int[] result = new int[length + 1];

        // add for each 'bit'
        for (int i = 0; i < length + 1; i++) {
            // '0' -> 48, ASCII of other numeric number minus '0' -> real number in Dec
            int abit = i < arg1.length() ? (args1[i] - '0') : 0;
            int bbit = i < arg2.length() ? (args2[i] - '0') : 0;
            result[i] = abit + bbit;
        }

        // handle bit exceeds capacity cases
        for (int i = 0; i < result.length; i++) {
            if (result[i] > 10) {
                result[i + 1] += 1;
                result[i] %= 10;
            }
        }

        //4.assemble the result
        boolean zeroFlag = true;
        StringBuffer buffer = new StringBuffer();
        for (int i = length; i >= 0; i--) {
            if (result[i] == 0 && zeroFlag) {
                continue;
            } else {
                zeroFlag = false;
            }
            buffer.append(result[i]);
        }

        return buffer.toString();
    }

    /**
     * Big string number subtract
     *
     * @param arg1
     * @param arg2
     * @return
     */
    public static String subtract(String arg1, String arg2) {
        //1.verify the input, if not valid throw exception
        if (null == arg1 || null == arg2 || !arg1.matches(REGEX) || !arg2.matches(REGEX)) {
            throw new InvalidParameterException();
        }

        //2. compare(arg1, arg2) to get the +/- sign for the final result
        int val = isGreatThan(arg1, arg2);
        // if arg1 == args, the result is 0;
        if (val == 0) {
            return "0";
        }

        //3.extract each 'bit' for calculation, reverse to make sure the calculation starts from lower bits,
        // rather than from higher bits
        char[] args1 = null;
        char[] args2 = null;
        String argTmp1 = arg1;
        String argTmp2 = arg2;

        if (val > 0) {
            args1 = new StringBuffer(arg1).reverse().toString().toCharArray();
            args2 = new StringBuffer(arg2).reverse().toString().toCharArray();
        } else {
            args1 = new StringBuffer(arg2).reverse().toString().toCharArray();
            args2 = new StringBuffer(arg1).reverse().toString().toCharArray();

            String temp = argTmp1;
            argTmp1 = argTmp2;
            argTmp2 = temp;
        }

        //4. calculation
        int length = args1.length > args2.length ? args1.length : args2.length;
        int[] result = new int[length];

        // minus for each 'bit'
        for (int i = 0; i < length; i++) {
            // '0' -> 48, ASCII of other numeric number minus '0' -> real number in Dec
            int abit = i < argTmp1.length() ? (args1[i] - '0') : 0;
            int bbit = i < argTmp2.length() ? (args2[i] - '0') : 0;
            result[i] = abit - bbit;
        }

        // handle bit with negative number
        for (int i = 0; i < result.length; i++) {
            if (result[i] < 0) {
                result[i + 1] -= 1;
                result[i] += 10;
            }
        }

        //5.assemble the result
        StringBuffer buffer = new StringBuffer();
        //if arg1 < arg2, the result should be an negative value
        if (val < 0) buffer.append("-");
        for (int i = length - 1; i >= 0; i--) {
            buffer.append(result[i]);
        }

        return buffer.toString();
    }

    /**
     * compare arg1 with arg2
     *
     * @param arg1
     * @param arg2
     * @return 1: arg1>arg2; 0: arg1==arg2; -1: arg1<arg2
     */
    private static int isGreatThan(String arg1, String arg2) {
        int length1 = arg1.length();
        int length2 = arg2.length();
        char[] args1 = arg1.toCharArray();
        char[] args2 = arg2.toCharArray();

        int flag = 0;

        // arg1 is larger than arg2
        if (length1 > length2)
            flag = 1;
            // arg1 is lower than arg2
        else if (length1 < length2)
            flag = -1;
            // have same length
        else {
            int n = length1;
            int i = 0;
            for (; i < n; i++) {
                if (args1[i] > args2[i]) {
                    flag = 1;
                    break;
                } else if (args1[i] < args2[i]) {
                    flag = -1;
                    break;
                }
            }
            if ((i == n) && args1[i - 1] == args2[i - 1])
                flag = 1;
        }
        return flag;
    }
}
