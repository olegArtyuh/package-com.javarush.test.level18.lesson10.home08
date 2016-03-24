package com.javarush.test.level18.lesson10.home08;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* Нити и байты
Читайте с консоли имена файлов, пока не будет введено слово "exit"
Передайте имя файла в нить ReadThread
Нить ReadThread должна найти байт, который встречается в файле максимальное число раз, и добавить его в словарь resultMap,
где параметр String - это имя файла, параметр Integer - это искомый байт.
Закрыть потоки. Не использовать try-with-resources
*/

public class Solution {
    public static Map<String, Integer> resultMap = new HashMap<String, Integer>();

    //write file name and start Thread
    public static void main(String[] args) throws IOException, InterruptedException {
        String fileName;
        ArrayList<String> names = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!(fileName = reader.readLine()).equals("exit")) {
            names.add(fileName);
        }

        for(String s : names) {
            new ReadThread(s).start();
        }reader.close();
    }

    public static class ReadThread extends Thread {
        public String name;
        public ReadThread(String fileName) {
            this.name = fileName;
            //implement constructor body
        }

        @Override
        public void run()
        {
            //read and write bytes collected
            ArrayList<Integer> arr = new ArrayList<Integer>();
            try {
                FileInputStream fileInputStream = new FileInputStream(name);
                while (fileInputStream.available() > 0) {
                    arr.add(fileInputStream.read()); }
                fileInputStream.close();
            }catch (Exception e) {}

            int byt = 0;
            int max = 0;
            int count;

            //the logic of calculating the maximum repetitive bytes
            for (int i = 0; i < arr.size(); i++) {
                count = 0;
                for (int j = 0; j < arr.size(); j++) {
                        if (i != j) {
                            if (arr.get(i).equals(arr.get(j))) {
                                count++;
                            }
                        }
                    }
                if (count > max || count == max) {
                        byt = arr.get(i);
                        max = count;
                }
            }
            //the result of the addition
            synchronized (resultMap) {
                resultMap.put(name, byt);
            }
        }
    }
}