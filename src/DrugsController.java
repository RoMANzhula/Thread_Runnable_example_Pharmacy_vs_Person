import java.util.HashMap;
import java.util.Map;

public class DrugsController { //публичный класс КонтроллерЛекарств
    public static Map<Drug, Integer> allDrugs = new HashMap<Drug, Integer>();   //создали список дял нашего лекарства в виде Карты <Лекарство, Количество>

    static { //статический блок, который однозначно запуститься перед конструктором и проинициализирует наши обьекты
        Drug panadol = new Drug(); //создаем новый обьект - лекарство
        panadol.setName("Panadol"); //устанавливаем название новому лекарству через Сеттер
        allDrugs.put(panadol, 5); //добавляем в нашу Карту(список лекарств) новое лекарство с ценой

        Drug analgin = new Drug(); //создаем новый обьект - лекарство
        analgin.setName("Analgin"); //устанавливаем название новому лекарству через Сеттер
        allDrugs.put(analgin, 18); //добавляем в нашу Карту(список лекарств) новое лекарство с ценой

        Drug placebo = new Drug(); //создаем новый обьект - лекарство
        placebo.setName("Fervex"); //устанавливаем название новому лекарству через Сеттер
        allDrugs.put(placebo, 1); //добавляем в нашу Карту(список лекарств) новое лекарство с ценой
    }

    public synchronized void buy(Drug drug, int count) { //создаем метод купить(на вход получаем: лекарство и количество) и
        //синхронизируем его, чтоб монитор контролировал mutex для нескольких потоков (т.е. делаем допуск в метод для потоков поочередным от греха подальше)
        String name = Thread.currentThread().getName(); //создаем контейнер-строку, которая будет содержать в себе имя текущего потока
        if (!allDrugs.containsKey(drug)) { //если в нашем списке лекарств не будет совпадений по ключу с лекарством, которе
            //клиент хочет купить, то
            System.out.println("Not available"); //выводим в консоль следующий литерал
        }
        Integer currentCount = allDrugs.get(drug); //создаем(при помощи класса-обертки,т.к. в нем есть необходимые методы для
        //работы с обьектами) контейнер-счетчик текущего количества лекарств = из всего списка лекарств получить данное лекарство
        if (currentCount < count) { //если текущее количество лекарств меньше количества лекарств, которе хотят купить, то
            System.out.println(String.format("%s want %s %d pieces. In stock - %d", name, drug.getName(), count, currentCount)); //используя
            // строковый формат для отображения ответа, выводим в консоль - "Текущий поток" хочет "название лекарства" "кол-во лекарства"
            // шт. В наличии - "текущее кол-во лекарств в аптеке(складе)"
        } else { //иначе
            allDrugs.put(drug, (currentCount - count)); //в общий список лекарств добавляем (название лекарства, (текущее количество
            //лекарства в аптеке(складе) минус количество лекарства, которое хотят купить)
            System.out.println(String.format("%s buy %s %d pieces. Balance - %d", name, drug.getName(), count, (currentCount - count))); //опять
            //используем строковый формат для отображения ответа и выводим в консоль - "Текущий поток" купил(а) "название лекарства" "количество
            //лекарства" шт. Осталось - "текущее количество лекарства в аптеке(складе) минус количество лекарства, которое хотят купить"
        }
    }

    public synchronized void sell(Drug drug, int count) { //создаем метод продать (на вход получаем: название лекарства и его количество)
        System.out.println(Thread.currentThread().getName() + " Purchase " + drug.getName() + " " + count + " pieces."); //выводим в консоль -
        //имя текущего потока + литерал + название лекарства(с помощью Геттера) + литерал + количество лекарства
        if (!allDrugs.containsKey(drug)) { //если в нашем списке лекарств не будет совпадений по ключу с лекарством, которе
            //аптека хочет продать, то
            allDrugs.put(drug, 0); //в общий список лекарств добавляем лекарство со значением(количеством) 0
        }
        Integer currentCount = allDrugs.get(drug); //создаем(при помощи класса-обертки,т.к. в нем есть необходимые методы для
        //работы с обьектами) контейнер-счетчик текущего количества лекарств = из всего списка лекарств получить данное лекарство
        allDrugs.put(drug, (currentCount + count)); //в общий список лекарств добавляем (лекарство, (текущее количество лекарства +
        //количество, которое мы продаем))
    }
}
