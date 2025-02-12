package com.yers.pandev_tech_task.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextsHelperUtil {
    public static String fileError(){
        return "❌ Ошибка при обработке файла!";
    }
    public static String fileSuccess(){
        return "✅ Категории успешно загружены!";
    }
    public static String noCategoriesInDb(){
        return "❌ В базе данных нет категорий!";
    }
    public static String categoriesSuccessfullyAddedInDb(){
        return "✅ Дерево категорий успешно добавлено!";
    }
    public static String categoriesAddedInRootOfCategory(String name){
        return "✅ Добавлена корневая категория: " + name;
    }
    public static String categoriesAddedInRootOfCategoryWithChildElement(String name, String parentName){
        return "✅ Добавлен дочерний элемент '" + name + "' в '" + parentName + "'";
    }
    public static String categoriesWithParentNameNotFound(String parentName){
        return "❌ Ошибка: родительская категория '" + parentName + "' не найдена!";
    }
    public static String categoriesParentNameAlreadyExist(String parentName){
        return "❌ Ошибка: категория '" + parentName + "' уже существует!";
    }
    public static String categoriesSuccessfullyRemovedFromDb(String parentName){
        return "✅ Категория '" + parentName + "' и все её подкатегории удалены!";
    }
    public static String categoriesNotFound(String name){
        return "❌ Ошибка: категория '" + name + "' не найдена!";
    }
    public static String adminPanel(){
        return """
        📌 **Доступные команды для Админа:**
        
        🔹 `/viewTree` - Просмотр дерева категорий.
        🔹 `/addElement <название>` - Добавить корневой элемент.
        🔹 `/addElement <родитель> <дочерний>` - Добавить подкатегорию.
        🔹 `/removeElement <название>` - Удалить элемент (и все его подкатегории).
        🔹 `/download` - Скачать Excel-файл с категориями.
        🔹 `/upload` - Загрузить Excel-файл с категориями.
        🔹 `/help` - Показать список доступных команд.
        
        ✨ **Бот для управления деревом категорий.**""";
    }
    public static String becameAdmin(){
        return """
        🎉 ✅ Вы стали админом!
        
        %s
        """;
    }
    public static String alreadyHasUserRole() {
    return "ℹ️ У вас уже минимальная роль (User).";}
    public static String becameUser() {
    return "✅ Вы стали пользователем!";
    }

    public static String wrongPassword() {
    return "❌ Неверное секретное слово!";}

    public static String AlreadyAdmin() {
    return "✅ Вы уже админ!";}

    public static String wrongCommand() {
    return "❌ Неизвестная команда. Используйте /help для просмотра доступных команд.";}

    public static String errorDuringSendMessage() {
    return "Пройзошла ошибка при отправке сообщение пользователю.";}

    public static String problemUploadingFile() {
    return "❌ Ошибка при загрузке файла!";}
    public static String problemSendingFile() {
        return "❌ Ошибка при отправке файла!";}

    public static String noEnoughRightToDelete() {
    return "❌ У вас нет прав для удаления данных ❌";}

    public static String addElementWithChildElement() {
    return "⚠️ Используйте: /addElement <родительская_категория> <дочерняя_категория>";}

    public static String startText() {
    return """
            👋 Приветствуем вас в Телеграм-боте 'Дерево Категорий'!
            
            Этот бот демонстрирует мои навыки и возможности работы с категориями.
            Доступные команды:
            ✅ /help — список команд
            ✅ /viewTree — показать дерево категорий
            ✅ /check <секретное_слово> — проверить доступ к роли админа
            ✅ /removeElement <категория> — удалить категорию (⚠️ только для админов)
            ✅ /addElement <родительская_категория> <дочерняя_категория> — добавить категорию (⚠️ только для админов)
            ✅ /download — скачать Excel-файл с категориями
            ✅ /upload — загрузить Excel-файл с категориями
            ✅ /downgrade — понизить роль пользователя (⚠️ только для админов)
            """;
    }


    public static String warnHowToRemoveElementFromCategory() {
        return "⚠️ Используйте: /removeElement <категория>";
    }

    public static String nullAnswerFromServer() {
    return "❌ Ошибка: пустой ответ от сервиса";}

    public static String warnHowToUsePassword() {
    return "⚠️ Использование: /check <секретное_слово>";}
}
