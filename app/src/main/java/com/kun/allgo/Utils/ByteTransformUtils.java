package com.kun.allgo.Utils;

/**
 * Created by 12120 on 7/1/2016.
 */
public class ByteTransformUtils {
    public static byte[] TransformFromCSharp(String src)
    {
        String[] data = src.split(",");
        byte[] result = new byte[data.length];

        for (int i = 0; i < data.length; i++)
        {
            int temp = Integer.valueOf(data[i]);
            if (temp >= 128)
            {
                temp -= 256;
            }
            result[i] = (byte) temp;
        }

        return result;
    }

    public static String TransformToCSharp(byte[] src)
    {
        String result = "";

        for (int i = 0; i < src.length; i++)
        {
            int temp = (int) (src[i]);
            if (temp < 0)
            {
                temp += 256;
            }
            result += String.valueOf(temp)+",";
        }
        result = result.substring(0, result.length()-1);
        return result;
    }
}
