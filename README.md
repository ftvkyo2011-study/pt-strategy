## pt-strategy

### Семестровое задание по предмету "Технологии программирования"

### Dependencies:
* JDK 8 (JDK 9 не поддерживается этим Gradle)
* Gradle (Gradle wrapper will install it automatically)
* LWJGL (Gradle will install it automatially)

#### Основная информация
Для независимости от IntelliJ IDEA IDE используется система сборки
Gradle. Ее файлы:

```text
./
├── build.gradle
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
```

Также тут есть файл `.gitignore`, который указывает программе
Git, какие файлы не следует автоматически добавлять в
"файлы для коммита".

Кроме того, здесь есть файл `.gitattributes`, в котором программе Git указано,
что он должен относиться к .bat файлам с осторожностью и не заменять в них
CRLF на LF переносы строк при pull.

Все остальное лежит в папке `src/` - собственно исходный код программы.
Здесь есть две папки - `main` и `test`, в них содержатся
основной код и тесты, соответственно.

#### Установка и запуск
Все, что нужно - склонировать репозиторий и запустить команды:

```bash
./gradlew build
./gradlew run
```

Gradle автоматически загрузит зависимость -
библиотеку `LWJGL` - в свой кеш в домашней папке текущего пользователя.
После этого он запустит сборку, могут появиться директории `build` и/или `out`,
а потом Gradle запустит программу.
Точкой входа является метод `main` из класса `Game`, это указано в инструкциях
сборки в `build.gradle`: `mainClassName = 'Game'`.


#### Дополнительные инструкции:
* Чтобы собрать программу на Ubuntu, требуется установить
пакет `openjdk-8-jdk`.
* Чтобы добавить проект под контроль IntelliJ IDEA
(версия без встроенного JDK) вам нужно:
    1) Открыть папку с проектом, не изменяя стандартные параметры.
    2) Если IDEA выдает ошибку на стандартные классы Java, вам нужно зайти
    в File -> Project Structure -> SDKs и добавить там openjdk-8 заново.
    3) После этого нужно там же выбрать пункт Project и изменить JDK
    на вновь добавленный. После индексирования все заработает.
    4) Чтобы собрать проект, нужно создать цель для сборки. В правом верхнем
    углу в выпадающем списке выбрать "Edit Configurations" и создать
    конфигурацию Application. В ней прописать параметры Main class: Game,
    Use classpath or module: pt-strategy_main .


#### Ссылки
* [Концепция](https://docs.google.com/document/d/e/2PACX-1vSpHRiPfLlqVUm37otCjSqb913a2Rj6wKtj59bPCHMOy2IUX041F3jNrHfqbqNyguhXppTrVQphbS43/pub)
* [Техническое задание](https://docs.google.com/document/d/e/2PACX-1vQM3NRpSZ0w1k_juJK0osW2sz-D8cKg0Lm-9CzF7Vcr4Iquctj_FX4qmdZEONR3xDyRBMt_i7b5aspf/pub)
