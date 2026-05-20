package core;

import model.enums.Language;

public class AppContext {

    private static Language language = Language.EN;

    public static Language getLanguage() {
        return language;
    }

    public static void setLanguage(Language lang) {
        language = lang;
    }

    public static String tr(String key) {

        if (language == Language.RU) {
            switch (key) {

                case "login":             return "Войти";
                case "register":          return "Регистрация";
                case "showUsers":         return "Показать Всех Пользователей";
                case "changeLanguage":    return "Изменить Язык";
                case "about":             return "О Системе";
                case "exit":              return "Выход";
                case "choose":            return "Выберите: ";
                case "wrong":             return "Неверный Выбор.";
                case "goodbye":           return "До Свидания!";

                case "mainMenu":          return "Главное Меню";
                case "welcome":           return "Добро Пожаловать!";
                case "allUsers":          return "Все Пользователи";
                case "langChanged":       return "Язык Изменён: ";

                case "studentMenu":       return "Меню Студента";
                case "availableCourses":  return "Доступные Курсы";
                case "registerCourse":    return "Записаться На Курс";
                case "myCourses":         return "Мои Курсы";
                case "myMarks":           return "Мои Оценки";
                case "transcript":        return "Транскрипт";
                case "viewTeacher":       return "Информация О Преподавателе";
                case "rateTeacher":       return "Оценить Преподавателя";
                case "studentOrgs":       return "Студенческие Организации";
                case "myOrg":             return "Моя Организация";
                case "subscribeJournal":  return "Подписаться На Журнал";
                case "notifications":     return "Уведомления";
                case "sendMessage":       return "Отправить Сообщение";
                case "switchLanguage":    return "Сменить Язык";
                case "becomeResearcher":  return "Стать Исследователем";
                case "submitRequest":     return "Подать Заявку";
                case "viewNews":          return "Новости И Комментарии";
                case "logout":            return "Выйти";

                case "teacherMenu":       return "Меню Преподавателя";
                case "putMark":           return "Поставить Оценку";
                case "studentsList":      return "Список Студентов";
                case "sendComplaint":     return "Отправить Жалобу";
                case "simpleReport":      return "Простой Отчёт";
                case "detailedReport":    return "Подробный Отчёт";
                case "research":          return "Исследования";
                case "recommendation":    return "Рекомендация";
                case "rating":            return "Рейтинг";

                case "managerMenu":       return "Меню Менеджера";
                case "courses":           return "Курсы";
                case "addCourse":         return "Добавить Курс";
                case "assignCourse":      return "Назначить Курс";
                case "studentsByGpa":     return "Студенты По GPA";
                case "studentsByName":    return "Студенты По Имени";
                case "teachers":          return "Преподаватели";
                case "report":            return "Отчёт";
                case "requests":          return "Заявки";
                case "publishNews":       return "Опубликовать Новость";
                case "newsList":          return "Список Новостей";
                case "createOrg":         return "Создать Организацию";
                case "addComment":        return "Добавить Комментарий";
            }
        }

        if (language == Language.KZ) {
            switch (key) {

                case "login":             return "Кіру";
                case "register":          return "Тіркелу";
                case "showUsers":         return "Барлық Қолданушыларды Көрсету";
                case "changeLanguage":    return "Тілді Өзгерту";
                case "about":             return "Жүйе Туралы";
                case "exit":              return "Шығу";
                case "choose":            return "Таңдаңыз: ";
                case "wrong":             return "Қате Таңдау.";
                case "goodbye":           return "Сау Болыңыз!";

                case "mainMenu":          return "Бас Мәзір";
                case "welcome":           return "Қош Келдіңіз!";
                case "allUsers":          return "Барлық Қолданушылар";
                case "langChanged":       return "Тіл Өзгертілді: ";

                case "studentMenu":       return "Студент Мәзірі";
                case "availableCourses":  return "Қолжетімді Курстар";
                case "registerCourse":    return "Курсқа Жазылу";
                case "myCourses":         return "Менің Курстарым";
                case "myMarks":           return "Менің Бағаларым";
                case "transcript":        return "Транскрипт";
                case "viewTeacher":       return "Оқытушы Туралы Ақпарат";
                case "rateTeacher":       return "Оқытушыны Бағалау";
                case "studentOrgs":       return "Студенттік Ұйымдар";
                case "myOrg":             return "Менің Ұйымым";
                case "subscribeJournal":  return "Журналға Жазылу";
                case "notifications":     return "Хабарландырулар";
                case "sendMessage":       return "Хабарлама Жіберу";
                case "switchLanguage":    return "Тілді Ауыстыру";
                case "becomeResearcher":  return "Зерттеуші Болу";
                case "submitRequest":     return "Өтініш Беру";
                case "viewNews":          return "Жаңалықтар Мен Пікірлер";
                case "logout":            return "Шығу";

                case "teacherMenu":       return "Оқытушы Мәзірі";
                case "putMark":           return "Баға Қою";
                case "studentsList":      return "Студенттер Тізімі";
                case "sendComplaint":     return "Шағым Жіберу";
                case "simpleReport":      return "Қысқа Есеп";
                case "detailedReport":    return "Толық Есеп";
                case "research":          return "Зерттеу";
                case "recommendation":    return "Ұсыныс";
                case "rating":            return "Рейтинг";

                case "managerMenu":       return "Менеджер Мәзірі";
                case "courses":           return "Курстар";
                case "addCourse":         return "Курс Қосу";
                case "assignCourse":      return "Курс Тағайындау";
                case "studentsByGpa":     return "GPA Бойынша Студенттер";
                case "studentsByName":    return "Аты Бойынша Студенттер";
                case "teachers":          return "Оқытушылар";
                case "report":            return "Есеп";
                case "requests":          return "Өтініштер";
                case "publishNews":       return "Жаңалық Жариялау";
                case "newsList":          return "Жаңалықтар Тізімі";
                case "createOrg":         return "Ұйым Құру";
                case "addComment":        return "Пікір Қосу";
            }
        }

        // Default English
        switch (key) {

            case "login":             return "Login";
            case "register":          return "Register";
            case "showUsers":         return "Show All Users";
            case "changeLanguage":    return "Change Language";
            case "about":             return "About System";
            case "exit":              return "Exit";
            case "choose":            return "Choose: ";
            case "wrong":             return "Wrong Choice.";
            case "goodbye":           return "Goodbye!";

            case "mainMenu":          return "Main Menu";
            case "welcome":           return "Welcome!";
            case "allUsers":          return "All Users";
            case "langChanged":       return "Language Changed: ";

            case "studentMenu":       return "Student Menu";
            case "availableCourses":  return "Available Courses";
            case "registerCourse":    return "Register For Course";
            case "myCourses":         return "My Courses";
            case "myMarks":           return "My Marks";
            case "transcript":        return "Transcript";
            case "viewTeacher":       return "View Teacher Info";
            case "rateTeacher":       return "Rate Teacher";
            case "studentOrgs":       return "Student Organizations";
            case "myOrg":             return "My Organization";
            case "subscribeJournal":  return "Subscribe To Journal";
            case "notifications":     return "Notifications";
            case "sendMessage":       return "Send Message";
            case "switchLanguage":    return "Switch Language";
            case "becomeResearcher":  return "Become Researcher";
            case "submitRequest":     return "Submit Request";
            case "viewNews":          return "View News & Comments";
            case "logout":            return "Logout";

            case "teacherMenu":       return "Teacher Menu";
            case "putMark":           return "Put Mark";
            case "studentsList":      return "Students List";
            case "sendComplaint":     return "Send Complaint";
            case "simpleReport":      return "Simple Report";
            case "detailedReport":    return "Detailed Report";
            case "research":          return "Research";
            case "recommendation":    return "Recommendation";
            case "rating":            return "Rating";

            case "managerMenu":       return "Manager Menu";
            case "courses":           return "Courses";
            case "addCourse":         return "Add Course";
            case "assignCourse":      return "Assign Course";
            case "studentsByGpa":     return "Students By GPA";
            case "studentsByName":    return "Students By Name";
            case "teachers":          return "Teachers";
            case "report":            return "Report";
            case "requests":          return "Requests";
            case "publishNews":       return "Publish News";
            case "newsList":          return "News List";
            case "createOrg":         return "Create Organization";
            case "addComment":        return "Add Comment";
        }

        return key;
    }
}