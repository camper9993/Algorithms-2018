package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.StrictMath.sqrt;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String firs, String second) {
        char[] first = firs.toCharArray();
        char[] sec = second.toCharArray();
        char[][] arr = new char[first.length + 1][sec.length + 1];
        StringBuilder strMax = new StringBuilder();


        for (int i = 0; i < first.length + 1; i++) {
            for (int j = 0; j < sec.length + 1; j++) {
                if (i == 0 && j > 0) {
                    arr[i][j] = sec[j - 1]; //Заполняем первую строку вторым словом
                }
                if (i > 0 && j == 0) {
                    arr[i][j] = first[i - 1]; //Заполняем первый столбец первым словом
                }
                if (i > 0 && j > 0 && arr[0][j] == arr[i][0]) {
                    StringBuilder str = new StringBuilder();
                    arr[i][j] = '!'; //Ставим !, в клетку координатами которой являются одинковые буквы.
                    str.append(arr[i][0]);
                    int a = i;
                    int b = j;
                    while (arr[a - 1][b - 1] == '!' && a > 0 && b > 0) {
                        a--;
                        b--;                            //Проверяем, сколько одинаковых подряд букв у нас идет
                        str.append(arr[a][0]);
                    }
                    if (str.length() > strMax.length()) //Если строка больше, чем предудащая максимальная подстрока, меняем ее.
                        strMax = str;
                }
            }

        }
        return strMax.reverse().toString();//Так как мы записываем подстроку начиная с конца нашей таблицы то ее нужно переписать задом на перед.
    }
    //Трудемкость: O(n * m) n,m - длина заданный слов
    //Ресурсоемкость : O(n * m)

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается...
     */
    static public int calcPrimesNumber(int limit) {
        int count = 0;
        if (limit <= 1)
            return count;
        else {
            int number = 2;
            while (number <= limit) {
                for (int i = 2; i <= sqrt(number); i++) {
                    if (number % i == 0) {
                        count--;
                        break;
                    }
                }
                number++;
                count++;
            }
        }
        return count;
    }
    //Трудоемкость : T = O(n * sqrt(n))
    //Ресурскоемкость : R = O(1)
    //n = limit

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    //Трудоемкость - ???
    //Ресурсоемкость = R(height * width + path.size)
    static class Balda {
        private static Set<String> result;
        private static int height;
        private static int width;
        private static List<String> list;
        private static Set<Integer> path;


        static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException {
            list = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(inputName));
            String line;
            String[] lenght;
            while ((line = reader.readLine()) != null) {
                lenght = line.split(" ");
                list.addAll(Arrays.asList(lenght));
                width = lenght.length;
            }
            reader.close();
            result = new HashSet<>();
            height = list.size() / width;
            int size = result.size();
            for (String word : words)
                for (int i = 0;i < list.size();i++)
                    if (list.get(i).equals(String.valueOf(word.charAt(0)))) {//Если нашли первую букву слова, запускаем
                        path = new HashSet<>();                     //рекурсивынй алгоритм поиска остальной части слова.
                        findWord(word, i, 1);                //path нужен для того, чтобы он не возвращался искать
                            if (result.size() > size) {            //буквы которые уже нашел.(запоминать пройденный маршрут)
                                size = result.size();
                                break;
                            }
                    }
            return result;
        }

        private static boolean checkInList(int index) {
            return index >= 0 && index < list.size();
        }

        private static void findWord(String word,int index, int count) {//Сам рекурсивный алгоритм
            if (count != word.length()) {
                path.add(index);
                String letter = String.valueOf(word.charAt(count));
                if (checkInList(index - 1) && list.get(index - 1).equals(letter) && index % width != 0 && !path.contains(index - 1)) {
                    path.add(index - 1);
                    findWord(word, index - 1, count + 1);
                }
                if (checkInList(index + 1) && list.get(index + 1).equals(letter) && (index + 1) % width != 0 && !path.contains(index + 1)) {
                    path.add(index + 1);
                    findWord(word, index + 1, count + 1);
                }
                if (checkInList(index - width) && list.get(index - width).equals(letter) && !path.contains(index - width)) {
                    path.add(index - width);
                    findWord(word, index - width, count + 1);
                }
                if (checkInList(index + width) && list.get(index + width).equals(letter) && !path.contains(index + width)) {
                    path.add(index + width);
                    findWord(word, index + width, count + 1);
                }
            }
            else {
                result.add(word);
            }
        }
    }
}
