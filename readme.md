# Aquatic. Auto QA Framework for UI Testing

## Что такое Aquatic

Aquatic — это фреймворк для написания удобных для чтения и поддержки автоматизированных Web-тестов на Java.

Фреймворк предоставляет краткий API, благодаря которому можно формировать тест кейсы из шагов.
При этом формирование тест кейса сосредоточено исключительно на применении ранее созданных шагов.
Логика исполнения теста и его шагов отделены максимально и независимы, что позволяет написать универсальные шаги,
которые могут переиспользоваться в других тест-кейсах.

Aquatic сильно завязан на Selenide, поэтому создан он преимущественно для WEB-Тестирования.

### Модули

Aquatic старается придерживаться лучшим практикам проектирования opensource проектов и предоставлять API,
которым сможем воспользоваться любой желающий, без особой логики реализации под конкретные кейсы.

Для достижения этих практик проект разбит на модули:

- Aquatic Lib. Модуль базовых классов и инструментов. Не имеет настроек конфигурирования конкретной среды, под
  конкретный фреймворк.
  Предоставляет интерфейс AquaticApi для основного взаимодействия с внешней средой.

- Aquatic Spring Boot Starter. Стартер для Spring Boot 3, в котором реализована базовая логика для запуска тестов через
  REST, и поднятия контекста приложения.

- Aquatic Spring Example. Пример использования стартера для Spring Boot с базовыми тестами по воспроизведению 3 шагов на
  примере Google поиска.

## Использование

Полные примеры использования можно найти в модуле Spring Boot Example.

Для формирования теста необходимо:

- Сформировать список основных шагов тест-кейса
- Убедиться в достаточной декомпозиции шагов
- Понять в каком контексте существует тест
- Для каждого из шага реализовать предоставление `AquaticStep`
- Сформировать `TestTamplate` с использованием готовых шагов

### Определение контекста теста

Любой тест-кейс должен обладать контекстом теста, в рамках которого тест исполняется.
Для поиска в Google можно реализовать класс GoogleContext, который в качестве примера будет просто содержать искомую
строку.

Роль контекста в исполнении программы:

- Структурированное хранение переменных между исполняемыми шагами
- Предотвращение вызова шагов с разными контекстами

Простыми словами контекст это обычный Java Class, без особой логики.

```java

@Getter
@Setter
@NoArgsConstructor
public class GoogleContext {
    private String search;
}
```

### Реализация шага теста

Для определения логики исполнения шага в программе можно использовать два варианта.

#### Внутри класса

Шаги могут быть объявлены внутри класса, при этом их может быть бесконечное множество.
Над шагом должна быть объявлена аннотация `@AquaticStep`.
В качестве входящего аргумента необходимо указать тип контекста.

```java
public class SomeClass {
    @AquaticStep(
            id = "OPEN_GOOGLE",
            name = "Открытие гугла",
            preCondition = "Открыт браузер",
            postCondition = "Открыта базовая страница гугл поиска"
    )
    public void openGoogle(GoogleContext context) {
        Selenide.open("https://www.google.ru/");
    }
}
```

Таким образом, реализован шаг, где будет открыта главная страница Google.
При этом неизвестно кто и как вызовет исполнение шага, содержится лишь сценарий того, как ему в данном случае
отработать.

#### Самостоятельный класс

Логика шага может быть объявлена также внутри самостоятельного класса.
Данный кейс скорее необходим для ситуаций, когда мы хотим как-то параметризовать шаг,
при этом логику сделать для него общую, а не дублировать шаги.

```java

@AquaticStep(
        id = "WRITE_TO_SEARCH_GOOGLE_STEP",
        name = "Набор текста в поисковое окно гугла",
        preCondition = "Открыт гугл",
        postCondition = "Открыт гугл и в поле поиска вписано необходимое значение"
)
public class WriteToSearchGoogleStep {

    @AquaticStepParam
    private String searchText = "java";

    @AquaticStepExecution
    public void execution(GoogleTestContext context) {
        WebElement element = $x("//textarea");
        element.sendKeys(searchText);
        context.setSearch(searchText);
    }

    public static WriteToSearchGoogleStep searchCake(GoogleTestContext context) {
        return WriteToSearchGoogleStep.builder()
                .searchText("cake")
                .build();
    }

    ;

    public static WriteToSearchGoogleStep searchDonat(GoogleTestContext context) {
        return WriteToSearchGoogleStep.builder()
                .searchText("donat")
                .build();
    }

    ;
}
```

