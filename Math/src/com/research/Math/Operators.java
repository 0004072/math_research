package com.research.Math;
import java.math.BigInteger;
import java.util.regex.*;

/**
 * Created by 000407 on 9/23/2016.
 */
public class Operators {
    private static int[] num1, num2, ans;

    public static void main(String[] args) {
        //perform("1024", "1024", "*");
        //perform("11a22", "2937", "=");
        //perform("123401651621651651651616541564542616516516556416516516516516516216541651651651651651651651651655454", "3128678165118941561651616515165165162165165165165165165165165156515616516516516516516516516516516511", "*");
        System.out.println(lt("594651465", "494644654646551864561564"));
        /*gt("2345", "2456");//false
        gt("2345", "3456");//false
        System.out.println();
        gt("2345", "2345");//true
        gt("2345", "2344");//true
        gt("2345", "2334");//true
        gt("2345", "2234");//true
        gt("2345", "1234");//true*/
    }

    /**
     * Generates the diminished radix complement of a decimal number.
     * @param arr Array containing the integer to find the complement of
     */
    private static void ninesComp(int[] arr){
        for(int i=0;  i<arr.length-1; i++)
            arr[i] = 9-arr[i];
    }

    /**
     * Generates the radix complement of a decimal number.
     * @param arr Number that needs to be complemented.
     */
    private static void tensComp(int[] arr){
        for(int i=0;  i<arr.length-1; i++)
            arr[i] = 9-arr[i];
        int[] one = new int[ans.length];
        one[0] = 1;
        positionAdd(arr, one, arr, 0, 0);
    }

    /**
     * Compares two given integers. Integers are taken as strings.
     * @param num1 Number to compare
     * @param num2 Number to be compared with
     * @return int[] array containing the indices of the
     *      - first occurrence of value at index in num1 is less than the value at index in num2.
     *      - first occurrence of value at index in num1 is greater than the value at index in num2.
     */
    private static int[] compare(String num1, String num2) {
        boolean flags[][];
        int len = num1.length() > num2.length() ? num1.length() : num2.length();
        int[] n1 = new int[len], n2 = new int[len];
        flags = new boolean[len][2];

        for (int i = 0; i < len; i++) {
            if (i < num1.length())
                n1[i] = num1.codePointAt(num1.length()-i-1) - 48;

            if (i < num2.length())
                n2[i] = num2.codePointAt(num2.length()-i-1) - 48;
        }

        for(int i = 0; i < len; i++){
            flags[len - i - 1][0] = n1[i] >= n2[i];

            flags[len - i - 1][1] = n1[i] <= n2[i];
        }

        int flag[] = {0,0};
        for(flag[0]=0; flag[0]<flags.length; flag[0]++){
            if(!flags[flag[0]][0])
                break;
        }

        for(flag[1]=0; flag[1]<flags.length; flag[1]++){
            if(!flags[flag[1]][1])
                break;
        }

        return flag;
    }

    /**
     * Performs the positional addition recursively until reaches the end of the array.
     * @param a First addend
     * @param b Second addend
     * @param ans Sum
     * @param pos Current position being operated
     * @param c Carry forward to next position
     */
    private static void positionAdd(int[] a, int[] b, int[] ans, int pos, int c){
        if(pos == ans.length-1)
            return;
        int numA = pos<a.length?a[pos]:0, numB = pos<b.length?b[pos]:0;
        ans[pos] = (numA+numB+c)%10;
        int carry = (numA+numB+c)/10;
        positionAdd(a, b, ans, ++pos, carry);
    }

    /**
     * Performs the subtraction at a given position.
     * @param a The minuend
     * @param b The subtrahend
     * @param ans The difference
     * @param pos Current position being operated
     * @param br Borrow from the next position
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
     * @param num The multiplicand
     * @param mp The multiplier
     * @param posAns Array that holds the multiple
     * @param pos Current position being operated
     * @param c Carry forward to the next point
     * @param offset Offset from the decimal point before adding to the overall answer
     */
    private static void positionMultiply(int[] num, int mp, int[] posAns, int pos, int c, int offset){
        if(pos == num.length)
            return;

        int val = (num[pos]*mp+c)%10;
        c = (num[pos]*mp+c)/10;
        posAns[pos+offset] = val;

        positionMultiply(num, mp, posAns, ++pos, c, offset);
    }

