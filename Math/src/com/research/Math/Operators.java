package com.research.Math;

/**
 * Created by 000407 on 9/23/2016.
 */
public class Operators {
    private static int[] num1, num2;

    public static void main(String[] args) {
        System.out.println(toStr(divide("38", "7", 5)));
    }

    //Transformation methods: Converts a string into an array

    /**
     * Transforms a given number as a string, into an integer array in the reverse order.
     *
     * @param num Number to be transformed as a java.lang.String
     * @param len Length of the required array
     * @return Returns the integer array, is the transformation is successful, null otherwise.
     * @throws Error if the string is not really an integer value. (containing invalid characters in a number).
     */
    private static int[] transformReverse(String num, int len) throws Error {
        int arr[] = null;
        if (!num.matches("^[+-]?\\d+$"))
            throw new Error("Invalid number detected!");
        arr = new int[len];
        for (int i = 0; i < num.length(); i++) {
            arr[num.length() - i - 1] = num.codePointAt(i) - 48;
        }
        return arr;
    }

    /**
     * Transforms a given number as a string, into an integer array.
     *
     * @param num Number to be transformed as a java.lang.String
     * @param len Length of the required array
     * @return Returns the integer array, is the transformation is successful, null otherwise.
     * @throws Error if the string is not really an integer value. (containing invalid characters in a number).
     */
    private static int[] transform(String num, int len) throws Error {
        int arr[] = null;
        if (!num.matches("^[+-]?\\d+$"))
            throw new Error("Invalid number detected!");
        arr = new int[len];
        for (int i = 0; i < num.length(); i++) {
            arr[i] = num.codePointAt(i);
        }
        return arr;
    }

    /**
     * Generates the diminished radix complement of a decimal number.
     *
     * @param arr Array containing the integer to find the complement of
     */
    private static void ninesComp(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            arr[i] = 9 - arr[i];
    }

    /**
     * Generates the radix complement of a decimal number.
     *
     * @param arr Number that needs to be complemented.
     */
    private static int[] tensComp(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            arr[i] = 9 - arr[i];
        int[] one = new int[arr.length];
        one[0] = 1;
        positionAdd(arr, one, arr, 0, 0);
        return arr;
    }

    /**
     * Compares two given integers. Integers are taken as strings.
     *
     * @param num1 Number to compare
     * @param num2 Number to be compared with
     * @return int[] array containing the indices of the
     * - first occurrence of value at index in num1 is less than the value at index in num2.
     * - first occurrence of value at index in num1 is greater than the value at index in num2.
     */
    private static int[] compare(String num1, String num2) {
        boolean flags[][];
        int len = num1.length() > num2.length() ? num1.length() : num2.length();
        int[] n1 = new int[len], n2 = new int[len];
        flags = new boolean[len][2];

        for (int i = 0; i < len; i++) {
            if (i < num1.length())
                n1[i] = num1.codePointAt(num1.length() - i - 1) - 48;

            if (i < num2.length())
                n2[i] = num2.codePointAt(num2.length() - i - 1) - 48;
        }

        for (int i = 0; i < len; i++) {
            flags[len - i - 1][0] = n1[i] >= n2[i];

            flags[len - i - 1][1] = n1[i] <= n2[i];
        }

        int flag[] = {0, 0};
        for (flag[0] = 0; flag[0] < flags.length; flag[0]++) {
            if (!flags[flag[0]][0])
                break;
        }

        for (flag[1] = 0; flag[1] < flags.length; flag[1]++) {
            if (!flags[flag[1]][1])
                break;
        }

        return flag;
    }

    /**
     * Performs the positional addition recursively until reaches the end of the array.
     *
     * @param a   First addend
     * @param b   Second addend
     * @param ans Sum
     * @param pos Current position being operated
     * @param c   Carry forward to next position
     */
    private static void positionAdd(int[] a, int[] b, int[] ans, int pos, int c) {
        if (pos == ans.length)
            return;
        int numA = pos < a.length ? a[pos] : 0, numB = pos < b.length ? b[pos] : 0;
        ans[pos] = (numA + numB + c) % 10;
        int carry = (numA + numB + c) / 10;
        positionAdd(a, b, ans, ++pos, carry);
    }

    /**
     * Performs the subtraction at a given position.
     *
     * @param a   The minuend
     * @param b   The subtrahend
     * @param ans The difference
     * @param pos Current position being operated
     * @param br  Borrow from the next position
     */
    private static void positionSubtract(int[] a, int[] b, int[] ans, int pos, int br) {
        if (pos == ans.length)
            return;

        int tmp = a[pos] - br - b[pos];
        br = 0;
        if (tmp < 0) {
            tmp += 10;
            br = 1;
        }
        ans[pos] = tmp;

        positionSubtract(a, b, ans, ++pos, br);
    }

    /**
     * Performs the multiplication over an indicated position recursively.
     *
     * @param num    The multiplicand
     * @param mp     The multiplier
     * @param posAns Array that holds the multiple
     * @param pos    Current position being operated
     * @param c      Carry forward to the next point
     * @param offset Offset from the decimal point before adding to the overall answer
     */
    private static void positionMultiply(int[] num, int mp, int[] posAns, int pos, int c, int offset) {
        if (pos == num.length)
            return;

        int val = (num[pos] * mp + c) % 10;
        c = (num[pos] * mp + c) / 10;
        posAns[pos + offset] = val;

        positionMultiply(num, mp, posAns, ++pos, c, offset);
    }

