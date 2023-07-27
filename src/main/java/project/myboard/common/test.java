package project.myboard.common;

import java.util.Scanner;

public class test {

    private static String solution(String str) {
        String answer = "";
        for (int i=0; i<str.length(); i++) {
            if (str.indexOf(str.charAt(i))==i) answer += str.charAt(i);
        }

        return answer;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = "   .       .   .\n" +
                "\n" +
                "  ,  ,  ,  , .             ;  ,    ,    ,       .   .,    .     .   ,  ,  ,  , .\n" +
                "\n" +
                "    .    .       ., ,    ,    ,      .             ;     .  .,      .     .       .,      .    .     .,      .    .     .\n" +
                "\n" +
                " .  .    .\n" +
                "\n" +
                " .       .   ,  ,  ,  , .,  .   .  ,    ,    ,      .,   .  .       .\n" +
                "\n" +
                "  .   .  ,    ,    ,      .,  ,  ,  ,  , .     .    .,  .    .   .\n" +
                "\n" +
                " ,    ,    ,      .        .     .\n" +
                "\n" +
                "      .      .   ,  ,  ,  , .,  ,  ,   ,  , .  ,    ,    ,      .     .,    .    .  .\n" +
                "\n" +
                "      .  .  .,      .  .  .\n" +
                "\n" +
                "      .     .       .\n" +
                "\n" +
                "   .   ,  ,  ,  , .  .,       .   .   .\n" +
                "\n" +
                "  .    .       .\n" +
                "\n" +
                "  ,  ,  ,  , .     .    .,  .        .   ,  ,  ,  , .,      .     .       .\n" +
                "\n" +
                "   .   ,  ,  ,  , .  ., .  .  .\n" +
                "\n" +
                " ,    ,    ,      .  .       .\n" +
                "\n" +
                "    .  ,    ,    ,      .  ., .  .    .,  .    .    ., ,    ,    ,      .  .       .\n" +
                "\n" +
                "    .  ,    ,    ,      .  .,    .    .       ., .  .  .,      .     .       .\n" +
                "\n" +
                "   .   ,  ,  ,  , .  .,  .  .       .\n" +
                "\n" +
                "        ,   .    .         ,   .,  ,  ,  ,  , .    .     ,   .\n" +
                "\n" +
                "     ";
        System.out.println(solution(str));
    }

}