    /**
     * Performs the multiplication over 2 given numbers. Generates the result into an array in the reverse order.
     */
    private static void multiply(){
        ans = new int[num1.length+num2.length];
        for(int i=0; i<num2.length; i++){
            int[] cur = new int[num1.length+i+1];
            positionMultiply(num1, num2[i], cur, 0, 0, i);
            positionAdd(ans, cur, ans, 0, 0);
        }
    }

    private static void divide(){

    }

    //comparison methods

    /**
     * Compares two given numbers.
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is greater than the second, false otherwise.
     */
    private static boolean gt(String n1, String n2){
        int arr[] = compare(n1, n2);
        return (arr[0]>arr[1]);
    }

    /**
     * Compares two given numbers.
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is greater than or equal to the second, false otherwise.
     */
    private static boolean ge(String n1, String n2){
        int arr[] = compare(n1, n2);
        return (arr[0] >= arr[1]);
    }

    /**
     * Compares two given numbers.
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is less than the second, false otherwise.
     */
    private static boolean lt(String n1, String n2){
        int arr[] = compare(n1, n2);
        return (arr[0]<arr[1]);
    }

    /**
     * Compares two given numbers.
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is less than or equal to the second, false otherwise.
     */
    private static boolean le(String n1, String n2){
        int arr[] = compare(n1, n2);
        return (arr[0] <= arr[1]);
    }

    /**
     * Compares two given numbers.
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is equal to the second, false otherwise.
     */
    private static boolean eq(String n1, String n2){
        int arr[] = compare(n1, n2);
        return (arr[0] == arr[1]);
    }

    /**
     * Compares two given numbers.
     * @param n1 Number to compare
     * @param n2 Number to compare with
     * @return Return true if the first is not equal to the second, false otherwise.
     */
    private static boolean ne(String n1, String n2){
        int arr[] = compare(n1, n2);
        return (arr[0] != arr[1]);
    }

    /**
     * Performs the indicated operation over the given numbers. If the inputs are invalid or the operator is invaid,
     * an error will be thrown.
     * @param a First operand for the operation.
     * @param b Second operand for the operation.
     * @param op Operation to perform.
     */
    public static void perform(String a, String b, String op){
        int i = a.length(), j = b.length();
        int len = i > j ? i: j;
        ++len;

        num1 = new int[len];
        num2 = new int[len];
        ans = new int[len];

        try{
            if(!op.matches("[+*/-]{1}"))
                throw new Error(String.format("Invalid operation detected! %s", op));
            int val;
            for(int k=0; k<i; k++){
                val = a.codePointAt(i-k-1)-48;
                if(val < 0 || val > 9)
                    throw new Error(String.format("Invalid number('%c') detected!", a.charAt(k)));
                num1[k] = val;
            }

            for(int k=0; k<j; k++){
                val = b.codePointAt(j-k-1)-48;
                if(val < 0 || val > 9)
                    throw new Error(String.format("Invalid number('%c') detected!", b.charAt(k)));
                num2[k] = val;
            }

            switch(op){
                case "+":
                    positionAdd(num1, num2, ans, 0, 0);
                    break;

                case "-":
                    positionSubtract(num1, num2, ans, 0, 0);
                    if(ans[ans.length-1] == 9) {
                        tensComp(ans);
                    }
                    break;

                case "*":
                    multiply();
                    break;
            }
            populateAns();
        }
        catch(Throwable th){
            th.printStackTrace();
        }
    }

    /**
     * Populate ans[] array into a string in a human readable format.
     */
    public static void populateAns() {
        String str = "";
        for(int k=0; k<ans.length; k++) {
            int z = ans.length-k-1;
            if(k==0 && ans[z]==9)
                str += "-";
            else
                str += ans[z];
        }

        System.out.println("Answer="+str);
    }
}
