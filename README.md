<div align="center">
  <h1>Petclinic-test</h1>
  <p><strong>Фреймворк для выполнения автотестов веб-приложения Spring PetClinic Sample Application</strong></p>

  <p>
    <a href="#quick-start">Быстрый старт</a> ·
    <a href="#features">Возможности</a> ·
    <a href="#contributing">Участие</a>
  </p>
</div>
---

## О проекте

Данный фреймоворк предназначен для тестирования веб-приложения **Spring PetClinic Sample Application**.
Для запуска данного проекта необходимо развернуть у себя
проект [Spring PetClinic Sample Application](https://github.com/spring-projects/spring-petclinic).
У себя, данный проект, развернул с помощью **Docker Compose**.

## Установка

### Разворачивание проекта

- клонировать себе проект Spring PetClinic Sample Application
- собрать билд проекта
- развернуть с помощью Docker или Docker Compose

> Подробная инструкция описана в README.md проекта.

### Клонирование репозитория

`git clone https://github.com/raketavlad/petclinic-tests.git`

### Переход в проект

`cd petclinic-tests`

## Использование

### Запуск тестового прогона

Запуск тестового набора осущствляется командой:  
`mvn clean test`  
По умолчанию запускается smoke-набор.

### Переменные проекта

| **Переменная** | **Описание**                                                 | **Пример**                     |
|----------------|--------------------------------------------------------------|--------------------------------|
| testngXML      | Для выбора тестового набора<br/>(по умолчанию `smoke.xml`)   | `-DtestngXML='regression.xml'` |
| base.url       | Задаёт базовую url проекта<br/>(по умаолчанию из конфига)    | `-Dbase.url='google.com'`      |
| db.url         | Задаёт url базы данных <br/>(по умаолчанию из конфига)       | `-Ddb.url='jdbc:pgsql:...'`    |
| db.user        | Задаёт имя пользователя БД<br/>(по умаолчанию из конфига)    | `-Ddb.user='postgres'`         |
| db.password    | Задаёт пароль пользователя БД<br/>(по умаолчанию из конфига) | `-Ddb.password='postgres'`     |




