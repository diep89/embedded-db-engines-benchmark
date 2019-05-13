package mil.ea.cideso.satac;

import java.util.Arrays;

public class Conversions {
    public static void main(String args[]) {
        Integer i1 = 128;
        // byte b1 = i1; // compilation error

        // byte b1 = (byte) i1;
        byte b1 = i1.byteValue();

        System.out.println("int value: " + i1); // prints 10
        System.out.println("Converted byte value: " + b1); // prints 10

        String str = "Sexo";
        byte[] b = str.getBytes();
        System.out.println("Array " + b);
        System.out.println("Array as String" + Arrays.toString(b));
        String s = new String(b);
        System.out.println("String - " + s);
    }
}