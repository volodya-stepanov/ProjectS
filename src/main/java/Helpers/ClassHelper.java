package Helpers;

/**
 * Содержит вспомогательные функции для работы с классами
 */
public class ClassHelper {  //TODO: Разобраться, почему не получается сделать класс статическим. Или вообще избавиться от класса

    /**
     * Определяет, является ли данный объект экземпляром данного класса
     * @param obj Объект
     * @param cl Класс
     * @return Истина, если объект является экземпляром класса, иначе ложь
     */
    public boolean isTypeOf(Object obj, Class cl){
        if (obj.getClass().equals(cl)){
            return true;
        }

        return false;
    }
}
