# java-explore-with-me

Дипломный проект выполненый в ходе обучения на платформе Яндекс.Практикум.

Explore with me - этой афиша. В этой афише можно предложить какое-либо событие от выставки до похода в кино и собрать компанию для участия в нём.

Приложение разделенно два основных модуля: основной сервис и сервис статистики.
Функцией сервиса статистики является сохранение и выгрузка информации о посещениях пользователями событий созданных в основном сервисе. 
Передача статистики происходит через модуль stat_client который реализован как подключаемая библиотека.
В основном же есть три группы API: публичный, приватный и административный и соответствующие им сервисы.

В процессе разработки решил использовать Hibernate для уменьшения времени на затраты написания простых запросов.

В процессе реализации научился создавать многомодульные приложения на maven, остальное же это закрепление всего того, что приобрел за период обучения.

