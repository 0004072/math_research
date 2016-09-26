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
        perform("123401651621651651651616541564542616516516556416516516516516516216541651651651651651651651651655454", "3128678165118941561651616515165165162165165165165165165165165156515616516516516516516516516516516511", "*");
    }

    /**
     * Generates the diminished radix complement of a decimal number.
     * @param arr
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

    /*private static boolean gt(String num1, String num2) {
        boolean flag = false;
        int len = num1.length() > num2.length() ? num1.length() : num2.length();
        int[] n1 = new int[len], n2 = new int[len];

        for (int i = 0; i < len; i++) {
            if (i < num1.length())
                n1[i] = num1.codePointAt(i) - 48;

            if (i < num2.length())
                n2[i] = num2.codePointAt(i) - 48;
        }

        for(int i=0; i<len; i++){
            if()
        }

        return flag;
    }*/
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
            int val = 0;
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
