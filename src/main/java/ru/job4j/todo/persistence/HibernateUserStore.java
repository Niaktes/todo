package ru.job4j.todo.persistence;

import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

@Repository
@AllArgsConstructor
public class HibernateUserStore implements UserStore {

    private final CrudStore crudStore;

    /**
     * Сохранить пользователя в базе данных.
     * @param user пользователь.
     * @return пользователь с ID.
     */
    @Override
    public Optional<User> save(User user) {
        Optional<User> result = Optional.empty();
        if (crudStore.run(session -> session.save(user))) {
            result = Optional.of(user);
        }
        return result;
    }

    /**
     * Получить пользователя по логину и паролю.
     * @param login логин пользователя.
     * @param password пароль пользователя.
     * @return Optional пользователя.
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudStore.optional(
                "FROM User WHERE login = :uLogin AND password = :uPassword",
                User.class,
                Map.of("uLogin", login, "uPassword", password)
        );
    }

}