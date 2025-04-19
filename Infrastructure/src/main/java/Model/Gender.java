package Model;

/**
 * Перечисление {@code Gender} представляет пол человека.
 * Включает два значения:
 * <ul>
 *   <li>{@link #MALE} - Мужской пол</li>
 *   <li>{@link #FEMALE} - Женский пол</li>
 * </ul>
 * Пример использования:
 * <pre>
 *     Gender gender = Gender.MALE;
 * </pre>
 */
public enum Gender {
    /**
     * Мужской пол
     */
    MALE,

    /**
     * Женский пол
     */
    FEMALE;
}
