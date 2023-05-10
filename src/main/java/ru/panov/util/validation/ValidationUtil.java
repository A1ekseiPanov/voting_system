package ru.panov.util.validation;

import lombok.experimental.UtilityClass;
import ru.panov.HasId;
import ru.panov.error.IllegalRequestDataException;
import ru.panov.error.NotFoundException;

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

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
}