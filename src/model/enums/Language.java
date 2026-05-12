package model.enums;
public enum Language {
    KZ, EN, RU;
    public String getGreeting() {
        switch (this) {
            case KZ: return "Сәлем!";
            case RU: return "Привет!";
            default: return "Hello!";
        }
    }
}
