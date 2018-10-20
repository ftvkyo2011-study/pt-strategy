## pt-strategy

### Семестровое задание по предмету "Технологии программирования"

[![Build Status](https://travis-ci.com/ftvkyo/pt-strategy.svg?branch=master)](https://travis-ci.com/ftvkyo2011-study/pt-strategy)

**У этого репозитория есть раздел Wiki с дополнительной информацией.**

### CONTRIBUTING
Если вы хотите внести свой вклад в проект, пожалуйста, прочтите CONTRIBUTING.md

### Dependencies (зависимости):
* JDK 8 (JDK 9 не поддерживается этим Gradle)
* Gradle (Gradlew установит ее автоматически)
* LWJGL (Gradlew установит ее автоматически)

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

Gradle автоматически загрузит зависимость - библиотеку `LWJGL` -
в свой кеш в домашней папке текущего пользователя. После этого он запустит
сборку, могут появиться директории `build` и/или `out`, а потом Gradle
запустит программу. Точкой входа является метод `main` из класса `Main`,
это указано в инструкциях сборки в `build.gradle`: `mainClassName = 'Main'`.

Мы изменили конфигурационный файл Gradle так, чтобы библиотека `LWJGL`
правильно загружалась и на Linux и на Windows.


#### Запуск тестов:
Для этого достаточно запустить:

```bash
./gradlew test
```

Если Gradle не написал об ошибках - значит все тесты выполнились успешно.