    private static void positionDivide(int n1[], int n2[], int ans[], int i) {
        if (i == ans.length)
            return;
        int cyc[] = new int[ans.length];
        int tmp[] = new int[cyc.length];
        int j = 0;
        while (true) {
            for (int x = 0; x < cyc.length; x++) {
                tmp[x] = cyc[x];
            }

            positionAdd(tmp, n2, tmp, 0, 0);
            boolean flag = gt(toStr(tmp), toStr(n1));
            if (flag) {
                break;
            }

            for (int x = 0; x < cyc.length; x++) {
                cyc[x] = tmp[x];
            }

            j++;
        }
        // TODO: 9/29/16 Leads to errors when dealing division of very large numbers with small numbers.
        ans[i] = j;
        positionSubtract(n1, cyc, n1, 0, 0);
        for (int x = n1.length - 1; x > 0; x--) {
            n1[x] = n1[x - 1];
            n1[x - 1] = 0;
        }
        positionDivide(n1, n2, ans, ++i);
    }

    //comparison methods

    /**
     * Compares two given numbers.
     *
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is greater than the second, false otherwise.
     */
    private static boolean gt(String n1, String n2) {
        int arr[] = compare(n1, n2);
        return (arr[0] > arr[1]);
    }

    /**
     * Compares two given numbers.
     *
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is greater than or equal to the second, false otherwise.
     */
    private static boolean ge(String n1, String n2) {
        int arr[] = compare(n1, n2);
        return (arr[0] >= arr[1]);
    }

    /**
     * Compares two given numbers.
     *
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is less than the second, false otherwise.
     */
    private static boolean lt(String n1, String n2) {
        int arr[] = compare(n1, n2);
        return (arr[0] < arr[1]);
    }

    /**
     * Compares two given numbers.
     *
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is less than or equal to the second, false otherwise.
     */
    private static boolean le(String n1, String n2) {
        int arr[] = compare(n1, n2);
        return (arr[0] <= arr[1]);
    }

    /**
     * Compares two given numbers.
     *
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is equal to the second, false otherwise.
     */
    private static boolean eq(String n1, String n2) {
        int arr[] = compare(n1, n2);
        return (arr[0] == arr[1]);
    }

    /**
     * Compares two given numbers.
     *
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is not equal to the second, false otherwise.
     */
    private static boolean ne(String n1, String n2) {
        int arr[] = compare(n1, n2);
        return (arr[0] != arr[1]);
    }

    //Arithmetic operations

    /**
     * Adds two given integers
     *
     * @param a Addend
     * @param b Addend
     * @return integer array containing the sum of the addends in the reverse order
     */
    public static int[] add(String a, String b) {
        int i = a.length(), j = b.length(), ans[] = null;
        int len = i > j ? i : j;
        ++len;
        try {
            num1 = transformReverse(a, len);
            num2 = transformReverse(b, len);
            ans = new int[len];
            positionAdd(num1, num2, ans, 0, 0);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return ans;
    }

    /**
     * Performs the multiplication of two given numbers.
     *
     * @param n1 Multiplicand
     * @param n2 Multiplier
     * @return Returns the product of the two, as a reversed int[] array
     */
    private static int[] multiply(String n1, String n2) {
        int ans[] = null;
        try {
            int len = n1.length() + n2.length();
            num1 = transformReverse(n1, len);
            num2 = transformReverse(n2, len);
            ans = new int[len];
            for (int i = 0; i < num2.length; i++) {
                int[] cur = new int[num1.length + i + 1];
                positionMultiply(num1, num2[i], cur, 0, 0, i);
                positionAdd(ans, cur, ans, 0, 0);
            }
        } catch (Error er) {
            er.printStackTrace();
        }
        return ans;
    }

    public static int[] subtract(String a, String b) {
        int i = a.length(), j = b.length(), ans[] = null;
        int len = i > j ? i : j;
        ++len;
        try {
            num1 = transformReverse(a, len);
            num2 = transformReverse(b, len);
            ans = new int[len];
            positionSubtract(num1, num2, ans, 0, 0);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return ans;
    }

    private static int[] divide(String n1, String n2, int pre) {
        int ans[] = new int[pre];
        try {
            num1 = transformReverse(n1, pre);
            num2 = transformReverse(n2, pre);
            positionDivide(num1, num2, ans, 0);
        } catch (Error er) {
            er.printStackTrace();
        }
        return ans;
    }

    /**
     * Populates the given answer to a human readable format.
     *
     * @param arr Integer array to be transformed.
     */
    public static void populateAns(String op1, String op2, String op, int arr[]) {
        if (arr == null) {
            System.out.println("Empty array!");
            return;
        }

        String str = "";
        for (int k = 0; k < arr.length; k++) {
            int z = arr.length - k - 1;
            if (k == 0 && arr[z] == 9)
                str += "-";
            else
                str += arr[z];
        }

        System.out.printf("%s %s %s = %s\n", op1, op, op2, str);
    }

    private static String toStr(int arr[]) {
        if (arr == null) {
            System.out.println("Empty array!");
            return null;
        }
        String s = "";
        for (int i : arr)
            s = String.format("%d%s", i, s);

        return s;
    }
}
