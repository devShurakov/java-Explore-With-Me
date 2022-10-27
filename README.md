# Explore With Me. Cервис поиска интересных событий.
###### Пэт проект в рамках прохождения учебного курса Яндекс.
[![Explore With Me API Tests](https://github.com/Gidrosliv/java-explore-with-me/actions/workflows/api-tests.yml/badge.svg)](https://github.com/Gidrosliv/java-explore-with-me/actions/workflows/api-tests.yml)






## Описание:

Бэкенд сервиса, который позволяет находить интересные мероприятия вокруг себя, а также размещать свои. Пример интерфейса:

![Alt text](https://github.com/devShurakov/java-Explore-With-Me/blob/main/WebInterfaceExample.png)

Архитектура приложения представлена двумя частями, взаимодействующими между собой: основной сервис и сервис статистики. 

#### API основного сервиса разделён на три части:

Публичное API, доступна без регистрации любому пользователю сети:
* Сортировка списка событий по количеству просмотров либо по датам событий.
* Просмотр подробной информации о конкретном событии.
* Есть возможность получения всех имеющихся категорий и подборок событий (такие подборки могут составлять администраторы ресурса).
* Каждый публичный запрос для получения списка событий или полной информации о мероприятии фиксируется сервисом статистики.

Закрытое API, доступна только авторизованным пользователям:
* Пользователи могут добавлять в приложение новые мероприятия, редактировать их и просматривать после добавления.
* Могут подавать заявки на участие в интересующих мероприятиях.
* Создатель мероприятия имеет возможность подтверждать заявки, которые отправили другие пользователи сервиса.

Административное API, для администраторов сервиса:
* Добавление, изменение и удаление категорий для событий.
* Возможность добавлять, удалять и закреплять на главной странице подборки мероприятий.
* Модерацию событий, размещённых пользователями, — публикация или отклонение.
* Управление пользователями — добавление, просмотр и удаление.

#### Сервис статистики:
* Информация о количестве обращений пользователей к спискам событий. 
* Информация о количестве запросов к подробной информации о событии. 
* Отчет по собраной ринформации.

## Спецификация:

Спецификации [основного сервиса](https://github.com/devShurakov/java-Explore-With-Me/blob/main/ewm-main-service-spec.json) и [сервиса статистики](https://github.com/devShurakov/java-Explore-With-Me/blob/main/ewm-stats-service-spec.json) можно посмотреть с помощью [Swagger](https://editor-next.swagger.io) 

## Шаблоны проектирования:

В приложении использован шаблон проектирования Data Transfer Object (DTO Pattern).

## Тестирование проекта:

Проверить работоспособность приложения, можно с помощью [готовой коллекции тестов](https://github.com/devShurakov/java-Explore-With-Me/tree/main/postman), например в Postman.

## Как запускать приложение ?

* склонировать и открыть проект в IntelliJ IDEA 
* запустить приложение Docker
* выполнения сборку проекта
* далее в терминал запустить команду docker compose up и дождаться успешного запуска проекта в контейнерах Docker.

##  Технологический стек:
![Java 11](https://img.shields.io/badge/-Java-green) ![11](https://img.shields.io/badge/-11-orange) ![Spring Boot 2.7.2 ](https://img.shields.io/badge/-Spring%20Boot-blue) ![2.7.2 ](https://img.shields.io/badge/-2.7.2-orange) ![Postgres SQL](https://img.shields.io/badge/-Postgres%20SQL-brightgreen) ![Postgres SQL](https://img.shields.io/badge/-11--alpine%20-orange) ![Lombock](https://img.shields.io/badge/-Lombok%201.18.24-lightgrey) ![hib](https://img.shields.io/badge/-Hibernate%205.6.10%20-green) ![Apache](https://img.shields.io/badge/-Apache%20Maven%204.0.0-blue) ![Docker](https://badgen.net/badge/icon/docker?icon=docker&label) ![Git](https://badgen.net/badge/icon/github?icon=github&label)     

<a href="#" onClick="scroll(0,0); return false" title="наверх">вверх страницы</a>
