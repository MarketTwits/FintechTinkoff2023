# FintechTinkoff2023

## Финтех 2023
## Лабораторная работа
## Курс Android Developer
В качестве задания предлагаем Вам написать android-приложение со списком фильмов и их описанием.
В роли источника данных для нашего приложения будет выступать [неофициальный API кинопоиска](https://kinopoiskapiunofficial.tech/).
## Основные требования к приложению:
1. На главном экране необходимо отображать список популярных фильмов.
2. В каждой карточке фильма на главной странице должны содержаться следующие элементы:
* 2.1 Наименование фильма.
* 2.2 Изображение-постер фильма.
* 2.3 Год выпуска.
3. При клике на карточку открывается экран с постером фильма, описанием, жанром, страной производства.
4. Если сеть недоступна или в процессе загрузки произошла ошибка, необходимо предусмотреть уведомление пользователя об этом.
### Необязательные требования, но будет плюсом, если Вы сможете их реализовать:
1. При смене ориентации устройства, список фильмов занимает только 50% экрана, во второй половине будет отображаться описание фильма.
2. На главном экране присутствуют разделы «Популярное» и «Избранное». При длительном клике на карточку, фильм помещается в избранное и хранится в базе данных. Карточки фильмов из избранного доступны в оффлайн-режиме.
3. При просмотре популярных, выделяются фильмы, находящиеся в избранном.
4. В разделах доступен поиск фильмов по наименованию (в соответствии с выбранным разделом).
### Так же будет здорово, если:
• Приложение написано на Kotlin.
• Обеспечена общая плавность и стабильность приложения. 
• Во время длительных загрузок, отображаются шиммеры/прогресс бары. 
• Ответы от API должны быть закешированы хотя бы на время сессии. 
• Приложение покрыто UNIT тестами. 
### Примечания к реализации: 
• Для получения топа фильмов используйте get запрос /api/v2.2/films/top с параметрами type = TOP_100_POPULAR_FILMS . 
• Для получения описания фильма используйте get запрос /api/v2.2/films/top/<id фильма> 
* В качестве ключа API можно использовать e30ffed0-76ab-4dd6-b41f-4c9da2b2735b, этот ключ не имеет ограничений по количеству запросов в сутки, но имеет ограничение 20 запросов в секунду.  
• В качестве альтернативы вы можете зарегистрироваться самостоятельно и получить собственный ключ, но тогда будет действовать ограничение в 500 запросов в день, учитывайте это в момент отладки приложения.
• Мы предлагаем следовать примеру нашего дизайна, который можно посмотреть здесь. Но если вы уверены, что ваш вариант дизайна будет лучше – можете сделать по своему
