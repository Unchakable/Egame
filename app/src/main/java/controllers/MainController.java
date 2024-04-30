package controllers;
import java.util.ArrayList;
import java.util.List;
import model.Questions;

public class MainController {
    public static List<Questions> getQuestions() {
        Questions questions;
        List<Questions> questionsList = new ArrayList<>();
        String question = "Автомат получает на вход два трёхзначных числа. По этим числам строится новое число по следующим правилам: записывается результат сложения значений старших разрядов двух заданных чисел; к нему дописывается результат сложения значений средних разрядов этих чисел по такому правилу: если он меньше первой суммы, то второе полученное число приписывается к первому слева, иначе — справа; итоговое число получают приписыванием справа к полученному после второго шага числу суммы значений младших разрядов исходных чисел. Определите, какое из предложенных чисел может быть результатом работы автомата.";
        String decision = "Решим задачу с помощью рассуждений. Итоговое число состоит из трёх частей, каждая из которых представляет собой сумму двух однозначных чисел. Сумма цифр старших разрядов принадлежит отрезку [2, 18]. Минимальные цифры в старших разрядах трёхзначного числа равны 1, минимальная сумма старших разрядов равна 2. Максимальные цифры в разрядах равны 9, максимальная сумма равна 18. Суммы цифр остальных разрядов принадлежат отрезку [0, 18]. По заданию можно определить, что вторая часть числа должна быть больше первой.";
        String rightAnswer = "91216";
        String wrongAnswer1 = "3507";
        String wrongAnswer2 =  "13412";
        String wrongAnswer3 =  "13197";
        boolean inUsed = false;
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "Производится одноканальная монозвукозапись с частотой дискретизации 16 кГц и 24-битным разрешением. Запись длится 1 минуту, её результаты записываются в файл, сжатие данных не производится. Какое из приведённых ниже чисел наиболее близко к размеру полученного файла, выраженному в мегабайтах?";
        decision = "Измерение звука производится 16 000 раз в секунду или 16000 ∙ 60 раз в минуту. Для сохранения результата каждого измерения требуется 24 бит = 3 байт. Всего 16 000 ∙ 60 ∙ 3 байт. Вычислим размер файла в мегабайтах: 6000 / 2048 = 3.";
        rightAnswer = "3";
        wrongAnswer1 = "0,2";
        wrongAnswer2 =  "2";
        wrongAnswer3 =  "4";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "Для кодирования некоторой последовательности, состоящей из букв А, Б, В, Г и Д, решили использовать неравномерный двоичный код, позволяющий однозначно декодировать двоичную последовательность, появляющуюся на приёмной стороне канала связи. Использовали код: А – 1, Б – 000, В – 001, Г – 011. Укажите, каким кодовым словом должна быть закодирована буква Д. Длина этого кодового слова должна быть наименьшей из всех возможных. Код должен удовлетворять свойству однозначного декодирования.";
        decision = "В задании используется префиксный код — код со словами переменной длины, в котором ни одно кодовое слово не является началом другого кодового слова. Он позволяет однозначно декодировать сообщение без специального разделения кодов символов. Проверим, являются ли предложенные ответы началом заданных кодовых слов или, наоборот, заданные коды — началом кодов, предложенных в ответах. 00 — является началом кода символа Б (000). 0101 — не является началом ни одного из заданных кодов символов, ни один из заданных кодов не является началом этого кода. 11 — код символа А (1) является началом этого кода. 010 — не является началом ни одного из заданных кодов символов, ни один из заданных кодов не является началом этого кода. Таким образом, кодом буквы Д может быть 0101 и 010. По условию задания требуется выбрать кодовое слово минимальной длины, следовательно, верным ответом является код 010.";
        rightAnswer = "010";
        wrongAnswer1 = "11";
        wrongAnswer2 =  "0101";
        wrongAnswer3 =  "00";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "Какое из приведённых имён удовлетворяет логическому условию: первая буква гласная, последняя буква согласная, в слове больше 5 букв?";
        decision = "Введём обозначения высказываний: А — первая буква гласная, В — последняя буква согласная, С — в слове больше 5 букв. Логическое условие можно записать в виде выражения: А → (В ∧ C) = А ∨ В ∧ C.";
        rightAnswer = "СЕРГЕЙ";
        wrongAnswer1 = "ПОЛИНА";
        wrongAnswer2 =  "ГЛЕБ";
        wrongAnswer3 =  "НИКИТА";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "Автоматическое устройство осуществило перекодировку информационного сообщения на русском языке длиной 75 символов, первоначально записанного в 16-битной кодировке Unicode, в 8-битный код КОИ-8. На сколько битов уменьшилась длина сообщения?";
        decision = "Сообщение состоит из 75 символов. Первоначально оно имело объём 75 ∙ 2 байт, после перекодирования — 75 ∙ 1 байт. Следовательно, объём сообщения уменьшился на 75 ∙ 2 – 75 = 75 байт = 600 бит.";
        rightAnswer = "600";
        wrongAnswer1 = "1200";
        wrongAnswer2 =  "800";
        wrongAnswer3 =  "400";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "При сдаче экзамена в университете в память компьютера заносится индивидуальный буквенно-цифровой код студента и его оценка. Код состоит из 12 символов. В качестве символов используют 26 букв английского алфавита и десятичные цифры в любом порядке. При этом используют посимвольное кодирование и все символы кодируются одинаковым и минимально возможным количеством битов. Оценка — число от 0 до 10 — также кодируется с использованием минимально возможного и одинакового количества битов. Код студента вместе с его оценкой записывается минимально возможным и целым числом байтов. Определите объём файла, содержащего такую информацию, после сдачи экзамена 48 студентами.";
        decision = "Количество возможных символов в индивидуальном коде студента 26 букв + 10 цифр = 36. Определим минимально необходимое количество битов для кодирования 36 различных символов. При мощности алфавита, равной двум (для кодирования числа используются цифры 0 и 1), количество разрядов (бит) для кодирования одного из 36 различных символов определяется по формуле: |I| = |Log2 N|, где I — количество битов для кодирования одного символа, а N — количество возможных различных символов. Запись |X| означает, что Х округляется до ближайшего большего целого. Для кодирования одного символа нам потребуется |log2 36| = 6 бит. На 12 символов кода необходимо 6 ∙ 12 = 72 бит. Для кодирования оценки необходимо |Log2 11| = 4 бит. Итого на каждого студента 72 + 4 = 76 бит, или 10 байт. Информация о 48 студентах займёт 48 ∙ 10 = 480 байт.";
        rightAnswer = "480 байт";
        wrongAnswer1 = "624 байт";
        wrongAnswer2 =  "384 байт";
        wrongAnswer3 =  "528 байт";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "По каналу связи передаются сообщения, содержащие только семь букв: А, Б, Г, И, М, Р, Я. Для передачи используется двоичный код, удовлетворяющий условию Фано. Кодовые слова для некоторых букв известны: А — 010, Б — 011, Г — 100. Какое наименьшее количество двоичных знаков потребуется для кодирования слова МАГИЯ?";
        decision = "Условие Фано означает, что ни одно кодовое слово не является началом другого кодового слова. Следующая буква должна кодироваться как 11, поскольку 10мы взять не можем. 100 взять не можем из-за Г, значит, следующая буква должна быть закодирована кодом 101. Следующая буква должна кодироваться как 000, поскольку 00 взять не можем, иначе не останется кодовых слов для оставшейся буквы, которые удовлетворяют условию Фано. Значит, последняя буква будет кодироваться как 001. Тогда наименьшее количество двоичных знаков, которые потребуются для кодирования слова МАГИЯ равно 2 + 3 + 3 + 3 + 3 = 14.";
        rightAnswer = "14";
        wrongAnswer1 = "28";
        wrongAnswer2 =  "32";
        wrongAnswer3 =  "16";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "Автомат обрабатывает натуральное число N (0 ≤ N ≤ 255) по следующему алгоритму: 1. Строится восьмибитная двоичная запись числа N; 2. Все цифры двоичной записи заменяются на противоположные (0 на 1, 1 на 0); 3. Полученное число переводится в десятичную запись; 4. Из нового числа вычитается исходное, полученная разность выводится на экран. Пример: дано число N = 13, алгоритм работает следующим образо: 1. Восьмибитная двоичная запись числа N: 00001101; 2. Все цифры заменяются на противоположные, новая запись 11110010; 3. Десятичное значение полученного числа 242; 4. На экран выводится число 242 − 13 = 229. Какое число нужно ввести в автомат, чтобы в результате получилось 111?";
        decision = "Заметим, что инверсия двоичной восьмибитной записи числа в сумме с исходным числом дает 11111111, то есть 255. (В исходном примере: 00001101 + 11110010 = 11111111.) Следовательно, если исходное число равно N, то инвертированное число равно 255 − N. Затем автомат осуществляет вычитание, вычисляя 255 − 2N. Поэтому, чтобы найти число, которое нужно ввести в автомат для получения 111, нужно решить уравнение 255 − 2N = 111. Тем самым, искомое число равно 72.";
        rightAnswer = "72";
        wrongAnswer1 = "84";
        wrongAnswer2 =  "96";
        wrongAnswer3 =  "64";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "Музыкальный фрагмент был оцифрован и записан в виде файла без использования сжатия данных. Получившийся файл был передан в город А по каналу связи за 15 секунд. Затем тот же музыкальный фрагмент был оцифрован повторно с разрешением в 2 раза выше и частотой дискретизации в 1,5 раза меньше, чем в первый раз. Сжатие данных не производилось. Полученный файл был передан в город Б; пропускная способность канала связи с городом Б в 2 раза выше, чем канала связи с городом А. Сколько секунд длилась передача файла в город Б?";
        decision = "Объём файла прямо пропорционален разрешению файла и его частоте дискретизации, следовательно, объём файла во втором случае в 2/1,5 = 4/3 раза больше. Длительность передачи обратно пропорциональна пропускной способности канала связи, откуда получаем, что длительность передачи файла во второй раз равна: 15 · (1/2) · (4/3) = 10.";
        rightAnswer = "10";
        wrongAnswer1 = "20";
        wrongAnswer2 =  "15";
        wrongAnswer3 =  "12";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "В каждой строке электронной таблицы записаны четыре натуральных числа. Определите, сколько в таблице таких четвёрок, из которых можно выбрать три числа, которые не могут быть сторонами никакого треугольника, в том числе вырожденного (вырожденным называется треугольник, у которого сумма длин двух сторон равна длине третьей стороны).";
        decision = "Заметим, что если наибольшее число из четырёх больше суммы двух наименьших чисел в строке, то в такой четвёрке чисел можно выбрать три числа, которые не могут быть сторонами никакого треугольника, в том числе вырожденного. Тогда в ячейку E1 запишем формулу =МАКС(A1:D1) и скопируем её во все ячейки диапазона E2:E5000. В ячейку F1 запишем формулу =МИН(A1:D1) и скопируем её во все ячейки диапазона F2:F5000. В ячейку G1 запишем формулу =НАИМЕНЬШИЙ(A1:D1;2) и скопируем её во все ячейки диапазона G2:G5000. Таким образом, найдём наибольшее число и два наименьших числа в строке. В ячейку H1 запишем формулу =ЕСЛИ(E1>F1+G1;1;0) и скопируем её во все ячейки диапазона H2:H5000. Окончательно, с помощью формулы = СУММ(H1:H5000) получим ответ   — 3094.";
        rightAnswer = "3094";
        wrongAnswer1 = "2028";
        wrongAnswer2 =  "5448";
        wrongAnswer3 =  "3694";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "С помощью текстового редактора определите, сколько раз, не считая сносок, встречается слово «долг» или «Долг» в тексте романа в стихах А.С.Пушкина «Евгений Онегин». Другие формы слова «долг», такие как «долги», «долгами» и т.д., учитывать не следует.";
        decision = "Воспользуемся поисковыми средствами текстового редактора. В строке поиска последовательно будем вводить сначала « долг», потом «Долг ». Подсчитав общее количество результатов, получаем ответ — 1.";
        rightAnswer = "1";
        wrongAnswer1 = "2";
        wrongAnswer2 =  "3";
        wrongAnswer3 =  "4";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        question = "В файле содержится последовательность целых чисел. Элементы последовательности могут принимать целые значения от −10000 до 10000 включительно. Определите количество пар последовательности, в которых только одно число оканчивается на 3, а сумма квадратов элементов пары не меньше квадрата максимального элемента последовательности, оканчивающегося на 3. В ответе запишите два числа: сначала количество найденных пар, затем максимальную из сумм квадратов элементов таких пар. В данной задаче под парой подразумевается два идущих подряд элемента последовательности.";
        decision = "Считаем числа из файла. Сначала найдём максимальное по модулю число, заканчивающееся на «3», и его квадрат запишем в переменную max_square. Далее пройдём по всей последовательности чисел, рассматривая каждые два соседних числа. Будем проверять, что только одно из них кончается цифрой «3», а также что их сумма квадратов не меньше значения переменной max_square. Если пара чисел будет удовлетворять условию, будем увеличивать значения счётчика count, а также в переменную max_square_sum записывать максимальную сумму квадратов.";
        rightAnswer = "180 - 190360573";
        wrongAnswer1 = "180 - 196360573";
        wrongAnswer2 =  "120 - 190360573";
        wrongAnswer3 =  "120 - 196360573";
        questions = new Questions(question, decision, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, inUsed);
        questionsList.add(questions);
        return questionsList;
    }
}