Здесь важно отметить метод аннотаций `@AquaticStepExecution`, в рамках которого будет описан сценарий обработки.
В качестве входящего аргумента метода необходимо обязательно указать тип контекста, для которого он применим.

Сами параметризованные шаги необходимо формировать тут же в виде статических методов, как это сделано для
`searchCake(...)` и `searchDonat(...)`.

### Формирование шаблона теста

Когда все шаги уже декомпозированы и реализованы, можно наконец собрать из них тест-кейсы.

Для этого необходимо объявить метод, возвращающий `TestTemplate<GoogleContext>`, который будет формировать цепочку
шагов.
Цепочка шагов формируется строго через ссылки на готовые методы шагов, использование lambda `(context) -> {...}`
выражений недопустимо.

> Здесь есть ограничение движка, что необходимо передавать ссылки на методы. Но на мой взгляд это даже правильно, что
> никакой логики в виде lamda выражений не должно присутствовать в сформированных шагах. Если хочется что-то добавить
> из логики в описание шагов теста, то значит шаги определены некорректны.

Каждый шаблон теста необходимо при этом отметить аннотацией `@AquaticTestTemplate`.

В итоге сформированные тесты выглядят следующим образом:

```java
public class SimpleGoogleTest {

    private final GoogleTestSteps googleTestSteps;

    @AquaticTestTemplate(
            id = "SIMPLE_FIRST_TEST",
            name = "Поиск в гугле тортика",
            description = "Ищем тортики"
    )
    public TestTemplate<GoogleContext> simpleFirstTest() {
        return TestTemplate.executeFor(GoogleTestContext.class)
                .then(googleTestSteps::openGoogle)
                .then(WriteToSearchGoogleStep::searchCake)
                .then(googleTestSteps::search);
    }

    @AquaticTestTemplate(
            id = "SIMPLE_SECOND_TEST",
            name = "Поиск в гугле пончика",
            description = "Ищем пончики"
    )
    public TestTemplate<GoogleContext> simpleSecondTest() {
        return TestTemplate.executeFor(GoogleTestContext.class)
                .then(googleTestSteps::openGoogle)
                .then(WriteToSearchGoogleStep::searchDonat)
                .then(googleTestSteps::search);
    }
}
```

На данном примере реализовано 2 тестовых кейса с различным результатом, но при этом из 3 одинаковых шагов.
В более сложных вариантах, где действия могут повторяться на различных этапах данное решение позволит по максимуму
переиспользовать уже раннее разработанные кейсы.

## FAQ

- Q: Зачем это все, почему не JUnit?<br/>
  A: Junit не удобен для дальнейших задумок. Кроме того, необходимо любой тестовый фреймворк нужно настроить для
  написания тестов, а тут есть все из коробки.


- Q: Можно ли использовать это с JUnit?<br/>
  A: Можно. Необходимо подключить Aquatic Lib и выполнить базовую конфигурацию как это примерно делается для Spring Boot
  Starter.


- Q: Есть ли интеграции с Allure, TestIT и прочее?<br/>
  A: В данный момент нет, но с помощью системы событийной архитектуры для результатов теста можно реализовать собственные решения.


- Q: В чем преимущество данного решения?<br/>
  A: Их на мой взгляд несколько:
    - Легкий порок вхождения. Не все тестировщики смогут с 0 написать качественный тестовый фреймворк, тут же уже есть
      все для базовой работы.
    - Максимальная попытка переиспользования шагов на различных этапах теста и в различных тестах. Встречал проекты,
      где одни и те же действия писались из теста в тест, не очень понятно зачем.
    - Возможность на основе Event-системы навязать свою логику по факту исполнения тестов. 
    - Отделение логики теста от его шагов, по чтению шаблона теста (`TestTemplate`) понятно о чем он.
    - Исключено использование глобальных переменных для хранения контекста теста
  
  Минусы тоже присутствуют:
    - Обязательное использование Method reference в описании шагов теста
    - Скромный функционал в текущий момент

## Release notes

### Версия 1.0.0

**Aquatic Lib**
- Создание тестов и шагов из Java кода
- Базовая логика для событийной системы

**Aquatic Spring Boot Starter**
- Возможность добавления теста в очередь по REST вызову

**Aquatic Spring Example**
- Реализован простенький пример использования фреймворка

## В планах

- [ ] Доработать событийную систему. Добавить событий.
- [ ] Реализовать модуль хранения результатов теста.
- [ ] Реализовать функционал запуска теста по REST из произвольного набора шагов.
- [ ] Доработать логику параметризованных шагов
- [ ] Перевести документацию на английский язык
- [ ] Реализовать example модуль для использования с JUnit 

