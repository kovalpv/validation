# Приложение validation

##

Проект Validation представляет собой приложение для управления заказами, которое демонстрирует валидацию данных на уровне домена при выполнении бизнес-операций. Ошибки обрабатываются и выталкиваются на уровень запроса.

## Технологии

- **Spring Boot**: 3.3.7
- **Java**: 21
- **MapStruct**
- **Lombok**
- **ArchUnit**

## Запуск приложения

1. Убедитесь, что у вас установлен JDK 17 и Maven.
2. Склонируйте репозиторий:

```bash
   git clone https://github.com/your-repo/fastsoft-validation.git
```

3. Перейдите в директорию проекта:

```bash
   cd fastsoft-validation
```

4. Запустите приложение:

```bash
   mvn spring-boot:run
```

## Тестирование

```bash
   mvn test
```

## http-client

Для выполнения запросов вручную, можно воспользоваться файлом http-client `docs/order.rest`.