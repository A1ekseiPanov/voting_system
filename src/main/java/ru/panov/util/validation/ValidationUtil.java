package ru.panov.util.validation;

import lombok.experimental.UtilityClass;
import ru.panov.HasId;
import ru.panov.error.IllegalRequestDataException;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId hasId) {
        if (!hasId.isNew()) {
            throw new IllegalRequestDataException(hasId.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId hasId, int id) {
        if (hasId.isNew()) {
            hasId.setId(id);
        } else if (hasId.id() != id) {
            throw new IllegalRequestDataException(hasId.getClass().getSimpleName() + " must has id=" + id);
        }
    }
}