package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.*;
import static java.lang.Integer.parseInt;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        Pattern check = Pattern.compile("[0-2][0-9]:[0-6][0-9]:[0-6][0-9]");
        Matcher matcher;
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        IllegalArgumentException exception = new IllegalArgumentException("Неверный формат входных данных.");
        String line;
        List<String> timeList = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                matcher = check.matcher(line);
                if (matcher.matches()) {
                    timeList.add(line);
                } else throw exception;
            }
        reader.close();
        Collections.sort(timeList);
        for (String element : timeList) {
            writer.write(element + "\n");
        }
        writer.close();
    }
    //Трудоемкость: O(n*log(n))
    //Ресурсоемкость: O(n)


    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 15.0
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 15.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException  {
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        String line;
        List<Double> timeList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if(parseDouble(line) >= -273.0 && parseDouble(line) <= 500.0)
                timeList.add(parseDouble(line));
            else
                throw new NumberFormatException("Неверный формат входных данных.");
        }
        reader.close();
        bucketSort(timeList);
        for (double element : timeList) {
            writer.write(element/10.0 + "\n");
        }
        writer.close();
    }

    static private void bucketSort(List<Double> list) {
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Double element: list) {
            max = max(element,max);
            min = min(element,min);
        }
        max *= 10;
        min *= 10;
        int[] buckets = new int[(int)(max) - (int)(min) + 1];
        for (double element: list) {
            buckets[(int)(element*10) - (int)min]++;
        }
        int arrayIndex = 0;
        for (int i = 0; i < buckets.length; i++) {
            for (int j = buckets[i]; j > 0; j--) {
                list.set(arrayIndex, i + min);
                arrayIndex++;
            }
        }
    }
    //Трудоемкость O(n) : n - кол - во температур в файле;
    //Ресурсоемкость R(n);

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        String line;
        Map<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> result = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            result.add(parseInt(line));
            map.put(parseInt(line), 0);
        }
        reader.close();

        for (int element : result) {
            map.put(element,map.get(element) + 1);
        }

        int mostCommonNumber = 0;
        int frequency = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > frequency) {
                frequency = entry.getValue();
                mostCommonNumber = entry.getKey();
            }
            if (entry.getValue() == frequency && entry.getKey() < mostCommonNumber)
                mostCommonNumber = entry.getKey();

        }
        List<Integer> sample = new ArrayList<>();
        sample.add(mostCommonNumber);
        result.removeAll(sample);

        while (frequency > 0) {
            result.add(mostCommonNumber);
            frequency--;
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (int element: result)
            writer.write(element + "\n");
        writer.close();
    }

    //Трудоемкость: O(n)
    //Ресурсоемкость: R(n + m) n - количество входны данных, m - количество элементов map


    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}